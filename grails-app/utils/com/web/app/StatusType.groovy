package com.web.app

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

public enum StatusType {
	All,
	Draft,
	Review,
	Final;
	

	private static final Log log = LogFactory.getLog(StatusType.class);
	
	
}
