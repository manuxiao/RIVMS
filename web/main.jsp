<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>欢迎进入系统!</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="js-lib/ext-js/resources/css/ext-all.css">
<script type="text/javascript" src="js-lib/ext-js/adapter/ext/ext-base-debug.js"></script>
<script type="text/javascript" src="js-lib/ext-js/ext-all-debug.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/CheckboxField.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/Spinner.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/SpinnerStrategy.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/locale/micrite-base-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/ProgressBarPager.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/TabCloseMenu.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/util.js"></script>

<script type="text/javascript" src="standard/RowExpander.js"></script>

<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/Spinner.css">
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/micrite-all.css">
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/resources/css/CheckboxField.css">
<link rel="stylesheet" type="text/css" href="main.css">
<link rel="stylesheet" type="text/css" href="js-lib/ext-ux-js/fileuploadfield/css/fileuploadfield.css">
<script type="text/javascript" src="js-lib/ext-ux-js/fileuploadfield/FileUploadField.js"></script>
<script type="text/javascript" src="main.js"></script>

<script type="text/javascript" src="security/locale/micrite-security-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="crm/locale/micrite-crm-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="car/locale/micrite-car-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="enterprise/locale/micrite-enterprise-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="check/locale/micrite-check-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="dic/locale/micrite-dic-lang-<%=session.getAttribute("WW_TRANS_I18N_LOCALE")%>.js"></script>
<script type="text/javascript" src="myresource.js"></script>
</head>
<body>
<div id="x-loading-mask" style="width:100%; height:100%; background:#618BAC; position:absolute; z-index:20000; left:0; top:0;">&#160;</div>
  <div id="x-loading-panel" style="position:absolute;left:40%;top:40%;border:1px solid #9c9f9d;padding:2px;background:#d1d8db;width:300px;text-align:center;z-index:20001;">
    <div style="border:1px solid #c1d1d6;color:#666;background:white;padding:10px;margin:0;padding-left: 20px;height:30px;text-align:left;">
      <div id="load-status" style="height:30px;padding-top:5px;"><s:text name="loading.system"/></div>
    </div>
</div>

<s:hidden id="pageSize" name="pageSize"></s:hidden>
<script type="text/javascript" src="js-lib/ext-ux-js/ComplexGrid.js"></script>
<script type="text/javascript" src="js-lib/ext-ux-js/LovTreeCombo.js"></script>

<div id="header"></div>
<s:if test="%{skin=='Gray'}" >
<script>
Ext.util.CSS.swapStyleSheet('theme', 'js-lib/ext-ux-js/resources/css/xtheme-gray-extend.css');
</script>
</s:if>
</body>
</html>