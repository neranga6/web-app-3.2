package com.web.app

import org.springframework.dao.DataIntegrityViolationException
//import org.springframework.security.access.annotation.Secured
//import grails.plugin.springsecurity.SpringSecurityService
//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])

class IngredientController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    //SpringSecurityService springSecurityService


    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [ingredientInstanceList: Ingredient.list(params), ingredientInstanceTotal: Ingredient.count()]
    }

	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
    def create() {
        [ingredientInstance: new Ingredient(params)]
    }

	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
   def save() {
		
		User currentUser = springSecurityService.getCurrentUser()
		params["lastModBy"]=currentUser
		
        def ingredientInstance = new Ingredient(params)
        if (!ingredientInstance.save(flush: true)) {
            render(view: "create", model: [ingredientInstance: ingredientInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'ingredient.label', default: 'Ingredient'), ingredientInstance.id])
        redirect(action: "show", id: ingredientInstance.id)
    }

    def show() {
        def ingredientInstance = Ingredient.get(params.id)
        if (!ingredientInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingredient.label', default: 'Ingredient'), params.id])
            redirect(action: "list")
            return
        }

        [ingredientInstance: ingredientInstance]
    }

	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
   def edit() {
        def ingredientInstance = Ingredient.get(params.id)
        if (!ingredientInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingredient.label', default: 'Ingredient'), params.id])
            redirect(action: "list")
            return
        }

        [ingredientInstance: ingredientInstance]
    }

	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
    def update() {
        def ingredientInstance = Ingredient.get(params.id)
        if (!ingredientInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingredient.label', default: 'Ingredient'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (ingredientInstance.version > version) {
                ingredientInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'ingredient.label', default: 'Ingredient')] as Object[],
                          "Another user has updated this Ingredient while you were editing")
                render(view: "edit", model: [ingredientInstance: ingredientInstance])
                return
            }
        }

		User currentUser = springSecurityService.getCurrentUser()
        ingredientInstance.properties = params
		ingredientInstance.lastModBy = currentUser

        if (!ingredientInstance.save(flush: true)) {
            render(view: "edit", model: [ingredientInstance: ingredientInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'ingredient.label', default: 'Ingredient'), ingredientInstance.id])
        redirect(action: "show", id: ingredientInstance.id)
    }

	//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
   def delete() {
        def ingredientInstance = Ingredient.get(params.id)
        if (!ingredientInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingredient.label', default: 'Ingredient'), params.id])
            redirect(action: "list")
            return
        }

        try {
            ingredientInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'ingredient.label', default: 'Ingredient'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ingredient.label', default: 'Ingredient'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
