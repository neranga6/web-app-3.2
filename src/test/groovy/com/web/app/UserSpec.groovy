package com.web.app

import spock.lang.Specification
import spock.lang.Unroll
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(User)
@Mock([Role, UserRole])

class UserSpec extends Specification{

    void setup() {
		//mockForConstraintsTests(User, [new User()])
    }

    void teardown() {
        // Tear down logic here
    }
	
	@Unroll("test User all constraints #field is #error")
    "test User all constraints"() {

		when:
		def p = new User(username: 'jeff',password:"1234",firstName:"Matt",lastName:"Carter")

		then:
		p.username == username
		p.password == password
		p.firstName == firstName
		p.lastName == lastName

		where:
		username            | password       	|firstName |lastName
		'jeff'              | "1234"	 	    | "Matt"   | "Carter"


	}
	
	private createRoles(Integer count) {
		def roles = []
		count.times {
			roles << new Role()
		}

		roles
	}
	
	def "find role for user"(){
		
		setup: 
		def userRole = new Role(authority:"ROLE_USER").save(flush:true, failOnError:true)
		def adminRole = new Role(authority:"ROLE_ADMIN").save()
		def cyclops = new User(username:"cyclops",firstName:"Scott",lastName:"Summers", password:"password",enabled:true,accountExpired:false,accountLocked:false,passwordExpired:false).save()
		cyclops.springSecurityService = Mock(grails.plugin.springsecurity.SpringSecurityService)
	      
		when:
		UserRole.create(cyclops, userRole)
		UserRole.create(cyclops,adminRole)
		
		
		then:
		
		cyclops.getUserRole() == 'ADMIN'
		

		
	}

}
