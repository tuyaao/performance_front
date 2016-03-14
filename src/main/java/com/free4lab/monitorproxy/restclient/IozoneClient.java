package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class IozoneClient extends AbstractClient<BeanIozone>{
	
	private static final String BY_IDTIME_PATH = "dataIo/Io/";
	
	@Override
	protected Class<?> getType() {
		return BeanIozone.class;
	}

    //这个本来是用来返回一列对象的，但我们第一用String传值，第二都是返回一个对象，所以用不到
	@Override
	protected GenericType<List<BeanIozone>> getGenericType() {
		GenericType<List<BeanIozone>> type = new GenericType<List<BeanIozone>>() {};
		return type;
	}
	
	@Override
	protected String getByIdTimePath() {
		return BY_IDTIME_PATH;
	}

}
