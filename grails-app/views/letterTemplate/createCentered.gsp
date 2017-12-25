<%@ page import="com.web.app.LetterTemplate" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>
        <asset:stylesheet src="jquery-ui.css"/>
        <asset:javascript src="js/jquery-1.11.1.min.js"/>
        <asset:javascript src="js/jquery-ui-1.10.4.custom.min.js"/>
        <asset:stylesheet src="letterBuilder.css"/>
        <asset:javascript src="js/application.js"/>
      	<g:set var="entityName" value="${message(code: 'letterTemplate.label', default: 'LetterTemplate')}" />
		<title><g:message code="default.create.label" default='LetterTemplate' args="[entityName]" /></title>
	</head>
	<body>
					
		<div id="create-letterTemplate" class="content scaffold-create" role="main">
			<h2><g:message code="default.create.label" default="Create Template" args="[entityName]" /></h2>
			<h2>Template Builder for TN # <span id="letterTemplateId"><g:fieldValue bean="${letterTemplateInstance}" field="id"/></span>  |  <g:link action="preview" id="${letterTemplateInstance.id}">Preview this template</g:link></h2>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${letterTemplateInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${letterTemplateInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form  >
			
				<g:hiddenField name="id" value="${letterTemplateInstance?.id}" />
				<g:hiddenField name="version" value="${letterTemplateInstance?.version}" />
				<sec:ifAnyGranted roles="ROLE_WRITER">
					<g:actionSubmit class="delete letterDeleteButton" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</sec:ifAnyGranted>
				
				<div id="letterBorder">
					<div id="top">
						<table id="topSection">
							<tbody>
	                            <tr>
	                                <td>
	                                	<sec:ifAnyGranted roles="ROLE_WRITER">
	                                    <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
											               onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'logo']">Select Logo Modules
             							</g:remoteLink>
	                                    <br />
	                                    </sec:ifAnyGranted>
	                                   	<g:render template="ingredientTemplate" collection="${logoList}" var="ingredientModule"/>
	                                    <div class="refaddress">
	                                        <div class="refaddress">
	                                        <sec:ifAnyGranted roles="ROLE_WRITER">
	                                        <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'address']">Select Address Modules
             								</g:remoteLink>
	                                        <br />
	                                        </sec:ifAnyGranted>
	                                        <g:render template="ingredientTemplate" collection="${addressList}" var="ingredientModule"/>
	                                    </div>
										</div>
	                                </td>
	                                <td>
	                                	<sec:ifAnyGranted roles="ROLE_WRITER">
	                                    <g:remoteLink controller="letterTemplate"
					                         			   action="recipe"
					                         			   id="${letterTemplateInstance.id}"
             											   onSuccess="populateModuleSelectionList(data)"
             											   params="[group:'referenceInformation']">Select Reference Information Modules
             							</g:remoteLink>
	                                    <br />
	                                    </sec:ifAnyGranted>
	                                   <g:render template="ingredientTemplate" collection="${refInfoList}" var="ingredientModule"/>
	                                </td>
	                            </tr>
                            </tbody>
                        </table>
                     
                       </div> 
                  

                    <div class="bodycentered">
                    	<sec:ifAnyGranted roles="ROLE_WRITER">
                        <g:remoteLink controller="letterTemplate"
                 			   action="recipe"
                 			   id="${letterTemplateInstance.id}"
							   onSuccess="populateModuleSelectionList(data)"
							   params="[group:'body']">Select Body Modules
             			</g:remoteLink>
             			<br />
             			</sec:ifAnyGranted>
                         <g:render template="ingredientTemplate" collection="${bodyList}" var="ingredientModule"/>
                       <g:render template="ingredientTemplate" collection="${moduleList}" var="bodyModule"/>
                       <sec:ifAnyGranted roles="ROLE_WRITER">
                        <g:remoteLink controller="letterTemplate"
					    	   action="recipe"
					    	   id="${letterTemplateInstance.id}"
             				   onSuccess="populateModuleSelectionList(data)"
             				   params="[group:'closing']">Select Closing Modules
             			</g:remoteLink>
                        <br />
                        </sec:ifAnyGranted>
                       <g:render template="ingredientTemplate" collection="${closingList}" var="ingredientModule"/>
                   </div>
                </div>
				
			</g:form>
			<g:render template="moduleSelectionModal"/>
		</div>
		</body>
</html>
