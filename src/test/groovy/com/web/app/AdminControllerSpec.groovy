package com.web.app

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(HomeController)
class AdminControllerSpec extends Specification {
     void testabout() {
        when:
        controller.about()
        then:
        view == "/home/about"
    }
}

