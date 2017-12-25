package com.web.app

//import grails.plugin.springsecurity.SpringSecurityService

class HomeController {
    static defaultAction='home'
    //SpringSecurityService springSecurityService


    def home() {
        render(view: "home")
//        User currentUser = springSecurityService.getCurrentUser()
//        if (currentUser) {
//            if (currentUser.firstName != session.getAttribute("firstName")) {
//                session.setAttribute('firstName', currentUser.firstName)
//                render(view: "home")
//            }
//        } else {
//            redirect(controller: 'login', action: 'index')
//        }
    }

    def about() {
        render(view: "about")
    }
}
