<%@page defaultCodec="none" %>
<%@ page import="com.web.app.LetterTemplate" %>
<%@ page import="com.web.app.StatusType" %>
<!doctype html>
<html>

	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="modules.css"/>
		<asset:javascript src="js/reorder.js"/>
		<asset:javascript src="fp.js"/>
		<asset:stylesheet src="fp.css"/>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'Template')}" />
		<script>
			function open_win()
			{
				window.open("${createLink(action: 'listReport', absolute: 'true', params:'[print:$print]')}${emailURLParms +'&print=true'}")
			}
		</script>
		<title><g:message code="default.list.label" default='List of Templates' args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-letterTemplate" class="content scaffold-list" role="main">
			<h2>List of Templates</h2>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<table width="75%">
					<g:form controller="letterTemplate" action="listReport" class="rightNWImage">
					<tr>
						<th id="filterHeader" align="left">Status</th>
						<th id="filterHeader" align="left">Category</th>
					
					</tr>
					<tr>
						<td id="tdFilter" align="left">
	                        <g:select name="statusType" id="ddlSearchType" from="${StatusType.values()}" keys="${StatusType.values()}" value="${params.statusType}" />
	                    </td>
	                    <td id="tdFilter">
	                        <g:select name="categorySelection" id="ddlSearchType" from="${categoryNameList}" keys="${categoryNameList}" value="${params.categorySelection}" />
							                        
	                        <g:actionSubmit value="Filter" action="listReport"/>
	                    </td>
	                    <td id="tdFilter">    
							<g:if test="${flash.error}">
							<div class="message">${flash.error}</div>
							</g:if>
							
							
							<input value="Print Report"onclick="open_win()"	type="button">
							
							<input value="Email Report"onclick="location.href='mailto:?Subject=Letter Templates Report&body=${createLink(action: 'listReport', absolute: 'true')}${emailURLParms.encodeAsURL()}'"
						type="button">
				
						</td>
					
					</tr>	
                        </g:form>
				</table>
			  <table id='modulTable'>
				<thead>
					<tr>

						<g:sortableColumn property="id" title="Template#" params="[statusType: statusType ]" />

						<g:sortableColumn property="status" title="Status" params="[statusType: statusType ]"/>

					    <g:sortableColumn property="category"  title="${message(code: "letterTemplate.category.label" , default:'Category')}" />

						<g:sortableColumn property="name" title="${message(code: 'letterTemplate.name.label' , default:'Name')}" />

						<g:sortableColumn property="description" title="${message(code: 'letterTemplate.description.label',default:'Description (Audience & Intent)' )}" params="[statusType: statusType ]"/>

						<g:sortableColumn property="lastUpdated" title="${message(code: 'default.lastUpdated.label',default:'Date Modified')}" params="[statusType: statusType ]"/>

						<th data-sort="Last Modified By">Last Modified By</th>

					</tr>
				</thead>
				<tbody>
				<g:each in="${letterTemplateInstanceList}" status="i" var="letterTemplateInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "id")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: letterTemplateInstance, field: "category.name")}</td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "name")}</td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "description")}</td>
						
						<td><g:formatDate date="${letterTemplateInstance.lastUpdated}" /></td>
						
						<td>${fieldValue(bean: letterTemplateInstance, field: "lastModBy")}</td>
					
						
					</tr>
				</g:each>
				
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${letterTemplateInstanceTotal}" params="${params}" />
			</div>
		</div>
	</body>
</html>
