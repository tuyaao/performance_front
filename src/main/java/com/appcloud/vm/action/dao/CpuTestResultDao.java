package com.appcloud.vm.action.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.action.dbentity.CpuTestResult;
import com.appcloud.vm.utils.HibernateSessionFactory;

public class CpuTestResultDao{
	
	private Logger logger = Logger.getLogger(CpuTestResultDao.class);
	
	public CpuTestResultDao(){
		
	}
	
	/**@return获取CpuTestResult实例*/
	public List<CpuTestResult> findAllByBetween(Integer id, Timestamp timeMin, Timestamp timeMax) {

    	List<CpuTestResult> list = null;
    	Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select model from CpuTestResult model where model.vmId"
            + "= :propertyValue" + " and model.testTime >= :timeMin AND model.testTime <=:timeMax ";
        	Query query = session.createQuery(sql);
        	query.setParameter("time1", timeMin);
        	query.setParameter("time2", timeMax);
            list = query.list();
            try { 
    			session.getTransaction().commit();
    		}catch (HibernateException he){ 
    		he.printStackTrace(); 
    		transaction.rollback(); 
    		}finally{ 
    			session.close(); 
    		} 
            return list;  	
}
	
	/**@return获取CpuTestResult的时间和希望的值*/
	public ArrayList findAllExcatByBetween(Integer id, Timestamp timeMin, Timestamp timeMax) {
		    Session session = HibernateSessionFactory.getInstance().getSession();
		    Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select model.testTime, model.totalTime from CpuTestResult model where model.vmId = :id and model.testTime >= :timeMin and model.testTime <= :timeMax ";
        	Query query = session.createQuery(sql);
        	query.setInteger("id", id);
        	query.setTimestamp("timeMin", timeMin);
        	query.setTimestamp("timeMax", timeMax);
        	ArrayList list = (ArrayList)query.list();
            try { 
    			session.getTransaction().commit();
    		}catch (HibernateException he){ 
    		he.printStackTrace(); 
    		transaction.rollback(); 
    		}finally{ 
    			session.close(); 
    		} 
            return list;  	
}


	
	

}
