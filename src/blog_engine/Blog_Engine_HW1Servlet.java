package blog_engine;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Blog_Engine_HW1Servlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
		resp.setContentType("text/plain");
		//resp.getWriter().println("Hi, this is a prototype blog engine created by Alex Hoganson, a Computer Engineering student at UT.");
		resp.getWriter().println("Link Pressed");
		
	}
}
