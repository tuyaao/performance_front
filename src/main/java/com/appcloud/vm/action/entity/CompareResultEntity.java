package com.appcloud.vm.action.entity;

import java.util.ArrayList;
import java.util.Properties;

import com.appcloud.vm.utils.PropertiesUtil;

public class CompareResultEntity {
	
	//反射可以生成get/set方法？
	//写adapter来封装接口或者改前端
	
	private ArrayList<CompareResultAbstractEntity> ResultEntitylist = new ArrayList<CompareResultAbstractEntity>();
	
	public CompareResultEntity(){
		
		Properties p = new PropertiesUtil().getPropertyFileConfiguration("create-instance-conf.properties");
		String string = p.getProperty("CompareResultEntity");
		String[] strings = string.split(",");
		for(String a : strings){
			try {
				ResultEntitylist.add((CompareResultAbstractEntity)Class.forName("com.appcloud.vm.action.entity.CompareResult" + a + "Entity").newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * @param  CurveList
	 * @result 如果isnull为true,则设定list为空
	 * */
	public void SetCurve(){
		for(CompareResultAbstractEntity a : ResultEntitylist){
			a.SetCurve();
		}
	}
	
	/**
	 * @param  CurveList
	 * @result 删除掉单张图里面所有曲线的末尾的0
	 * */ 
	public void cutCurve(){
		for(CompareResultAbstractEntity a : ResultEntitylist){
			a.cutCurveEnd();
		}
	}

	public ArrayList<CompareResultAbstractEntity> getResultEntitylist() {
		return ResultEntitylist;
	}

	public void setResultEntitylist(
			ArrayList<CompareResultAbstractEntity> resultEntitylist) {
		ResultEntitylist = resultEntitylist;
	}

}
