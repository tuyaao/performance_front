<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div class="front-loading hidden" id="loadingcharts">
  			<div class="front-loading-block"></div>
  			<div class="front-loading-block"></div>
  			<div class="front-loading-block"></div>
	</div>
	<s:iterator value="{'cpu','memory','ioseqrd','ioseqwr','iorndrd','iorndwr','oltptrans','oltpdead','oltprdwr'}" id='chartsid' status='st'>
		<div id="<s:property value='chartsid' />" class="panel panel-default front-panel hidden">
		    <div class="panel-heading">
     				<h3 class="panel-title">
    					    <s:if test="#st.count == 1">CPU性能 (单位: 秒)<span id="time" style="float: right">注: 值越小性能越高</span></s:if>
       					<s:elseif test="#st.count == 2">内存读写性能 (单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 3">硬盘I/O[顺序读](单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 4">硬盘I/O[顺序写](单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 5">硬盘I/O[随机读](单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 6">硬盘I/O[随机写](单位: MB/S)</s:elseif>
       					<s:elseif test="#st.count == 7">MySQL性能(单位: 事务数/s)</s:elseif>
       					<s:elseif test="#st.count == 8">MySQL—死锁</s:elseif>
       					<s:elseif test="#st.count == 9">MySQL性能(单位: 读写次数/s)</s:elseif>   				
     				</h3>  		
  				</div>
  				<div class="panel-body">
				<div id="<s:property value='chartsid' />highcharts" style="height: 250px;"></div>
  				</div>
		</div>
	</s:iterator>
	<div class="marginBottomForCompare" id="marginBottomForCompare" style="margin-top: 90px;"></div>