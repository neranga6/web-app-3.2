package com.web.app

import grails.test.mixin.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(ModuleController) // this activates ControllerUnitTestMixin which gives access to model and view properties
@Mock([Module, User, Section, SectionGroup, CommentService, Comment, BusinessRuleService, BusinessRule])
class ModuleControllerSpec extends Specification{
	
	def setup(){
//		setupUsers()
//		setupSpringSecurityStub()
//		controller.springSecurityService = springSecurityServiceStub
//
	}
	
	def populateValidParams(params) {
		  assert params != null
		  
		  params["content"] = 'Thank you for contacting us.  We will get back to your shortly'
		  params["status"] = 'Draft'

		  SectionGroup logoGroup = new SectionGroup(groupName : "Logo", sequence : 1,lastModBy: setupCreatingUser)
		
		  Section introSection = new Section(sectionName:"First Section",sequence: 1, group: logoGroup, lastModBy: setupCreatingUser)
		 
		  params["section"] = introSection
		  
	}	
	
	def "calling index redirects to module list"(){
		
		when:
		controller.index()
		
		then:
		"/module/list" == response.redirectedUrl
		
	}
	
//	def "calling list should return a module list size of zero"(){
//
//		setup:
//		controller.springSecurityService = springSecurityServiceStub
//
//		when:
//		def model = controller.list()
//
//		then:
//		model.moduleInstanceList.size() == 0
//		model.moduleInstanceTotal == 0
//
//
//	}
//
//	def "searching by content with criteria match should return one record"(){
//
//		setup:
//		ConstraintUnitSpec.createSampleModule("wolverine", "some module")
//		ConstraintUnitSpec.createSampleModule("cyclops", "another module")
//
//		when:
//		params.usageFilterType = UsageFilterType.LessThan
//		params.usageCount = 999
//		params.searchType = "MODULE_CONTENT"
//		params.criteria = "Some"
//		def model = controller.list()
//
//		then:
//		model.moduleInstanceList.size() == 1
//		model.moduleInstanceTotal == 1
//		model.moduleInstanceList[0].content == "some module"
//	}
//
//	def "searching by content without a criteria match should return no records"(){
//
//		setup:
//		ConstraintUnitSpec.createSampleModule()
//
//		when:
//		params.usageFilterType = UsageFilterType.LessThan
//		params.usageCount = 999
//		params.searchType = "MODULE_CONTENT"
//		params.criteria = "AAABBBCCC"
//		def model = controller.list()
//
//		then:
//		model.moduleInstanceList.size() == 0
//		model.moduleInstanceTotal == 0
//	}
//
//	def "searching by id should return one record"(){
//
//		setup:
//		ConstraintUnitSpec.createSampleModule("wolverine", "some module")
//		ConstraintUnitSpec.createSampleModule("cyclops", "another module")
//
//		when:
//		params.usageFilterType = UsageFilterType.LessThan
//		params.usageCount = 999
//		params.searchType = "MODULE_ID"
//		params.criteria = "1"
//		controller.globalSearch()
//
//
//		then:
//		// model property is provided by uisng the @TestFor(*Controller) which pulls in the ControllerUnitTestMixin
//		model.moduleInstanceList.size() == 1
//		model.moduleInstanceTotal == 1
//		model.moduleInstanceList[0].content == "some module"
//	}
//
	def "searching by an id that is not found should return to list"(){
		
		setup:
		ConstraintUnitSpec.createSampleModule("wolverine", "some module")
		ConstraintUnitSpec.createSampleModule("cyclops", "another module")
		
		when:
		params.searchType = "MODULE_ID"
		params.criteria = "50"
		controller.globalSearch()
		
		then:
		"/module/list" == response.redirectedUrl
	}
	
	def "calling create should return a module that is not null"(){
		
		when:
		def model = controller.create()
		
		then:
		model.moduleInstance != null
		
	}
	
	
//	def "calling save should redirect to edit on success"(){
//
//		setup:
//		populateValidParams(params)
//
//		when:
//		controller.save()
//		def module = Module.get(1)
//
//		then:
//		response.redirectedUrl == '/module/edit/1'
//		controller.flash.message != null
//		Module.count() == 1
//
//		assert module.lastModBy.username == loggedInUser.username
//	}
//
//	def "calling save with comments should return to edit and comments should not be null"(){
//
//		setup:
//		populateValidParams(params)
//
//		when:
//		params["comment"] = 'This is just some test comment to make sure saving a comment works'
//
//		controller.save()
//		def module = Module.get(1)
//
//		then:
//		response.redirectedUrl == '/module/edit/1'
//		controller.flash.message != null
//		Module.count() == 1
//		module.comments != null
//
//	}
//
//	def "calling save with business rules should return to edit and businessRules should not be null"(){
//
//		setup:
//		populateValidParams(params)
//
//		when:
//		params["rule"] = 'X-men rule'
//
//		controller.save()
//		def module = Module.get(1)
//
//		then:
//		response.redirectedUrl == '/module/edit/1'
//		controller.flash.message != null
//		Module.count() == 1
//		module.businessRules != null
//	}
//
//	def "calling show should should return a valid module"(){
//
//		setup:
//		populateValidParams(params)
//
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//
//		def module = new Module(params)
//
//		module.save()
//
//		when:
//		params.id = module.id
//
//		def model = controller.show()
//
//		then:
//		model.moduleInstance == module
//
//	}
//
//	def "calling edit should return a valid module" (){
//
//		setup:
//		populateValidParams(params)
//
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//
//		def module = new Module(params)
//		module.save()
//		def mockControl = mockFor(SpringSecurityUtils)
//		mockControl.demand.static.ifAllGranted {String arg -> return true}
//
//		when:
//
//		params.id = module.id
//
//		def model = controller.edit()
//
//		then:
//		model.moduleInstance == module
//	}
//
	
	def "calling edit with an invalid id should return you to list"(){
		
		setup:
		def sampleModule = ConstraintUnitSpec.createSampleModule()
		
		when:
		params.id = "aaa"
		def model = controller.edit()
		
		then:
		flash.message != null
		response.redirectedUrl == '/module/list'
	}
	
	
//	def "calling update without an id should redirect to the module list"(){
//
//		when:
//		controller.update()
//
//		then:
//		flash.message != null
//		response.redirectedUrl == '/module/list'
//	}
//
//	def "calling update with invalid update params should display edit view" (){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def module = new Module(params)
//
//        module.save()
//
//
//		when:
//		params.id = module.id
//		params.status = "Bad Status"
//		controller.update()
//
//		then:
//		view == "/module/edit"
//		model.moduleInstance != null
//
//	}
//
//	def "calling update with valid params should redirect back to edit and have a valid lastModBy" () {
//
//		setup:
//
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def module = new Module(params)
//		module.save()
//
//		when:
//		params.id = module.id
//		params["comment"] = 'This is just some test comment to make sure saving a comment works'
//		params["rule"] = 'X-men rock!!!'
//		controller.update()
//		def updatedModule = Module.get(module.id)
//
//		then:
//		response.redirectedUrl == "/module/edit/$module.id"
//		flash.message != null
//		updatedModule.comments != null
//		updatedModule.businessRules != null
//		updatedModule.lastModBy.username == loggedInUser.username
//
//	}
//
//	def "calling update with an outdated version number should result in an error"(){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def module = new Module(params)
//		module.save(flush:true)
//
//
//		when:
//		params.id = module.id
//		params.version = -1
//		controller.update()
//
//		then:
//		view == "/module/edit"
//		model.moduleInstance != null
//		model.moduleInstance.errors.getFieldError('version')
//
//	}
//
//	def "attempting to delete without an input id should redirect back to module list" (){
//
//		when:
//		controller.delete()
//
//		then:
//		flash.message != null
//		response.redirectedUrl == '/module/list'
//
//	}
//
//	def "attempting to delete with a valid id should result in module no longer found" (){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def module = new Module(params)
//		module.save(flush:true)
//
//		when:
//		params.id = module.id
//		controller.delete()
//
//
//		then:
//		Module.count() == 0
//		Module.get(module.id) == null
//		response.redirectedUrl == '/module/list'
//	}
	
	def "test copy with copy record not found should return to module list"(){
		
		when:
		controller.copy()
		
		then:
		flash.message != null
		response.redirectedUrl == '/module/list'
	}
	
//	def "test copying a module should result in two modules existing"(){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//
//		populateValidParams(params)
//		def originalModule = new Module(params)
//		originalModule.save()
//
//		when:
//		params.id = originalModule.id
//		def model = controller.copy()
//
//		then:
//		def editedModel = controller.modelAndView.model.moduleInstance
//		editedModel.id != originalModule.id
//		editedModel.lastModBy.username == loggedInUser.username
//		controller.flash.message != null
//		Module.count() == 2
//
//
//	}
//
	


}
