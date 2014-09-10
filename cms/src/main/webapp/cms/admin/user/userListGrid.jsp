<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/html_include.jsp" %>

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
			<a class="delete" href="javascript:delUser(${user.id});">删除</a>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pager" style="float:right;">
	<jsp:include page="/pager.jsp">
		<jsp:param value="/cms/user/listGrid" name="url"/>
		<jsp:param value="table" name="container"/>
	</jsp:include>
</div>
