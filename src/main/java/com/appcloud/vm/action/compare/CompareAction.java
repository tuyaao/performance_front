package com.appcloud.vm.action.compare;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.entity.CompareResultEntity;
import com.appcloud.vm.action.entity.VM48InforEntity;
import com.appcloud.vm.common.CompareResultInstance;
import com.appcloud.vm.common.CompareResultInstanceFactory;
import com.appcloud.vm.common.Constants;
import com.appcloud.vm.common.IntevalCalendarTimeStamp;
import com.appcloud.vm.utils.InitializeListener;
import com.appcloud.vm.utils.StringUtil;
import com.appcloud.vm.utils.ThreadPool;
import com.appcloud.vm.utils.TimeIntervalUtil;
import com.opensymphony.xwork2.ActionSupport;

public class CompareAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CompareAction.class);
	
	private String selectstarttime = "";// 选择的开始时间,月/日/年 时/分/秒
	private String selectendtime = "";// 选择的最后时间,月/日/年 时/分/秒
	private String instanceselecttxt = "";//选择的虚拟机id集合
	private String hostselecttxt = "";// 选择的指标集合
	private String companyIdtxt = "";//公司id
	
	private CompareResultEntity compareResultEntity = new CompareResultEntity();//所有查询结果的集合
	private String[] instanceSelectArray;// 虚拟机
	private String[] indicatorSelectArray;// 指标
	private String[] companyIdSelectArray;// 公司id
	
	private List<CompareResultInstance> cpu = new ArrayList<CompareResultInstance>();//所有虚拟机的cpu值，一个虚拟机一条曲线
	private List<CompareResultInstance> ioSeqrd = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> ioSeqwr = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> ioRndrd = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> ioRndwr = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> mem = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> oltptrans = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> oltpdead = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> oltprdwr = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> pingBaidu = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> ping163 = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> pingQQ = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> pingSina = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> pingSouhu = new ArrayList<CompareResultInstance>();
	
	
	
	private ArrayList<VM48InforEntity> VM48InforList = InitializeListener.getVM48InforList();
	
	public String execute() throws Exception {
		if ( instanceselecttxt.equals("")){
			return SUCCESS;
		}
		Long sumstart = System.currentTimeMillis();
		System.out.println("进入比较");
		System.out.println("选择虚拟机id:" + instanceselecttxt);
		System.out.println("选择指标:" + hostselecttxt);
		System.out.println("公司Id:" + companyIdtxt);
		System.out.println("查看开始时间:" + selectstarttime);
		System.out.println("查看结束时间:" + selectendtime);
	    
		TimeIntervalUtil timeIntervalUtil = new TimeIntervalUtil();
		IntevalCalendarTimeStamp inteval = timeIntervalUtil.getInteval(selectstarttime, selectendtime );
//		Calendar caSelectTimeStart = inteval.getCaSelectTimeStart();
//		Calendar caSelectTimeEnd = inteval.getCaSelectTimeEnd();
		Timestamp tsSelectTimeEnd = inteval.getTsSelectTimeEnd();
		Timestamp tsSelectTimeStart = inteval.getTsSelectTimeStart();
//		logger.error(tsSelectTimeEnd);
//		logger.error(tsSelectTimeStart);

		StringUtil stringUtil = new StringUtil();
		instanceSelectArray = stringUtil.subStartAndEnd(instanceselecttxt).split(",");
		indicatorSelectArray = stringUtil.subStartAndEnd(hostselecttxt).split(",");
		
//		System.out.println("indicatorSelectArray.size()" + indicatorSelectArray.length);
//		for (int i = 0; i < instanceSelectArray.length; i++) {
//			System.out.println("虚拟机id:" + instanceSelectArray[i]);
//		}
//		for (int i = 0; i < indicatorSelectArray.length; i++) {
//			System.out.println("类型id:" + indicatorSelectArray[i]);
//		}
		
		Long start = System.currentTimeMillis();
		ArrayList<FutureTask<CompareResultInstance>> FutureTaskList = new ArrayList<FutureTask<CompareResultInstance>>();
		//根据虚拟机的id查询公司的id,组成arrayList,这样可以防止每次在多线程内部计算公司id，引起混乱
		for (int i = 0; i < instanceSelectArray.length; i++) {
			// 填充多个虚拟机的时间-值
			for (String indicatorSelect : indicatorSelectArray) {
				switch (Integer.valueOf(indicatorSelect)) {
				case 0:
					// 填充一台虚拟机的时间-值
					GetCurveForEachVmTask taskCPU = new GetCurveForEachVmTask(Integer.valueOf(instanceSelectArray[i]), getCompanyIdByInstanceId(Integer.valueOf(instanceSelectArray[i])), tsSelectTimeStart, tsSelectTimeEnd, 0);
			        FutureTask<CompareResultInstance> futureTaskCPU = new FutureTask<CompareResultInstance>(taskCPU);
			        ThreadPool.submitThread(futureTaskCPU);
			        FutureTaskList.add(futureTaskCPU);
					break;
				case 1:
					// 填充一台虚拟机的时间-值
					GetCurveForEachVmTask taskMEM = new GetCurveForEachVmTask(Integer.valueOf(instanceSelectArray[i]), getCompanyIdByInstanceId(Integer.valueOf(instanceSelectArray[i])), tsSelectTimeStart, tsSelectTimeEnd, 1);
			        FutureTask<CompareResultInstance> futureTaskMEM = new FutureTask<CompareResultInstance>(taskMEM);
			        ThreadPool.submitThread(futureTaskMEM);
			        FutureTaskList.add(futureTaskMEM);
					break;
				case 2:
					// 填充一台虚拟机的时间-值
					GetCurveForEachVmTask taskIO = new GetCurveForEachVmTask(Integer.valueOf(instanceSelectArray[i]), getCompanyIdByInstanceId(Integer.valueOf(instanceSelectArray[i])), tsSelectTimeStart, tsSelectTimeEnd, 2);
			        FutureTask<CompareResultInstance> futureTaskIO = new FutureTask<CompareResultInstance>(taskIO);
			        ThreadPool.submitThread(futureTaskIO);
			        FutureTaskList.add(futureTaskIO);
					break;
				case 3:
					// 填充一台虚拟机的时间-值
					GetCurveForEachVmTask taskSQL = new GetCurveForEachVmTask(Integer.valueOf(instanceSelectArray[i]), getCompanyIdByInstanceId(Integer.valueOf(instanceSelectArray[i])), tsSelectTimeStart, tsSelectTimeEnd, 3);
			        FutureTask<CompareResultInstance> futureTaskSQL = new FutureTask<CompareResultInstance>(taskSQL);
			        ThreadPool.submitThread(futureTaskSQL);
			        FutureTaskList.add(futureTaskSQL);
					break;
				case 4:
					// 填充一台虚拟机的时间-值
					GetCurveForEachVmTask taskPing = new GetCurveForEachVmTask(Integer.valueOf(instanceSelectArray[i]), getCompanyIdByInstanceId(Integer.valueOf(instanceSelectArray[i])), tsSelectTimeStart, tsSelectTimeEnd, 4);
			        FutureTask<CompareResultInstance> futureTaskPing = new FutureTask<CompareResultInstance>(taskPing);
			        ThreadPool.submitThread(futureTaskPing);
			        FutureTaskList.add(futureTaskPing);
					break;
				default:
				}
			}
			
		}
		for (FutureTask<CompareResultInstance> futureTask : FutureTaskList ){
			futureTask.get();
		}
		Long end = System.currentTimeMillis();
		logger.error("获得所有数据的时间："+Long.toString(end-start));
		compareResultEntity.setCpuCurveList(cpu);
		compareResultEntity.setMemoryCurveList(mem);
		compareResultEntity.setFileIoSeqrdCurveList(ioSeqrd);
		compareResultEntity.setFileIoSeqwrCurveList(ioSeqwr);
		compareResultEntity.setFileIoRndrdCurveList(ioRndrd);
		compareResultEntity.setFileIoRndwrCurveList(ioRndwr);
		compareResultEntity.setOltpTransCurveList(oltptrans);
		compareResultEntity.setOltpDeadCurveList(oltpdead);
		compareResultEntity.setOltpRdWtCurveList(oltprdwr);
		compareResultEntity.setPingBaiduCurveList(pingBaidu);
		compareResultEntity.setPingSinaCurveList(pingSina);
		compareResultEntity.setPing163CurveList(ping163);
		compareResultEntity.setPingSouhuCurveList(pingSouhu);
		compareResultEntity.setPingQQCurveList(pingQQ);
		
		compareResultEntity.SetCurve();
		cutCompareResultEntityCurveEnd(compareResultEntity);
		Long sumend = System.currentTimeMillis();
		logger.error("总共花费时间："+Long.toString(sumend-sumstart));	
		//Test();
		return SUCCESS;
	}
	
	public Integer getCompanyIdByInstanceId(Integer id){
		for ( VM48InforEntity vM48InforEntity : VM48InforList ){
			if ( id.equals(vM48InforEntity.getId()) ){
				return vM48InforEntity.getPlatformId();
			}
		}
		return null;
	}
	
	public void Test(){
		
		try{
			logger.error("-------------------test----------------------");
			for(int i = 0; i <= compareResultEntity.getCpuCurveList().get(0).getCurve().size(); i++){
				logger.error("cup时间："+ compareResultEntity.getCpuCurveList().get(0).getCurve().get(i).get(Constants.CURVEINSTANCEMAPTIME));
				if (compareResultEntity.getCpuCurveList().get(0).getCurve().get(i).get(Constants.CURVEINSTANCEMAPVALUE) == null){
					logger.error("cup值："+ "为null");
				} else {
					logger.error("cup值："+ compareResultEntity.getCpuCurveList().get(0).getCurve().get(i).get(Constants.CURVEINSTANCEMAPVALUE));
				}
			}
			
			logger.error("mem时间长度："+ compareResultEntity.getMemoryCurveList().size());
			for(int i = 0; i <= compareResultEntity.getMemoryCurveList().get(0).getCurve().size(); i++){
				logger.error("mem时间："+ compareResultEntity.getMemoryCurveList().get(0).getCurve().get(i).get(Constants.CURVEINSTANCEMAPTIME));
				if (compareResultEntity.getMemoryCurveList().get(0).getCurve().get(i).get(Constants.CURVEINSTANCEMAPVALUE) == null){
					logger.error("mem值："+ "为null");
				} else {
					logger.error("mem值："+ compareResultEntity.getMemoryCurveList().get(0).getCurve().get(i).get(Constants.CURVEINSTANCEMAPVALUE));
				}
			}
			
			logger.error("io时间长度："+ compareResultEntity.getFileIoRndrdCurveList().size());
			for(int j = 0; j <= compareResultEntity.getFileIoRndrdCurveList().get(0).getCurve().size(); j++){
				logger.error("io时间："+ compareResultEntity.getFileIoRndrdCurveList().get(0).getCurve().get(j).get(Constants.CURVEINSTANCEMAPTIME));
				if (compareResultEntity.getFileIoRndrdCurveList().get(0).getCurve().get(j).get(Constants.CURVEINSTANCEMAPVALUE) == null){
					logger.error("io值："+ "为null");
				} else {
					logger.error("io值："+ compareResultEntity.getFileIoRndrdCurveList().get(0).getCurve().get(j).get(Constants.CURVEINSTANCEMAPVALUE));
				}
			}
			
			for(int k = 0; k <= compareResultEntity.getOltpRdWtCurveList().get(0).getCurve().size(); k++){
				logger.error("oltp时间："+ compareResultEntity.getOltpRdWtCurveList().get(0).getCurve().get(k).get(Constants.CURVEINSTANCEMAPTIME));
				if (compareResultEntity.getOltpRdWtCurveList().get(0).getCurve().get(k).get(Constants.CURVEINSTANCEMAPVALUE) == null){
					logger.error("oltp值："+ "为null");
				} else {
					logger.error("oltp值："+ compareResultEntity.getOltpRdWtCurveList().get(0).getCurve().get(k).get(Constants.CURVEINSTANCEMAPVALUE));
				}
			}
			
			for(int k = 0; k <= compareResultEntity.getPingBaiduCurveList().get(0).getCurve().size(); k++){
				logger.error("ping时间："+ compareResultEntity.getPingBaiduCurveList().get(0).getCurve().get(k).get(Constants.CURVEINSTANCEMAPTIME));
				if (compareResultEntity.getPingBaiduCurveList().get(0).getCurve().get(k).get(Constants.CURVEINSTANCEMAPVALUE) == null){
					logger.error("ping值："+ "为null");
				} else {
					logger.error("ping值："+ compareResultEntity.getPingBaiduCurveList().get(0).getCurve().get(k).get(Constants.CURVEINSTANCEMAPVALUE));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * 给一个云主机的ID,公司id,开始截止时间,获得一个配置的一条曲线
	 * */
	class GetCurveForEachVmTask implements Callable<CompareResultInstance>{
		private Integer id;
		private Integer comId;
		private Timestamp tsSelectTimeStart;
		private Timestamp tsSelectTimeEnd;
		private Integer type;
		
		public GetCurveForEachVmTask(Integer id, Integer comId, Timestamp tsSelectTimeStart, Timestamp tsSelectTimeEnd, Integer type){
		this.id = id;
		this.comId = comId;
		this.tsSelectTimeStart = tsSelectTimeStart;
		this.tsSelectTimeEnd = tsSelectTimeEnd;
		this.type = type;
		}
		
	    @Override
	    public CompareResultInstance call() throws Exception {
	    	Long start = System.currentTimeMillis();
	    	CompareResultInstanceFactory compareResultInstanceFactory = new CompareResultInstanceFactory(compareResultEntity);//这里写的不好，每次虽然对象不一样，但是里面的值相同，用于setnotnull
	    	CompareResultInstance compareResultInstance = new CompareResultInstance();
	    	switch (type) {
			case 0:
				cpu.add(compareResultInstanceFactory.getCompareResultCpu(id, comId, tsSelectTimeStart, tsSelectTimeEnd));
				break;
			case 1:
				mem.add(compareResultInstanceFactory.getCompareResultMem(id, comId, tsSelectTimeStart, tsSelectTimeEnd));
				break;
			case 2:
				List<CompareResultInstance> compareResultIoList = compareResultInstanceFactory.getCompareResultIo(id, comId, tsSelectTimeStart, tsSelectTimeEnd);
				ioSeqrd.add(compareResultIoList.get(0));
				ioSeqwr.add(compareResultIoList.get(1));
				ioRndrd.add(compareResultIoList.get(2));
				ioRndwr.add(compareResultIoList.get(3));
				break;
			case 3:
				List<CompareResultInstance> compareResultOltpList = compareResultInstanceFactory.getCompareResultOltp(id, comId, tsSelectTimeStart, tsSelectTimeEnd);
				oltptrans.add(compareResultOltpList.get(0));
				oltpdead.add(compareResultOltpList.get(1));
				oltprdwr.add(compareResultOltpList.get(2));
				break;
			case 4:
				List<CompareResultInstance> compareResultpingList = compareResultInstanceFactory.getCompareResultPing(id, comId, tsSelectTimeStart, tsSelectTimeEnd);
				pingBaidu.add(compareResultpingList.get(0));
				ping163.add(compareResultpingList.get(1));
				pingQQ.add(compareResultpingList.get(2));
				pingSina.add(compareResultpingList.get(3));
				pingSouhu.add(compareResultpingList.get(4));
				break;
			default:
			}
	    	Long end = System.currentTimeMillis();
	    	logger.error("每一次遍历的获取数据时间："+Long.toString(end-start));
	    	return compareResultInstance;
	    }
	}
	
	
	/**
	 * @param CompareResultEntity
	 * @result 删除掉里面所有曲线的末尾的0
	 * */ 
	public void cutCompareResultEntityCurveEnd(CompareResultEntity compareResultEntity){
		cutCurveEnd(compareResultEntity.getCpuCurveList());
		cutCurveEnd(compareResultEntity.getMemoryCurveList());
		cutCurveEnd(compareResultEntity.getFileIoRndrdCurveList());
		cutCurveEnd(compareResultEntity.getFileIoRndwrCurveList());
		cutCurveEnd(compareResultEntity.getFileIoSeqrdCurveList());
		cutCurveEnd(compareResultEntity.getFileIoSeqwrCurveList());
		cutCurveEnd(compareResultEntity.getOltpRdWtCurveList());
		cutCurveEnd(compareResultEntity.getOltpTransCurveList());
		cutCurveEnd(compareResultEntity.getOltpDeadCurveList());
		cutCurveEnd(compareResultEntity.getPingBaiduCurveList());
		cutCurveEnd(compareResultEntity.getPing163CurveList());
		cutCurveEnd(compareResultEntity.getPingQQCurveList());
		cutCurveEnd(compareResultEntity.getPingSinaCurveList());
		cutCurveEnd(compareResultEntity.getPingSouhuCurveList());
	}
	
	/**
	 * @param List<CompareResultInstance> list
	 * @result 删除掉单张图里面所有曲线的末尾的0
	 * */ 
	public void cutCurveEnd(List<CompareResultInstance> list){
		try{
			if (null == list || list.size() == 0){
				//logger.error("list 为空");
				return;
			}
			//logger.error("不为空");
			for (int i = 0; i < list.size(); i++ ){
				List<Map<String,String>> curve = list.get(i).getCurve();
				Boolean flag = true;
				Iterator<Map<String,String>> iter = curve.iterator();  
				while(iter.hasNext()){  
				    if( null == iter.next().get(Constants.CURVEINSTANCEMAPVALUE) && flag){  
				        iter.remove();  
				    }  else{
				    	flag = false;
				    }
				}  
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getSelectstarttime() {
		return selectstarttime;
	}

	public void setSelectstarttime(String selectstarttime) {
		this.selectstarttime = selectstarttime;
	}

	public String getSelectendtime() {
		return selectendtime;
	}

	public void setSelectendtime(String selectendtime) {
		this.selectendtime = selectendtime;
	}

	public String getInstanceselecttxt() {
		return instanceselecttxt;
	}

	public void setInstanceselecttxt(String instanceselecttxt) {
		this.instanceselecttxt = instanceselecttxt;
	}

	public String getHostselecttxt() {
		return hostselecttxt;
	}

	public void setHostselecttxt(String hostselecttxt) {
		this.hostselecttxt = hostselecttxt;
	}

	public String getCompanyIdtxt() {
		return companyIdtxt;
	}

	public void setCompanyIdtxt(String companyIdtxt) {
		this.companyIdtxt = companyIdtxt;
	}
	
	public CompareResultEntity getCompareResultEntity() {
		return compareResultEntity;
	}

	public void setCompareResultEntity(CompareResultEntity compareResultEntity) {
		this.compareResultEntity = compareResultEntity;
	}
	
	public List<CompareResultInstance> getPingBaidu() {
		return pingBaidu;
	}

	public void setPingBaidu(List<CompareResultInstance> pingBaidu) {
		this.pingBaidu = pingBaidu;
	}

	public List<CompareResultInstance> getPing163() {
		return ping163;
	}

	public void setPing163(List<CompareResultInstance> ping163) {
		this.ping163 = ping163;
	}

	public List<CompareResultInstance> getPingQQ() {
		return pingQQ;
	}

	public void setPingQQ(List<CompareResultInstance> pingQQ) {
		this.pingQQ = pingQQ;
	}

	public List<CompareResultInstance> getPingSina() {
		return pingSina;
	}

	public void setPingSina(List<CompareResultInstance> pingSina) {
		this.pingSina = pingSina;
	}

	public List<CompareResultInstance> getPingSouhu() {
		return pingSouhu;
	}

	public void setPingSouhu(List<CompareResultInstance> pingSouhu) {
		this.pingSouhu = pingSouhu;
	}

	public static void main(String[] args) throws Exception {

	}

}
