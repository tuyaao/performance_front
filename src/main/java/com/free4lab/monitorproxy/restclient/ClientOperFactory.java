package com.free4lab.monitorproxy.restclient;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;

public class ClientOperFactory {
	private static Logger logger = Logger.getLogger(ClientOperFactory.class);

	// 其他的地方使用factory
	public static List<?> findByIdTime(Class bean, String id, Timestamp start,
			Timestamp end) {
		
		String[] split = bean.getName().split("\\.");
		switch( split[split.length-1] ){
		case "BeanCpu":
			List<BeanCpu> listCpu =  new CpuClient().findByIdTime(id, start, end);
			return listCpu;
		case "BeanMem":
			List<BeanMem> listMem =  new MemClient().findByIdTime(id, start, end);
			return listMem;
		case "BeanIozone":
			List<BeanIozone> listIozone =  new IozoneClient().findByIdTime(id, start, end);
			return listIozone;
		case "BeanTpcc":
			List<BeanTpcc> listTpcc =  new TpccClient().findByIdTime(id, start, end);
			return listTpcc;
		case "BeanPing":
			List<BeanPing> listPing =  new PingClient().findByIdTime(id, start, end);
			return listPing;
		default:
			logger.error("ClientOperFactory findByIdTime 类型不存在");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<BeanCpu> list = new CpuClient().findByIdTime("35", new Timestamp(Long.valueOf("1447203600000")),
				new Timestamp(Long.valueOf(Calendar.getInstance()
						.getTimeInMillis() + "")));
		for (BeanCpu it : list) {
			System.out.println("收到结果:" + it.toString());
		}
	}

}
