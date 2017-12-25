package com.web.app
//import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.dao.DataIntegrityViolationException
//import org.springframework.security.access.annotation.Secured


class CategoryController {
   // SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [categoryInstanceList: Category.list(params), categoryInstanceTotal: Category.count()]
    }

    //@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
 def   create() {
        [categoryInstance: new Category(params)]
    }

   // @Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
  def  save() {

        User currentUser = springSecurityService.getCurrentUser()
        params["lastModBy"] = currentUser

        def categoryInstance = new Category(params)

        categoryInstance.lastModBy = currentUser

        if (!categoryInstance.save(flush: true)) {
            render(view: "create", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
        redirect(action: "show", id: categoryInstance.id)
    }

    def show() {
        def categoryInstance = Category.get(params.id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])
            redirect(action: "list")
            return
        }

        [categoryInstance: categoryInstance]
    }

   // @Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
   def edit() {
        def categoryInstance = Category.get(params.id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])
            redirect(action: "list")
            return
        }

        [categoryInstance: categoryInstance]
    }

   // @Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
    def update() {
        def categoryInstance = Category.get(params.id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (categoryInstance.version > version) {
                categoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'category.label', default: 'Category')] as Object[],
                        "Another user has updated this Category while you were editing")
                render(view: "edit", model: [categoryInstance: categoryInstance])
                return
            }
        }

        User currentUser = springSecurityService.getCurrentUser()
        categoryInstance.properties = params
        categoryInstance.lastModBy = currentUser

        if (!categoryInstance.save(flush: true)) {
            render(view: "edit", model: [categoryInstance: categoryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
        redirect(action: "list")
    }

   // @Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
   def delete() {

        def categoryInstance = Category.get(params.id)
        if (!categoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), params.id])
            redirect(action: "list")
            return
        }

        try {
            categoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
