<asset:stylesheet src="moduleSelectionAndReorder.css"/>
<div id="dialog-modal" title="Select Module">
	<g:formRemote name="recipeForm" on404="alert('not found!')"
		url="[controller: 'letterTemplate', action:'updateIngredients']">
		<div id="moduleSelection" class="moduleOrderModal">
			<table id="moduleSelectionTable">
				<tr>
					<td id="recipeSelectionCell">
						<table>
							<tr>
								<td>
									<h2>Recipe</h2>
								</td>
							</tr>
							<tr>
								<td>
									<ul id="sortable"></ul>
								</td>
							</tr>
						</table>
					</td>
					<td id="moduleSelectionCell">
						<table>
							<tr>
								<td>
									<h2>Modules</h2>
								</td>
							</tr>
							<tr>
								<td>
									<select id="letterBuilderSectionDropDown"
										name="letterBuilderSection"
										onchange="${remoteFunction(action: 'sectionModules',
		                       											params: '\'sectionId=\' + this.value',
											   							onSuccess: 'populateAvailableModuleList(data)')}">
										<option value="">Choose</option>
									</select>

									<input name="moduleIdSearch" type="text" id="moduleId" value="${params.moduleId}"
														onchange="${remoteFunction(action: 'moduleById',
		                       											params: '\'moduleId=\' + this.value',
											   							onSuccess: 'populateAvailableModuleList(data)')}"/>

	                        		<input name="moduleIdSearchButton" type="button" id="moduleIdButton" value="search"
														onclick="$('#moduleId').trigger('onchange');"/>
									<div id="modulePanel">
									<table id="moduleList"></table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	<input type="hidden"  name="id" value="1" />
	</g:formRemote>
</div>
