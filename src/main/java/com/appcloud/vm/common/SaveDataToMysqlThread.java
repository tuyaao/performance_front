package com.appcloud.vm.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;
import com.free4lab.monitorproxy.restclient.ClientOperFactory;

public class SaveDataToMysqlThread extends Thread {
	
	public void run() {
//		while (true) {
//			try {
//				Thread.sleep(1000 * 30);//半分钟
//				System.out.println("开始就算");
//				Calendar startCal = getWeekDay();
//				startCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DATE), 0, 0, 0);
//				Calendar endCal = Calendar.getInstance();
//				Timestamp timeEnd = new Timestamp(endCal.getTimeInMillis());
//		    	Timestamp timeStart = new Timestamp(startCal.getTimeInMillis()); 
//		    	
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String testUpToTime = sdf.format(startCal.getTime());
//				String testUpToTimeEnd = sdf.format(endCal.getTime());
//				System.out.println("统计每周数据的开始时间："+testUpToTime);
//				System.out.println("统计每周数据的结束时间："+testUpToTimeEnd);
//				
//				ArrayList<VM48InforEntity> VM48InforList = InitializeListener.getVM48InforList();
//				getResultAndSave(testUpToTime, VM48InforList, timeStart, timeEnd);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	//这个线程做之前要先登录，也就是说程序跑起来之前要先登录一下，然后再运行的时候鉴权才不会出错
	public static void getResultAndSave(String startTimeBean, ArrayList<VM48InforEntity> VM48InforList, Timestamp startTime, Timestamp endTime){
		System.out.println("开始获取");
		for (VM48InforEntity it :VM48InforList){
			System.out.println("当前获取的主机公司名称是："+it.getPlatformName());
			List<BeanCpu> cpuTestResultWeek = (List<BeanCpu>)ClientOperFactory.findByIdTime(BeanCpu.class, it.getId()+"", startTime, endTime);
			List<BeanMem> memTestResultWeek = (List<BeanMem>)ClientOperFactory.findByIdTime(BeanMem.class, it.getId()+"", startTime, endTime);
			List<BeanTpcc> oltpTestResultWeek = (List<BeanTpcc>)ClientOperFactory.findByIdTime(BeanTpcc.class, it.getId()+"", startTime, endTime);
			List<BeanIozone> fileTestResultWeek = (List<BeanIozone>)ClientOperFactory.findByIdTime(BeanIozone.class, it.getId()+"", startTime, endTime);
			List<BeanPing> pingTestResultWeek = (List<BeanPing>)ClientOperFactory.findByIdTime(BeanPing.class, it.getId()+"", startTime, endTime);
		
			//cpu获取,现在是如果没有获取到本周任何数据，就什么都不做
			System.out.println("CPU获取个数："+cpuTestResultWeek.size());
			if(cpuTestResultWeek.size() > 0){
				SumWeekCpu sumWeekCpu = new SumWeekCpu(null, it.getId(), it.getPlatformName(), startTimeBean, 0.0f, cpuTestResultWeek.size());
				float cpuSum = 0.0f;
				for(BeanCpu beanCpu : cpuTestResultWeek){
					cpuSum += beanCpu.getTotalTime();
				}
				sumWeekCpu.setTotalTime(cpuSum);
				
				SumWeekCpuDao sumWeekCpuDao = new SumWeekCpuDao();
				System.out.println("sumWeekCpu.getUuid()："+sumWeekCpu.getUuid()+";"+"sumWeekCpu.getTime()："+sumWeekCpu.getTime());
				List<SumWeekCpu> sumWeekCpuFind = sumWeekCpuDao.findByProperty("uuid", sumWeekCpu.getUuid(), "time", sumWeekCpu.getTime());
			    if(sumWeekCpuFind.size() > 0){
			    	sumWeekCpuDao.deleteById((SumWeekCpu)sumWeekCpuFind.get(0));
			    }
			    sumWeekCpuDao.save(sumWeekCpu);
			    System.out.println("CPU保存");
			}
		    
		    //mem获取
		    System.out.println("MEM获取个数："+memTestResultWeek.size());
		    if(memTestResultWeek.size() > 0){
		    	SumWeekMem sumWeekMem = new SumWeekMem(null, it.getId(), it.getPlatformName(), startTimeBean, 0.0f, memTestResultWeek.size());
				float memSum = 0.0f;
				for(BeanMem beanMem : memTestResultWeek){
					memSum += beanMem.getTransferSpeed();
				}
				sumWeekMem.setTransferSpeed(memSum);
				
				SumWeekMemDao sumWeekMemDao = new SumWeekMemDao();
				List<SumWeekMem> sumWeekMemFind = sumWeekMemDao.findByProperty("uuid", sumWeekMem.getUuid(), "time", sumWeekMem.getTime());
			    if(sumWeekMemFind.size() > 0){
			    	sumWeekMemDao.deleteById((SumWeekMem)sumWeekMemFind.get(0));
			    }
			    sumWeekMemDao.save(sumWeekMem);
			    System.out.println("MEM保存");
		    }
			
			//tpcc获取
		    System.out.println("TPCC获取个数:"+oltpTestResultWeek.size());
		    if(oltpTestResultWeek.size() > 0){
		    	SumWeekTpcc sumWeekTpcc = new SumWeekTpcc(null, it.getId(), it.getPlatformName(), startTimeBean, 0.0f, memTestResultWeek.size());
				float tpccSum = 0.0f;
				for(BeanTpcc beanTpcc : oltpTestResultWeek){
					tpccSum += beanTpcc.getTpmc();
				}
				sumWeekTpcc.setTpmc(tpccSum);
				
				SumWeekTpccDao sumWeekTpccDao = new SumWeekTpccDao();
				List<SumWeekTpcc> sumWeekTpccFind = sumWeekTpccDao.findByProperty("uuid", sumWeekTpcc.getUuid(), "time", sumWeekTpcc.getTime());
			    if(sumWeekTpccFind.size() > 0){
			    	sumWeekTpccDao.deleteById((SumWeekTpcc)sumWeekTpccFind.get(0));
			    }
			    sumWeekTpccDao.save(sumWeekTpcc);
			    System.out.println("TPCC保存");
		    }
			
			//iozone获取
		    System.out.println("IOZONE获取个数:"+fileTestResultWeek.size());
		    if(fileTestResultWeek.size() > 0){
		    	SumWeekIozone sumWeekIozone = new SumWeekIozone(null, it.getId(), it.getPlatformName(), startTimeBean, 16, 16,
						0, 0, 0, 0, 0, 0, oltpTestResultWeek.size());
				Integer iowrite = 0;
				Integer iorewrite = 0;
				Integer ioread = 0;
				Integer ioreread = 0;
				Integer iorandomRead = 0;
				Integer iorandomWrite = 0;
				for(BeanIozone beanIozone : fileTestResultWeek){
					iowrite += beanIozone.getWrite();
					iorewrite += beanIozone.getRewrite();
					ioread += beanIozone.getRead();
					ioreread += beanIozone.getReread();
					iorandomRead += beanIozone.getRandomRead();
					iorandomWrite += beanIozone.getRandomWrite();
				}
				sumWeekIozone.setWrite(iowrite);
				sumWeekIozone.setRewrite(iorewrite);
				sumWeekIozone.setRead(ioread);
				sumWeekIozone.setReread(ioreread);
				sumWeekIozone.setRandomRead(iorandomRead);
				sumWeekIozone.setRandomWrite(iorandomWrite);
				
				SumWeekIozoneDao sumWeekIozoneDao = new SumWeekIozoneDao();
				List<SumWeekIozone> sumWeekIozoneFind = sumWeekIozoneDao.findByProperty("uuid", sumWeekIozone.getUuid(), "time", sumWeekIozone.getTime());
			    if(sumWeekIozoneFind.size() > 0){
			    	sumWeekIozoneDao.deleteById((SumWeekIozone)sumWeekIozoneFind.get(0));
			    }
			    sumWeekIozoneDao.save(sumWeekIozone);
			    System.out.println("IOZONE保存");
		    }
		    
		//ping获取
		    System.out.println("PING获取个数:"+pingTestResultWeek.size());
		    if(pingTestResultWeek.size() > 0){
		    	SumWeekPing entityBaidu = new SumWeekPing(null, it.getId(), it.getPlatformName(), startTimeBean, Constants.BAiDU, 0, 0, 0);
				SumWeekPing entity163 = new SumWeekPing(null, it.getId(), it.getPlatformName(), startTimeBean, Constants.N163, 0, 0, 0);
				SumWeekPing entityQQ = new SumWeekPing(null, it.getId(), it.getPlatformName(), startTimeBean, Constants.QQ, 0, 0, 0);
				SumWeekPing entitySina = new SumWeekPing(null, it.getId(), it.getPlatformName(), startTimeBean, Constants.SINA, 0, 0, 0);
				SumWeekPing entitySouhu = new SumWeekPing(null, it.getId(), it.getPlatformName(), startTimeBean, Constants.SOUHU, 0, 0, 0);
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
						SOUHULoss += beanPing.getAvg();
						SOUHULoss += beanPing.getLoss();
						SOUHUCount++;
						break;
					}
				}
				
				SumWeekPingDao sumWeekPingDao = new SumWeekPingDao();
				List<SumWeekPing> sumWeekPingFind = sumWeekPingDao.findByProperty("uuid", entityBaidu.getUuid(), "time", entityBaidu.getTime());
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

	// 获取本周周一
	public static Calendar getWeekDay() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return c;
	}


}
