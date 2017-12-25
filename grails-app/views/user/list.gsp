
<%@ page import="com.web.app.User" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="userManagement.css"/>
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.list.label" default='User' args="[entityName]" /></title>
	</head>
	<body>
		<g:render template="/admin/adminbar" />
		<g:link controller="user" action="create" class="button">Add User</g:link>
		<div id="list-user" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" default='User List' args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:if test="${flash.warning}">
				<div class="message_error" style="color: red;">${flash.warning}</div>
			</g:if>
			 <table id='modulTable'>
				<thead>
					<tr>
						<th>&nbsp;</th>
						<g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'User Name')}" />
					
						<g:sortableColumn property="firstName" title="${message(code: 'user.firstName.label', default: 'First Name')}" />
					
						<g:sortableColumn property="lastName" title="${message(code: 'user.lastName.label', default: 'Last Name')}" />
						
						<th><g:message code="user.role.label" default="Role" /></th>
									
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${userInstanceList}" status="i" var="userInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>
							<g:form>
								<fieldset class="buttons">
									<g:hiddenField name="id" value="${userInstance?.id}" />
									<g:link class="edit" action="edit" id="${userInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
								
								</fieldset>
							</g:form>
						</td>
						
						<td>${fieldValue(bean: userInstance, field: "username")}</td>
					
						<td>${fieldValue(bean: userInstance, field: "firstName")}</td>
					
						<td>${fieldValue(bean: userInstance, field: "lastName")}</td>
						
						<td><g:if test="${userInstance.userRole=='USER'}">READ ONLY</g:if><g:else>${fieldValue(bean: userInstance, field: "userRole")}</g:else>
						</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${userInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
