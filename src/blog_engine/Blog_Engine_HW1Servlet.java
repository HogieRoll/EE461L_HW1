package blog_engine;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
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
		
		System.out.println("Length"+pT.length());
		System.out.println("Length"+pC.length());
		if(pT.length()==0||pC.length()==0)
		{
			resp.sendRedirect("Post.jsp");
		}
		else
		{
			System.out.println("Not Empty");
			UserService userService = UserServiceFactory.getUserService();
		    User user = userService.getCurrentUser();
			if(user==null)
			{
				resp.sendRedirect("Post.jsp");
			}
			else
			{
				// 1) create a java calendar instance
				Calendar calendar = Calendar.getInstance();
				 
				// 2) get a java.util.Date from the calendar instance.
//				    this date will represent the current instant, or "now".
				calendar.setTimeZone(TimeZone.getTimeZone("US/Central"));
				java.util.Date now = calendar.getTime();
				
				
			    resp.getWriter().println(user.getEmail()+" "+now.toString());
			    Key postKey = KeyFactory.createKey("Postbook", "Wall");
			    Entity Post=new Entity("Post",postKey);
			    Post.setProperty("Title", pT);
			    Post.setProperty("Content", pC);
			    Post.setProperty("Date", calendar.getTime());
			    String DateDay=Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
			    Post.setProperty("DateDay", DateDay);
			    String DateMonth=Integer.toString(calendar.get(Calendar.MONTH)+1);
			    Post.setProperty("DateMonth", DateMonth);
			    String DateYear=Integer.toString(calendar.get(Calendar.YEAR));
			    Post.setProperty("DateYear", DateYear);
			    String DateHour=Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
			    String DateMinute=Integer.toString(calendar.get(Calendar.MINUTE));
			    String DateSecond=Integer.toString(calendar.get(Calendar.SECOND));
			    Post.setProperty("DateReadable", DateMonth+"/"+DateDay+"/"+DateYear+" "+DateHour+":"+DateMinute+":"+DateSecond);
			    Post.setProperty("Author", user.getEmail());
			    
			    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		        datastore.put(Post);
		        resp.sendRedirect("Home.jsp");
			}
		}
	}
}
