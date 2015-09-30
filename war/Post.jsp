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
  	<nav class="navbar navbar-default">
  	<div class="container-fluid">
    	<div class="navbar-header">
     	 <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
      	  <span class="sr-only">Toggle navigation</span>
       	  <span class="icon-bar"></span>
       	  <span class="icon-bar"></span>
       	  <span class="icon-bar"></span>
      	</button>
      	<a class="navbar-brand" href="../Home.jsp">HogieRoll</a>
    	</div>

    	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      	  <ul class="nav navbar-nav">
        	<li><a href="#">About <span class="sr-only">(current)</span></a></li>
        	<%if(user!=null){%>
        	<li><a href="../Post.jsp">Write Post</a></li>
        	<%}else{%>
        	<li><a href="#"><font color: "FFFF00">Write Post</font></a></li>
        	<%}%>
        	<li class="dropdown">
          <a class="dropdown-toggle" role="button" aria-expanded="false" href="#" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">All</a></li>
            <li><a href="#">Most Popular</a></li>
            <li><a href="#">Least Popular</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li>
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
        <button class="btn btn-default" type="reset">Cancel</button>
        <button class="btn btn-primary" input type="submit">Submit</button>
      </div>
    </div>
  </fieldset>
</form>
</body>
</html>