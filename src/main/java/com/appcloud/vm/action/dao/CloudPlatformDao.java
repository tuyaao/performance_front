package com.appcloud.vm.action.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.utils.HibernateSessionFactory;

public class CloudPlatformDao {

	private Logger logger = Logger.getLogger(CloudPlatformDao.class);

	public CloudPlatformDao() {

	}

	/** @return获取所有的CloudPlatform实例 */
	public List<CloudPlatform> findAll() {
		List<CloudPlatform> cloudPlatformList = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "from CloudPlatform";
		Query query = session.createQuery(sql);
		cloudPlatformList = query.list();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return cloudPlatformList;
	}

	

}
