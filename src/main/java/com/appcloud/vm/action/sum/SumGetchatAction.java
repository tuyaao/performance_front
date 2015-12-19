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

import com.appcloud.vm.action.dao.CpuTestResultWeekDao;
import com.appcloud.vm.action.dao.FileIoTestResultWeekDao;
import com.appcloud.vm.action.dao.MemoryTestResultWeekDao;
import com.appcloud.vm.action.dao.OltpTestResultWeekDao;
import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.action.dbentity.CpuTestResultWeek;
import com.appcloud.vm.action.dbentity.FileIoTestResultWeek;
import com.appcloud.vm.action.dbentity.MemoryTestResultWeek;
import com.appcloud.vm.action.dbentity.OltpTestResultWeek;
import com.appcloud.vm.action.entity.CloudPlatformRanking;
import com.appcloud.vm.utils.InitializeListener;
import com.appcloud.vm.utils.StringUtil;
import com.appcloud.vm.utils.ThreadPool;
import com.appcloud.vm.utils.TimeIntervalUtil;
import com.opensymphony.xwork2.ActionSupport;

public class SumGetchatAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SumGetchatAction.class);
	private Integer vmSum = 0;//云主机总个数
	private String upToTime = "upToTime";//截至时间
	private String testUpToTime = "testUpToTime";//事务数测试表的截至时间的那一周的开始
	private String testUpToTimeEnd = "testUpToTimeEnd";//事务数测试表的截至时间的那一周的现在
    private ArrayList<CloudPlatformRanking> cloudPlatformCpuRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformMemRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformReadRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformWriteRankingList = new ArrayList<CloudPlatformRanking>();
    private ArrayList<CloudPlatformRanking> cloudPlatformTransRankingList = new ArrayList<CloudPlatformRanking>();
    
    
    private List<CloudPlatform> cloudPlatformList = InitializeListener.getCloudPlatformList() ;// 云平台集合
	
	private CpuTestResultWeekDao cpuTestResultWeekDao = new CpuTestResultWeekDao();
	private MemoryTestResultWeekDao memoryTestResultWeekDao = new MemoryTestResultWeekDao();
	private FileIoTestResultWeekDao fileIoTestResultWeekDao = new FileIoTestResultWeekDao();
	private OltpTestResultWeekDao oltpTestResultWeekDao = new OltpTestResultWeekDao();
	
	private TimeIntervalUtil timeIntervalUtil = new TimeIntervalUtil();
	private StringUtil stringUtil = new StringUtil();
	
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

		Collections.sort(cloudPlatformCpuRankingList, new SortByCpu());
		Collections.sort(cloudPlatformMemRankingList, new SortByMem());
		Collections.sort(cloudPlatformReadRankingList, new SortByRead());
		Collections.sort(cloudPlatformWriteRankingList, new SortByWrite());
		Collections.sort(cloudPlatformTransRankingList, new SortByTransaction());
		
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
	    logger.error("公花费总时间"+ Long.toString(end-start));
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
		Long sumstart = System.currentTimeMillis();
		ArrayList<FutureTask<CloudPlatformRanking>> FutureTaskList = new ArrayList<FutureTask<CloudPlatformRanking>>();
		for (CloudPlatformRanking cloudPlatform : cloudPlatformRankingList){
			GetChatForEachVmTask task = new GetChatForEachVmTask(cloudPlatform);
	        FutureTask<CloudPlatformRanking> futureTask = new FutureTask<CloudPlatformRanking>(task);
	        ThreadPool.submitThread(futureTask);
	        FutureTaskList.add(futureTask);
	        //cloudPlatform = futureTask.get();
		}
		for (FutureTask<CloudPlatformRanking> futureTask : FutureTaskList ){
			futureTask.get();
		}
		Long sumend = System.currentTimeMillis();
		logger.error("总共花费时间："+Long.toString(sumend-sumstart));
        
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
		
	    @Override
	    public CloudPlatformRanking call() throws Exception {
			start = System.currentTimeMillis();
	    	Float ranking = 0.0f;
	    	//获取cpu数据
	    	CpuTestResultWeek cpuTestResultWeek = cpuTestResultWeekDao.findInstanceById(cloudPlatformRanking.getId());
			cloudPlatformRanking.setTestTime(cpuTestResultWeek.getTestTime());
			ranking = cpuTestResultWeek.getTotalTime();
//			logger.error("cpuTotalTime:"+ranking);
			ranking /= cpuTestResultWeek.getCount();
			cloudPlatformRanking.setCpu(ranking);
			//获取mem数据
			MemoryTestResultWeek memTestResultWeekList = memoryTestResultWeekDao.findInstanceById(cloudPlatformRanking.getId());
			ranking = memTestResultWeekList.getTransferSpeed();
//			logger.error("memgetTransferSpeed:"+ranking);
			ranking /= memTestResultWeekList.getCount();
			cloudPlatformRanking.setMem(ranking);
			//获取flie数据
			List<FileIoTestResultWeek> fileTestResultWeekList = fileIoTestResultWeekDao.findInstanceById(cloudPlatformRanking.getId());
			for (FileIoTestResultWeek fileIoTestResultWeek : fileTestResultWeekList){
				if (fileIoTestResultWeek.getTestMode().equals("RNDRD")){
					ranking = fileIoTestResultWeek.getTransferSpeed();
//					logger.error("fileIoRNDRDgetTransferSpeed:"+ranking);
					ranking /= fileIoTestResultWeek.getCount();
					cloudPlatformRanking.setRndrd(ranking);
				} else if (fileIoTestResultWeek.getTestMode().equals("RNDWR")){
					ranking = fileIoTestResultWeek.getTransferSpeed();
//					logger.error("fileIoRNDWRgetTransferSpeed:"+ranking);
					ranking /= fileIoTestResultWeek.getCount();
					cloudPlatformRanking.setRndwr(ranking);
				}
			}
			//获取mysql数据
			OltpTestResultWeek oltpTestResultWeek = oltpTestResultWeekDao.findInstanceById(cloudPlatformRanking.getId());
			ranking = oltpTestResultWeek.getTransactionFrq();
//			logger.error("OltpTestTransactionFrq:"+ranking);
			ranking /= oltpTestResultWeek.getCount();
			cloudPlatformRanking.setTransaction(ranking);
//			logger.error("id:"+cloudPlatformRanking.getId()+"getRndrd："+cloudPlatformRanking.getRndrd()+"getRndwr："+cloudPlatformRanking.getRndwr()+"getTransaction"+cloudPlatformRanking.getTransaction()+"getCpu"+cloudPlatformRanking.getCpu()+"getMem"+cloudPlatformRanking.getMem());
			Long end = System.currentTimeMillis();
			logger.error("每一个线程花费时间："+Long.toString(end-start));
			System.out.println("cloudPlatformRanking.getCloudPlatformName()"+cloudPlatformRanking.getCloudPlatformName());
			System.out.println("cloudPlatformRanking.getCpu()"+cloudPlatformRanking.getCpu());
			System.out.println("cloudPlatformRanking.getMem()"+cloudPlatformRanking.getMem());
			System.out.println("cloudPlatformRanking.getRndrd()"+cloudPlatformRanking.getRndrd());
			System.out.println("cloudPlatformRanking.getRndwr()"+cloudPlatformRanking.getRndwr());
			System.out.println("cloudPlatformRanking.getTransaction("+cloudPlatformRanking.getTransaction());
			System.out.println("cloudPlatformRanking.getId()"+cloudPlatformRanking.getId());
			System.out.println("cloudPlatformRanking.getTestTime()"+cloudPlatformRanking.getTestTime());
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
	
	

	
}
