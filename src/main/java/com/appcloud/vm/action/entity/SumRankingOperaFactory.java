package com.appcloud.vm.action.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class SumRankingOperaFactory {
	
	private ArrayList<SumRankingCpuEntity> cloudPlatformCpuRankingList = new ArrayList<SumRankingCpuEntity>();
    private ArrayList<SumRankingMemEntity> cloudPlatformMemRankingList = new ArrayList<SumRankingMemEntity>();
    private ArrayList<SumRankingRndrdEntity> cloudPlatformReadRankingList = new ArrayList<SumRankingRndrdEntity>();
    private ArrayList<SumRankingRndwrEntity> cloudPlatformWriteRankingList = new ArrayList<SumRankingRndwrEntity>();
    private ArrayList<SumRankingTransEntity> cloudPlatformTransRankingList = new ArrayList<SumRankingTransEntity>();
    private ArrayList<SumRankingPingBaiduEntity> cloudPlatformPingBaiduRankingList = new ArrayList<SumRankingPingBaiduEntity>();
    private ArrayList<SumRankingPing163Entity> cloudPlatformPing163RankingList = new ArrayList<SumRankingPing163Entity>();
    private ArrayList<SumRankingPingSinaEntity> cloudPlatformPingSinaRankingList = new ArrayList<SumRankingPingSinaEntity>();
    private ArrayList<SumRankingPingQQEntity> cloudPlatformPingQQRankingList = new ArrayList<SumRankingPingQQEntity>();
    private ArrayList<SumRankingPingSouhuEntity> cloudPlatformPingSouhuRankingList = new ArrayList<SumRankingPingSouhuEntity>();
    
    public static void main(String[] args){
    	SumRankingOperaFactory it = new SumRankingOperaFactory();
    }
    
    public SumRankingOperaFactory(){
    	//先这么写，要写在配置文件里面，或者先读取厂商的个数
    	//难道我就没有动态获取所有的属性，然后填充他的方法吗？一定要这么加？
    	for(int i = 0; i < 5; i++ ){
    		SumRankingCpuEntity a = new SumRankingCpuEntity();
    		cloudPlatformCpuRankingList.add(a);
    		SumRankingMemEntity b = new SumRankingMemEntity();
    		cloudPlatformMemRankingList.add(b);
    		SumRankingRndrdEntity c = new SumRankingRndrdEntity();
    		cloudPlatformReadRankingList.add(c);
    		SumRankingRndwrEntity d = new SumRankingRndwrEntity();
    		cloudPlatformWriteRankingList.add(d);
    		SumRankingTransEntity e = new SumRankingTransEntity();
    		cloudPlatformTransRankingList.add(e);
    		SumRankingPingBaiduEntity f = new SumRankingPingBaiduEntity();
    		cloudPlatformPingBaiduRankingList.add(f);
    		SumRankingPing163Entity g = new SumRankingPing163Entity();
    		cloudPlatformPing163RankingList.add(g);
    		SumRankingPingSinaEntity h = new SumRankingPingSinaEntity();
    		cloudPlatformPingSinaRankingList.add(h);
    		SumRankingPingQQEntity j = new SumRankingPingQQEntity();
    		cloudPlatformPingQQRankingList.add(j);
    		SumRankingPingSouhuEntity k = new SumRankingPingSouhuEntity();
    		cloudPlatformPingSouhuRankingList.add(k);
    	}
    }
    
    public void sort(){
    	Collections.sort(cloudPlatformCpuRankingList);
    	Collections.sort(cloudPlatformMemRankingList);
    	Collections.sort(cloudPlatformReadRankingList);
    	Collections.sort(cloudPlatformWriteRankingList);
    	Collections.sort(cloudPlatformTransRankingList);
    	Collections.sort(cloudPlatformPingBaiduRankingList);
    	Collections.sort(cloudPlatformPing163RankingList);
    	Collections.sort(cloudPlatformPingSinaRankingList);
    	Collections.sort(cloudPlatformPingQQRankingList);
    	Collections.sort(cloudPlatformPingSouhuRankingList);
    }
    
    //当值为0的时候，也就是数据不存在的时候，从数组中清除
    public void delVoid(){
    	Iterator<SumRankingCpuEntity> iterCpu = cloudPlatformCpuRankingList.iterator();  
    	Iterator<SumRankingMemEntity> iterMem = cloudPlatformMemRankingList.iterator();  
    	Iterator<SumRankingRndrdEntity> iterRead = cloudPlatformReadRankingList.iterator();  
    	Iterator<SumRankingRndwrEntity> iterWrite = cloudPlatformWriteRankingList.iterator();  
    	Iterator<SumRankingTransEntity> iterTrans = cloudPlatformTransRankingList.iterator();  
    	Iterator<SumRankingPingBaiduEntity> iterPingBaidu = cloudPlatformPingBaiduRankingList.iterator();  
    	Iterator<SumRankingPing163Entity> iterPing163 = cloudPlatformPing163RankingList.iterator();  
    	Iterator<SumRankingPingSinaEntity> iterPingSina = cloudPlatformPingSinaRankingList.iterator();  
    	Iterator<SumRankingPingQQEntity> iterPingQQ = cloudPlatformPingQQRankingList.iterator();  
    	Iterator<SumRankingPingSouhuEntity> iterPingSou = cloudPlatformPingSouhuRankingList.iterator();  
    	while(iterCpu.hasNext()){  
    		SumRankingCpuEntity s = iterCpu.next();  
    	    if(s.getCpu() <= 0){  
    	    	iterCpu.remove();  
    	    }  
    	}  
    	while(iterMem.hasNext()){  
    		SumRankingMemEntity s = iterMem.next();  
    	    if(s.getMem() <= 0){  
    	    	iterMem.remove();  
    	    }  
    	}  
    	while(iterRead.hasNext()){  
    		SumRankingRndrdEntity s = iterRead.next();  
    	    if(s.getRndrd() <= 0){  
    	    	iterRead.remove();  
    	    }  
    	}  
    	while(iterWrite.hasNext()){  
    		SumRankingRndwrEntity s = iterWrite.next();  
    	    if(s.getRndwr() <= 0){  
    	    	iterRead.remove();  
    	    }  
    	}  
    	while(iterTrans.hasNext()){  
    		SumRankingTransEntity s = iterTrans.next();  
    	    if(s.getTransaction() <= 0){  
    	    	iterTrans.remove();  
    	    }  
    	}  
    	while(iterPingBaidu.hasNext()){  
    		SumRankingPingBaiduEntity s = iterPingBaidu.next();  
    	    if(s.getPingBaidu() <= 0){  
    	    	iterPingBaidu.remove();  
    	    }  
    	}  
    	while(iterPing163.hasNext()){  
    		SumRankingPing163Entity s = iterPing163.next();  
    	    if(s.getPing163() <= 0){  
    	    	iterPing163.remove();  
    	    }  
    	}  
    	while(iterPingSina.hasNext()){  
    		SumRankingPingSinaEntity s = iterPingSina.next();  
    	    if(s.getPingSina() <= 0){  
    	    	iterPingSina.remove();  
    	    }  
    	}  
    	while(iterPingQQ.hasNext()){  
    		SumRankingPingQQEntity s = iterPingQQ.next();  
    	    if(s.getPingQQ() <= 0){  
    	    	iterPingQQ.remove();  
    	    }  
    	} 
    	while(iterPingQQ.hasNext()){  
    		SumRankingPingSouhuEntity s = iterPingSou.next();  
    	    if(s.getPingSouhu() <= 0){  
    	    	iterPingSou.remove();  
    	    }  
    	}
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
    
}
