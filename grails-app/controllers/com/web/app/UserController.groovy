package com.web.app

import org.springframework.dao.DataIntegrityViolationException
//import org.springframework.security.access.annotation.Secured
//@Secured(['ROLE_ADMIN'])
class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]

    }

    def create() {

    }

    def save() {
        if (User.findByUsername(params.username)) {
            flash.warning = "User cannot be created username " + params.username + " already exists"
            redirect(action: "list")
            return
        } else {

            //Note this field can be edited later, if the Admin wishes to disable the User's account
            params.enabled = true

            def userInstance = new User(params)

            if (!userInstance.save(flush: true)) {
                render(view: "create", model: [userInstance: userInstance])
                return
            }

            def roleAuthority = params.userRoleSelection ?: 'USER'
            def dbRoleName = 'ROLE_' + roleAuthority
            def role = Role.findByAuthority(dbRoleName)
            def userRole = UserRole.create(userInstance, role)

            if (!userInstance.save(flush: true)) {
                render(view: "create", model: [userInstance: userInstance])
                return
            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
            redirect(action: "list")

        }
    }

    def show() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit() {
	
		def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

		[userInstance: userInstance]
    }

    def update() {
		
	    def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params
	
		if (userInstance.userRole != params.userRoleSelection){
				def roleAuthority = params.userRoleSelection ?: 'USER'
				def dbRoleName = 'ROLE_' + roleAuthority
				def role = Role.findByAuthority(dbRoleName)
				// not calling removeAll because I got this in my unit test - 
				// java.lang.UnsupportedOperationException: String-based queries like [executeUpdate] are currently not supported in this implementation of GORM. Use criteria instead.
				def roles = userInstance.getAuthorities()
				roles.each{
					UserRole.remove(userInstance, it)
				}
				UserRole.create(userInstance, role)
		}
			

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
			return
        }

		flash.message = message(code: 'default.updated.message.string.arg', args: [message(code: 'user.label', default: 'User'), userInstance.username])
        redirect(action: "list")
    }

    def delete() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        try {
			userInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), username])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
