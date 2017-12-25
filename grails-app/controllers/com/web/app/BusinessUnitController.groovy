package com.web.app

//import grails.plugin.springsecurity.annotation.Secured
import org.springframework.dao.DataIntegrityViolationException

//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
class BusinessUnitController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [businessUnitInstanceList: BusinessUnit.list(params), businessUnitInstanceTotal: BusinessUnit.count()]
    }

    def create() {
        [businessUnitInstance: new BusinessUnit(params)]
    }

    def save() {
        def businessUnitInstance = new BusinessUnit(params)
        if (!businessUnitInstance.save(flush: true)) {
            render(view: "create", model: [businessUnitInstance: businessUnitInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), businessUnitInstance.id])
        redirect(action: "list")
    }

    def edit(Long id) {
        def businessUnitInstance = BusinessUnit.get(id)
        if (!businessUnitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), id])
            redirect(action: "list")
            return
        }

        [businessUnitInstance: businessUnitInstance]
    }

    def update(Long id, Long version) {
        def businessUnitInstance = BusinessUnit.get(id)
        if (!businessUnitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (businessUnitInstance.version > version) {
                businessUnitInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'businessUnit.label', default: 'BusinessUnit')] as Object[],
                        "Another user has updated this BusinessUnit while you were editing")
                render(view: "edit", model: [businessUnitInstance: businessUnitInstance])
                return
            }
        }

        businessUnitInstance.properties = params

        if (!businessUnitInstance.save(flush: true)) {
            render(view: "edit", model: [businessUnitInstance: businessUnitInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), businessUnitInstance.id])
        redirect(action: "list")
    }

    def delete(Long id) {
        def businessUnitInstance = BusinessUnit.get(id)
        if (!businessUnitInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), id])
            redirect(action: "list")
            return
        }

        try {
            businessUnitInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'businessUnit.label', default: 'BusinessUnit'), id])
            redirect(action: "list")
        }
    }
}
