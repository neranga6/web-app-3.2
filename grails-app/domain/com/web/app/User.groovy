package com.web.app

class User {

	def springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	String firstName
	String lastName


	static constraints = {
		username blank: false, unique: true
		password nullable:true
		firstName nullable:true, blank:true
		lastName nullable:true, blank:true
	}

	static mapping = {
		password column: '`password`'
		// "User" is a reserved word in Oracle, so change the underlying table name
		table "WRITERS_USER"
	}

	Set<Role> getAuthorities() {
			def userRoles = UserRole.findAllByUser(this).collect { it.role } as Set
		}


	def beforeInsert() {
		if (password == null){
			password = "password"
			encodePassword()
		}

	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

	String toString() {
		username
	}

	String getUserRole(){
		def roles = this.getAuthorities()
		def role = ""
			roles.each{
				role = it.authority

			}
			if(role){

				return role.substring(5)
			}
			return role
		}

}
