<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div id="printPreview_micrite"></div>
<script type="text/javascript">
var htmlText ='' 
	+'<object classid="clsid:CAFEEFAC-0016-0000-0017-ABCDEFFEDCBA" codebase="javaplugin/jinstall-6u17-windows-i586.cab#Version=6,0,170,4" WIDTH="450" HEIGHT="300">'
	+'    <PARAM NAME="CODE" VALUE="org.gaixie.micrite.web.PrintApplet.class">'
	+'    <param name="type" value="application/x-java-applet;jpi-version=1.6.0_17">'
	+'    <PARAM NAME="demicron" VALUE="localhost">'
	+'    <comment>'
	+'	<embed'
	+'            type = "application/x-java-applet;jpi-version=1.6.0_17" \'
	+'            CODE = org.gaixie.micrite.web.PrintApplet.class \'
	+'            WIDTH = 450 \'
	+'            HEIGHT = 300 \'
	+'            demicron ="localhost" \'
	+'fontSize="12" '
	+'pageWidth="300" '  
	+'pageHeight="410" '
	+'previewWidth="260" ' 
	+'previewHeight="300" '
	+'str0="${car.yingyunNo}" '
	+'xy0="30,20" '
	+'str1="${car.owner}" '
	+'xy1="60,40" '
	+'str2="${car.licenseNumber}" '
	+'xy2="60,60" '
	+'str3="${car.carType.name}" '
	+'xy3="60,80" '
	+'str4="<s:date name="car.evaluateDate" format="yyyy-MM-dd" />" '
	+'xy4="60,160" '
	+'str5="${car.skillRank.name}" '
	+'xy5="130,160" '
	+'	    pluginspage = "javaplugin/index.html#download">'
	+'	    <noembed>浏览器不支持 Java(tm).</noembed>'
	+'	</embed>'
	+'    </comment>'
	+'</object>';
       Ext.onReady(function(){
          var panel = new Ext.Panel({
           renderTo:'printPreview_micrite',
           //title:'hello', 
           html:htmlText
          });
      })
</script>