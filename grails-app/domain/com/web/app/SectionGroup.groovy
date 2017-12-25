package com.web.app

class SectionGroup {

	String groupName
	//We don't use sequence for anything, but we're keeping it in the database for reference
	//regarding the list order of Groups
	Integer sequence
	User lastModBy

	Date lastUpdated
	Date dateCreated

	static hasMany = [sections : Section]
		
    static constraints = {
		groupName blank:false
		sequence range: 1..20
		sections nullable : true
    }
	
	String toString() {
		groupName
	}
	
	static mapping = {
		sort groupName : "asc"
	//	id generator:'assigned'
	}
}
