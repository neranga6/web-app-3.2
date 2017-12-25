<!doctype html>
<html>
	<head>
		<title>Grails Runtime Exception</title>
		
		<asset:stylesheet rel="stylesheet" src="errors.css"  type="text/css"/>
	</head>
	<body>
		<g:renderException exception="${exception}" />
		<g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}" />
	</body>
</html>