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
public class Subscribe_Servlet extends HttpServlet 
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException 
	{
			UserService userService = UserServiceFactory.getUserService();
		    User user = userService.getCurrentUser();
			if(user==null)
			{
				resp.sendRedirect("Home.jsp");
			}
			else
			{				
			    Key subKey = KeyFactory.createKey("SubList", "SubInfo");
			    Entity Subscription=new Entity("Subscription",subKey);
			    Subscription.setProperty("UserEmail", user.getEmail());
			    
			    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			    Query query = new Query("Subscription", subKey).addSort("UserEmail", Query.SortDirection.ASCENDING);
			    List<Entity> Subscriptions = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
			    for(Entity Sub: Subscriptions)
			    {
			    	if(Sub.getProperty("UserEmail").equals(user.getEmail()))
			    	{
			    		resp.sendRedirect("Home.jsp");
			    		System.out.println("Already Subscribed");
			    		return;
			    	}
			    }
		        datastore.put(Subscription);
		        resp.sendRedirect("Home.jsp");
			}
		}
	}