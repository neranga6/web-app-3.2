//// Place your Spring DSL code here
beans = {

//   customEditorRegistrar(MyEditorRegistrar)
//
//    preAuthenticatedUserDetailsService(org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper) {
//        userDetailsService = ref('userService') }
//
//    preAuthenticatedAuthenticationProvider(org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider) {
//        preAuthenticatedUserDetailsService = ref('preAuthenticatedUserDetailsService')
//    }
//
//    requestHeaderAuthenticationFilter(org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter) {
//        principalRequestHeader = 'CT-REMOTE-USER'
//        authenticationManager = ref('authenticationManager')
//        exceptionIfHeaderMissing = true
//    }
//
//    cleartrustStubFilter(com.nationwide.security.web.authentication.preauth.stub.ClearTrustRequestHeaderStubFilter,
//            ['unknown':[:],
//             'writer':[
//                     'fullName':'Joe User',
//                     'lastname':'User',
//                     'email':'userj99@nationwide.com',
//                     'firstname':'Joe',
//                     'employeeNumber':'123456',
//                     'groupMembership':'cn=ROLE_USER,ou=LDAP,ou=groups,dc=nationwidedir,dc=pilot',
//                     'telephoneNumber':'5555555555',
//                     'ct-auth-type':'2048',
//                     'ct-web-svr-id':'B2E_J2EE_DEV'],
//             'reviewer':[
//                     'fullName':'Mary Manager',
//                     'lastname':'Manager',
//                     'email':'mgr9999@nationwide.com',
//                     'firstname':'Mary',
//                     'employeeNumber':'654321',
//                     'groupMembership':'cn=ROLE_USER,ou=LDAP,ou=groups,dc=nationwidedir,dc=pilot,cn=ROLE_SUPERVISOR,ou=LDAP,ou=groups,dc=nationwidedir,dc=pilot',
//                     'telephoneNumber':'5555555555',
//                     'ct-auth-type':'2048',
//                     'ct-web-svr-id':'B2E_J2EE_DEV']
//            ]) {
//    }
//
//    userService(grails.plugin.springsecurity.userdetails.GormUserDetailsService){
//        grailsApplication=ref('grailsApplication')
//    }
//
//    clearTrustEntryPoint(org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint) {bean ->
//                bean.constructorArgs   = [('/login/ssoDenied')]
//    }
//
//    exceptionTranslationFilter(org.springframework.security.web.access.ExceptionTranslationFilter) {bean ->
//        bean.constructorArgs = [ ref('clearTrustEntryPoint')]
//
//    }


}
