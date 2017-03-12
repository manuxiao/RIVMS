<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>applet测试</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>1.1 PrintThread (x2)</h1>
<object
    classid="clsid:CAFEEFAC-0016-0000-0017-ABCDEFFEDCBA"
    codebase="http://<%=request.getServerName() %>:<%=request.getServerPort()%><%=request.getContextPath()%>/javaplugin/jre-6u23-windows-i586.exe"
    WIDTH="450" HEIGHT="200">
    <PARAM NAME= CODE VALUE="org.gaixie.micrite.web.PrintApplet.class">
    <param name= "type" value = "application/x-java-applet;jpi-version=1.6.0_17">
</object>

    <applet code="org.gaixie.micrite.web.PrintApplet.class" width=450 height=100> 
    <param name="demicron" value="localhost">
	<param name="reg" value="A00046">
    </applet>
</body>
</html>