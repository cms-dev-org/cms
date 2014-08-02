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
<title>添加用户</title>

<script type="text/javascript">
$(function() {
	
	//新增用户表单验证
	$('#addForm').validate({
		rules		: {
			username		: {
				required	: true,
				username	: true,
				minlength	: 5
			},
			password		: {
				required	: true,
				minlength	: 3
			},
			passwordConfirm : {
				equalTo		: '#password'
			},
			nickname		: 'required',
			email			: 'email'
		},
		messages	: {
			username		: {
				required	: '账号不能为空！',
				username	: '账号只能输入英文、下划线和数字！',
				minlength	: jQuery.format('账号长度至少为{0}个字符！')
			},
			password		: {
				required	: '密码不能为空！',
				minlength	: jQuery.format('密码长度至少为{0}个字符！')
			},
			passwordConfirm	: '确认密码必须与密码一致！',
			nickname		: '姓名不能为空！',
			email			: '邮箱格式不正确！'
		},
		errorElement: 'span',
		errorClass	: 'error-tip'
	});
})

</script>
</head>

<body>

<div class="container">

	<form id="addForm" action="saveUser" method="post">
	<div id="search_bar" class="mt10">
		<div class="box">
			<div class="box_border">
				<div class="box_top">
					<b class="pl15">添加用户</b>
				</div>
				<div class="box_center pt10 pb10">
					<table class="form_table" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="td_right wh200"><span style="color:red;">*</span>账号：</td>
							<td><input type="text" name="username" class="input-text lh25"
								size="30">
							</td>
						</tr>
						<tr>
							<td class="td_right wh200"><span style="color:red;">*</span>密码：</td>
							<td><input type="password" id="password" name="password" class="input-text lh25"
								size="30">
							</td>
						</tr>
						<tr>
							<td class="td_right wh200"><span style="color:red;">*</span>确认密码：</td>
							<td><input type="password" name="passwordConfirm" class="input-text lh25"
								size="30">
							</td>
						</tr>
						<tr>
							<td class="td_right wh200"><span style="color:red;">*</span>姓名：</td>
							<td><input type="text" name="nickname" class="input-text lh25"
								size="30">
							</td>
						</tr>
						<tr>
							<td class="td_right">性别：</td>
							<td>
								<div class="select_containers">
									<select name="sex" class="select">
										<option value="0">未知</option>
										<option value="1">男</option>
										<option value="2">女</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_right wh200">邮箱：</td>
							<td><input type="text" name="email" class="input-text lh25"
								size="30">
							</td>
						</tr>
						<tr>
							<td class="td_right wh200">手机号：</td>
							<td><input type="text" name="mobilePhone" class="input-text lh25"
								size="30">
							</td>
						</tr>
						<tr>
							<td class="td_right wh200">用户组：</td>
							<td>
								<c:forEach items="${group}" var="result">
									<input type="checkbox" name="groupIds" value="${result.id}"> ${result.name}
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td class="td_right wh200">用户角色：</td>
							<td>
								<c:forEach items="${role}" var="result">
									<input type="checkbox" name="roleIds" value="${result.id}"> ${result.name}
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="box_bottom pb5 pt5 pr10"
					style="border-top:1px solid #dadada;">
					<div class="search_bar_btn" style="text-align:right;">
						<input type="submit" value="确定" class="ext_btn ext_btn_submit" >
						<input type="button" value="返回"
							onclick="location.href='javascript:history.go(-1)'"
							class="ext_btn">
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
</div>
</body>
</html>
