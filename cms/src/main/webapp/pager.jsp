<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

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