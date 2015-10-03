package blog_engine;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.util.Logging.*;
import java.util.logging.*;

@SuppressWarnings("serial")
public class GAEJCronServlet extends HttpServlet {
private static final Logger _logger = Logger.getLogger(GAEJCronServlet.class.getName());
@SuppressWarnings("deprecation")
public void doGet(HttpServletRequest req, HttpServletResponse resp)
throws IOException {

try {
_logger.info("Cron Job has been executed");

//Put your logic here
//BEGIN
Properties props = new Properties();
Session session = Session.getDefaultInstance(props, null);

//1) create a java calendar instance
Calendar calendar = Calendar.getInstance();
calendar.setTimeZone(TimeZone.getTimeZone("US/Central"));
 
// 2) get a java.util.Date from the calendar instance.
//    this date will represent the current instant, or "now".
java.util.Date now = calendar.getTime();
int DayofMonth=calendar.get(Calendar.DAY_OF_MONTH);
int Month=calendar.get(Calendar.MONTH)+1;
int Year=calendar.get(Calendar.YEAR);

DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
Key postKey = KeyFactory.createKey("Postbook", "Wall");
Query postquery = new Query("Post", postKey).addSort("Date", Query.SortDirection.DESCENDING);
List<Entity> posts = datastore.prepare(postquery).asList(FetchOptions.Builder.withLimit(10));
String msgBody = "Digest\n";int i=0;
boolean flag=false;
while(i<posts.size())
{
	Entity Post=posts.get(i);i++;
	if(Post.getProperty("DateDay").toString().equals(Integer.toString(DayofMonth))&&
	   Post.getProperty("DateMonth").toString().equals(Integer.toString(Month))&&
	   Post.getProperty("DateYear").toString().equals(Integer.toString(Year)))
	{
		msgBody+=Post.getProperty("Title").toString()+"\n";
		msgBody+=Post.getProperty("Content")+"\n";
		msgBody+=Post.getProperty("Author")+"\n";
		msgBody+=Post.getProperty("DateReadable")+"\n\n";
		flag=true;
	}
}
if(flag==false)
{
	return;
}
Key subKey = KeyFactory.createKey("SubList", "SubInfo");
Query query = new Query("Subscription", subKey).addSort("UserEmail", Query.SortDirection.ASCENDING);
List<Entity> Subscriptions = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

try {
	for(Entity Sub: Subscriptions)
	{
		 Message msg = new MimeMessage(session);
		 msg.setFrom(new InternetAddress("alexh.458@gmail.com", "HogieRoll Admin"));
		 msg.addRecipient(Message.RecipientType.TO,new InternetAddress(Sub.getProperty("UserEmail").toString(), "Mr. User"));
		 msg.setSubject("Daily HogieRoll Update");
		 msg.setText(msgBody);
		 Transport.send(msg);
		 System.out.println("Sent Email to"+Sub.getProperty("UserEmail").toString());
	}
   

} catch (AddressException e) {
    // ...
} catch (MessagingException e) {
    // ...
}
//END
}
catch (Exception ex) {
//Log any exceptions in your Cron Job
}
}

@Override
public void doPost(HttpServletRequest req, HttpServletResponse resp)
throws ServletException, IOException {
doGet(req, resp);
}
}