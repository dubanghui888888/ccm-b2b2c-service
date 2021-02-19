package com.mb.ext.web.util;

import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.service.spec.ConnectionDTO;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.entity.AbstractBaseEntity;
import com.mb.framework.util.ConfigConstants;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

@Repository
@Component
public class ConnectivityCheckUtil extends AbstractDAO<AbstractBaseEntity>
{

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private JavaMailSender sender;

	/**
	 * 
	 * This method is used for testing database connection;
	 * 
	 * @return
	 */
	public ConnectionDTO getDBConnectivity()
	{

		String dbConnMessage = ConfigConstants.FAILURE_MSG;
		ConnectionDTO conn = new ConnectionDTO();

		try
		{

			String result = em.createNativeQuery("select 1 from DUAL").getSingleResult().toString();

			if (null != result)
			{

				dbConnMessage = ConfigConstants.SUCCESS_MSG;
			}
		}
		catch (Exception e)
		{
			logger.error("Error occurred in testing database connectivity", e);
		}
		conn.setHostName(propertyRepository.getProperty(ConfigConstants.DB_HOST));
		conn.setPort(propertyRepository.getProperty(ConfigConstants.DB_PORT));
		conn.setAccessTime(GregorianCalendar.getInstance().getTime().toString());
		conn.setVersion(propertyRepository.getProperty(ConfigConstants.VERSION_ID));
		conn.setMessage(dbConnMessage);
		logger.debug("Connection details for database" + conn.getHostName() + conn.getPort() + conn.getAccessTime() + conn.getMessage());
		return conn;
	}


}
