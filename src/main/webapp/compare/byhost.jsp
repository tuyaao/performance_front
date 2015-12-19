<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>按主机</title>
  	<s:include value="../template/_head.jsp" />
  	<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui.css" />
	<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui-timepicker-addon.css" />
</head>
<body>
<div id="container">
	<s:include value="../template/_pub_banner.jsp"></s:include>
	<div id="inner">
		<s:include value="../template/_left.jsp?menu=byhost"></s:include>
		<div class="right">
			<table class="formtable xparseline" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td class="darkblueletter leftalign" style="vertical-align: middle;">配置：</td>
						<td class="leftalign padding5" id="setting">
							<button class="button leftbutton" id="1" name="configureselect" onclick="clickSetting(this)">1核2G</button>
							<button class="graybutton middlebutton" id="2" name="noselect" onclick="clickSetting(this)">2核4G</button>
						</td> 
					</tr>
					<tr>
						<td class="darkblueletter leftalign" style="vertical-align: middle;">提供商：</td>
						<td class="leftalign padding5">
						    <input type="radio" value="4" name="companyselect" >
							<label>阿里云-北京</label>
							<input type="radio" value="5" name="companyselect" >
							<label>阿里云-青岛</label>
							<input type="radio" value="6" name="companyselect" >
							<label>Ucloud</label>
							<input type="radio" value="3" name="companyselect" >
							<label>华为</label>
							<input type="radio" value="2" name="companyselect" >
							<label>云海</label><br/>
							<input type="radio" value="8" name="companyselect" >
							<label>青云</label>	
							<input type="radio" value="7" name="companyselect" >
							<label>盛大云</label>
							<input type="radio" value="9" name="companyselect" >
							<label>亚马逊</label>	
							<input type="radio" value="10" name="companyselect" >
							<label>腾讯云</label>	
						</td>
					</tr>
					<tr>
						<td class="darkblueletter leftalign" style="vertical-align: middle;">指标：</td>
						<td class="leftalign padding5">
							<input type="checkbox" value="0" name="hostselect" >
							<label>CPU</label>
							<input type="checkbox" value="1" name="hostselect" >
							<label>内存</label>
							<input type="checkbox" value="2" name="hostselect" >
							<label>硬盘I/O</label>
							<input type="checkbox" value="3" name="hostselect" >
							<label>综合</label>
						</td>
					</tr>
					<tr>
						<td class="darkblueletter leftalign" style="vertical-align: middle;">时间：</td>
						<td class="leftalign padding5">截止到
	       					<span class="leftmargin_10">
	       						<input type="text" name="rest_example_4_end" id="rest_example_4_end" value="" class="squareinput leftmargin_10 rightmargin_20" style="width:175px" />
	       					</span>
    					</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td><button class="button" onclick="confirm('compare/getconfigureaction')">确定</button></td>
					</tr>
					</tbody>
			</table>
					<div class="topmargin_20" id="cpuhighcharts"></div>
					<div class="topmargin_20" id="memoryhighcharts"></div>
					<div class="topmargin_20" id="ioseqrdhighcharts"></div>
					<div class="topmargin_20" id="ioseqwrhighcharts"></div>
					<div class="topmargin_20" id="iorndrdhighcharts"></div>
					<div class="topmargin_20" id="iorndwrhighcharts"></div>
					<div class="topmargin_20" id="oltptranshighcharts"></div>
					<div class="topmargin_20" id="oltpdeadhighcharts"></div>
					<div class="topmargin_20" id="oltprdwrhighcharts"></div>
					<div class="topmargin_20" id="oltpotherhighcharts"></div>
					<div class="leftfloat">
						<a class="blueletter">测试方法说明</a>
					</div>
					<div class="rightfloat">
						<a class="blueletter">查看数据表</a>
					</div>
					<div class="rightfloat">
						<a class="blueletter rightmargin_20">下载数据时间</a>
					</div>
		</div>
	</div>

</div>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/public.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/highcharts.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui-sliderAccess.js"></script>
<script type="text/javascript" src="js/myhighcharts.js"></script>
<script type="text/javascript" src="js/timeview.js"></script>

<script>
function clickSetting(obj){
	if($(obj).hasClass("button")){
		$(obj).removeClass("button").addClass("graybutton");
		$(obj).attr("name","notselected");
	}else{
		$(obj).removeClass("graybutton").addClass("button");
		$(obj).attr("name","configureselect");
	}
	//给按钮换颜色
	//$("#setting .button").removeClass("button").addClass("graybutton");
	//$(obj).removeClass("graybutton").addClass("button");
}
</script>
</body>
</html>