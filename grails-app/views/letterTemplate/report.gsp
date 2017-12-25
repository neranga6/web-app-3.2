<%@ page import="com.web.app.LetterTemplate"%>
<!doctype html>
<html>
<head>
<meta name="layout" content="oyslayout"/>
<g:set var="entityName"
	value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
<title><g:message code="default.create.label"
		args="[entityName]" /></title>
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'printItReport.css')}" media="print"
	type="text/css">
</head>
<body>
	<div id="printReport">
		<div id="reportHeader">
		<table id="reportHeaderTable">
			<tr>
			<td>
				<div>
					<g:if test="${letterTemplateInstance?.name}">
						<span id="name-label" class="property-label"><g:message
								code="letterTemplate.name.label" default="Name" />:</span>
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue
								bean="${letterTemplateInstance}" field="name" /></span>
					</g:if>
				</div>
				<div>
					<g:if test="${letterTemplateInstance?.id}">
						<span id="id-label" class="property-label"><g:message
								code="letterTemplate.id.label" default="ID"/>:</span>
						<span class="property-value" aria-labelledby="id-label"><g:fieldValue
								bean="${letterTemplateInstance}" field="id" /></span>
					</g:if>
				</div>
				<div>
					<g:if test="${letterTemplateInstance?.description}">
						<span id="description-label" class="property-label"><g:message
								code="letterTemplate.description.label" default="Description (Audience & Intent)" />:</span>
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue
								bean="${letterTemplateInstance}" field="description" /></span>
					</g:if>
				</div>
				<div>
					<g:if test="${letterTemplateInstance?.category}">
						<span id="category-label" class="property-label"><g:message
								code="letterTemplate.category.label" default="Category"/>:</span>
						<span class="property-value" aria-labelledby="category-label"><g:fieldValue
								bean="${letterTemplateInstance}" field="category" /></span>
					</g:if>
				</div>
			</td>
				<td>
					<span id="comments-label" class="property-label"><g:message code="letterTemplate.comments.label" default="Comments" />:</span>
						<g:if test="${letterTemplateInstance?.comments}">
							<span id="templateCommentSpan" class="property-value" aria-labelledby="comments-label">
									<g:each var="comment" in="${commentInstanceList}">
									<div>(<g:formatDate format="MM-dd-yyyy" date="${comment.lastUpdated}"/>)(<g:fieldValue bean="${comment.lastModBy}" field="username"/>) <g:fieldValue bean="${comment}" field="comment" /></div>
									</g:each>
							</span>
					</g:if>
				</td>
			</tr>
			</table>
		</div>
		<table id="reportTable" class="tableData">
			<thead>
				<tr>
					<th id="reportGroup">Group</th>
					<th id="reportModule">Module</th>
					<th id="reportContent">Content</th>
					<th id="reportBusinessRules">Business Rules</th>
					<th id="reportComments">Module Comments</th>
				</tr>
			</thead>
			<tbody>
				<g:each var="module" in="${modules}">
					<tr>
						<td><g:fieldValue bean="${module}"
								field="section.group.groupName" /></td>
						<td><g:fieldValue bean="${module}" field="id" /></td>
						<td>
							${raw(module.content)}
						</td>
						<td><g:if test="${module.businessRules}">
								<ul>
									<g:each var="businessRule" in="${module.businessRules}">
										<li><g:fieldValue bean="${businessRule}" field="rule" /></li>
									</g:each>
								</ul>
							</g:if></td>
						<td><g:if test="${module.comments}">
								<ul>
									<g:each var="comment" in="${module.comments}">
										<li><g:fieldValue bean="${comment}" field="comment" /></li>
									</g:each>
								</ul>
							</g:if></td>
					</tr>
				</g:each>
			</tbody>
		</table>
	</div>
</body>
</html>