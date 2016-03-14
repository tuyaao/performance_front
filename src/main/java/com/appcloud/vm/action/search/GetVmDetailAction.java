package com.appcloud.vm.action.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.entity.VM48InforEntity;
import com.appcloud.vm.common.InitializeListener;
import com.opensymphony.xwork2.ActionSupport;

public class GetVmDetailAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(GetVmDetailAction.class);
	
	private String configureSelecttxt;// 配置
	private String companyselecttxt;// 提供商
	private List<VM48InforEntity> vmDetailList= new ArrayList<VM48InforEntity>();// 云主机详情集合
	
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
					VM48InforEntity vmDetailClass = new VM48InforEntity();
					vmDetailClass.setId(vM48InforEntity.getId());
					vmDetailClass.setPlatformId(vM48InforEntity.getPlatformId());
					vmDetailClass.setPlatformName(vM48InforEntity.getPlatformName());
					vmDetailClass.setCpu(vM48InforEntity.getCpu());
					vmDetailClass.setDisk(vM48InforEntity.getDisk());
					vmDetailClass.setMemory(vM48InforEntity.getMemory());
					vmDetailClass.setOs(vM48InforEntity.getOs());
					vmDetailList.add(vmDetailClass);
				}
			}
		}
		
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
		System.out.println(t.getVmDetailList().get(0).getPlatformName());
		System.out.println(t.getVmDetailList().get(1).getPlatformName());

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

	public List<VM48InforEntity> getVmDetailList() {
		return vmDetailList;
	}

	public void setVmDetailList(List<VM48InforEntity> vmDetailList) {
		this.vmDetailList = vmDetailList;
	}
	
}
