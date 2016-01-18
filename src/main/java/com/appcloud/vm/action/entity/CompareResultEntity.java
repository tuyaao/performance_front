package com.appcloud.vm.action.entity;

import java.util.ArrayList;
import java.util.List;

import com.appcloud.vm.common.CompareResultInstance;

public class CompareResultEntity {
	
	private Boolean cpuCurveListAllNull = true;
	private Boolean fileIoSeqrdCurveListAllNull = true;
	private Boolean fileIoSeqwrCurveListAllNull = true;
	private Boolean fileIoRndrdCurveListAllNull = true;
	private Boolean fileIoRndwrCurveListAllNull = true;
	private Boolean memoryCurveListAllNull = true;
	private Boolean oltpTransCurveListAllNull = true;
	private Boolean oltpDeadCurveListAllNull = true;
	private Boolean oltpRdWtCurveListAllNull = true;
	private Boolean pingBaiduCurveListAllNull = true;
	private Boolean ping163CurveListAllNull = true;
	private Boolean pingQQCurveListAllNull = true;
	private Boolean pingSinaCurveListAllNull = true;
	private Boolean pingSouhuCurveListAllNull = true;
	
	private List<CompareResultInstance> cpuCurveList = new ArrayList<CompareResultInstance>();// cpu曲线集合
	private List<CompareResultInstance> fileIoSeqrdCurveList = new ArrayList<CompareResultInstance>();// fileIo顺序读曲线集合
	private List<CompareResultInstance> fileIoSeqwrCurveList = new ArrayList<CompareResultInstance>();// fileIo顺序写曲线集合
	private List<CompareResultInstance> fileIoRndrdCurveList = new ArrayList<CompareResultInstance>();// fileIo随机读曲线集合
	private List<CompareResultInstance> fileIoRndwrCurveList = new ArrayList<CompareResultInstance>();// fileIo随机写曲线集合
	private List<CompareResultInstance> memoryCurveList = new ArrayList<CompareResultInstance>();// memory曲线集合
	private List<CompareResultInstance> oltpTransCurveList = new ArrayList<CompareResultInstance>();// oltp事务曲线集合
	private List<CompareResultInstance> oltpDeadCurveList = new ArrayList<CompareResultInstance>();// oltp死锁曲线集合
	private List<CompareResultInstance> oltpRdWtCurveList = new ArrayList<CompareResultInstance>();// oltp死锁曲线集合
	private List<CompareResultInstance> pingBaiduCurveList = new ArrayList<CompareResultInstance>();// ping曲线集合
	private List<CompareResultInstance> ping163CurveList = new ArrayList<CompareResultInstance>();// ping曲线集合
	private List<CompareResultInstance> pingQQCurveList = new ArrayList<CompareResultInstance>();// ping曲线集合
	private List<CompareResultInstance> pingSinaCurveList = new ArrayList<CompareResultInstance>();// ping曲线集合
	private List<CompareResultInstance> pingSouhuCurveList = new ArrayList<CompareResultInstance>();// ping曲线集合
	
	public CompareResultEntity(){
		
	}
	
	public void SetCurve(){
		if (cpuCurveListAllNull) cpuCurveList = null;
		if (fileIoSeqrdCurveListAllNull) fileIoSeqrdCurveList = null;
		if (fileIoSeqwrCurveListAllNull) fileIoSeqwrCurveList = null;
		if (fileIoRndrdCurveListAllNull) fileIoRndrdCurveList = null;
		if (fileIoRndwrCurveListAllNull) fileIoRndwrCurveList = null;
		if (memoryCurveListAllNull) memoryCurveList = null;
		if (oltpTransCurveListAllNull) oltpTransCurveList = null;
		if (oltpDeadCurveListAllNull) oltpDeadCurveList = null;
		if (oltpRdWtCurveListAllNull) oltpRdWtCurveList = null;
		if (pingBaiduCurveListAllNull) pingBaiduCurveList = null;
		if (ping163CurveListAllNull) ping163CurveList = null;
		if (pingQQCurveListAllNull) pingQQCurveList = null;
		if (pingSinaCurveListAllNull) pingSinaCurveList = null;
		if (pingSouhuCurveListAllNull) pingSouhuCurveList = null;
	}

	public List<CompareResultInstance> getCpuCurveList() {
		return cpuCurveList;
	}

	public void setCpuCurveList(List<CompareResultInstance> cpuCurveList) {
		this.cpuCurveList = cpuCurveList;
	}

	public List<CompareResultInstance> getFileIoSeqrdCurveList() {
		return fileIoSeqrdCurveList;
	}

	public void setFileIoSeqrdCurveList(
			List<CompareResultInstance> fileIoSeqrdCurveList) {
		this.fileIoSeqrdCurveList = fileIoSeqrdCurveList;
	}

	public List<CompareResultInstance> getFileIoSeqwrCurveList() {
		return fileIoSeqwrCurveList;
	}

	public void setFileIoSeqwrCurveList(
			List<CompareResultInstance> fileIoSeqwrCurveList) {
		this.fileIoSeqwrCurveList = fileIoSeqwrCurveList;
	}

	public List<CompareResultInstance> getFileIoRndrdCurveList() {
		return fileIoRndrdCurveList;
	}

	public void setFileIoRndrdCurveList(
			List<CompareResultInstance> fileIoRndrdCurveList) {
		this.fileIoRndrdCurveList = fileIoRndrdCurveList;
	}

	public List<CompareResultInstance> getFileIoRndwrCurveList() {
		return fileIoRndwrCurveList;
	}

	public void setFileIoRndwrCurveList(
			List<CompareResultInstance> fileIoRndwrCurveList) {
		this.fileIoRndwrCurveList = fileIoRndwrCurveList;
	}

	public List<CompareResultInstance> getMemoryCurveList() {
		return memoryCurveList;
	}

	public void setMemoryCurveList(List<CompareResultInstance> memoryCurveList) {
		this.memoryCurveList = memoryCurveList;
	}

	public List<CompareResultInstance> getOltpTransCurveList() {
		return oltpTransCurveList;
	}

	public void setOltpTransCurveList(List<CompareResultInstance> oltpTransCurveList) {
		this.oltpTransCurveList = oltpTransCurveList;
	}

	public List<CompareResultInstance> getOltpDeadCurveList() {
		return oltpDeadCurveList;
	}

	public void setOltpDeadCurveList(List<CompareResultInstance> oltpDeadCurveList) {
		this.oltpDeadCurveList = oltpDeadCurveList;
	}

	public List<CompareResultInstance> getOltpRdWtCurveList() {
		return oltpRdWtCurveList;
	}

	public void setOltpRdWtCurveList(List<CompareResultInstance> oltpRdWtCurveList) {
		this.oltpRdWtCurveList = oltpRdWtCurveList;
	}

	public Boolean getCpuCurveListAllNull() {
		return cpuCurveListAllNull;
	}

	public void setCpuCurveListAllNull(Boolean cpuCurveListAllNull) {
		this.cpuCurveListAllNull = cpuCurveListAllNull;
	}

	public Boolean getFileIoSeqrdCurveListAllNull() {
		return fileIoSeqrdCurveListAllNull;
	}

	public void setFileIoSeqrdCurveListAllNull(Boolean fileIoSeqrdCurveListAllNull) {
		this.fileIoSeqrdCurveListAllNull = fileIoSeqrdCurveListAllNull;
	}

	public Boolean getFileIoSeqwrCurveListAllNull() {
		return fileIoSeqwrCurveListAllNull;
	}

	public void setFileIoSeqwrCurveListAllNull(Boolean fileIoSeqwrCurveListAllNull) {
		this.fileIoSeqwrCurveListAllNull = fileIoSeqwrCurveListAllNull;
	}

	public Boolean getFileIoRndrdCurveListAllNull() {
		return fileIoRndrdCurveListAllNull;
	}

	public void setFileIoRndrdCurveListAllNull(Boolean fileIoRndrdCurveListAllNull) {
		this.fileIoRndrdCurveListAllNull = fileIoRndrdCurveListAllNull;
	}

	public Boolean getFileIoRndwrCurveListAllNull() {
		return fileIoRndwrCurveListAllNull;
	}

	public void setFileIoRndwrCurveListAllNull(Boolean fileIoRndwrCurveListAllNull) {
		this.fileIoRndwrCurveListAllNull = fileIoRndwrCurveListAllNull;
	}

	public Boolean getMemoryCurveListAllNull() {
		return memoryCurveListAllNull;
	}

	public void setMemoryCurveListAllNull(Boolean memoryCurveListAllNull) {
		this.memoryCurveListAllNull = memoryCurveListAllNull;
	}

	public Boolean getOltpTransCurveListAllNull() {
		return oltpTransCurveListAllNull;
	}

	public void setOltpTransCurveListAllNull(Boolean oltpTransCurveListAllNull) {
		this.oltpTransCurveListAllNull = oltpTransCurveListAllNull;
	}

	public Boolean getOltpDeadCurveListAllNull() {
		return oltpDeadCurveListAllNull;
	}

	public void setOltpDeadCurveListAllNull(Boolean oltpDeadCurveListAllNull) {
		this.oltpDeadCurveListAllNull = oltpDeadCurveListAllNull;
	}

	public Boolean getOltpRdWtCurveListAllNull() {
		return oltpRdWtCurveListAllNull;
	}

	public void setOltpRdWtCurveListAllNull(Boolean oltpRdWtCurveListAllNull) {
		this.oltpRdWtCurveListAllNull = oltpRdWtCurveListAllNull;
	}

	public Boolean getPingBaiduCurveListAllNull() {
		return pingBaiduCurveListAllNull;
	}

	public void setPingBaiduCurveListAllNull(Boolean pingBaiduCurveListAllNull) {
		this.pingBaiduCurveListAllNull = pingBaiduCurveListAllNull;
	}

	public Boolean getPing163CurveListAllNull() {
		return ping163CurveListAllNull;
	}

	public void setPing163CurveListAllNull(Boolean ping163CurveListAllNull) {
		this.ping163CurveListAllNull = ping163CurveListAllNull;
	}

	public Boolean getPingQQCurveListAllNull() {
		return pingQQCurveListAllNull;
	}

	public void setPingQQCurveListAllNull(Boolean pingQQCurveListAllNull) {
		this.pingQQCurveListAllNull = pingQQCurveListAllNull;
	}

	public Boolean getPingSinaCurveListAllNull() {
		return pingSinaCurveListAllNull;
	}

	public void setPingSinaCurveListAllNull(Boolean pingSinaCurveListAllNull) {
		this.pingSinaCurveListAllNull = pingSinaCurveListAllNull;
	}

	public Boolean getPingSouhuCurveListAllNull() {
		return pingSouhuCurveListAllNull;
	}

	public void setPingSouhuCurveListAllNull(Boolean pingSouhuCurveListAllNull) {
		this.pingSouhuCurveListAllNull = pingSouhuCurveListAllNull;
	}

	public List<CompareResultInstance> getPingBaiduCurveList() {
		return pingBaiduCurveList;
	}

	public void setPingBaiduCurveList(List<CompareResultInstance> pingBaiduCurveList) {
		this.pingBaiduCurveList = pingBaiduCurveList;
	}

	public List<CompareResultInstance> getPing163CurveList() {
		return ping163CurveList;
	}

	public void setPing163CurveList(List<CompareResultInstance> ping163CurveList) {
		this.ping163CurveList = ping163CurveList;
	}

	public List<CompareResultInstance> getPingQQCurveList() {
		return pingQQCurveList;
	}

	public void setPingQQCurveList(List<CompareResultInstance> pingQQCurveList) {
		this.pingQQCurveList = pingQQCurveList;
	}

	public List<CompareResultInstance> getPingSinaCurveList() {
		return pingSinaCurveList;
	}

	public void setPingSinaCurveList(List<CompareResultInstance> pingSinaCurveList) {
		this.pingSinaCurveList = pingSinaCurveList;
	}

	public List<CompareResultInstance> getPingSouhuCurveList() {
		return pingSouhuCurveList;
	}

	public void setPingSouhuCurveList(List<CompareResultInstance> pingSouhuCurveList) {
		this.pingSouhuCurveList = pingSouhuCurveList;
	}

}
