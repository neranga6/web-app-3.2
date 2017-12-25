package com.web.app

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

public enum UsageFilterType {
	GreaterThan("Greater Than (>)"),
	LessThan("Less Than (<)"),
	Equal("Equals (=)");
	
	private String text;
	
	UsageFilterType(String text) {
		this.text = text
	}
	
	static def textValues(){
		values().collect {it.text}		
	}

	private static final Log log = LogFactory.getLog(UsageFilterType.class);
	
	
}
