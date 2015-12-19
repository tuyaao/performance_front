package com.appcloud.vm.action.dao;

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
import com.appcloud.vm.utils.HibernateSessionFactory;

public class VMInstanceDao {
	
	private Logger logger = Logger.getLogger(VMInstanceDao.class);
	
	public VMInstanceDao(){
		
	}
	
	/**@return获取所有的VMInstance实例*/
	public List<VMInstance> findAll() {
		List<VMInstance> vMInstanceList = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "from VMInstance"; 
		Query query = session.createQuery(sql); 
		vMInstanceList = query.list();
		try { 
			session.getTransaction().commit();
		}catch (HibernateException he){ 
		he.printStackTrace(); 
		transaction.rollback(); 
		}finally{ 
			session.close(); 
		} 
		return vMInstanceList;
    }
	
	/**@return根据需求返回VMInstance的个数*/
	public Long countByProperty(String property, Object value){
		Long count = 0L;
		Configuration configuration = new AnnotationConfiguration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory(); //根据config建立sessionFactory 
		Session session = sessionFactory.openSession(); //factory用于建立session，开启Session，相当于开启JDBC的Connection
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select count(model)" +
            		" from " + "VMInstance" + " model" +
            		" where model." + property + "= :propertyValue";
        	Query query = session.createQuery(sql); 
            query.setParameter("propertyValue", value);
            count = (Long)query.uniqueResult();
            try { 
    			session.getTransaction().commit();
    		}catch (HibernateException he){ 
    		he.printStackTrace(); 
    		transaction.rollback(); 
    		}finally{ 
    			session.close(); 
    			sessionFactory.close(); 
    		} 
            return count.longValue();
    }
	
	/**@return根据需求返回VMInstance的实例*/
    @SuppressWarnings("unchecked")
    public List<VMInstance> findByProperty(String propertyName,final Object value) {
    	
    	List<VMInstance> list = null;
		Configuration configuration = new AnnotationConfiguration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory(); //根据config建立sessionFactory 
		Session session = sessionFactory.openSession(); //factory用于建立session，开启Session，相当于开启JDBC的Connection
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select model from VMInstance model where model."
                    + propertyName + "= :propertyValue";
        	Query query = session.createQuery(sql); 
            query.setParameter("propertyValue", value);
            list = query.list();
            try { 
    			session.getTransaction().commit();
    		}catch (HibernateException he){ 
    		he.printStackTrace(); 
    		transaction.rollback(); 
    		}finally{ 
    			session.close(); 
    			sessionFactory.close(); 
    		} 
            return list;  	
    }
	

//		logger.error("do here 3");
//		List<VMInstance> vMInstances = query.list(); //序列化 
//		logger.error("do here 4");
//		Iterator it = vMInstances.iterator(); //迭代 
//		logger.error("do here 5");
//		while (it.hasNext()){ 
//			vMInstance = (VMInstance) it.next(); 
//			logger.error(vMInstance.getName());
//		} 

//	@Override
//	public Class getEntityClass() {
//		// TODO Auto-generated method stub
//		return VMInstance.class;
//	}
//
//	@Override
//	public String getPUName() {
//		// TODO Auto-generated method stub
//		return "VMC_PU";
//	}
//
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
