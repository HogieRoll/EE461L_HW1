package blog_engine;

import java.io.IOException;
import javax.servlet.http.*;
import java.util.List;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@SuppressWarnings("serial")
public class Unsubscribe_Servlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
			UserService userService = UserServiceFactory.getUserService();
		    User user = userService.getCurrentUser();
			Key subKey = KeyFactory.createKey("SubList", user.getEmail());
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.delete(subKey);
			resp.sendRedirect("Home.jsp");
			}
		}
