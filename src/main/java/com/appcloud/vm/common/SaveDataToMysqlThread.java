package com.appcloud.vm.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.appcloud.mysqldao.SumWeekCpuDao;
import com.appcloud.mysqldao.SumWeekIozoneDao;
import com.appcloud.mysqldao.SumWeekMemDao;
import com.appcloud.mysqldao.SumWeekPingDao;
import com.appcloud.mysqldao.SumWeekTpccDao;
import com.appcloud.vm.action.dbentity.SumWeekCpu;
import com.appcloud.vm.action.dbentity.SumWeekIozone;
import com.appcloud.vm.action.dbentity.SumWeekMem;
import com.appcloud.vm.action.dbentity.SumWeekPing;
import com.appcloud.vm.action.dbentity.SumWeekTpcc;
import com.appcloud.vm.action.entity.VM48InforEntity;
import com.appcloud.vm.action.entity.WeekSumForMysqlSave;
import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;
import com.free4lab.monitorproxy.restclient.ClientOperFactory;

public class SaveDataToMysqlThread extends Thread {
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 30);//半分钟
				System.out.println("获取周统计数据并存到sql表里线程一次计算开始");
				Calendar startCal = getWeekDay();
				startCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DATE), 0, 0, 0);
				Calendar endCal = Calendar.getInstance();
				Timestamp timeStart = new Timestamp(startCal.getTimeInMillis()); 
				Timestamp timeEnd = new Timestamp(endCal.getTimeInMillis());
		    	
		    	showEndAndStartTime(startCal, endCal);
				
				ArrayList<VM48InforEntity> VM48InforList = InitializeListener.getVM48InforList();
				HashMap<Integer,WeekSumForMysqlSave> saveWeekSumForMysql = InitializeListener.getSaveWeekSumForMysql();
				getResultAndSave(timeStart, saveWeekSumForMysql, timeStart, timeEnd);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//这个线程做之前要先登录，也就是说程序跑起来之前要先登录一下，然后再运行的时候鉴权才不会出错
	public static void getResultAndSave(Timestamp timeStart, HashMap<Integer,WeekSumForMysqlSave> map, Timestamp startTime, Timestamp endTime){
		System.out.println("开始获取");
		System.out.println("map.size():"+map.size());
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
		Map.Entry entry = (Map.Entry) iter.next();
		int key = (int)entry.getKey();
		WeekSumForMysqlSave comBean = (WeekSumForMysqlSave)entry.getValue();
		
		System.out.println("当前获取的主机公司名称是："+comBean.getCompanyName());
		List<BeanCpu> cpuTestResultWeek = (List<BeanCpu>)ClientOperFactory.findByIdTime(BeanCpu.class, comBean.getUuid()+"", startTime, endTime);
		List<BeanMem> memTestResultWeek = (List<BeanMem>)ClientOperFactory.findByIdTime(BeanMem.class, comBean.getUuid()+"", startTime, endTime);
		List<BeanTpcc> oltpTestResultWeek = (List<BeanTpcc>)ClientOperFactory.findByIdTime(BeanTpcc.class, comBean.getUuid()+"", startTime, endTime);
		List<BeanIozone> fileTestResultWeek = (List<BeanIozone>)ClientOperFactory.findByIdTime(BeanIozone.class, comBean.getUuid()+"", startTime, endTime);
		List<BeanPing> pingTestResultWeek = (List<BeanPing>)ClientOperFactory.findByIdTime(BeanPing.class, comBean.getUuid()+"", startTime, endTime);
	
		//cpu获取,现在是如果没有获取到本周任何数据，就什么都不做
		System.out.println("CPU获取个数："+cpuTestResultWeek.size());
		if(cpuTestResultWeek.size() > 0){
			SumWeekCpu sumWeekCpu = new SumWeekCpu();
			iniSumWeekCpu(sumWeekCpu,comBean);
			float cpuSum = 0.0f;
			for(BeanCpu beanCpu : cpuTestResultWeek){
				cpuSum += beanCpu.getTotalTime();
			}
			sumWeekCpu.setSumTime(timeStart);
			sumWeekCpu.setTotalTime(cpuSum);
			sumWeekCpu.setCount(cpuTestResultWeek.size());
			
			SumWeekCpuDao sumWeekCpuDao = new SumWeekCpuDao();
			List<SumWeekCpu> sumWeekCpuFind = sumWeekCpuDao.findByProperty("uuid", sumWeekCpu.getUuid(), "time", sumWeekCpu.getSumTime());
			if(sumWeekCpuFind.size() > 0){
		    	sumWeekCpuDao.deleteById((SumWeekCpu)sumWeekCpuFind.get(0));
		    }
		    sumWeekCpuDao.save(sumWeekCpu);
		    System.out.println("CPU保存");
		}
	    
	    //mem获取
	    System.out.println("MEM获取个数："+memTestResultWeek.size());
	    if(memTestResultWeek.size() > 0){
	    	SumWeekMem sumWeekMem = new SumWeekMem();
	    	iniSumWeekMem(sumWeekMem,comBean);
			float memSum = 0.0f;
			for(BeanMem beanMem : memTestResultWeek){
				memSum += beanMem.getTransferSpeed();
			}
			sumWeekMem.setSumTime(timeStart);
			sumWeekMem.setTransferSpeed(memSum);
			sumWeekMem.setCount(memTestResultWeek.size());
			
			SumWeekMemDao sumWeekMemDao = new SumWeekMemDao();
			List<SumWeekMem> sumWeekMemFind = sumWeekMemDao.findByProperty("uuid", sumWeekMem.getUuid(), "time", sumWeekMem.getSumTime());
		    if(sumWeekMemFind.size() > 0){
		    	sumWeekMemDao.deleteById((SumWeekMem)sumWeekMemFind.get(0));
		    }
		    sumWeekMemDao.save(sumWeekMem);
		    System.out.println("MEM保存");
	    }
		
		//oltp获取
	    System.out.println("TPCC获取个数:"+oltpTestResultWeek.size());
	    if(oltpTestResultWeek.size() > 0){
	    	SumWeekTpcc sumWeekTpcc = new SumWeekTpcc();
	    	iniSumWeekTpcc(sumWeekTpcc,comBean);
			float tpccSum = 0.0f;
			for(BeanTpcc beanTpcc : oltpTestResultWeek){
				tpccSum += beanTpcc.getTpmc();
			}
			sumWeekTpcc.setSumTime(timeStart);
			sumWeekTpcc.setTpmc(tpccSum);
			sumWeekTpcc.setCount(oltpTestResultWeek.size());
			
			SumWeekTpccDao sumWeekTpccDao = new SumWeekTpccDao();
			List<SumWeekTpcc> sumWeekTpccFind = sumWeekTpccDao.findByProperty("uuid", sumWeekTpcc.getUuid(), "time", sumWeekTpcc.getSumTime());
		    if(sumWeekTpccFind.size() > 0){
		    	sumWeekTpccDao.deleteById((SumWeekTpcc)sumWeekTpccFind.get(0));
		    }
		    sumWeekTpccDao.save(sumWeekTpcc);
		    System.out.println("TPCC保存");
	    }
		
		//iozone获取
	    System.out.println("IOZONE获取个数:"+fileTestResultWeek.size());
	    if(fileTestResultWeek.size() > 0){
	    	SumWeekIozone sumWeekIozone = new SumWeekIozone();
	    	iniSumWeekIozone(sumWeekIozone,comBean);
	    	float fileSize = 0;
	    	float recordSize = 0;
	    	float write = 0;
	    	float rewrite = 0;
	    	float read = 0;
	    	float reread = 0;
	    	float randomRead = 0;
	    	float randomWrite = 0;
			for(BeanIozone beanIozone : fileTestResultWeek){
				fileSize += beanIozone.getFileSize();
			    recordSize += beanIozone.getRecordSize();
			    write += beanIozone.getWrite();
			    rewrite += beanIozone.getRewrite();
			    read += beanIozone.getRead();
			    reread += beanIozone.getReread();
			    randomRead += beanIozone.getRandomRead();
			    randomWrite += beanIozone.getRandomWrite();
			}
			
			sumWeekIozone.setSumTime(timeStart);
			sumWeekIozone.setFileSize(fileSize);
			sumWeekIozone.setRecordSize(recordSize);
			sumWeekIozone.setWrite(write);
			sumWeekIozone.setRewrite(rewrite);
			sumWeekIozone.setRead(read);
			sumWeekIozone.setReread(reread);
			sumWeekIozone.setRandomRead(randomRead);
			sumWeekIozone.setRandomWrite(randomWrite);
			sumWeekIozone.setCount(fileTestResultWeek.size());
			
			SumWeekIozoneDao sumWeekIozoneDao = new SumWeekIozoneDao();
			List<SumWeekIozone> sumWeekIozoneFind = sumWeekIozoneDao.findByProperty("uuid", sumWeekIozone.getUuid(), "time", sumWeekIozone.getSumTime());
		    if(sumWeekIozoneFind.size() > 0){
		    	sumWeekIozoneDao.deleteById((SumWeekIozone)sumWeekIozoneFind.get(0));
		    }
		    System.out.println(sumWeekIozone.toString());
		    sumWeekIozoneDao.save(sumWeekIozone);
		    System.out.println("IOZONE保存");
	    }
	    
	    //ping获取
	    System.out.println("PING获取个数:"+pingTestResultWeek.size());
	    if(pingTestResultWeek.size() > 0){
	    	SumWeekPing entityBaidu = new SumWeekPing();
			SumWeekPing entity163 = new SumWeekPing();
			SumWeekPing entityQQ = new SumWeekPing();
			SumWeekPing entitySina = new SumWeekPing();
			SumWeekPing entitySouhu = new SumWeekPing();
			iniSumWeekPing(entityBaidu,comBean);
			iniSumWeekPing(entity163,comBean);
			iniSumWeekPing(entityQQ,comBean);
			iniSumWeekPing(entitySina,comBean);
			iniSumWeekPing(entitySouhu,comBean);
			float BaiduSum = 0.0f;
			float N163Sum = 0.0f;
			float QQSum = 0.0f;
			float SINASum = 0.0f;
			float SOUHUSum = 0.0f;
			
			float BAiDULoss = 0;
			float N163Loss = 0;
			float QQLoss = 0;
			float SINALoss = 0;
			float SOUHULoss = 0;
			
			Integer BAiDUCount = 0;
			Integer N163Count = 0;
			Integer QQCount = 0;
			Integer SINACount = 0;
			Integer SOUHUCount = 0;
			for (BeanPing beanPing : pingTestResultWeek){
				switch(beanPing.getDestIp()){
				case Constants.BAiDU:
					BaiduSum += beanPing.getAvg();
					BAiDULoss += beanPing.getLoss();
					BAiDUCount++;
					break;
				case Constants.N163:
					N163Sum += beanPing.getAvg();
					N163Loss += beanPing.getLoss();
					N163Count++;
					break;
				case Constants.QQ:
					QQSum += beanPing.getAvg();
					QQLoss += beanPing.getLoss();
					QQCount++;
					break;
				case Constants.SINA:
					SINASum += beanPing.getAvg();
					SINALoss += beanPing.getLoss();
					SINACount++;
					break;
				case Constants.SOUHU:
					SOUHUSum += beanPing.getAvg();
					SOUHULoss += beanPing.getLoss();
					SOUHUCount++;
					break;
				}
			}
			
			BaiduSum /= BAiDUCount;
			N163Sum /= N163Count;
			QQSum /= QQCount;
			SINASum /= SINACount;
			SOUHUSum /= SOUHUCount;
			
			entityBaidu.setSumTime(timeStart);
			entityBaidu.setDestIP(Constants.BAiDU);
			entityBaidu.setAvg(BaiduSum);
			entityBaidu.setLoss(BAiDULoss);
			entityBaidu.setCount(BAiDUCount);
			
			entity163.setSumTime(timeStart);
			entity163.setDestIP(Constants.N163);
			entity163.setAvg(N163Sum);
			entity163.setLoss(N163Loss);
			entity163.setCount(N163Count);
			
			entityQQ.setSumTime(timeStart);
			entityQQ.setDestIP(Constants.QQ);
			entityQQ.setAvg(QQSum);
			entityQQ.setLoss(QQLoss);
			entityQQ.setCount(QQCount);
			
			entitySina.setSumTime(timeStart);
			entitySina.setDestIP(Constants.SINA);
			entitySina.setAvg(SINASum);
			entitySina.setLoss(SINALoss);
			entitySina.setCount(SINACount);
			
			entitySouhu.setSumTime(timeStart);
			entitySouhu.setDestIP(Constants.SOUHU);
			entitySouhu.setAvg(SOUHUSum);
			entitySouhu.setLoss(SOUHULoss);
			entitySouhu.setCount(SOUHUCount);
			
			SumWeekPingDao sumWeekPingDao = new SumWeekPingDao();
			List<SumWeekPing> sumWeekPingFind = sumWeekPingDao.findByProperty("uuid", entityBaidu.getUuid(), "time", entityBaidu.getSumTime());
		    if(sumWeekPingFind.size() > 0){
		    	for(SumWeekPing its : sumWeekPingFind){
		    		sumWeekPingDao.deleteById(its);
		    	}
		    }
		    sumWeekPingDao.save(entityBaidu);
		    sumWeekPingDao.save(entity163);
		    sumWeekPingDao.save(entityQQ);
		    sumWeekPingDao.save(entitySina);
		    sumWeekPingDao.save(entitySouhu);
		    System.out.println("PING保存");
			}
		}
		
	}
	
	public static void iniSumWeekCpu(SumWeekCpu sumWeekCpu, WeekSumForMysqlSave comBean){
		sumWeekCpu.setUuid(comBean.getUuid());
		sumWeekCpu.setCpu(comBean.getCpu());
		sumWeekCpu.setMem(comBean.getMem());
		sumWeekCpu.setDisk(comBean.getDisk());
		sumWeekCpu.setBandwidth(comBean.getBandwidth());
		sumWeekCpu.setIp(comBean.getIp());
		sumWeekCpu.setMac(comBean.getMac());
		sumWeekCpu.setCreateTime(comBean.getCreateTime());
		sumWeekCpu.setUpdateTime(comBean.getUpdateTime());
		sumWeekCpu.setOs(comBean.getOs());
		sumWeekCpu.setCompanyId(comBean.getCompanyId());
		sumWeekCpu.setCompanyName(comBean.getCompanyName());
	}
	
	public static void iniSumWeekMem(SumWeekMem sumWeekMem, WeekSumForMysqlSave comBean){
		sumWeekMem.setUuid(comBean.getUuid());
		sumWeekMem.setCpu(comBean.getCpu());
		sumWeekMem.setMem(comBean.getMem());
		sumWeekMem.setDisk(comBean.getDisk());
		sumWeekMem.setBandwidth(comBean.getBandwidth());
		sumWeekMem.setIp(comBean.getIp());
		sumWeekMem.setMac(comBean.getMac());
		sumWeekMem.setCreateTime(comBean.getCreateTime());
		sumWeekMem.setUpdateTime(comBean.getUpdateTime());
		sumWeekMem.setOs(comBean.getOs());
		sumWeekMem.setCompanyId(comBean.getCompanyId());
		sumWeekMem.setCompanyName(comBean.getCompanyName());
	}
	
	public static void iniSumWeekTpcc(SumWeekTpcc sumWeekTpcc, WeekSumForMysqlSave comBean){
		sumWeekTpcc.setUuid(comBean.getUuid());
		sumWeekTpcc.setCpu(comBean.getCpu());
		sumWeekTpcc.setMem(comBean.getMem());
		sumWeekTpcc.setDisk(comBean.getDisk());
		sumWeekTpcc.setBandwidth(comBean.getBandwidth());
		sumWeekTpcc.setIp(comBean.getIp());
		sumWeekTpcc.setMac(comBean.getMac());
		sumWeekTpcc.setCreateTime(comBean.getCreateTime());
		sumWeekTpcc.setUpdateTime(comBean.getUpdateTime());
		sumWeekTpcc.setOs(comBean.getOs());
		sumWeekTpcc.setCompanyId(comBean.getCompanyId());
		sumWeekTpcc.setCompanyName(comBean.getCompanyName());
	}
	
	public static void iniSumWeekIozone(SumWeekIozone sumWeekIozone, WeekSumForMysqlSave comBean){
		sumWeekIozone.setUuid(comBean.getUuid());
		sumWeekIozone.setCpu(comBean.getCpu());
		sumWeekIozone.setMem(comBean.getMem());
		sumWeekIozone.setDisk(comBean.getDisk());
		sumWeekIozone.setBandwidth(comBean.getBandwidth());
		sumWeekIozone.setIp(comBean.getIp());
		sumWeekIozone.setMac(comBean.getMac());
		sumWeekIozone.setCreateTime(comBean.getCreateTime());
		sumWeekIozone.setUpdateTime(comBean.getUpdateTime());
		sumWeekIozone.setOs(comBean.getOs());
		sumWeekIozone.setCompanyId(comBean.getCompanyId());
		sumWeekIozone.setCompanyName(comBean.getCompanyName());
	}
	
	public static void iniSumWeekPing(SumWeekPing sumWeekPing, WeekSumForMysqlSave comBean){
		sumWeekPing.setUuid(comBean.getUuid());
		sumWeekPing.setCpu(comBean.getCpu());
		sumWeekPing.setMem(comBean.getMem());
		sumWeekPing.setDisk(comBean.getDisk());
		sumWeekPing.setBandwidth(comBean.getBandwidth());
		sumWeekPing.setIp(comBean.getIp());
		sumWeekPing.setMac(comBean.getMac());
		sumWeekPing.setCreateTime(comBean.getCreateTime());
		sumWeekPing.setUpdateTime(comBean.getUpdateTime());
		sumWeekPing.setOs(comBean.getOs());
		sumWeekPing.setCompanyId(comBean.getCompanyId());
		sumWeekPing.setCompanyName(comBean.getCompanyName());
	}
	
	// 获取本周周一
	public static Calendar getWeekDay() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return c;
	}
	
	//显示本周一的时间到现在此时此刻的时间
	public static void showEndAndStartTime(Calendar startCal, Calendar endCal){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String testUpToTime = sdf.format(startCal.getTime());
		String testUpToTimeEnd = sdf.format(endCal.getTime());
		System.out.println("统计每周数据的开始时间："+testUpToTime);
		System.out.println("统计每周数据的结束时间："+testUpToTimeEnd);
	}


}
