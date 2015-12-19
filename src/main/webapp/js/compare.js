//var userId =  $( '.pub_banner' ).attr("userid" );
var userId = 726;
//初始化比较栏
function initComBox(){
	// 初始化比较栏 html
	var div = document.createElement("div");
	div.setAttribute("id","compareBox");
	div.setAttribute("class","hidden");
	var width = document.body.offsetWidth;
	if ( width > 768 ){
		div.setAttribute("style","position: fixed;  left: 0px; bottom: 0px; z-index: 999;padding:5px 0;height:auto;width:100%;background-color:#f4f4f4");
	}else{
//		document.getElementById("marginBottomForCompare").setAttribute("style","margin-top: 0px");
	}
	var insertplace = document.getElementById("container");
	insertplace.appendChild(div);
	var div1 = document.createElement("div");
	div1.setAttribute("id","comparePlace");
	div1.setAttribute("style","width:90%;display:inline-block");
	div.appendChild(div1);
	
	var table = document.createElement("table");
	table.setAttribute("id","edit");
	table.setAttribute("style","width:90px;text-align: center;height:100px;margin-right:0px;display:inline-block;position: absolute;right: 0;");
	var tr1 = document.createElement("tr");
	var td1 = document.createElement("td");
	td1.setAttribute("style","padding-top:10px;");
	var span1  = document.createElement("span");
	span1.setAttribute("id","com_num");
	var str1 = document.createTextNode("[");
	var str2 = document.createTextNode("/4]");
	td1.appendChild(str1);
	td1.appendChild(span1);
	td1.appendChild(str2);
	tr1.appendChild(td1);
	table.appendChild(tr1);
	var tr2 = document.createElement("tr");
	var td2 = document.createElement("td");
	var a = document.createElement("a");
	a.setAttribute("href","javascript:void(0)");
	a.setAttribute("onclick","clearAll()");
	a.setAttribute("class","blueletter");
	var str = document.createTextNode("清空对比栏");
	a.appendChild(str);
	td2.appendChild(a);
	tr2.appendChild(td2);
	table.appendChild(tr2);
	var tr3 = document.createElement("tr");
	var td3 = document.createElement("td");
	var a2 = document.createElement("a");
	a2.setAttribute("class","button");
	a2.innerHTML="对比";
	a2.setAttribute("href","mainjsp/compare.jsp");
//	a2.setAttribute("target","_blank");
	td3.appendChild(a2);
	tr3.appendChild(td3);
	table.appendChild(tr3);
	div.appendChild(table);
			
	modifyBox();
	Storage.onstorage(userId + "_compare", onCompareStorageChange);
}
//加入对比的操作
function addCompare(ele){
	var id = $(ele).attr("data-id");
	var com = loadStorage();
	if(com.length==4){
		alert("最多只能比较4个云主机！")
		return false;
	}else{
		for(var i=0 ; i < com.length; i ++){
			if (id ==com[i].id){
				alert("此云主机已加入比较！");
				return false;
				
			}else{
				continue;
			}
		}
		var comMsg={
				id:id,
				name:"",
				cpu:$(ele).attr("data-cpu"),
				ram:$(ele).attr("data-mem"),
				image:""
		};
		if(id == 4 || id == 8 || id == 38){
			comMsg.name = "云海";
			comMsg.image = "images/yunhai.png";
		} else if(id == 16 || id == 35 || id == 11){
			comMsg.name = "华为云";
			comMsg.image = "images/huaweicloud.png";
		} else if(id == 15 || id == 14 || id == 34){
			comMsg.name = "阿里云-北京";
			comMsg.image = "images/alicloud.png";
		} else if(id == 13 || id == 12 || id == 33){
			comMsg.name = "阿里云-青岛";
			comMsg.image = "images/alicloud.png";
		} else if(id == 17 || id == 18 || id == 30){
			comMsg.name = "ucloud";
			comMsg.image = "images/ucloud.png";
		} else if(id == 19 || id == 20 || id == 31){
			comMsg.name = "盛大云";
			comMsg.image = "images/grandcloud.png";
		} else if(id == 22 || id == 36){
			comMsg.name = "青云";
			comMsg.image = "images/qingcloud.png";
		} else if(id == 24 || id == 25 || id == 32){
			comMsg.name = "亚马逊";
			comMsg.image = "images/EC2.png";
		} else if(id == 26 || id == 27 || id == 28){
			comMsg.name = "腾讯云";
			comMsg.image = "images/tecentcloud.png";
		} else if(id == 37){
			comMsg.name = "星云";
			comMsg.image = "images/xingyuncloud.png";
		}
		com.push(comMsg);
		Storage.setItem(userId + "_compare", JSON.stringify(com));
		onCompareStorageChange();
	}
}
//移除对比
function removeCompare(id){
	var com = loadStorage();
	for(var i=0 ; i < com.length; i ++){
		if(com[i].id == id ){
			 com.splice(i,1);
			Storage.setItem(userId + "_compare",JSON.stringify(com));
			onCompareStorageChange();
			break;
		}else{
			continue;
		}
	}
}
//清空
function clearAll(){
	Storage.removeItem(userId + "_compare");
	onCompareStorageChange();
}

function onCompareStorageChange(){
	modifyBox();
}
//更改比较栏
function modifyBox(){
	var com = loadStorage();
	if(com.length == 0){
		if(!$("#compareBox").hasClass("hidden")){
			$("#compareBox").addClass("hidden");
		}
	}else{
		if($("#compareBox").hasClass("hidden")){
			$("#compareBox").removeClass("hidden");
		}
		
		var htmlStr="";
		
		for(var i=0 ; i < com.length; i ++){
			htmlStr += "<table class=\"col-md-3 col-xs-6\" style=\"width:220px;height:80px;padding:5px;margin-left:15px;margin-top:5px;margin-bottom:5px;background-color:#ffffff;border:#d5d9e0 1px solid;display:inline-block\">"+
						"<tr><td rowspan=\"3\" style=\"width:70px\"><img src="+com[i].image+" width=50px height=50px style=\"margin-left:5px;\"></td><td style=\"padding-top:5px;\">"+
						com[i].name+"主机</td></tr><tr><td>"+com[i].cpu+"CPU,"+com[i].ram+"M内存</td></tr><tr>"+
						"<td><a class=\"blueletter\" href=\"mainjsp/searchdetail.jsp?id="+com[i].id+"&name='"+com[i].name+"'&cpu="+com[i].cpu+"&ram="+com[i].ram+"\">详情</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:void(0)\" class=\"blueletter\" onclick = \"removeCompare("+com[i].id+")\">移除</a></td>"	+
						"</tr></table>"
						
		}
//		alert(width);
//		alert(com.length);
		var width = document.body.offsetWidth;
		if ((width < 768) && (com.length > 1) ){
			document.getElementById("compareBox").setAttribute("style","position: static;  left: 0px; bottom: 0px; z-index: 999;padding:5px 0;height:auto;width:100%;background-color:#f4f4f4");
			document.getElementById("marginBottomForCompare").setAttribute("style","margin-top: 0px");
		}else{
			document.getElementById("compareBox").setAttribute("style","position: fixed;  left: 0px; bottom: 0px; z-index: 999;padding:5px 0;height:auto;width:100%;background-color:#f4f4f4");
			document.getElementById("marginBottomForCompare").setAttribute("style","margin-top: 90px");
		}
		$("#comparePlace").html(htmlStr);
		
		$("#com_num").html(com.length);
	}
}
function compare(){
	
}
function loadStorage(){
	try{
		var compareString = Storage.getItem(userId + "_compare") || "[]";
		var com = JSON.parse(compareString);
		if(typeof com != "object" ){
			Storage.setItem(userId + "_compare","[]");
			return JSON.parse("[]");
		}
		return com;
	}catch(e){
		console.log("compareMsg error");
		Storage.setItem(userId + "_compare","[]");
		return JSON.parse("[]");
	}
}
window.setTimeout("initComBox()",400);