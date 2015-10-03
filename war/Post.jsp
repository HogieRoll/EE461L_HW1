<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
  <head>
    <link rel="stylesheet" href="bootstrap.css">
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
	<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>"><font color="blue">sign out</font></a>.)</p>
	<%
    } else {
	%>
	<p>Hello!
	<a href="<%= userService.createLoginURL(request.getRequestURI()) %>"><font color="blue">
	Sign in</font></a> to make posts or subscribe.</p>
	<%
	    }
	%>
	</div>
<nav class="navbar navbar-default">
  	<div class="container-fluid">
    	<div class="navbar-header">
    	<a class="navbar-brand" href="../Home.jsp">HogieRoll</a>
    	<ul class="nav navbar-nav">
        <%if(user!=null){%>
        <li><a href="../Post.jsp">Write Post</a></li>
        <%}else{%>
        <li><a href="#"><font color: "FFFF00">Write Post</font></a></li>
        <%}%>
      	</ul>
    	</div>
  </div>
</nav>
<form class="form-horizontal" action="/submit" method="get">
  <fieldset>
    <legend>Write Post</legend>
    <div class="form-group">
      <label class="col-lg-2 control-label" for="postTitle">Title</label>
      <div class="col-lg-10">
        <input class="form-control" id="postTitle" name="postTitle" type="text" placeholder="Title">
      </div>
    </div>   
    <div class="form-group">
      <label class="col-lg-2 control-label" for="postText">Textarea</label>
      <div class="col-lg-10">
        <textarea class="form-control" name="postContent" id="postText" rows="8"></textarea>
        <span class="help-block">All posts require a title.</span>
      </div>
    </div>
    <div class="form-group">
      <div class="col-lg-10 col-lg-offset-2">
        <button class="btn btn-primary" input type="submit">Submit</button>
        <a href="../Home.jsp" class="button">Cancel</a>
      </div>
    </div>
  </fieldset>
</form>
</body>
</html>