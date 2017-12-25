<%@ page import="com.web.app.Module" %>
<html>

<script type="text/javascript">
    window.tinymce = { suffix: "", base: "/tinymce" }
</script>

<head>
	<meta name="layout" content="oyslayout"/>
	<style>.hidden{display:none;}</style>
	<asset:stylesheet src="modules.css"/>
	<asset:javascript src="js/jquery-1.11.1.min.js"/>
	<asset:javascript src="js/jquery-ui-1.10.4.custom.min.js"/>
	<asset:stylesheet src="jquery-ui-1.10.4.custom.css"/>
	<asset:javascript src="tinymce/tinymce.min.js"/>
	<asset:javascript src="richtextEditor.js"/>
	<asset:javascript src="js/browserWarning.js"/>
	<asset:javascript src="sweetalert/dist/sweetalert-dev.js"/>
	<asset:stylesheet src="sweetalert/dist/sweetalert.css"/>
	<asset:stylesheet src="sweetalert/themes/twitter/twitter.css"/>
	<asset:stylesheet src="oys.css"/>
	<g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
	<title><g:message code="default.edit.label" default="Edit Module" args="[entityName]" /></title>
</head>
<body>
<div id="edit-module" class="content scaffold-edit" role="main">
	<script type="text/javascript">
        browserWarning({});
	</script>
	<br/>
	<h2><g:message code="default.edit.label" default="Edit Module" args="[entityName]" /> #<g:fieldValue bean="${moduleInstance}" field="id"/></h2>
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
<div>
	<g:form method="post" >
		<sec:ifAnyGranted roles="ROLE_WRITER">
			<fieldset class="buttons">
				<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Save Module')}" />
				<g:actionSubmit class="copy" action="copy" value="${message(code: 'default.button.copy.label', default: 'Copy Module')}" />
			</fieldset>
		</sec:ifAnyGranted>
		<g:hiddenField name="id" value="${moduleInstance?.id}" />
		<g:hiddenField name="version" value="${moduleInstance?.version}" />
		<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'content', 'error')} required">
		</div>
		<div>
			<fieldset class="form">
				<g:render template="form"/>
		</div>
		</div>
    <div>
		<sec:ifAnyGranted roles="ROLE_WRITER,">
			<fieldset class="buttons">
			<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Save Module')}" />
			<g:actionSubmit class="copy" action="copy" value="${message(code: 'default.button.copy.label', default: 'Copy Module')}" />
			<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete Module')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
			</div>
		</sec:ifAnyGranted>
	</g:form>
	<div>
		<sec:ifAnyGranted roles="ROLE_WRITER">
			<input name="image" type="file" id="upload" class="hidden" onchange="">
		</sec:ifAnyGranted>
	</div>
	<div id="imageUploadPanel">
		<sec:ifAnyGranted roles="ROLE_WRITER">
			<h3>Image Upload</h3>
			<g:uploadForm action="upload" controller="image" maxNumberOfFiles="1" forceIframeTransport="true" acceptFileTypes="/(\\.|\\/)(gif|jpe?g|png|bmp)\$/i">
				<input type="file" name="myFile" />
				<input type="submit"  />
			</g:uploadForm>
		</sec:ifAnyGranted>
	</div>
</body>
</html>
