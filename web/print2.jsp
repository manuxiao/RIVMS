<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div id="printPreview_micrite"></div>
<script type="text/javascript">
var htmlText = '<applet width="400" height="300" '
	+'type="application/x-java-applet;jpi-version=1.6.0_23" '
	+'codebase="http://<%=request.getServerName() %>:<%=request.getServerPort()%><%=request.getContextPath()%>/javaplugin/jre-6u23-windows-i586.exe">'
	+'<PARAM NAME="java_code" VALUE="com.hansheng.m2010.applet.PrintApplet.class">'
	+'<PARAM NAME="java_codebase" VALUE=".">'
	+'<PARAM NAME="archive" VALUE="mprintapplet.jar">'
	+'<param name="callbackJsFn" value="callbackJsFn">'
	+'<param name="checkIds" value="${ids}">'
	+'<param name="fontSize" value="12">'
	+'<param name="pageWidth" value="300">'
	+'<param name="pageHeight" value="410">'
	+'<param name="previewWidth" value="260">'
	+'<param name="previewHeight" value="300">'
<s:iterator value="checks" status="of">
+'<param name="str${of.index}" value="<s:date name="jianTime" format="yyyy-MM-dd" /> 至 <s:date name="endTime" format="yyyy-MM-dd" />">'
+'<param name="xy${of.index}" value="25,${(of.index+printIndex)*25+50}">' 
</s:iterator>
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
var checkIdsStrParam='';
function callbackJsFn(str){
	try{
		hasDone=true;
		checkIdsStrParam=str;
	}catch(e){alert(e);}
};
function fn(){
	if(hasDone){
		try{
			
			var checkIds = Ext.util.JSON.decode(checkIdsStrParam);

			var checkIdsStr='';
			for(var i=0;i<checkIds.length;i++) {
				checkIdsStr+='checkIds='+checkIds[i];
				if(i<checkIds.length-1){
					checkIdsStr+='&';
				}
			}
			
			Ext.Ajax.request({   
				url:'print2.action?action=confirm&'+checkIdsStr+'&printIndex=${printIndex}&id=${id}',
				success:function(response){
					var obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success){
	                	showMsg('success', '打印成功');
					}else{
						showMsg('failure', obj.message);
					}
					var w = Ext.WindowMgr.getActive();
					if(w!=null)
						w.close();
				}
				/*,
				failure: function(response) {
					showMsg('failure', response.responseText);
				}*/
			});
			
		}catch(e){alert('打印完毕后回调函数异常：'+e);}
	}else{
		setTimeout('fn()', 500);
	}
	
};
fn();
</script>