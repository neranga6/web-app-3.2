package com.web.app

import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry

/**
 * This is necessary so that ID fields are NOT displayed with commas.
 * 
 * @see http://stackoverflow.com/questions/2108255/how-can-i-change-the-way-grails-gsp-fieldvalue-formats-integers#answer-12197559
 */
class MyEditorRegistrar implements PropertyEditorRegistrar {
	public void registerCustomEditors(PropertyEditorRegistry reg) {
		reg.registerCustomEditor(Long, new LongEditor())
	}
}
