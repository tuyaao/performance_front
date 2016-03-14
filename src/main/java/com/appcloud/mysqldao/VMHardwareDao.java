package com.appcloud.mysqldao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.appcloud.vm.action.dbentity.VMhardware;
import com.appcloud.vm.common.HibernateSessionFactory;

public class VMHardwareDao extends AbstractDao<VMhardware> {
	
	@Override
	protected Class<VMhardware> getEntityClass() {
		return VMhardware.class;
	}
	
//	@Override
//	public IEntityManagerHelper getEntityManagerHelper() {
//		// TODO Auto-generated method stub
//		return new NoCacheEntityManagerHelper();
//	}
//
//	@Override
//	public List<VMInstance> findAll() {
//		// TODO Auto-generated method stub
//		return super.findAll();
//	}
//	
	

	


	
	

}
