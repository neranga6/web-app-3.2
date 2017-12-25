package com.web.app
import grails.test.mixin.*
import spock.lang.Specification

@TestFor(BusinessUnitController)
@Mock(BusinessUnit)
class BusinessUnitControllerTests extends Specification{
	
	

    def populateValidParams(params) {
        assert params != null

        params["name"] = 'BU1'
    }

    void testIndex() {
        controller.index()
        expect:
		 "/businessUnit/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

		expect:
        	model.businessUnitInstanceList.size() == 0
			model.businessUnitInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        expect:
			model.businessUnitInstance != null
    }


	
}
