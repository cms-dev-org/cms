<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<form id="pageForm">
	<input type="hidden" name="pager.totalSize" value="${pager.totalSize }">
	<input type="hidden" name="pager.totalPage" value="${pager.totalPage }">
</form>

<div class="page mt10">
	<div class="pagination">
		<ul>
			<li class="first-child"><a href="#">首页</a></li>
			<li class="disabled"><span>上一页</span></li>
			<li class="active"><span>1</span></li>
			<li><a href="#">2</a></li>
			<li><a href="#">下一页</a></li>
			<li class="last-child"><a href="#">末页</a></li>
		</ul>
	</div>
</div>