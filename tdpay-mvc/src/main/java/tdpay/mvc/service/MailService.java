package tdpay.mvc.service;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.isols.common.utils.StringUtils;
import tdpay.mvc.common.MailMessage;
import tdpay.mvc.common.MailMessage.MailMessageType;
import tdpay.mvc.service.shared.SystemPropertyService;

/**
 * メールサービスクラス
 */
@Service
public class MailService {

    @SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(MailService.class);

    @Autowired
    protected SystemPropertyService systemPropertyService;

    public MailMessage creaateMailMessage(final MailMessageType messageType) {
		MailMessage message = new MailMessage();
		return message;
	}

    /**
     * 送信する
     */
    public void send(final MailMessage message) {

    	System.setProperty("sun.nio.cs.map", "x-windows-iso2022jp/ISO-2022-JP");

		Properties properties = new Properties();
		properties.put("mail.smtp.host", message.getSmtpHost());
		properties.put("mail.smtp.port", message.getSmtpPort());
		properties.put("mail.smtp.auth", message.getSmtpAuth());
		properties.put("mail.smtp.starttls.enable", message.getStartTls());
        if (message.getStartTls() != null && message.getStartTls().equals(Boolean.TRUE)) {
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.socketFactory.fallback", "true");
        }
        properties.put("mail.smtp.connectiontimeout", "10000");
		properties.put("mail.smtp.timeout", "10000");
		//props.put("mail.debug", "true");

    	Session session = Session.getInstance(properties,
		    new javax.mail.Authenticator() {
		       protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(message.getUserName(), StringUtils.decrypt(message.getPassword()));
		       }
		    });

    	MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.setFrom(new InternetAddress(message.getFrom()));
			mimeMessage.setSubject(message.getSubject(), message.getEncoding());

	        if (message.getToList() != null) {
	            for (final String to : message.getToList()) {
	                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	            }
	        }

	        if (message.getCcList() != null) {
	            for (final String cc : message.getCcList()) {
	                mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
	            }
	        }

            Multipart multiPart = new MimeMultipart();

            BodyPart textPart = new MimeBodyPart();
            textPart.setText(message.getText());
            multiPart.addBodyPart(textPart);

            if (message.getAttachmentFileList() != null) {
                for (final File file : message.getAttachmentFileList()) {
                    BodyPart attachedFilePart = new MimeBodyPart();
                    DataSource fds = new FileDataSource(file);
                    attachedFilePart.setDataHandler(new DataHandler(fds));
                    attachedFilePart.setFileName(file.getName());
                    multiPart.addBodyPart(attachedFilePart);
                }
            }
            mimeMessage.setContent(multiPart);
            mimeMessage.saveChanges();
        } catch (MessagingException e) {
			logger.error(e.getMessage(), e);
        }

    	try {
			Transport.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		}
    }
}
