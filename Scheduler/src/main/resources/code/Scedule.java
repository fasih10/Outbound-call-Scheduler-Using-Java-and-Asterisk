package code;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.event.OriginateResponseEvent;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.action.StatusAction;


public class Scedule extends TimerTask
{
    private ManagerConnection managerConnection;

    public Scedule() throws IOException, IllegalStateException, AuthenticationFailedException, TimeoutException
    {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(
                "localhost", "manager", "fas123");

        this.managerConnection = factory.createManagerConnection();
     
     
        managerConnection.login();
   
    }

    public void run()
    {
    	String number;
    	ResultSet rs = null;
    	 Connection con = null;
    	 Statement mystatement1 = null;
    	
		    String url = "jdbc:mysql://localhost:3306/Schedule";
		    String username = "root";
		    String password = "Fas@10";
		    try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		      try {
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      try {
				mystatement1 = con.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
        OriginateAction originateAction;
        ManagerResponse originateResponse;
String sql1="SELECT * FROM call_info WHERE status="+100;

try {

	rs = mystatement1.executeQuery(sql1);

} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

try {
	while(rs.next())
	{
	 number= rs.getString("cell_no");
	   System.out.println(number);
	   
	   originateAction = new OriginateAction();
	   originateAction.setChannel("SIP/" + number);
	   originateAction.setContext("default");
	   originateAction.setExten("1300");
	   originateAction.setPriority(new Integer(1));
	   originateAction.setTimeout(new Integer(30000));
	   originateAction.setAsync(true);
	   
	
	   
	   // connect to Asterisk and log in
	 
	   
	   
	   // send the originate action and wait for a maximum of 30 seconds for Asterisk
	   // to send a reply
	   originateResponse = managerConnection.sendAction(originateAction, 30000);
	  
	   // print out whether the originate succeeded or not
	   
	   System.out.println(originateResponse.getResponse());
	   
	
	   String userresponse=originateResponse.getResponse();
	   
	   
	   
	 
	
	   
	  
	
	   // and finally log off and disconnect
	 
	}
} catch (IllegalArgumentException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IllegalStateException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (TimeoutException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
//managerConnection.logoff();
    }


      
    
    

    public static void main(String[] args) throws Exception
    {
        
        Timer timer = new Timer();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormatter .parse("2021-01-19 13:05:45");
        int period = 10000;//10secs
        timer.schedule(new Scedule(),0,period );

        
        
    }
}