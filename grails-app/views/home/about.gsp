<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="userManagement.css"/>
	    <asset:stylesheet src="about.css"/>
		 </head>
	<body>
	<g:render template="/admin/adminbar" />
		<div id="status" role="complementary">
			%{--<h1>Build Information</h1>--}%
				%{--<span class="label">Environment: </span><span>${grails.util.Environment.current.name}<br></span>--}%
		<br>
			<h1>Build Information</h1>
		      <span class="label">Application Name : </span><span><g:meta name="info.app.name"/><br></span>
				<span class="label">Grails version: </span><span><g:meta name="info.app.grailsVersion"/><br></span>
				<span class="label">Groovy version: </span><span>${GroovySystem.getVersion()}<br></span>
				<span class="label">JVM version: </span><span>${System.getProperty('java.version')}<br></span>
				<span class="label">Reloading active: </span><span>${grails.util.Environment.reloadingAgentEnabled}<br></span>
				<br>
			<h1>Installed Plugins</h1>
			    <ul>
				<g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
					<li>${plugin.name} - ${plugin.version}</li>
				</g:each>
			</ul>
		</div>
	</body>
</html>
