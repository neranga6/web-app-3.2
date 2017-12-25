package com.web.app
//import org.springframework.security.access.annotation.Secured

/**
 * Search functions for the writers tool.
 */

//@Secured(['ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_WRITER'])
class SearchController {

	SearchType readSearchType(String rawSearchType) {
		SearchType searchType = SearchType.createSearchType(rawSearchType)
		
		if (!searchType) {
			flash.error = message(code: "typeMismatch.searchType", args: [rawSearchType])
			redirect uri: '/'
		}
		
		return searchType
	}
	
    def index() {
		log.debug "Parameters for search: ${params}"

		def searchType = readSearchType(params.searchType)
		def searchParams = [searchType: params.searchType, criteria: params.criteria]
		
		if(searchType) {
			
			switch(searchType) {
				case SearchType.EMPTY:
					emptySearch()
					break
                case SearchType.MODULE_CONTENT:
				case SearchType.MODULE_ID:
					searchByModule(searchParams)
					break
                case SearchType.LETTER_TEMPLATE_NAME:
				case SearchType.LETTER_TEMPLATE_ID:
				case SearchType.LETTER_TEMPLATE_DESCRIPTION:
				case SearchType.LETTER_TEMPLATE_CATEGORY:
					searchByLetterTemplate(searchParams)
					break
            }
		}
	}
	
	private emptySearch() {
		flash.error = message(code: "searchType.empty")
		log.warn "No search type selected in the dropdown"
		redirect uri: '/'
	}
	
	private searchByModule(params) {
		log.trace "Searching by module content with parameters \"${params}\""
		redirect(controller: "Module", action: "globalSearch", params: params)
	}
	
	private searchByLetterTemplate(params) {
		log.trace "Searching by letter template category with parameters \"${params}\""
		redirect(controller: "LetterTemplate", action: "list", params: params)
	}
}
