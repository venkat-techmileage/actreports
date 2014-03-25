package act.reports.service;

import java.io.File;
import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailingService
{
	final Session session = Session.getInstance(System.getProperties(), null);

	@Autowired
	private JavaMailSender mailSender;
	
	private Logger logger=Logger.getLogger(MailingService.class);
	
	public MailingService(JavaMailSender mailSender)
    {
		this.mailSender=mailSender;
	}
	public MailingService()
    {
		
	}
	public boolean sendReciptThroughMail(MailingDetails mailDetails)
	{
		boolean receipt=true;
		try{
			//Transport bus = session.getTransport("smtp");
			//bus.connect();
			MimeMessage message=new MimeMessage(session);
			MimeMultipart content = new MimeMultipart();
			for(String pdfPath:mailDetails.getAttachments())
			{
				File pdfFile=new File(pdfPath);
				MimeBodyPart pdfContent=new MimeBodyPart();
				try
				{
					pdfContent.attachFile(pdfFile);
					content.addBodyPart(pdfContent);
				}
				catch (IOException e)
				{
					receipt=false;
					logger.error(e);
				}
				catch (MessagingException e)
				{
					receipt=false;
					logger.error(e);
				}			
			}
			if(receipt)
			{
				try
				{
					message.setFrom(new InternetAddress(mailDetails.getMailFrom()));
					message.setRecipients(Message.RecipientType.TO,mailDetails.getMailTo());
					message.setSubject(mailDetails.getMailSubject());
					message.setContent(mailDetails.getMailContent(),mailDetails.getContentType());
					message.setContent(content);
					mailSender.send(message);
					//bus.close();
				}
				catch (Exception e)
				{
					receipt=false;
					logger.error(e);
				}
			}
		}
		catch (Exception e)
		{
			receipt=false;
			logger.error(e);
		}
		return receipt;
	} 
}
