<%@ page import="com.web.app.LetterTemplate" %>
<!doctype html>

<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="previewLetter.css"/>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
	   	<form id="leftNavPreviewForm">
	         <div class="previewWrapper">
	         	<b>You are now previewing Template #<g:fieldValue bean="${letterTemplateInstance}" field="id"/></b>
	         	<sec:ifAnyGranted roles="ROLE_WRITER">
					<g:link action="editStructure" id="${letterTemplateInstance.id}"><asset:image src="modify.png" alt="Edit Structure"/></g:link>

				 </sec:ifAnyGranted>
	         	<g:link action="exportToWord" id="${letterTemplateInstance.id}"><asset:image src="print.jpg" alt="Export to Word"/></g:link>
	            <!--this sets the width to roughly 81/2 by 11-->
	         
		            <table class="previewBody">
			            <tr>
				            <td class="previewBodyCell">
				                <div id="previewTop">
				                    <table id="previewReference">
				                        <tr>
				                            <td class="previewLogoAndAddress">
				                                <g:render template="previewIngredientTemplate" collection="${logoList}" var="ingredientModule"/>
				                                <div class="previewRefaddress">
				                                	<p>&nbsp;&nbsp;</p>
				                                    <g:render template="previewIngredientTemplate" collection="${ddressList}" var="ingredientModule"/>
				                                </div>
				                            </td>
				                            <td class="previewReference"><g:render template="previewIngredientTemplate" collection="${refInfoList}" var="ingredientModule"/></td>
				                        </tr>
				                    </table>
				                </div>
				                <div class="previewMainBodyCentered">
				                    <g:render template="previewIngredientTemplate" collection="${bodyList}" var="ingredientModule"/>
				                    <g:render template="previewIngredientTemplate" collection="${closingList}" var="ingredientModule"/>
				                </div>
				            
				              </td>
			            </tr>
				 </table>
			 </div>
   	 	</form>
	</body>
</html>