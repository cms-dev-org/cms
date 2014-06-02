<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>后台首页</title>
<jsp:include page="/html_include.jsp"></jsp:include>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" href="admin/css/common.css">
<link rel="stylesheet" href="admin/css/style.css">
<script type="text/javascript" src="admin/js/index.js"></script>
<script type="text/javascript">
	$(function() {
		$(".sideMenu").slide({
			titCell : "h3",
			targetCell : "ul",
			defaultIndex : 0,
			effect : 'slideDown',
			delayTime : '300',
			trigger : 'click',
			triggerTime : '150',
			defaultPlay : true,
			returnDefault : false,
			easing : 'easeInQuint',
			endFun : function() {
				scrollWW();
			}
		});
		$(window).resize(function() {
			scrollWW();
		});
		
		$('.sideMenu').mymenutree();
		
	});
	function scrollWW() {
		if ($(".side").height() < $(".sideMenu").height()) {
			$(".scroll").show();
			var pos = $(".sideMenu ul:visible").position().top - 38;
			$('.sideMenu').animate({
				top : -pos
			});
		} else {
			$(".scroll").hide();
			$('.sideMenu').animate({
				top : 0
			});
			n = 1;
		}
	}

	var n = 1;
	function menuScroll(num) {
		var Scroll = $('.sideMenu');
		var ScrollP = $('.sideMenu').position();
		if (num == 1) {
			Scroll.animate({
				top : ScrollP.top - 38
			});
			n = n + 1;
		} else {
			if (ScrollP.top > -38 && ScrollP.top != 0) {
				ScrollP.top = -38;
			}
			if (ScrollP.top < 0) {
				Scroll.animate({
					top : 38 + ScrollP.top
				});
			} else {
				n = 1;
			}
			if (n > 1) {
				n = n - 1;
			}
		}
	}
</script>
</head>
<body>
	<div class="top">
		<div id="top_t">
			<div id="logo" class="fl"></div>
			<div id="photo_info" class="fr">
				<div id="photo" class="fl">
					<a href="#"><img src="admin/images/a.png" alt="" width="60"
						height="60"> </a>
				</div>
				<div id="base_info" class="fr">
					<div class="help_info">
						<a href="1" id="hp">&nbsp;</a> <a href="2" id="gy">&nbsp;</a> <a
							href="3" id="out">&nbsp;</a>
					</div>
					<div class="info_center">
						admin <span id="nt">通知</span><span><a href="#" id="notice">3</a>
						</span>
					</div>
				</div>
			</div>
		</div>
		<div id="side_here">
			<div id="side_here_l" class="fl"></div>
			<div id="here_area" class="fl">当前位置：</div>
		</div>
	</div>
	<div class="side">
		<div class="sideMenu" style="margin:0 auto">
			<h3>用户管理</h3>
			<ul>
				<li class="on" src="/cms/user/list">会员管理</li>
				<li>会员组管理</li>
				<li>会员功能配置</li>
				<li>管理员管理</li>
				<li>个人资料</li>
				<li src="cms/admin/main.jsp">DEMO</li>
			</ul>
			<h3>系统设置</h3>
			<ul>
				<li>系统信息</li>
				<li>基本设置</li>
				<li>语言设置</li>
				<li>图片设置</li>
			</ul>
			
			<h3>导航菜单</h3>
			<ul>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
			</ul>
			<h3>导航菜单</h3>
			<ul>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
			</ul>
			<h3>导航菜单</h3>
			<ul>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
			</ul>
			<h3>导航菜单</h3>
			<ul>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
				<li>导航菜单</li>
			</ul>
		</div>
	</div>
	<div class="main">
		<iframe name="right" id="rightMain" frameborder="no" src="/cms/user/list"
			scrolling="auto" width="100%" height="auto" allowtransparency="true"></iframe>
	</div>
	<div class="bottom">
		<div id="bottom_bg">萌宝软件 版权所有    Copyright 2014-2015 MENGBAO Corporation, All Rights Reserved</div>
	</div>
	<div class="scroll">
		<a href="javascript:;" class="per" title="使用鼠标滚轴滚动侧栏"
			onclick="menuScroll(1);"></a> <a href="javascript:;" class="next"
			title="使用鼠标滚轴滚动侧栏" onclick="menuScroll(2);"></a>
	</div>
</body>

</html>

