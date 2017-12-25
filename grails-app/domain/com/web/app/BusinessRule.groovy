package com.web.app

class BusinessRule{

	String rule
	User lastModBy

	Date lastUpdated
	Date dateCreated
	
	static belongsTo = [module : Module]
	
    static constraints = {
		rule blank : false, size: 0..500
    }

	static mapping = {
		cache true
		// "Comment" is a reserved word in Oracle, so change the underlying column name
		table: "BUSINESS_RULE"
	}


	String toString() {
		rule
	}


	BusinessRule getInstance() {
		new BusinessRule(rule: this.rule, lastModBy: this.lastModBy)
	}



}
