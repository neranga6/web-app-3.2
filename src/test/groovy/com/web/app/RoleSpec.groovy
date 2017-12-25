package com.web.app

import spock.lang.Specification
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Role)
@Mock(Role)
class RoleSpec extends Specification{

//    void setup() {
//		mockDomain(Role, [new Role(authority:"DUPLICATE")])
//    }
//
//    void teardown() {
//        // Tear down logic here
//    }

//	@Unroll("test Role all constraints #field is #error")
//    "test Role all constraints"() {
//
//		when:
//		def obj = new Role("$field": val)
//
//		then:
//		validateConstraints(obj, field, error)
//
//		where:
//		error               | field        	| val
//		'nullable'          | 'authority'	 	| null
//		'valid'				| 'authority'	    | "ROLE_ADMIN"
//		'unique'			| 'authority'	    | "DUPLICATE"
//
//	}

	def "test persisting a valid Role"() {
		setup:
		mockDomain(Role)
		mockDomain(User)		
		
		when:
		Role testRole = new Role(authority:testRoleName)
		//testRole.lastModBy = generateUser("cyclops")		
		testRole.save()
		
		then:
		Role.findByAuthority(testRoleName) != null
		
		where:
		testRoleName = "Admin"
	} 

}
