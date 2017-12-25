
<%@ page import="com.web.app.LetterTemplate" %>
<!doctype html>

<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="modules.css"/>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'Template')}" />
		<title><g:message code="default.list.label" default= 'Template List' args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-letterTemplate" class="content scaffold-list" role="main">
			<h2><g:message code="default.list.label" default= 'Template List' args="[entityName]" /></h2>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table id='modulTable'>
				<thead>
					<tr>
						<sec:ifNotGranted roles="ROLE_WRITER">
							<th colspan="2"></th>
						</sec:ifNotGranted>
						<sec:ifAnyGranted roles="ROLE_WRITER">
							<th colspan="4"></th>
						</sec:ifAnyGranted>
						
						<g:sortableColumn property="id" title="${message(code: 'letterTemplate.id.label', default:'ID')}" />
						
						<g:sortableColumn property="name" title="${message(code: 'letterTemplate.name.label' , default:'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'letterTemplate.description.label',default:'Description (Audience & Intent)')}" />
					
						<g:sortableColumn property="category.name" title="${message(code: 'letterTemplate.category.label', default:'Category')}" />
						
						<th><g:message code="lastModBy.label" default='Last Modified By' /></th>
					
						<g:sortableColumn property="lastUpdated" title="${message(code:'default.lastUpdated.label',default:'Date Modified' )}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${letterTemplateInstanceList}" status="i" var="letterTemplateInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					

						<td><g:link class="listLink" action="preview" id="${letterTemplateInstance?.id}">Preview</g:link></td>
						
						
						<sec:ifAnyGranted roles="ROLE_WRITER">
							<td><g:link class="listLink" action="review" id="${letterTemplateInstance?.id}"><g:message code="letterTemplate.review.link" default="Review & Edit Content" /></g:link></td>
							<td><g:link class="listLink" action="editStructure" id="${letterTemplateInstance?.id}"><g:message code="letterTemplate.structure.link" default="Edit Structure" /></g:link></td>
						</sec:ifAnyGranted>
						
						<td><g:link class="listLink" action="report" id="${letterTemplateInstance?.id}"><g:message code="letterTemplate.report.link" default="IT Report" /></g:link></td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "id")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "category.name")}</td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "lastModBy")}</td>
					
						<td><g:formatDate date="${letterTemplateInstance.lastUpdated}" /></td>
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
