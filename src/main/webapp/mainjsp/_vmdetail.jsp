<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:iterator value="vmDetailList" id="vm">
		<tr>
			<td>
				<div class="media front-overflow-visible">
					<div class="media-left">
						<s:if test="#vm.platformId == 2">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/yunhai.png"></a>
						</s:if><s:elseif test="#vm.platformId == 3">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/huaweicloud.png" ></a>
						</s:elseif><s:elseif test="#vm.platformId == 4 || #vm.platformId == 5">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/alicloud.png"></a>
						</s:elseif><s:elseif test="#vm.platformId == 6">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/ucloud.png"></a>
						</s:elseif><s:elseif test="#vm.platformId == 7">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/grandcloud.png"></a>
						</s:elseif><s:elseif test="#vm.platformId == 8">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformNamee" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/qingcloud.png"></a>
						</s:elseif><s:elseif test="#vm.platformId == 9">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/EC2.png"></a>
						</s:elseif><s:elseif test="#vm.platformId == 10">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/tecentcloud.png"></a>
						</s:elseif><s:elseif test="#vm.platformId == 11">
							<a href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />">
							<img class="media-object img-circle img-avatar-50" src="images/xingyuncloud.png"></a>
						</s:elseif>					
					</div>
					<div class="media-body front-overflow-visible">
						<h5 class="media-heading" style="position: relative">
							<div class="front-text-title">
								<a style="white-space: pre-wrap" href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />"><s:property value="#vm.platformName" />主机</a>
							</div>
							<div class="front-top-right">
								<a class="blueletter rightfloat" href="javascript:void(0);" data-id="<s:property value="#vm.id" />"
								data-cpu="<s:property value="#vm.cpu" />" data-mem="<s:property value="#vm.memory" />" onclick="addCompare(this)">加入对比</a>
			    				<a class="blueletter rightfloat" href="mainjsp/searchdetail.jsp?id=<s:property value="#vm.id" />&name='<s:property value="#vm.platformName" />'&cpu=<s:property value="#vm.cpu" />&ram=<s:property value="#vm.memory" />" >查看</a>
							</div>
						</h5>
						<div class="front-text-break" style="white-space: pre-wrap">硬件配置: CPU: <s:property value="#vm.cpu" />核; 内存: <s:property value="#vm.memory" />M; 硬盘: <s:property value="#vm.disk" />G; 操作系统: <s:property value="#vm.os" /></div>
					</div>
				</div>			
			</td>
		</tr>
</s:iterator>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/public.js"></script>