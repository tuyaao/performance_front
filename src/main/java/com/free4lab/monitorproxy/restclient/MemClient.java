package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class MemClient extends AbstractClient<BeanMem>{
	
	private static final String BY_IDTIME_PATH = "dataMem/Mem/";
	
	@Override
	protected Class<?> getType() {
		return BeanMem.class;
	}

    //这个本来是用来返回一列对象的，但我们第一用String传值，第二都是返回一个对象，所以用不到
	@Override
	protected GenericType<List<BeanMem>> getGenericType() {
		GenericType<List<BeanMem>> type = new GenericType<List<BeanMem>>() {};
		return type;
	}
	
	@Override
	protected String getByIdTimePath() {
		return BY_IDTIME_PATH;
	}
	
}
