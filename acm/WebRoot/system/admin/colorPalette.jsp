<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<G4Studio:html title="调色板" >
<G4Studio:import src="/system/admin/js/colorPalette.js"/>
<G4Studio:body>
<G4Studio:div key="colorPaletteDiv">
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="288" height="160">
  <param name="movie" value="./resource/flash/colorPicker.swf" />
  <param name="quality" value="high" />
  <embed src="./resource/flash/colorPicker.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="288" height="160"></embed>
</object>
</G4Studio:div>
</G4Studio:body>
</G4Studio:html>