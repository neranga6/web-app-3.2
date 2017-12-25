<%@ page import="com.edm.Module" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="oyslayout"/>
	<style>.hidden{display:none;}</style>
	<asset:stylesheet src="modules.css"/>
	<asset:javascript src="js/jquery-1.11.1.min.js"/>
	<asset:javascript src="js/jquery-ui-1.10.4.custom.min.js"/>
	<asset:stylesheet src="jquery-ui-1.10.4.custom.css"/>
	<asset:javascript src="tinymce/tinymce.min.js"/>
	<asset:javascript src="richtextEditor.js"/>
	<asset:stylesheet src="oys.css"/>
	<asset:javascript src="js/browserWarning.js"/>
	<asset:javascript src="sweetalert/dist/sweetalert-dev.js"/>
	<asset:stylesheet src="sweetalert/dist/sweetalert.css"/>
	<asset:stylesheet src="sweetalert/themes/twitter/twitter.css"/>
	<g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
	<title><g:message code="default.create.label" default="Create Module" args="[entityName]" /></title>
</head>
<body>
<div id="create-module" class="content scaffold-create" role="main">
	<script type="text/javascript">
        browserWarning({});
	</script>
	<h2><g:message code="default.create.label" default="Create Module" args="[entityName]" /></h2>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<g:hasErrors bean="${moduleInstance}">
		<ul class="errors" role="alert">
			<g:eachError bean="${moduleInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
			</g:eachError>
		</ul>
	</g:hasErrors>
	<g:form action="save" >
		<fieldset class="form">
			<g:render template="form"/>
		</fieldset>
		<fieldset class="buttons">
			<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
		</fieldset>
	</g:form>
</div>
<div>
	<sec:ifAnyGranted roles="ROLE_WRITER">
		<input name="image" type="file" id="upload" class="hidden" onchange="">
	</sec:ifAnyGranted>
</div>

</body>
</html>
