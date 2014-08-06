<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>

<div id="pager" class="dialogPageBox clearfix">
<form id="pageForm">
<input type="hidden" name="pager.totalSize" value="${pager.totalSize }">
<input type="hidden" name="pager.totalPage" value="${pager.totalPage }">
</form>
<input type="hidden" id="pageNum" name="pager.pageNum" value="${pager.pageNum}">
    <div class="total">总记录<span>${pager.totalSize}</span>条</div>
    <c:choose>
    <c:when test="${pager.totalPage > 1}">
    <ul>
        <li>
        <c:choose>
	       <c:when test="${pager.hasPrevPage}">
		      <a href="javascript:;" class="prev_next pageButton"  pageFlag="P">上一页</a>
		   </c:when>
		   <c:otherwise>
			  <a href="javascript:;" class="prev_next prev_next_dis"  pageFlag="P">上一页</a>
			</c:otherwise>
		</c:choose>
        </li>
        <c:forEach items="${pager.pageButton}" var="buttonList" varStatus="st">
                  <li><a href="javascript:;" class="num" id="num_${st.index+1}">${st.index+1}</a></li>
        </c:forEach>
        <c:choose>
                 <c:when test="${pager.totalPage >= 6}">
                    <!-- 如果是第一页加载1 2 页  -->
                    <c:choose>
                       <c:when test="${pager.pageNum == 1}">
                           <li><a href="javascript:;" class="num" id="num_1">1</a></li>
                           <li><a href="javascript:;" class="num" id="num_2">2</a></li>
                       </c:when>
                    <c:otherwise>
                       <li><a href="javascript:;" class="num" id="num_1">1</a></li>
                    </c:otherwise>
                    </c:choose>
                    <!-- 从第三页开始前面加... -->
                    <c:choose>
                       <c:when test="${pager.pageNum >= 3 }">
                          <li><a href="javascript:;">…</a></li>
                       </c:when>
                    </c:choose>
                    <c:choose>
                       <c:when test="${pager.pageNum>1 && pager.pageNum < pager.totalPage}">
                       <!-- 如果不是第二页要加载前一页（第一页已经在前面添加） -->
                       <c:choose>
                           <c:when test="${pager.pageNum>2}">
                              <li><a href="javascript:;" class="num" id="num_${pager.pageNum-1}">${pager.pageNum-1}</a></li>
                           </c:when>
                       </c:choose>
                       <li><a href="javascript:;" class="num">${pager.pageNum}</a></li>
                       <!-- 如果不是最后第二页要加载后一页（最后一页已经在后面添加） -->
                       <c:choose>
                           <c:when test="${pager.pageNum+1 < pager.totalPage}">
                             <li><a href="javascript:;" class="num" id="num_${pager.pageNum+1}">${pager.pageNum+1}</a></li>
                           </c:when>
                       </c:choose>
                       </c:when>
                    </c:choose>
                    <!-- 不是后三页后面拼接... -->
                    <c:choose>
                       <c:when test="${pager.totalPage - pager.pageNum >=2}">
                           <li><a href="javascript:;">…</a></li>
                       </c:when>
                    </c:choose>
                    <!-- 最后一页加载时要同时加载前一页 -->
                    <c:choose>
                       <c:when test="${pager.totalPage == pager.pageNum}">
                           <li><a href="javascript:;" class="num" id="num_${pager.pageNum-1}">${pager.pageNum-1}</a></li>
                           <li><a href="javascript:;" class="num" id="num_${pager.pageNum}">${pager.pageNum}</a></li>
                       </c:when> 
                       <c:otherwise>
                           <li><a href="javascript:;" class="num" id="num_${pager.totalPage}">${pager.totalPage}</a></li>
                       </c:otherwise>
                    </c:choose>
                 </c:when>
        </c:choose>
        <li>
        <c:choose>
				<c:when test="${pager.hasNextPage}">
					<a href="javascript:;" class="prev_next pageButton" pageFlag="N" >下一页</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:;" class="prev_next prev_next_dis" pageFlag="N" >下一页</a>
				</c:otherwise>
		</c:choose>
        </li>
        <li class="jump"><span>跳转</span><input id="jump" type="text" /><span>页</span></li>
        <li class="num_jump_box"><a href="javascript:;" class="num_jump" id="jumpPage">GO</a></li>
        </c:when>
       </c:choose>
    </ul>
</div>