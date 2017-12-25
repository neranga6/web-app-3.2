<%@ page import="com.web.app.Comment" %>

<div class="form-field">
	<label>
		<g:message code="comment.module.id.label" />
	</label>
	<g:fieldValue bean="${commentInstance}" field="module.id"/>
	<g:hiddenField name="module.id" value="${fieldValue(bean: commentInstance, field: 'module.id')}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: commentInstance, field: 'comment', 'error')} required form-field">
	<label for="comment">
		<g:message code="comment.comment.label" default="Comment" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="comment" cols="40" rows="5" maxlength="500" required="" value="${commentInstance?.comment}"/>
</div>
