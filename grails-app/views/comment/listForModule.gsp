<%@ page import="com.web.app.Comment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="none" />
		<asset:stylesheet src="oys.css"/>
		<title>Comment List for Module</title>
	</head>
	<body>
	<div class="iframeLineBreak">
		<g:each in="${commentInstanceList}" status="i" var="commentInstance">
			Added: <g:formatDate date="${commentInstance.dateCreated}" /><br/>
			${fieldValue(bean: commentInstance, field: "comment")}<br/>
			<br/>
		</g:each>
	</div>
	</body>
</html>