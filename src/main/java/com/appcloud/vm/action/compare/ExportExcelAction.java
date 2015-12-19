package com.appcloud.vm.action.compare;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

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



public class ExportExcelAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ExportExcelAction.class);
	
	private String instanceselecttxt = "";//选择的虚拟机id集合
	private String hostselecttxt = "";// 选择的指标集合
	private String selectstarttime = "";// 选择的开始时间,月/日/年 时/分/秒
	private String selectendtime = "";// 选择的最后时间,月/日/年 时/分/秒
	
	private CompareResultEntity compareResultEntity = new CompareResultEntity();//所有查询结果的集合
	private String[] instanceSelectArray;// 虚拟机
	private String[] indicatorSelectArray;// 指标
	
	private List<CompareResultInstance> cpu = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> ioSeqrd = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> ioSeqwr = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> ioRndrd = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> ioRndwr = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> mem = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> oltptrans = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> oltpdead = new ArrayList<CompareResultInstance>();
	private List<CompareResultInstance> oltprdwr = new ArrayList<CompareResultInstance>();
	
	private ArrayList<VM48InforEntity> VM48InforList = InitializeListener.getVM48InforList();
	private CompareResultInstanceFactory compareResultInstanceFactory = new CompareResultInstanceFactory(compareResultEntity);
	
	InputStream excelStream ;
	//private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private String fileName;
	
	private WritableWorkbook workbook;
    private String[][] title = {{"Host","Time","TotalTime"},{"Host","Time","TransferSpeed"},{"Host","Time","SEQRD","SEQWR","RNDRD","RNDWR"},{"Host","Time","TransactionFrq","DeadLockFrq","ReadWriteFrq"}};
	private Integer sheetNumber = 0;
	
//	private static int[] a = {30, 28, 37, 36, 38, 35, 31, 32, 33, 34};
//	private static int b = 0;
	
	@Override
	public String execute() throws Exception{
		logger.info("进入导出数据Action");
//		instanceselecttxt = ","+a[b]+",";b++;
//		hostselecttxt = ",0,1,2,3,";
//		selectstarttime = "08/01/2015 00:00:01";
//		selectendtime = "10/01/2015 00:00:01";
		System.out.println("选择虚拟机id:" + instanceselecttxt);
		System.out.println("选择指标:" + hostselecttxt);
		System.out.println("查看开始时间:" + selectstarttime);
		System.out.println("查看结束时间:" + selectendtime);

		
		TimeIntervalUtil timeIntervalUtil = new TimeIntervalUtil();
		IntevalCalendarTimeStamp inteval = timeIntervalUtil.getInteval(selectstarttime, selectendtime );
//		Calendar caSelectTimeStart = inteval.getCaSelectTimeStart();
//		Calendar caSelectTimeEnd = inteval.getCaSelectTimeEnd();
		Timestamp tsSelectTimeEnd = inteval.getTsSelectTimeEnd();
		Timestamp tsSelectTimeStart = inteval.getTsSelectTimeStart();
		
		StringUtil stringUtil = new StringUtil();
		instanceSelectArray = stringUtil.subStartAndEnd(instanceselecttxt).split(",");
		indicatorSelectArray = stringUtil.subStartAndEnd(hostselecttxt).split(",");
//		for (int i = 0; i < indicatorSelectArray.length; i++){
//			System.out.println("指标数组第" + i + "个：" +indicatorSelectArray[i]);
//		}
		
//		System.out.println("indicatorSelectArray.size()" + indicatorSelectArray.length);
//		for (int i = 0; i < instanceSelectArray.length; i++) {
//			System.out.println("虚拟机id:" + instanceSelectArray[i]);
//		}
//		for (int i = 0; i < indicatorSelectArray.length; i++) {
//			System.out.println("类型id:" + indicatorSelectArray[i]);
//		}

		ArrayList<FutureTask<CompareResultInstance>> FutureTaskList = new ArrayList<FutureTask<CompareResultInstance>>();
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
				default:
				}
			}
			
		}
		for (FutureTask<CompareResultInstance> futureTask : FutureTaskList ){
			futureTask.get();
		}
		compareResultEntity.setCpuCurveList(cpu);
		compareResultEntity.setMemoryCurveList(mem);
		compareResultEntity.setFileIoSeqrdCurveList(ioSeqrd);
		compareResultEntity.setFileIoSeqwrCurveList(ioSeqwr);
		compareResultEntity.setFileIoRndrdCurveList(ioRndrd);
		compareResultEntity.setFileIoRndwrCurveList(ioRndwr);
		compareResultEntity.setOltpTransCurveList(oltptrans);
		compareResultEntity.setOltpDeadCurveList(oltpdead);
		compareResultEntity.setOltpRdWtCurveList(oltprdwr);
		
		compareResultEntity.SetCurve();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			workbook = Workbook.createWorkbook(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> nameList = new ArrayList<String>();
		List<String> timeList = new ArrayList<String>();
		List<String> valueList1 = new ArrayList<String>();
		List<String> valueList2 = new ArrayList<String>();
		List<String> valueList3 = new ArrayList<String>();
		List<String> valueList4 = new ArrayList<String>();
		String[] name;
		String[] time;
		String[] value1;
		String[] value2;
		String[] value3;
		String[] value4;
		for(String indicator : indicatorSelectArray){
			switch(Integer.valueOf(indicator).intValue()){
			case 0:
				nameList = new ArrayList<String>();
				timeList = new ArrayList<String>();
				valueList1 = new ArrayList<String>();
				List<CompareResultInstance> cpuCurveList = compareResultEntity.getCpuCurveList();//所有的曲线
				for (int i = 0; null!= cpuCurveList && i < cpuCurveList.size(); i++){ //单条曲线
					List<Map<String,String>> curve = cpuCurveList.get(i).getCurve();//每一条曲线
					String InstanceName = getNameById(cpuCurveList.get(i).getCompanyId(), cpuCurveList.get(i).getInstanceId());
					for (int j = 0; j < curve.size(); j++){
						timeList.add(curve.get(j).get(Constants.CURVEINSTANCEMAPTIME));
						valueList1.add(null == curve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "NA":curve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
						//这个我不能理解为什么null 无法改为"暂无数据"
						//valueList1.add(curve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
						try {
							nameList.add(InstanceName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				name = (String[])nameList.toArray(new String[nameList.size()]);
				time = (String[])timeList.toArray(new String[timeList.size()]);
				value1 = (String[])valueList1.toArray(new String[valueList1.size()]);
				addSheetInWorkBook(workbook, sheetNumber++, "CPU", title[0], name, time, value1 );
				break;
		    case 1:
		    	nameList = new ArrayList<String>();
				timeList = new ArrayList<String>();
				valueList1 = new ArrayList<String>();
				List<CompareResultInstance> memCurveList = compareResultEntity.getMemoryCurveList();//所有的曲线
				for (int i = 0; null != memCurveList && i < memCurveList.size(); i++){ //单条曲线
					List<Map<String,String>> curve = memCurveList.get(i).getCurve();
					String InstanceName = getNameById(memCurveList.get(i).getCompanyId(), memCurveList.get(i).getInstanceId());
					for (int j = 0; j<curve.size(); j++){
						timeList.add(curve.get(j).get(Constants.CURVEINSTANCEMAPTIME));
						valueList1.add(null == curve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "NA" : curve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
						try {
							nameList.add(InstanceName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				name = (String[])nameList.toArray(new String[nameList.size()]);
				time = (String[])timeList.toArray(new String[timeList.size()]);
				value1 = (String[])valueList1.toArray(new String[valueList1.size()]);
				addSheetInWorkBook(workbook, sheetNumber++, "Memory", title[1], name, time, value1 );
		    	break;
		    case 2:
		    	nameList = new ArrayList<String>();
				timeList = new ArrayList<String>();
				valueList1 = new ArrayList<String>();
				valueList2 = new ArrayList<String>();
				valueList3 = new ArrayList<String>();
				valueList4 = new ArrayList<String>();
		    	List<CompareResultInstance> ioSeqrdCurveList = compareResultEntity.getFileIoSeqrdCurveList();//所有的曲线
		    	List<CompareResultInstance> ioSeqwrCurveList = compareResultEntity.getFileIoSeqwrCurveList();//所有的曲线
		    	List<CompareResultInstance> ioRndrdCurveList = compareResultEntity.getFileIoRndrdCurveList();//所有的曲线
		    	List<CompareResultInstance> ioRndwrCurveList = compareResultEntity.getFileIoRndwrCurveList();//所有的曲线
		    	try {
					getSheetColForIo(ioSeqrdCurveList, ioSeqwrCurveList, ioRndrdCurveList, ioRndwrCurveList, nameList, timeList, valueList1, valueList2, valueList3, valueList4);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	
				name = (String[])nameList.toArray(new String[nameList.size()]);
				time = (String[])timeList.toArray(new String[timeList.size()]);
				value1 = (String[])valueList1.toArray(new String[valueList1.size()]);
				value2 = (String[])valueList2.toArray(new String[valueList2.size()]);
				value3 = (String[])valueList3.toArray(new String[valueList3.size()]);
				value4 = (String[])valueList4.toArray(new String[valueList4.size()]);
				addSheetInWorkBook(workbook, sheetNumber++, "FileIO", title[2], name, time, value1, value2, value3, value4 );
		    	break;
		    case 3:
		    	nameList = new ArrayList<String>();
				timeList = new ArrayList<String>();
				valueList1 = new ArrayList<String>();
				valueList2 = new ArrayList<String>();
				valueList3 = new ArrayList<String>();
		    	//目前的数据other不全，所以没有弄，要是想要有，compare必须封装完整数据
		    	List<CompareResultInstance> oltpTransCurveList = compareResultEntity.getOltpTransCurveList();//所有的曲线
		    	List<CompareResultInstance> oltpDeadCurveList = compareResultEntity.getOltpDeadCurveList();//所有的曲线
		    	List<CompareResultInstance> oltpRdWtCurveList = compareResultEntity.getOltpRdWtCurveList();//所有的曲线
		    	try {
					getSheetColForMySql(oltpTransCurveList, oltpDeadCurveList, oltpRdWtCurveList, nameList, timeList, valueList1, valueList2, valueList3);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	
				name = (String[])nameList.toArray(new String[nameList.size()]);
				time = (String[])timeList.toArray(new String[timeList.size()]);
				value1 = (String[])valueList1.toArray(new String[valueList1.size()]);
				value2 = (String[])valueList2.toArray(new String[valueList2.size()]);
				value3 = (String[])valueList3.toArray(new String[valueList3.size()]);
				addSheetInWorkBook(workbook, sheetNumber++, "MySQL", title[3], name, time, value1, value2, value3);
		    	break;
		    default:
			}
		}
		try {
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		excelStream = new ByteArrayInputStream(out.toByteArray());
		Date date = new Date();
		fileName = "PerformanceData_"+new SimpleDateFormat("yyyy-MM-dd").format(date)+".xls";
		try {
			this.setFileName(new String(fileName.getBytes(), "ISO8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private void addSheetInWorkBook(WritableWorkbook workbook, Integer sheetNumber, String sheetName, String[] sheetTitle, String[] ... ontent ){
		WritableSheet sheet = workbook.createSheet(sheetName, sheetNumber);
		for (int i = 0; i < sheetTitle.length; i++){
			try {
				sheet.addCell(new Label(i, 0, sheetTitle[i]));//列，行，值
			}catch(WriteException we){
				we.printStackTrace();
			}
		}
		Integer rowNumber = ontent[0].length;
		for ( int j = 0; j < rowNumber; j++){
			for( int k = 0; k < ontent.length; k++){
				try {
					sheet.addCell(new Label(k, j+1, ontent[k][j]));
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
			
	/**@param 文件io的CurveList:一共四个
	 * @return 输入sheet里面的数据：每列一个List 
	 * @throws Exception */
	private void  getSheetColForIo (List<CompareResultInstance> ioSeqrdCurveList, List<CompareResultInstance> ioSeqwrCurveList, List<CompareResultInstance> ioRndrdCurveList, List<CompareResultInstance> ioRndwrCurveList, 
			List<String> nameList, List<String> timeList, List<String> valueList1, List<String> valueList2, List<String> valueList3, List<String> valueList4) throws Exception{
		for (int i = 0; null != ioSeqrdCurveList && i < ioSeqrdCurveList.size(); i++){ //单条曲线,曲线条数相等，可用一条曲线的数量代替
			List<Map<String,String>> ioSeqrdcurve = ioSeqrdCurveList.get(i).getCurve();
			List<Map<String,String>> ioSeqwrCurve = ioSeqwrCurveList.get(i).getCurve();
			List<Map<String,String>> ioRndrdCurve = ioRndrdCurveList.get(i).getCurve();
			List<Map<String,String>> ioRndwrCurve = ioRndwrCurveList.get(i).getCurve();
			String InstanceName = getNameById(ioSeqrdCurveList.get(i).getCompanyId(), ioSeqrdCurveList.get(i).getInstanceId());
			if (ioSeqrdcurve.size() == ioSeqwrCurve.size() && ioSeqwrCurve.size() == ioRndrdCurve.size() && ioRndrdCurve.size() == ioRndwrCurve.size() ){
				logger.error("文件io 四个指标的单条曲线的点数一致");
			}
			for (int j = 0; j<ioSeqrdcurve.size(); j++){//每条曲线点数相等，可用一条曲线的点数代替
				nameList.add(InstanceName);
				timeList.add(ioSeqrdcurve.get(j).get(Constants.CURVEINSTANCEMAPTIME));
				valueList1.add(null == ioSeqrdcurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "暂无数据" : ioSeqrdcurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
				valueList2.add(null == ioSeqwrCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "暂无数据" : ioSeqwrCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
				valueList3.add(null == ioRndrdCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "暂无数据" : ioRndrdCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
				valueList4.add(null == ioRndwrCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "暂无数据" : ioRndwrCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
			}
		}
		if (null != ioSeqrdCurveList && ioSeqrdCurveList.size() == ioSeqwrCurveList.size() && ioSeqwrCurveList.size() == ioRndrdCurveList.size() && ioRndrdCurveList.size() == ioRndwrCurveList.size() ){
			logger.error("文件io 四个指标曲线条数相等");
		}
		logger.error("valueList1点数： " + valueList1.size());
		logger.error("valueList2点数： " + valueList2.size());
		logger.error("valueList3点数： " + valueList3.size());
		logger.error("valueList4点数： " + valueList4.size());
	}
	
	/**@param mysql的CurveList:一共三个(因为获取数据的action那里other的返回数据不全，这里想要other数据，compareAction需封装完全的数据)
	 * @return 输入sheet里面的数据：每列一个List 
	 * @throws Exception */
	private void  getSheetColForMySql (List<CompareResultInstance> oltpTransCurveList , List<CompareResultInstance> oltpDeadCurveList , List<CompareResultInstance> oltpRdWtCurveList,  
			List<String> nameList, List<String> timeList, List<String> valueList1, List<String> valueList2, List<String> valueList3 ) throws Exception{
		for (int i = 0; null != oltpTransCurveList && i < oltpTransCurveList.size(); i++){ //单条曲线,曲线条数相等，可用一条曲线的数量代替
			List<Map<String,String>> oltpTransCurve = oltpTransCurveList.get(i).getCurve();
			List<Map<String,String>> oltpDeadCurve = oltpDeadCurveList.get(i).getCurve();
			List<Map<String,String>> oltpRdWtCurve = oltpRdWtCurveList.get(i).getCurve();
			String InstanceName = getNameById(oltpTransCurveList.get(i).getCompanyId(), oltpTransCurveList.get(i).getInstanceId());
			if (oltpTransCurve.size() == oltpDeadCurve.size() && oltpDeadCurve.size() == oltpRdWtCurve.size() ){
				logger.error("mysql 此条曲线的所有点的数量一致");
			}
			for (int j = 0; j<oltpTransCurve.size(); j++){//每条曲线点数相等，可用一条曲线的点数代替
				nameList.add(InstanceName);
				timeList.add(oltpTransCurve.get(j).get(Constants.CURVEINSTANCEMAPTIME));
				valueList1.add(null == oltpTransCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "暂无数据" : oltpTransCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
				valueList2.add(null == oltpDeadCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "暂无数据" : oltpDeadCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
				valueList3.add(null == oltpRdWtCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE)? "暂无数据" : oltpRdWtCurve.get(j).get(Constants.CURVEINSTANCEMAPVALUE));
			}
		}
		if (null != oltpTransCurveList && oltpTransCurveList.size() == oltpDeadCurveList.size() && oltpDeadCurveList.size() == oltpRdWtCurveList.size() ){
			logger.error("mysql curves 曲线条数相等");
		}
		logger.error("valueList1点数： " + valueList1.size());
		logger.error("valueList2点数： " + valueList2.size());
		logger.error("valueList3点数： " + valueList3.size());
	}
	
	private String getNameById(Integer companyId, Integer instanceId) throws Exception{
		for ( VM48InforEntity vM48InforEntity : VM48InforList ){
			if ( instanceId.equals(vM48InforEntity.getId()) ){
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(vM48InforEntity.getPlatformName()).append(" [").append(vM48InforEntity.getCpu()).append("G/").append(vM48InforEntity.getMemory()).append("M]");
				String name = stringBuilder.toString();
				return name;
			}
		}
		return "没找到";
	}
	
	public Integer getCompanyIdByInstanceId(Integer id){
		for ( VM48InforEntity vM48InforEntity : VM48InforList ){
			if ( id.equals(vM48InforEntity.getId()) ){
				return vM48InforEntity.getPlatformId();
			}
		}
		return null;
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
			default:
			}
	    	Long end = System.currentTimeMillis();
	    	logger.error("每一次遍历的获取数据时间："+Long.toString(end-start));
	    	return compareResultInstance;
	    }
			
	}
	
	//调研一下当list一直加入空时，为什么想把空加进去加不进去
	public List<String> changeNullToNoDataTxt(List<String> list){
		for ( String v1: list){
			if( null == v1 || null == v1){ 
				logger.error("数据为空");
				v1 = "暂无数据";
			} else{
				logger.error("数据不为空"+v1);
			}
		}
		return list;
	}
	
	public void testCompareResultEntity(CompareResultEntity compareResultEntity){
		//尝试输出cpu曲线内容第一条
		logger.error("获取size: "+compareResultEntity.getCpuCurveList().size());
		List<Map<String, String>> curve = compareResultEntity.getCpuCurveList().get(0).getCurve();
		for (Map<String, String> map: curve){
			logger.error("时间：" + map.get(Constants.CURVEINSTANCEMAPTIME) + "; 值：" + map.get(Constants.CURVEINSTANCEMAPVALUE));
		}
	}
	
	public String getInstanceselecttxt() {
		return instanceselecttxt;
	}


	public void setInstanceselecttxt(String instanceselecttxt) {
		this.instanceselecttxt = instanceselecttxt;
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


	public String getHostselecttxt() {
		return hostselecttxt;
	}


	public void setHostselecttxt(String hostselecttxt) {
		this.hostselecttxt = hostselecttxt;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}


	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
