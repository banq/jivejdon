

//document.onmousewheel = on_mousewheel;

var pt_showPos = "";
var pt_opImg;

function pt_mousemove(e)
{
	var srcElement = e ? e.target : window.event.srcElement;

	var l = 10, t = 10;
	var o, tn;

	o = srcElement;
	while(o && o.parentNode != document)
	{
		if(o.id == "pictools")return;
		if(o.id == "pictools1")return;
		l += o.offsetLeft;
		t += o.offsetTop;
		o = o.offsetParent;
	}

	if(srcElement.tagName == 'IMG' && (srcElement.offsetWidth > 48 || srcElement.w > 48) && (srcElement.offsetHeight > 48 || srcElement.h > 48))
	{
		tn = "pictools";		
		if(pt_showPos != "" + l + ":" + t)
		{
			pt_opImg = srcElement;
			if(!pt_opImg.p)
			{
				pt_opImg.w = pt_opImg.offsetWidth;
				pt_opImg.h = pt_opImg.offsetHeight;

				pt_opImg.width = pt_opImg.w;
				pt_opImg.height = pt_opImg.h;
				pt_opImg.style.width = "";
				pt_opImg.style.height = "";

				pt_opImg.p = 100;
			}

			pt_showPos = "" + l + ":" + t;
			document.getElementById(tn).style.left = l;
			document.getElementById(tn).style.top = t;
			document.getElementById(tn).style.display = "";
		}
	}else if(pt_showPos != "")
	{
		pt_showPos = "";
		document.getElementById("pictools").style.display = "none";
		document.getElementById("pictools1").style.display = "none";
	}
}

document.onmousemove = pt_mousemove;

function pt_restore()
{
	pt_opImg.width = pt_opImg.w;
	pt_opImg.height = pt_opImg.h;
	pt_opImg.p = 100;
}

function pt_fit()
{
	pt_opImg.p = parseInt(64000 / pt_opImg.w);
	pt_opImg.width = 640;
	pt_opImg.height = parseInt(pt_opImg.h * pt_opImg.p / 100);
}

function pt_zoomin()
{
	pt_opImg.p = parseInt(pt_opImg.p * 1.2);
	pt_opImg.width = parseInt(pt_opImg.w * pt_opImg.p / 100);
	pt_opImg.height = parseInt(pt_opImg.h * pt_opImg.p / 100);
}

function pt_zoomout()
{
	if(parseInt(pt_opImg.w * pt_opImg.p / 100) > 320)
	{
		pt_opImg.p = parseInt(pt_opImg.p / 1.2);
		pt_opImg.width = parseInt(pt_opImg.w * pt_opImg.p / 100);
		pt_opImg.height = parseInt(pt_opImg.h * pt_opImg.p / 100);
	}
}

document.write('<style>\n' + 
'.pictools {position:absolute;border:1px solid;border-left-color:#dddddd;border-top-color:#dddddd;border-right-color:#333333;border-bottom-color:#333333;background:#dddddd;}\n' + 
'.pictools1 {border:1px solid;border-left-color:#ffffff;border-top-color:#ffffff;border-right-color:#999999;border-bottom-color:#999999;}\n' + 
'.pt_bt {padding:3 4 4 4;}\n' + 
'.pt_bt_up {padding:2 3 3 3;border:1px solid;border-left-color:#ffffff;border-top-color:#ffffff;border-right-color:#999999;border-bottom-color:#999999;}\n' + 
'.pt_bt_down {padding:2 2 3 4;border:1px solid;border-left-color:#999999;border-top-color:#999999;border-right-color:#ffffff;border-bottom-color:#ffffff;}\n' + 
'</style>\n' + 
'<table cellpadding=1 cellspacing=0 class="pictools" id="pictools" style="display:none;"><tr><td class="pictools1"><table cellpadding=0 cellspacing=0><tr>' +
'<td class="pt_bt" onmouseover="this.className=\'pt_bt_up\'" onmouseout="this.className=\'pt_bt\'" onmouseup="this.className=\'pt_bt_up\'" onmousedown="this.className=\'pt_bt_down\'" onclick="pt_restore()"><img src="'+ contextPath +'/common/js/img/bt_img_full_size.gif" width=15 height=15 title="orign"></td>' +
'<td class="pt_bt" onmouseover="this.className=\'pt_bt_up\'" onmouseout="this.className=\'pt_bt\'" onmouseup="this.className=\'pt_bt_up\'" onmousedown="this.className=\'pt_bt_down\'" onclick="pt_fit()"><img src="'+ contextPath +'/common/js/img/bt_img_fit.gif" width=15 height=15 title="fit"></td>' +
'<td class="pt_bt" onmouseover="this.className=\'pt_bt_up\'" onmouseout="this.className=\'pt_bt\'" onmouseup="this.className=\'pt_bt_up\'" onmousedown="this.className=\'pt_bt_down\'" onclick="pt_zoomin()"><img src="'+ contextPath +'/common/js/img/bt_img_zoom_in.gif" width=15 height=15 title="enlarge"></td>' +
'<td class="pt_bt" onmouseover="this.className=\'pt_bt_up\'" onmouseout="this.className=\'pt_bt\'" onmouseup="this.className=\'pt_bt_up\'" onmousedown="this.className=\'pt_bt_down\'" onclick="pt_zoomout()"><img src="'+ contextPath +'/common/js/img/bt_img_zoom_out.gif" width=15 height=15 title="ensmall"></td>' +

'</tr></table></td></tr></table>\n' +
'<table cellpadding=1 cellspacing=0 class="pictools" id="pictools1" style="display:none;"><tr><td class="pictools1"><table cellpadding=0 cellspacing=0><tr>' +

'</tr></table></td></tr></table>\n');
