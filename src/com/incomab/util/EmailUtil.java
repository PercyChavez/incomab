/*
 * Developed by: Alexis Peralta Holyoak.
 */
package com.incomab.util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author peral
 */
//CLAS USED TO SEND EMAILS 
public class EmailUtil {
    private static final String from="broarshuacho@gmail.com";
    private static final String pass="dreamteam";
    public  void SendEmail(String to,String content,String subject,String ruta,String nombre) {           
        try {            
            //configure properties for smtp server
            Properties p=new Properties();
            p.put("mail.smtp.host","smtp.gmail.com");
            p.setProperty("smtp-relay.gmail.com", "587");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", from);
            p.setProperty("mail.smtp.auth", "true");
            //create session
            Session s=Session.getDefaultInstance(p, null);
            BodyPart texto=new MimeBodyPart();
            texto.setText(content);
            MimeMultipart m=new MimeMultipart();
            //to send attached files
            BodyPart adjunto=new MimeBodyPart();
            if(!ruta.equals("")){
                //configure path of attachaed file and its name
                adjunto.setDataHandler(new DataHandler(new FileDataSource(ruta)));
                adjunto.setFileName(nombre);
            }
            if(!ruta.equals("")){
                //add attached files to body
                m.addBodyPart(adjunto);
            }           
            m.addBodyPart(texto);
            MimeMessage message=new MimeMessage(s);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(m);
            //transport throug smtp server
            Transport transport=s.getTransport("smtp");
            transport.connect(from,pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
  
}


