<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>综述首页 - 云主机性能监测</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/front.css">
<link rel="stylesheet" href="css/responsive.css">
</head>
<body class="front-body">
	<s:include value="/template/_bootbanner.jsp?index=home" />
	<div class="front-inner">
		<div class="container">
			<s:iterator
				value="{'cpucharts','memcharts','readcharts','writecharts','trancharts','pingcharts'}"
				id='chartsid' status='st'>
				<!-- <div class="col-md-12 col-xs-12"> -->
				<s:if test="#st.first">
					<div class="col-md-6 col-xs-12">
				</s:if>
				<s:else>
					<div class="col-md-6 col-xs-12">
				</s:else>
				<div class="panel panel-default front-panel">
					<div class="panel-heading">
						<s:if test="#st.count == 1">
							<h3 class="panel-title" style="font-weight: bold;">云主机性能对比(4核8G) - CPU</h3>
						</s:if>
						<s:elseif test="#st.count == 2">
							<h3 class="panel-title" style="font-weight: bold;">云主机性能对比(4核8G) - 内存</h3>
						</s:elseif>
						<s:elseif test="#st.count == 3">
							<h3 class="panel-title" style="font-weight: bold;">云主机性能对比(4核8G) - 硬盘随机读</h3>
						</s:elseif>
						<s:elseif test="#st.count == 4">
							<h3 class="panel-title" style="font-weight: bold;">云主机性能对比(4核8G) - 硬盘随机写U</h3>
						</s:elseif>
						<s:elseif test="#st.count == 5">
							<h3 class="panel-title" style="font-weight: bold;">云主机性能对比(4核8G)
								- MySQL事务</h3>
						</s:elseif>
						<s:elseif test="#st.count == 6">
							<select id="pingchartselect" class="panel-title"
								onchange="changePingChart()"
								style="font-weight: bold; border: 0; border: none;" name="cars">
								<option id="optionbaidu" class="panel-title"
									style="font-weight: bold;" value="baiduPing">云主机性能对比(4核8G)
									- PINGBaidu</option>
								<option id="option163" class="panel-title"
									style="font-weight: bold;" value="163Ping">云主机性能对比(4核8G)
									- PING163</option>
								<option id="optionsina" class="panel-title"
									style="font-weight: bold;" value="sinaPing">云主机性能对比(4核8G)
									- PINGSina</option>
								<option id="optionqq" class="panel-title"
									style="font-weight: bold;" value="qqPing">云主机性能对比(4核8G)
									- PINGQQ</option>
								<option id="optionsouhu" class="panel-title"
									style="font-weight: bold;" value="souhuPing">云主机性能对比(4核8G)
									- PINGSouhu</option>
							</select>
						</s:elseif>
						<span id="time<s:property value='chartsid' />"
							style="float: right"></span>
					</div>
					<div class="panel-body" style="height: 280px;">
						<div class="front-loading hidden"
							id="loading<s:property value='chartsid' />"
							style="margin-top: 90px;">
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
					<h3 class="panel-title" style="font-weight: bold;">已接入的云主机服务提供商</h3>
				</div>
				<div class="panel-body" style="background: #fafafa;">
					<div class="front-loading hidden" id="loadingicon">
						<div class="front-loading-block"></div>
						<div class="front-loading-block"></div>
						<div class="front-loading-block"></div>
					</div>
					<div id="comlist" class="row"></div>
				</div>

			</div>
		</div>
		<div class="col-md-12 col-xs-12">
			<div class="panel panel-default front-panel">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold;">测试方法&指标</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<s:iterator value="{'cpu','memory','multi','mysql','mysql'}"
							id='instructionid' status='st'>
							<div class="col-md-6 col-xs-6"
								style="text-align: center; margin-bottom: 10px;">
								<div class="media-left front-media-left">
									<img src="images/<s:property value='instructionid' />.png"
										class="img-circle" width="100px" height="100px" border="0"
										align="middle">
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
								<s:elseif test="#st.count == 5">
									<div class="media-body front-media-left-body">
										<h4 class="media-heading" style="position: relative">PING</h4>
										<div class="front-text-break" style="text-align: left;">此项测试针对网络性能，测试机对5个网站ping,计算得到结果的平均往返时延。</div>
									</div>
								</s:elseif>
							</div>
						</s:iterator>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-12 col-xs-12" style="width: 50%">
			<div class="panel panel-default front-panel">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold;">测评机构介绍</h3>
				</div>
				<div class="panel-body,">
					<div class="row">
						<div class="panel-heading"
							style="text-align: left; padding: 0px 30px 20px 30px">
							<br>北京邮电大学 网络与交换技术国家重点实验室 交换与智能控制研究中心 "自邮之翼"项目组。<br /> <br>电子邮箱：contactus@free4lab.com<br />
							<br>地址：北京市海淀区西土城路10号北京邮电大学新科研楼五层。<br />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-12 col-xs-12" style="width: 50%">
			<div class="panel panel-default front-panel">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-weight: bold;">免责声明</h3>
				</div>
				<div class="panel-body,">
					<div class="row">
						<div class="panel-heading"
							style="text-align: left; padding: 10px 30px 10px 30px">
							项目设立之初，我们把自己的目标定位为有独立性、普适性、公正性的第三方云主机测评项目。我们在较短的时间内建立了比较完善的测试构架，采用业界权威的测评工具，对CPU、内存、硬盘I/O、MySQL、网络进行测试。
							在以后的一段时间内，我们还将引入更多的测评角度，进一步提高云主机测试的全面性。我们深知项目的发展离不开用户的关注与认可，
							我们会终将秉承以客观事实的态度，公平、公正的原则，积极进取的服务理念，为广大用户提供更为全面、优质、公正的测评服务。</div>
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
		var rankcpu = null;
		var rankmem = null;
		var rankread = null;
		var rankwrite = null;
		var ranktrans = null;
		var rankPing = null;
		var rankPingBaidu = null;
		var rankPing163 = null;
		var rankPingSina = null;
		var rankPingQQ = null;
		var rankPingSouhu = null;

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

		$(function() {
			$("#loadingtrancharts").removeClass("hidden");//显示正在加载动画
			$("#loadingcpucharts").removeClass("hidden");
			$("#loadingmemcharts").removeClass("hidden");
			$("#loadingreadcharts").removeClass("hidden");
			$("#loadingwritecharts").removeClass("hidden");
			$("#loadingpingcharts").removeClass("hidden");
			$("#loadingicon").removeClass("hidden");
			$
					.post(
							"homecompanyajax",
							{},
							function(data) {
								var company = data.cloudPlatformEntityList;
								for ( var i = 0; i < company.length; i++) {
									var html = '<div class="col-md-4 col-xs-6" style="text-align: center; margin-bottom: 10px;">'
											+ '<a id='+company[i].id+'_vmsrc href="">'
											+ '<img src="" id='+company[i].id+'_vmpicture class="img-circle" width="100px" height="100px" border="0" align="middle"></a>'
											+ '<br>'
											+ '<span class="bigsize"><a id='+company[i].id+'_vmname class="blueletter" href="">'
											+ company[i].name
											+ '</a></span>'
											+ '<br>'
											+ '<span class="bigsize"><a id='+company[i].id+'_vmdomain class="blueletter" href="" target="_blank"></a></span>'
											+ '</div>';
									var col = $("#comlist").html();
									$("#comlist").html(col + html);
									var comId = company[i].id;
									var imageSrc = "";
									var domain = "";
									var ahref = "";
									switch (comId) {
									case 2:
										imageSrc = "images/yunhai.png";
										domain = "iaas.free4lab.com";
										ahref = "http://iaas.free4lab.com/";
										break;
									case 3:
										imageSrc = "images/huaweicloud.png";
										domain = "www.hwclouds.com";
										ahref = "http://www.hwclouds.com/";
										break;
									case 4:
									case 5:
										imageSrc = "images/alicloud.png";
										domain = "www.aliyun.com";
										ahref = "http://www.aliyun.com/";
										break;
									case 6:
										imageSrc = "images/ucloud.png";
										domain = "www.ucloud.cn";
										ahref = "http://www.ucloud.cn/";
										break;
									case 11:
										imageSrc = "images/xingyuncloud.png";
										domain = "www.scloudm.com";
										ahref = "http://www.scloudm.com/";
										break;
									default:
										break;
									}

									$("#" + comId + "_vmpicture").attr("src",
											imageSrc);
									$("#" + comId + "_vmsrc").attr("href",
											"search/vmdetail?id=" + comId);
									$("#" + comId + "_vmname").attr("href",
											"search/vmdetail?id=" + comId);
									$("#" + comId + "_vmdomain").html(domain);
									$("#" + comId + "_vmdomain").attr("href",
											ahref);
									$("#loadingicon").addClass("hidden");
								}
							});
			$.post("homechartajax", {},
					function(data) {
						rankcpu = data.cloudPlatformCpuRankingList;
						rankmem = data.cloudPlatformMemRankingList;
						rankread = data.cloudPlatformReadRankingList;
						rankwrite = data.cloudPlatformWriteRankingList;
						ranktrans = data.cloudPlatformTransRankingList;
						rankPing = data.cloudPlatformPingBaiduRankingList;
						rankPingBaidu = data.cloudPlatformPingBaiduRankingList;
						rankPing163 = data.cloudPlatformPing163RankingList;
						rankPingSina = data.cloudPlatformPingSinaRankingList;
						rankPingQQ = data.cloudPlatformPingQQRankingList;
						rankPingSouhu = data.cloudPlatformPingSouhuRankingList;

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
						var pingx = [];
						var pingdata = [];
						var time = data.testUpToTime + " - "
								+ data.testUpToTimeEnd;
						$("#timetran").html(time);
						$("#timecpu").html(time);
						$("#timemem").html(time);
						$("#timeread").html(time);
						$("#timewrite").html(time);
						$("#timeping").html(time);
						for ( var i = 0; i < rankcpu.length; i++) {
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
							pingx.push(rankPing[i].cloudPlatformName);
							pingdata.push(rankPing[i].pingBaidu);
						}

						var datatable = [];
						var dataline = {
							name : "事务",
							data : transactiondata
						}
						datatable.push(dataline);
						drawChart("trancharts", "事务", transactionx, datatable,
								1, "#058DC7");
						$("#loadingtrancharts").addClass("hidden");
						var datatable = [];
						dataline = {
							name : "CPU",
							data : cpudata
						}
						datatable.push(dataline);
						drawChart("cpucharts", "cpu", cpux, datatable, 1,
								"#50B432");
						$("#loadingcpucharts").addClass("hidden");
						var datatable = [];
						dataline = {
							name : "内存",
							data : memdata
						}
						datatable.push(dataline);
						drawChart("memcharts", "内存", memx, datatable, 1,
								"#ED561B");
						$("#loadingmemcharts").addClass("hidden");
						var datatable = [];
						dataline = {
							name : "随机读",
							data : readdata
						}
						datatable.push(dataline);
						drawChart("readcharts", "随机读", readx, datatable, 1,
								"#DDDF00");
						$("#loadingreadcharts").addClass("hidden");
						var datatable = [];
						dataline = {
							name : "随机写",
							data : writedata
						}
						datatable.push(dataline);
						drawChart("writecharts", "随机写", writex, datatable, 1,
								"#24CBE5");
						$("#loadingwritecharts").addClass("hidden");
						var datatable = [];
						dataline = {
							name : "PING",
							data : pingdata
						}
						datatable.push(dataline);
						drawChart("pingcharts", "PING", pingx, datatable, 1,
								"#AA7700");
						$("#loadingpingcharts").addClass("hidden");
					});
		})

		function drawChart(id, title, xdata, data, xstep, color) {
			var hitsChartOptions = configChartOptions(id, title, xdata, data,
					xstep, color);
			var chart = new Highcharts.Chart(hitsChartOptions);
		}

		function configChartOptions(holderid, title, xdata, datas, xstep, color) {
			var chartOptions = {
				chart : {
					renderTo : holderid,
					defaultSeriesType : 'column',
					style:{padding : '5px 0 0 0'
					}
				},
				plotOptions : {
					column : {
						color : color,
						borderColor : "",//去边框
						shadow : false
					//去阴影
					},
				},
				legend : {
					enabled : false,
					align : 'center',
					verticalAlign : 'bottom',
					layout : 'horizontal',
					y : 15,
				},
				credits : {
					enabled : false
				},
				title : {
					text : '',
					style : {
						margin : '10px 0 0 0' // center it
					}
				},
				yAxis : {
					min : 0,
					title : {
						text : ''
					}
				},
				xAxis : {
					tickInterval : xstep,
					categories : xdata,
					labels : {
					//		                step:20,
					//		                align:'right',

					}
				},
				tooltip : {
					formatter : function() {
						return this.series.name + ' 数据:<b>' + this.point.y
								+ '</b><br/>公司:<b>' + xdata[this.point.x]
								+ '</b>';
					}
				},
				scrollbar : {
					enabled : true
				},
				series : datas
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
			var pingx = [];
			var pingdata = [];
			for ( var i = 0; i < rankPing.length; i++) {
				pingx.push(rankPing[i].cloudPlatformName);
				switch (grade) {
				case "optionbaidu":
					pingdata.push(rankPing[i].pingBaidu);
					break;
				case "option163":
					pingdata.push(rankPing[i].ping163);
					break;
				case "optionsina":
					pingdata.push(rankPing[i].pingSina);
					break;
				case "optionqq":
					pingdata.push(rankPing[i].pingQQ);
					break;
				case "optionsouhu":
					pingdata.push(rankPing[i].pingSouhu);
					break;
				}
			}
			var datatable = [];
			dataline = {
				name : "PING",
				data : pingdata
			}
			datatable.push(dataline);
			drawChart("pingcharts", "PING", pingx, datatable, 1, "#AA7700");
		}
		
	</script>

</body>
</html>