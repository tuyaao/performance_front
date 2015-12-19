package com.appcloud.vm.action.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.action.dbentity.FileIoTestResultWeek;
import com.appcloud.vm.utils.HibernateSessionFactory;

public class FileIoTestResultWeekDao {
	
	private Logger logger = Logger.getLogger(FileIoTestResultWeekDao.class);
	
	public FileIoTestResultWeekDao(){
		
	}
	
	/** @return 获取所有的FileIoTestResultWeek实例一个，按测试时间降序 */
	public List<FileIoTestResultWeek> findInstanceById(Integer id) {
		List<FileIoTestResultWeek> fileIoTestResultWeekList = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select model from FileIoTestResultWeek model where model.vmId"
				+ "= :id" + " order by testTime desc ";
		Query query = session.createQuery(sql);
		query.setInteger("id", id);
		query.setMaxResults(4);
		fileIoTestResultWeekList = query.list();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		System.out.println("fileDao查看"+fileIoTestResultWeekList.get(0).getVmId()+"____"+fileIoTestResultWeekList.get(0).getTestTime());
	    return fileIoTestResultWeekList;
	}
	
}
