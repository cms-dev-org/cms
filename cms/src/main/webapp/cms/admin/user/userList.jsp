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
		//$('.delete').confirmOperator();
		$.post('/cms/user/listGrid', {}, function (data) {
			$('#table').html(data);
        }, 'html');
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
		<input type="text" name="name" class="input-text lh25" size="30">
		<input type="button" name="button" class="btn btn82 btn_search" value="查询">
	</div>
	
	<div id="table" class="mt10">
		<jsp:include page="userListGrid.jsp"></jsp:include>
	</div>
	</div>
</body>
</html>
