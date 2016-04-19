<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div class="front-loading hidden" id="loadingcharts">
  			<div class="front-loading-block"></div>
  			<div class="front-loading-block"></div>
  			<div class="front-loading-block"></div>
	</div>
	<s:iterator value="{'cpu','memory','ioseqrd','ioseqwr','iorndrd','iorndwr','oltptrans','oltpdead','oltprdwr','ping'}" id='chartsid' status='st'>
		<div id="<s:property value='chartsid' />" class="panel panel-default front-panel hidden">
		    <div class="panel-heading">
    					<s:if test="#st.count == 1"><h3 class="panel-title" style="font-weight: bold;">CPU(单位: S)<span id="time" style="float: right">注: 值越小性能越高</span></h3></s:if>
       					<s:elseif test="#st.count == 2"><h3 class="panel-title" style="font-weight: bold;">内存读写性能 (单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 3"><h3 class="panel-title" style="font-weight: bold;">硬盘I/O[顺序读](单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 4"><h3 class="panel-title" style="font-weight: bold;">硬盘I/O[顺序写](单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 5"><h3 class="panel-title" style="font-weight: bold;">硬盘I/O[随机读](单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 6"><h3 class="panel-title" style="font-weight: bold;">硬盘I/O[随机写](单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 7"><h3 class="panel-title" style="font-weight: bold;">MySQL性能(单位: 事务数/s)</s:elseif>
       					<s:elseif test="#st.count == 8"><h3 class="panel-title" style="font-weight: bold;">MySQL—死锁</s:elseif>
       					<s:elseif test="#st.count == 9"><h3 class="panel-title" style="font-weight: bold;">MySQL性能(单位: 读写次数/s)</s:elseif>   	
       					
       					<s:elseif test="#st.count == 10">
							<select id="pingchartselect" class="panel-title"
								onchange="changePingChart()"
								style="font-weight: bold; border: 0; border: none;" name="cars">
								<option id="optionbaidu" class="panel-title"
									style="font-weight: bold;" value="baiduPing">ping www.baidu.com (单位: S))</option>
								<option id="option163" class="panel-title"
									style="font-weight: bold;" value="163Ping">ping www.163.com (单位: S)</option>
								<option id="optionsina" class="panel-title"
									style="font-weight: bold;" value="sinaPing">ping www.sina.com.cn (单位: S)</option>
								<option id="optionqq" class="panel-title"
									style="font-weight: bold;" value="qqPing">ping www.qq.com (单位: S)</option>
								<option id="optionsouhu" class="panel-title"
									style="font-weight: bold;" value="souhuPing">ping www.sohu.com (单位: S)</option>
							</select>
						</s:elseif>
       						
  				</div>
  				<div class="panel-body">
				<div id="<s:property value='chartsid' />highcharts" style="height: 250px;"></div>
  				</div>
		</div>
	</s:iterator>
	<div class="marginBottomForCompare" id="marginBottomForCompare" style="margin-top: 90px;"></div>