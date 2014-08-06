<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String url = request.getParameter("url");
	String container = request.getParameter("container");
%>
<style>
	.show-pager-num {
		display 	: none; 
		border 		: solid 1px #D3DBDE; 
		position 	: absolute; 
		width 		: 58px;
		height		: 125px;
		overflow-y	: scroll;
	}
	.show-pager-num {
		background-color	: #fff;
		background			: url('/cms/admin/images/bottom_bg.png') 0px 0px;
	}
	.show-pager-num li{
		text-align	: center;
		color		: #08c;
		cursor		: pointer;
	}
	.show-pager-num li:hover{
		text-align		: center;
		background-color: #3EAFE0;
	}
	.pager-num-view {
		width		: 35px; 
		text-align	: center;
	}
</style>
<script type="text/javascript">
	$(function() {
		var url = '<%=url%>';//查询数据请求，需要传入
		if(!url) {alert('分页url参数缺失');return;}
		var container = $('#<%=container%>');//包含查询出数据的dom节点
		if(!container) {alert('分页container参数缺失');return;}
		
		$(document).click(function (event) { $('#pagerNumList').hide() });//隐藏分页弹出框  
		
		//加载跳转页面弹出框
		var pages = parseInt($('#pageForm input[name=totalPage]').val());
		var numList = '';
		for(var i=1; i<=pages; i++) {
			numList += '<li class="jumpToPage">' + i + '</li>';
		}
		$('#pagerNumList').html(numList);
		
		$('.pagination ul li a').click(function(event) {
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
				jump(pagerStart, pagerSize, url, container);
			} else if(pageFlag == 'N') {//下一页
				currentPage = currentPage + 1;
				pagerStart = pagerStart + pagerSize;
				
				if(currentPage > totalPage) {//如果没有末页 则跳到首页
					pagerStart = 0;
					currentPage = 1;
				}
				jump(pagerStart, pagerSize, url, container);
			} else if(pageFlag == 'F') {//首页
				pagerStart = 0;
				currentPage = 1;
				jump(pagerStart, pagerSize, url, container);
			} else if(pageFlag == 'E') {//末页
				currentPage = totalPage;
				pagerStart = pagerSize * (totalPage - 1);
				jump(pagerStart, pagerSize, url, container);
			} else if(pageFlag == 'J') {//选择页数
				//取消事件冒泡  
                event.stopPropagation();
                var offset = $(event.target).offset();
                var left = offset.left - 1 + 'px';
                var top = offset.top + $(event.target).height() - 146 + 'px';
                $('#pagerNumList').css({ top: top, left: left});  
				$('#pagerNumList').toggle();
			}
		});
		
		$('.jumpToPage').click(function() {
			
			var pagerSize = parseInt($('#pageForm input[name=pagerSize]').val());
			var pageNum = $.trim($(this).text());//要跳转到的页面数
			var currentPage = parseInt($('#pageForm input[name=currentPage]').val());//当前页面数
			
			if(pageNum == currentPage) {//要跳转到的页面如果跟当前页面时同一个页面 则不跳转
				return;
			}
			
			var pagerStart = pagerSize * (pageNum - 1);
			jump(pagerStart, pagerSize, url, container)
		});
	})
	
	function jump(pagerStart, pagerSize, url, el) {
		var params = {
			pagerStart 	: pagerStart, 
			pagerSize 	: pagerSize
		};
		$.post(url, params, function (data) {
			el.html(data);
		}, 'html');
	}
</script>

<form id="pageForm">
	<input type="hidden" name="pagerStart" value="${pager.pagerStart}">
	<input type="hidden" name="pagerSize" value="${pager.pagerSize}">
	<input type="hidden" name="totalPage" value="${pager.totalPage}">
	<input type="hidden" name="currentPage" value="${pager.currentPage}">
	<input type="hidden" name="totalSize" value="${pager.totalSize}">
</form>

<ul id="pagerNumList" class="show-pager-num">
</ul>  
<div class="page mt10">
	<div class="pagination">
		<ul>
			<li class="first-child"><a href="javascript:void(0);" pageFlag="F">首页</a></li>
		    <c:choose>
		    	<c:when test="${pager.hasPrePage == true}"><li><a href="javascript:void(0);" pageFlag="P">上一页</a></li></c:when>
		    	<c:otherwise><li class="disabled"><span>上一页</span></li></c:otherwise>
		    </c:choose>
		    
		    <li><a class="pager-num-view" href="javascript:void(0);" pageFlag="J">${pager.currentPage}/${pager.totalPage}</a></li>
		    
		    <c:choose>
		    	<c:when test="${pager.hasNextPage == true}"><li><a href="javascript:void(0);" pageFlag="N">下一页</a></li></c:when>
		    	<c:otherwise><li class="disabled"><span>下一页</span></li></c:otherwise>
		    </c:choose>
			<li class="last-child"><a href="javascript:void(0);" pageFlag="E">末页</a></li>
		</ul>
	</div>
	
	
</div>