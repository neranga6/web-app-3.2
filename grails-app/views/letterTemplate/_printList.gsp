<%@ page import="com.web.app.LetterTemplate" %>
<%@ page import="com.web.app.StatusType" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="none"/>
		<asset:stylesheet src='oys.css' media="print, screen" type="text/css"/>
		<asset:stylesheet src='printModule.css' media="print, screen" type="text/css"/>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'Template')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-letterTemplate" class="content scaffold-list" role="main">
			<h2>List of Templates</h2>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<table>
					<g:form controller="letterTemplate" action="listReport" class="rightNWImage">
	                        <g:select name="statusType" id="ddlSearchType" from="${StatusType.values()}" keys="${StatusType.values()}" value="${params.statusType}" />
	                        <g:select name="categorySelection" id="ddlSearchType" from="${categoryNameList}" keys="${categoryNameList}" value="${params.categorySelection}" />
							<g:if test="${flash.error}">
							<div class="message">${flash.error}</div>
							</g:if>
							
							
							
                        </g:form>
			
				<thead>
					<tr>
												
						<th>Template #</th>
						
						<th>Status</th>
						
						<th><g:message code="letterTemplate.category.label"/> </th>
						
						<th><g:message code="letterTemplate.name.label"/> </th>
					
						<th><g:message code="letterTemplate.description.label"/></th>
					
						<th><g:message code="default.lastUpdated.label"/></th>
						
						<th><g:message code="default.lastModBy.label" /></th>
					
						
					</tr>
				</thead>
				<tbody>
				<g:each in="${letterTemplateInstanceList}" status="i" var="letterTemplateInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "id")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "category.name")}</td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "name")}</td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "description")}</td>
						
						<td><g:formatDate date="${letterTemplateInstance.lastUpdated}" /></td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "lastModBy")}</td>
					
						
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${letterTemplateInstanceTotal}" params="${params}" />
			</div>
		</div>
	</body>
</html>
