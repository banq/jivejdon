<%@ page session="false"  %>
<%
response.setContentType("text/css");
%>
/* START AUTOCOMPLETE */
.ac_holder{
	position:relative;
}
.ac_field{	
    background-image:url(<%=request.getContextPath()%>/common/js/styles/autocomplete/autocomplete_leftcap.gif);
	background-position:right center;
	background-repeat:no-repeat;
}
.ac_field_busy{
    background-image:url(<%=request.getContextPath()%>/common/js/styles/autocomplete/autocomplete_spinner.gif);
	background-position:right center;
	background-repeat:no-repeat;
}

div.autocomplete
{
	position: absolute;
	background-position: top left;
	background-repeat: no-repeat;
	padding: 1px 0 0 0;
}

div.autocomplete div.ac_header,
div.autocomplete div.ac_footer
{
	position: relative;
	height: 4px;
	padding: 0 6px;

	background-position: top right;
	background-repeat: no-repeat;
	overflow: hidden;
}
div.autocomplete div.ac_footer
{
	
}

div.autocomplete div.ac_header div.ac_corner,
div.autocomplete div.ac_footer div.ac_corner
{
	position: absolute;
	top: 0;
	left: 0;
	height: 4px;
	width: 4px;
	
	background-color: #ffffff;
	background-position: top left;
	background-repeat: no-repeat;
}
div.autocomplete div.ac_footer div.ac_corner
{

}
div.autocomplete div.ac_header div.ac_bar,
div.autocomplete div.ac_footer div.ac_bar
{
	height: 4px;
	overflow: hidden;
	background-color: #ffffff;
}


div.autocomplete ul
{
	list-style: none;
	margin: 0 0 -4px 0;
	padding: 0;
	overflow: hidden;
	background-color: #ffffff;
}

div.autocomplete ul li
{
	color: #ccc;
	padding: 0;
	margin: 0 1px 1px;
	text-align: left;
}

div.autocomplete ul li a
{
	color: #666666;
	display: block;
	text-decoration: none;
	background-color: transparent;
	text-shadow: #aaa 0px 0px 2px;
	position: relative;
	padding: 0;
	width: 100%;
}
div.autocomplete ul li a:hover
{
	background-color: #888888;
}
div.autocomplete ul li.ac_highlight a:hover
{
	background-color: #1B5CCD;
}

div.autocomplete ul li a span
{
	display: block;
	padding: 1px 4px;
}

div.autocomplete ul li a span small
{
	font-weight: normal;
	color: #999;
}

div.autocomplete ul li.ac_highlight a span small
{
	color: #ccc;
}

div.autocomplete ul li.ac_highlight a
{
	color: #555;
	background-color:#F5EDA0;
	
	background-position: bottom right;
	background-repeat: no-repeat;
}

div.autocomplete ul li.ac_highlight a span
{

	background-position: bottom left;
	background-repeat: no-repeat;
}

div.autocomplete ul li a .tl,
div.autocomplete ul li a .tr
{
	background-image: transparent;
	background-repeat: no-repeat;
	width: 4px;
	height: 4px;
	position: absolute;
	top: 0;
	padding: 0;
	margin: 0;
}
div.autocomplete ul li a .tr
{
	right: 0;
}

div.autocomplete ul li.ac_highlight a .tl
{
	left: 0;

	background-position: bottom left;
}

div.autocomplete ul li.ac_highlight a .tr
{
	right: 0;
	background-position: bottom right;
}
div.autocomplete ul li.as_warning
{
	
	text-align: center;
}
div.autocomplete ul em
{
	font-style: normal;
	color: #800040;
}
/* END AUTOCOMPLETE */