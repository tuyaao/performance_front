package com.appcloud.mysqldao;

import com.appcloud.vm.action.dbentity.CloudPlatform;

public class CloudPlatformDao extends AbstractDao<CloudPlatform> {
	
	@Override
	protected Class<CloudPlatform> getEntityClass() {
		return CloudPlatform.class;
	}

}
