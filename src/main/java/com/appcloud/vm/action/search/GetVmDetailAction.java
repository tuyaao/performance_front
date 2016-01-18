package com.appcloud.vm.action.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.entity.VM48InforEntity;
import com.appcloud.vm.action.entity.VmDetailClass;
import com.appcloud.vm.common.InitializeListener;
import com.opensymphony.xwork2.ActionSupport;

public class GetVmDetailAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(GetVmDetailAction.class);
	
	private String configureSelecttxt;// 配置
	private String companyselecttxt;// 提供商
	private List<VmDetailClass> vmDetailList= new ArrayList<VmDetailClass>();// 云主机详情集合
	
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		System.out.println("搜索云主机详情");
		long start = System.currentTimeMillis();
		List<Integer> company = returnList(companyselecttxt);
		List<Integer> config = returnList(configureSelecttxt);
		
		ArrayList<VM48InforEntity> VM48InforList = InitializeListener.getVM48InforList();
		for (Integer com : company) {
			for ( VM48InforEntity vM48InforEntity : VM48InforList ){
				if( com.equals( vM48InforEntity.getPlatformId() ) ){
					VmDetailClass vmDetailClass = new VmDetailClass();
					vmDetailClass.setId(vM48InforEntity.getId());
					vmDetailClass.setComId(vM48InforEntity.getPlatformId());
					vmDetailClass.setComName(vM48InforEntity.getPlatformName());
					vmDetailClass.setCpu(vM48InforEntity.getCpu());
					vmDetailClass.setDisk(vM48InforEntity.getDisk());
					vmDetailClass.setMem(vM48InforEntity.getMemory());
					vmDetailClass.setOs(vM48InforEntity.getOs());
					vmDetailList.add(vmDetailClass);
				}
			}
		}
		
		
		
//			//此公司所有虚拟机
//			QueryObject<VmInstance> query = new QueryObject<VmInstance>();
//			query.addFilterBean(new FilterBean<VmInstance>("cloudPlatformId",
//					com, FilterBeanType.EQUAL));
//			List<VmInstance> VmInstanceListforcom = (List<VmInstance>) vmInstanceProxy
//					.searchAll(query);
//			//此公司的名字和id
//			QueryObject<CloudPlatform> queryStr = new QueryObject<CloudPlatform>();
//			queryStr.addFilterBean(new FilterBean<CloudPlatform>("id",
//					com, FilterBeanType.EQUAL));
//			List<CloudPlatform> cloud = (List<CloudPlatform>) cloudPlatformProxy
//					.searchAll(queryStr);
//			String comName = "";
//			Integer comId = 0;
//			if(cloud != null){
//				comName = cloud.get(0).getName();
//				comId = cloud.get(0).getId();
//			}
//			for (VmInstance vm : VmInstanceListforcom) {
//				QueryObject<VmHardware> queryVmHardware = new QueryObject<VmHardware>();
//				queryVmHardware.addFilterBean(new FilterBean<VmHardware>(
//						"uuid", vm.getId().toString(), FilterBeanType.EQUAL));
//				List<VmHardware> vmHardwareInstance = ((List<VmHardware>) vmHardwareProxy
//						.searchAll(queryVmHardware));
//				if(vmHardwareInstance.size() == 0){
//					System.out.println(vm.getId().toString()+"硬件查询结果为空");
//				}
//				if (vmHardwareInstance != null && vmHardwareInstance.size() != 0) {
//					VmHardware vmHardware = vmHardwareInstance.get(0);
//					for (Integer conf : config) {
//						if (vmHardware.getCpu().equals(conf)) {
//							VmDetailClass vmDetailClass = new VmDetailClass(
//									vm.getId(), comName, comId, vmHardware.getCpu(),
//									vmHardware.getMemory(),
//									vmHardware.getDisk(), vm.getOs());
//							vmDetailList.add(vmDetailClass);
//						}
//					}
//				}
//			}
		
		long end = System.currentTimeMillis();
		logger.error("查询总时间"+ Long.toString(end-start));
		return SUCCESS;
	}

	public static List<Integer> returnList(String param) {
		List<Integer> list = new ArrayList<Integer>();
		if (param != null) {
			String[] array = param.split(",");
			Integer id = 0;
			for (String e : array) {
				try {
					if (e != null && e != "") {
						id = Integer.parseInt(e);
						list.add(id);
					}
				} catch (Exception ex) {
				}
			}
		}
		return list;
	}

	public static void main(String args[]) throws Exception {
		GetVmDetailAction t = new GetVmDetailAction();

		t.setCompanyselecttxt("2,3");
		t.setConfigureSelecttxt("1");
		t.execute();
		System.out.println(t.getVmDetailList().get(0).getComName());
		System.out.println(t.getVmDetailList().get(1).getComName());

	}

	public String getConfigureSelecttxt() {
		return configureSelecttxt;
	}

	public void setConfigureSelecttxt(String configureSelecttxt) {
		this.configureSelecttxt = configureSelecttxt;
	}

	public String getCompanyselecttxt() {
		return companyselecttxt;
	}

	public void setCompanyselecttxt(String companyselecttxt) {
		this.companyselecttxt = companyselecttxt;
	}

	public List<VmDetailClass> getVmDetailList() {
		return vmDetailList;
	}

	public void setVmDetailList(List<VmDetailClass> vmDetailList) {
		this.vmDetailList = vmDetailList;
	}
	
}
