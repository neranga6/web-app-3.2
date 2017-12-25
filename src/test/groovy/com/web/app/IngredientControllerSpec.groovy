package com.web.app

import spock.lang.Specification
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(IngredientController)
@Mock([Ingredient, LetterTemplate])
class IngredientControllerSpec extends Specification{
	
	def setup()  {
//		setupUsers()
//		setupSpringSecurityStub()
//		controller.springSecurityService = springSecurityServiceStub
	}
	
	def populateValidParams(params) {
		assert params != null
		
		params["sequence"] = 3
		params["comment"] = "Just Some Comment"
		params["lastModBy"] = setupModifyingUser
		params["module"] = new Module(content:'some content')
		params["letter"] = new LetterTemplate(name:'some letter name')
	  }
	
	def "calling index should redirect to the ingredient list"(){
		
		when:
		controller.index()
		
		then:
		response.redirectedUrl == "/ingredient/list"
		
		
	}
	
	def "calling list should return a ingredient list size of zero"(){
		
		when:
		def model = controller.list()
		
		then:
		model.ingredientInstanceList.size() == 0
		model.ingredientInstanceTotal == 0
		
		
	}
	
		
	def "calling create should return an ingredientInstance that is not null"() {
		
		when:
		def model = controller.create()
		
		then:
		model.ingredientInstance != null
	}
	
//	def "calling save with no params should present the create page"() {
//
//		when:
//		controller.save()
//
//		then:
//		model.ingredientInstance != null
//		view ==  '/ingredient/create'
//	}
	
//	def "calling save with valid params result in a valid ingredient being saved"() {
//
//		setup:
//		populateValidParams(params)
//
//		when:
//		controller.save()
//		def ingredient = Ingredient.get(1)
//
//		then:
//		response.redirectedUrl == '/ingredient/show/1'
//		controller.flash.message != null
//		Ingredient.count() == 1
//		ingredient.lastModBy.username == loggedInUser.username
//	}
//
	def "calling show without a valid id should redirect to ingredient list"() {
		when:
		controller.show()
			
		then:
		flash.message != null
		response.redirectedUrl == '/ingredient/list'
	}
	
//	def "calling show with an id of an existing ingredient should return that ingredient"() {
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def ingredient = new Ingredient(params)
//		ingredient.save()
//
//
//		when:
//		params.id = ingredient.id
//		def model = controller.show()
//
//		then:
//		model.ingredientInstance == ingredient
//	}
	
	def "calling edit with no params.id should redirect to ingredient list"(){
		
		when:
		controller.edit()
		
		then:
		flash.message != null
		response.redirectedUrl == '/ingredient/list'
		
	}
	
//	def "calling edit with a valid id should return a valid ingredient" (){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def ingredient = new Ingredient(params)
//		ingredient.save() != null
//
//		when:
//		params.id = ingredient.id
//		def model = controller.edit()
//
//		then:
//		model.ingredientInstance == ingredient
//	}
//
//	def "calling update without an id should redirect to the ingredient list"(){
//
//		when:
//		controller.update()
//
//		then:
//		flash.message != null
//		response.redirectedUrl == '/ingredient/list'
//	}
//
//	def "calling update with invalid update params should display edit view" (){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def ingredient = new Ingredient(params)
//		ingredient.save()
//
//		when:
//		params.id = ingredient.id
//		params.module = null
//		controller.update()
//
//		then:
//		view == "/ingredient/edit"
//		model.ingredientInstance != null
//
//	}
//
//	def "calling update with valid params should redirect to show and have a valid lastModBy" () {
//
//		setup:
//
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def ingredient = new Ingredient(params)
//		ingredient.save()
//
//		when:
//		params.id = ingredient.id
//		controller.update()
//		def updatedIngredient = Ingredient.get(ingredient.id)
//
//		then:
//		response.redirectedUrl == "/ingredient/show/$ingredient.id"
//		flash.message != null
//		updatedIngredient.lastModBy.username == loggedInUser.username
//
//	}
//
//	def "calling update with an outdated version number should result in an error"(){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def ingredient = new Ingredient(params)
//		ingredient.save(flush:true)
//
//
//		when:
//		params.id = ingredient.id
//		params.version = -1
//		controller.update()
//
//		then:
//		view == "/ingredient/edit"
//		model.ingredientInstance != null
//		model.ingredientInstance.errors.getFieldError('version')
//
//	}
//
//	def "attempting to delete without an input id should redirect back to ingredient list" (){
//
//		when:
//		controller.delete()
//
//		then:
//		flash.message != null
//		response.redirectedUrl == '/ingredient/list'
//
//	}
//
//	def "attempting to delete with a valid id should result in ingredient no longer found" (){
//
//		setup:
//		params["createdBy"] = setupCreatingUser
//		params["lastModBy"] = setupModifyingUser
//		populateValidParams(params)
//		def ingredient = new Ingredient(params)
//		ingredient.save(flush:true)
//
//		when:
//		params.id = ingredient.id
//		controller.delete()
//
//
//		then:
//		Ingredient.count() == 0
//		Ingredient.get(ingredient.id) == null
//		response.redirectedUrl == '/ingredient/list'
//	}
//
	
}
