<%@ page import="com.web.app.LetterTemplateComment" %>
<div class="form-field">
	<label>
		<g:message code="letterTemplateComment.letterTemplate.id.label" />
	</label>
	<g:fieldValue bean="${letterTemplateCommentInstance}" field="letterTemplate.id"/>
</div>

<div class="form-field">
	<label>
		<g:message code="letterTemplate.label" />
	</label>
	<g:fieldValue bean="${letterTemplateCommentInstance}" field="letterTemplate"/>
</div>

<div class="fieldcontain ${hasErrors(bean: letterTemplateCommentInstance, field: 'comment', 'error')} required form-field">
	<label for="comment">
		<g:message code="letterTemplateComment.comment.label" default="Comment" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="comment" cols="40" rows="5" maxlength="500" required="" value="${letterTemplateCommentInstance?.comment}"/>
</div>
