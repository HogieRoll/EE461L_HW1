<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
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
        <li align="right"><a href="/subscribe">Subscribe</a></li>
        <li align="right"><a href="/unsubscribe">UnSubscribe</a></li>
      	</ul>
    	</div>
  </div>
</nav>
<div class="jumbotron">
  <div align="left">
  <h1>HogieRoll Blog Engine</h1>
  <p>An efficient platform for unfiltered thoughts.  The best kind.</p>
  </div>
  <span><img src="images/Family-of-Hoagies.jpg" alt="Hoagie Roll" style="width:820px;height:452px;"></span>
</div>
<legend><font size="10">Recent Posts</font><span><a href="../Archive.jsp">View Archived Posts</a></span></legend>
<%
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
Key postKey = KeyFactory.createKey("Postbook", "Wall");
// Run an ancestor query to ensure we see the most up-to-date
// view of the Greetings belonging to the selected Guestbook.
Query query = new Query("Post", postKey).addSort("Date", Query.SortDirection.DESCENDING);
List<Entity> posts = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
if(posts.size()==0)
{
%><p1>There are no posts to show</p1><%
}
System.out.println(posts.toString());
int i=0;
while(i<5&&i<posts.size())
{
  Entity Post=posts.get(i);i++;
  pageContext.setAttribute("Post_Content",Post.getProperty("Content"));
  pageContext.setAttribute("Post_Title", Post.getProperty("Title"));
  pageContext.setAttribute("Post_Author", Post.getProperty("Author"));
  pageContext.setAttribute("Post_DateReadable", Post.getProperty("DateReadable"));
  %>
  	<br><div class="panel panel-default">
  	<div class="panel-heading">
    	<h3 class="panel-title"><h2>${fn:escapeXml(Post_Title)}<span><font size= "4">   -${fn:escapeXml(Post_Author)}</font></span></h2></h3>
  	</div>
  	<div class="panel-body">
    	<blockquote>${fn:escapeXml(Post_Content)}</blockquote>
    	<p>${fn:escapeXml(Post_DateReadable)}</p>
  		</div>
	</div></br>
  <%
}
%>
</body>
</html>
