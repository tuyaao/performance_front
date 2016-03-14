package com.appcloud.mysqldao;

import java.util.List;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.appcloud.vm.action.dbentity.VMInstance;
import com.appcloud.vm.action.dbentity.VMhardware;
import com.appcloud.vm.common.HibernateSessionFactory;

public class VMInstanceDao extends AbstractDao<VMInstance> {
	
	@Override
	protected Class<VMInstance> getEntityClass() {
		return VMInstance.class;
	}
	
}
