<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('.pagination ul li a').click(function() {
			var url = '/cms/user/list';
		});
	})
</script>

<form id="pageForm">
	<input type="hidden" name="pager.pagerSize" value="${pager.pagerSize}">
	<input type="hidden" name="pager.totalPage" value="${pager.totalPage}">
	<input type="hidden" name="pager.currentPage" value="${pager.currentPage}">
</form>

<div class="page mt10">
	<div class="pagination">
		<ul>
			<li class="first-child"><a href="#">首页</a></li>
		    <li class="disabled"><span>上一页</span></li>
			<li class="active"><span>1</span></li>
			<li><a href="#">2</a></li>
			<li><a href="javascript:void(0);">下一页</a></li>
			<li class="last-child"><a href="#">末页</a></li>
			
		</ul>
	</div>
</div>