package com.appcloud.mysqldao;

import java.util.List;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.appcloud.vm.common.HibernateSessionFactory;

public abstract class AbstractDao<T> {
	private Logger logger = Logger.getLogger(getEntityClass());

	// T只是一个约束，想要知道具体是什么类型，还是要传参
	protected abstract Class<T> getEntityClass();

	private String getClassName() {
		return getEntityClass().getName();
	}

	/**
	 * @return 返回表中所有值
	 */
	@SuppressWarnings("unchecked") // protected不仅是只有自己的子类可以继承，也保证了只有和子类在一个包下的factory可以使用
	protected List<T> getAll() {
		List<T> List = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "from " + getClassName();
		Query query = session.createQuery(sql);
		List = (List<T>) query.list();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return List;
	}

	/**
	 * @return 根据需求返回个数
	 */
	protected Long countByProperty(String property, Object value) {
		Long count = 0L;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select count(model)" + " from " + getClassName()
				+ " model" + " where model." + property + "= :propertyValue";
		Query query = session.createQuery(sql);
		query.setParameter("propertyValue", value);
		count = (Long) query.uniqueResult();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return count.longValue();
	}

	/**
	 * @parameter 单个属性和属性值
	 * @return 根据需求返回实例列表
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByProperty(String propertyName, final Object value) {
		List<T> list = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select model from " + getClassName()
				+ " model where model." + propertyName + "= :propertyValue";
		Query query = session.createQuery(sql);
		query.setParameter("propertyValue", value);
		list = (List<T>) query.list();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * @parameter 两个属性和属性值
	 * @return 根据需求返回实例列表
	 */
	//我不知道为什么根据具体的set值不行了，这个灵活的hql语句要会写
	//总是预存sql语句！！！！怎么样不预存  main可以用加载的方式来做
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propertyName1, final Object value1,
			String propertyName2, final Object value2) {

		List<T> list = null;
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction(); // 创建事务的对象ts
//		System.out.println("getClassName():"+getClassName());
		String[] a = getClassName().split("\\.");
		int size = a.length;
//		System.out.println("a[size - 1]："+a[size - 1]);
		String sql = "from " + a[size - 1]
				+ " model where model.uuid"
				+ "= :propertyValue1 and model.sumTime"
				+ "= :propertyValue2";
//		System.out.println("sql:"+sql);
		Query query = session.createQuery(sql);
		query.setParameter("propertyValue1", value1);
		query.setParameter("propertyValue2", value2);
		list = (List<T>) query.list();
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return list;
	}
	
	/**
	 * @parameter 条目
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public void save(T object) {
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction(); // 创建事务的对象ts
		session.save(object);
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
	}
	
	/**
	 * @parameter 条目
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public void deleteById(T object) {
		Session session = HibernateSessionFactory.getInstance().getSession();
		Transaction transaction = session.beginTransaction(); // 创建事务的对象ts
		session.delete(object);
		try {
			session.getTransaction().commit();
		} catch (HibernateException he) {
			he.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
	}

}
