<%@ page import="com.web.app.Module" %>
<div class="group">

	<div class="richTextPanel">

		<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'section', 'error')} required form-field">
			<label for="section">
				<g:message code="module.section.label" default="Section Selected" />
				<span class="required-indicator">*</span>
			</label>
			<g:select id="section" name="section.id" from="${com.web.app.Section.list(sort: 'sectionName')}" noSelection="['':'-- select section --']" optionKey="id" required="" value="${moduleInstance?.section?.id}" class="many-to-one"/>
		</div>

		<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'status', 'error')} form-field">
			<label for="status">
				<g:message code="module.status.label" default="Module Status" />
			</label>
			<g:if test="${moduleInstance.statusEnabled}" >
				<g:select name="status" from="${moduleInstance.statusValues}" value="${moduleInstance?.status}" valueMessagePrefix="module.status" />
			</g:if>
			<g:else>
				<g:fieldValue bean="${moduleInstance}" field="status"/>
			</g:else>
		</div>

		<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'businessUnit', 'error')} form-field">
			<label for="businessUnit">
				<g:message code="module.businessUnit.label" default="Business Unit" />
			</label>
			<g:select id="businessUnit" name="businessUnit.id" from="${com.web.app.BusinessUnit.list(sort: 'name')}" noSelection="['':'-- select Business Unit --']" optionKey="id" required="" value="${moduleInstance?.businessUnit?.id}" class="many-to-one"/>
		</div>
		<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'content', 'error')} required">
			<label>
				<g:message code="module.content.label" default="Module Content" />
				<span class="required-indicator">*</span>
			</label>
			<g:textArea name="content" class="mceEditor" cols="40" rows="5" maxlength="8000" value="${raw(moduleInstance?.content)}"/>
		</div>

		<g:if test="${moduleInstance.id != null}">
			<h3>Letters</h3>
			<iframe class="moduleUsedInLettersList" src='<g:createLink controller="module" action="letters" id="${moduleInstance.id}" absolute="true"/>' frameborder="0" id="lettersListIFrame"></iframe>
		</g:if>
	</div>


	<div class="commentBusinessRulePanel">
		<sec:ifAnyGranted roles="ROLE_WRITER">
			<div>
				<h3>Add a Comment</h3>
				<hr/>
				<textarea name="comment" id="comment" rows="5" cols="30"></textarea><br/>
				<br/><br/>
			</div>
		</sec:ifAnyGranted>

		<g:if test="${moduleInstance.id != null}">
			<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'comments', 'error')} ">
				<h3><g:message code="module.comments.label" default="View Comments" /></h3>
				<hr/>
				<iframe width="100%" src='<g:createLink controller="comment" action="listForModule" id="${moduleInstance.id}" absolute="true"/>' frameborder="0"></iframe>
			</div>
		</g:if>
		<sec:ifAnyGranted roles="ROLE_WRITER">
			<div>
				<h3>Add a Business Rule</h3>
				<hr/>
				<textarea name="rule" id="rule" rows="5" cols="30"></textarea><br/>
				<br/><br/>
			</div>
		</sec:ifAnyGranted>
		<g:if test="${moduleInstance.id != null}">
			<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'businessRules', 'error')} ">
				<h3><g:message code="module.businessRules.label" default="View Business Rules" /></h3>
				<hr/>
				<iframe width="100%" src='<g:createLink controller="businessRule" action="listForModule" id="${moduleInstance.id}" absolute="true"/>' frameborder="0"></iframe>
			</div>
		</g:if>

	</div>
</div>
