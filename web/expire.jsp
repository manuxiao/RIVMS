<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
    这里的Access denied与403.jsp不同，这里是表示因为你没有登录，所以access denied
    403.jsp表示如果你登录了，但是URL拦截认证失败
--%>
超时信息:您登陆的网页已过期，请重新 <a id="session-expired" href="j_spring_security_logout">登入</a>