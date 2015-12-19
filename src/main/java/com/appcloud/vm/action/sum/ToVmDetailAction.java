package com.appcloud.vm.action.sum;

import java.util.List;

import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.action.entity.CloudPlatformEntity;
import com.appcloud.vm.utils.InitializeListener;
import com.opensymphony.xwork2.ActionSupport;

public class ToVmDetailAction extends ActionSupport{

	
	private static final long serialVersionUID = 1L;
	private int id;
	private List<CloudPlatformEntity> cloudPlatformEntityList = InitializeListener.getCloudPlatformEntityList();// 云平台的id,名字,含有几台虚拟机的实体类集合
	private List<CloudPlatform> cloudPlatformList = InitializeListener.getCloudPlatformList();//云平台集合
	
	public String execute() throws Exception{	
		System.out.println("进入性能查询之前的Action");
		
//		for(int i = 0;i < cloudPlatformList.size(); i++){
//			QueryObject<VmInstance> queryVmInstance = new QueryObject<VmInstance>();
//			queryVmInstance.addFilterBean(new FilterBean<VmInstance>("cloudPlatformId", cloudPlatformList.get(i).getId(), FilterBeanType.EQUAL));	
//			CloudPlatformEntity cloudPlatformEntity = new CloudPlatformEntity();
//			cloudPlatformEntity.setId(cloudPlatformList.get(i).getId());
//			cloudPlatformEntity.setName(cloudPlatformList.get(i).getName());
//			cloudPlatformEntity.setContainCompanyQuantity(((List<VmInstance>)vmInstanceProxy.searchAll(queryVmInstance)).size());
//			cloudPlatformEntityList.add(cloudPlatformEntity);
//		}
		
		return SUCCESS;
	}


	public List<CloudPlatformEntity> getCloudPlatformEntityList() {
		return cloudPlatformEntityList;
	}


	public void setCloudPlatformEntityList(
			List<CloudPlatformEntity> cloudPlatformEntityList) {
		this.cloudPlatformEntityList = cloudPlatformEntityList;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
}

	
	