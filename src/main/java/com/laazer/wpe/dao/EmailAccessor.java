package com.laazer.wpe.dao;

import com.laazer.wpe.model.Email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Laazer
 */
public class  EmailAccessor {

    private static final String EMAIL_HOST = "localhost";

    private final Session session;

    public EmailAccessor() {
        final Properties prop = System.getProperties();
        final Session session = Session.getDefaultInstance(prop);
        prop.setProperty("mail.smtp.host", EMAIL_HOST);
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
            message.setText(email.getText());
            Transport.send(message);
        } catch (final MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
