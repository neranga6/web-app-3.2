package com.web.app

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(AdminController)
class HomeControllerSpec extends Specification {
     void testIndex() {
        when:
        controller.index()
        then:
        view == "/admin/admin"
    }
}

