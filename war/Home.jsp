<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <link rel="stylesheet" href="default.css">
    <title>HogieRoll</title>
  </head>
  <body>
  <div style="background-color:grey; color:white;">
  	<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
      pageContext.setAttribute("user", user);
	%>
	<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
	<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>"><font color="FFFFFF">sign out</font></a>.)</p>
	<%
    } else {
	%>
	<p>Hello!
	<a href="<%= userService.createLoginURL(request.getRequestURI()) %>"><font color="FFFFFF">
	Sign in</font></a> to make posts or subscribe.</p>
	<%
	    }
	%>
	</div>
	<center> 
      <h1>  HogieRoll Blog!  </h1> 
    </center>
    <div style="background-color:white; color:grey; padding:20px; float:left;">
		<a href="blog_engine_hw1">About</a></br>
		<a href="blog_engine_hw1">Make Post</a></br>
	</div>
	<div style="background-color:grey; color:grey; padding:20px; float:left;">
		<a href="blog_engine_hw1">About</a></br>
		<a href="blog_engine_hw1">Make Post</a></br>
	</div>
  </body>
</html>
