public class GuestLoggingInfo implements java.io.Serializable 
{ 
    private Date loggingDate = new Date(); 
    private String uid; 
    private transient String pwd; 
    
    GuestLoggingInfo() 
    { 
        uid = "guest"; 
        pwd = "guest"; 
    } 
    public String toString() 
    { 
        //same as above 
     } 
} 
