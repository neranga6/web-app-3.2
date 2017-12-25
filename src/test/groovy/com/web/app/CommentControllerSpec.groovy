package com.web.app

import spock.lang.Specification
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(CommentController)
@Mock([Comment, CommentService, Module, User, Section, SectionGroup])
class CommentControllerSpec extends Specification{

	def setup()  {
//		setupUsers()
//		setupSpringSecurityStub()
//	controller.springSecurityService = springSecurityServiceStub
	}
	
	def populateValidParams(params) {
		assert params != null
		params["comment"] = 'some comment'
		params["lastModBy"] = setupModifyingUser
		params["module"] = new Module(content:'some content')
	}
	
	def "calling index should redirect to the comment list"(){
		
		when:
		controller.index()
		
		then:
		response.redirectedUrl == "/comment/list"
		
		
	}
	
	def "calling list should return a comment list size of zero"(){
		
		when:
		def model = controller.list()
		
		then:
		model.commentInstanceList.size() == 0
		model.commentInstanceTotal == 0
		
		
	}
	
		
	def "calling create should return a commentInstance that is not null"() {
		
		when:
		def model = controller.create()
		
		then:
		model.commentInstance != null
	}
	
//	def "calling save with no params should present the create page"() {
//
//		when:
//		controller.save()
//
//		then:
//		model.commentInstance != null
//		view ==  '/comment/create'
//	}
//

	def "calling edit with no params.id should redirect to comment list"(){
		
		when:
		controller.edit()
		
		then:
		flash.message != null
		response.redirectedUrl == '/comment/list'
		
	}
	

	
	def "test for redirect to comment list when no id is passed in listForModule"() {
		
		when:
		controller.listForModule()
		
		then:
		response.redirectedUrl == '/comment/list'
	}
	
	def "test getting comment list for module"() {
		
		setup:
		ConstraintUnitSpec.createSampleModule()
		
		when:
		params.id = 1
		def model = controller.listForModule()
		
		then:
		model.commentInstanceTotal == 2
		model.commentInstanceList != null
		model.commentInstanceList[0].comment == "sample comment #1"
		model.commentInstanceList[1].comment == "sample comment #2"
	}
	
}
