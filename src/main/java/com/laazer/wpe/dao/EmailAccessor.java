package com.laazer.wpe.dao;

import com.laazer.wpe.model.Email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Laazer
 */
public class EmailAccessor {

    private static final String EMAIL_HOST = "127.0.0.1";

    private final Session session;

    public EmailAccessor() {
        final Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", EMAIL_HOST);
        final Session session = Session.getDefaultInstance(prop);
        this.session = session;
    }

    public EmailAccessor(final String host, final String port,
                         final String uname, final String password) {
        final Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", host);
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        final Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(uname, password);
                    }
                });
        this.session = session;
    }

    public void sendEmail(final Email email) {
        try {
            MimeMessage message = new MimeMessage(this.session);
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(email.getSender()));
            // Set To Field: adding recipient's email to from field.
            for(final String rep : email.getRecipients()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(rep));
            }
            message.setSubject(email.getSubject());
            message.setContent(email.getBody(), "text/html");
            Transport.send(message);
        } catch (final MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
