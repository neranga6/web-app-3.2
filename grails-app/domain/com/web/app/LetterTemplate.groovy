package com.web.app

class LetterTemplate {
	static final List STS_LIST = ["Draft","Review","Final"]
	
	String name
	String description
	Category category
	String status
	Model model
	User createdBy
	User lastModBy

	Date lastUpdated
	Date dateCreated
		
	static hasMany = [recipe:Ingredient, comments: LetterTemplateComment]
	
    static constraints = {
		name blank:false 
		description blank:false, maxSize:500
		status inList:STS_LIST,nullable:true
		category nullable:false
	}

	String toString() {
		
		"${this.id} - ${this.name}" 
		
	}
	
	static mapping = {
		sort dateCreated: "desc"
	//	id generator:'assigned'
	}
}
