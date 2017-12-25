package com.web.app

import spock.lang.Specification
import spock.lang.Unroll
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(LetterTemplate)
@Mock([LetterTemplate,Ingredient,Category,Model])
class LetterTemplateSpec extends Specification{

    void setup() {
        //mock a person with some data (put unique violations in here so they can be tested, the others aren't needed)
        //mockForConstraintsTests(LetterTemplate, [new LetterTemplate()])
    }

    void teardown() {
        // Tear down logic here
    }

	@Unroll("test LetterTemplate field #field constraint #error")
    "test LetterTemplate all constraints"() {
		
		when:
		def p = new LetterTemplate(name: 'jeff',description:"name",status:"employed")

		then:
		p.name == name
		p.description == description
		p.status == status

		where:
		name         | description   | status
		'jeff'       | 'name'	 	| "employed"

		}
	
}
