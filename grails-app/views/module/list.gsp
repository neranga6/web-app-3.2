<%@page defaultCodec="none" %>
<%@ page import="com.web.app.Module" %>
<%@ page import="com.web.app.StatusType" %>
<%@ page import="com.web.app.UsageFilterType" %>
<!doctype html>
<html>
	<head>
	<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="modules.css"/>
		<asset:stylesheet src="oys.css"/>
		<link rel="shortcut icon" href="${createLinkTo(dir:'images',file:"favicon.ico")}" type="image/x-icon" />
		<g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
		<script>
			function open_win()
			{
				window.open("${createLink( action: 'list', absolute: 'true',params:'[print:$print]')}${emailURLParms +'&print=true'}")
			}

            function goToPage(numberofResults){
			         window.location.href="${createLink(controller:'module' ,action:'list' ,params:[numOfResultsSelect:""])}"+ numberofResults;
            }

		</script>
		<g:message title="${message(code:'list.label', default: 'Module List')}"/>
	</head>
	<body>
		<div id="list-module" class="content scaffold-list" role="main">
			<h2><g:message code="default.list.label" default='Module List' args="[entityName]" /></h2>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table id="filterTable" width="100%">	
				<g:form controller="module" action="list" class="rightNWImage">
				<tr>
					<th id="filterHeader" align="left">Status</th>
					<th id="filterHeader" align="left">Usage Amount</th>
					<th id="filterHeader" align="left"></th>
					<th id="filterHeader" align="left">Content</th>
					
				</tr>
				   <tr>
					<td id="tdFilter" align="left">	<g:select name="statusType" id="ddlSearchType" from="${StatusType.values()}" keys="${StatusType.values()}" value="${params.statusType}" /></td>
	                	<td id="tdFilter">
	                	<g:select name="usageFilterType" id="ddlUsageFilter" from="${UsageFilterType.textValues()}" keys="${UsageFilterType.values()}" value="${params.usageFilterType}" />
	                	</td>
	                	<td id="usageCountBox">
	                	<g:textField name="usageCount" id="usageCountType" value="${usedCount}" />
	                	</td>
	                	<td id="tdFilter"><input name="criteria" type="text" id="moduleContentSearch" value="${params.criteria}"/>
	                        <g:actionSubmit value="Filter" action="list"/>
	                        <g:if test="${flash.error}">
								<div class="message">${flash.error}</div>
							</g:if>
						</td>
					  <td>

						  <input id="print" value="Print Report" onclick="open_win();" type="button">
						<input value="Email Report"onclick="location.href='mailto:?Subject=Modules Report&body=${createLink(action: 'list', absolute: 'true')}${emailURLParms.encodeAsURL()}'"
						type="button">
					<td>
					<td id="tdFilter" align="right">
						Results to Show per Page:
					</td>
					<td id="tdFilter" align="right" >
						<g:select id="numOfResultsDropDown" onchange="goToPage((this.value));" name="numOfResultsSelect" from="${['10', '25', '50', 'All'] }" keys="${[10,25, 50, 1000]}" value="${numOfResults2Show}"/>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
             </g:form>
             </table>
			<table class='modulTable'>
					<tr>

						<g:sortableColumn property="status" title="${message(code: 'module.status.label', default: 'Status')}" />

						<g:sortableColumn property="id" title="${message(code: 'module.content.label', default: 'ID')}" />

						<g:sortableColumn property="section.sectionName" title="${message(code: 'module.content.label', default: 'Section')}" />

						<th><g:message code="module.status.label" default="Content" /></th>

						<th><g:message code="module.usageCount.label" default="Usage Count" /></th>

						<th><g:message code="module.businessUnit.label" default="Business Unit" /></th>

						<th><g:message code="module.usedBy.label" default="Used In" /></th>

						<th><g:message code="module.createdBy.label" default="Created By" /></th>

						<g:sortableColumn property="dateCreated" title="${message(code: 'module.dateCreated.label', default: 'Date Created')}" />

						<th><g:message code="module.lastModBy.label" default="Last Mod By" /></th>

						<g:sortableColumn property="lastUpdated" title="${message(code: 'module.lastUpdated.label', default: 'Last Updated')}" />

					</tr>

				<tbody>
				<g:each in="${moduleInstanceList}" status="i" var="moduleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					<td>${moduleInstance.status}</td>
					
					<sec:ifAnyGranted roles="ROLE_WRITER">
						<td><g:link class="listLink" action="edit" id="${moduleInstance.id}">${fieldValue(bean: moduleInstance, field: "id")}</g:link></td>
					</sec:ifAnyGranted>
					<sec:ifNotGranted roles="ROLE_WRITER">
						<td>${fieldValue(bean: moduleInstance, field: "id")}</td>
					</sec:ifNotGranted>
						<td>${fieldValue(bean: moduleInstance, field: "section.sectionName")}</td>
						
						<td>${raw(moduleInstance.content)}</td>
						
						<td>${moduleInstance.ingredients.size()}</td>
						
						<td>${moduleInstance.businessUnit}</td>
					
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
