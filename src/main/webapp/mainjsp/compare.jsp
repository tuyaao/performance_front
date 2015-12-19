<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	<title>性能对比 - 云主机性能监测</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui.css" />
	<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui-timepicker-addon.css" />
    <link rel="stylesheet" href="css/front.css">
    <link rel="stylesheet" href="css/responsive.css">
</head>
<body class="front-body" id="container">
	<s:include value="../template/_bootbanner.jsp?index=compare"/>
	<div class="front-inner">
		<div class="container" id="aaa">
			<div class="panel panel-default front-panel">
				<div class="panel-heading">
      				<h3 class="panel-title">
         				性能对比
      				</h3>
   				</div>
   				<div class="panel-body" style="background: #fafafa;">
   				   	<div class="form-horizontal">
						<div class="form-group">
							<label class="col-md-1 control-label front-label" style="padding-top: 0px">指标</label>
							<div class="col-md-5">
								<div class="front-checkboxes front-checkboxes-bottom">
									<label><input type="checkbox" value="0" name="dataselect" checked="checked">CPU</label>
									<label><input type="checkbox" value="1" name="dataselect">内存</label>
									<label><input type="checkbox" value="2" name="dataselect">硬盘I/O</label>
									<label><input type="checkbox" value="3" name="dataselect">MySQL</label>
								</div>
							</div>	
						</div>
						<div class="form-group">
							<label class="col-md-1 control-label front-label" style="padding-top: 5px">时间</label>
							<div class="col-md-11 col-xs-12">
								<div class="front-inline col-md-1 col-xs-4" style="float: left; margin-top: 5px; padding-left: 0px; padding-right: 5px; width: 81px;">开始时间</div>
								<input type="text" id="rest_example_4_start" value=""
									class="squareinput leftmargin_10 rightmargin_10 col-md-2 col-xs-8" style="float: left; width: 175px; margin-top: 2px; margin-right: 45px;" />
								<div class="front-inline col-md-1 col-xs-4" style="float: left; margin-top: 5px; padding-left: 0px; padding-right: 5px; width: 81px;">截止时间</div>
								<input type="text" id="rest_example_4_end" value=""
									class="squareinput leftmargin_10 rightmargin_10 col-md-2 col-xs-8" style="float: left; width: 175px; margin-top: 2px; margin-right: 45px;" />
								<div id="termsnull" class="front-inline text-danger hidden col-md-2" style="float: left; margin-top: 5px; padding-left: 0px; width: 241px;">开始与截止时间差间隔不超过8天</div>
							</div>
							
						</div>
						<div class="front-rw-right-btns">
							<button type="button" id="resetsearch" class="btn btn-default" onclick="resetting()">重置</button>
							<button type="button" id="groupsearch" class="btn btn-primary" onclick="compareConfirm('','','','')">确定</button>
						</div>
					</div>		
   				</div>
			</div>
			<s:include value="_allcharts.jsp"/>
		</div>
	</div>
	
<%-- 	<script src="js/LAB.min.js"></script> --%>
<%-- 	<script type="text/javascript">
          window.onload = function() {
              $LAB
              .script("js/highstock.js").script("js/init_storage.js").script("bootstrap.min.js")
          }
      </script> --%>
    <script src="http://code.jquery.com/jquery.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/highcharts.js"></script>
    <script type="text/javascript" src="js/init_storage.js"></script>
    <script type="text/javascript" src="js/timeview.js"></script>
    <script type="text/javascript" src="js/drawcharts.js"></script>
<%--     <script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/public.js"></script> --%>
    <script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui.min.js"></script>
    <script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui-timepicker-addon.js"></script>
    <script type="text/javascript" src="js/compare.js"></script>
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
	compareConfirm('','','','');
});
function resetting(){
	var $dataselect = $("input[name=dataselect]:checked");
	$dataselect.each(function(){
		$(this).attr('checked',false);
	});

}

</script>

</body>
</html>