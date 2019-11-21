<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="all_popup" style="display: none;">
	<div class="popupLayer">
		<div style="position: fixed; top: 0px; left: 0px; width: 100%; height: 100%; z-index: 100; opacity: 0.9; background-color: rgb(0, 0, 0); "></div>
	</div>
	<div class="popup_box" style="width: 620px; height: 400px; cursor: pointer; border-top: 20px solid #666">
		<!-- 변경될 수 있는값은 html에서 작성 -->
		<iframe id="layerIframe" name="layerIframe" style="width:100%;height:100%" src="" mce_src='about:blank' scrolling='no' frameborder='0' title=""></iframe>
	</div>
</div>
<div id="loading-view" style="display: none;">
	<div class="popupLayer">
		<div class="back"></div>
	</div>
	<div class="popup_box" style="width: 40px; height: 40px; background: transparent">
		<img src='/resources/image/loading-motion.gif' />
	</div>
</div>
<FORM id="bookDetail" name="bookDetail"  method = "POST"  target = "layerIframe" action = "/bookDetail" >
	<input type = "hidden"  name="title"  value="" />
</FORM>