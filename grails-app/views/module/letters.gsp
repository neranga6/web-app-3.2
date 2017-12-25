<%@ page import="com.web.app.Comment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="none" />
		<asset:stylesheet src="oys.css" type="text/css"/>
		<title>Letter List for Module</title>
	</head>
	<body>
	
<g:if test="${moduleInstance.id != null}">
This modules is used in <g:fieldValue bean="${moduleInstance}" field="letterCount"/> letters.

<div class="fieldcontain ${hasErrors(bean: moduleInstance, field: 'ingredients', 'error')} ">
	
<ul class="one-to-many">
<g:each in="${moduleInstance?.uniqueLetters?}" var="letterTemplateInstance">
    <li><g:link target="_parent" controller="letterTemplate" action="editStructure" id="${letterTemplateInstance.id}">${letterTemplateInstance.id}: ${letterTemplateInstance.name}</g:link></li>
</g:each>
</ul>

</div>
</g:if>
	</body>
</html>