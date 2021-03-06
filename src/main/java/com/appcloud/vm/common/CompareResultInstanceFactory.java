package com.appcloud.vm.common;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.entity.CompareResultAbstractEntity;
import com.appcloud.vm.action.entity.CompareResultEntity;
import com.appcloud.vm.utils.StringUtil;
import com.appcloud.vm.utils.TimeIntervalUtil;
import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;
import com.free4lab.monitorproxy.restclient.ClientOperFactory;

/**
 * @param 需要查询的虚拟机ID
 *            ，公司ID，起始和结束时间
 * @result 封装好的，已经插入空值的CompareResultInstance，相当于一条曲线
 * */
public class CompareResultInstanceFactory {
	
	private CompareResultEntity compareResultEntity;
	private StringUtil stringUtil = new StringUtil();
	private Logger logger = Logger
			.getLogger(CompareResultInstanceFactory.class);
	
	public CompareResultInstanceFactory(){
	}
	
	public CompareResultInstanceFactory(CompareResultEntity compareResultEntity){
		this.compareResultEntity = compareResultEntity;
	}

	public CompareResultInstance getCompareResultCpu(Integer id,
			Integer cloudPlatformId, Timestamp tsSelectTimeStart,
			Timestamp tsSelectTimeEnd) throws Exception {
		CompareResultInstance compareResultInstance = new CompareResultInstance();
		compareResultInstance.setCompanyId(cloudPlatformId);
		compareResultInstance.setInstanceId(id);
		LinkedHashMap<Calendar, Float> cpuTestMap = new LinkedHashMap<Calendar, Float>();
//    	Long start = System.currentTimeMillis();
//		logger.error("do here cpu");
		List<BeanCpu> cpuTestVm = (List<BeanCpu>)ClientOperFactory.findByIdTime(BeanCpu.class, id+"", tsSelectTimeStart, tsSelectTimeEnd);
		if (cpuTestVm.size() > 0){
			ArrayList<CompareResultAbstractEntity> compareResultList = compareResultEntity.getResultEntitylist();
			for(CompareResultAbstractEntity a : compareResultList){
				if( a.getSubClassName().equals("CompareResultCpuEntity")){
					a.setCurveListAllNull(false);
				}
			}
		}
//		logger.error("每一次获取cpu的个数"+cpuTestVm.size());
		for (int k = 0; k < cpuTestVm.size(); k++) {
			BeanCpu obj = cpuTestVm.get(k);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date)obj.getCreatedTime());
			cpuTestMap.put(calendar, (float)(Math.round(obj.getTotalTime()*100))/100);
//			logger.error("Cpu初始查询时间："+stringUtil.cal2String(calendar)+"; 初始查询值："+cpuTestVm.get(k).getTotalTime()+"");
		}
		compareResultInstance.setCurve(insertTestResultGeneral(cpuTestMap,
		TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
//    	Long end = System.currentTimeMillis();
//    	logger.error("每一次cpu的获取时间"+Long.toString(end-start));
		return compareResultInstance;
	}

	public CompareResultInstance getCompareResultMem(Integer id,
			Integer cloudPlatformId, Timestamp tsSelectTimeStart,
			Timestamp tsSelectTimeEnd) throws Exception {
		CompareResultInstance compareResultInstance = new CompareResultInstance();
		compareResultInstance.setCompanyId(cloudPlatformId);
		compareResultInstance.setInstanceId(id);

		LinkedHashMap<Calendar, Float> memTestMap = new LinkedHashMap<Calendar, Float>();
//		Long start = System.currentTimeMillis();
//		logger.error("do here mem");
		List<BeanMem> memTestVm = (List<BeanMem>)ClientOperFactory.findByIdTime(BeanMem.class, id+"", tsSelectTimeStart, tsSelectTimeEnd);
//		logger.error("每一次获取mem的个数"+memTestVm.size());
		if (memTestVm.size() > 0){
			ArrayList<CompareResultAbstractEntity> compareResultList = compareResultEntity.getResultEntitylist();
			for(CompareResultAbstractEntity a : compareResultList){
				if( a.getSubClassName().equals("CompareResultMemEntity")){
					a.setCurveListAllNull(false);
				}
			}
		}
		for (int k = 0; k < memTestVm.size(); k++) {
			BeanMem obj = memTestVm.get(k);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date)obj.getCreatedTime());
			memTestMap.put(calendar, (float)(Math.round(obj.getTransferSpeed()*100))/100);
//			logger.error("Mem初始查询时间："+stringUtil.cal2String(calendar)+"; 初始查询值："+memTestVm.get(k).getTransferSpeed()+"");
		}
		compareResultInstance.setCurve(insertTestResultGeneral(memTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
//		Long end = System.currentTimeMillis();
//    	logger.error("每一次mem的获取时间"+Long.toString(end-start));
		return compareResultInstance;
	}

	public List<CompareResultInstance> getCompareResultIo(Integer id,
			Integer cloudPlatformId, Timestamp tsSelectTimeStart,
			Timestamp tsSelectTimeEnd) throws Exception {
		List<CompareResultInstance> compareResultInstanceList = new ArrayList<CompareResultInstance>();

		CompareResultInstance ioSeqrdTestInstance = new CompareResultInstance();
		CompareResultInstance ioSeqwrTestInstance = new CompareResultInstance();
		CompareResultInstance ioRndrdTestInstance = new CompareResultInstance();
		CompareResultInstance ioRndwrTestInstance = new CompareResultInstance();
		ioSeqrdTestInstance.setCompanyId(cloudPlatformId);
		ioSeqwrTestInstance.setCompanyId(cloudPlatformId);
		ioRndrdTestInstance.setCompanyId(cloudPlatformId);
		ioRndwrTestInstance.setCompanyId(cloudPlatformId);
		ioSeqrdTestInstance.setInstanceId(id);
		ioSeqwrTestInstance.setInstanceId(id);
		ioRndrdTestInstance.setInstanceId(id);
		ioRndwrTestInstance.setInstanceId(id);

		LinkedHashMap<Calendar, Float> ioSeqrdTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> ioSeqwrTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> ioRndrdTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> ioRndwrTestMap = new LinkedHashMap<Calendar, Float>();
//    	Long start = System.currentTimeMillis();
		try {
			List<BeanIozone> ioTestVm = (List<BeanIozone>)ClientOperFactory.findByIdTime(BeanIozone.class, id+"", tsSelectTimeStart, tsSelectTimeEnd);
//			Long end = System.currentTimeMillis();
//	    	logger.error("每一次fileio的获取时间"+Long.toString(end-start));
//	    	logger.error("每一次获取io的个数"+ioTestVm.size());
	    	if (ioTestVm.size() > 0){
	    		ArrayList<CompareResultAbstractEntity> compareResultList = compareResultEntity.getResultEntitylist();
				for(CompareResultAbstractEntity a : compareResultList){
					if( a.getSubClassName().equals("CompareResultFileRndrdEntity") || a.getSubClassName().equals("CompareResultFileRndwrEntity") || a.getSubClassName().equals("CompareResultFileSeqrdEntity") || a.getSubClassName().equals("CompareResultFileSeqwrEntity") ){
						a.setCurveListAllNull(false);
					}
				}
			}
			for (int k = 0; k < ioTestVm.size(); k++) {
				BeanIozone obj = ioTestVm.get(k);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(obj.getCreatedTime());
					ioSeqrdTestMap
							.put(calendar, (float)(Math.round(Float.parseFloat(obj.getRead()+"")*100))/100);
					ioSeqwrTestMap
							.put(calendar, (float)(Math.round(Float.parseFloat(obj.getWrite()+"")*100))/100);
					ioRndrdTestMap
							.put(calendar, (float)(Math.round(Float.parseFloat(obj.getRandomRead()+"")*100))/100);
					ioRndwrTestMap
							.put(calendar, (float)(Math.round(Float.parseFloat(obj.getRandomWrite()+"")*100))/100);
//					logger.error("IORead初始查询时间："+stringUtil.cal2String(calendar)+"; 初始查询值："+ioTestVm.get(k).getRead()+"");
					
			}
//			logger.error("do here Io");
			ioSeqrdTestInstance.setCurve(insertTestResultGeneral(ioSeqrdTestMap,
					TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
			ioSeqwrTestInstance.setCurve(insertTestResultGeneral(ioSeqwrTestMap,
					TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
			ioRndrdTestInstance.setCurve(insertTestResultGeneral(ioRndrdTestMap,
					TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
			ioRndwrTestInstance.setCurve(insertTestResultGeneral(ioRndwrTestMap,
					TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
			compareResultInstanceList.add(ioSeqrdTestInstance);
			compareResultInstanceList.add(ioSeqwrTestInstance);
			compareResultInstanceList.add(ioRndrdTestInstance);
			compareResultInstanceList.add(ioRndwrTestInstance);
//			end = System.currentTimeMillis();
//	    	logger.error("filo时间"+Long.toString(end-start));
		} catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		return compareResultInstanceList;
	}

	public List<CompareResultInstance> getCompareResultOltp(Integer id,
			Integer cloudPlatformId, Timestamp tsSelectTimeStart,
			Timestamp tsSelectTimeEnd) throws Exception {

		List<CompareResultInstance> compareResultInstanceList = new ArrayList<CompareResultInstance>();

		CompareResultInstance oltpTransTestInstance = new CompareResultInstance();
		CompareResultInstance oltpDeadTestInstance = new CompareResultInstance();
		CompareResultInstance oltpRdwrTestInstance = new CompareResultInstance();
		CompareResultInstance oltpOtherTestInstance = new CompareResultInstance();

		oltpTransTestInstance.setCompanyId(cloudPlatformId);
		oltpDeadTestInstance.setCompanyId(cloudPlatformId);
		oltpRdwrTestInstance.setCompanyId(cloudPlatformId);
		oltpOtherTestInstance.setCompanyId(cloudPlatformId);
		oltpTransTestInstance.setInstanceId(id);
		oltpDeadTestInstance.setInstanceId(id);
		oltpRdwrTestInstance.setInstanceId(id);
		oltpOtherTestInstance.setInstanceId(id);

		LinkedHashMap<Calendar, Float> oltptransTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> oltpdeadTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> oltprdwrTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> oltpotherTestMap = new LinkedHashMap<Calendar, Float>();
//		Long start = System.currentTimeMillis();
		
		List<BeanTpcc> oltpTestVm = (List<BeanTpcc>)ClientOperFactory.findByIdTime(BeanTpcc.class, id+"", tsSelectTimeStart, tsSelectTimeEnd);
//		logger.error("每一次获取oltp的个数"+oltpTestVm.size());
		for (int k = 0; k < oltpTestVm.size(); k++) {
			BeanTpcc obj = oltpTestVm.get(k);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date)obj.getCreatedTime());
			oltptransTestMap.put(calendar, (float)(Math.round(Float.parseFloat(obj.getTpmc()+"")*100))/100);
			oltpdeadTestMap.put(calendar, (float)(Math.round(Float.parseFloat("0")*100))/100);
			oltprdwrTestMap.put(calendar, (float)(Math.round(Float.parseFloat("0")*100))/100);
			oltpotherTestMap.put(calendar, (float)(Math.round(Float.parseFloat("0")*100))/100);
//			logger.error("Oltp初始查询时间："+stringUtil.cal2String(calendar)+"; 初始查询值："+oltpTestVm.get(k).getTpmc()+"");
		}
		
//		Long end = System.currentTimeMillis();
//    	logger.error("每一次oltp的获取时间"+Long.toString(end-start));
		if (oltpTestVm.size() > 0){
			ArrayList<CompareResultAbstractEntity> compareResultList = compareResultEntity.getResultEntitylist();
			for(CompareResultAbstractEntity a : compareResultList){
				if( a.getSubClassName().equals("CompareResultOltpDeadEntity") || a.getSubClassName().equals("CompareResultOltpTransEntity") || a.getSubClassName().equals("CompareResultOltpRdWtEntity") ){
					a.setCurveListAllNull(false);
				}
			}
		}
//		logger.error("do here oltp");
		oltpTransTestInstance.setCurve(insertTestResultGeneral(oltptransTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
		oltpDeadTestInstance.setCurve(insertTestResultGeneral(oltpdeadTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
		oltpRdwrTestInstance.setCurve(insertTestResultGeneral(oltprdwrTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
		oltpOtherTestInstance.setCurve(insertTestResultGeneral(oltpotherTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));

		compareResultInstanceList.add(oltpTransTestInstance);
		compareResultInstanceList.add(oltpDeadTestInstance);
		compareResultInstanceList.add(oltpRdwrTestInstance);
		compareResultInstanceList.add(oltpOtherTestInstance);
//		Long end = System.currentTimeMillis();
//    	logger.error("每一次oltp的获取时间"+Long.toString(end-start));
		return compareResultInstanceList;
	}
	
	public List<CompareResultInstance> getCompareResultPing(Integer id,
			Integer cloudPlatformId, Timestamp tsSelectTimeStart,
			Timestamp tsSelectTimeEnd) throws Exception {
		
		List<CompareResultInstance> compareResultInstanceList = new ArrayList<CompareResultInstance>();
		
		CompareResultInstance PingBaiduTestInstance = new CompareResultInstance();
		CompareResultInstance Ping163TestInstance = new CompareResultInstance();
		CompareResultInstance PingQQTestInstance = new CompareResultInstance();
		CompareResultInstance PingSinaTestInstance = new CompareResultInstance();
		CompareResultInstance PingSouhuTestInstance = new CompareResultInstance();

		PingBaiduTestInstance.setCompanyId(cloudPlatformId);
		Ping163TestInstance.setCompanyId(cloudPlatformId);
		PingQQTestInstance.setCompanyId(cloudPlatformId);
		PingSinaTestInstance.setCompanyId(cloudPlatformId);
		PingSouhuTestInstance.setCompanyId(cloudPlatformId);
		PingBaiduTestInstance.setInstanceId(id);
		Ping163TestInstance.setInstanceId(id);
		PingQQTestInstance.setInstanceId(id);
		PingSinaTestInstance.setInstanceId(id);
		PingSouhuTestInstance.setInstanceId(id);

		LinkedHashMap<Calendar, Float> pingBaiduTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> ping163TestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> pingQQTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> pingSinaTestMap = new LinkedHashMap<Calendar, Float>();
		LinkedHashMap<Calendar, Float> pingSouhuTestMap = new LinkedHashMap<Calendar, Float>();
		
		
		Long start = System.currentTimeMillis();
//		logger.error("do here ping");
		List<BeanPing> pingTestVm = (List<BeanPing>)ClientOperFactory.findByIdTime(BeanPing.class, id+"", tsSelectTimeStart, tsSelectTimeEnd);

//		logger.error("每一次获取ping的大概个数"+pingTestVm.size()/5);
		for (int k = 0; k < pingTestVm.size(); k++) {
			//获取ping 顺序的
			BeanPing obj = pingTestVm.get(k);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date)obj.getCreatedTime());
			switch(obj.getDestIp()){
			case Constants.BAiDU:
				pingBaiduTestMap.put(calendar, (float)(Math.round(obj.getAvg()*100))/100);
				break;
			case Constants.N163:
				ping163TestMap.put(calendar, (float)(Math.round(obj.getAvg()*100))/100);
				break;
			case Constants.SINA:
				pingQQTestMap.put(calendar, (float)(Math.round(obj.getAvg()*100))/100);
				break;
			case Constants.QQ:
				pingSinaTestMap.put(calendar, (float)(Math.round(obj.getAvg()*100))/100);
				break;
			case Constants.SOUHU:
				pingSouhuTestMap.put(calendar, (float)(Math.round(obj.getAvg()*100))/100);
				break;
			}
//			logger.error("Ping初始查询时间："+stringUtil.cal2String(calendar)+"; 初始查询值："+pingTestVm.get(k).getAvg()+"");
		}
		
		if (pingBaiduTestMap.size() > 0){
			ArrayList<CompareResultAbstractEntity> compareResultList = compareResultEntity.getResultEntitylist();
			for(CompareResultAbstractEntity a : compareResultList){
				if( a.getSubClassName().equals("CompareResultPing163Entity") || a.getSubClassName().equals("CompareResultPingBaiduEntity") || a.getSubClassName().equals("CompareResultPingQQEntity") || a.getSubClassName().equals("CompareResultPingSinaEntity") || a.getSubClassName().equals("CompareResultPingSouhuEntity") ){
					a.setCurveListAllNull(false);
				}
			}
	    }
		
		PingBaiduTestInstance.setCurve(insertTestResultPing(pingBaiduTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
		Ping163TestInstance.setCurve(insertTestResultPing(ping163TestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
		PingQQTestInstance.setCurve(insertTestResultPing(pingQQTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
		PingSinaTestInstance.setCurve(insertTestResultPing(pingSinaTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));
		PingSouhuTestInstance.setCurve(insertTestResultPing(pingSouhuTestMap,
				TimeIntervalUtil.timestamp2Calendar(tsSelectTimeStart), TimeIntervalUtil.timestamp2Calendar(tsSelectTimeEnd)));

		compareResultInstanceList.add(PingBaiduTestInstance);
		compareResultInstanceList.add(Ping163TestInstance);
		compareResultInstanceList.add(PingQQTestInstance);
		compareResultInstanceList.add(PingSinaTestInstance);
		compareResultInstanceList.add(PingSouhuTestInstance);
//		Long end = System.currentTimeMillis();
//    	logger.error("每一次ping的获取时间"+Long.toString(end-start));
		return compareResultInstanceList;
	}
	

	/**
	 * @param 数据源
	 *            一个虚拟机实例的：LinkedHashMap<Calendar, Float>(没有缺值插入)
	 *            起始时间：需要从何时开始计算是否有缺点，这个应该是和上面的查询开始时间对应的，不可以随意传值
	 *            截止时间：计算截止时间，这个应该是和上面的查询截止时间对应的，不可以随意传值 默认上两个值都是8*24个点
	 *            要求，传入的数据可以缺值，但绝不可无序，适用于传入的map中的日历时间必须是有序的
	 * @return 返回List<Map<String,String>，默认为8*24个map,map中第一对为时间，第二对为值，都是String
	 * @throws Exception
	 * */
	/*public List<Map<String, String>> insertTestResult(
			LinkedHashMap<Calendar, Float> testMap, Calendar caSelectTimeStart,
			Calendar caSelectTimeEnd) throws Exception {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Iterator curveForInstanceIter = testMap.entrySet().iterator();// 虚拟机所有的integer
		float dataCurve[] = new float[24 * 8];
		for (int i = 0; i<dataCurve.length; i++){
			dataCurve[i] = -1;
		}
		try {
			int number = 0;
			Map.Entry entry;
			if (curveForInstanceIter.hasNext()) {
				entry = (Map.Entry) curveForInstanceIter.next();
				Calendar a = Calendar.getInstance();
				a = (Calendar) caSelectTimeStart.clone();
				while (a.compareTo(caSelectTimeEnd) == -1) {// a早返回-1;
															// a相同,返回0;
															// a晚,返回1
					if (a.get(Calendar.YEAR) == ((Calendar) entry.getKey())
							.get(Calendar.YEAR)
							&& a.get(Calendar.MONTH) == ((Calendar) entry
									.getKey()).get(Calendar.MONTH)
							&& a.get(Calendar.DATE) == ((Calendar) entry
									.getKey()).get(Calendar.DATE)
							&& a.get(Calendar.HOUR_OF_DAY) == ((Calendar) entry
									.getKey()).get(Calendar.HOUR_OF_DAY)) {
						dataCurve[number] = Float.parseFloat(entry.getValue()
								.toString());
						// System.out.println(Float.parseFloat(entry.getValue().toString()));
						if (curveForInstanceIter.hasNext()) {
							entry = (Map.Entry) curveForInstanceIter.next();
						} else {
							// System.out.println("缺值");
							break;
						}
					} else {
						dataCurve[number] = -1;
					}
					number++;
					a.add(Calendar.HOUR, 1);
				}
			} else {
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		Calendar startCal = Calendar.getInstance();
		startCal = (Calendar) caSelectTimeStart.clone();
		for (int i = 0; i < 24 * 8; i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put(Constants.CURVEINSTANCEMAPTIME,
					stringUtil.cal2String(startCal));

			map.put(Constants.CURVEINSTANCEMAPVALUE, dataCurve[i] == -1 ? null
					: (dataCurve[i] + ""));
			startCal.add(Calendar.HOUR, 1);
			list.add(map);
		}
		return list;
	}*/
	
	/**
	 * @param 数据源
	 *            一个虚拟机实例的：LinkedHashMap<Calendar, Float>(没有缺值插入)
	 *            起始时间：需要从何时开始计算是否有缺点，这个应该是和上面的查询开始时间对应的，不可以随意传值
	 *            截止时间：计算截止时间，这个应该是和上面的查询截止时间对应的，不可以随意传值 
	 *            时间间隔：总共多少个小时，和返回的多少个map是固定的
	 *            要求，传入的数据可以缺值，也可以无序，适用于map里面的map中的日历时间为无序的
	 *            之前是一个小时一个数据，现在改为4个小时一次了，所以这里改了插入的间隔
	 * @return 返回List<Map<String,String>,map中第一对为时间，第二对为值，都是String,map个数和上值对应
	 *         如果testMap为空，即这段没有任何数据，则返回空
	 * 
	 * @throws Exception
	 * */
	public List<Map<String, String>> insertTestResultGeneral(
			LinkedHashMap<Calendar, Float> testMap, Calendar caSelectTimeStart, Calendar caSelectTimeEnd)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		int intevalHour = new Long((caSelectTimeEnd.getTimeInMillis()-caSelectTimeStart.getTimeInMillis())/(1000*60*60*4)).intValue()+1;
//		System.out.println("setcurve_intevalHour:"+intevalHour);
		
		Calendar a = Calendar.getInstance();
		a = (Calendar) caSelectTimeStart.clone();//这里临时设置了一个毫秒值
		a.set(Calendar.MILLISECOND,0);
		
		Iterator curveForInstanceIter = testMap.entrySet().iterator();// 虚拟机所有的integer
		float dataCurve[] = new float[intevalHour];
		for (int i = 0; i<dataCurve.length; i++){
			dataCurve[i] = -1;
		}
		try {
			int number = 0;
			Map.Entry entry;
			if (curveForInstanceIter.hasNext()) {
				entry = (Map.Entry) curveForInstanceIter.next();

				while (true) {
//					System.out.println("时间"+stringUtil.cal2String((Calendar) entry.getKey()));
					long timeL = ((Calendar) entry.getKey()).getTimeInMillis()
							- a.getTimeInMillis();
					int hours = new Long(timeL / (1000 * 60 * 60 * 4)).intValue();
//					System.out.println("差"+hours+"小时");
					if (hours <= dataCurve.length-1){
						dataCurve[hours] = Float.parseFloat(entry.getValue()
								.toString());	
					}
					if (curveForInstanceIter.hasNext()) {
						entry = (Map.Entry) curveForInstanceIter.next();
					} else {
						break;
					}
				}
			} else {
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		Calendar startCal = Calendar.getInstance();
		startCal = (Calendar) caSelectTimeStart.clone();
		startCal.add(Calendar.HOUR, -1);
		for (int i = 0; i < intevalHour; i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put(Constants.CURVEINSTANCEMAPTIME,
					stringUtil.cal2String(startCal));
			map.put(Constants.CURVEINSTANCEMAPVALUE, dataCurve[i] == -1 ? null
					: (dataCurve[i] + ""));
			startCal.add(Calendar.HOUR, 4);
			list.add(map);
		}
		
		return list;
	}
	
	/**
	 * @param 数据源
	 *            一个虚拟机实例的：LinkedHashMap<Calendar, Float>(没有缺值插入)
	 *            起始时间：需要从何时开始计算是否有缺点，这个应该是和上面的查询开始时间对应的，不可以随意传值
	 *            截止时间：计算截止时间，这个应该是和上面的查询截止时间对应的，不可以随意传值 
	 *            时间间隔：总共多少个小时，和返回的多少个map是固定的
	 *            要求，传入的数据可以缺值，也可以无序，适用于map里面的map中的日历时间为无序的
	 * @return 返回List<Map<String,String>,map中第一对为时间，第二对为值，都是String,map个数和上值对应
	 *         如果testMap为空，即这段没有任何数据，则返回空
	 * 
	 * @throws Exception
	 * */
	public List<Map<String, String>> insertTestResultPing(
			LinkedHashMap<Calendar, Float> testMap, Calendar caSelectTimeStart, Calendar caSelectTimeEnd)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		int intevalHour = new Long((caSelectTimeEnd.getTimeInMillis()-caSelectTimeStart.getTimeInMillis())/(1000*60*60)).intValue()+1;
//		System.out.println("setcurve_intevalHour:"+intevalHour);
		
		Calendar a = Calendar.getInstance();
		a = (Calendar) caSelectTimeStart.clone();//这里临时设置了一个毫秒值
		a.set(Calendar.MILLISECOND,0);
		
		Iterator curveForInstanceIter = testMap.entrySet().iterator();// 虚拟机所有的integer
		float dataCurve[] = new float[intevalHour];
		for (int i = 0; i<dataCurve.length; i++){
			dataCurve[i] = -1;
		}
		try {
			int number = 0;
			Map.Entry entry;
			if (curveForInstanceIter.hasNext()) {
				entry = (Map.Entry) curveForInstanceIter.next();

				while (true) {
//					System.out.println("时间"+stringUtil.cal2String((Calendar) entry.getKey()));
					long timeL = ((Calendar) entry.getKey()).getTimeInMillis()
							- a.getTimeInMillis();
					int hours = new Long(timeL / (1000 * 60 * 60)).intValue();
//					System.out.println("差"+hours+"小时");
					if (hours <= dataCurve.length-1){
						dataCurve[hours] = Float.parseFloat(entry.getValue()
								.toString());	
					}
					if (curveForInstanceIter.hasNext()) {
						entry = (Map.Entry) curveForInstanceIter.next();
					} else {
						break;
					}
				}
			} else {
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		Calendar startCal = Calendar.getInstance();
		startCal = (Calendar) caSelectTimeStart.clone();
		for (int i = 0; i < intevalHour; i++) {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put(Constants.CURVEINSTANCEMAPTIME,
					stringUtil.cal2String(startCal));
			map.put(Constants.CURVEINSTANCEMAPVALUE, dataCurve[i] == -1 ? null
					: (dataCurve[i] + ""));
			startCal.add(Calendar.HOUR, 1);
			list.add(map);
		}
		
		return list;
	}
	
	public static void main(String[] args) throws Exception {
//		CompareResultInstanceFactory cf = new CompareResultInstanceFactory();
//		cf.getCompareResultCpu(11, 3, Timestamp.valueOf("2015-04-20 00:00:00"), Timestamp.valueOf("2015-04-27 00:00:00"));
	}
	
	
	
	
	
}
