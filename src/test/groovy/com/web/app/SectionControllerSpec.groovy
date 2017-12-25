package com.web.app

import spock.lang.Specification
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(SectionController)
@Mock(Section)
class SectionControllerSpec extends Specification{

	def setup()  {
//		setupUsers()
//		setupSpringSecurityStub()
//		controller.springSecurityService = springSecurityServiceStub
	}
	
	def populateValidParams(params) {
      assert params != null
	  params["group"] = new SectionGroup(groupName:"Advocacy Message", sequence: 1)
	  params["lastModBy"] = new User(name:'cyclops')
	  params["sequence"] = 3
      params["sectionName"] = "Tip"
    }
	
	def "calling index should redirect to the section list"(){
		
		when:
		controller.index()
		
		then:
		response.redirectedUrl == "/section/list"
		
		
	}
	
	def "calling list should return a section list size of zero"(){
		
		when:
		def model = controller.list()
		
		then:
		model.sectionInstanceList.size() == 0
		model.sectionInstanceTotal == 0
		
		
	}
	
		
	def "calling create should return a sectionInstance that is not null"() {
		
		when:
		def model = controller.create()
		
		then:
		model.sectionInstance != null
	}
	

	def "calling show without a valid id should redirect to section list"() {
		when:
		controller.show()
			
		then:
		flash.message != null
		response.redirectedUrl == '/section/list'
	}
	
	def "calling show with an id of an existing section should return that section"() {
		
		setup:
		populateValidParams(params)
		def section = new Section(params)
		section.save()
		

		when:
		params.id = section.id
		def model = controller.show()

		then:
		model.sectionInstance == section
	}
	
	def "calling edit with no params.id should redirect to section list"(){
		
		when:
		controller.edit()
		
		then:
		flash.message != null
		response.redirectedUrl == '/section/list'
		
	}
	
	def "calling edit with a valid id should return a valid section object" (){
		
		setup:
		populateValidParams(params)
		def section = new Section(params)
		section.save() != null
		
		when:
		params.id = section.id
		def model = controller.edit()
		
		then:
		model.sectionInstance == section
	}
	

//	def "calling update with invalid update params should display edit view" (){
//
//		setup:
//		populateValidParams(params)
//		def section = new Section(params)
//		section.save()
//
//		when:
//		params.id = section.id
//		params.sectionName = ""
//		controller.update()
//
//		then:
//		view == "/section/edit"
//		model.sectionInstance != null
//
//	}
//
//	def "calling update with valid params should redirect back to list and have a valid lastModBy" () {
//
//		setup:
//		populateValidParams(params)
//		def section = new Section(params)
//		section.save()
//
//		when:
//		params.id = section.id
//		controller.update()
//		def updatedSection = Section.get(section.id)
//
//		then:
//		response.redirectedUrl == "/section/list"
//		flash.message != null
//		updatedSection.lastModBy.username == loggedInUser.username
//
//	}
//
//	def "calling update with an outdated version number should result in an error"(){
//
//		setup:
//		populateValidParams(params)
//		def section = new Section(params)
//		section.save(flush:true)
//
//
//		when:
//		params.id = section.id
//		params.version = -1
//		controller.update()
//
//		then:
//		view == "/section/edit"
//		model.sectionInstance != null
//		model.sectionInstance.errors.getFieldError('version')
//
//	}
//
//	def "attempting to delete without an input id should redirect back to section list" (){
//
//		when:
//		controller.delete()
//
//		then:
//		flash.message != null
//		response.redirectedUrl == '/section/list'
//
//	}
//
//	def "attempting to delete with a valid id should result in section no longer found" (){
//
//		setup:
//		populateValidParams(params)
//		def section = new Section(params)
//		section.save(flush:true)
//
//		when:
//		params.id = section.id
//		controller.delete()
//
//
//		then:
//		Section.count() == 0
//		Section.get(section.id) == null
//		response.redirectedUrl == '/section/list'
//	}
//
}
