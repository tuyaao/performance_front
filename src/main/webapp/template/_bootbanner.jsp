<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="row">
			<div class="navbar-header">
			    <button type="button" class="navbar-toggle collapsed" id="per-toggle" data-target="#freeshare-navbar-collapse">
            		<span class="sr-only">Toggle navigation</span>
            		<span class="icon-bar"></span>
            		<span class="icon-bar"></span>
            		<span class="icon-bar"></span>
          		</button>
				<a class="navbar-brand front-brand"><img id="logo" src="images/logo.png" border="0" /></a>
			</div>
			<div class="pub_banner hidden" user="<s:property value='#session.email'/>" acctoken="<s:property value='#session.accessToken.substring(8, 24)'/>"></div>
			<div class="collapse navbar-collapse" id="freeshare-navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="${param.index == 'home' ? 'front-active' : ''}">
					<a href="mainjsp/homepage.jsp">综述首页</a></li>
					<li class="${param.index == 'search' ? 'front-active' : ''}">
					<a href="search/vmdetail?id=0">性能查询</a></li>
					<li class="${param.index == 'compare' ? 'front-active' : ''}">
					<a href="mainjsp/compare.jsp">性能对比</a></li>
					<li class="${param.act == 'more' ? 'front-active' : ''}">
						<a href="javascript:void(0)" id="nav-more" class="dropdown-toggle" aria-haspopup="true" role="button" aria-expanded="false">更多<span class="caret"></span></a>
						<ul class="dropdown-menu front-no-border" role="menu" aria-labelledby="new" aria-expanded="false">
							<li style="text-align: center"><a href="http://account.free4inno.com/users/users/userinfo" target="_blank">
								<img style="margin-top: 5px" src="http://account.free4inno.com/users/users/download?uuid=<s:property value='#session.useravatar'/>" onerror="javascript:this.src='images/user.png'" class="center-block img-circle img-avatar-50"> </a><span class="center-block">${session.username}</span>
									<span class="center-block"><small>${session.email}</small></span></li>
							<li class="divider"></li>
							<li style="text-align: center"><a href="javascript:void(0)" onclick="logout(event)">退出</a></li>			
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
</nav>
<div id="logout" data-name="<s:property value="#acctoken" />" data-index="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/"></div>
<script type="text/javascript" src="js/init_storage.js"></script>
<script>



function logout(event){
	event.stopPropagation();
	$.ajax({  
        url : "logout",  
        type:"post",
        dataType:"json", 
        async:false,
        data:{},  
        success:function(data) {
        	console.info(data);
        	if(typeof(com) != "undefined"){
        		com.xmpp.close();
        	}
        	if(data.result == 'success'){
        		var access_token = $(".pub_banner").attr("acctoken");
	        	var accountAddr = "http://account.free4lab.com/";
	        	$.ajax({  
	                url:accountAddr+"api/oauth2/revokeoauth2?callback=?",  
	                type:"post",
	                dataType:"json",  
	                data:{'access_token':access_token},  
	                complete:function(result) {
	                	if(typeof(com) != "undefined"){
	                		com.xmpp.close();
	                	}
	                	
	                }
	            });
        		
        	}else
        		alert('logout fail:' + data.message);
        		
        	location.replace($("#logout").attr("data-index"));
        	clearAll();
        },
        error:function(data){
        	alert('logout error:' + data);
        }
    });
} 

</script>