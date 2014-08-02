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
			toAddUserPage();
		});
		$('.delete').confirmOperator();
		
	});
	
	//跳转到添加用户页面
	function toAddUserPage() {
		window.location.href = "saveUserInit";
	}
	//改变用户状态
	function changeUserStatus(id, ths) {
		$.ajax({
			url		: 'editUserStatus',
			data	: {id : id},
			type	: 'GET',
			success	: function(response) {
				if(response.success) {
					if(response.data == 1) {
						$(ths).empty().append('停用');
						$(ths).prev().empty().append('已启用 | ');
					} else {
						$(ths).empty().append('启用');
						$(ths).prev().empty().append('已停用 | ');
					}
				} else {
					alert("修改失败，请联系管理员！");
				}
			}
		});
	}
</script>

</head>
<body>

<div class="container">

<div id="button" class="mt5" style="float:right">
	<input id="addUserBtn" type="button" name="button" class="btn btn82 btn_add" value="新增" />
	
	<input type="text" name="name" class="input-text wh200" size="10">
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
			<c:forEach items="${pager.data}" var="user">
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
				<c:if test="${user.status eq 1}"><span>已启用 | </span><a href="javascript:void(0)" onclick="changeUserStatus(${user.id}, this)">停用</a></c:if>
				<c:if test="${user.status eq 0}"><span>已停用 | </span><a href="javascript:void(0)" onclick="changeUserStatus(${user.id}, this)">启用</a></c:if>
				</td>
				<td class="td_center">
					<a href="udpate/${user.id}">修改</a>&nbsp;&nbsp;
					<a class="delete" href="delete/${user.id}">删除</a>
				</td>
			</tr>
			</c:forEach>
		</table>
		<div class="pager" style="float:right;">
			<jsp:include page="/pager.jsp"></jsp:include>
		</div>
	</div>
</div>
</div>
</body>
</html>
