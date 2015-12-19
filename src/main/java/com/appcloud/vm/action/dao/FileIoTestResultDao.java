package com.appcloud.vm.action.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.action.dbentity.FileIoTestResult;
import com.appcloud.vm.utils.HibernateSessionFactory;

public class FileIoTestResultDao {
	
	private Logger logger = Logger.getLogger(FileIoTestResultDao.class);
	
	public FileIoTestResultDao(){
		
	}
	
	/**@return获取FileIoTestResult实例*/
	public List<FileIoTestResult> findAllByBetween(Integer id, Timestamp timeMin, Timestamp timeMax) {

    	List<FileIoTestResult> list = null;
    	Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select model from FileIoTestResult model where model.vmId = :id and model.testTime between :timeMin and :timeMax ";
        	Query query = session.createQuery(sql);
        	query.setInteger("id", id);
        	query.setTimestamp("timeMin", timeMin);
        	query.setTimestamp("timeMax", timeMax);
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
	
	/**@return获取FileIoTestResult的时间和希望的值*/
	public ArrayList findAllExcatByBetween(Integer id, Timestamp timeMin, Timestamp timeMax) {

//		 SELECT test_time, test_mode, transfer_speed FROM file_io_test_result  WHERE vm_id = 30 AND  test_time  BETWEEN "2015-08-27 23:00:00" AND "2015-09-02 23:00:00";
//		 CREATE INDEX vmid_testtime ON file_io_test_result (vm_id,test_time);
//    	 order by 的索引优化
//		 http://www.cnblogs.com/cy163/archive/2008/10/27/1320798.html
//		 要想根据一个索引排序一个索引和数据，使用myisamchk --sort-index --sort-records=1（如果你想要在索引1上排序）。如果只有一个索引，想要根据该索引的次序读取所有的记录，这是使查询更快的一个好方法。但是请注意，第一次对一个大表按照这种方法排序时将花很长时间！
//         手册orderby优化
//         使用ANALYZE TABLE tbl_name为扫描的表更新关键字分布
//         7.2.19. 其它优化技巧
		    Session session = HibernateSessionFactory.getInstance().getSession();
		    Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select model.testTime, model.testMode, model.transferSpeed from FileIoTestResult model where model.vmId = :id and model.testTime >= :timeMin and model.testTime <= :timeMax ";
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
