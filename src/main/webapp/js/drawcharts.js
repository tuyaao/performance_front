/**
 * 云主机性能对比
 */
    
var myname = [];
var dataForExportExcel;
var dataselect = document.getElementsByName("dataselect");//需要查询的指标

var cpuCurveList = null;
var memoryCurveList = null;
var fileIoSeqrdCurveList = null;
var fileIoSeqwrCurveList = null;
var fileIoRndrdCurveList = null;
var fileIoRndwrCurveList = null;
var oltpTransCurveList = null;
var oltpDeadCurveList = null;
var oltpRdWtCurveList = null;
var rankPing = null;
var rankPingBaidu = null;
var rankPing163 = null;
var rankPingSina = null;
var rankPingQQ = null;
var rankPingSouhu = null;

function compareConfirm(id,name,cpu,ram){
	$("#cpu").addClass("hidden");
	$("#memory").addClass("hidden");
	$("#ioseqrd").addClass("hidden");
	$("#ioseqwr").addClass("hidden");
	$("#iorndrd").addClass("hidden");
	$("#iorndwr").addClass("hidden");
	$("#oltptrans").addClass("hidden");
	$("#oltpdead").addClass("hidden");
	$("#oltprdwr").addClass("hidden");
	$("#ping").addClass("hidden");	
	$("#termsnull").addClass("hidden");
	$("#loadingcharts").removeClass("hidden");
    var selectendtime = $("#rest_example_4_end").val();//查询截至时间
    var selectstarttime = $("#rest_example_4_start").val();//查询起始时间
    var timediff = Date.parse(selectendtime)-Date.parse(selectstarttime);
    if(timediff > 691200000){//时间超过8天，毫秒
    	$("#termsnull").removeClass("hidden");
    }else{
    	var dataselectarray = "," ;
    	var localstorage =  localStorage.getItem("726_compare");
    	var json = $.parseJSON(localstorage);//底层对比栏相关数据
    	console.info(json);
    	var idlist=",";//所需查询的主机id列表
    	if(id != null && id != ""){//若id为空则为性能对比页面请求处理，不为空是性能详情页单个id
    		idlist += id+",";  		
    	}else{
    		if(json != null && json != ""){
    			for(var i=0; i<json.length; i++){
    				idlist += json[i].id + ",";  				
    			}
    		}else{
    			$("#loadingcharts").addClass("hidden");
    		}
    	}
    	for(var i=0; i<dataselect.length; i++){
    		if(dataselect[i].checked){
    			dataselectarray += dataselect[i].value+",";			
    		}
    	}

    	$.post("compare/companyaction",{ instanceselecttxt: idlist, selectendtime: selectendtime, selectstarttime: selectstarttime,hostselecttxt: dataselectarray},function(data){
    		dataForExportExcel = data.compareResultEntity;
    		for( var j = 0 ; j < data.compareResultEntity.resultEntitylist.length ; j++){
    			switch( data.compareResultEntity.resultEntitylist[j].subClassName ){
    			case "CompareResultCpuEntity":
    				cpuCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultFileRndrdEntity":
    				fileIoRndrdCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultFileRndwrEntity":
    				fileIoRndwrCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultFileSeqrdEntity":
    				fileIoSeqrdCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultFileSeqwrEntity":
    				fileIoSeqwrCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultMemEntity":
    				memoryCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultOltpDeadEntity":
    				oltpDeadCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultOltpTransEntity":
    				oltpTransCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultOltpRdWtEntity":
    				oltpRdWtCurveList = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultPing163Entity":
    				rankPing163 = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultPingBaiduEntity":
    				rankPing = data.compareResultEntity.resultEntitylist[j];
    				rankPingBaidu = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultPingQQEntity":
    				rankPingQQ = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultPingSinaEntity":
    				rankPingSina = data.compareResultEntity.resultEntitylist[j];
    				break;
    			case "CompareResultPingSouhuEntity":
    				rankPingSouhu = data.compareResultEntity.resultEntitylist[j];
    				break;
    			}
    		    }
    		if(dataselect[0].checked && cpuCurveList != null){
    			$("#cpu").removeClass("hidden");
    			chartdata("cpuhighcharts", "CPU", cpuCurveList);
//    			$("#cpuhighcharts").prev().removeClass("hidden");
    			
    		}
    		if(dataselect[1].checked && memoryCurveList != null){
    			$("#memory").removeClass("hidden");
    			chartdata("memoryhighcharts", "内存", memoryCurveList);
//    			$("#memoryhighcharts").prev().removeClass("hidden");
    			
    		}
    		if(dataselect[2].checked && fileIoSeqrdCurveList != null){
    			$("#ioseqrd").removeClass("hidden");
    			chartdata("ioseqrdhighcharts", "硬盘-顺序读", fileIoSeqrdCurveList);
//    			$("#ioseqrdhighcharts").prev().removeClass("hidden");
    			$("#ioseqwr").removeClass("hidden");
    			chartdata("ioseqwrhighcharts", "硬盘-顺序写", fileIoSeqwrCurveList);
//    			$("#ioseqwrhighcharts").prev().removeClass("hidden");
    			$("#iorndrd").removeClass("hidden");
    			chartdata("iorndrdhighcharts", "硬盘-随机读", fileIoRndrdCurveList);
//    			$("#iorndrdhighcharts").prev().removeClass("hidden");
    			$("#iorndwr").removeClass("hidden");
    			chartdata("iorndwrhighcharts", "硬盘-随机写", fileIoRndwrCurveList);
//    			$("#iorndwrhighcharts").prev().removeClass("hidden");
    			
    		}
    		if(dataselect[3].checked && oltpTransCurveList != null){
    			$("#oltptrans").removeClass("hidden");
    			chartdata("oltptranshighcharts", "MySQL—事务", oltpTransCurveList); 			
//    			$("#oltptranshighcharts").prev().removeClass("hidden");
//    			chartdata("oltpdeadhighcharts", "MySQL—死锁", oltpDeadCurveList);
//    			$("#oltpdeadhighcharts").prev().removeClass("hidden");
//    			$("#oltprdwr").removeClass("hidden");
//    			chartdata("oltprdwrhighcharts", "MySQL—读写", oltpRdWtCurveList);
//    			$("#oltprdwrhighcharts").prev().removeClass("hidden");
    		}
    		
//    		if(dataselect[4].checked){
//    			alert("dataselect[4].checked");
//    		}
//    		if(data.compareResultEntity.pingCurveList != null){
//    			alert("data.compareResultEntity.pingCurveList != null");
//    		}
    		
    		if(dataselect[4].checked && rankPing != null){
    			$("#ping").removeClass("hidden");
    			chartdata("pinghighcharts", "PING", rankPing); 			
    		}
    		$("#loadingcharts").addClass("hidden");
    	});
    }
}

function chartdata(id, name, data){
	var datatable = [];
	var cpuCurveList = data.curveList;
	var legendname;//图例名字
	if(cpuCurveList != null){
		//$("#datanotice").addClass("hidden"););
		var xstep = 0;//每个图例所取的点数，根据所查数据大小决定
		
		for(var i=0; i<cpuCurveList.length; i++){
			var mydata = [];//纵坐标轴数据
			var mxdata = [];//横坐标轴时间
			for(var m=0; m<cpuCurveList[i].curve.length; m++){
				if(cpuCurveList[i].curve[m].CurveInstanceMapValue != null){
					mydata.push(parseFloat(cpuCurveList[i].curve[m].CurveInstanceMapValue));
					mxdata.push(cpuCurveList[i].curve[m].CurveInstanceMapTime);
				}else{
					mydata.push(null);
					mxdata.push(cpuCurveList[i].curve[m].CurveInstanceMapTime);
				}
			}
//			console.info(mydata);
			switch(cpuCurveList[i].companyId){
				case 2:
					legendname = "云海4核8G主机"
					break;
				case 3:
					legendname = "华为云4核8G主机"
					break;
				case 4:
					legendname = "阿里云-北京4核8G主机"
					break;	
				case 5:
					legendname = "阿里云-青岛4核8G主机"
					break;	
				case 6:
					legendname = "UCloud4核8G主机"
					break;
				case 11:
					legendname = "星云4核8G主机"
					break;
				default:
					break;
			}
			var dataline = {
					name: legendname,
					data: mydata
			}
			console.info(dataline);
			datatable.push(dataline);
		}
		if(mxdata.length > 96){
			xstep = 24;
		}else if(mxdata.length > 48){
			xstep = 12;
		}else if(mxdata.length > 24){
			xstep = 6;
		}else if(mxdata.length > 12){
			xstep = 3;
		}else{
			xstep = 1;
		}
//		console.info(mydata);
		
		drawChart(id, name, mxdata, datatable, xstep);
	}

}

function drawChart(id, title, xdata, data, xstep) {
  	var hitsChartOptions = configChartOptions(id, title, xdata, data, xstep);
    var chart = new Highcharts.Chart(hitsChartOptions);
}

function configChartOptions(holderid, title, xdata, datas, xstep) {
    var chartOptions = {
        chart: {
            renderTo: holderid,
            defaultSeriesType: 'line',
            reflow: true,
            margin:[22, 15, 30, 43]
        },
        legend: {
        	align: 'left',
        	verticalAlign: 'top',
        	x: 50,
        	y: -10,
//            layout: 'vertical',
//            style: {
//                bottom:'0px',
//                right:'0px'
//            }
        },
        credits : {
            enabled:false
        },
        title: {
            text: '',
            style: {
                margin: '10px 0 0 0' // center it
            }
        },
        yAxis: {
            min:0,
            title: {
                text: ''
            }
        },
        xAxis: {
        	tickInterval: xstep,
            categories:xdata,
			labels:{
//	                step:20,
//	                align:'right',

            }
        },
        tooltip: {
            formatter: function() {
                    return this.series.name +' 数据:<b>'+this.point.y+'</b><br/>时间:<b>'+xdata[this.point.x]+'</b>';
            }
        },
        scrollbar: {
            enabled: true
        },
        series: datas
    };
    return chartOptions;
  }

function changePingChart() {
	var objS = document.getElementById("pingchartselect");
	var grade = objS.options[objS.selectedIndex].id;
	switch (grade) {
	case "optionbaidu":
		rankPing = rankPingBaidu;
		break;
	case "option163":
		rankPing = rankPing163;
		break;
	case "optionsina":
		rankPing = rankPingSina;
		break;
	case "optionqq":
		rankPing = rankPingQQ;
		break;
	case "optionsouhu":
		rankPing = rankPingSouhu;
		break;
	}
	chartdata("pinghighcharts", "PING", rankPing); 			
}

function exportVmExcel(ele) {
	var hostselecttxt = ",";
	for(var i=0; i<dataselect.length; i++){
		if(dataselect[i].checked){
			hostselecttxt += dataselect[i].value+",";			
		}
	}
	var selectstarttime = $("#rest_example_4_start").val();
	var selectendtime = $("#rest_example_4_end").val();
    var instanceselecttxt ="," + $("#addrr").attr("data-id") + ",";
    
	console.info(hostselecttxt);
	console.info(selectstarttime);
	console.info(selectendtime);
	console.info(instanceselecttxt);
	
//	$(ele).attr("href","compare/vmexcel?compareResultEntity="+dataForExportExcel+"&hostselecttxt="+hostselecttxt);
//	$.post("compare/vmexcel",{ compareResultEntity: dataForExportExcel, hostselecttxt: hostselecttxt},function(data){
//	});

	var temp = document.createElement("form");        
    temp.action = "compare/vmexcel";        
    temp.method = "post";        
    temp.style.display = "none"; 
    var PARAMS={ hostselecttxt: hostselecttxt, selectstarttime: selectstarttime, selectendtime: selectendtime, instanceselecttxt: instanceselecttxt };
    for (var par in PARAMS) {        
        var opt = document.createElement("textarea");
        opt.name = par;        
        opt.value = PARAMS[par];        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);
    temp.submit();        
    return temp;  
}
