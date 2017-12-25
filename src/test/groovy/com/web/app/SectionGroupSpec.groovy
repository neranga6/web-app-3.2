package com.web.app

import org.junit.Ignore
import spock.lang.Specification
import spock.lang.Unroll
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(SectionGroup)
@Ignore
class SectionGroupSpec extends Specification{

    void setup() {
        //mock an object with some data (put unique violations in here so they can be tested, the others aren't needed)
        mockForConstraintsTests(SectionGroup, [new SectionGroup()])
    }

    void teardown() {
        // Tear down logic here
    }

	@Unroll("test Group all constraints #field is #error")
    "test Group all constraints"() {
		
		when:
		def obj = new SectionGroup("$field": val)

		then:
		validateConstraints(obj, field, error)

		where:
		error                  | field        		| val
		'nullable'             | 'groupName'		| null
		'blank'                | 'groupName'		| ''
		'valid'                | 'groupName'		| 'Some name'
		'range'                | 'sequence'		    | -1
		'range'                | 'sequence'         | 0
		'range'                | 'sequence'		    | 21
		'valid'                | 'sequence'		    | 20
		'nullable'             | 'sequence'         | null
		'nullable'             | 'lastModBy'        | null
		'valid'                | 'lastModBy'        | new User()
		'valid'                | 'lastUpdated'      | new Date()
		'valid'                | 'sections'         | null 
		'valid'                | 'sections'         | createSection(10)
		 
 	}
	
		
	def "test persisting a valid SectionGroup"() {
		setup:
		mockDomain(Role)
		mockDomain(User)
		mockDomain(SectionGroup)
				
		when:
		Role adminRole = generateRole(WritersToolConstants.ADMIN_ROLE).save(flush:true)
		User biffle = generateUser("biffleg").save(flush:true)
		
		Date creationDate = new Date()
		SectionGroup logoGroup = new SectionGroup(groupName : "Logo", sequence : 1, lastModBy : biffle, lastUpdated : creationDate).save()
			
		then:
		SectionGroup.findByGroupName(testSectionGroup)
		SectionGroup.findByGroupName(testSectionGroup).lastModBy.username == "biffleg"
						
		where:
		testSectionGroup = "Logo"
		
		
	}
	
	private createSection(Integer count){
		def sections = []
		count.times {
			sections << new Section()
		}
		sections
	}
	
}