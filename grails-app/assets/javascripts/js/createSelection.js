/**
 * This is used in the Create Letter screen to show a small thumbnail image when a model is selected in the dropdown.
 */


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

