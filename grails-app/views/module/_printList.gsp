
<%@ page import="com.web.app.Module" %>
<%@ page import="com.web.app.StatusType" %>
<%@ page import="com.web.app.UsageFilterType" %>
<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<script disposition="head">var CONTEXT_ROOT = "${request.contextPath}";</script>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<asset:stylesheet src='oys.css' media="print, screen" type="text/css"/>
		<asset:stylesheet src='printModule.css' media="print, screen" type="text/css"/>
		<link rel="shortcut icon" href="${createLinkTo(dir:'images',file:"favicon.ico")}" type="image/x-icon" />
		<g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
		<title><g:message code="default.list.label" default="Print List" args="[entityName]" /></title>


	</head>
	<body>
		<div id="list-module" class="content scaffold-list" role="main">
			<h2><g:message code="default.list.label" default="Module List" args="[entityName]" /></h2>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table id="filterTable" width="100%">	
				<g:form controller="module" action="list" class="rightNWImage">
				<tr>
					<td id="tdFilter" align="left">
				            <g:select name="statusType" id="ddlSearchType" from="${StatusType.values()}" keys="${StatusType.values()}" value="${params.statusType}" />
	                        
	                        <g:select name="usageFilterType" id="ddlSearchType" from="${UsageFilterType.textValues()}" keys="${UsageFilterType.values()}" value="${params.usageFilterType}" />
	                        
	                        <g:textField name="usageCount" id="usageCountType" value="${usedCount}" />
	                        
	                        <input name="criteria" type="text" id="moduleContentSearch" value="${params.criteria}"/>

						  <a href="#" onclick="window.print();return false;"><input value="Print" type="button"></a>
	                        <g:if test="${flash.error}">
								<div class="message">${flash.error}</div>
							</g:if>
					</td>
					
                </tr>		
             </g:form>
             </table>
             <table>
      			<thead>
					<tr>
					
						<th><g:message code="module.status.label" default="Status" /></th>
						
						<th><g:message code="module.id.label" default="ID" /></th>
												
						<th><g:message code="module.section.label" default="Section" /></th>
												
						<th><g:message code="module.status.label" default="Content" /></th>
					
						<th><g:message code="module.usageCount.label" default="Usage Count" /></th>
						
						<th><g:message code="module.usedBy.label" default="Used In" /></th>
											
						<th><g:message code="module.createdBy.label" default="Created By" /></th>
					
						<th><g:message code="module.dateCreated.label" default="Date Created" /></th>
					
						<th><g:message code="module.lastModBy.label" default="Last Mod By" /></th>
					
						<th><g:message code="module.lastUpdated.label" default="Last Updated" /></th>
						
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${moduleInstanceList}" status="i" var="moduleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
					<td>${moduleInstance.status}</td>
					
					<td>${fieldValue(bean: moduleInstance, field: "id")}</td>
					
						<td>${fieldValue(bean: moduleInstance, field: "section.sectionName")}</td>
						
						<td>${raw(moduleInstance.content)}</td>
						
						<td>${moduleInstance.ingredients.size()}</td>
					
						<td>${moduleInstance.ingredients.collect{it.letter.id}}</td>
					
						<td>${fieldValue(bean: moduleInstance, field: "createdBy")}</td>
					
						<td><g:formatDate date="${moduleInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: moduleInstance, field: "lastModBy")}</td>
					
						<td><g:formatDate date="${moduleInstance.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${moduleInstanceTotal}" params="${params}" />
			</div>
		</div>
	</body>
</html>
