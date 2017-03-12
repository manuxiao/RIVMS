<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div id="printPreview_micrite"></div>
<script type="text/javascript">
var htmlText = '<applet width="550" height="380" '
	+'type="application/x-java-applet;jpi-version=1.6.0_23" '
	+'codebase="http://<%=request.getServerName() %>:<%=request.getServerPort()%><%=request.getContextPath()%>/javaplugin/jre-6u23-windows-i586.exe">'
	+'<PARAM NAME="java_code" VALUE="com.hansheng.m2010.applet.PrintApplet.class">'
	+'<PARAM NAME="java_codebase" VALUE=".">'
	+'<PARAM NAME="archive" VALUE="mprintapplet.jar">'
	+'<param name="callbackJsFn" value="callbackJsFn">'
	+'<param name="fontSize" value="19">'
	+'<param name="pageWidth" value="600">'  
	+'<param name="pageHeight" value="410">'
	+'<param name="previewWidth" value="600">'  
	+'<param name="previewHeight" value="410">'
	+'<param name="str0" value="违章处理联系单">' 
	+'<param name="xy0" value="230,70">'
	+'<param name="str1" value="单位(车主)：${car.owner}">' 
	+'<param name="xy1" value="140,110">'
	+'<param name="str2" value="车号：${car.licenseNumber}">' 
	+'<param name="xy2" value="190,130">'
	+'<param name="str3" value="经查因：二级维护超期${-car.daysToExpired}天(<s:date name="car.maintainDateEnd" format="yyyy-MM-dd" />到期)">' 
	+'<param name="xy3" value="120,160">'
	+'<param name="str4" value="请按有关法规予以处罚。">'
	+'<param name="xy4" value="120,180">'
	+'<param name="str5" value="签发人：慈溪市公管所">'
	+'<param name="xy5" value="250,240">'  
	+'<param name="str6" value="<s:date name="currentDate" format="yyyy-MM-dd" />">'
	+'<param name="xy6" value="280,260">'  
+'</applet>';
var printPanel;//	printPanel.close(); 
Ext.onReady(function(){
	printPanel = new Ext.Panel({
		renderTo:'printPreview_micrite',
		//title:'hello', 
		html:htmlText
	});
});
var hasDone=false;
function callbackJsFn(str){
	try{
		hasDone=true;
	}catch(e){alert(e);}
};
function fn(){
	if(hasDone){
		try{
			var w = Ext.WindowMgr.getActive();
			if(w!=null)
				w.close();
		}catch(e){alert(e);}
	}else{
		setTimeout('fn()', 500);
	}
};
fn();
</script>