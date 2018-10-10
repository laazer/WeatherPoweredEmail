package com.laazer.wpe.dao;

import com.laazer.wpe.model.Email;
import com.laazer.wpe.model.User;
import com.laazer.wpe.model.WeatherDisplayData;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
public class EmailAccessor {

    private static final String EMAIL_HOST = "127.0.0.1";
    private static final String WPE_SENDER = "no-reply.weather_powered_email@laazer.com";
    private static final String WPE_SUBJECT = " Day Forecast";

    private SpringTemplateEngine templateEngine;
    private final Session session;

    public EmailAccessor() {
        final Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.host", EMAIL_HOST);
        final Session session = Session.getDefaultInstance(prop);
        this.session = session;
    }

    public EmailAccessor(final String host, final String port,
                         final String uname, final String password,
                         final SpringTemplateEngine templateEngine) {
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
        this.templateEngine = templateEngine;
    }

    public void sendEmail(final Email email) {
        try {
            final MimeMessage message = new MimeMessage(this.session);
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
            log.error("Error sending email.", mex);
        }
    }

    public void sendWeatherReportEmail(final User user, final WeatherDisplayData today,
                                       final List<WeatherDisplayData> weekForecast) {
        try {
            final MimeMessage message = new MimeMessage(this.session);
            final MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(WPE_SENDER));
            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject(weekForecast.size() + WPE_SUBJECT);
            // Create the content for this email
            final Context context = new Context();
            context.setVariable("user", user);
            context.setVariable("mdf", weekForecast.subList(0, 5));
            context.setVariable("today", today);
            final String body = this.templateEngine.process("email/weatherEmail", context);
            helper.setText(body, true);
            Transport.send(message);
        } catch (final MessagingException mex) {
            log.error("Error sending email.", mex);
        }
    }
}
