<%@page defaultCodec="none" %>
<%@ page import="com.web.app.Module" %>
<%@ page import="com.web.app.StatusType" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="modules.css"/>
		<asset:javascript src="js/reorder.js"/>
		<link rel="shortcut icon" href="${createLinkTo(dir:'images',file:"favicon.ico")}" type="image/x-icon" />
		<g:set var="entityName" value="${message(code: 'moduleTemplate.label', default: 'Module')}" />
		<script>
			function open_win()
			{
				window.open("${createLink(action:'listReport', absolute: 'true', params:'[print:$print]')}${emailURLParms +'&print=true'}")
			}
		</script>
		<title><g:message code="default.list.label" default='List of Modules Report' args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-module" class="content scaffold-list" role="main">
			<h2>List of Modules Report</h2>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<g:form controller="module" action="listReport" class="rightNWImage">
			<tr>
					<th id="filterHeader" align="left">Status</th>
					<th id="filterHeader" align="left">Section</th>
					<th id="filterHeader" align="left">Content</th>
					
					
				</tr>
				<tr>
					<td id="tdFilter" align="left">
				            <g:select name="statusType" id="ddlSearchType" from="${StatusType.values()}" keys="${StatusType.values()}" value="${params.statusType}" />
				            </td>
	                        
	                     <td>    <g:select name="sectionSelection" id="ddlSearchType" from="${sectionNameList}" keys="${sectionNameList}" value="${params.sectionSelection}" /></td>
	                          
	                        <td>  <input name="criteria" type="text" id="moduleContentSearch" value="${params.criteria}"/>
	                        
	                        	<g:actionSubmit value="Filter" action="listReport"/>
	                        	</td>
	                        	<td></td>
	                        	<td>
	                 		<g:if test="${flash.error}">
								<div class="message">${flash.error}</div>
							</g:if>

							<input value="Print Report"onclick="open_win()"	type="button">
							
							<input value="Email Report"onclick="location.href='mailto:?Subject=Modules Report&body=${createLink(action: 'listReport', absolute: 'true')}${emailURLParms.encodeAsURL()}'"
							type="button">
							</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							</tr>
                        </g:form>
                </table>
			    <table id='modulTable'>
				<thead>
					<tr>
						<g:sortableColumn property="id" title="${message(code:'module.module.label', default:'Module#')}" />
						<g:sortableColumn property="status" title="${message(code:'module.status.label', default:'Status')}" />
						<g:sortableColumn property="section" title="${message(code:'module.section.label', default:'Section')}" />
						<th><g:message code="module.status.label" default="Content" /></th>
						<g:sortableColumn property="lastUpdated" title="${message(code:'module.lastUpdated.label', default:'Last Updated')}" />
						<g:sortableColumn property="lastModBy" title="${message(code:'module.lastModBy.label', default:'Last Mod By')}" />
						<th><g:message code='module.businessRules.label' default='Business Rules' /></th>
						<th><g:message code='module.comments.label' default='Comments' /></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${moduleInstanceList}" status="i" var="moduleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">


						<td><g:link class="listLink" action="edit" id="${moduleInstance.id}">${fieldValue(bean: moduleInstance, field: "id")}</g:link></td>

						<td>${moduleInstance.status}</td>

					
						<td>${fieldValue(bean: moduleInstance, field: "section.sectionName")}</td>
						
						<td>${raw(moduleInstance.content)}</td>
					
						<td><g:formatDate date="${moduleInstance.lastUpdated}" /></td>
						
						<td>${fieldValue(bean: moduleInstance, field: "lastModBy")}</td>
						
						<td><g:if test="${moduleInstance.businessRules}">
								<ul>
									<g:each var="businessRule" in="${moduleInstance.businessRules}">
										<li><g:fieldValue bean="${businessRule}" field="rule" /></li>
									</g:each>
								</ul>
							</g:if></td>
						<td><g:if test="${moduleInstance.comments}">
								<ul>
									<g:each var="comment" in="${moduleInstance.newComments}">
										<li><g:fieldValue bean="${comment}" field="comment" /></li>
									</g:each>
								</ul>
							</g:if></td>
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
