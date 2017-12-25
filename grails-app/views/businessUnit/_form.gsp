<%@ page import="com.web.app.BusinessUnit" %>
<table border="1">
	<tbody>
		<tr>
			<td>
				<div class="fieldcontain ${hasErrors(bean: businessUnitInstance, field: 'name', 'error')} required">
					<label for="name">
						<g:message code="businessUnit.businessUnitName.label" default="Business Unit" />
						<span class="required-indicator">*</span>
					</label>
				</div>
			</td>
			<td>
				<g:textField class="adminTextField" name="name" required="" value="${businessUnitInstance?.name}"/>
			</td>
		</tr>
	</tbody>
</table>
