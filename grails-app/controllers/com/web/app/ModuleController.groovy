package com.web.app

//import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.dao.DataIntegrityViolationException
//import org.springframework.security.access.annotation.Secured

//@Secured(['ROLE_USER','ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
class ModuleController {
	CommentService commentService
	BusinessRuleService businessRuleService
	//SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    SearchType readSearchType(String rawSearchType) {
		SearchType searchType = SearchType.createSearchType(rawSearchType)
		
		if (!searchType) {
			flash.error = message(code: "typeMismatch.searchType", args: [rawSearchType])
			redirect uri: '/'
		}
		
		return searchType
	}
	
	private Module retrieveModuleById(moduleId) {
		def moduleInstance
		
		try {
			moduleInstance = Module.get(moduleId)
		} catch(ex) {
			log.warn("User entered a moduleId that could not be retrieved: \"${moduleId}\"", ex)
		}
		
		return moduleInstance
	}
	
	private List<Module> retrieveModuleByFieldLike(String fieldName, String searchCriteria) {
		def moduleCriteria = Module.createCriteria()
		
		def modules = moduleCriteria.list(params) {
			// the percent symbols are required for the "like" operation
			ilike(fieldName, "%${searchCriteria}%")
		}
		
		return modules
	}
		
    def index() {
        redirect(action: "list", params: params)
    }

	def createCriteria(params){
		
		SearchType searchType
		def criteria = params.criteria
		// figure out the usage count to use in queries below
		int usageCount = params.usageCount?params.usageCount as int:0
		
		def usageFilter = "sizeGt" // default to greater than
		UsageFilterType usageType = params.usageFilterType
		switch (params.usageFilterType) {
			case UsageFilterType.LessThan.toString():
				usageFilter = "sizeLt"
				break

            case UsageFilterType.Equal.toString():
				usageFilter = "sizeEq"
				break
        }
		
		return {
				// the percent symbols are required for the "like" operation
				and{
					if(params.statusType && params.statusType!= "All") {
						eq("status", "${params.statusType}", [ignoreCase:true])
					}
					"$usageFilter"("ingredients", usageCount)
					if(criteria){
						ilike("content", "%${criteria}%")
					}
				}
				order("id", "desc")
				
			}
		
	}
	
	/**
	 * List all Modules.  If the parameter "content" is specified, then we return the modules with that content (case insensitive).
	 */
    def list() {
        //params.max = Math.min(params.max ? params.int('max') : 15, 100)
		params.max = params.numOfResultsSelect?params.numOfResultsSelect as Integer: 25

		StatusType statusType
		SearchType searchType
		def criteria = params.criteria
		// figure out the usage count to use in queries below
		int usageCount = params.usageCount?params.usageCount as int:0
		
		def modules = []
		
		int total = 0
		
		def c = Module.createCriteria()
		
		def criteriaClosure = createCriteria(params)
		
		println "criteriaClosure type = ${criteriaClosure.class}"
		
		modules = c.list (params, criteriaClosure)
		total = modules.totalCount
		def criteriaParams = criteriaClosure.params
		//def criteriaPrint = criteriaParams.find{it.key == "amp;print"}

		// create the EmailURLParameters to properly set the sort
		String emailURLParameters = "?statusType=${params.statusType?:StatusType.All.toString()}"
		
		def usageTypeSetting = params.usageFilterType?:UsageFilterType.GreaterThan
		emailURLParameters+="&usageFilterType=${usageTypeSetting}"
		
		if(params.criteria){
			// had to encode this since you could have spaces and such in it.
			emailURLParameters+= "&criteria=${params.criteria.encodeAsURL()}"
		}
		if(params.usageCount){
			emailURLParameters+= "&usageCount=${params.usageCount}"
		}
		
		// if we have sort info in the params, then add them to the email URL
		emailURLParameters+=Utils.createSortURL(params)
		
		if(params.print){
				render(template: "printList", model: [moduleInstanceList: modules, moduleInstanceTotal: total, statusType:params.statusType?:'All', usageTypeFilter: params.usageFilterType?:UsageFilterType.GreaterThan.text, usedCount: params.usageCount?:'0', emailURLParms: emailURLParameters, numOfResults2Show:params.max])
		}else{
			[moduleInstanceList: modules, moduleInstanceTotal: total, statusType:params.statusType?:'All', usageTypeFilter: params.usageFilterType?:UsageFilterType.GreaterThan.text, usedCount: params.usageCount?:'0', emailURLParms: emailURLParameters, numOfResults2Show:params.max]
		}
    }
	
	def globalSearch(){
		params.max = params.numOfResultsSelect?params.numOfResultsSelect as Integer: 25
		
		SearchType searchType
		def criteria
		
		if(params.searchType) {
			searchType = readSearchType(params.searchType)
			criteria = params.criteria
		}
		
		def modules = []
		int total = 0
		
		if(searchType == SearchType.MODULE_ID && criteria) {
			def moduleId = criteria
			def moduleInstance = retrieveModuleById(moduleId)
			if (!moduleInstance) {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), criteria])
				redirect(action: "list")
				return
			}
			
			modules.add(moduleInstance)
			total = 1
		} else if(searchType == SearchType.MODULE_CONTENT && criteria) {
			def content = criteria
			
			modules = retrieveModuleByFieldLike("content", content)
			total = modules.totalCount
		} else {
			modules = Module.list(params)
			total = Module.count
		}
		
		render(view: "list", model: [moduleInstanceList: modules, moduleInstanceTotal: total, numOfResults2Show:params.max])
	}
	

    def create() {
        [moduleInstance: new Module(params)]
    }

    def save() {
		
		User currentUser
		params["lastModBy"]=currentUser
		
		def moduleInstance = new Module(params)
		
		if (moduleInstance.createdBy == null) {
			moduleInstance.createdBy = currentUser
		}		
		moduleInstance.lastModBy = currentUser

		
		if (!moduleInstance.save(flush: true)) {
            render(view: "create", model: [moduleInstance: moduleInstance])
            return
        }
	
		
		if(params.comment){
			addComment(params, moduleInstance, currentUser)
		}
				
		if(params.rule){
			addRule(params, moduleInstance, currentUser)
		}
				
		flash.message = message(code: 'default.created.message', args: [message(code: 'module.label', default: 'Module'), moduleInstance.id])
        redirect(action: "edit", id: moduleInstance.id)
    }

    def show() {
        def moduleInstance = retrieveModuleById(params.id)
        if (!moduleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }

        [moduleInstance: moduleInstance]
    }
	
	def letters() {
		edit()
	}

	
    def edit() {
        def moduleInstance = retrieveModuleById(params.id)
        if (!moduleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }

		
		//If the User is a Reviewer or Admin, add Final to the status drop down list
	//	if (SpringSecurityUtils.ifAllGranted("ROLE_REVIEWER")) {
			moduleInstance.statusValues << "Final"						
		//The User is a Writer
//		}else {
//			If the status is Review or Final, set the display to read only on the screen
		//	if (moduleInstance.status in ["Review","Final"])
//				moduleInstance.statusEnabled = false
//		}

        [moduleInstance: moduleInstance]
    }

	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
    def update() {
		
        def moduleInstance = retrieveModuleById(params.id)
        if (!moduleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (moduleInstance.version > version) {
                moduleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'module.label', default: 'Module')] as Object[],
                          "Another user has updated this Module while you were editing")
                render(view: "edit", model: [moduleInstance: moduleInstance])
                return
            }
        }

		User currentUser = springSecurityService.getCurrentUser()
		moduleInstance.properties = params
		moduleInstance.lastModBy = currentUser
		
        if (!moduleInstance.save(flush: true)) {
            render(view: "edit", model: [moduleInstance: moduleInstance])
            return
        }

		if(params.comment){
			addComment(params, moduleInstance, currentUser)
		}
		
		if(params.rule){
			addRule(params, moduleInstance, currentUser)
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'module.label', default: 'Module'), moduleInstance.id])
        redirect(action: "edit", id: moduleInstance.id)
    }

	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER'])
    def delete() {
        def moduleInstance = retrieveModuleById(params.id)
        if (!moduleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
            return
        }

        try {
            moduleInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'module.label', default: 'Module'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	/**
	 * This is the "Copy Module" functionality, which clones an existing Module 
	 * into a brand new one.  The Comment items are not copied over.
	 */
	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
	def copy() {
		def moduleInstance = retrieveModuleById(params.id)
		if (!moduleInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])
			redirect(action: "list")
			return
		}

		//Create a new instance from the initial 
		def newModuleInstance = cloneMe(moduleInstance)
		newModuleInstance.save(flush:true)

		//Notify the user that the Module has been copied and render the edit view
		flash.message = message(code:'module.copied.message', args:[newModuleInstance.id])
		render(view:"edit",model: [moduleInstance: newModuleInstance])		
	}
	

   /*
    * Clone a module from an existing Module.
    * 
    * Note that copying the collections as a whole will result in a shared collection
    * which GORM and Hibernate will not allow.
    */
   Module cloneMe(Module instanceToClone) {
	   User currentUser = springSecurityService.getCurrentUser()
   		Module newModule = new Module()
		newModule.status = instanceToClone.status
		newModule.content = instanceToClone.content
		newModule.createdBy = currentUser
		newModule.lastModBy = currentUser
		newModule.section = instanceToClone.section
		
		
		// BusinessRules are not shared; they're copied over to the new instance.
		instanceToClone.businessRules?.each{
			newModule.addToBusinessRules(it.getInstance())
		}
		
		// Ingredients are not copied when a module is copied because they should not be associated with a letter
		// 
//		instanceToClone.ingredients?.each {
//			newModule.addToIngredients(it.getInstance())
//		}
		
		// don't clone comments (new Module instance will have no associated comments)
				
		return newModule
   }
	
	def insertImage(){
		
	}
	
	def uploadImage(){
		
	}
	
	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
	private void addComment(Object params, Module moduleInstance, User user){

		def commentInstance = commentService.createComment(params)
		commentInstance.module = moduleInstance
		
		commentInstance.lastModBy = user
		if (commentService.saveComment(commentInstance)) {
			moduleInstance.addToComments(commentInstance)
		}
		
	}
	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
	private void addRule(Object params, Module moduleInstance, User user){
		
		def ruleInstance = businessRuleService.createBusinessRule(params)
		ruleInstance.module = moduleInstance
		
		ruleInstance.lastModBy = user
		if (businessRuleService.saveBusinessRule(ruleInstance)) {
			moduleInstance.addToBusinessRules(ruleInstance)
		}
		
	}

	def listReport(){
		params.order = params.order ?: "desc"
		params.order = params.order ?: "asc"
		params.max = Math.min(params.max ? params.int('max') : 20, 100)
		
		StatusType statusType
		def criteria
		List<Module> modules = []
		int total = 0

		def sectionNameList = ["All"]
		
		def sectionList = Section.list()
		
		sectionList.each{ sectionNameList.add(it.sectionName) }
						
		def c = Module.createCriteria()
		
		def criteriaClosure = createCriteriaForReport(params)


		modules = c.list (params, criteriaClosure)

		if(params.order =="asc") {
			modules = modules.sort {it.id}

		}else if(params.order =="desc"){

			modules = modules.sort {-it.id}
		}


		total = modules.totalCount
		def criteriaParams = criteriaClosure.params


		// get latest 3 comments for each module
		modules.each {
			
			if(it.comments && it.comments.size()>0){
				
				// first add a property to hold the 3 comments
				it.metaClass.newComments = [] // empty list

				def sortedComments = it.comments.sort {comment->
					comment.lastUpdated // sort by lastUpdated this will put the newest at the bottom
				}
				
								
				// we need to collect the last 3 in the sorted list to get the latest ones
				def lastIndex = sortedComments.size() -1
				// so we need to start at the last index and work our way backwards through the list
				// to get the 3 latest comments.
				// so, go from the end to 3 from the end if there are at least 3... if not go to 0(start of the list)
				def bottomRange = sortedComments.size() >3?lastIndex-2:0
				// we go backwards to put the latest at the top
				(lastIndex..bottomRange).each{i->
					it.newComments.add(sortedComments[i])
				}
			
			}
		}
		
		
		// create the EmailURLParameters to properly set the sort
		String emailURLParameters = "?statusType=${params.statusType?:StatusType.All.toString()}&sectionSelection=${params.sectionSelection?params.sectionSelection.encodeAsURL():'All'}"
		// if we have sort info in the params, then add them to the email URL
		emailURLParameters+=Utils.createSortURL(params)
		if(params.criteria){
			// had to encode this since you could have spaces and such in it.
			emailURLParameters+= "&criteria=${params.criteria.encodeAsURL()}"
		}
		
		if(params.print){
			render(template: "printListReport", model: [sectionNameList:sectionNameList, moduleInstanceList: modules, moduleInstanceTotal: total, statusType:params.statusType?:'All', emailURLParms: emailURLParameters])
		}else{
			[sectionNameList:sectionNameList, moduleInstanceList: modules, moduleInstanceTotal: total, statusType:params.statusType?:'All', emailURLParms: emailURLParameters]	
		}
	}
	
	
	def createCriteriaForReport(params){
		def criteria = params.criteria
		return {
				createAlias("section","_section")
				// the percent symbols are required for the "like" operation
				and{
					if(params.statusType && params.statusType!= "All") {
						eq("status", "${params.statusType}", [ignoreCase:true])
					}
					
					if(params.sectionSelection && params.sectionSelection!= "All"){
						eq("_section.sectionName", "${params.sectionSelection}", [ignoreCase:true])
					}
					if(criteria){
						ilike("content", "%${criteria}%")
					}
				}
				
				order("_section.sectionName", "asc")
				order("id", "desc")
				
			}
		
	}
		
}
