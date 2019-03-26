package email;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
 
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import database.*;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
 
/**
 * A servlet that takes message details from user and send it as a new e-mail
 * through an SMTP server.
 *
 * @author www.codejava.net
 *
 */
public class PassSend extends HttpServlet {
    private String host;
    private String port;
    private String user;
    private String pass;
    Connection con;
 

    public void init() {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }
 

   
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        String to="dhayav98@gmail.com";
        String str1=request.getParameter("t1");
        Properties properties = System.getProperties();
       // properties.put("mail.smtp.host", host);
       properties.put( "mail.smtp.host","smtp.gmail.com");
        properties.put( "mail.smtp.socketFactory.port","465");
        properties.put( "mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put( "mail.smtp.auth","true");
        
//properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
 
        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
 
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                user, pass);
                    }
                });
        
 
/*       try
       {
           
            con=connection.connection1();
            Statement ss=con.createStatement();
            ResultSet rs=ss.executeQuery("select password, email from registration where username='"+str1+"'");
            if(rs.next())
            {*/
        try
        {
                 Message message = new MimeMessage(session);
 
            message.setFrom(new InternetAddress(user));
             message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Your Password");
            message.setContent("password","text/html");
            Transport transport = session.getTransport("smtp");
        // sends the e-mail
       transport.connect(host,user, pass);
       transport.send(message);
       out.println("Your Password has been sucessfully sent to your mail id");

            }
    
        
    catch (Exception e)
    {
            e.printStackTrace();
        }
 
    }     
 
        
    }
    
    
    
    