package com.web.app

import spock.lang.Specification
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(LetterTemplateController)
@Mock([LetterTemplate, groovy.lang.Category, Model, Role, User, Module, Section, SectionGroup, Comment, Ingredient])
class LetterTemplateControllerSpec extends Specification{


	def setup() {
//		setupUsers()
//		setupSpringSecurityStub()
//		controller.springSecurityService = springSecurityServiceStub
//		controller.letterTemplateService = new LetterTemplateService()
	}
	
	def populateValidParams(params) {
	  assert params != null
	  params["name"] = 'Recommendation'
	  params["description"] = 'This is a valid letter of recommendation'
	  params["category"] = new groovy.lang.Category(name:"Car Repair")
	  params["status"] = 'Draft'
	  params["model"] = new Model(templateStyle : "Left Nav", imageLocation:"C://Images")
	  params["createdBy"] = setupCreatingUser
	  params["lastModBy"] = setupModifyingUser
	}
	
	def "calling index should redirect to the letterTempate list"(){
		
		when:
		controller.index()
		
		then:
		response.redirectedUrl == "/letterTemplate/list"
		
		
	}
	
	def "calling list with no letterTemplates should return a letterTemplate list size of zero"(){
		
		when:
		def model = controller.list()
		
		then:
		model.letterTemplateInstanceList.size() == 0
		model.letterTemplateInstanceTotal == 0
		
		
	}
	
	def "calling list with three letterTemplates should return three letterTemplates" (){
		
		setup:
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second letter")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "third letter")
		
		when:
		def model = controller.list()
		
		then:
		model.letterTemplateInstanceList.size() == 3
		model.letterTemplateInstanceTotal == 3
		
	}
	
	def "calling list with letterTemplate ID should return that letter" () {
		
		setup:
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second letter")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "third letter")
		
		when:
		params.searchType = "LETTER_TEMPLATE_ID"
		params.criteria = "3"
		def model = controller.list()
		
		then:
		model.letterTemplateInstanceList.size() == 1
		model.letterTemplateInstanceTotal == 1
		model.letterTemplateInstanceList[0].name == "third letter"
	}
	
	def "calling list with an ID not found should redirect back to letterTemplate list"(){
		
		setup:
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		
		when:
		params.searchType = "LETTER_TEMPLATE_ID"
		params.criteria = "3"
		def model = controller.list()
		
		then:
		response.redirectedUrl == '/letterTemplate/list'
	}
	
	def "calling list with an ID that is the incorrect date type should redirect to list" () {
		
		setup:
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		
		when:
		params.searchType = "LETTER_TEMPLATE_ID"
		params.criteria = "abcde"
		def model = controller.list()
		
		then:
		response.redirectedUrl == '/letterTemplate/list'
		
		
	}
	
	def "calling list with partial name should return the correct records"() {
		
		setup:
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second one")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "my third letter")
		
		when:
		params.searchType = "LETTER_TEMPLATE_NAME"
		params.criteria = "Letter"
		def model = controller.list()
		
		then:
		model.letterTemplateInstanceList.size() == 2
		model.letterTemplateInstanceTotal == 2
		model.letterTemplateInstanceList[0].name == "first letter"
		model.letterTemplateInstanceList[1].name == "my third letter"
		
	}
	
//	def "calling list with partial descriptions should return the correct records"(){
//
//		setup:
//		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter", "claims IT", "payment")
//		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second one", "billing", "bill form")
//		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "my third letter", "sales", "quote")
//		ConstraintUnitSpec.createSampleLetterTemplate("nightcrawler", "a 4th letter", "claims business", "CSR form")
//
//		when:
//		params.searchType = "LETTER_TEMPLATE_DESCRIPTION"
//		params.criteria = "Form"
//		def model = controller.list()
//
//		then:
//		model.letterTemplateInstanceList.size() == 2
//		model.letterTemplateInstanceTotal == 2
//		model.letterTemplateInstanceList[0].name == "second one"
//		model.letterTemplateInstanceList[1].name == "a 4th letter"
//	}
//
	def "calling list with partial category name should return the correct records"(){
		
		setup:
		ConstraintUnitSpec.createSampleLetterTemplate("wolverine", "first letter", "claims IT")
		ConstraintUnitSpec.createSampleLetterTemplate("gambit", "second one", "billing")
		ConstraintUnitSpec.createSampleLetterTemplate("cyclops", "my third letter", "sales")
		ConstraintUnitSpec.createSampleLetterTemplate("nightcrawler", "a 4th letter", "claims business")
		
		when:
		params.searchType = "LETTER_TEMPLATE_CATEGORY"
		params.criteria = "Claims"
		def model = controller.list()
		
		then:
		model.letterTemplateInstanceList.size() == 2
		model.letterTemplateInstanceTotal == 2
		model.letterTemplateInstanceList[0].name == "first letter"
		model.letterTemplateInstanceList[1].name == "a 4th letter"
	}
	
//	def "create letterTemplate from existing" (){
//
//		setup:
//		Model modelTemplate = new Model(templateStyle : "Left Bar", imageLocation : "Some location").save()
//	    populateValidParams(params)
//	    def existingLetterTemplate = new LetterTemplate(params)
//	    def ingredient1 = new Ingredient(sequence : 1, comment : 'Comment one')
//	    def ingredient2 = new Ingredient(sequence : 2, comment : 'Comment two')
//	    existingLetterTemplate.addToRecipe(ingredient1)
//	    existingLetterTemplate.addToRecipe(ingredient2)
//	    existingLetterTemplate.model = modelTemplate
//	    existingLetterTemplate.save(flush: true)
//		Category selectedCategory = new Category(name : 'Some Category', lastModBy : setupModifyingUser).save()
//		selectedCategory.save(flush: true)
//
//		when:
//		params["letterTemplateGroup"] = 'existing'
//		params["modelExistingTemplate"] = '1'
//		params["templateName"] = 'Uber Template'
//		params["templateDescription"] = 'Some description'
//		params["categoryId"] = '1'
//
//		controller.create()
//
//		then:
//
//		response.redirectedUrl == '/letterTemplate/editStructure/2?name=Recommendation&description=This+is+a+valid+letter+of+recommendation&category=Car+Repair&status=Draft&model=Left+Nav&createdBy=cyclops&lastModBy=gambit&letterTemplateGroup=existing&modelExistingTemplate=1&templateName=Uber+Template&templateDescription=Some+description&categoryId=1'
//	}
//
//
//	def "create new letterTemplate" () {
//
//		setup:
//		Category selectedCategory = new Category(name : 'Some Category', lastModBy : setupModifyingUser).save()
//		selectedCategory.save()
//		Model modelTemplate = new Model(id: 1, templateStyle : 'Left Bar', imageLocation : 'My hard drive').save()
//
//		when:
//		populateValidParams(params)
//		params["letterTemplateGroup"] = 'new'
//		params["modelExistingTemplate"] = '1'
//		params["templateName"] = 'Uber Template'
//		params["templateDescription"] = 'Some description'
//		params["categoryId"] = '1'
//		params["newModelTemplate"] = '1'
//		def model = controller.create()
//
//		then:
//		response.redirectedUrl == '/letterTemplate/editStructure/1?name=Recommendation&description=This+is+a+valid+letter+of+recommendation&category=Car+Repair&status=Draft&model=Left+Nav&createdBy=cyclops&lastModBy=gambit&letterTemplateGroup=new&modelExistingTemplate=1&templateName=Uber+Template&templateDescription=Some+description&categoryId=1&newModelTemplate=1'
//	}
//
//	def "calling moduleById should return a JSON response that represents correct module" () {
//
//		setup:
//
//		// Added a lot of extra data... originally thought I needed to only to get modules associated with a section, but I
//		// believe I need to just get any module regardless of section.  I will verify and update this test by either removing some
//		// of the data or adding the sectionId as a paramter to the controller.
//
//		def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : setupModifyingUser).save(failOnError:true)
//		def bodyGroup = new SectionGroup(groupName : "Body", sequence : 1, lastModBy : setupModifyingUser).save(failOnError:true)
//
//		def logoSection = new Section(sectionName : "Section1", sequence : 1, lastModBy : setupModifyingUser, group : logoGroup).save(failOnError:true)
//		def bodySection = new Section(sectionName : "Some other section", sequence : 1, lastModBy : setupModifyingUser, group : bodyGroup).save(failOnError:true)
//
//		// the initial logoSection module which we will add to the letter.
//		def module1 = new Module(content: "Module1", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save(failOnError:true)
//		// These are our other logoSection Modules
//		def module2 = new Module(content: "Module2", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save(failOnError:true)
//		def module3 = new Module(content: "Module3", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save(failOnError:true)
//		def module4 = new Module(content: "Module4", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save(failOnError:true)
//
//		// our one bodySection module
//		def moduleB = new Module(content: "Some other plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : bodySection).save(failOnError:true)
//
//
//		when:
//		params["moduleId"] = module2.id
//		controller.moduleById()
//
//		then:
//		response.text == "{\"moduleList\":[{\"id\":${module2.id},\"content\":\"${module2.content}\"}]}"
//
//	}
//
//	def "calling moduleById with bad moduleId should return error message" () {
//
//		setup:
//
//		// Added a lot of extra data... originally thought I needed to only to get modules associated with a section, but I
//		// believe I need to just get any module regardless of section.  I will verify and update this test by either removing some
//		// of the data or adding the sectionId as a paramter to the controller.
//		def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : setupModifyingUser).save()
//		def bodyGroup = new SectionGroup(groupName : "Body", sequence : 1, lastModBy : setupModifyingUser).save(failOnError:true)
//
//
//		def logoSection = new Section(sectionName : "Section1", sequence : 1, lastModBy : setupModifyingUser, group : logoGroup).save()
//		def bodySection = new Section(sectionName : "Some other section", sequence : 1, lastModBy : setupModifyingUser, group : bodyGroup).save(failOnError:true)
//
//		// the initial logoSection module which we will add to the letter.
//		def module1 = new Module(content: "Module1", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
//		// These are our other logoSection Modules
//		def module2 = new Module(content: "Module2", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
//		def module3 = new Module(content: "Module3", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
//		def module4 = new Module(content: "Module4", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
//
//		// our one bodySection module
//		def moduleB = new Module(content: "Some other plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : bodySection).save(failOnError:true)
//
//
//		when:
//		params["moduleId"] = 111 // use module from other section
//
//		controller.moduleById()
//
//		then:
//
//		response.text == "Could not find Module with id:111"
//
//
//	}
//
//	def "calling recipe should return a JSON response that contains sections and attached modules" () {
//
//		setup:
//		Model modelTemplate = new Model(templateStyle : "Left Bar", imageLocation : "Some location").save()
//		populateValidParams(params)
//		def existingLetterTemplate = new LetterTemplate(params)
//		def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : setupModifyingUser).save()
//		def bodyGroup = new SectionGroup(groupName : "Body", sequence : 1, lastModBy : setupModifyingUser).save()
//		String sectionName
//		Integer sequence
//		User lastModBy
//		def logoSection = new Section(sectionName : "Some section", sequence : 1, lastModBy : setupModifyingUser, group : logoGroup).save()
//		def bodySection = new Section(sectionName : "Some other section", sequence : 1, lastModyBy : setupModifyingUser, group : bodyGroup).save()
//		def module1 = new Module(content: "Some plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
//		def module2 = new Module(content: "Some other plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : bodySection).save()
//		def ingredient1 = new Ingredient(sequence : 1, comment : 'Comment one', module : module1)
//		def ingredient2 = new Ingredient(sequence : 2, comment : 'Comment two', module : module2)
//		existingLetterTemplate.addToRecipe(ingredient1)
//		existingLetterTemplate.addToRecipe(ingredient2)
//		existingLetterTemplate.model = modelTemplate
//		existingLetterTemplate.createdBy = setupCreatingUser
//		existingLetterTemplate.lastModBy = setupModifyingUser
//		assert existingLetterTemplate.save() != null
//
//		when:
//		params["id"] = '1'
//		params["group"] = 'logo'
//		def model = controller.recipe()
//
//		then:
//		response.text == '{"modulesAndSections":{"sections":[{"id":1,"name":"Some section"}],"attachedModules":[{"ingredientId":1,"moduleId":1,"moduleContent":"Some plain text content"}]}}'
//
//
//	}
//
//
//	def "calling preview on a letterTemplate should return the correct view" () {
//
//		setup:
//		Model modelTemplate = new Model(templateStyle : "Left Bar", imageLocation : "Some location").save()
//
//		populateValidParams(params)
//		def existingLetterTemplate = new LetterTemplate(params)
//
//		def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : setupModifyingUser).save()
//
//
//		String sectionName
//		Integer sequence
//		User lastModBy
//		def logoSection = new Section(sectionName : "Some section", sequence : 1, lastModBy : setupModifyingUser, group : logoGroup).save()
//
//		def module1 = new Module(content: "Some plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
//
//		assert module1 != null
//
//		existingLetterTemplate.createdBy = setupCreatingUser
//		existingLetterTemplate.lastModBy = setupModifyingUser
//		existingLetterTemplate.model = modelTemplate
//		existingLetterTemplate.save()
//		def ingredient1 = new Ingredient(sequence : 1, comment : 'Comment one', module : module1, lastModBy : setupModifyingUser, letter : existingLetterTemplate).save()
//
//		existingLetterTemplate.addToRecipe(ingredient1).save()
//
//		when:
//		params["id"] = '1'
//		controller.preview()
//
//		then:
//		controller.modelAndView.getViewName() == '/letterTemplate/previewLeftBar'
//		controller.modelAndView.model.letterTemplateInstance != null
//		controller.modelAndView.model.logoList.size() == 1
//
//	}
//
//	def "calling edit structure should return the correct create view" () {
//
//		setup:
//		Model modelTemplate = new Model(templateStyle : "Left Bar", imageLocation : "Some location").save()
//
//	    populateValidParams(params)
//	    def existingLetterTemplate = new LetterTemplate(params)
//
//	    def logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : setupModifyingUser).save()
//
//
//	    String sectionName
//	    Integer sequence
//	    User lastModBy
//	    def logoSection = new Section(sectionName : "Some section", sequence : 1, lastModBy : setupModifyingUser, group : logoGroup).save()
//
//        def module1 = new Module(content: "Some plain text content", status : "Draft", createdBy : setupCreatingUser, lastModBy : setupModifyingUser, section : logoSection).save()
//
//	    assert module1 != null
//
//	    existingLetterTemplate.createdBy = setupCreatingUser
//	    existingLetterTemplate.lastModBy = setupModifyingUser
//	    existingLetterTemplate.model = modelTemplate
//	    existingLetterTemplate.save()
//	    def ingredient1 = new Ingredient(sequence : 1, comment : 'Comment one', module : module1, lastModBy : setupModifyingUser, letter : existingLetterTemplate).save()
//
//	    existingLetterTemplate.addToRecipe(ingredient1).save()
//
//		when:
//		params["id"] = '1'
//		controller.editStructure()
//
//		then:
//		controller.modelAndView.getViewName() == '/letterTemplate/createLeftBar'
//		controller.modelAndView.model.letterTemplateInstance != null
//		controller.modelAndView.model.logoList.size() == 1
//
//	}
//
	def "calling show without a valid id should redirect to letterTemplate list"() {
		when:
		controller.show()
			
		then:
		flash.message != null
		response.redirectedUrl == '/letterTemplate/list'
	}
	
//	def "calling show with an id of an existing letterTemplate should return that letterTemplate"() {
//
//		setup:
//		populateValidParams(params)
//		def letterTemplate = new LetterTemplate(params)
//        letterTemplate.save() != null
//
//		when:
//		params.id = letterTemplate.id
//        def model = controller.show()
//
//		then:
//		model.letterTemplateInstance == letterTemplate
//	}
	
	def "calling edit with no params.id should redirect to letterTemplate list"(){
		
		when:
		controller.edit()
		
		then:
		flash.message != null
		response.redirectedUrl == '/letterTemplate/list'
		
	}
	
//	def "calling edit with a valid id should return a valid letterTemplate" (){
//
//		setup:
//		populateValidParams(params)
//		def letterTemplate = new LetterTemplate(params)
//		letterTemplate.save() != null
//
//		when:
//		params.id = letterTemplate.id
//		def model = controller.edit()
//
//		then:
//		model.letterTemplateInstance == letterTemplate
//	}
//
//	def "calling update without an id should redirect to the letterTemplate list"(){
//
//		when:
//		controller.update()
//
//		then:
//		flash.message != null
//		response.redirectedUrl == '/letterTemplate/list'
//	}
//
//	def "calling update with invalid update params should display edit view" (){
//
//		setup:
//		populateValidParams(params)
//		def letterTemplate = new LetterTemplate(params)
//		letterTemplate.save()
//
//		when:
//		params.id = letterTemplate.id
//		params.status = "blah"
//		controller.update()
//
//		then:
//		view == "/letterTemplate/review"
//		model.letterTemplateInstance != null
//
//	}
//
//	def "calling update with valid params should redirect to show and have a valid lastModBy" () {
//
//		setup:
//
//		populateValidParams(params)
//		def letterTemplate = new LetterTemplate(params)
//		letterTemplate.save()
//
//		when:
//		params.id = letterTemplate.id
//		controller.update()
//
//		then:
//		response.redirectedUrl == "/letterTemplate/review/$letterTemplate.id"
//		flash.message != null
//
//	}
//
//	def "calling update with an outdated version number should result in an error"(){
//
//		setup:
//		populateValidParams(params)
//		def letterTemplate = new LetterTemplate(params)
//		letterTemplate.save(flush:true)
//
//
//		when:
//		params.id = letterTemplate.id
//		params.version = -1
//		controller.update()
//
//		then:
//		view == "/letterTemplate/review"
//		model.letterTemplateInstance != null
//		model.letterTemplateInstance.errors.getFieldError('version')
//
//	}
//
//	def "attempting to delete without an input id should redirect back to letterTemplate list" (){
//
//		when:
//		controller.delete()
//
//		then:
//		flash.message != null
//		response.redirectedUrl == '/letterTemplate/list'
//
//	}
//
//	def "attempting to delete with a valid id should result in ingredient no longer found" (){
//
//		setup:
//		populateValidParams(params)
//		def letterTemplate = new LetterTemplate(params)
//		letterTemplate.save(flush:true)
//
//		when:
//		params.id = letterTemplate.id
//		controller.delete()
//
//
//		then:
//		LetterTemplate.count() == 0
//		LetterTemplate.get(letterTemplate.id) == null
//		response.redirectedUrl == '/letterTemplate/list'
//	}
//
//	def "call delete with ingredients should result in template being deleted" () {
//
//		setup:
//		//Create a stubbed out SpringSecurityService for unit tests
//		def springSecurityService = new Object()
//		//Override the encodePassword method, which is used by the User object before data is inserted
//		springSecurityService.metaClass.encodePassword = {String password =  "ENCODED_PASSWORD"}
//		User user = new User(username:'nimoyL', password:"enterprise", firstName:"Leonard",lastName:"Nimoy")
//		user.springSecurityService = springSecurityService
//
//		user.save()
//
//		def sectionGroup = new SectionGroup(groupName:"Advocacy Message", sequence: 2, lastModBy: user)
//		sectionGroup.save()
//		def section = new Section(sectionName:"Tip", sequence: 3, lastModBy: user, group: sectionGroup)
//		section.save()
//
//		def module = new Module(content : "some module", status : "Draft", section : section, createdBy: user, lastModBy: user)
//		def comment1 = new Comment(comment: "sample comment #1", lastModBy: user, module: module, lastUpdated: new Date())
//		comment1.save()
//
//		def comment2 = new Comment(comment: "sample comment #2", lastModBy: user, module: module, lastUpdated: new Date() + 15)
//		comment2.save()
//
//		comment1.save()
//		module.comments = [comment1, comment2]
//		module.save()
//		module.id > 0
//
//		def category = new Category(name: 'Claims', lastModBy: module.lastModBy)
//		category.save()
//
//		def model = new Model(templateStyle:"Coupon",imageLocation:"\\images\\someimage.jpg")
//		model.save()
//
//		populateValidParams(params)
//		def letterTemplate = new LetterTemplate(params)
//
//		def ingredient1 = new Ingredient(comment: "Ingredient #1", lastModBy: module.lastModBy, sequence: 1, letter: letterTemplate, module: module)
//		ingredient1.save()
//
//		def ingredient2 = new Ingredient(comment: "Ingredient #2", lastModBy: module.lastModBy, sequence: 2, letter: letterTemplate, module: module)
//		ingredient2.save()
//
//		def ingredient3 = new Ingredient(comment: "Ingredient #3", lastModBy: module.lastModBy, sequence: 3, letter: letterTemplate, module: module)
//		ingredient3.save()
//
//		letterTemplate.recipe = [ingredient1, ingredient2, ingredient3]
//
//		when:
//		params.id = letterTemplate.id
//		controller.delete()
//
//		then:
//		Module.count() == 1
//		LetterTemplate.count() == 0
//		LetterTemplate.get(letterTemplate.id) == null
//		response.redirectedUrl == '/letterTemplate/list'
//
//	}
//
//	def "calling updateRecipe should reorder the recipe"(){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		params.recipe = "3,1,2"
//		controller.updateRecipe()
//
//		then:
//		response.text == '{"success":true}'
//		Ingredient.get(3).sequence == 1
//		Ingredient.get(1).sequence == 2
//		Ingredient.get(2).sequence == 3
//	}
//
//	def "calling updateRecipe when ingredient id is invalid should fail and not update recipe" (){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		params.recipe = "3,1,500,2"
//		controller.updateRecipe()
//
//		then:
//		response.text == '{"success":false}'
//		Ingredient.get(1).sequence == 1
//		Ingredient.get(2).sequence == 2
//		Ingredient.get(3).sequence == 3
//	}
//
//	def "calling update should return false after a database exception occurs"(){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		params.recipe = "3,1,2"
//
//		// force a save of IngredientId #2 to throw an exception
//		Ingredient.get(2).metaClass.save { Map args ->
//			throw new DataIntegrityViolationException("something terrible happened")
//		}
//
//		controller.updateRecipe()
//
//		then:
//		response.text == '{"success":false}'
//
//
//	}
//
//	def "delete middle ingredient in recipe should update sequence correctly" (){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		params.deleteIngredientId = "2"
//		params.recipe = "1,3"
//		controller.deleteIngredient()
//
//		then:
//		response.text == '{"success":true}'
//		LetterTemplate.get(1).recipe.size() == 2
//		Ingredient.get(1).sequence == 1
//		Ingredient.get(2) == null
//		Ingredient.get(3).sequence == 2
//	}
//
//	def "deleting an ingredient from a recipe with only one ingredient should result in an empty sequence" (){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		// adjust the test data to have only one recipe
//		Ingredient ingredient = Ingredient.get(2)
//		ingredient.delete(flush: true)
//
//		ingredient = Ingredient.get(3)
//		ingredient.delete(flush: true)
//
//		when:
//		params.deleteIngredientId = "1"
//		params.recipe = ""
//
//		controller.deleteIngredient()
//
//		then:
//		response.text == '{"success":true}'
//		LetterTemplate.get(1).recipe.size() == 0
//		Ingredient.get(1) == null
//
//	}
//
//	def "attempting and failing to remove a middle ingredient should result in nothing changing" (){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		params.deleteIngredientId = "2"
//		params.recipe = "1,3"
//
//		// force a delete to throw an exception
//		Ingredient.get(2).metaClass.delete { Map args ->
//			throw new DataIntegrityViolationException("something terrible happened")
//		}
//
//		controller.deleteIngredient()
//
//		then:
//		response.text == '{"success":false}'
//		LetterTemplate.get(1).recipe.size() == 3
//		Ingredient.get(1).sequence == 1
//		Ingredient.get(2).sequence == 2
//		Ingredient.get(3).sequence == 3
//
//
//
//	}
//
//	def "adding an ingredient with no letterTemplate Id should fail" (){
//
//		setup:
//		params.moduleId = 1
//
//		when:
//		def result = controller.addIngredient()
//
//		then:
//		response.text == '{"success":false}'
//	}
//
//	def "adding an ingredient with no module Id should fail"(){
//
//		setup:
//		params.letterTemplateId = 1
//
//		when:
//		def result = controller.addIngredient()
//
//		then:
//		response.text == '{"success":false}'
//
//	}
//
//	def "happy path for adding ingredient results in an ingredient being added"(){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		params.letterTemplateId = 1
//		params.moduleId = 1
//
//		controller.addIngredient()
//		def newIngredient = Ingredient.get(4)
//
//		then:
//		newIngredient != null
//		response.text == '{"success":true,"ingredientId":4}'
//		letterTemplate.recipe.size() == 4
//		letterTemplate.recipe.contains(newIngredient)
//		Module.get(1).ingredients.size() == 4
//		Module.get(1).ingredients.contains(newIngredient)
//
//		// The new ingredient should be at the end of the sequence
//		newIngredient.sequence == 4
//	}
//
//	def "unhappy path for adding an ingredient results in db exception and failure response"(){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		// force a save to throw an exception
//		letterTemplate.metaClass.save { Map args ->
//			throw new DataIntegrityViolationException("something terrible happened")
//		}
//
//		params.letterTemplateId = 1
//		params.moduleId = 1
//
//		controller.addIngredient()
//
//		then:
//		response.text == '{"success":false}'
//
//
//	}
//
//	def "adding a comment to a letterTemplate should result in comment being added"(){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		params.id = "1"
//		params.comment = "This is some comment"
//
//		controller.addComment()
//		def letter = LetterTemplate.get(1)
//
//		then:
//		response.getStatus() == 200
//		letter?.comments.size() == 1
//
//
//	}
//
//	def "adding a comment to a letterTemplate should fail if request parameters are bad"(){
//
//		setup:
//		def letterTemplate = ConstraintUnitSpec.createSampleLetterTemplate()
//
//		when:
//		params.id = "2"
//		params.comment = "This is some comment"
//
//		controller.addComment()
//		def letter = LetterTemplate.get(1)
//
//		then:
//		response.getStatus() == 404
//		letter?.comments == null
//	}
//
		


}
