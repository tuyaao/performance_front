package com.appcloud.vm.action.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.appcloud.vm.common.CompareResultInstance;
import com.appcloud.vm.common.Constants;

public class CompareResultAbstractEntity {

	protected String subClassName = "";//这么写的原因是，多态调用可以知道属于哪个子类，protected子类访问十分方便，其他类访问需要提供get set函数
	protected Boolean CurveListAllNull = true;
	protected List<CompareResultInstance> CurveList = new ArrayList<CompareResultInstance>();//关于某个测试方面的多条曲线集合
	
	protected void SetCurve(){
		if (CurveListAllNull) CurveList = null;
	}

	public Boolean getCurveListAllNull() {
		return CurveListAllNull;
	}

	public void setCurveListAllNull(Boolean curveListAllNull) {
		CurveListAllNull = curveListAllNull;
	}

	public List<CompareResultInstance> getCurveList() {
		return CurveList;
	}

	public void setCurveList(List<CompareResultInstance> curveList) {
		CurveList = curveList;
	}

	public String getSubClassName() {
		return subClassName;
	}

	public void setSubClassName(String subClassName) {
		this.subClassName = subClassName;
	}
	
	/**
	 * @param  CurveList
	 * @result 删除掉单张图里面所有曲线的末尾的0
	 * */ 
	protected void cutCurveEnd(){
		try{
			if (null == CurveList || CurveList.size() == 0){
				//logger.error("list 为空");
				return;
			}
			//logger.error("不为空");
			for (int i = 0; i < CurveList.size(); i++ ){
				List<Map<String,String>> curve = CurveList.get(i).getCurve();
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
	
}
