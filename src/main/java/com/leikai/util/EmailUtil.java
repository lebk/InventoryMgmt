package com.leikai.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.leikai.po.Os;
import com.leikai.po.User;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */

public class EmailUtil
{
	 static Logger logger = Logger.getLogger(EmailUtil.class);

	  public static void send(String uName, String uEmail, String targetVMLoc, Os srcVM, boolean isSuccessfullyCreateVM, Integer jobId)
	  {
	    logger.info("Send email start...");

	    String to = uEmail;

	    String from = VMFactoryConfigUtil.getEmailSender();

	    String host = VMFactoryConfigUtil.getEmailServer();
	    Properties properties = System.getProperties();
	    properties.setProperty("mail.smtp.host", host);
	    Session session = Session.getDefaultInstance(properties);

	    try
	    {
	      MimeMessage message = new MimeMessage(session);

	      message.setFrom(new InternetAddress(from));

	      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	      
	      message.setSentDate(new Date());

	      if (isSuccessfullyCreateVM == true)
	      {
	        String adminName = srcVM.getAdminname();
	        String adminPassword = srcVM.getAdminpassword();

	        message.setSubject("VM is created successfully!");

	        // create the message part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();

	        // set the email message text
	        messageBodyPart.setText("Hi " + uName + ",\n\nYour image is successfully created, please go to:\n\n" + "\t" + targetVMLoc
	            + ".\n\nWe suggest to use SCP to download the image.\n"
	            + "For each image(Configured OS), the autologin is enabled, at the sametime, we also has configured an admin user: (" + adminName + ":"
	            + adminPassword + ").\n");

	        //Multipart multipart = new MimeMultipart();
	        //multipart.addBodyPart(messageBodyPart);

	        // set the email attachment file

	        String logPath = VMUtil.getLocalZippedFile(jobId.toString());

	        MimeBodyPart attachmentPart = new MimeBodyPart();
	        FileDataSource fileDataSource = new FileDataSource(logPath){
	            @Override
	            public String getContentType()
	            {
	                return "application/octet-stream";
	            }
	        };
	        
	        attachmentPart.setDataHandler(new DataHandler(fileDataSource));
	        attachmentPart.setFileName(fileDataSource.getName());
	        //messageBodyPart.setFileName(logPath.substring(logPath.lastIndexOf("\\")));

	        Multipart multipart = new MimeMultipart();
	        
	        multipart.addBodyPart(messageBodyPart);
	        multipart.addBodyPart(attachmentPart);

	        // Put parts in message
	        message.setContent(multipart);

	      } else
	      {
	        message.setSubject("Fail to create the Image!");
	        message.setText("Hi " + uName + ",\n\nWe are failed to create the image, please contact the VMFactory team!");
	      }
	      Transport.send(message);
	      logger.info("Send email complete!");

	    } catch (Exception e)
	    {
	      e.printStackTrace();
	      logger.error("get unexpected exception while send email:" + e.getMessage());
	    }
	  }



}
