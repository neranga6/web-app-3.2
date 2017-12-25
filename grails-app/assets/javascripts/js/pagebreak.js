	var originalHTML ="";
	var foundBreak = false;

	function getHTMLAfterPageBreak (htmlStr) {
		// get the HTML after the <p>PAGEBREAK</p>
		var pageBreakText = "--------------PAGEBREAK--------------";
		var sizeOfPageBreakText = pageBreakText.length;
		var index = htmlStr.indexOf(pageBreakText);
		console.log("index of pageBreak=" + index);
		// add 4 to go past </p> tag
		var htmlAfterPageBreak = htmlStr.substring((index+sizeOfPageBreakText +4));
		console.log("After page break:" + htmlAfterPageBreak);

		return htmlAfterPageBreak;
}


function getHTMLBeforePageBreak (htmlStr) {
	// get the HTML before the <p>PAGEBREAK</p>
		var pageBreakText = "--------------PAGEBREAK--------------";
		var sizeOfPageBreakText = pageBreakText.length;
		console.log("Size of pageBreakText = " + sizeOfPageBreakText);
		var index = htmlStr.indexOf(pageBreakText);
		// subtract 3 to go before <p> tag
		var htmlBeforePageBreak = htmlStr.substring(0, index-3);
		

		return htmlBeforePageBreak;
}


(function() {
    var beforePrint = function() {
        console.log('Functionality to run before printing.');
        
        // all of this craziness is to create a pagebreak
        // page-break-before does not work inside of div's that are not set to float:none
        // but setting the divs to float:none means that whole page gets all rearranged
        // So, by finding the PAGEBREAK section and breaking the DIVs at that point and 
        // inserting a page break there seemed to be the easiest solution.
        
        // NOTE: currently window.matchMedia is broke in Chrome.
        // IE uses window.onbeforeprint so it is working.
        
        var htmlOfMainBody = $("div.previewMainBody").html();
        originalHTML = htmlOfMainBody; // save the original HTML to set the page back later.
        var breakBodySuffix = 1;
        var previewWrapperSuffix = 1;
        var mainBodySelector = "div.previewMainBody";
        var previewWrapperSelector = "div.previewWrapper";
        while( htmlOfMainBody!=null && htmlOfMainBody.indexOf("PAGEBREAK")>0){
        	foundBreak = true;
	        //console.log(htmlOfMainBody);
	        var htmlAfterPageBreak  = getHTMLAfterPageBreak(htmlOfMainBody);
	       // console.log(htmlAfterPageBreak);
	        
	        var htmlBeforePageBreak  = getHTMLBeforePageBreak(htmlOfMainBody);
	        
	       console.log("--------------------------------------------");
	        
	       console.log(htmlBeforePageBreak);
	        
	        console.log("mainBodySelector=" + mainBodySelector);
	        // now set the first part of the template to be just what was before page break
	        $(mainBodySelector).html(htmlBeforePageBreak);
	        
	       	
	       	var breakAndWrappers = "<div style='float:none;page-break-before:always;' class='pageBreaker'></div><div class='previewWrapper' id='previewWrapper" + previewWrapperSuffix+ "'><div class='printPreviewBody'><div class='previewMainBody' id='breakBody"+breakBodySuffix+"'></div></div></div>";
	       	console.log("breakAndWrappers=" +  breakAndWrappers);
	       	
	       	$(previewWrapperSelector).append(breakAndWrappers);
	       	
	       	mainBodySelector = "div#breakBody" + breakBodySuffix;
	       	console.log("mainBodySelector after change=" + mainBodySelector);
	       	
	       	$(mainBodySelector).html(htmlAfterPageBreak);
	       	
	       	// initialize values for next iteration through loop
	       	htmlOfMainBody = htmlAfterPageBreak; // $(mainBodySelector).html();
	       	// console.log("htmlOfMainBody="  + htmlOfMainBody);
	       	previewWrapperSelector = "div#previewWrapper" + previewWrapperSuffix;
	        previewWrapperSuffix = previewWrapperSuffix + 1;
	       	breakBodySuffix = breakBodySuffix +1;
        }
       	
    };
    
	    var afterPrint = function() {
	        console.log('Functionality to run after printing');
	        console.log('Found break = ' + foundBreak);
	        if(foundBreak){
	        	// clear what's in the 
	        	$("div.previewMainBody").empty();
	        	// add original html back :
	        	$("div.previewMainBody").html(originalHTML);
	        	// now remove any extra previewMainBody tags
	        	$("div[id^='previewWrapper']").remove();
	        	$("div[class^='pageBreaker']").remove();
	      	}
	    };
	        
	     // console.log(window.matchMedia)
	
			if(window.matchMedia){
	        var mediaQueryList = window.matchMedia('print');
	        mediaQueryList.addListener(function(mql) {
	            if (mql.matches) {
	                beforePrint();
	            } else {
	                afterPrint();
	            }
	        });
	      }
	 
	
	    window.onbeforeprint = beforePrint;
	    window.onafterprint = afterPrint;
	    
  
    
    
}());