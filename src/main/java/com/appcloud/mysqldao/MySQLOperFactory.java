package com.appcloud.mysqldao;

import java.util.List;

import org.apache.log4j.Logger;

public class MySQLOperFactory {
	private static Logger logger = Logger.getLogger(MySQLOperFactory.class);
	
    //由于要使用静态的对象，getAll()需要是静态的，而父类函数使用了泛型，还有abstract函数获取子类类名，所以没办法做成静态的函数，静态函数也没有办法重写，这个怎么办...
	public static  List<?> getAll( Class bean ){
		String[] split = bean.getName().split("\\.");
		switch( split[split.length-1] ){
		case "CloudPlatform":
			return new CloudPlatformDao().getAll();
		case "VMhardware":
			return new VMHardwareDao().getAll();
		case "VMInstance":
			return new VMInstanceDao().getAll();
		default:
			logger.error("bean.getName()" + bean.getName() );
			logger.error("MySQLOperation getAll 类型不存在" );
			return null;
			}
	}
	
	
}
