grails {
    profile = 'web'
    codegen {
        defaultPackage = 'writers_tool'
    }
}

info {
    app {
        name = '@info.app.name@'
        version = '@info.app.version@'
        grailsVersion = '@info.app.grailsVersion@'
    }
}

hibernate {
    cache.queries = false
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory'
}

spring {
    groovy {
        template['check-template-location'] = false
    }
}

grails {
    gorm {
        autowire = true
    }
}

grails {
    mime {
        disable {
            accept {
                header {
                    userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
                }
            }
        }

        types {
            all = '*/*'
            atom = 'application/atom+xml'
            css = 'text/css'
            csv = 'text/csv'
            form = 'application/x-www-form-urlencoded'
            html = ['text/html', 'application/xhtml+xml']
            js = 'text/javascript'
            json = ['application/json', 'text/json']
            multipartForm = 'multipart/form-data'
            rss = 'application/rss+xml'
            text = 'text/plain'
            hal = ['application/hal+json', 'application/hal+xml']
            xml = ['text/xml', 'application/xml']
        }
    }
}

urlmapping {
    cache {
        maxsize = 2000
    }
}

controllers {
    defaultScope = 'singleton'
}

converters {
    encoding = 'UTF-8'
}

// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

grails.plugin.springsecurity.successHandler.alwaysUseDefault = true
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/'
grails.plugin.springsecurity.userLookup.userDomainClassName = 'User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'UserRole'
grails.plugin.springsecurity.authority.className = 'Role'
grails.plugin.springsecurity.roleHierarchy = ''' ROLE_ADMIN > ROLE_REVIEWER
                                                 ROLE_REVIEWER > ROLE_WRITER
                                                 ROLE_WRITER > ROLE_USER
                                             '''

grails.plugin.springsecurity.providerNames = ['preAuthenticatedAuthenticationProvider', 'anonymousAuthenticationProvider']

environments {

    localOracleDev {
        grails.logging.jul.usebridge = true

    }
    development {
        grails.logging.jul.usebridge = true

    }
    test {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

dataSource {
    dbCreate = ""
    dialect = "org.hibernate.dialect.Oracle10gDialect"
    pooled = true
    autoReconnect = true
    jmxExport = true
    driverClassName = "oracle.jdbc.driver.OracleDriver"
    properties {
        maxActive = -1
        minEvictableIdleTimeMillis = 1800000
        timeBetweenEvictionRunsMillis = 1800000
        numTestsPerEvictionRun = 3
        testOnBorrow = true
        testWhileIdle = true
        testOnReturn = true
        validationQuery = "SELECT 1 FROM DUAL"
    }
}

// environment specific settings
environments {

    development {

        dataSource {
            dbCreate = "create"// one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""


        }
    }
    localOracleDev {
        dataSource {
            dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:oracle:thin:@//shar05racx.nwie.net:1521/edm01x.nsc.net"
            driverClassName = "oracle.jdbc.driver.OracleDriver"
            username = "WritXmen"
            password = "R3dSun1"
            dialect = "org.hibernate.dialect.Oracle10gDialect"
        }

    }

    test {
        dataSource {
            dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:oracle:thin:@//shar05racx.nwie.net:1521/edm01x.nsc.net"
            driverClassName = "oracle.jdbc.driver.OracleDriver"
            username = "WritXmen"
            password = "R3dSun1"
            dialect = "org.hibernate.dialect.Oracle10gDialect"
        }
    }

    production {
        dataSource {
             //JNDI configuration
             dbCreate = ""
             jndiName = "jdbc/writersTool"
             dialect = "org.hibernate.dialect.Oracle10gDialect"
         //  direct acess to prod data base
//           dbCreate = "" // one of 'create', 'create-drop', 'update', 'validate', ''
//           url = "jdbc:oracle:thin:@//shar05racp.nwie.net:1521/edm01p.nsc.net"
//           driverClassName = "oracle.jdbc.driver.OracleDriver"
//           username = "WRITPROD"
//           password = "B3cXz1tg"
//           dialect = "org.hibernate.dialect.Oracle10gDialect"
        }
    }
}

grails.plugin.springsecurity.rejectIfNoRule = true
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.assets.less.compile = 'less4j'
grails.assets.plugin."twitter-bootstrap".excludes = ["**/*.less"]
grails.assets.plugin."twitter-bootstrap".includes = ["bootstrap.less"]

grails.plugin.springsecurity.interceptUrlMap = [
        '/js/**'    : ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/css/**'   : ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/assets/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/images/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/login/**' : ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/logout/**': ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/admin/**' : ['ROLE_REVIEWER', 'IS_AUTHENTICATED_FULLY'],
        '/**'       : ['ROLE_USER', 'IS_AUTHENTICATED_FULLY'],
        '/'         : ['permitAll']

]
grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/javascripts/**', filters: 'none'],
        [pattern: '/**/tinymce/**', filters: 'none']


]