<%@ page language="java" isErrorPage="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--超时信息:<s:text name="exception.unhandled"/>--%>
<%
	String message = null;
	//System.out.println("llll5="+exception);
	if (exception != null) {
		//System.out.println("llll="+exception);
		message = exception.getMessage();
		System.out.println("e1:"+exception.getMessage()+","+exception.getLocalizedMessage());
		//exception.printStackTrace();//new java.io.PrintWriter(out) 
	} else if (request.getAttribute("javax.servlet.error.exception") != null) {
		Exception e = ((Exception) request
				.getAttribute("javax.servlet.error.exception"));
		message = e.getMessage();
		System.out.println("e2:"+e.getMessage()+","+e.getLocalizedMessage());
		//e.printStackTrace();
	}else{
		message="";
	}
//<s:text name="exception.unhandled" />
//
%>
{'message':'<%=message %><b> check </b>your authorities!','success':false}
