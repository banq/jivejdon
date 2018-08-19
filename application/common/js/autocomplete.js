 
 //below is from autocomplete.js
function ac(id, contextPath){
     var options = {
		      script: contextPath+'/query/tags.shtml?method=tags&',
		      varname:'q',
		      json:true,
		      shownoresults:true,
		      maxresults:16,
		      callback: function (obj) { 
		      }
     		};

	    	new AutoComplete(id,options);
}

var AutoComplete=Class.create();
AutoComplete.prototype={
Version:'1.3.0',
REQUIRED_PROTOTYPE:'1.6.0',
initialize:function(id,param){
this.PROTOTYPE_CHECK();
this.fld=$(id);
if(!this.fld){
throw("AutoComplete requires a field id to initialize");}
this.sInp="";
this.nInpC=0;
this.aSug=[];
this.iHigh=0;
this.options=param?param:{};
var k,def={
valueSep:null,
minchars:1,
meth:"get",
varname:"input",
className:"autocomplete",
timeout:3000,
delay:500,
offsety:-5,
shownoresults:true,
noresults:"No results were found.",
maxheight:250,
cache:true,
maxentries:25,
onAjaxError:null,
setWidth:false,
minWidth:100,
maxWidth:200,
useNotifier:true};
for(k in def){
if(typeof(this.options[k])!=typeof(def[k]))
this.options[k]=def[k];}
if(this.options.useNotifier){
this.fld.addClassName('ac_field');}
var p=this;
this.fld.onkeypress=function(ev){return p.onKeyPress(ev);};
this.fld.onkeyup=function(ev){return p.onKeyUp(ev);};
this.fld.onblur=function(ev){p.resetTimeout();return true;};
this.fld.setAttribute("AutoComplete","off");},
convertVersionString:function(versionString){
var r=versionString.split('.');
return parseInt(r[0])*100000+parseInt(r[1])*1000+parseInt(r[2]);},
PROTOTYPE_CHECK:function(){
if((typeof Prototype=='undefined')||(typeof Element=='undefined')||(typeof Element.Methods=='undefined')||(this.convertVersionString(Prototype.Version)<
this.convertVersionString(this.REQUIRED_PROTOTYPE)))
throw("AutoComplete requires the Prototype JavaScript framework >= "+
this.REQUIRED_PROTOTYPE);},
onKeyPress:function(e){
if(!e)e=window.event;
var key=e.keyCode||e.wich;
switch(key){
case Event.KEY_RETURN:
this.setHighlightedValue();
Event.stop(e);
break;
case Event.KEY_TAB:
this.setHighlightedValue();
break;
case Event.KEY_ESC:
this.clearSuggestions();
break;}
return true;},
onKeyUp:function(e){
if(!e)e=window.event;
var key=e.keyCode||e.wich;
if(key==Event.KEY_UP||key==Event.KEY_DOWN){
this.changeHighlight(key);
Event.stop(e);}
else this.getSuggestions(this.fld.value);
return true;},
getSuggestions:function(val){
if(val==this.sInp)return false;
if($(this.acID))$(this.acID).remove();
this.sInp=val;
if(val.length<this.options.minchars){
this.aSug=[];
this.nInpC=val.length;
return false;}
var ol=this.nInpC;
this.nInpC=val.length?val.length:0;
var l=this.aSug.length;
if(this.options.cache&&(this.nInpC>ol)&&l&&(l<this.options.maxentries)){
var arr=new Array();
for(var i=0;i<l;i++){
try{if (this.aSug[i].value.toLowerCase().indexOf(val.toLowerCase()) != -1)
        {
  				arr.push(this.aSug[i]);
        }}catch(e){}
}
this.aSug=arr;
this.createList(this.aSug);}else{
var p=this;
clearTimeout(this.ajID);
this.ajID=setTimeout(function(){p.doAjaxRequest(p.sInp)},this.options.delay);}
document.helper=this;
return false;},
getLastInput:function(str){
var ret=str;
if(undefined !=this.options.valueSep){
var idx=ret.lastIndexOf(this.options.valueSep);
ret=idx==-1?ret:ret.substring(idx+1,ret.length);}
return ret;},
doAjaxRequest:function(input){
if(input!=this.fld.value)
return false;
this.sInp=this.getLastInput(this.sInp);
if(typeof this.options.script=='function')
var url=this.options.script(encodeURIComponent(this.sInp));
else
var url=this.options.script+this.options.varname+'='+encodeURIComponent(this.sInp);
if(!url)return false;
var p=this;
var m=this.options.meth;
if(this.options.useNotifier){
this.fld.removeClassName('ac_field');
this.fld.addClassName('ac_field_busy');};
var options={
method:m,
onSuccess:function(req){
if(p.options.useNotifier){
p.fld.removeClassName('ac_field_busy');
p.fld.addClassName('ac_field');};
p.setSuggestions(req,input);},
onFailure:(typeof p.options.onAjaxError=='function')?function(status){
if(p.options.useNotifier){
p.fld.removeClassName('ac_field_busy');
p.fld.addClassName('ac_field');}
p.options.onAjaxError(status)}:
function(status){
if(p.options.useNotifier){
p.fld.removeClassName('ac_field_busy');
p.fld.addClassName('ac_field');}
alert("AJAX error: "+status);}}
new Ajax.Request(url,options);},
setSuggestions:function(req,input){
if(input!=this.fld.value)
return false;
this.aSug=[];
if(this.options.json){
var jsondata=eval('('+req.responseText+')');
this.aSug=jsondata.results;}else{
var results=req.responseXML.getElementsByTagName('results')[0].childNodes;
for(var i=0;i<results.length;i++){
if(results[i].hasChildNodes())
this.aSug.push({'id':results[i].getAttribute('id'),'value':results[i].childNodes[0].nodeValue,'info':results[i].getAttribute('info')});}}
this.acID='ac_'+this.fld.id;
this.createList(this.aSug);},
createDOMElement:function(type,attr,cont,html){
var ne=document.createElement(type);
if(!ne)
return 0;
for(var a in attr)
ne[a]=attr[a];
var t=typeof(cont);
if(t=="string"&&!html)
ne.appendChild(document.createTextNode(cont));
else if(t=="string"&&html)
ne.innerHTML=cont;
else if(t=="object")
ne.appendChild(cont);
return ne;},
createList:function(arr){
if($(this.acID))$(this.acID).remove();
this.killTimeout();
if(arr.length==0&&!this.options.shownoresults)return false;
var div=this.createDOMElement('div',{id:this.acID,className:this.options.className});
var hcorner=this.createDOMElement('div',{className:'ac_corner'});
var hbar=this.createDOMElement('div',{className:'ac_bar'});
var header=this.createDOMElement('div',{className:'ac_header'});
header.appendChild(hcorner);
header.appendChild(hbar);
div.appendChild(header);
var ul=this.createDOMElement('ul',{id:'ac_ul'});
var p=this;
if(arr.length==0&&this.options.shownoresults){
var li=this.createDOMElement('li',{className:'ac_warning'},this.options.noresults);
ul.appendChild(li);}else{
for(var i=0,l=arr.length;i<l;i++){
var val=arr[i].value;
var st=val.toLowerCase().indexOf(this.sInp.toLowerCase());
var output=val.substring(0,st)+'<em>'+val.substring(st,st+this.sInp.length)+'</em>'+val.substring(st+this.sInp.length);
var span=this.createDOMElement('span',{},output,true);
if(arr[i].info!=''){
var br=this.createDOMElement('br',{});
span.appendChild(br);
var small=this.createDOMElement('small',{},arr[i].info);
span.appendChild(small);}
var a=this.createDOMElement('a',{href:'#'});
var tl=this.createDOMElement('span',{className:'tl'},'&nbsp;',true);
var tr=this.createDOMElement('span',{className:'tr'},'&nbsp;',true);
a.appendChild(tl);
a.appendChild(tr);
a.appendChild(span);
a.name=i+1;
a.onclick=function(){
p.setHighlightedValue();
return false;};
a.onmouseover=function(){
p.setHighlight(this.name);};
var li=this.createDOMElement('li',{},a);
ul.appendChild(li);}}
div.appendChild(ul);
var fcorner=this.createDOMElement('div',{className:'ac_corner'});
var fbar=this.createDOMElement('div',{className:'ac_bar'});
var footer=this.createDOMElement('div',{className:'ac_footer'});
footer.appendChild(fcorner);
footer.appendChild(fbar);
div.appendChild(footer);
var pos=this.fld.cumulativeOffset();
div.style.left=pos[0]+"px";
div.style.top=pos[1]+this.fld.offsetHeight+"px";
var w=(
this.options.setWidth&&this.fld.offsetWidth<this.options.minWidth)?this.options.minWidth:(
this.options.setWidth&&this.fld.offsetWidth>this.options.maxWidth)?this.options.maxWidth:
this.fld.offsetWidth;
div.style.width=w+"px";
div.onmouseover=function(){p.killTimeout()};
div.onmouseout=function(){p.resetTimeout()};
document.getElementsByTagName("body")[0].appendChild(div);
this.iHigh=1;
this.setHighlight(1);
this.toID=setTimeout(
function(){
p.clearSuggestions()},this.options.timeout);},
changeHighlight:function(key){
var list=$("ac_ul");
if(!list)
return false;
var n;
n=(key==Event.KEY_DOWN||key==Event.KEY_TAB)?this.iHigh+1:this.iHigh-1;
n=(n>list.childNodes.length)?list.childNodes.length:((n<1)?1:n);
this.setHighlight(n);},
setHighlight:function(n){
var list=$('ac_ul');
if(!list)return false;
if(this.iHigh>0)this.clearHighlight();
this.iHigh=Number(n);
list.childNodes[this.iHigh-1].className='ac_highlight';
this.killTimeout();},
clearHighlight:function(){
var list=$('ac_ul');
if(!list)return false;
if(this.iHigh>0){
list.childNodes[this.iHigh-1].className='';
this.iHigh=0;}},
setHighlightedValue:function(){
if(this.iHigh){
if(!this.aSug[this.iHigh-1])return;
if(undefined !=this.options.valueSep){
var str=this.getLastInput(this.fld.value);
var idx=this.fld.value.lastIndexOf(str);
str=this.aSug[this.iHigh-1].value+this.options.valueSep;
this.sInp=this.fld.value=idx==-1?str:this.fld.value.substring(0,idx)+str;}else{
var str=this.getLastInput(this.fld.value);
var idx=this.fld.value.lastIndexOf(str);
str=this.aSug[this.iHigh-1].value;
this.sInp=this.fld.value=idx==-1?str:this.fld.value.substring(0,idx)+str;}
this.fld.focus();
if(this.fld.selectionStart)
this.fld.setSelectionRange(this.sInp.length,this.sInp.length);
this.clearSuggestions();
if(typeof this.options.callback=='function')
this.options.callback(this.aSug[this.iHigh-1]);}},
killTimeout:function(){
clearTimeout(this.toID);},
resetTimeout:function(){
this.killTimeout();
var p=this;
this.toID=setTimeout(
function(){
p.clearSuggestions();},p.options.timeout);},
clearSuggestions:function(){
this.killTimeout();
if($(this.acID)){
this.fadeOut(300,function(){
$(this.acID).remove();});}},
fadeOut:function(milliseconds,callback){
this._fadeFrom=1;
this._fadeTo=0;
this._afterUpdateInternal=callback;
this._fadeDuration=milliseconds;
this._fadeInterval=50;
this._fadeTime=0;
var p=this;
this._fadeIntervalID=setInterval(
function(){
p._changeOpacity()},this._fadeInterval);},
_changeOpacity:function(){
if(!$(this.acID)){
this._fadeIntervalID=clearInterval(this._fadeIntervalID);
return;}
this._fadeTime+=this._fadeInterval;
var ieop=Math.round((this._fadeFrom+((this._fadeTo-this._fadeFrom)*(this._fadeTime/this._fadeDuration)))*100)
var op=ieop/100;
var el=$(this.acID);
if(el.filters){
try{
el.filters.item("DXImageTransform.Microsoft.Alpha").opacity=ieop;}catch(e){
el.style.filter='progid:DXImageTransform.Microsoft.Alpha(opacity='+ieop+')';}}else{
el.style.opacity=op;}
if(this._fadeTime>=this._fadeDuration){
clearInterval(this._fadeIntervalID);
if(typeof this._afterUpdateInternal=='function')
this._afterUpdateInternal();}}}

