<%@ page import="com.web.app.SearchType" %>
<!doctype html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title><g:layoutTitle default="OYS Home"/></title>
    <script type="text/javascript"  disposition="head">var CONTEXT_ROOT = "${request.contextPath}";</script>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <g:layoutHead/>
    <asset:stylesheet src="modules.css"/>
    <asset:stylesheet src="oys.css"/>
    <asset:javascript src="js/application.js"/>
    <asset:javascript src="js/createSelection.js"/>
    <asset:stylesheet src="listgrid.css"/>
     <asset:stylesheet src="jquery-ui-1.8.21.custom.css"/>
    <link rel="shortcut icon" href="${createLinkTo(dir:'images',file:"favicon.ico")}" type="image/x-icon" />
</head>
<body>

<div class="pageTools no-print">
    <div class="no-print">
        <asset:image src='small_client-letter-icon.gif' alt="OYS Document Management logo"/>
        <asset:image src='rsz_block_application_title.png' alt="OYS Document Management banner"/>
    </div>
    <g:set var="user" value="${session.'firstName'}"/>
    <br/>
    <div id="salutation" >
        Welcome, ${user}
    </div>
       <g:form controller="search" class="rightNWImage" >
        <input name="criteria" type="text" id="txtSearchText" value="${params.criteria}"/>
        <g:select name="searchType" id="ddlSearchType" from="${SearchType.values()*.description}" keys="${SearchType.values()}" value="${params.searchType}" />
        <g:actionSubmit value="${message(code:'default.button.search.label', default: 'Go')}" action="index" />
        <g:if test="${flash.error}">
            <div class="message">${flash.error}</div>
        </g:if>
    </g:form>
</div>
<div>
    <div id="tabs" class="tabs ui-tabs ui-corner-all titleAlign">
        <ul class="ui-tabs-nav ui-helper-clearfix ui-widget-header ui-corner-all">
            <li class="tabList"><g:link controller="home" action="index">Main</g:link></li>
            <li class="tabList"><g:link controller="module">Modules</g:link></li>
            <li class="tabList"><g:link controller="letterTemplate" action="list">Templates</g:link></li>
            <li class="tabList"><g:link controller="letterTemplate" action="listReport">Templates Report</g:link></li>
            <li class="tabList"><g:link controller="module" action="listReport">List of Modules Report</g:link></li>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <li class="tabList"><g:link controller="admin">Administration</g:link></li>
            </sec:ifAnyGranted>
        </ul>
        <div id="mainContent">
            <div id="mainWrapper">
                <div id="main">
                    <g:layoutBody/>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer">
&copy;2012 Nationwide Mutual Insurance Company. All rights reserved.
</div>
</body>
</html>
