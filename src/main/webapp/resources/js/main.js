/*
 * Url preview script 
 * powered by jQuery (http://www.jquery.com)
 * 
 * written by Alen Grakalic (http://cssglobe.com)
 * 
 * for more info visit http://cssglobe.com/post/1695/easiest-tooltip-and-image-preview-using-jquery
 *
 */
 
this.screenshotPreview = function(){	
	/* CONFIG */
		
		xOffset = 20;
		yOffset = 30;
		
		// these 2 variable determine popup's distance from the cursor
		// you might want to adjust to get the right result
		
	/* END CONFIG */
	$("a.screenshot").hover(function(e){
		this.t = this.title;
		this.title = "";	
		var c = (this.t != "") ? "<br/>" + this.t : "";
		$("body").append("<p id='screenshot'><img src='"+ this.rel +"' alt='' />"+ c +"</p>");	
	

		
		$("#screenshot")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px")
			.fadeIn("fast");						
    },
	
	
	
	function(){
		this.title = this.t;	
		$("#screenshot").remove();
    });	
	$("a.screenshot").mousemove(function(e){
		$("#screenshot")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};


// starting the script on page load
$(document).ready(function(){
	screenshotPreview();
	//Assigning Priority here
	$(".priority-red").attr('alt', 'priority 1');
	$(".priority-purple").attr('alt', 'priority 2');
	$(".priority-orange").attr('alt', 'priority 3');
	$(".priority-yellow").attr('alt', 'priority 4');
	$(".priority-green").attr('alt', 'priority 5');
	$(".priority-blue").attr('alt', 'priority 6');
	$(".priority-brown").attr('alt', 'priority 7');
	$(".priority-light-grey").attr('alt', 'priority 8');
	//assigning elapsed time
	$(".time-yellow").attr('alt', '5 minutes before scheduled dispatch time for call on hold');
	$(".time-red").attr('alt', 'Time of dispatch for the call on hold');
	});