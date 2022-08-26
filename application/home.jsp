<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/js/slide/style_css.jsp"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/post.css"/>
</head>

<body>
<div  style="width: 250px; float: left; overflow: hidden;">
<div id="slideimg" class="block" style="height: 255px; overflow: hidden;">
</div></div>
<script src="<%=request.getContextPath()%>/common/js/prototype.js" type="text/javascript"></script> 
<script src="<%=request.getContextPath()%>/forum/imagesSlide.shtml?count=5&width=240&height=230"></script>
<script src="<%=request.getContextPath()%>/common/js/slide.js"></script>
<script>
slideout();
init();
setTimeout("stop();",60000); 
</script>

<center>
<table  border="0" cellpadding="5" cellspacing="5" bgcolor="#F6F6F4" width="600">
                          <tr>
                            <td align="left">

<div id="approved" ></div>

<script type="text/javascript">
	 function approved(){
        var pars =  "";
        new Ajax.Updater("approved", '<%=request.getContextPath()%>/query/threadApprovedNewList.shtml?start=0&count=15', { method: 'get', parameters: pars });
   }
  approved();
 
  
</script> </td></tr></table>
                        </center>
</body>
</html>
