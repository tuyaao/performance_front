package com.appcloud.vm.action.sum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.action.entity.CloudPlatformRanking;
import com.appcloud.vm.common.Constants;
import com.appcloud.vm.utils.InitializeListener;
import com.appcloud.vm.utils.StringUtil;
import com.appcloud.vm.utils.ThreadPool;
import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;
import com.free4lab.monitorproxy.restclient.CpuClient;
import com.free4lab.monitorproxy.restclient.IozoneClient;
import com.free4lab.monitorproxy.restclient.MemClient;
import com.free4lab.monitorproxy.restclient.PingClient;
import com.free4lab.monitorproxy.restclient.TpccClient;
import com.opensymphony.xwork2.ActionSupport;

public class SumGetchatAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SumGetchatAction.class);
	private String testUpToTime = "testUpToTime";//事务数测试表的截至时间的那一周的开始
	private String testUpToTimeEnd = "testUpToTimeEnd";//事务数测试表的截至时间的那一周的现在
    private ArrayList<CloudPlatformRanking> cloudPlatformCpuRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformMemRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformReadRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformWriteRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformTransRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformPingBaiduRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformPing163RankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformPingSinaRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformPingQQRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformPingSouhuRankingList = new ArrayList<CloudPlatformRanking>();
    
    private List<CloudPlatform> cloudPlatformList = InitializeListener.getCloudPlatformList() ;// 云平台集合
	
	private CpuClient cpuTestResultWeekDao = new CpuClient();
	private MemClient memoryTestResultWeekDao = new MemClient();
	private IozoneClient fileIoTestResultWeekDao = new IozoneClient();
	private TpccClient oltpTestResultWeekDao = new TpccClient();
	private PingClient pingTestResultWeekDao = new PingClient();
	
	long start = System.currentTimeMillis();
	long end = System.currentTimeMillis();
	
	public String execute() {	
		System.out.println("进入综述首页");	
		start = System.currentTimeMillis();
		
		//本来应该是：公司->虚拟机->硬件cpu达2G->所有指标的表->最近的一条记录>取平均值->排名
		//现在直接获取内存里的数据，因此直接数据库查四个表即可
		try {
			cloudPlatformCpuRankingList = getRankingNew(cloudPlatformList);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		//logger.error("公司->虚拟机->硬件cpu达2G->所有指标的表->最近的一条记录>取平均值->排名 获取性能总表"+ Long.toString(end-start));
		cloudPlatformMemRankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();
		cloudPlatformReadRankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();   
		cloudPlatformWriteRankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();
		cloudPlatformTransRankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();
		cloudPlatformPingBaiduRankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();
		cloudPlatformPing163RankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();
		cloudPlatformPingSinaRankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();
		cloudPlatformPingQQRankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();
		cloudPlatformPingSouhuRankingList = (ArrayList<CloudPlatformRanking>)cloudPlatformCpuRankingList.clone();
		
		Collections.sort(cloudPlatformCpuRankingList, new SortByCpu());
		Collections.sort(cloudPlatformMemRankingList, new SortByMem());
		Collections.sort(cloudPlatformReadRankingList, new SortByRead());
		Collections.sort(cloudPlatformWriteRankingList, new SortByWrite());
		Collections.sort(cloudPlatformTransRankingList, new SortByTransaction());
		Collections.sort(cloudPlatformPingBaiduRankingList, new SortByPingBaidu());
		Collections.sort(cloudPlatformPing163RankingList, new SortByPing163());
		Collections.sort(cloudPlatformPingSinaRankingList, new SortByPingSina());
		Collections.sort(cloudPlatformPingQQRankingList, new SortByPingQQ());
		Collections.sort(cloudPlatformPingSouhuRankingList, new SortByPingSouhu());
		
//		logger.error("CPU");
//		for(CloudPlatformRanking cloudPlatformRanking:cloudPlatformCpuRankingList){
//			logger.error(cloudPlatformRanking.getCloudPlatformName());
//		}
//		logger.error("Mem");
//		for(CloudPlatformRanking cloudPlatformRanking:cloudPlatformMemRankingList){
//			logger.error(cloudPlatformRanking.getCloudPlatformName());
//		}
//		logger.error("ReadRank");
//		for(CloudPlatformRanking cloudPlatformRanking:cloudPlatformReadRankingList){
//			logger.error(cloudPlatformRanking.getCloudPlatformName());
//		}
//		logger.error("WriteRanking");
//		for(CloudPlatformRanking cloudPlatformRanking:cloudPlatformWriteRankingList){
//			logger.error(cloudPlatformRanking.getCloudPlatformName());
//		}
//		logger.error("TransRanking");
//		for(CloudPlatformRanking cloudPlatformRanking:cloudPlatformTransRankingList){
//			logger.error(cloudPlatformRanking.getCloudPlatformName());
//		}
//		logger.error("Ping");
//		for(CloudPlatformRanking cloudPlatformRanking:cloudPlatformPingRankingList){
//			logger.error(cloudPlatformRanking.getPing());
//			logger.error(cloudPlatformRanking.getCloudPlatformName());
//		}
		testUpToTime = getRankingListLastTime(cloudPlatformCpuRankingList);//这里假设其每周都有数据，否则应该+7判断，如果晚于今天，则是今天，否则是+7的日子
//		logger.error(getRankingListLastTime(cloudPlatformCpuRankingList));
//		logger.error(testUpToTime);
		
		Calendar calendarEnd = StringUtil.String2calYMD(testUpToTime);
		calendarEnd.add(Calendar.DATE, 6);
		if ( calendarEnd.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()){
			testUpToTimeEnd = StringUtil.timestamp2String(StringUtil.calendar2Timestamp(Calendar.getInstance()), 0);
		} else {
			testUpToTimeEnd = StringUtil.timestamp2String(StringUtil.calendar2Timestamp(calendarEnd), 0);
		}
	    end = System.currentTimeMillis();
	    logger.error("综述首页获取图表花费总时间"+ Long.toString(end-start));
		return SUCCESS;
	}

	/**
	 * @param ArrayList<CloudPlatformRanking>集合
	 * @return ArrayList<CloudPlatformRanking>里面测试时间的最大值
	 * */
	private String getRankingListLastTime(ArrayList<CloudPlatformRanking> cloudPlatformRankingList){
		String lastTime = "";
		Timestamp timestamp = cloudPlatformRankingList.get(0).getTestTime();
		for (CloudPlatformRanking cloudPlatformRanking : cloudPlatformRankingList){
			if (cloudPlatformRanking.getTestTime().getTime() > timestamp.getTime()){
				timestamp = cloudPlatformRanking.getTestTime();
			}
		}
		Calendar temp  = StringUtil.timestamp2Calendar(timestamp);
		temp.add(Calendar.DATE, 7);
		timestamp = StringUtil.calendar2Timestamp(temp);
		lastTime = StringUtil.timestamp2String(timestamp, 0);
		return lastTime;
	}
	
	/**
	 * @param List<CloudPlatform>集合
	 * @return
	 * 所有的虚拟机已经在内存中获取到了,只需要查各个指标的数据而已 
	 * CloudPlatformRanking 这里面填充的是云主机的id
	 * ArrayList<CloudPlatformRanking>所有公司的未排序的数据集合
	 * */
	private ArrayList<CloudPlatformRanking> getRankingNew(List<CloudPlatform> cloudPlatformList) throws Exception{
		ArrayList<CloudPlatformRanking> cloudPlatformRankingList = InitializeListener.getCloudPlatformRankingList();//这里需要保证每次使用都更新，否则就会是上一次的数据
//		Long sumstart = System.currentTimeMillis();
		ArrayList<FutureTask<CloudPlatformRanking>> FutureTaskList = new ArrayList<FutureTask<CloudPlatformRanking>>();
		for (CloudPlatformRanking cloudPlatform : cloudPlatformRankingList){
			GetChatForEachVmTask task = new GetChatForEachVmTask(cloudPlatform);
	        FutureTask<CloudPlatformRanking> futureTask = new FutureTask<CloudPlatformRanking>(task);
	        ThreadPool.submitThread(futureTask);
	        FutureTaskList.add(futureTask);
		}
		
		for (FutureTask<CloudPlatformRanking> futureTask : FutureTaskList ){
			futureTask.get();
		}
//		Long sumend = System.currentTimeMillis();
//		logger.error("总共花费时间："+Long.toString(sumend-sumstart));
        
		return cloudPlatformRankingList;
	}

	/**
	 * 
	 * 对一个云主机的所有表进行查找和计算，其实更好的是写sql语句，每一个表查询多个云主机值并赋值
	 * */
	class GetChatForEachVmTask implements Callable<CloudPlatformRanking>{
		public CloudPlatformRanking cloudPlatformRanking;
		public GetChatForEachVmTask(CloudPlatformRanking cloudPlatformRanking){
			this.cloudPlatformRanking  = cloudPlatformRanking;
		}
		
	    @Override  //目前就是获得第一周，没有查看开始的时间，然后再结合过去的时间,这个开始时间可能需要设置为每周一0点0分
	    public CloudPlatformRanking call() throws Exception {
	    	Timestamp timeEnd = new Timestamp(Calendar.getInstance().getTimeInMillis());
	    	Timestamp timeStart = new Timestamp(Calendar.getInstance().getTimeInMillis() - 1000*60*60*24*7); 
//	    	logger.error("开始时间:"+timeStart.getTime());
//	    	logger.error("结束时间:"+timeEnd.getTime());
			start = System.currentTimeMillis();
	    	Float ranking = 0.0f;
	    	
	    	//获取cpu数据
	    	List<BeanCpu> cpuTestResultWeek = cpuTestResultWeekDao.findCpuByIdTime(cloudPlatformRanking.getId()+"", timeStart, timeEnd);
			cloudPlatformRanking.setTestTime(timeStart);
			if(cpuTestResultWeek.size() > 0){
				for (BeanCpu beanCpu : cpuTestResultWeek){
					ranking += beanCpu.getTotalTime();
				}
				ranking /= cpuTestResultWeek.size();
			}
//			logger.error("cpuTotalTime:"+ranking);
//			logger.error("cpuTotalTime个数:"+cpuTestResultWeek.size());
			cloudPlatformRanking.setCpu(ranking);
			
			//获取mem数据
			ranking = 0.0f;
			List<BeanMem> memTestResultWeekList = memoryTestResultWeekDao.findMemByIdTime(cloudPlatformRanking.getId()+"", timeStart, timeEnd);
			if(memTestResultWeekList.size() > 0){
				for (BeanMem beanMem : memTestResultWeekList){
					ranking += beanMem.getTransferSpeed();
				}
				ranking /= memTestResultWeekList.size();
			}
//			logger.error("memgetTransferSpeed:"+ranking);
//			logger.error("memgetTransferSpeed个数:"+memTestResultWeekList.size());
			cloudPlatformRanking.setMem(ranking);
			
			//获取flie数据
			Integer rankingRndrd = 0;
			Integer rankingRndwr = 0;
			List<BeanIozone> fileTestResultWeekList = fileIoTestResultWeekDao.findIoByIdTime(cloudPlatformRanking.getId()+"", timeStart, timeEnd);
			if(fileTestResultWeekList.size() > 0){
				for (BeanIozone beanIozone : fileTestResultWeekList){
					rankingRndrd += beanIozone.getRandomRead();
					rankingRndwr += beanIozone.getRandomWrite();
				}
				rankingRndrd /= fileTestResultWeekList.size();
				rankingRndwr /= fileTestResultWeekList.size();
			}
//			logger.error("rankingRndrd:"+rankingRndrd);
//			logger.error("rankingRndrd个数:"+fileTestResultWeekList.size());
//			logger.error("rankingRndwr:"+rankingRndwr);
//			logger.error("rankingRndwr个数:"+fileTestResultWeekList.size());
			cloudPlatformRanking.setRndrd(rankingRndrd);
			cloudPlatformRanking.setRndwr(rankingRndwr);
			
			//获取mysql数据
			ranking = 0.0f;
			List<BeanTpcc> oltpTestResultWeek = oltpTestResultWeekDao.findTpccByIdTime(cloudPlatformRanking.getId()+"", timeStart, timeEnd);
			if(oltpTestResultWeek.size() > 0){
				for (BeanTpcc beanTpcc : oltpTestResultWeek){
					ranking += beanTpcc.getTpmc();
				}
				ranking /= oltpTestResultWeek.size();
			}
//			logger.error("OltpTestTransactionFrq:"+ranking);
//			logger.error("OltpTestTransactionFrq个数:"+oltpTestResultWeek.size());
//			logger.error("id:"+cloudPlatformRanking.getId()+"getRndrd："+cloudPlatformRanking.getRndrd()+"getRndwr："+cloudPlatformRanking.getRndwr()+"getTransaction"+cloudPlatformRanking.getTransaction()+"getCpu"+cloudPlatformRanking.getCpu()+"getMem"+cloudPlatformRanking.getMem());
			cloudPlatformRanking.setTransaction(ranking);
			
			//获取ping数据
			float rankingBAiDU = 0.0f;
			float rankingN163 = 0.0f;
			float rankingSINA = 0.0f;
			float rankingQQ = 0.0f;
			float rankingSOUHU = 0.0f;
			List<BeanPing> pingTestResultWeek = pingTestResultWeekDao.findPingByIdTime(cloudPlatformRanking.getId()+"", timeStart, timeEnd);
			if(pingTestResultWeek.size() > 0){
				for (BeanPing beanPing : pingTestResultWeek){
					switch(beanPing.getDestIp()){
					case Constants.BAiDU:
						rankingBAiDU += beanPing.getAvg();
						break;
					case Constants.N163:
						rankingN163 += beanPing.getAvg();
						break;
					case Constants.SINA:
						rankingSINA += beanPing.getAvg();
						break;
					case Constants.QQ:
						rankingQQ += beanPing.getAvg();
						break;
					case Constants.SOUHU:
						rankingSOUHU += beanPing.getAvg();
						break;
					}
//					logger.error("显示每一个的ping时间:"+beanPing.getAvg());
				}
				rankingBAiDU /= pingTestResultWeek.size()/5;
				rankingN163 /= pingTestResultWeek.size()/5;
				rankingSINA /= pingTestResultWeek.size()/5;
				rankingQQ /= pingTestResultWeek.size()/5;
				rankingSOUHU /= pingTestResultWeek.size()/5;
			}
//			logger.error("pingTestResultWeek:"+ranking);
//			logger.error("pingTestResultWeek个数:"+pingTestResultWeek.size());
			cloudPlatformRanking.setPingBaidu(rankingBAiDU);
			cloudPlatformRanking.setPing163(rankingN163);
			cloudPlatformRanking.setPingSina(rankingSINA);
			cloudPlatformRanking.setPingQQ(rankingQQ);
			cloudPlatformRanking.setPingSouhu(rankingSOUHU);
			
//			Long end = System.currentTimeMillis();
//			logger.error("每一个线程花费时间："+Long.toString(end-start));
			
//			System.out.println("cloudPlatformRanking.getCloudPlatformName()"+cloudPlatformRanking.getCloudPlatformName());
//			System.out.println("cloudPlatformRanking.getId()"+cloudPlatformRanking.getId());
//			System.out.println("cloudPlatformRanking.getTestTime()"+cloudPlatformRanking.getTestTime());
//			System.out.println("cloudPlatformRanking.getCpu()"+cloudPlatformRanking.getCpu());
//			System.out.println("cloudPlatformRanking.getMem()"+cloudPlatformRanking.getMem());
//			System.out.println("cloudPlatformRanking.getRndrd()"+cloudPlatformRanking.getRndrd());
//			System.out.println("cloudPlatformRanking.getRndwr()"+cloudPlatformRanking.getRndwr());
//			System.out.println("cloudPlatformRanking.getTransaction()"+cloudPlatformRanking.getTransaction());
//			System.out.println("cloudPlatformRanking.getPing()"+cloudPlatformRanking.getPing());
			return cloudPlatformRanking;
	    }
	}
	
	private class SortByCpu implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getCpu() > s2.getCpu()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByMem implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getMem() > s2.getMem()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByRead implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getRndrd() > s2.getRndrd()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByWrite implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getRndwr() > s2.getRndwr()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByTransaction implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getTransaction() > s2.getTransaction()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByPingBaidu implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getPingBaidu() < s2.getPingBaidu()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByPing163 implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getPing163() < s2.getPing163()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByPingQQ implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getPingQQ() < s2.getPingQQ()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByPingSina implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getPingSina() < s2.getPingSina()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	private class SortByPingSouhu implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
			 if (s1.getPingSouhu() < s2.getPingSouhu()){
				  return -1;
			  } else {
				  return 1;
			  }
		 }
		}
	
	public String getTestUpToTime() {
		return testUpToTime;
	}

	public void setTestUpToTime(String testUpToTime) {
		this.testUpToTime = testUpToTime;
	}

	public String getTestUpToTimeEnd() {
		return testUpToTimeEnd;
	}

	public void setTestUpToTimeEnd(String testUpToTimeEnd) {
		this.testUpToTimeEnd = testUpToTimeEnd;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformCpuRankingList() {
		return cloudPlatformCpuRankingList;
	}

	public void setCloudPlatformCpuRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformCpuRankingList) {
		this.cloudPlatformCpuRankingList = cloudPlatformCpuRankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformMemRankingList() {
		return cloudPlatformMemRankingList;
	}

	public void setCloudPlatformMemRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformMemRankingList) {
		this.cloudPlatformMemRankingList = cloudPlatformMemRankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformReadRankingList() {
		return cloudPlatformReadRankingList;
	}

	public void setCloudPlatformReadRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformReadRankingList) {
		this.cloudPlatformReadRankingList = cloudPlatformReadRankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformWriteRankingList() {
		return cloudPlatformWriteRankingList;
	}

	public void setCloudPlatformWriteRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformWriteRankingList) {
		this.cloudPlatformWriteRankingList = cloudPlatformWriteRankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformTransRankingList() {
		return cloudPlatformTransRankingList;
	}

	public void setCloudPlatformTransRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformTransRankingList) {
		this.cloudPlatformTransRankingList = cloudPlatformTransRankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformPingBaiduRankingList() {
		return cloudPlatformPingBaiduRankingList;
	}

	public void setCloudPlatformPingBaiduRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformPingBaiduRankingList) {
		this.cloudPlatformPingBaiduRankingList = cloudPlatformPingBaiduRankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformPing163RankingList() {
		return cloudPlatformPing163RankingList;
	}

	public void setCloudPlatformPing163RankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformPing163RankingList) {
		this.cloudPlatformPing163RankingList = cloudPlatformPing163RankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformPingSinaRankingList() {
		return cloudPlatformPingSinaRankingList;
	}

	public void setCloudPlatformPingSinaRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformPingSinaRankingList) {
		this.cloudPlatformPingSinaRankingList = cloudPlatformPingSinaRankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformPingQQRankingList() {
		return cloudPlatformPingQQRankingList;
	}

	public void setCloudPlatformPingQQRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformPingQQRankingList) {
		this.cloudPlatformPingQQRankingList = cloudPlatformPingQQRankingList;
	}

	public ArrayList<CloudPlatformRanking> getCloudPlatformPingSouhuRankingList() {
		return cloudPlatformPingSouhuRankingList;
	}

	public void setCloudPlatformPingSouhuRankingList(
			ArrayList<CloudPlatformRanking> cloudPlatformPingSouhuRankingList) {
		this.cloudPlatformPingSouhuRankingList = cloudPlatformPingSouhuRankingList;
	}

	
}
