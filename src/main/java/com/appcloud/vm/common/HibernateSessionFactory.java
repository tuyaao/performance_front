package com.appcloud.vm.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
	  private  final Configuration configuration;
      private  final SessionFactory sessionFactory;

    //SessionFactory是工厂模式
    //工厂模式  也就是它每次会生成一个新的副本对象 而这些副本对象之间没有关系的 也就保证了安全性
    private HibernateSessionFactory() {
    	//configuration = new Configuration().configure();
    	System.out.println("开始加载configuration");	
    	configuration = new AnnotationConfiguration().configure();
    	System.out.println("加载configuration完毕");	
        sessionFactory = configuration.buildSessionFactory();
        System.out.println("buildSessionFactory完毕");	
    }

    //必须通过getInstance()获取HibernateSessionFactory.getSession
    //关于session  是线程不安全的，使用ThreadLocal
    //session 在缓存在操作数据的时候应该具有隔离性. 也就是尽可能的将你要操做的一组数据放到同一个 session 缓存中 ,
    //这样不至于在清理缓存的时候出现数据更新紊乱的情况.session不是线程安全的 , 在设计时候应该尽可能的避免多个线程共享一个session. 
    //每数据库事务对应一个session    getCurrentSession创建的session会和绑定到当前线程,而openSession不会。
    //getCurrentSession创建的线程会在事务回滚或事物提交后自动关闭,而openSession必须手动关闭
    //考虑一个线程如何可以有多个
    //新版本的Hibernate在处理Session的时候已经内置了延迟加载机制，只有在真正发生数据库操作的时候，才会从数据库连接池获取数据库连接，我们不必过于担心Session的共享会导致整个线程生命周期内数据库连接被持续占用。
    public Session getSession() {
        return sessionFactory.openSession();
    }

    //Java 单例  多线程同步获取 并保证单例 和JavaIDE 都可用  http://devbean.blog.51cto.com/448512/203501/
    //内部外部，包括（静态内部）类只有在调用的时候进行加载，但是加载的时候是原子性的; 
    //静态成员变量或方法是在类加载的时候执行的。普通的也是，只有常量才在编译的时候初始化
    //常量（static final 修饰的）在编译时会存入调用类的常量池，所以已经存在，不会引起类的初始化，其初始化方法同普通静态变量
    //声明类型为类A的变量,但不初始化,那么不是主动请求类A,也不会初始化类A
    private static class SingletonHolder {
        private final static HibernateSessionFactory INSTANCE = new HibernateSessionFactory();
    }
    

    public static HibernateSessionFactory getInstance() {
    	 return SingletonHolder.INSTANCE;
    }
    
    public static void closeSession(Session session){  
        if (session != null){  
            if (session.isOpen()){  
                session.close();  
            }  
        }  
    }  
    
    
}
