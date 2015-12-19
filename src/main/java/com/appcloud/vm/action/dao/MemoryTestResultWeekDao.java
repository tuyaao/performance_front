package com.appcloud.vm.action.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.action.dbentity.MemoryTestResultWeek;
import com.appcloud.vm.utils.HibernateSessionFactory;

public class MemoryTestResultWeekDao {

	private Logger logger = Logger.getLogger(MemoryTestResultWeekDao.class);

	public MemoryTestResultWeekDao() {

	}

	/** @return 获取所有的MemoryTestResultWeek实例一个，按测试时间降序 */
	public MemoryTestResultWeek findInstanceById(Integer id) {
		System.out.println("memDao查看id" + id);
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select * from memory_test_result_week where vm_id = "+ id +" order by test_time desc limit 0,1"; 
		SQLQuery query = session.createSQLQuery(sql).addEntity(MemoryTestResultWeek.class);
		MemoryTestResultWeek memoryTestResultWeek = (MemoryTestResultWeek)query.uniqueResult();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			System.out.println(he.getStackTrace());
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
//		System.out.println("memDao查看"
//				+ memoryTestResultWeekList.get(0).getVmId() + "____"
//				+ memoryTestResultWeekList.get(0).getTestTime());
		return memoryTestResultWeek;
	}

}
