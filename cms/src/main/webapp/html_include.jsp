<%
	String contextPath = request.getContextPath();
%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>

<link rel="stylesheet" href="<%=contextPath%>/admin/css/common.css" />
<link rel="stylesheet" href="<%=contextPath%>/admin/css/main.css" />
<link rel="stylesheet" href="<%=contextPath%>/admin/css/core.css" />

<script type="text/javascript" src="<%=contextPath%>/resources/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=contextPath%>/resources/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/resources/js/additional-methods.js"></script>
<script type="text/javascript" src="<%=contextPath%>/resources/js/cookie.js"></script>
<script type="text/javascript" src="<%=contextPath%>/resources/js/jquery.SuperSlide.js"></script>

<script type="text/javascript">
	jQuery.ctx = '<%=contextPath%>';
	
	$.ajaxSetup({'error' : myfunc});
	
	function myfunc(XMLHttpRequest, textStatus, errorThrown){
		if(XMLHttpRequest.status == 403){
			window.top.location = '${ctx}/index.jsp';
		} else if(XMLHttpRequest.status==500){
			window.top.location = '${ctx}/index.jsp';
		} else if(XMLHttpRequest.status==408){
			window.top.location = '${ctx}/index.jsp';
		}
	}
</script>
		