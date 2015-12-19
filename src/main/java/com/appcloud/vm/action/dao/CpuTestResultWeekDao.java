package com.appcloud.vm.action.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.action.dbentity.CpuTestResultWeek;
import com.appcloud.vm.utils.HibernateSessionFactory;

public class CpuTestResultWeekDao{
	
	private Logger logger = Logger.getLogger(CpuTestResultWeekDao.class);
	
	public CpuTestResultWeekDao(){
		
	}
	
	/** @return 获取所有的CpuTestResultWeek实例一个，按测试时间降序 */
	public CpuTestResultWeek findInstanceById(Integer id) {
		List<CpuTestResultWeek> cpuTestResultWeekList = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select model from CpuTestResultWeek model where model.vmId"
            + "= :id" + " order by testTime desc ";
		Query query = session.createQuery(sql);
		query.setInteger("id", id);
		query.setMaxResults(1);
		cpuTestResultWeekList = query.list();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		System.out.println("CPUDao查看"+cpuTestResultWeekList.get(0).getVmId()+"____"+cpuTestResultWeekList.get(0).getTestTime());
		if (cpuTestResultWeekList != null & cpuTestResultWeekList.size() >= 1 ){
			return cpuTestResultWeekList.get(0);
		} else {
			return null;
		}
	}
	
}
