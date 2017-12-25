<%@ page import="com.web.app.LetterTemplate" %>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'name', 'error')} required form-field">
	<label for="name">
		<g:message code="letterTemplate.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField class="letterNameField" name="name" required="" value="${letterTemplateInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'description', 'error')} form-field">
	<label for="description">
		<g:message code="letterTemplate.description.label" default="Description" />
		
	</label>
	<g:textArea class="richText" name="description" cols="40" rows="5" maxlength="500" value="${letterTemplateInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'status', 'error')} form-field">
	<label for="status">
		<g:message code="letterTemplate.status.label" default="Status" />
		
	</label>
	<g:select name="status" from="${LetterTemplate.STS_LIST}" value="${letterTemplateInstance?.status}" valueMessagePrefix="letterTemplate.status" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'category', 'error')} required form-field">
	<label for="category">
		<g:message code="letterTemplate.category.label" default="Category" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="category" name="category.id" from="${com.web.app.Category.list()}" optionKey="id" required="" value="${letterTemplateInstance?.category?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateInstance, field: 'model', 'error')} required form-field">
	<label for="model">
		<g:message code="letterTemplate.model.label" default="Model" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="model" name="model.id" from="${com.web.app.Model.list()}" optionKey="id" required="" value="${letterTemplateInstance?.model?.id}" class="many-to-one"/>
</div>
