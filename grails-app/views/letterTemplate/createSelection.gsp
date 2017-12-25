<!doctype html>
<html>
	<head>
		<meta name="layout" content="oyslayout"/>
		<script>var ctx = "<%=request.getContextPath()%>"</script>
		<asset:stylesheet src="home.css"/>
		%{--<asset:javascript src="js/application.js"/>--}%
		<asset:javascript src="js/createSelection.js"/>
	</head>
	<body>
		<div class="mainPageColumnLeft">
            <div class="colHeader1" >
                <h2><span class="alternateTextColor">Templates</span></h2>
             </div>
        </div>
	    <h3>Add New Template</h3>
        <b>Build New Template From:</b>
        <br />
        <br />
     	<div id="createTemplateSelect">
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
	     	<g:form id="createTemplateSelectionForm" action="create" >
		        <g:radioGroup name="letterTemplateGroup" labels="['Model', 'Existing Letter']" values="['new','existing']" value="new" >
					<p class="radioButton"><g:message code="${it.label}" />: ${it.radio}</p>
				</g:radioGroup>
				<br />
				<br />
				 
				<label for="newModelTemplate">
					<g:message code="model.selection.label" default="Select model to use" />
					<span class="required-indicator">*</span>
				</label>
				 <br />
				<g:select id="newModelTemplate" class="model.id" from="${com.edm.Model.list()}" optionKey="id" value="${model?.id}"
                          name="newModelTemplate" noSelection="['':'-select-']"/>
		        <g:select style='display:none;' id="modelExistingTemplate" class="letterTemplate.id" from="${com.edm.LetterTemplate.list()}" optionKey="id" value="${letterTemplate?.id}"
                          name="modelExistingTemplate" noSelection="['':'-select-']"/>
		        <br />
				<br /> 
				<div id="modelImage"></div>
		        <label for="templateName">
					<g:message code="model.templateName.label" default="Enter Template Name:" />
					<span class="required-indicator">*</span>
				</label>
				 <br />
		         <g:textField name="templateName" value="" />
		         
		        <br />
				<br /> 
		        <label for="templateDescription">
					<g:message code="model.templateDescription.label" default="Description (Audience & Intent):" />
					<span class="required-indicator">*</span>
				</label>
				 <br />
		         <g:textArea name="templateDescription" value="" />
		         
		        <br />
				<br />
				<label for="category">
					<g:message code="letterTemplate.category.label" default="Category" />
					<span class="required-indicator">*</span>
				</label>
				<br />
				<g:select id="category" name="categoryId" from="${com.edm.Category.list()}" optionKey="id" value="${category?.id}" noSelection="['':'-select-']"/>
				<br/>
				<br/>
						
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.add.letter.label', default: 'Add Letter')}" />
				</fieldset>
			</g:form>
		</div>
			<script>
                var modelToImageMap = {
                    "Left Bar": "leftbar.png",
                    "Centered": "centered.png",
                    "Coupon/Tearoff": "coupon.png"
                };

                function switchModelImage() {
                    var modelName = $("#newModelTemplate option:selected").text(),
                        imageFile = modelToImageMap[modelName],
                        imagePath = ctx + "/assets/" + imageFile,
                        image = $("<img/>", {
                            src: imagePath
                        });

                    $("#modelImage").children().remove();

                    if(imageFile) {
                        $("#modelImage").append(image);
                    }
                }

                if (typeof jQuery !== 'undefined') {
                    $(function() {
                        $("#newModelTemplate").change(switchModelImage);
                    });
                }

                      $(".radioButton").change(function(){
                        var radioValue = $('input[name=letterTemplateGroup]:checked').val();

                        if(radioValue == "new"){
                            $('#newModelTemplate').show();
                            $('#modelExistingTemplate').hide();

                        }else if(radioValue == 'existing'){
                            $('#newModelTemplate').hide();
                            $('#modelExistingTemplate').show();
                        };

                    });

                    $("#saveLetterCommentButton").click(function(){

                        var letterId = $('#id').val();
                        var comment = $('#letterComment').val();
                        var version = $('#version').val();

                        addLetterTemplateComment(letterId, comment, version);

                    });

                    $("#selectAddress").click(function(){

                        $('#dialog-modal').dialog({ height: 200 });

                    });

                    $("#sectionList").change(function(){
                        alert('Change selected');
                    });

                    $("#addLetterCommentButton").click(function(){

                        $('#letterComment').val('');
                        $('#addLetterComment-modal').dialog('open');

                    });
           	</script>
	</body>
</html>