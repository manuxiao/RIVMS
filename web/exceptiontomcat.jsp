<%@ page language="java" isErrorPage="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
System.out.println("this is tomcat exception.");
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
		
		System.out.println("javax.servlet.error.status_code="+request.getAttribute("javax.servlet.error.status_code"));
		System.out.println("javax.servlet.error.message"+request.getAttribute("javax.servlet.error.message"));
		message=(String)request.getAttribute("javax.servlet.error.message");
//		Enumeration enu = request.getAttributeNames();
//		while(enu.hasMoreElements()){
//			String name=(String)enu.nextElement();
//			System.out.println(name+"="+request.getAttribute(name));
//		}
	}
//<s:text name="exception.unhandled" />
//
%>
{'message':'<%=message %>','success':false}