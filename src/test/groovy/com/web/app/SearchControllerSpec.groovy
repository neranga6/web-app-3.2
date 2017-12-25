package com.web.app

import grails.test.mixin.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(SearchController)
class SearchControllerSpec extends Specification{
	
	def " test unrecognized search type"(){
		
		when:
		params.searchType = 'blah'
		controller.index()
		
		then:
		flash.error == 'typeMismatch.searchType'
		"/" == response.redirectedUrl
	}
	
	def "empty search type"(){
		
		when:
		params.searchType = 'EMPTY'
		controller.index()
		
		then:
		flash.error == 'searchType.empty'
		"/" == response.redirectedUrl
	}
	
	def "search by module"() {
		
		when:
		params.searchType = 'MODULE_CONTENT'
		params.criteria = "blah"
		controller.index()
		
		then:
		"/module/globalSearch?searchType=MODULE_CONTENT&criteria=blah" == response.redirectedUrl
	}
	
	def "search by module id"() {
		
		when:
		params.searchType = 'MODULE_ID'
		params.criteria = "567"
		controller.index()
		
		then:
		"/module/globalSearch?searchType=MODULE_ID&criteria=567" == response.redirectedUrl
	}
	
	def "search by letterTemplate name"() {
		
		when:
		params.searchType = 'LETTER_TEMPLATE_NAME'
		params.criteria = "some name"
		controller.index()
		
		then:
		"/letterTemplate/list?searchType=LETTER_TEMPLATE_NAME&criteria=some+name" == response.redirectedUrl
	}
	
	def "search by letterTemplate id"() {
		
		when:
		params.searchType = 'LETTER_TEMPLATE_ID'
		params.criteria = "890"
		controller.index()
		
		then:
		"/letterTemplate/list?searchType=LETTER_TEMPLATE_ID&criteria=890" == response.redirectedUrl
	}
	
	def "search by letterTemplate description"() {
		
		when:
		params.searchType = 'LETTER_TEMPLATE_DESCRIPTION'
		params.criteria = "customer billing change"
		controller.index()
		
		then:
		"/letterTemplate/list?searchType=LETTER_TEMPLATE_DESCRIPTION&criteria=customer+billing+change" == response.redirectedUrl
	}
	
	def "search by letterTemplate category"() {
		
		when:
		params.searchType = 'LETTER_TEMPLATE_CATEGORY'
		params.criteria = "claims"
		controller.index()
		
		then:
		"/letterTemplate/list?searchType=LETTER_TEMPLATE_CATEGORY&criteria=claims" == response.redirectedUrl
	}

}
