package com.mb.ext.web.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mb.ext.core.service.spec.ConnectionDTO;
import com.mb.ext.web.util.ConnectivityCheckUtil;
import com.mb.framework.util.log.LogHelper;
import org.springframework.web.bind.annotation.ResponseBody;


/**数据库连接测试
 *
 * @author B2C商城
 *
 */
@Controller
public class ConnectionTestController
{
	private static int refreshCount = 0;

	private static final int TOTAL_ALLOWED_REFRESH_COUNT = 200;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Autowired
	private ConnectivityCheckUtil connCheckUtil;

	/**测试数据库连接
	 * @param model
	 * @return view
	 */
	@RequestMapping(value = "/testConnection", method = RequestMethod.GET)
	@ResponseBody
	public String getConnectionDetails(ModelMap model)
	{
		String viewName = "testConnection";
		refreshCount++;
		if (refreshCount > TOTAL_ALLOWED_REFRESH_COUNT)
		{
			viewName = "";
		}
		else
		{

			try
			{

				logger.info("getConnectionDetails");
				List<ConnectionDTO> connList = new ArrayList<ConnectionDTO>();
				// test database connectivity
				ConnectionDTO dbConn = connCheckUtil.getDBConnectivity();
				viewName = dbConn.getMessage();
			}
			catch (Exception e)
			{
				logger.error("Exception occurred in connectivity test controller", e);
			}
		}
		return viewName;
	}

}
