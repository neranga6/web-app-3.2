package com.web.app

import spock.lang.Specification
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(CategoryController)
@Mock(Category)
class CategoryControllerSpec extends Specification{
	
	def setup()  {
//		setupUsers()
//		setupSpringSecurityStub()
//		controller.springSecurityService = springSecurityServiceStub
	}
	
	def populateValidParams(params) {
		assert params != null
		params["name"] = 'Car Repair'
	//	params["lastModBy"] = setupModifyingUser
	  }
	
	def "calling index should redirect to the category list"(){
		
		when:
		controller.index()
		
		then:
		response.redirectedUrl == "/category/list"
		
		
	}
	
	def "calling list should return a category list size of zero"(){
		
		when:
		def model = controller.list()
		
		then:
		model.categoryInstanceList.size() == 0
		model.categoryInstanceTotal == 0
		
		
	}
	
		
	def "calling create should return a categoryInstance that is not null"() {
		
		when:
		def model = controller.create()
		
		then:
		model.categoryInstance != null
	}


	def "calling show without a valid id should redirect to category list"() {
		when:
		controller.show()
			
		then:
		flash.message != null
		response.redirectedUrl == '/category/list'
	}
	
	def "calling edit with no params.id should redirect to category list"(){
		
		when:
		controller.edit()
		
		then:
		flash.message != null
		response.redirectedUrl == '/category/list'
		
	}


}



