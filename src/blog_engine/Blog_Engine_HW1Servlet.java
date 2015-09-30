package blog_engine;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@SuppressWarnings("serial")
public class Blog_Engine_HW1Servlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
		resp.setContentType("text/plain");
		//resp.getWriter().println("Hi, this is a prototype blog engine created by Alex Hoganson, a Computer Engineering student at UT.");
		resp.getWriter().println("Link Pressed");
		String pT=req.getParameter("postTitle");
		String pC=req.getParameter("postContent");
		
		resp.getWriter().println(pT);
		resp.getWriter().println(pC);
		if(pT.length()==0||pC.length()==0)
		{
			resp.sendRedirect("../Post.jsp");
		}
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
		if(user==null)
		{
			resp.sendRedirect("../Home.jsp");
		}
		// 1) create a java calendar instance
		Calendar calendar = Calendar.getInstance();
		 
		// 2) get a java.util.Date from the calendar instance.
//		    this date will represent the current instant, or "now".
		java.util.Date now = calendar.getTime();
		
	    resp.getWriter().println(user.getEmail()+" "+now.toString());
	    Key postKey = KeyFactory.createKey("Postbook", "Wall");
	    Entity Post=new Entity("Post",postKey);
	    Post.setProperty("Title", pT);
	    Post.setProperty("Content", pC);
	    Post.setProperty("Date", now);
	    Post.setProperty("Author", user.getEmail());
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        datastore.put(Post);
        resp.sendRedirect("../Home.jsp");
	}
}
