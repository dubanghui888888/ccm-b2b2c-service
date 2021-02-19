package com.mb.framework.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @author LUOCHANGQING
 *
 */
/**
 * The Logging wrapper class for system. <br>
 * Entire system should use this class to log the system logs.<br>
 * Currently configured using log4j. <br>
 * If there is a need to change using other Logging machenism, by changing this
 * class will do.<br>
 * Without affecting other classes.<br>
 * <br>
 * Note: it is a singleton per class for performance tuning <br>
 */
public final class LogHelper implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;

	private Logger log;

	private static final String PREFIX = "";

	private static final String BIZ_ERROR_PREFIX = "(BE)";

	private static final String TRACE_PREFIX = "(T) ";

	private static final String INTERFACE_PREFIX = "(I) ";

	private static final String PERFORMANCE_PREFIX = "(P) ";

	private static final String STACK_TRACE = "[Exception Caught Caused By:]";

	private static Map<String, LogHelper> loggerContainer;

	private static LogHelper instance;

	/**
	 * @param className
	 * @param logenable
	 */
	private LogHelper(String className, boolean logenable)
	{
		log = LoggerFactory.getLogger(className);
	}

	/**
	 * Return the LogHelper class
	 * 
	 * @param className
	 *            The class name that is going to log required
	 * @return LogHelper
	 */
	public static LogHelper getInstance(String className)
	{

		if (loggerContainer == null)
		{
			loggerContainer = new HashMap<String, LogHelper>();
		}
		instance = (LogHelper) loggerContainer.get(className);
		if (instance == null)
		{
			instance = new LogHelper(className, true);
			loggerContainer.put(className, instance);
		}
		return instance;
		// return new LogHelper(className,true);
	}

	/**
	 * Check the debug level is enabled for logging
	 * 
	 * @return true if debug level is enable
	 */
	public boolean isDebugEnabled()
	{
		return log.isDebugEnabled();
	}

	/**
	 * Make a banner
	 */
	public void banner()
	{
		if (log.isDebugEnabled())
		{
			log.debug("\n==================================================================\n");
		}
	}

	/**
	 * Log at debug level
	 * 
	 * @param debugMsg
	 */
	public void debug(String debugMsg)
	{
		if (log.isDebugEnabled())
		{
			log.debug(PREFIX + debugMsg);
		}
	}

	/**
	 * Log at debug level
	 * 
	 * @param debugMsg
	 */
	public void debug(String debugMsg, Throwable e)
	{
		if (log.isDebugEnabled())
		{
			log.debug(PREFIX + debugMsg);
			log.debug(STACK_TRACE, e);
		}
	}

	/**
	 * Log at error level
	 * 
	 * @param errorMsg
	 * @return String the error reference code
	 */
	public synchronized String error(String errorMsg)
	{
		String errorRef = String.valueOf(System.currentTimeMillis());
		// if(log.isErrorEnabled())
		{
			log.error(PREFIX + "error ref: " + errorRef);
			log.error(PREFIX + errorMsg);
		}
		return errorRef;
	}

	/**
	 * Log at error level
	 * 
	 * @param errorMsg
	 * @param e
	 *            Throwable. The stack trace will be logged
	 * @return String the error reference code
	 */
	public synchronized String error(String errorMsg, Throwable e)
	{
		String errorRef = String.valueOf(System.currentTimeMillis());

		// if(log.isErrorEnabled())
		{
			log.error(errorRef);
			log.error(PREFIX + errorMsg);
			log.error(STACK_TRACE, e);
		}
		return errorRef;
	}

	/**
	 * Log at error level
	 * 
	 * @param errorMsg
	 * @param e
	 *            Throwable. The stack trace will be logged
	 * @return String the error reference code
	 */
	public synchronized String bizError(String errorMsg, Throwable e)
	{
		String errorRef = String.valueOf(System.currentTimeMillis());

		if (log.isInfoEnabled())
		{
			log.debug(errorRef);
			log.debug(BIZ_ERROR_PREFIX + errorMsg);
			log.debug(STACK_TRACE, e);
		}
		return errorRef;
	}

	/**
	 * Log at info level
	 * 
	 * @param infoMsg
	 */
	public synchronized void info(String infoMsg)
	{
		if (log.isInfoEnabled())
		{
			log.info(PREFIX + infoMsg);
		}
	}

	/**
	 * Log at TRACE level. Entry of every method should call this method
	 * 
	 * @param traceMsg
	 */
	public void trace(String traceMsg)
	{
		if (log.isTraceEnabled())
		{
			log.trace(TRACE_PREFIX + traceMsg);
		}
	}

	/**
	 * Check the trace level is enabled for logging
	 * 
	 * @return true if trace level is enable
	 */
	public boolean isTraceEnabled()
	{
		return log.isTraceEnabled();
	}

	/**
	 * Log the performance at INFO level. Entry and exit timing of every layer,
	 * sub-system or third party system.
	 * 
	 * @param performanceMsg
	 * @param start
	 * @param end
	 */
	public void performance(String performanceMsg, long start, long end)
	{
		if (log.isInfoEnabled())
		{
			log.info(PERFORMANCE_PREFIX + performanceMsg + " duration[" + (end - start) + " ms]");
		}
	}

	/**
	 * Log the input to third party system or input from third party system.
	 * This is to use in the interface programs.
	 * 
	 * @param data
	 */
	public void input(String data)
	{
		if (log.isInfoEnabled())
		{
			log.info(INTERFACE_PREFIX + " input: " + data);
		}
	}

	/**
	 * Log the output to third party system or output to third party system.
	 * This is to use in the interface programs.
	 * 
	 * @param data
	 */
	public void output(String data)
	{
		if (log.isInfoEnabled())
		{
			log.info(INTERFACE_PREFIX + " output: " + data);
		}
	}

	/**
	 * Log at WARN level
	 * 
	 * @param warnMsg
	 */
	public void warn(String warnMsg)
	{
		log.warn(PREFIX + warnMsg);
	}

}