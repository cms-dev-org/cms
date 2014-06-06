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
<%@ include file="/html_include.jsp" %>

<title>用户列表</title>

<script type="text/javascript" src="../admin/js/colResizable-1.3.min.js"></script>
<script type="text/javascript" src="../admin/js/common.js"></script>
<script type="text/javascript" src="../admin/js/jquery.cms.core.js"></script>

<script type="text/javascript">
	$(function() {
		$('#addUserBtn').click(function() {
			toAddUserPage()
		});
		$('.delete').confirmOperator();
	});
	
	//跳转到添加用户页面
	function toAddUserPage() {
		window.location.href = "saveUserInit";
	}
</script>

</head>
<body>

<div class="container">

<div id="button" class="mt5">
	<input id="addUserBtn" type="button" name="button" class="btn btn82 btn_add" value="新增" />
	
	<input type="text" name="name" class="input-text  wh200" size="10">
	<input type="button" name="button" class="btn btn82 btn_search" value="查询">
</div>

<div id="table" class="mt10">
	<div class="box span10 oh">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="list_table">
			<tr>
				<th width="50"><input type="checkbox" /></th>
				<th width="100">账号</th>
				<th width="100">姓名</th>
				<th width="100">性别</th>
				<th width="100">手机号</th>
				<th width="100">邮箱</th>
				<th width="100">状态</th>
				<th width="100">操作</th>
			</tr>
			<c:forEach items="${datas.data}" var="user">
			<tr class="tr">
				<td class="td_center">
					<input type="checkbox" />
				</td>
				<td class="td_center">${user.username}</td>
				<td class="td_center">${user.nickname}</td>
				<td class="td_center">
				<c:if test="${user.sex eq 0}">未知</c:if>
				<c:if test="${user.sex eq 1}">男</c:if>
				<c:if test="${user.sex eq 2}">女</c:if>
				</td>
				<td class="td_center">${user.mobilePhone}</td>
				<td class="td_center"><a href="mailto:${user.email}">${user.email}</a></td>
				<td class="td_center">
				<c:if test="${user.status eq 1}">启用</c:if>
				<c:if test="${user.status eq 0}">停用</c:if>
				</td>
				<td class="td_center">
					<a href="udpate/${user.id}">修改</a>&nbsp;&nbsp;
					<a class="delete" href="delete/${user.id}">删除</a>
				</td>
			</tr>
			</c:forEach>
		</table>
		<div class="page mt10">
			<div class="pagination">
				<ul>
					<li class="first-child"><a href="#">首页</a>
					</li>
					<li class="disabled"><span>上一页</span>
					</li>
					<li class="active"><span>1</span>
					</li>
					<li><a href="#">2</a>
					</li>
					<li><a href="#">下一页</a>
					</li>
					<li class="last-child"><a href="#">末页</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
</div>
</body>
</html>
