package com.web.app
import spock.lang.Specification

class UsageFilterTypeTest extends Specification {
	
	def "textValues() returns expected list of values with String versions"(){
		
		given:
			
			def textList = UsageFilterType.textValues()
		
		expect:
		
			textList[0] == "Greater Than (>)"
			textList[1] == "Less Than (<)"
			textList[2] == "Equals (=)" 
		
	}

}
