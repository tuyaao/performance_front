package com.appcloud.vm.action.sum;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.action.entity.SumRankingAbstractEntity;
import com.appcloud.vm.action.entity.SumRankingCpuEntity;
import com.appcloud.vm.action.entity.SumRankingMemEntity;
import com.appcloud.vm.action.entity.SumRankingOperaFactory;
import com.appcloud.vm.action.entity.SumRankingPing163Entity;
import com.appcloud.vm.action.entity.SumRankingPingBaiduEntity;
import com.appcloud.vm.action.entity.SumRankingPingQQEntity;
import com.appcloud.vm.action.entity.SumRankingPingSinaEntity;
import com.appcloud.vm.action.entity.SumRankingPingSouhuEntity;
import com.appcloud.vm.action.entity.SumRankingRndrdEntity;
import com.appcloud.vm.action.entity.SumRankingRndwrEntity;
import com.appcloud.vm.action.entity.SumRankingTransEntity;
import com.appcloud.vm.common.Constants;
import com.appcloud.vm.common.InitializeListener;
import com.appcloud.vm.common.ThreadPool;
import com.appcloud.vm.utils.StringUtil;
import com.free4lab.monitorproxy.hbasetemp.BeanCpu;
import com.free4lab.monitorproxy.hbasetemp.BeanIozone;
import com.free4lab.monitorproxy.hbasetemp.BeanMem;
import com.free4lab.monitorproxy.hbasetemp.BeanPing;
import com.free4lab.monitorproxy.hbasetemp.BeanTpcc;
import com.free4lab.monitorproxy.restclient.ClientOperFactory;
import com.opensymphony.xwork2.ActionSupport;

public class SumGetchatAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SumGetchatAction.class);
	private String testUpToTime = "testUpToTime";//事务数测试表的截至时间的那一周的开始
	private String testUpToTimeEnd = "testUpToTimeEnd";//事务数测试表的截至时间的那一周的现在
	private SumRankingOperaFactory lists;
    private ArrayList<SumRankingCpuEntity> cloudPlatformCpuRankingList = null;
    private ArrayList<SumRankingMemEntity> cloudPlatformMemRankingList = null;
    private ArrayList<SumRankingRndrdEntity> cloudPlatformReadRankingList = null;
    private ArrayList<SumRankingRndwrEntity> cloudPlatformWriteRankingList = null;
    private ArrayList<SumRankingTransEntity> cloudPlatformTransRankingList = null;
    private ArrayList<SumRankingPingBaiduEntity> cloudPlatformPingBaiduRankingList = null;
    private ArrayList<SumRankingPing163Entity> cloudPlatformPing163RankingList = null;
    private ArrayList<SumRankingPingSinaEntity> cloudPlatformPingSinaRankingList = null;
    private ArrayList<SumRankingPingQQEntity> cloudPlatformPingQQRankingList = null;
    private ArrayList<SumRankingPingSouhuEntity> cloudPlatformPingSouhuRankingList = null;
    
    private List<CloudPlatform> cloudPlatformList = InitializeListener.getCloudPlatformList() ;// 云平台集合
	
	long start = System.currentTimeMillis();
	long end = System.currentTimeMillis();
	
	public String execute() {	
		System.out.println("进入综述首页");	
		start = System.currentTimeMillis();
		
		//本来应该是：公司->虚拟机->硬件cpu达2G->所有指标的表->最近的一条记录>取平均值->排名
		//现在直接获取内存里的数据，因此直接数据库查四个表即可
		try {
			lists = getRankingNew();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		//logger.error("公司->虚拟机->硬件cpu达2G->所有指标的表->最近的一条记录>取平均值->排名 获取性能总表"+ Long.toString(end-start));
		
		lists.sort();
		
		cloudPlatformCpuRankingList = lists.getCloudPlatformCpuRankingList();
	    cloudPlatformMemRankingList = lists.getCloudPlatformMemRankingList();
	    cloudPlatformReadRankingList = lists.getCloudPlatformReadRankingList();
	    cloudPlatformWriteRankingList = lists.getCloudPlatformWriteRankingList();
	    cloudPlatformTransRankingList = lists.getCloudPlatformTransRankingList();
	    cloudPlatformPingBaiduRankingList = lists.getCloudPlatformPingBaiduRankingList();
	    cloudPlatformPing163RankingList = lists.getCloudPlatformPing163RankingList();
	    cloudPlatformPingSinaRankingList = lists.getCloudPlatformPingSinaRankingList();
	    cloudPlatformPingQQRankingList = lists.getCloudPlatformPingQQRankingList();
	    cloudPlatformPingSouhuRankingList = lists.getCloudPlatformPingSouhuRankingList();
		
		testUpToTime = getRankingListLastTime(lists.getCloudPlatformCpuRankingList());//这里假设其每周都有数据，否则应该+7判断，如果晚于今天，则是今天，否则是+7的日子
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
	public String getRankingListLastTime( ArrayList<SumRankingCpuEntity> list ){
		String lastTime = "";
		Timestamp timestamp = list.get(0).getTestTime();
		for (SumRankingCpuEntity entity : list){
			if (entity.getTestTime().getTime() > timestamp.getTime()){
				timestamp = entity.getTestTime();
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
	private SumRankingOperaFactory getRankingNew() throws Exception{
		SumRankingOperaFactory Lists = InitializeListener.getCloudPlatformRankingList();//这里需要保证每次使用都更新，否则就会是上一次的数据
		ArrayList<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>> rucanLists =  new ArrayList<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>>();
		ArrayList<ArrayList<? extends SumRankingAbstractEntity>> cpuList =  new ArrayList<ArrayList<? extends SumRankingAbstractEntity>>();
		ArrayList<ArrayList<? extends SumRankingAbstractEntity>> memList =  new ArrayList<ArrayList<? extends SumRankingAbstractEntity>>();
		ArrayList<ArrayList<? extends SumRankingAbstractEntity>> fileList =  new ArrayList<ArrayList<? extends SumRankingAbstractEntity>>();
		ArrayList<ArrayList<? extends SumRankingAbstractEntity>> transList =  new ArrayList<ArrayList<? extends SumRankingAbstractEntity>>();
		ArrayList<ArrayList<? extends SumRankingAbstractEntity>> pingList =  new ArrayList<ArrayList<? extends SumRankingAbstractEntity>>();
		rucanLists.add(cpuList);
		rucanLists.add(memList);
		rucanLists.add(fileList);
		rucanLists.add(transList);
		rucanLists.add(pingList);
		ArrayList<FutureTask<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>>> FutureTaskList = new ArrayList<FutureTask<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>>>();
		
		//归纳futureTask的入参
		Field[] fs = SumRankingOperaFactory.class.getDeclaredFields();
	       for ( int j = 0 ; j < fs.length ; j++){
	           Field f = fs[j];
	           f.setAccessible( true ); // 设置些属性是可以访问的
	           if(f.getName().contains("Read") || f.getName().contains("Write")){
	        	   ArrayList<SumRankingAbstractEntity> list = (ArrayList<SumRankingAbstractEntity>)f.get(Lists);
	        	   fileList.add(list);
	           }else if(f.getName().contains("Ping")){
	        	   ArrayList<SumRankingAbstractEntity> list = (ArrayList<SumRankingAbstractEntity>)f.get(Lists);
	        	   pingList.add(list);
	           }else if(f.getName().contains("Trans")){
	        	   ArrayList<SumRankingAbstractEntity> list = (ArrayList<SumRankingAbstractEntity>)f.get(Lists);
	        	   transList.add(list);
	           }else if(f.getName().contains("Cpu")){
	        	   ArrayList<SumRankingAbstractEntity> list = (ArrayList<SumRankingAbstractEntity>)f.get(Lists);
	        	   cpuList.add(list);
	           }else{
	        	   ArrayList<SumRankingAbstractEntity> list = (ArrayList<SumRankingAbstractEntity>)f.get(Lists);
	        	   memList.add(list);
	           }
	          }
	       
	      for(ArrayList<ArrayList<? extends SumRankingAbstractEntity>> it : rucanLists){
	           GetChatForEachVmTask task = new GetChatForEachVmTask(it);
		       FutureTask<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>> futureTask = new FutureTask<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>>(task);
		       ThreadPool.submitThread(futureTask);
		       FutureTaskList.add(futureTask);
	      }
		
	      while(true){
	    	  Boolean getAllDone = true;
	    	  for (FutureTask<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>> futureTask : FutureTaskList ){
	  			if(!futureTask.isDone()) getAllDone = false; 
	  		}
	    	  if(getAllDone) break;
	      }
	      
	    //直接在循环里面get会阻塞
//		for (FutureTask<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>> futureTask : FutureTaskList ){
//			futureTask.get();
//		}
        
		return Lists;
	}

	/**
	 * 对一个表的所有需要的主机数据进行查询，并赋值  //这种获取方式太慢了，每个方面的每个公司一次，hebernate有没有可以一次获取所有数据的方式。
	 * */
	class GetChatForEachVmTask implements Callable<ArrayList<ArrayList<? extends SumRankingAbstractEntity>>>{
		public ArrayList<ArrayList<? extends SumRankingAbstractEntity>> lists;
		public GetChatForEachVmTask( ArrayList<ArrayList<? extends SumRankingAbstractEntity>> lists ) {
			this.lists  = lists;
		}
		
	    @Override  //目前就是获得第一周，没有查看开始的时间，然后再结合过去的时间,这个开始时间可能需要设置为每周一0点0分
	    public ArrayList<ArrayList<? extends SumRankingAbstractEntity>> call() throws Exception {
	    	Timestamp timeEnd = new Timestamp(Calendar.getInstance().getTimeInMillis());
	    	Timestamp timeStart = new Timestamp(Calendar.getInstance().getTimeInMillis() - 1000*60*60*24*7); 
//	    	logger.error("开始时间:"+timeStart.getTime());
//	    	logger.error("结束时间:"+timeEnd.getTime());
			start = System.currentTimeMillis();
	    	Float ranking = 0.0f;
	    	
	    	String showEnd = "";
	    	int lth = lists.size();
	    	switch(lth){
	    	case 1://其他
	    		ArrayList<? extends SumRankingAbstractEntity> list  = lists.get(0);
	    		switch(list.get(0).getSubClassName()){
		    	case "SumRankingCpuEntity"://获取cpu数据
		    		showEnd = "SumRankingCpuEntity";
		    		System.out.println("开始任务SumRankingCpuEntity:"+System.currentTimeMillis());
		    		for(int i = 0; i < list.size(); i++){
		    			SumRankingCpuEntity entity = (SumRankingCpuEntity)list.get(i);
		    			if(entity == null){
		    				System.out.println("entity is null");
		    			}
		    			List<BeanCpu> cpuTestResultWeek = (List<BeanCpu>)ClientOperFactory.findByIdTime(BeanCpu.class, entity.getId()+"", timeStart, timeEnd);
		    			entity.setTestTime(timeStart);
						if(cpuTestResultWeek.size() > 0){
							for (BeanCpu beanCpu : cpuTestResultWeek){
								ranking += beanCpu.getTotalTime();
							}
							ranking /= cpuTestResultWeek.size();
						}
//						logger.error("cpuTotalTime:"+ranking);
//						logger.error("cpuTotalTime个数:"+cpuTestResultWeek.size());
						entity.setCpu(ranking);
		    		}
		    		break;
		    	case "SumRankingMemEntity"://获取mem数据
		    		showEnd = "SumRankingMemEntity";
		    		System.out.println("开始任务SumRankingMemEntity:"+System.currentTimeMillis());
		    		for(int i = 0; i < list.size(); i++){
		    			SumRankingMemEntity entity = (SumRankingMemEntity)list.get(i);
		    		ranking = 0.0f;
					List<BeanMem> memTestResultWeekList = (List<BeanMem>)ClientOperFactory.findByIdTime(BeanMem.class, entity.getId()+"", timeStart, timeEnd);
					if(memTestResultWeekList.size() > 0){
						for (BeanMem beanMem : memTestResultWeekList){
							ranking += beanMem.getTransferSpeed();
						}
						ranking /= memTestResultWeekList.size();
					}
//					logger.error("memgetTransferSpeed:"+ranking);
//					logger.error("memgetTransferSpeed个数:"+memTestResultWeekList.size());
					entity.setMem(ranking);
		    		}
		    		break;
		    	case "SumRankingTransEntity"://获取mysql数据
		    		showEnd = "SumRankingTransEntity";
		    		System.out.println("开始任务SumRankingTransEntity:"+System.currentTimeMillis());
		    		for(int i = 0; i < list.size(); i++){
		    			SumRankingTransEntity entity = (SumRankingTransEntity)list.get(i);
		    		ranking = 0.0f;
					List<BeanTpcc> oltpTestResultWeek = (List<BeanTpcc>)ClientOperFactory.findByIdTime(BeanTpcc.class, entity.getId()+"", timeStart, timeEnd);
					if(oltpTestResultWeek.size() > 0){
						for (BeanTpcc beanTpcc : oltpTestResultWeek){
							ranking += beanTpcc.getTpmc();
						}
						ranking /= oltpTestResultWeek.size();
					}
//					logger.error("OltpTestTransactionFrq:"+ranking);
//					logger.error("OltpTestTransactionFrq个数:"+oltpTestResultWeek.size());
//					logger.error("id:"+cloudPlatformRanking.getId()+"getRndrd："+cloudPlatformRanking.getRndrd()+"getRndwr："+cloudPlatformRanking.getRndwr()+"getTransaction"+cloudPlatformRanking.getTransaction()+"getCpu"+cloudPlatformRanking.getCpu()+"getMem"+cloudPlatformRanking.getMem());
					entity.setTransaction(ranking);
		    		}
		    		break;
		    	}
	    		break;
	    	case 2://fileIO
	    		showEnd = "fileIO";
	    		System.out.println("开始任务fileIO:"+System.currentTimeMillis());
	    		ArrayList<? extends SumRankingAbstractEntity> rdlist;//由于这list是一起加进去的，所以认为公司的顺序是一样的
	    		ArrayList<? extends SumRankingAbstractEntity> wrlist;
	    		if(lists.get(0).get(0).getSubClassName().equals("SumRankingRndrdEntity")){
	    			rdlist = lists.get(0);
	    			wrlist = lists.get(1);
	    		}else{
	    			rdlist = lists.get(1);
	    			wrlist = lists.get(0);
	    		}
	    		for(int i = 0; i < rdlist.size(); i++){
	    		SumRankingRndrdEntity rdEntity = (SumRankingRndrdEntity)rdlist.get(i);
	    		SumRankingRndwrEntity wrEntity = (SumRankingRndwrEntity)wrlist.get(i);
				Integer rankingRndwr = 0;
				Integer rankingRndrd = 0;
				List<BeanIozone> fileTestResultWeekList = (List<BeanIozone>)ClientOperFactory.findByIdTime(BeanIozone.class, rdEntity.getId()+"", timeStart, timeEnd);
				if(fileTestResultWeekList.size() > 0){
					for (BeanIozone beanIozone : fileTestResultWeekList){
						rankingRndrd += beanIozone.getRandomRead();
						rankingRndwr += beanIozone.getRandomWrite();
					}
					rankingRndrd /= fileTestResultWeekList.size();
					rankingRndwr /= fileTestResultWeekList.size();
				}
//				logger.error("rankingRndrd:"+rankingRndrd);
//				logger.error("rankingRndrd个数:"+fileTestResultWeekList.size());
				rdEntity.setRndrd(rankingRndrd);
				wrEntity.setRndwr(rankingRndwr);
	    		}
	    		break;
	    	case 5://Ping
	    		showEnd = "Ping";
	    		System.out.println("开始任务Ping:"+System.currentTimeMillis());
	    		ArrayList<? extends SumRankingAbstractEntity> pingBaidulist = new ArrayList<SumRankingAbstractEntity>();//由于这list是一起加进去的，所以认为公司的顺序是一样的
	    		ArrayList<? extends SumRankingAbstractEntity> pingSinalist = new ArrayList<SumRankingAbstractEntity>();
	    		ArrayList<? extends SumRankingAbstractEntity> pingSouhulist = new ArrayList<SumRankingAbstractEntity>();
	    		ArrayList<? extends SumRankingAbstractEntity> pingQQlist = new ArrayList<SumRankingAbstractEntity>();
	    		ArrayList<? extends SumRankingAbstractEntity> ping163list = new ArrayList<SumRankingAbstractEntity>();
	    		for(ArrayList<? extends SumRankingAbstractEntity> it : lists){
	    			switch(it.get(0).getSubClassName()){
		    		case "SumRankingPingSouhuEntity":
		    			pingSouhulist = it;
		    			break;
		    		case "SumRankingPingSinaEntity":
		    			pingSinalist = it;
		    			break;
		    		case "SumRankingPingQQEntity":
		    			pingQQlist = it;
		    			break;
		    		case "SumRankingPing163Entity":
		    			ping163list = it;
		    			break;
		    		case "SumRankingPingBaiduEntity":
		    			pingBaidulist = it;
		    			break;
		    		}
	    		}
	    		for(int i = 0; i < pingBaidulist.size(); i++){
	    			long pingEachStart = System.currentTimeMillis();
	    			SumRankingPingBaiduEntity entityBaidu = (SumRankingPingBaiduEntity)pingBaidulist.get(i);
	    			SumRankingPing163Entity entity163 = (SumRankingPing163Entity)ping163list.get(i);
	    			SumRankingPingQQEntity entityQQ = (SumRankingPingQQEntity)pingQQlist.get(i);
	    			SumRankingPingSinaEntity entitySina = (SumRankingPingSinaEntity)pingSinalist.get(i);
	    			SumRankingPingSouhuEntity entitySouhu = (SumRankingPingSouhuEntity)pingSouhulist.get(i);
	    			float rankingBAiDU = 0.0f;
	    			float rankingN163 = 0.0f;
	    			float rankingQQ = 0.0f;
	    			float rankingSINA = 0.0f;
	    			float rankingSOUHU = 0.0f;
	    			long pingEachStartin = System.currentTimeMillis();
				List<BeanPing> pingTestResultWeek = (List<BeanPing>)ClientOperFactory.findByIdTime(BeanPing.class, entityBaidu.getId()+"", timeStart, timeEnd);
				System.out.println("每一个Ping从dbproxy获取花费时间："+(System.currentTimeMillis() - pingEachStartin)/1000);
				if(pingTestResultWeek.size() > 0){
					for (BeanPing beanPing : pingTestResultWeek){
						switch(beanPing.getDestIp()){
						case Constants.BAiDU:
							rankingBAiDU += beanPing.getAvg();
							break;
						case Constants.N163:
							rankingN163 += beanPing.getAvg();
							break;
						case Constants.QQ:
							rankingQQ += beanPing.getAvg();
							break;
						case Constants.SINA:
							rankingSINA += beanPing.getAvg();
							break;
						case Constants.SOUHU:
							rankingSOUHU += beanPing.getAvg();
							break;
						}
//						logger.error("显示每一个的ping时间:"+beanPing.getAvg());
					}
					rankingBAiDU /= pingTestResultWeek.size()/5;
					rankingN163 /= pingTestResultWeek.size()/5;
					rankingQQ /= pingTestResultWeek.size()/5;
					rankingSINA /= pingTestResultWeek.size()/5;
					rankingSOUHU /= pingTestResultWeek.size()/5;
				}
//				logger.error("pingTestResultWeek:"+ranking);
//				logger.error("pingTestResultWeek个数:"+pingTestResultWeek.size());
				entityBaidu.setPingBaidu(rankingBAiDU);
				entity163.setPing163(rankingN163);
				entityQQ.setPingQQ(rankingQQ);
				entitySina.setPingSina(rankingSINA);
				entitySouhu.setPingSouhu(rankingSOUHU);
				System.out.println("每一个Ping花费时间："+(System.currentTimeMillis() - pingEachStart)/1000);
	    		}
	    		break;
	    	}
	    	
//			System.out.println("cloudPlatformRanking.getCloudPlatformName()"+cloudPlatformRanking.getCloudPlatformName());
//			System.out.println("cloudPlatformRanking.getId()"+cloudPlatformRanking.getId());
//			System.out.println("cloudPlatformRanking.getTestTime()"+cloudPlatformRanking.getTestTime());
//			System.out.println("cloudPlatformRanking.getCpu()"+cloudPlatformRanking.getCpu());
//			System.out.println("cloudPlatformRanking.getMem()"+cloudPlatformRanking.getMem());
//			System.out.println("cloudPlatformRanking.getRndrd()"+cloudPlatformRanking.getRndrd());
//			System.out.println("cloudPlatformRanking.getRndwr()"+cloudPlatformRanking.getRndwr());
//			System.out.println("cloudPlatformRanking.getTransaction()"+cloudPlatformRanking.getTransaction());
//			System.out.println("cloudPlatformRanking.getPing()"+cloudPlatformRanking.getPing());
	    	System.out.println("任务结束"+showEnd+":"+(System.currentTimeMillis()-start)/1000);
	    	return lists;
	    }
	}
	
//	private class SortByCpu implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getCpu() > s2.getCpu()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByMem implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getMem() > s2.getMem()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByRead implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getRndrd() > s2.getRndrd()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByWrite implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getRndwr() > s2.getRndwr()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByTransaction implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getTransaction() > s2.getTransaction()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByPingBaidu implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getPingBaidu() < s2.getPingBaidu()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByPing163 implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getPing163() < s2.getPing163()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByPingQQ implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getPingQQ() < s2.getPingQQ()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByPingSina implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getPingSina() < s2.getPingSina()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
//	
//	private class SortByPingSouhu implements Comparator {
//		 public int compare(Object o1, Object o2) {
//			 CloudPlatformRanking s1 = (CloudPlatformRanking) o1;
//			 CloudPlatformRanking s2 = (CloudPlatformRanking) o2;
//			 if (s1.getPingSouhu() < s2.getPingSouhu()){
//				  return -1;
//			  } else {
//				  return 1;
//			  }
//		 }
//		}
	
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

	public ArrayList<SumRankingCpuEntity> getCloudPlatformCpuRankingList() {
		return cloudPlatformCpuRankingList;
	}

	public void setCloudPlatformCpuRankingList(
			ArrayList<SumRankingCpuEntity> cloudPlatformCpuRankingList) {
		this.cloudPlatformCpuRankingList = cloudPlatformCpuRankingList;
	}

	public ArrayList<SumRankingMemEntity> getCloudPlatformMemRankingList() {
		return cloudPlatformMemRankingList;
	}

	public void setCloudPlatformMemRankingList(
			ArrayList<SumRankingMemEntity> cloudPlatformMemRankingList) {
		this.cloudPlatformMemRankingList = cloudPlatformMemRankingList;
	}

	public ArrayList<SumRankingRndrdEntity> getCloudPlatformReadRankingList() {
		return cloudPlatformReadRankingList;
	}

	public void setCloudPlatformReadRankingList(
			ArrayList<SumRankingRndrdEntity> cloudPlatformReadRankingList) {
		this.cloudPlatformReadRankingList = cloudPlatformReadRankingList;
	}

	public ArrayList<SumRankingRndwrEntity> getCloudPlatformWriteRankingList() {
		return cloudPlatformWriteRankingList;
	}

	public void setCloudPlatformWriteRankingList(
			ArrayList<SumRankingRndwrEntity> cloudPlatformWriteRankingList) {
		this.cloudPlatformWriteRankingList = cloudPlatformWriteRankingList;
	}

	public ArrayList<SumRankingTransEntity> getCloudPlatformTransRankingList() {
		return cloudPlatformTransRankingList;
	}

	public void setCloudPlatformTransRankingList(
			ArrayList<SumRankingTransEntity> cloudPlatformTransRankingList) {
		this.cloudPlatformTransRankingList = cloudPlatformTransRankingList;
	}

	public ArrayList<SumRankingPingBaiduEntity> getCloudPlatformPingBaiduRankingList() {
		return cloudPlatformPingBaiduRankingList;
	}

	public void setCloudPlatformPingBaiduRankingList(
			ArrayList<SumRankingPingBaiduEntity> cloudPlatformPingBaiduRankingList) {
		this.cloudPlatformPingBaiduRankingList = cloudPlatformPingBaiduRankingList;
	}

	public ArrayList<SumRankingPing163Entity> getCloudPlatformPing163RankingList() {
		return cloudPlatformPing163RankingList;
	}

	public void setCloudPlatformPing163RankingList(
			ArrayList<SumRankingPing163Entity> cloudPlatformPing163RankingList) {
		this.cloudPlatformPing163RankingList = cloudPlatformPing163RankingList;
	}

	public ArrayList<SumRankingPingSinaEntity> getCloudPlatformPingSinaRankingList() {
		return cloudPlatformPingSinaRankingList;
	}

	public void setCloudPlatformPingSinaRankingList(
			ArrayList<SumRankingPingSinaEntity> cloudPlatformPingSinaRankingList) {
		this.cloudPlatformPingSinaRankingList = cloudPlatformPingSinaRankingList;
	}

	public ArrayList<SumRankingPingQQEntity> getCloudPlatformPingQQRankingList() {
		return cloudPlatformPingQQRankingList;
	}

	public void setCloudPlatformPingQQRankingList(
			ArrayList<SumRankingPingQQEntity> cloudPlatformPingQQRankingList) {
		this.cloudPlatformPingQQRankingList = cloudPlatformPingQQRankingList;
	}

	public ArrayList<SumRankingPingSouhuEntity> getCloudPlatformPingSouhuRankingList() {
		return cloudPlatformPingSouhuRankingList;
	}

	public void setCloudPlatformPingSouhuRankingList(
			ArrayList<SumRankingPingSouhuEntity> cloudPlatformPingSouhuRankingList) {
		this.cloudPlatformPingSouhuRankingList = cloudPlatformPingSouhuRankingList;
	}

	public SumRankingOperaFactory getLists() {
		return lists;
	}

	public void setLists(SumRankingOperaFactory lists) {
		this.lists = lists;
	}

}
