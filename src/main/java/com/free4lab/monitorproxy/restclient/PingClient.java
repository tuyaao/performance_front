package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//名字表示这是对host的操作，String表示返回值为String
public class PingClient extends AbstractClient<BeanPing>{
	private Logger logger = LoggerFactory.getLogger(PingClient.class);
	private static final String BEGIN_TIME = "btime";
	private static final String END_TIME = "etime";
	private static final String PATH = "dataPing/Ping/";
	@Override
	protected Class<?> getType() {
		return BeanPing.class;
	}

//这个本来是用来返回一列对象的，但我们第一用String传值，第二都是返回一个对象，所以用不到
	@Override
	protected GenericType<List<BeanPing>> getGenericType() {
		GenericType<List<BeanPing>> type = new GenericType<List<BeanPing>>() {};
		return type;
	}
	
	//通过主机id 和时间区间，来获得cpu
	public List<BeanPing> findPingByIdTime(String id, Timestamp start, Timestamp end ) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("id", id);
		params.add(BEGIN_TIME, start.getTime()+"");
		params.add(END_TIME, end.getTime()+"");
		
		return (List<BeanPing>)getList(PATH, params);
	}
	
	public static void main(String[] args) {
		PingClient hostClient = new PingClient();
		List<BeanPing> list = hostClient.findPingByIdTime("35", new Timestamp(Long.valueOf("1447203600000")), new Timestamp(Long.valueOf(Calendar.getInstance().getTimeInMillis()+"")));
		for (BeanPing it : list){
			System.out.println( "收到结果:"+it.toString() );	
		}
	}
	
}
