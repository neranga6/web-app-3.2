<%@ page import="com.web.app.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="oys.css" />
		<title>List for Letter Comments</title>
	</head>
	<body>
	<table>
		<g:each in="${commentInstanceList}" status="i" var="commentInstance">
		<tr>
			<td>Added: <g:formatDate date="${commentInstance.dateCreated}" /></td>
		</tr>
		<tr>
			<td>${fieldValue(bean: commentInstance, field: "comment")}</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		</g:each>
	</table>
	</body>
</html>