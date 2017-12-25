package com.web.app

import grails.test.mixin.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([User, Role, UserRole])
class UserControllerSpec extends Specification{
	
	def setup(){
		setupRoles()
		//setupSpringSecurityStub()
		
	}
	
	def populateValidParams(params) {
	  assert params != null
	  params["username"] = 'grahamj'
	  params["enabled"] = true
	  params["accountExpired"] = false
      params["accountLocked"] = false
	  params["passwordExpired"] = false
		  
    }
	
	
	def setupRoles(){
		new Role(authority:"ROLE_USER").save(flush:true, failOnError:true)
		new Role(authority:"ROLE_ADMIN").save(flush:true, failOnError:true)
		new Role(authority:"ROLE_WRITER").save(flush:true,failOnError:true)
		new Role(authority:"ROLE_REVIEWER").save(flush:true, failOnError:true)
	}
	def "calling index with no params should redirect back to list"(){

		when:
		controller.index()

		then:
		response.redirectedUrl == '/user/list'
	}

	def "calling edit with no params.id should redirect to letterTemplateComment list"(){

		when:
		controller.edit()

		then:
		flash.message != null
		response.redirectedUrl == '/user/list'

	}




//	def "calling update with invalid params should keep user on edit screen"(){
//
//		setup:
//		populateValidParams(params)
//		def user = new User(params)
//
//		user.springSecurityService = springSecurityServiceStub
//		// Give user an isDirty method because @Mock doesn't; it does not need to do anything.
//		user.metaClass.isDirty { }
//
//		user.save()
//
//		when:
//
//		// test invalid parameters in update
//		params.id = user.id
//		params["username"] = ""
//
//		controller.update()
//
//		then:
//
//		view == "/user/edit"
//		model.userInstance != null
//
//
//	}
	
//	def "calling update with valid params should return user back to list" (){
//
//		setup:
//		populateValidParams(params)
//		def user = new User(params)
//
//		user.springSecurityService = springSecurityServiceStub
//		// Give user an isDirty method because @Mock doesn't; it does not need to do anything.
//		user.metaClass.isDirty { }
//		user.save()
//
//		when:
//		params["id"] = user.id
//		params["username"] = 'grahamj'
//		params["userRoleSelection"] = 'ROLE_WRITER'
//
//		controller.update()
//
//		def updatedUser = User.get(user.id)
//
//
//		then:
//		response.redirectedUrl == "/user/list"
//		flash.message != null
//		updatedUser != null
//		updatedUser.username == 'grahamj'
//
//	}
	


}
