package com.appcloud.mysqldao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.common.HibernateSessionFactory;

public class CloudPlatformDao extends AbstractDao<CloudPlatform> {
	
	@Override
	protected Class<CloudPlatform> getEntityClass() {
		return CloudPlatform.class;
	}

}
