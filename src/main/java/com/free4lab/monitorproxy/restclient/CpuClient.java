package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//名字表示这是对host的操作，String表示返回值为String
public class CpuClient extends AbstractClient<BeanCpu>{
	private Logger logger = LoggerFactory.getLogger(CpuClient.class);
	private static final String PATH = "dataCpu/Cpu/";
	@Override
	protected Class<?> getType() {
		return BeanCpu.class;
	}

//这个本来是用来返回一列对象的，但我们第一用String传值，第二都是返回一个对象，所以用不到
	@Override
	protected GenericType<List<BeanCpu>> getGenericType() {
		GenericType<List<BeanCpu>> type = new GenericType<List<BeanCpu>>() {};
		return type;
	}
	
	//通过主机id 和时间区间，来获得cpu
	public List<BeanCpu> findCpuByIdTime(String id, Timestamp start, Timestamp end ) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("id", id);
		params.add(BEGIN_TIME, start.getTime()+"");
		params.add(END_TIME, end.getTime()+"");
		
		return (List<BeanCpu>)getList(PATH, params);
	}
	
	public static void main(String[] args) {
		CpuClient hostClient = new CpuClient();
		List<BeanCpu> list = hostClient.findCpuByIdTime("35", new Timestamp(Long.valueOf("1450080000000")), new Timestamp(Long.valueOf("1450512000000")));
		for (BeanCpu it : list){
			System.out.println( "收到结果:"+it.toString() );	
		}
	}
	
}
