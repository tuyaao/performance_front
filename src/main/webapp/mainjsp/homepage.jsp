<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	<title>综述首页 - 云主机性能监测</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/front.css">
    <link rel="stylesheet" href="css/responsive.css">
</head>
<body class="front-body">
	<s:include value="/template/_bootbanner.jsp?index=home"/>
	<div class="front-inner">
		<div class="container">
			<s:iterator value="{'trancharts','cpucharts','memcharts','readcharts','writecharts'}" id='chartsid' status='st'>
				<s:if test="#st.first"><div class="col-md-12 col-xs-12"></s:if>
				<s:else><div class="col-md-6 col-xs-12"></s:else>
					<div class="panel panel-default front-panel">
				    	<div class="panel-heading">
	      					<h3 class="panel-title">
	      						<s:if test="#st.count == 1">云主机性能对比(4核8G) - MySQL事务</s:if>
	         					<s:elseif test="#st.count == 2">云主机性能对比(4核8G) - CPU</s:elseif>
	         					<s:elseif test="#st.count == 3">云主机性能对比(4核8G) - 内存</s:elseif>
	         					<s:elseif test="#st.count == 4">云主机性能对比(4核8G) - 硬盘随机读</s:elseif>
	         					<s:elseif test="#st.count == 5">云主机性能对比(4核8G) - 硬盘随机写</s:elseif>	         				
	         					<span id="time<s:property value='chartsid' />" style="float: right"></span>
	      					</h3>	      		
	   					</div>
	   					<div class="panel-body" style="height: 280px; ">
	   						<div class="front-loading hidden" id="loading<s:property value='chartsid' />" style="margin-top: 90px;">
	    						<div class="front-loading-block"></div>
	    						<div class="front-loading-block"></div>
	    						<div class="front-loading-block"></div>
							</div>
							<div id=<s:property value='chartsid' /> style="height: 250px;"></div>
	   					</div>
					</div>
				</div>
			</s:iterator>
			<div class="col-md-12 col-xs-12">
				<div class="panel panel-default front-panel">
				    <div class="panel-heading">
	      				<h3 class="panel-title">
	         				已接入的云主机服务提供商
	      				</h3>
	   				</div>
	   				<div class="panel-body" style="background: #fafafa;">
	   				   	<div class="front-loading hidden" id="loadingicon">
	    					<div class="front-loading-block"></div>
	    					<div class="front-loading-block"></div>
	    					<div class="front-loading-block"></div>
						</div>
						<div id="comlist" class="row">
						</div>
	   				</div>
	
				</div>
			</div>
			<div class="col-md-12 col-xs-12">
				<div class="panel panel-default front-panel">
				    <div class="panel-heading">
	      				<h3 class="panel-title">
	         				测试指标说明
	      				</h3>
	   				</div>
	   				<div class="panel-body">
						<div class="row">
						<s:iterator value="{'cpu','memory','multi','mysql'}" id='instructionid' status='st'>
							<div class="col-md-6 col-xs-6" style="text-align: center; margin-bottom: 10px;">
								<div class="media-left front-media-left">
									<img src="images/<s:property value='instructionid' />.png" class="img-circle" width="100px" height="100px" border="0" align="middle">
								</div>
								<s:if test="#st.count == 1">
									<div class="media-body front-media-left-body">
										<h4 class="media-heading" style="position: relative">CPU</h4>
										<div class="front-text-break" style="text-align: left;">采用计算100000以内所有的素数的程序，记录花费的时间，时间越短，性能越好。</div>
									</div>
								</s:if>
		         				<s:elseif test="#st.count == 2">
									<div class="media-body front-media-left-body">
										<h4 class="media-heading" style="position: relative">内存</h4>
										<div class="front-text-break" style="text-align: left;">对内存连续读写的测试，内存块大小取8K，测试时间120s，得到结果为每秒传输的数据量。</div>
									</div>
		         				</s:elseif>
		         				<s:elseif test="#st.count == 3">
									<div class="media-body front-media-left-body">
										<h4 class="media-heading" style="position: relative">硬盘I/O</h4>
										<div class="front-text-break" style="text-align: left;">这里采用了四种模式（顺序写，顺序读，随机写，随机读）对IO进行测试，每种测试时间300s，
										得到结果为读取/写入的速度和每秒执行的请求数。</div>
									</div>			         				
		         				</s:elseif>
		         				<s:elseif test="#st.count == 4">
									<div class="media-body front-media-left-body">
										<h4 class="media-heading" style="position: relative">MySQL</h4>
										<div class="front-text-break" style="text-align: left;">此项测试针对MySQL性能，测试表的记录数为2000000，模拟了一个简单的事物处理系统的工作负载，
										得到的结果为每秒处理的事务数，读写请求数以及发生死锁数。</div>
									</div>			         				
		         				</s:elseif>
							</div>
						</s:iterator>
						</div>
	   				</div>
				</div>
			</div>
		</div>
	</div>

<script src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/loading.js"></script>
<script type="text/javascript">
$('#per-toggle').click(function() {
	var $target = $($(this).data('target'))
		
		if (!$target.hasClass('in')) {
			$target.slideDown()
			$target.addClass('in')
		} else {
			$target.slideUp(function() {
				$target.removeClass('in')
			})
		}
})

$('#nav-more').on('click', function() {
	$(this).parent().toggleClass('open')
})

$(function(){
	$("#loadingtrancharts").removeClass("hidden");//显示正在加载动画
	$("#loadingcpucharts").removeClass("hidden");
	$("#loadingmemcharts").removeClass("hidden");
	$("#loadingreadcharts").removeClass("hidden");
	$("#loadingwritecharts").removeClass("hidden");
	$("#loadingicon").removeClass("hidden");
	$.post("homecompanyajax",{},function(data){
		var company = data.cloudPlatformEntityList;
		for(var i=0; i<company.length; i++){
		    var html = '<div class="col-md-4 col-xs-6" style="text-align: center; margin-bottom: 10px;">'
		    			+'<a id='+company[i].id+'_vmsrc href="">'
		    			+'<img src="" id='+company[i].id+'_vmpicture class="img-circle" width="100px" height="100px" border="0" align="middle"></a>'
		    			+'<br>'
		    			+'<span class="bigsize"><a id='+company[i].id+'_vmname class="blueletter" href="">'+company[i].name+'</a></span>'
		    			+'<br>'
		    			+'<span class="bigsize"><a id='+company[i].id+'_vmdomain class="blueletter" href="" target="_blank"></a></span>'
		    			+'</div>';
		    var col = $("#comlist").html();
		    $("#comlist").html(col+html);
			var comId = company[i].id;
		    var imageSrc = "";
		    var domain = "";
		    var ahref = "";
			switch(comId){
			case 2:
				imageSrc = "images/yunhai.png";
				domain = "iaas.free4lab.com";
				ahref ="http://iaas.free4lab.com/";
				break;
			case 3:
				imageSrc = "images/huaweicloud.png";
				domain = "www.hwclouds.com";
				ahref ="http://www.hwclouds.com/";
				break;
			case 4:
 			case 5:
				imageSrc = "images/alicloud.png";
				domain = "www.aliyun.com";
				ahref ="http://www.aliyun.com/";
				break;	 
			case 6:
				imageSrc = "images/ucloud.png";
				domain = "www.ucloud.cn";
				ahref ="http://www.ucloud.cn/";
				break;
			case 11:
				imageSrc = "images/xingyuncloud.png";
				domain = "www.scloudm.com";
				ahref ="http://www.scloudm.com/";
				break;
			default:
				break;
		}

			$("#"+comId+"_vmpicture").attr("src",imageSrc);
			$("#"+comId+"_vmsrc").attr("href","search/vmdetail?id="+comId);
			$("#"+comId+"_vmname").attr("href","search/vmdetail?id="+comId);
			$("#"+comId+"_vmdomain").html(domain);
			$("#"+comId+"_vmdomain").attr("href",ahref);
			$("#loadingicon").addClass("hidden");
		}
	});
	$.post("homechartajax",{},function(data){   
		var rankcpu = data.cloudPlatformCpuRankingList;
		var rankmem = data.cloudPlatformMemRankingList;
		var rankread = data.cloudPlatformReadRankingList;
		var rankwrite = data.cloudPlatformWriteRankingList;
		var ranktrans = data.cloudPlatformTransRankingList;
		var mydata = [];
		var transactionx = [];		
		var transactiondata = [];
		var cpux = [];
		var cpudata = [];
		var memx = [];
		var memdata = [];
		var readx = [];
		var readdata = [];
		var writex = [];
		var writedata = [];
		var time = data.testUpToTime + " - " + data.testUpToTimeEnd;
		$("#timetran").html(time);
		$("#timecpu").html(time);
		$("#timemem").html(time);
		$("#timeread").html(time);
		$("#timewrite").html(time);
		for(var i=0; i<rankcpu.length; i++){
			cpux.push(rankcpu[i].cloudPlatformName);
			cpudata.push(rankcpu[i].cpu);
			memx.push(rankmem[i].cloudPlatformName);
			memdata.push(rankmem[i].mem);
			readx.push(rankread[i].cloudPlatformName);
			readdata.push(rankread[i].rndrd);
			writex.push(rankwrite[i].cloudPlatformName);
			writedata.push(rankwrite[i].rndwr);
			transactionx.push(ranktrans[i].cloudPlatformName);
			transactiondata.push(ranktrans[i].transaction);
		}

		var datatable = [];
		var dataline = {
			name: "事务",
			data: transactiondata
		}
		datatable.push(dataline);
		drawChart("trancharts", "事务", transactionx, datatable, 1, "#058DC7");
		$("#loadingtrancharts").addClass("hidden");
		var datatable = [];
		dataline = {
			name: "CPU",
			data: cpudata	
		}
		datatable.push(dataline);
		drawChart("cpucharts", "cpu", cpux, datatable, 1, "#50B432");
		$("#loadingcpucharts").addClass("hidden");
		var datatable = [];
		dataline = {
			name: "内存",
			data: memdata	
		}
		datatable.push(dataline);
		drawChart("memcharts", "内存", memx, datatable, 1, "#ED561B");
		$("#loadingmemcharts").addClass("hidden");
		var datatable = [];
		dataline = {
			name: "随机读",
			data: readdata	
		}
		datatable.push(dataline);
		drawChart("readcharts", "随机读", readx, datatable, 1, "#DDDF00");
		$("#loadingreadcharts").addClass("hidden");
		var datatable = [];
		dataline = {
			name: "随机写",
			data: writedata	
		}
		datatable.push(dataline);
		drawChart("writecharts", "随机写", writex, datatable, 1, "#24CBE5");
		$("#loadingwritecharts").addClass("hidden");
	});
	
	function drawChart(id, title, xdata, data, xstep, color) {
	  	var hitsChartOptions = configChartOptions(id, title, xdata, data, xstep, color);
	    var chart = new Highcharts.Chart(hitsChartOptions);
	}
	
	function configChartOptions(holderid, title, xdata, datas, xstep, color) {
	    var chartOptions = {
	        chart: {
	            renderTo: holderid,
	            defaultSeriesType: 'column',
	            //margin:[10, 10, 30, 30]
	        },
	        plotOptions: {
	        	column: {
	        		color: color,
	        		borderColor: "",//去边框
	        		shadow: false//去阴影
	        	},
	        },
	        legend: {
	        	enabled: false,
	        	align: 'center',
	        	verticalAlign: 'bottom',
	            layout: 'horizontal',
				y: 15,
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
//		                step:20,
//		                align:'right',

	            }
	        },
	        tooltip: {
	            formatter: function() {
	                    return this.series.name +' 数据:<b>'+this.point.y+'</b><br/>公司:<b>'+xdata[this.point.x]+'</b>';
	            }
	        },
	        scrollbar: {
	            enabled: true
	        },
	        series: datas
	    };
	    return chartOptions;
	  }
})

</script>

</body>
</html>