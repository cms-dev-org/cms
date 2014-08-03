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
			var url = '/cms/user/listGrid';//查询数据请求，需要传入
			var container = $('#table');//包含查询出数据的dom节点
			
			var pageFlag = $(this).attr('pageFlag');//翻页标识
			
			var pagerStart = parseInt($('#pageForm input[name=pagerStart]').val());
			var pagerSize = parseInt($('#pageForm input[name=pagerSize]').val());
			var totalPage = parseInt($('#pageForm input[name=totalPage]').val());
			var currentPage = parseInt($('#pageForm input[name=currentPage]').val());
			
			if(pageFlag == 'P') {//上一页
				currentPage = currentPage - 1;
				pagerStart = pagerStart - pagerSize;
				
				if(currentPage < 1) {//如果没有上一页 则跳到末页
					currentPage = totalPage;
					pagerStart = pagerSize * (totalPage - 1);
				}
			} else if(pageFlag == 'N') {//下一页
				currentPage = currentPage + 1;
				pagerStart = pagerStart + pagerSize;
				
				if(currentPage > totalPage) {//如果没有末页 则跳到首页
					pagerStart = 0;
					currentPage = 1;
				}
			} else if(pageFlag == 'F') {//首页
				pagerStart = 0;
				currentPage = 1;
			} else if(pageFlag == 'E') {//末页
				currentPage = totalPage;
				pagerStart = pagerSize * (totalPage - 1);
			}
			
			var params = {
				totalPage	: totalPage,
				currentPage : currentPage,
				pagerStart 	: pagerStart, 
				pagerSize 	: pagerSize
			};
			$.post(url, params, function (data) {
				container.html(data);
            }, 'html');
		});
	})
</script>

<form id="pageForm">
	<input type="hidden" name="pagerStart" value="${pager.pagerStart}">
	<input type="hidden" name="pagerSize" value="${pager.pagerSize}">
	<input type="hidden" name="totalPage" value="${pager.totalPage}">
	<input type="hidden" name="currentPage" value="${pager.currentPage}">
	<input type="hidden" name="totalSize" value="${pager.totalSize}">
</form>

<div class="page mt10">
	<div class="pagination">
		<ul>
			<li class="first-child"><a href="javascript:void(0);" pageFlag="F">首页</a></li>
		    <li><a href="javascript:void(0);" pageFlag="P">上一页</a></li>
		    
			<li><a href="javascript:void(0);" pageFlag="N">下一页</a></li>
			<li class="last-child"><a href="javascript:void(0);" pageFlag="E">末页</a></li>
		</ul>
	</div>
</div>