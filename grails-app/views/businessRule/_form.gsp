<%@ page import="com.web.app.BusinessRule" %>
<div class="form-field">
    <label>
        <g:message code="businessRule.module.id.label"/>
    </label>
    <g:fieldValue bean="${businessRuleInstance}" field="module.id"/>
</div>

<div class="fieldcontain ${hasErrors(bean: businessRuleInstance, field: 'rule', 'error')} required form-field">
    <label for="rule">
        <g:message code="businessRule.rule.label" default="Rule"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textArea name="rule" cols="40" rows="5" maxlength="500" required="" value="${businessRuleInstance?.rule}"/>
</div>
	
