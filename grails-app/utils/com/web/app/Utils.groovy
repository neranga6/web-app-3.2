package com.web.app

class Utils {
	
	static public String createSortURL(params){
		
		if(params.sort){		
			"&sort=${params.sort}&order=${params.order}&max=${params.max}"
		}else{
			''
		}
	} 

}
