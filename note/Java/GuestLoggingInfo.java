package test;

import java.util.Date;

public class GuestLoggingInfo implements java.io.Serializable 
{ 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date loggingDate = new Date(); 
    private String uid; 
    private transient String pwd; 
    
    GuestLoggingInfo() 
    { 
        uid = "guest"; 
        pwd = "guest"; 
    }
    
    GuestLoggingInfo(String usr, String password) 
    { 
        uid = usr; 
        pwd = password; 
    } 
    
    public String toString() {
		String password = null;
		if (pwd == null) {
			password = "NOT SET";
		} else {
			password = pwd;
		}
		return "login info: \n   " + "user: " + uid + "\n   logging date : " + loggingDate.toString()
				+ "\n   password: " + password;
	}

	public static void main(String args[]) {
		GuestLoggingInfo guestInfo = new GuestLoggingInfo();
		System.out.println(guestInfo.toString());
		
		GuestLoggingInfo guestInfo2 = new GuestLoggingInfo("MIKE", "MECHANICS");
		System.out.println(guestInfo2.toString());
	}
} 
