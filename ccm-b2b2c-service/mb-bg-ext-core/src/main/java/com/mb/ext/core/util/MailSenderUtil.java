package com.mb.ext.core.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

@Component
public class MailSenderUtil
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	@Qualifier("mailSender")
	private JavaMailSender sender;

	/**
	 * 
	 * This method is used for sending email with pdf as an attachment.
	 * 
	 * @param file
	 * @param sentTo
	 * @param ccTo
	 */
	public void sendMail(final String subject, final String body, final String sentTo, final String ccTo, final String fileName) throws BusinessException
	{
		try
		{

			// send mail with pdf as an attachment
			sender.send(new MimeMessagePreparator()
			{
				/**
				 * 
				 * This method is used to prepare the Mimemessage template.
				 * 
				 * @param mimeMessage
				 * @throws Exception
				 */
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception
				{
					MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					messageHelper.setTo(sentTo);
//					messageHelper.setCc(ccTo);

					 messageHelper.setFrom(propertyRepository.getProperty("mail.smtp.username"));

					 messageHelper.setSubject(subject);

					messageHelper.setText(body, true);
					/*messageHelper.addAttachment(fileName, new InputStreamSource()
					{
						*//**
						 * 
						 * This method is used for getting the file content.
						 * 
						 * @return
						 * @throws IOException
						 *//*
						@Override
						public InputStream getInputStream() throws IOException
						{
							return new ByteArrayInputStream(fileContent);
						}
					});*/

				}

			});
		}
		catch (Exception e)
		{
			logger.error("Exception occurred while sending email", e);
			
		}
	}
}
