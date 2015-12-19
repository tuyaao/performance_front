<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="pub_banner" sys="yhvm" user=<s:property value="#session.email"/>
	<s:if test="#session.accToken == null">
		未登录
	</s:if>
	acctoken=<s:property value="#session.accToken"/>
 			userId=<s:property value="#session.userId"/>
 			index="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"%>">
	
</div>

<%-- <div class="banner">
	<div class="content">
    	<img id="logo" src="images/logo.png" border="0" />
    	<span class="nav">
        	<a><img src="images/vm.png"  class='${ param.index=="v"?"cur":"" }'/></a>
    	</span>
  	</div>
</div> --%>