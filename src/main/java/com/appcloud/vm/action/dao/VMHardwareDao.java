package com.appcloud.vm.action.dao;

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

public class VMHardwareDao {
	
	private Logger logger = Logger.getLogger(VMHardwareDao.class);
	
	public VMHardwareDao(){
		
	}
	
	/**@return获取所有的VMHardware实例*/
	public List<VMhardware> findAll() {
		List<VMhardware> vMhardwareList = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
//		VMInstance vMInstance = (VMInstance)session.get(VMInstance.class, 12);
//		System.out.println("姓名为："+vMInstance.getIp());
		String sql = "from VMhardware"; 
		Query query = session.createQuery(sql); 
		vMhardwareList = query.list();
		try { 
			session.getTransaction().commit();
		}catch (HibernateException he){ 
		he.printStackTrace(); 
		transaction.rollback(); 
		}finally{ 
			session.close(); 
		} 
		return vMhardwareList;
    }
	
	/**@return根据需求返回VMHardware的个数*/
	public Long countByProperty(String property, Object value){
		Long count = 0L;
		Configuration configuration = new AnnotationConfiguration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory(); //根据config建立sessionFactory 
		Session session = sessionFactory.openSession(); //factory用于建立session，开启Session，相当于开启JDBC的Connection
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select count(model)" +
            		" from " + "VMHardware" + " model" +
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
	
	/**@单个属性和属性值
	 * @return根据需求返回VMHardware的实例*/
    @SuppressWarnings("unchecked")
    public List<VMhardware> findByProperty(String propertyName,final Object value) {	
    	List<VMhardware> list = null;
		Configuration configuration = new AnnotationConfiguration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory(); //根据config建立sessionFactory 
		Session session = sessionFactory.openSession(); //factory用于建立session，开启Session，相当于开启JDBC的Connection
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select model from VMhardware model where model."
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
	
    
    /**@两个属性和属性值
	 * @return根据需求返回VMHardware的实例*/
    @SuppressWarnings("unchecked")
    public List<VMhardware> findByProperty(String propertyName1,final Object value1,String propertyName2,
            final Object value2) {
    	
    	List<VMhardware> list = null;
		Configuration configuration = new AnnotationConfiguration().configure();
		SessionFactory sessionFactory = configuration.buildSessionFactory(); //根据config建立sessionFactory 
		Session session = sessionFactory.openSession(); //factory用于建立session，开启Session，相当于开启JDBC的Connection
		Transaction transaction = session.beginTransaction(); //创建事务的对象ts 
        	String sql  = "select model from VMHardware model where model."
                    + propertyName1 + "= :propertyValue1 and model."+ propertyName2 + "= :propertyValue2";
        	Query query = session.createQuery(sql); 
            query.setParameter("propertyName1", value1);
            query.setParameter("propertyName2", value2);
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
