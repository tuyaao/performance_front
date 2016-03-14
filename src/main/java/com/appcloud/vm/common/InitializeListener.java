package com.appcloud.vm.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;

import com.appcloud.mysqldao.BdLogDao;
import com.appcloud.mysqldao.MySQLOperFactory;
import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.action.dbentity.VMInstance;
import com.appcloud.vm.action.dbentity.VMhardware;
import com.appcloud.vm.action.entity.CloudPlatformEntity;
import com.appcloud.vm.action.entity.SumRankingAbstractEntity;
import com.appcloud.vm.action.entity.SumRankingOperaFactory;
import com.appcloud.vm.action.entity.VM48InforEntity;

//由于在开始进行了数据的获取，所以要监听数据库的改变。包括相应proxy的findall()和得到4核8G的主机所有相关数据
public class InitializeListener implements ServletContextListener {
 
    private static Boolean twice = false;	
	private static ArrayList<CloudPlatform> cloudPlatformList;
	private static ArrayList<VMInstance> VmInstanceList;
	private static ArrayList<VMhardware> vmHardwareList;

	private static ArrayList<VM48InforEntity> VM48InforList = new ArrayList<VM48InforEntity>();
	private static ArrayList<CloudPlatformEntity> cloudPlatformEntityList = new ArrayList<CloudPlatformEntity>();
	//private static ArrayList<ArrayList<? extends SumRankingAbstractEntity>> cloudPlatformRankingList = new ArrayList<ArrayList<? extends SumRankingAbstractEntity>>();
	//本来是这么写的，但是没法进行泛型转换
	private static SumRankingOperaFactory cloudPlatformRankingList = new SumRankingOperaFactory();
	private static BdLogDao dbLogDao = new BdLogDao();// 这里只有一个线程使用，多线程访问不安全

	public InitializeListener() {
// 这里本想双层的list,内部的list的size为0，填充公司名称和id的时候再生成的；但是实际生成的时候没有办法判断里面的具体子类是什么，没有办法网恋
//		Properties p = new PropertiesUtil()
//				.getPropertyFileConfiguration("create-instance-conf.properties");
//
//		String string = p.getProperty("SumRankingEntity");
//		String[] strings = string.split(",");
//		for (String a : strings) {
//			Array.newInstance(CloudPlatformEntity.class,10);
//			try {
//				cloudPlatformRankingList
//						.add((ArrayList<SumRankingAbstractEntity>)Array.newInstance(
//								Class.forName("com.appcloud.vm.action.entity.SumRanking"
//										+ a + "Entity"), strings.length));
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if(twice){
		return;
		}else{
			twice = true;
		}
		System.out.println("进入initializeListener");
		Session session = HibernateSessionFactory.getInstance().getSession();// 初始化HibernateSessionFactory,并预先获取session
		session.close();
		System.out.println("初始化HibernateSessionFactory完毕");
		// 获取云平台，云主机和云主机配置的所有信息
		new ThreadPool();
		System.out.println("初始化ThreadPool完毕");
		iniAll();
		System.out.println("初始化各类数据完毕");
		// ThreadPool.execute(new CheckTask());
	}

	@SuppressWarnings("unchecked")
	private void iniAll() {//现在使用的公司的所有数据(不只4核8G)。现在是取出全部，然后根据HashSet删除无用的数据。实际应该在数据库获取的时候就筛选。
		try {
			cloudPlatformList = (ArrayList<CloudPlatform>) MySQLOperFactory
					.getAll(CloudPlatform.class);
			System.out.println("总共有的公司个数：" + cloudPlatformList.size());

			// 由于数据库的数据遗留，这里只要6家公司的主机
			HashSet<Integer> temp = new HashSet<Integer>();
			for(CloudPlatform it : cloudPlatformList){
				temp.add(it.getId());
			}
			VmInstanceList = (ArrayList<VMInstance>) MySQLOperFactory
					.getAll(VMInstance.class);
			Iterator<VMInstance> VmInstanceListIter = VmInstanceList.iterator();
			while (VmInstanceListIter.hasNext()) {
				VMInstance vmInstance = VmInstanceListIter.next();
				if (!temp.contains(vmInstance.getCloudPlatformId())) {
					VmInstanceListIter.remove();
				}
			}
			System.out.println("总共有的虚拟机个数：" + VmInstanceList.size());

			// 由于数据库的数据遗留，这里只要6家公司的主机
			temp.clear();
			for(VMInstance it : VmInstanceList){
				temp.add(it.getId());
			}
			vmHardwareList = (ArrayList<VMhardware>) MySQLOperFactory
					.getAll(VMhardware.class);
			Iterator<VMhardware> VmHardwareIter = vmHardwareList.iterator();
			while (VmHardwareIter.hasNext()) {
				VMhardware vmHardware = VmHardwareIter.next();
				if (!temp.contains(Integer.parseInt(vmHardware.getUuid()))) {
					VmHardwareIter.remove();
				}
			}
			System.out.println("总共有的虚拟机的配置个数：" + vmHardwareList.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			iniVm48List();
			iniCloudPlatformEntityList(cloudPlatformList);// 注意初始化的依赖顺序
			iniCloudPlatformRankingList(VM48InforList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void iniVm48List() {
		for (VMhardware vmHardware : vmHardwareList) {
			if (vmHardware.getCpu().equals(4)) {
				VM48InforEntity vM48InforEntity = new VM48InforEntity();
				vM48InforEntity.setId(Integer.valueOf(vmHardware.getUuid()));
				vM48InforEntity.setCpu(vmHardware.getCpu());
				vM48InforEntity.setMemory(vmHardware.getMemory());
				vM48InforEntity.setDisk(vmHardware.getDisk());
				for (VMInstance vmInstance : VmInstanceList) {
					if (vmInstance.getId().equals(vM48InforEntity.getId())) {
						Integer cloudPlatformId = vmInstance
								.getCloudPlatformId();
						vM48InforEntity.setOs(vmInstance.getOs());
						for (CloudPlatform cloudPlatform : cloudPlatformList) {
							if (cloudPlatform.getId().equals(cloudPlatformId)) {
								vM48InforEntity.setPlatformId(cloudPlatform
										.getId());
								vM48InforEntity.setPlatformName(cloudPlatform
										.getName());
							}
						}
					}
				}
				VM48InforList.add(vM48InforEntity);
			}
		}
//		 testGetIniVm48();
	}

	/***
	 * @param cloudPlatformList
	 *            所有厂商的bean
	 * @return 前端需要的关于厂商的所有数据
	 * @throws Exception
	 */
	private void iniCloudPlatformEntityList(
			ArrayList<CloudPlatform> cloudPlatformList) throws Exception {
		for (CloudPlatform cloudPlatform : cloudPlatformList) {
			CloudPlatformEntity cloudPlatformEntity = new CloudPlatformEntity();
			cloudPlatformEntity.setId(cloudPlatform.getId());
			cloudPlatformEntity.setName(cloudPlatform.getName());
			cloudPlatformEntity.setContainCompanyQuantity(0);// 前端没有使用，所以默认返回0，使用了再查找
			cloudPlatformEntityList.add(cloudPlatformEntity);
		}
	}

	/***
	 * @param VM48InforList
	 *            所有厂商的4核8G的主机列表,由于一个公司只有一个4核8G的主机,所以用此主机代替公司
	 *            对于每个公司，将SumRankingOperaFactory下面所有的域里面填充一下这个公司的基础数据
	 * @return SumGetchatAction 里面需要的前端各指标排名的类
	 * @throws Exception
	 */
	private void iniCloudPlatformRankingList(
			ArrayList<VM48InforEntity> VM48InforList) throws Exception {
		for (int i = 0; i < VM48InforList.size(); i++) {
				 Field[] fs = SumRankingOperaFactory.class.getDeclaredFields();
			       for ( int j = 0 ; j < fs.length ; j++){
			           Field f = fs[j];
			           f.setAccessible( true ); // 设置些属性是可以访问的
			           ArrayList<SumRankingAbstractEntity> val = (ArrayList<SumRankingAbstractEntity>)f.get(cloudPlatformRankingList);
			           val.get(i).setId(VM48InforList.get(i).getId());
			           val.get(i).setCloudPlatformName(VM48InforList.get(i).getPlatformName());
			       }
//				for (int j = 0; j <= cloudPlatformRankingList.size(); j++) {
//					ArrayList<SumRankingAbstractEntity> it = (ArrayList<SumRankingAbstractEntity>) Array
//							.get(cloudPlatformRankingList, i);
//					it.get(i).setId(vM48InforEntity.getId());
//					it.get(i).setCloudPlatformName(
//							vM48InforEntity.getPlatformName());
//				}
		}
		System.out.println("QQ的list的个数："+cloudPlatformRankingList.getCloudPlatformPingQQRankingList().size());
		System.out.println("一共多少个："+cloudPlatformRankingList.getCloudPlatformPingBaiduRankingList().size());
	}

	private void testGetIniVm48() {
		for (VM48InforEntity vM48InforEntity : VM48InforList) {
			System.out.println("云平台ID:" + vM48InforEntity.getPlatformId()
					+ "__云平台名字:" + vM48InforEntity.getPlatformName()
					+ "__云主机ID:" + vM48InforEntity.getId() + "__CPU:"
					+ vM48InforEntity.getCpu() + "__MEM:"
					+ vM48InforEntity.getMemory() + "__DISK:"
					+ vM48InforEntity.getDisk());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	// 在程序开始的时候就运行，用来判断预获取的数据是否改变，如果改变了就重新获取
	class CheckTask implements Runnable, Serializable {

		CheckTask() {
		}

		public void run() {
			while (true) {
				if (!dbLogDao.countAll().equals(0)) {
					dbLogDao.deleteAll();
					iniAll();
				}
				try {
					Thread.sleep(1000 * 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static ArrayList<CloudPlatform> getCloudPlatformList() {
		return cloudPlatformList;
	}

	public static void setCloudPlatformList(
			ArrayList<CloudPlatform> cloudPlatformList) {
		InitializeListener.cloudPlatformList = cloudPlatformList;
	}

	public static ArrayList<VMInstance> getVmInstanceList() {
		return VmInstanceList;
	}

	public static void setVmInstanceList(ArrayList<VMInstance> vmInstanceList) {
		VmInstanceList = vmInstanceList;
	}

	public static ArrayList<VMhardware> getVmHardwareList() {
		return vmHardwareList;
	}

	public static void setVmHardwareList(ArrayList<VMhardware> vmHardwareList) {
		InitializeListener.vmHardwareList = vmHardwareList;
	}

	public static ArrayList<VM48InforEntity> getVM48InforList() {
		return VM48InforList;
	}

	public static void setVM48InforList(ArrayList<VM48InforEntity> vM48InforList) {
		VM48InforList = vM48InforList;
	}

	public static ArrayList<CloudPlatformEntity> getCloudPlatformEntityList() {
		return cloudPlatformEntityList;
	}

	public static void setCloudPlatformEntityList(
			ArrayList<CloudPlatformEntity> cloudPlatformEntityList) {
		InitializeListener.cloudPlatformEntityList = cloudPlatformEntityList;
	}

	public static SumRankingOperaFactory getCloudPlatformRankingList() {
		return cloudPlatformRankingList;
	}

	public static void setCloudPlatformRankingList(
			SumRankingOperaFactory cloudPlatformRankingList) {
		InitializeListener.cloudPlatformRankingList = cloudPlatformRankingList;
	}
	
}
