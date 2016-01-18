<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	<title>性能查询 - 云主机性能监测</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/front.css">
    <link rel="stylesheet" href="css/responsive.css">
</head>
<body class="front-body" id="container">
	<s:include value="../template/_bootbanner.jsp?index=search"/>
	<div class="front-inner">
		<div class="container">
			<div class="panel panel-default front-panel">
				<div class="panel-heading">
      				<h3 class="panel-title" style="font-weight: bold;">
         				云主机查询
      				</h3>
   				</div>
   				<div class="panel-body" style="background: #fafafa;">
   					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-md-1 control-label front-label" style="padding-top: 0px">提供商</label>
							<div class="col-md-11">
								<div class="front-checkboxes">
									<s:iterator value="cloudPlatformEntityList" id="provider" status="st">
											<label style="margin-left: 10px;"><input type="checkbox" value="<s:property value="#provider.id"/>" name="companyselect"><s:property value="#provider.name" /></label>
									</s:iterator>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-1 control-label front-label" style="padding-top: 0px">配置</label>
							<div class="col-md-5">
								<div class="front-checkboxes front-checkboxes-bottom">
									<label  style="margin-left: 10px;"><input type="checkbox" value="4" name="configureselect" checked="checked">4核8G</label>
								</div>
							</div>	
						</div>
						<div class="front-rw-right-btns">
							<button type="button" id="resetsearch" class="btn btn-default" onclick="resetting()">重置</button>
							<button type="button" id="groupsearch" class="btn btn-primary" onclick="searchConfirm()">查找</button>
						</div>
					</div>
   				</div>
			</div>
			<div class="front-loading hidden" id="loading">
    			<div class="front-loading-block"></div>
    			<div class="front-loading-block"></div>
    			<div class="front-loading-block"></div>
			</div>
			<div id="vmresult" class="panel panel-default front-panel hidden">
				<div class="panel-body front-no-padding">
					<table class="table table-striped front-table" style="margin-bottom: 0px">
						<tbody id="vmdetail">
						</tbody>
					</table>
				</div>
			</div>
			<div class="marginBottomForCompare" id="marginBottomForCompare" style="margin-top: 90px;"></div>
		</div>
	</div>
	<script type="text/javascript" src="js/LAB.min.js"></script>
<%-- <script type="text/javascript">
          window.onload = function() {
              $LAB
           .script("http://code.jquery.com/jquery.js").script("js/bootstrap.min.j").script("js/init_storage.js");
          }
</script> --%>

<script src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/init_storage.js"></script>
<%-- <script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/public.js"></script> --%>
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
	var id = ${param.id};
	if(id != 0){
		var $company = $("input[name=companyselect]");
		var $configure = $("input[name=configureselect]");
		$company.each(function(){
			if($(this).val() == id){
				$(this).attr('checked',true);
			}
		});
		$configure.each(function(){
			if($(this).val() == 4){
				$(this).attr('checked',true);
			}
		});
		searchConfirm();
	}
});
function searchConfirm(){
	$("#loading").removeClass("hidden");
	var $company = $("input[name=companyselect]:checked");
	var $configure = $("input[name=configureselect]:checked");
	var companyselecttxt = "";
	var configureSelecttxt = "";
	$company.each(function(){
		companyselecttxt += $(this).val() + ","
	});
	$configure.each(function(){
		configureSelecttxt += $(this).val() + ","
	});

	if($company.length == 0){
		alert("请选择一个公司");
		$("#loading").addClass("hidden");
		return false;
	}
	if($configure.length == 0){
		alert("请选择一个配置");
		$("#loading").addClass("hidden");
		return false;
	}
	$.post("search/vmdetailaction",{companyselecttxt:companyselecttxt,configureSelecttxt:configureSelecttxt},
			function(data){
		$("#vmresult").removeClass("hidden");
		$("#vmdetail").html(data);
		$("#loading").addClass("hidden");
	});
}
function resetting(){
	var $company = $("input[name=companyselect]:checked");
	var $configure = $("input[name=configureselect]:checked");
	$company.each(function(){
		$(this).attr('checked',false);
	});
	$configure.each(function(){
		$(this).attr('checked',false);
	});
}
</script>

</body>
</html>