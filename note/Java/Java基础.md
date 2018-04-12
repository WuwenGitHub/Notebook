# 一、Java编译与执行

  * 将源文件编译成字节码文件 --> java命令
  * 解释执行字节码文件  --> Javac命令
  * 命令寻找  --> path变量路径

# 二、Java Serialization 与 Transient

   Serialization:
    把对象的状态存储到硬盘上去，等需要的时候就可以再把它读出来使用
   
   Transient:
      对特定的对象数据在serialization时不进行存储
      底层的java虚拟机来说，该类型的变量不是一个类的永久性的状态
      ```java
      import java.io.*;
      import java.util.Date;
      
      public class LoggingInfo implements java.io.Serializable {
          private static final long serialVersionUID = 1L;
          private Date loggingDate = new Date();
          private String uid;
          private transient String pwd;

          LoggingInfo(String user, String password) {
              uid = user;
              pwd = password;
          }

          public String toString() {
              String password = null;

              if (pwd == null) {
                 password = "NOT SET";
              } else {
                 password = pwd;
              }

              return "logon info: \n   " + "user: " + uid + "\n   logging date : " + loggingDate.toString()
                  + "\n   password: " + password;
          }

          public static void main(String args[]) {
              LoggingInfo logInfo = new LoggingInfo("MIKE", "MECHANICS");
              System.out.println(logInfo.toString());

              try {
                  ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("logInfo.out"));
                  o.writeObject(logInfo);
                  o.close();
              } catch (Exception e) {// deal with exception}
                  System.out.println("hello world !");
              }

              try {
                  ObjectInputStream in = new ObjectInputStream(new FileInputStream("logInfo.out"));
                  LoggingInfo logInfo1 = (LoggingInfo) in.readObject();
                  System.out.println(logInfo1.toString());
              } catch (Exception e) {
                  // deal with exception
              }
          }
       }
     ```
