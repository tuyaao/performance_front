package com.appcloud.vm.action.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.action.dbentity.OltpTestResultWeek;
import com.appcloud.vm.utils.HibernateSessionFactory;

public class OltpTestResultWeekDao {
	
	private Logger logger = Logger.getLogger(OltpTestResultWeekDao.class);
	
	public OltpTestResultWeekDao(){
		
	}
	
	/** @return 获取所有的OltpTestResultWeek实例一个，按测试时间降序 */
	public OltpTestResultWeek findInstanceById(Integer id) {
		List<OltpTestResultWeek> OltpTestResultWeekList = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select model from OltpTestResultWeek model where model.vmId"
				+ "= :id" + " order by testTime desc ";
		Query query = session.createQuery(sql);
		query.setInteger("id", id);
		query.setMaxResults(1);
		OltpTestResultWeekList = query.list();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		System.out.println("fileDao查看"+OltpTestResultWeekList.get(0).getVmId()+"____"+OltpTestResultWeekList.get(0).getTestTime());
		if (OltpTestResultWeekList != null & OltpTestResultWeekList.size() >= 1 ){
			return OltpTestResultWeekList.get(0);
		} else {
			return null;
		}
	}

}
