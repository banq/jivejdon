
function overlay(curobj, subobjstr, opt_position){
if (document.getElementById){
var subobj=document.getElementById(subobjstr)
subobj.style.display=(subobj.style.display!="block")? "block" : "none"
var xpos=getposOffset(curobj, "left")+((typeof opt_position!="undefined" && opt_position.indexOf("right")!=-1)? -(subobj.offsetWidth-curobj.offsetWidth) : 0) 
var ypos=getposOffset(curobj, "top")+((typeof opt_position!="undefined" && opt_position.indexOf("bottom")!=-1)? curobj.offsetHeight : 0)
subobj.style.left=xpos+"px"
subobj.style.top=ypos+"px"
return false
}
else
return true
}


function leftRightgoPageREST(event)
{
   var page;
   event = event ? event : (window.event ? window.event : null);

   if (event.keyCode==39) 
      page = cstart + count               
   else if (event.keyCode==37) 
      page = cstart - count      

   alert("page=" + page);
   
   var path;
    if (page != 0)	    	
	   path = pageURL + "/" + page;
	else
	  path = pageURL ;
	document.location = path;
	
} 


function goToAnotherPageREST(contextPath, count)
{
	var page = document.getElementById("pageToGo").value * 1;
	var path;

	if (!isNaN(page) && page > 0) {	 
	    var ttt = (page-1) * count;
	    if (ttt != 0)	    	
		   path = contextPath + "/" + ttt;
	    else
	       path = contextPath ;
		document.location = path;
	}
	
} 

function goToAnotherPage(contextPath, count)
{
	var page = document.getElementById("pageToGo").value * 1;

	if (!isNaN(page) && page > 0) {
	    var mychar = "?";
	    if (contextPath.toLowerCase().indexOf("?") > -1)
	      mychar = "&";
		var path = contextPath + mychar + "count=" + count + "&start=" + ((page-1) * count) ;
		document.location = path;
	}
	
} 