<%@ page import="com.web.app.Section" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="userManagement.css"/>
		<g:set var="entityName" value="${message(code: 'businessUnit.label', default: 'Business Unit')}" />
		<title><g:message code="default.list.label" default= 'Business Unit' args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<g:link controller="businessUnit" action="create" class="button">Add Business Unit</g:link>
		<div id="list-businessUnit" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" default= 'Business Unit List' args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
		   <table id='userTable'>
				<thead>
					<tr>
						<th>&nbsp;</th>
						
						<g:sortableColumn property="name" title="${message(code: 'businessUnit.name.label', default: 'Business Unit')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${businessUnitInstanceList}" status="i" var="businessUnitInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>
							<g:form>
								<fieldset class="buttons">
									<g:hiddenField name="id" value="${businessUnitInstance?.id}" />
									<g:link class="edit" action="edit" id="${businessUnitInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
									<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
								</fieldset>
							</g:form>
						</td>
						
						<td>${fieldValue(bean: businessUnitInstance, field: "name")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${businessUnitInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
