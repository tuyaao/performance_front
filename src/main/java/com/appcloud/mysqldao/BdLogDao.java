package com.appcloud.mysqldao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.common.HibernateSessionFactory;

public class BdLogDao {

	private Logger logger = Logger.getLogger(BdLogDao.class);

	public BdLogDao() {

	}

	/**@return获取所有的dblog实例的个数,包括所有数据*/
	public Integer countAll(){
		Integer count = 0;
		try{
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
		String sql = "select count(id) from Dblog"; 
		Query query=session.createQuery(sql);
		count = Integer.valueOf(query.uniqueResult().toString());
		session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace(); 
		}
		return count;
	}
	
	/**删光数据库里面的所有数据*/
	public void deleteAll(){
		try{
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
		String sql = "delete * from dblog"; 
		Query query=session.createQuery(sql);
		query.executeUpdate();
		session.getTransaction().commit();
		} catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	

}
