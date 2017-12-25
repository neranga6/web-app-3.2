package com.web.app

import org.junit.Ignore
import spock.lang.Specification
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(LetterTemplateCommentController)
@Mock([LetterTemplateComment, User, LetterTemplate])

class LetterTemplateCommentControllerSpec extends Specification{

	String comment
	User lastModBy

	Date lastUpdated
	Date dateCreated
	
	def setup() {
//		setupUsers()
//		setupSpringSecurityStub()
//		controller.springSecurityService = springSecurityServiceStub
	}

	def populateValidParams(params) {
	  assert params != null
	  params["comment"] = 'some comment'
	  params["lastModBy"] = setupModifyingUser
	  params["letterTemplate"] = new LetterTemplate(name:'Some letter template')
	}
	
	def "calling index should redirect to the letterTemplateComment list"(){
		
		when:
		controller.index()
		
		then:
		response.redirectedUrl == "/letterTemplateComment/list"
		
		
	}
	
	def "calling list should return a letterTemplateComment list size of zero"(){
		
		when:
		def model = controller.list()
		
		then:
		model.letterTemplateCommentInstanceList.size() == 0
		model.letterTemplateCommentInstanceTotal == 0
		
		
	}
	
		
	def "calling create should return a letterTemplateCommentInstance that is not null"() {
		
		when:
		def model = controller.create()
		
		then:
		model.letterTemplateCommentInstance != null
	}

	@Ignore
	/*def "calling save with no params should present the create page"() {

		when:
		controller.save()

		then:
		//model.letterTemplateCommentInstance != null
		view ==  '/letterTemplateComment/create'
	}

	@Ignore
	def "calling save with valid params result in a valid letterTemplateComment being saved"() {
	
		setup:
		populateValidParams(params)
		
		when:
		controller.save()
		def letterTemplateComment = LetterTemplateComment.get(1)

		then:
		response.redirectedUrl == '/letterTemplateComment/show/1'
		controller.flash.message != null
		LetterTemplateComment.count() == 1
		//letterTemplateComment.lastModBy.username == loggedInUser.username
	}*/ "calling show without a valid id should redirect to letterTemplateComment list"() {
		when:
		controller.show()
			
		then:
		flash.message != null
		response.redirectedUrl == '/letterTemplateComment/list'
	}

	/*@Ignore
	def "calling show with an id of an existing letterTemplateComment should return that letterTemplateComment"() {
		
		setup:
		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		populateValidParams(params)
		def letterTemplateComment = new LetterTemplateComment(params)
		letterTemplateComment.save()
		

		when:
		params.id = letterTemplateComment.id
		def model = controller.show()

		then:
		model.letterTemplateCommentInstance == letterTemplateComment
	}*/
	
	def "calling edit with no params.id should redirect to letterTemplateComment list"(){
		
		when:
		controller.edit()
		
		then:
		flash.message != null
		response.redirectedUrl == '/letterTemplateComment/list'
		
	}

	/*@Ignore
	def "calling edit with a valid id should return a valid letterTemplateComment object" (){
		
		setup:
		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		populateValidParams(params)
		def letterTemplateComment = new LetterTemplateComment(params)
		letterTemplateComment.save() != null
		
		when:
		params.id = letterTemplateComment.id
		def model = controller.edit()
		
		then:
		model.letterTemplateCommentInstance == letterTemplateComment
	}

	@Ignore
	def "calling update without an id should redirect to the letterTemplateComment list"(){
		
		when:
		controller.update()
		
		then:
		flash.message != null
		response.redirectedUrl == '/letterTemplateComment/list'
	}

	@Ignore
	def "calling update with invalid update params should display edit view" (){
		
		setup:
		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		populateValidParams(params)
		def letterTemplateComment = new LetterTemplateComment(params)
		letterTemplateComment.save()
		
		when:
		params.id = letterTemplateComment.id
		params.comment = ""
		controller.update()
		
		then:
		view == "/letterTemplateComment/edit"
		model.letterTemplateCommentInstance != null
		
	}

	@Ignore
	def "calling update with valid params should redirect to letterTemplateComment list and have a valid lastModBy" () {
		
		setup:
		
		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		populateValidParams(params)
		def letterTemplateComment = new LetterTemplateComment(params)
		letterTemplateComment.save()
		
		when:
		params.id = letterTemplateComment.id
		controller.update()
		def updatedLetterTemplateComment = LetterTemplateComment.get(letterTemplateComment.id)
		
		then:
		response.redirectedUrl == "/letterTemplateComment/list"
		flash.message != null
		updatedLetterTemplateComment.lastModBy.username == loggedInUser.username
					
	}

	@Ignore
	def "calling update with an outdated version number should result in an error"(){
		
		setup:
		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		populateValidParams(params)
		def letterTemplateComment = new LetterTemplateComment(params)
		letterTemplateComment.save(flush:true)
		
		
		when:
		params.id = letterTemplateComment.id
		params.version = -1
		controller.update()
		
		then:
		view == "/letterTemplateComment/edit"
		model.letterTemplateCommentInstance != null
		model.letterTemplateCommentInstance.errors.getFieldError('version')
	
	}

	@Ignore
	def "attempting to delete without an input id should redirect back to letterTemplateComment list" (){
		
		when:
		controller.delete()
		
		then:
		flash.message != null
		response.redirectedUrl == '/letterTemplateComment/list'
		
	}

	 @Ignore
	 def "attempting to delete with a valid id should result in letterTemplateComment no longer found" (){
		
		setup:
		params["createdBy"] = setupCreatingUser
		params["lastModBy"] = setupModifyingUser
		populateValidParams(params)
		def letterTemplateComment = new LetterTemplateComment(params)
		letterTemplateComment.save(flush:true)
		
		when:
		params.id = letterTemplateComment.id
		controller.delete()
		
		
		then:
		LetterTemplateComment.count() == 0
		LetterTemplateComment.get(letterTemplateComment.id) == null
		response.redirectedUrl == '/letterTemplateComment/list'
	}*/

}
