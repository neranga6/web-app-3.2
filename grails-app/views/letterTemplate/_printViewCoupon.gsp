<%@ page import="com.web.app.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<asset:stylesheet src="previewLetter.css"/>
		<asset:stylesheet src='printScreen.css' media="print, screen" type="text/css"/>
		<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	
		<body>
	<div class="reportBackLink"><g:link controller="letterTemplate" action="list">Back to template list</g:link></div>

	    	<form id="leftNavPreviewForm">
		         <div class="previewWrapper">
		       
		            <!--this sets the width to roughly 81/2 by 11-->
				
	            <div class="printPreviewBody">
	                <div id="previewTop">
	                    <table id="previewReference">
	                        <tr>
	                            <td class="previewLogoAndAddress">
	                                <g:render template="previewIngredientTemplate" collection="${logoList}" var="ingredientModule"/>
	                                <div class="previewRefaddress">
	                                	<p>&nbsp;&nbsp;</p>
	                                    <g:render template="previewIngredientTemplate" collection="${addressList}" var="ingredientModule"/>
	                                </div>
	                            </td>
	                            <td class="previewReference"><g:render template="previewIngredientTemplate" collection="${refInfoList}" var="ingredientModule"/></td>
	                        </tr>
	                    </table>
	                </div>
	                <div class="previewLeftbar">
	                    <span class="previewTitlebar"><g:render template="previewTitleIngredientTemplate" collection="${titleList}" var="ingredientModule"/></span>
	                    <br /><br />
	                    <g:render template="previewIngredientTemplate" collection="${advMessageList}" var="ingredientModule"/>
	                </div>
	                <div class="previewMainBody">
	                    <g:render template="previewIngredientTemplate" collection="${bodyList}" var="ingredientModule"/>
	                    <g:render template="previewIngredientTemplate" collection="${closingList}" var="ingredientModule"/>
	                </div>
	                <div class="previewCoupon">
	                     <g:render template="previewIngredientTemplate" collection="${couponList}" var="ingredientModule"/>
	                </div>
	            </div>
			 </div>
   	 	</form>
	</body>
	
</html>