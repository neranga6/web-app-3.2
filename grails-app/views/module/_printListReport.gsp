
<%@ page import="com.web.app.Module" %>
<%@ page import="com.web.app.StatusType" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="none"/>
		<asset:stylesheet src="modules.css"/>
		<g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
		<asset:stylesheet src='oys.css' media="print, screen" type="text/css"/>
		<asset:stylesheet src='printModule.css' media="print, screen" type="text/css"/>
		<link rel="shortcut icon" href="${createLinkTo(dir:'images',file:"favicon.ico")}" type="image/x-icon" />
		<title><g:message code="default.list.label" default="Print List" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-module" class="content scaffold-list"  role="main">
			<h2>List of Modules Report</h2>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table id="filterTable" width="100%">
			<g:form controller="module" action="listReport" class="rightNWImage">
				<tr>
					<td id="tdFilter" align="left">
				            <g:select name="statusType" id="ddlSearchType" from="${StatusType.values()}" keys="${StatusType.values()}" value="${params.statusType}" />
	                        
	                         <g:select name="sectionSelection" id="ddlSearchType" from="${sectionNameList}" keys="${sectionNameList}" value="${params.sectionSelection}" />
	                          
	                          <input name="criteria" type="text" id="moduleContentSearch" value="${params.criteria}"/>

								<g:if test="${flash.error}">
								<div class="message">${flash.error}</div>
							</g:if>
						
							</td>
							</tr>
                        </g:form>
                </table>
                <table>
				<thead>
					<tr>
					
						
						<th><g:message code="module.content.label" default="Module #" /></th>
						
						
						<th><g:message code="module.status.label" default="Status" /></th>
						
						<th><g:message code="module.section.label" default="Section" /></th>
						
						
						<th><g:message code="module.content.label" default="Content" /></th>
						
						
						<th><g:message code="module.lastUpdated.label" default="Last Updated" /></th>				
						
						
						<th><g:message code="module.lastModBy.label" default="Last Mod By" /></th>
						
						<th><g:message code="module.businessRules.label" default="Business Rules" /></th>
						
						<th><g:message code="module.comments.label" default="Comments" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${moduleInstanceList}" status="i" var="moduleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						
						<td>${moduleInstance.id}</td>

						<td>${moduleInstance.status}</td>

					
						<td>${fieldValue(bean: moduleInstance, field: "section.sectionName")}</td>
						
						<td>${raw(moduleInstance.content)}</td>
					
						<td><g:formatDate date="${moduleInstance.lastUpdated}" /></td>
						
						<td>${fieldValue(bean: moduleInstance, field: "lastModBy")}</td>
						
						<td><g:if test="${moduleInstance.businessRules}">
								<ul>
									<g:each var="businessRule" in="${moduleInstance.businessRules}">
										<li><g:fieldValue bean="${businessRule}" field="rule" /></li>
									</g:each>
								</ul>
							</g:if></td>
						<td><g:if test="${moduleInstance.comments}">
								<ul>
									<g:each var="comment" in="${moduleInstance.comments}">
										<li><g:fieldValue bean="${comment}" field="comment" /></li>
									</g:each>
								</ul>
							</g:if></td>
					</tr>
				</g:each>
				</tbody>
				
			</table>
			<div class="pagination">
				<g:paginate total="${moduleInstanceTotal}" params="${params}" />
			</div>
		</div>
	</body>
</html>
