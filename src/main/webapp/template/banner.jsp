<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="row">
			<div class="navbar-header">
				<a class="navbar-brand front-brand" href="inverse"><img id="logo" src="images/logo.png" border="0" /></a>
			</div>
			<div class="pub_banner hidden" user="<s:property value='#session.email'/>" userid="<s:property value='#session.userId'/>" sys = "<s:property value='#session.version'/>" acctoken="<s:property value='#session.accessToken.substring(8, 24)'/>"></div>
			<div class="collapse navbar-collapse" id="freeshare-navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="${param.index == 'home' ? 'active' : ''}">
					<a href="mainjsp/homepage.jsp">综述首页</a></li>
					<li class="dropdown ${param.index == 'search' ? 'active' : ''}">
					<a href="search/vmdetail">主机查询</a></li>
					<li class="${param.index == 'compare' ? 'active' : ''}">
					<a href="mainjsp/compare.jsp">性能对比</a></li>
					<li class=""><a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" role="button" aria-expanded="false">更多<span class="caret"></span></a>
						<ul class="dropdown-menu front-no-border" role="menu" aria-labelledby="new" aria-expanded="false">
							<li style="text-align: center"><a href="http://account.free4lab.com/users/users/userinfo" target="_blank">
								<img style="margin-top: 5px" src="http://account.free4lab.com/users/users/download?uuid=60a1af72-2112-4dad-9b5f-74da6cb52ce6" onerror="javascript:this.src='images/user.png'" class="center-block img-circle img-avatar-50"> </a><span class="center-block">ç¨èª</span>
									<span class="center-block"><small>928434237@qq.com</small></span></li>
							<li class="divider"></li>
							<li><a href="javascript:void(0)" onclick="logout()"><span class="glyphicon glyphicon-log-out"></span>&nbsp;退出</a></li>
							
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
</nav>