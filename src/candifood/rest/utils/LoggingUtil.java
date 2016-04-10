package com.package.rest.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

public class LoggingUtil {
	private static final Logger logger = Logger.getLogger(LoggingUtil.class.getName());

	public static void logRequests(HttpServletRequest req) throws IOException {
		logger.info("aniruddh: logging started of the request veriable...");
		@SuppressWarnings("unchecked")
		Enumeration<String> parameterNames = req.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			logger.info(paramName+":");
			String[] paramValues = req.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				String paramValue = paramValues[i];
				logger.info("---" + paramValue);
			}

		}
	}

}
