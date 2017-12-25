package com.web.app

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(BusinessRuleController)
@Mock([BusinessRule, BusinessRuleService])

class BusinessRuleControllerSpec extends Specification{
	
	def setup() {
		
//		setupUsers()
//		setupSpringSecurityStub()
//		controller.springSecurityService = springSecurityServiceStub
	}
	
	def populateValidParams(params) {
		assert params != null
		params["rule"] = 'Some business rule'
		//params["lastModBy"] = new User(name:'cyclops')
		params["module"] = new Module(content:'some content')
	}

    def "calling index should redirect to the business rule list"(){
		
		when: 
		controller.index()
		
		then:
		response.redirectedUrl == "/businessRule/list"
		
		
	}
	
	def "calling list should return a business rule list size of zero"(){
		
		when:
		def model = controller.list()
		
		then:
		model.businessRuleInstanceList.size() == 0
        model.businessRuleInstanceTotal == 0
		
		
	}
	
		
	def "calling create should return a businessRuleInstance that is not null"() {
		
		when:
		def model = controller.create()
		
		then:
		model.businessRuleInstance != null
	}
	
	/*def "calling save with no params should present the create page"() {
		
		when:
		controller.save()
		
		then:
		model.businessRuleInstance != null
		view ==  '/businessRule/create'
	}
	
	def "calling save with valid params result in a valid businessRule being saved"() {
	
		setup:
		populateValidParams(params)
		
		when:
		controller.save()
		def businessRule = BusinessRule.get(1)

		then:
		response.redirectedUrl == '/businessRule/show/1'
		controller.flash.message != null
		BusinessRule.count() == 1
		businessRule.lastModBy.username == loggedInUser.username
	}*/
	
	def "calling show without a valid id should redirect to businessRule list"() {
		when:
		controller.show()
			
		then:
		flash.message != null
		response.redirectedUrl == '/businessRule/list'
	}
	
//	def "calling show with an id of an existing businessRule should return that businessRule"() {
//
//		setup:
////		params["createdBy"] = setupCreatingUser
////		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def businessRule = new BusinessRule(params)
//		businessRule.save()
//
//
//		when:
////		params.id = businessRule.id
//		def model = controller.show()
//
//		then:
//		model.businessRuleInstance == businessRule
//	}
	
	def "calling edit with no params.id should redirect to businessRule list"(){
		
		when:
		controller.edit()
		
		then:
		flash.message != null
		response.redirectedUrl == '/businessRule/list'
		
	}
	
//	def "calling edit with a valid id should return a valid businessRule object" (){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def businessRule = new BusinessRule(params)
//		businessRule.save() != null
//
//		when:
//		params.id = businessRule.id
//		def model = controller.edit()
//
//		then:
//		model.businessRuleInstance == businessRule
//	}
//
//	def "calling update without an id should redirect to the businessRule list"(){
//
//		when:
//		controller.update()
//
//		then:
//		//flash.message != null
//		view == '/businessRule/list'
//	}
//
//	def "calling update with invalid update params should display edit view" (){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def businessRule = new BusinessRule(params)
//		businessRule.save()
//
//		when:
//		params.id = businessRule.id
//		params.rule = ""
//		controller.update()
//
//		then:
//		view == "/businessRule/edit"
//		model.businessRuleInstance != null
//
//	}
//
//	def "calling update with valid params should redirect back to list and have a valid lastModBy" () {
//
//		setup:
//
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def businessRule = new BusinessRule(params)
//		businessRule.save()
//
//		when:
//		params.id = businessRule.id
//		params.rule =  "Some other rule"
//		controller.update()
//		def updatedBusinessRule = BusinessRule.get(businessRule.id)
//
//		then:
//		response.redirectedUrl == "/businessRule/list"
//		flash.message != null
//		updatedBusinessRule.lastModBy.username == loggedInUser.username
//
//	}
//
//	def "calling update with an outdated version number should result in an error"(){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def businessRule = new BusinessRule(params)
//		businessRule.save(flush:true)
//
//
//		when:
//		params.id = businessRule.id
//		params.version = -1
//		controller.update()
//
//		then:
//		view == "/businessRule/edit"
//		model.businessRuleInstance != null
//		model.businessRuleInstance.errors.getFieldError('version')
//
//	}
//
//	def "attempting to delete without an input id should redirect back to businessRule list" (){
//
//		when:
//		controller.delete()
//
//		then:
//		flash.message != null
//		response.redirectedUrl == '/businessRule/list'
//
//	}
//
//	def "attempting to delete with a valid id should result in businessRule no longer found" (){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def businessRule = new BusinessRule(params)
//		businessRule.save(flush:true)
//
//		when:
//		params.id = businessRule.id
//		controller.delete()
//
//
//		then:
//		BusinessRule.count() == 0
//		BusinessRule.get(businessRule.id) == null
//		response.redirectedUrl == '/businessRule/list'
//	}
	
	
	
}
