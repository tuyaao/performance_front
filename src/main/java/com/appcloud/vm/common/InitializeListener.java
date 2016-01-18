package com.appcloud.vm.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;

import com.appcloud.vm.action.dao.BdLogDao;
import com.appcloud.vm.action.dao.CloudPlatformDao;
import com.appcloud.vm.action.dao.VMHardwareDao;
import com.appcloud.vm.action.dao.VMInstanceDao;
import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.action.dbentity.VMInstance;
import com.appcloud.vm.action.dbentity.VMhardware;
import com.appcloud.vm.action.entity.CloudPlatformEntity;
import com.appcloud.vm.action.entity.CloudPlatformRanking;
import com.appcloud.vm.action.entity.VM48InforEntity;



//由于在开始进行了数据的获取，所以要监听数据库的改变。包括相应proxy的findall()和得到4核8G的主机所有相关数据
public class InitializeListener implements ServletContextListener{
	private static CloudPlatformDao cloudPlatformDao;
	private static VMInstanceDao vMInstanceDao;
	private static VMHardwareDao vMHardwareDao;
	private static ArrayList<CloudPlatform> cloudPlatformList;
	private static ArrayList<VMInstance> VmInstanceList;
	private static ArrayList<VMhardware> vmHardwareList;
	private static ArrayList<VM48InforEntity> VM48InforList = new ArrayList<VM48InforEntity>();
	private static ArrayList<CloudPlatformEntity> cloudPlatformEntityList = new ArrayList<CloudPlatformEntity>();
	private static ArrayList<CloudPlatformRanking> cloudPlatformRankingList = new ArrayList<CloudPlatformRanking>();
	private static BdLogDao dbLogDao = new BdLogDao();//这里只有一个线程使用，多线程访问不安全
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("进入initializeListener");	
		Session session = HibernateSessionFactory.getInstance().getSession();//初始化HibernateSessionFactory,并预先获取session
		session.close(); 
		System.out.println("初始化HibernateSessionFactory完毕");	
		//获取云平台，云主机和云主机配置的所有信息
		cloudPlatformDao = new CloudPlatformDao();
		vMInstanceDao = new VMInstanceDao();
		vMHardwareDao = new VMHardwareDao();
		new ThreadPool();
		System.out.println("初始化ThreadPool完毕");	
		iniAll();
		System.out.println("初始化各类数据完毕");	
//		ThreadPool.execute(new CheckTask());
	}
	
	private void iniAll(){
		try {
			cloudPlatformList =  (ArrayList<CloudPlatform>)cloudPlatformDao.findAll();
			//由于数据库的数据遗留，这里去掉四家公司

			System.out.println("总共有的公司个数："+cloudPlatformList.size());
			
			//由于数据库的数据遗留，这里去掉四家公司的主机
			VmInstanceList =  (ArrayList<VMInstance>)vMInstanceDao.findAll();
			Iterator<VMInstance> VmInstanceListIter = VmInstanceList.iterator();  
			while(VmInstanceListIter.hasNext()){  
				VMInstance vmInstance = VmInstanceListIter.next();  
			    if(vmInstance.getCloudPlatformId().equals(9)||vmInstance.getCloudPlatformId().equals(7)||vmInstance.getCloudPlatformId().equals(8)||vmInstance.getCloudPlatformId().equals(10)){  
			    	VmInstanceListIter.remove();  
			    }  
			}  
			
			System.out.println("总共有的虚拟机个数："+VmInstanceList.size());
			
			//由于数据库的数据遗留，这里去掉四家公司的主机的硬件成员
			vmHardwareList = ((ArrayList<VMhardware>) vMHardwareDao.findAll());
			Iterator<VMhardware> VmHardwareIter = vmHardwareList.iterator();  
			while(VmHardwareIter.hasNext()){  
				VMhardware vmHardware = VmHardwareIter.next();  
			    if(vmHardware.getUuid().equals("24")||vmHardware.getUuid().equals("25")||vmHardware.getUuid().equals("32")
			     ||vmHardware.getUuid().equals("20")||vmHardware.getUuid().equals("31")||vmHardware.getUuid().equals("19")
			     ||vmHardware.getUuid().equals("36")||vmHardware.getUuid().equals("21")||vmHardware.getUuid().equals("22")
			     ||vmHardware.getUuid().equals("26")||vmHardware.getUuid().equals("27")||vmHardware.getUuid().equals("28")){  
			    	System.out.println("："+vmHardwareList.size());
			    	VmHardwareIter.remove();  
			    }  
			}  
			System.out.println("总共有的虚拟机的配置个数："+vmHardwareList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			iniVm48List();
			iniCloudPlatformEntityList(cloudPlatformList);//注意初始化的依赖顺序
			iniCloudPlatformRankingList(VM48InforList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void iniVm48List(){
			for ( VMhardware vmHardware : vmHardwareList){
				if( vmHardware.getCpu().equals(4) && !(vmHardware.getUuid().equals("24")||vmHardware.getUuid().equals("25")||vmHardware.getUuid().equals("32")
					                                 ||vmHardware.getUuid().equals("20")||vmHardware.getUuid().equals("31")||vmHardware.getUuid().equals("19")
					                                 ||vmHardware.getUuid().equals("36")||vmHardware.getUuid().equals("21")||vmHardware.getUuid().equals("22")
					                                 ||vmHardware.getUuid().equals("26")||vmHardware.getUuid().equals("27")||vmHardware.getUuid().equals("28"))){
					VM48InforEntity vM48InforEntity = new VM48InforEntity();
					vM48InforEntity.setId(Integer.valueOf(vmHardware.getUuid()));
					vM48InforEntity.setCpu(vmHardware.getCpu());
					vM48InforEntity.setMemory(vmHardware.getMemory());
					vM48InforEntity.setDisk(vmHardware.getDisk());
					for ( VMInstance vmInstance : VmInstanceList){
						if( vmInstance.getId().equals(vM48InforEntity.getId()) ){
							Integer cloudPlatformId = vmInstance.getCloudPlatformId();
							vM48InforEntity.setOs(vmInstance.getOs());
							for ( CloudPlatform cloudPlatform : cloudPlatformList ){
								if ( cloudPlatform.getId().equals(cloudPlatformId) ){
									vM48InforEntity.setPlatformId(cloudPlatform.getId());
									vM48InforEntity.setPlatformName(cloudPlatform.getName());
								}
							}
						}
					}
					VM48InforList.add(vM48InforEntity);
				}
			}
			testGetIniVm48();
	}
	
	/***
	 * @param cloudPlatformList 所有厂商的bean
	 * @return 前端需要的关于厂商的所有数据
	 * @throws Exception 
	 */
	private void iniCloudPlatformEntityList( ArrayList<CloudPlatform> cloudPlatformList ) throws Exception{
		for( CloudPlatform cloudPlatform : cloudPlatformList ){
//			QueryObject<VmInstance> queryVmInstance = new QueryObject<VmInstance>();
//			queryVmInstance.addFilterBean(new FilterBean<VmInstance>("cloudPlatformId", cloudPlatformList.get(i).getId(), FilterBeanType.EQUAL));	
			CloudPlatformEntity cloudPlatformEntity = new CloudPlatformEntity();
			cloudPlatformEntity.setId(cloudPlatform.getId());
			cloudPlatformEntity.setName(cloudPlatform.getName());
			cloudPlatformEntity.setContainCompanyQuantity(0);//前端没有使用，所以默认返回0，使用了再查找
//			cloudPlatformEntity.setContainCompanyQuantity(((List<VmInstance>)vmInstanceProxy.searchAll(queryVmInstance)).size());
			cloudPlatformEntityList.add(cloudPlatformEntity);
		}
	}
	
	/***
	 * @param cloudPlatformList 所有厂商的bean
	 * @return SumGetchatAction 里面需要的前端各指标排名的类
	 * @throws Exception 
	 */
	private void iniCloudPlatformRankingList( ArrayList<VM48InforEntity> VM48InforList ) throws Exception{
		for( VM48InforEntity vM48InforEntity : VM48InforList ){
			CloudPlatformRanking cloudPlatformRanking = new CloudPlatformRanking( vM48InforEntity.getId(), vM48InforEntity.getPlatformName(), 0f, 0f, 0f, 0f, 0f, null, 0f, 0f, 0f, 0f, 0f);
			cloudPlatformRankingList.add(cloudPlatformRanking);
		}
	}
	
	private void testGetIniVm48(){
		for ( VM48InforEntity vM48InforEntity : VM48InforList){
			System.out.println("云平台ID:"+vM48InforEntity.getPlatformId()+"__云平台名字:"+vM48InforEntity.getPlatformName()+"__云主机ID:"+vM48InforEntity.getId()+"__CPU:"+vM48InforEntity.getCpu()+"__MEM:"+vM48InforEntity.getMemory()+"__DISK:"+vM48InforEntity.getDisk());
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	
	//在程序开始的时候就运行，用来判断预获取的数据是否改变，如果改变了就重新获取
	class CheckTask implements Runnable, Serializable
	{

	    CheckTask(){
	    }

	    public void run()
	    {
	        while(true){
	        	if (!dbLogDao.countAll().equals(0)){
	        		dbLogDao.deleteAll();
	        		iniAll();
	        	}
	        	try {
					Thread.sleep(1000*60);
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

	public static ArrayList<CloudPlatformRanking> getCloudPlatformRankingList() {
		return cloudPlatformRankingList;
	}

	public static void setCloudPlatformRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformRankingList) {
		InitializeListener.cloudPlatformRankingList = cloudPlatformRankingList;
	}
	
	
	
	
	

}
