<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<G4Studio:html title="软哈计数" uxEnabled="true">

<%
	String powerBrandJson = (String) request.getAttribute("powerBrandJson");
	String policyBrandJson = (String) request.getAttribute("policyBrandJson");
%>
<G4Studio:script>
	var powerBrandJson = <%=powerBrandJson %>;
	var policyBrandJson = <%=policyBrandJson %>;
</G4Studio:script>
<G4Studio:import src="/app/modules/consume/js/highqualitybrand.js"/>
<G4Studio:import src="/resource/extcomponent/BaseGrid.js"/>
<G4Studio:body>
</G4Studio:body>
</G4Studio:html>