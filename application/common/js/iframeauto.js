

var iframeids=["hotlist"]


var iframehide="yes"

function dyniframesize() 
{
var dyniframe=new Array()
for (i=0; i<iframeids.length; i++)
{
if (document.getElementById)
 {

dyniframe[dyniframe.length] = document.getElementById(iframeids[i]);
if (dyniframe[i] && !window.opera)
{
dyniframe[i].style.display="block"
if (dyniframe[i].contentDocument && dyniframe[i].contentDocument.body.offsetHeight) // 如果用户的浏览器是NetScape
dyniframe[i].height = dyniframe[i].contentDocument.body.offsetHeight; 
else if (dyniframe[i].Document && dyniframe[i].Document.body.scrollHeight) // 如果用户的浏览器是IE
dyniframe[i].height = dyniframe[i].Document.body.scrollHeight;
}
}
if ((document.all || document.getElementById) && iframehide=="no")
{
var tempobj=document.all? document.all[iframeids[i]] : document.getElementById(iframeids[i])
tempobj.style.display="block"
}
}
}

if (window.addEventListener)
window.addEventListener("load", dyniframesize, false)
else if (window.attachEvent)
window.attachEvent("onload", dyniframesize)
else
window.onload=dyniframesize

