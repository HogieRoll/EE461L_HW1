package blog_engine;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

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
	}
}
