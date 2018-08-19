/*
 * Compressed by JSA(www.xidea.org)
 */
Calendar=function(A,B,_,D){this.activeDiv=null;this.currentDateEl=null;this.checkDisabled=null;this.timeout=null;this.onSelected=_||null;this.onClose=D||null;this.dragging=false;this.hidden=false;this.minYear=1970;this.maxYear=2050;this.dateFormat=Calendar._TT["DEF_DATE_FORMAT"];this.ttDateFormat=Calendar._TT["TT_DATE_FORMAT"];this.isPopup=true;this.weekNumbers=true;this.mondayFirst=A;this.dateStr=B;this.ar_days=null;this.table=null;this.element=null;this.tbody=null;this.firstdayname=null;this.monthsCombo=null;this.yearsCombo=null;this.hilitedMonth=null;this.activeMonth=null;this.hilitedYear=null;this.activeYear=null;this.dateClicked=false;if(!Calendar._DN3){var C=new Array();for(var $=8;$>0;)C[--$]=Calendar._DN[$].substr(0,3);Calendar._DN3=C;C=new Array();for($=12;$>0;)C[--$]=Calendar._MN[$].substr(0,3);Calendar._MN3=C}};Calendar._C=null;Calendar.is_ie=(/msie/i.test(navigator.userAgent)&&!/opera/i.test(navigator.userAgent));Calendar._DN3=null;Calendar._MN3=null;Calendar.getAbsolutePos=function(A){var _={x:A.offsetLeft,y:A.offsetTop};if(A.offsetParent){var $=Calendar.getAbsolutePos(A.offsetParent);_.x+=$.x;_.y+=$.y}return _};Calendar.isRelated=function(_,A){var B=A.relatedTarget;if(!B){var $=A.type;if($=="mouseover")B=A.fromElement;else if($=="mouseout")B=A.toElement}while(B){if(B==_)return true;B=B.parentNode}return false};Calendar.removeClass=function(_,B){if(!(_&&_.className))return;var A=_.className.split(" "),C=new Array();for(var $=A.length;$>0;)if(A[--$]!=B)C[C.length]=A[$];_.className=C.join(" ")};Calendar.addClass=function($,_){Calendar.removeClass($,_);$.className+=" "+_};Calendar.getElement=function($){if(Calendar.is_ie)return window.event.srcElement;else return $.currentTarget};Calendar.getTargetElement=function($){if(Calendar.is_ie)return window.event.srcElement;else return $.target};Calendar.stopEvent=function($){if(Calendar.is_ie){window.event.cancelBubble=true;window.event.returnValue=false}else{$.preventDefault();$.stopPropagation()}return false};Calendar.addEvent=function($,A,_){if($.attachEvent)$.attachEvent("on"+A,_);else if($.addEventListener)$.addEventListener(A,_,true);else $["on"+A]=_};Calendar.removeEvent=function($,A,_){if($.detachEvent)$.detachEvent("on"+A,_);else if($.removeEventListener)$.removeEventListener(A,_,true);else $["on"+A]=null};Calendar.createElement=function($,A){var _=null;if(document.createElementNS)_=document.createElementNS("http://www.w3.org/1999/xhtml",$);else _=document.createElement($);if(typeof A!="undefined")A.appendChild(_);return _};Calendar._add_evs=function(el){with(Calendar){addEvent(el,"mouseover",dayMouseOver);addEvent(el,"mousedown",dayMouseDown);addEvent(el,"mouseout",dayMouseOut);if(is_ie){addEvent(el,"dblclick",dayMouseDblClick);el.setAttribute("unselectable",true)}}};Calendar.findMonth=function($){if(typeof $.month!="undefined")return $;else if(typeof $.parentNode.month!="undefined")return $.parentNode;return null};Calendar.findYear=function($){if(typeof $.year!="undefined")return $;else if(typeof $.parentNode.year!="undefined")return $.parentNode;return null};Calendar.showMonthsCombo=function(){var $=Calendar._C;if(!$)return false;var $=$,A=$.activeDiv,_=$.monthsCombo;if($.hilitedMonth)Calendar.removeClass($.hilitedMonth,"hilite");if($.activeMonth)Calendar.removeClass($.activeMonth,"active");var B=$.monthsCombo.getElementsByTagName("div")[$.date.getMonth()];Calendar.addClass(B,"active");$.activeMonth=B;_.style.left=A.offsetLeft+"px";_.style.top=(A.offsetTop+A.offsetHeight)+"px";_.style.display="block"};Calendar.showYearsCombo=function(_){var $=Calendar._C;if(!$)return false;var $=$,D=$.activeDiv,B=$.yearsCombo;if($.hilitedYear)Calendar.removeClass($.hilitedYear,"hilite");if($.activeYear)Calendar.removeClass($.activeYear,"active");$.activeYear=null;var E=$.date.getFullYear()+(_?1:-1),C=B.firstChild,F=false;for(var A=12;A>0;--A){if(E>=$.minYear&&E<=$.maxYear){C.firstChild.data=E;C.year=E;C.style.display="block";F=true}else C.style.display="none";C=C.nextSibling;E+=_?2:-2}if(F){B.style.left=D.offsetLeft+"px";B.style.top=(D.offsetTop+D.offsetHeight)+"px";B.style.display="block"}};Calendar.tableMouseUp=function(ev){var cal=Calendar._C;if(!cal)return false;if(cal.timeout)clearTimeout(cal.timeout);var _=cal.activeDiv;if(!_)return false;var A=Calendar.getTargetElement(ev);Calendar.removeClass(_,"active");if(A==_||A.parentNode==_)Calendar.cellClick(_);var C=Calendar.findMonth(A),B=null;if(C){B=new Date(cal.date);if(C.month!=B.getMonth()){B.setMonth(C.month);cal.setDate(B);cal.dateClicked=false;cal.callHandler()}}else{var $=Calendar.findYear(A);if($){B=new Date(cal.date);if($.year!=B.getFullYear()){B.setFullYear($.year);cal.setDate(B);cal.dateClicked=false;cal.callHandler()}}}with(Calendar){removeEvent(document,"mouseup",tableMouseUp);removeEvent(document,"mouseover",tableMouseOver);removeEvent(document,"mousemove",tableMouseOver);cal._hideCombos();_C=null;return stopEvent(ev)}};Calendar.tableMouseOver=function(D){var $=Calendar._C;if(!$)return;var A=$.activeDiv,B=Calendar.getTargetElement(D);if(B==A||B.parentNode==A){Calendar.addClass(A,"hilite active");Calendar.addClass(A.parentNode,"rowhilite")}else{Calendar.removeClass(A,"active");Calendar.removeClass(A,"hilite");Calendar.removeClass(A.parentNode,"rowhilite")}var C=Calendar.findMonth(B);if(C){if(C.month!=$.date.getMonth()){if($.hilitedMonth)Calendar.removeClass($.hilitedMonth,"hilite");Calendar.addClass(C,"hilite");$.hilitedMonth=C}else if($.hilitedMonth)Calendar.removeClass($.hilitedMonth,"hilite")}else{var _=Calendar.findYear(B);if(_)if(_.year!=$.date.getFullYear()){if($.hilitedYear)Calendar.removeClass($.hilitedYear,"hilite");Calendar.addClass(_,"hilite");$.hilitedYear=_}else if($.hilitedYear)Calendar.removeClass($.hilitedYear,"hilite")}return Calendar.stopEvent(D)};Calendar.tableMouseDown=function($){if(Calendar.getTargetElement($)==Calendar.getElement($))return Calendar.stopEvent($)};Calendar.calDragIt=function(B){var $=Calendar._C;if(!($&&$.dragging))return false;var _,A;if(Calendar.is_ie){A=window.event.clientY+document.body.scrollTop;_=window.event.clientX+document.body.scrollLeft}else{_=B.pageX;A=B.pageY}$.hideShowCovered();var C=$.element.style;C.left=(_-$.xOffs)+"px";C.top=(A-$.yOffs)+"px";return Calendar.stopEvent(B)};Calendar.calDragEnd=function(ev){var $=Calendar._C;if(!$)return false;$.dragging=false;with(Calendar){removeEvent(document,"mousemove",calDragIt);removeEvent(document,"mouseover",stopEvent);removeEvent(document,"mouseup",calDragEnd);tableMouseUp(ev)}$.hideShowCovered()};Calendar.dayMouseDown=function(_){var el=Calendar.getElement(_);if(el.disabled)return false;var $=el.calendar;$.activeDiv=el;Calendar._C=$;if(el.navtype!=300)with(Calendar){addClass(el,"hilite active");addEvent(document,"mouseover",tableMouseOver);addEvent(document,"mousemove",tableMouseOver);addEvent(document,"mouseup",tableMouseUp)}else if($.isPopup)$._dragStart(_);if(el.navtype==-1||el.navtype==1)$.timeout=setTimeout("Calendar.showMonthsCombo()",250);else if(el.navtype==-2||el.navtype==2)$.timeout=setTimeout((el.navtype>0)?"Calendar.showYearsCombo(true)":"Calendar.showYearsCombo(false)",250);else $.timeout=null;return Calendar.stopEvent(_)};Calendar.dayMouseDblClick=function($){Calendar.cellClick(Calendar.getElement($));if(Calendar.is_ie)document.selection.empty()};Calendar.dayMouseOver=function($){var el=Calendar.getElement($);if(Calendar.isRelated(el,$)||Calendar._C||el.disabled)return false;if(el.ttip){if(el.ttip.substr(0,1)=="_"){var date=null;with(el.calendar.date){date=new Date(getFullYear(),getMonth(),el.caldate)}el.ttip=date.print(el.calendar.ttDateFormat)+el.ttip.substr(1)}el.calendar.tooltips.firstChild.data=el.ttip}if(el.navtype!=300){Calendar.addClass(el,"hilite");if(el.caldate)Calendar.addClass(el.parentNode,"rowhilite")}return Calendar.stopEvent($)};Calendar.dayMouseOut=function(ev){with(Calendar){var el=getElement(ev);if(isRelated(el,ev)||_C||el.disabled)return false;removeClass(el,"hilite");if(el.caldate)removeClass(el.parentNode,"rowhilite");el.calendar.tooltips.firstChild.data=_TT["SEL_DATE"];return stopEvent(ev)}};Calendar.cellClick=function(B){var $=B.calendar,A=false,C=false,F=null;if(typeof B.navtype=="undefined"){Calendar.removeClass($.currentDateEl,"selected");Calendar.addClass(B,"selected");A=($.currentDateEl==B);if(!A)$.currentDateEl=B;$.date.setDate(B.caldate);F=$.date;C=true;$.dateClicked=true}else{if(B.navtype==200){Calendar.removeClass(B,"hilite");$.callCloseHandler();return}F=(B.navtype==0)?new Date():new Date($.date);$.dateClicked=(B.navtype==0);var _=F.getFullYear(),E=F.getMonth();function D(_){var $=F.getDate(),A=F.getMonthDays(_);if($>A)F.setDate(A);F.setMonth(_)}switch(B.navtype){case-2:if(_>$.minYear)F.setFullYear(_-1);break;case-1:if(E>0)D(E-1);else if(_-->$.minYear){F.setFullYear(_);D(11)}break;case 1:if(E<11)D(E+1);else if(_<$.maxYear){F.setFullYear(_+1);D(0)}break;case 2:if(_<$.maxYear)F.setFullYear(_+1);break;case 100:$.setMondayFirst(!$.mondayFirst);return;case 0:if((typeof $.checkDisabled=="function")&&$.checkDisabled(F))return false;break}if(!F.equalsTo($.date)){$.setDate(F);C=true}}if(C)$.callHandler();if(A){Calendar.removeClass(B,"hilite");$.callCloseHandler()}};Calendar.prototype.create=function(G){var K=null;if(!G){K=document.getElementsByTagName("body")[0];this.isPopup=true}else{K=G;this.isPopup=false}this.date=this.dateStr?new Date(this.dateStr):new Date();var $=Calendar.createElement("table");this.table=$;$.cellSpacing=0;$.cellPadding=0;$.calendar=this;Calendar.addEvent($,"mousedown",Calendar.tableMouseDown);var I=Calendar.createElement("div");this.element=I;I.className="calendar";if(this.isPopup){I.style.position="absolute";I.style.display="none"}I.appendChild($);var M=Calendar.createElement("thead",$),C=null,H=null,E=this,A=function($,_,A){C=Calendar.createElement("td",H);C.colSpan=_;C.className="button";Calendar._add_evs(C);C.calendar=E;C.navtype=A;if($.substr(0,1)!="&")C.appendChild(document.createTextNode($));else C.innerHTML=$;return C};H=Calendar.createElement("tr",M);var L=6;(this.isPopup)&&--L;(this.weekNumbers)&&++L;A("-",1,100).ttip=Calendar._TT["TOGGLE"];this.title=A("",L,300);this.title.className="title";if(this.isPopup){this.title.ttip=Calendar._TT["DRAG_TO_MOVE"];this.title.style.cursor="move";A("&#x00d7;",1,200).ttip=Calendar._TT["CLOSE"]}H=Calendar.createElement("tr",M);H.className="headrow";this._nav_py=A("&#x00ab;",1,-2);this._nav_py.ttip=Calendar._TT["PREV_YEAR"];this._nav_pm=A("&#x2039;",1,-1);this._nav_pm.ttip=Calendar._TT["PREV_MONTH"];this._nav_now=A(Calendar._TT["TODAY"],this.weekNumbers?4:3,0);this._nav_now.ttip=Calendar._TT["GO_TODAY"];this._nav_nm=A("&#x203a;",1,1);this._nav_nm.ttip=Calendar._TT["NEXT_MONTH"];this._nav_ny=A("&#x00bb;",1,2);this._nav_ny.ttip=Calendar._TT["NEXT_YEAR"];H=Calendar.createElement("tr",M);H.className="daynames";if(this.weekNumbers){C=Calendar.createElement("td",H);C.className="name wn";C.appendChild(document.createTextNode(Calendar._TT["WK"]))}for(var F=7;F>0;--F){C=Calendar.createElement("td",H);C.appendChild(document.createTextNode(""));if(!F){C.navtype=100;C.calendar=this;Calendar._add_evs(C)}}this.firstdayname=(this.weekNumbers)?H.firstChild.nextSibling:H.firstChild;this._displayWeekdays();var N=Calendar.createElement("tbody",$);this.tbody=N;for(F=6;F>0;--F){H=Calendar.createElement("tr",N);if(this.weekNumbers){C=Calendar.createElement("td",H);C.appendChild(document.createTextNode(""))}for(var J=7;J>0;--J){C=Calendar.createElement("td",H);C.appendChild(document.createTextNode(""));C.calendar=this;Calendar._add_evs(C)}}var D=Calendar.createElement("tfoot",$);H=Calendar.createElement("tr",D);H.className="footrow";C=A(Calendar._TT["SEL_DATE"],this.weekNumbers?8:7,300);C.className="ttip";if(this.isPopup){C.ttip=Calendar._TT["DRAG_TO_MOVE"];C.style.cursor="move"}this.tooltips=C;I=Calendar.createElement("div",this.element);this.monthsCombo=I;I.className="combo";for(F=0;F<Calendar._MN.length;++F){var _=Calendar.createElement("div");_.className="label";_.month=F;_.appendChild(document.createTextNode(Calendar._MN3[F]));I.appendChild(_)}I=Calendar.createElement("div",this.element);this.yearsCombo=I;I.className="combo";for(F=12;F>0;--F){var B=Calendar.createElement("div");B.className="label";B.appendChild(document.createTextNode(""));I.appendChild(B)}this._init(this.mondayFirst,this.date);K.appendChild(this.element)};Calendar._keyEvent=function(E){if(!window.calendar)return false;(Calendar.is_ie)&&(E=window.event);var $=window.calendar,B=(Calendar.is_ie||E.type=="keypress");if(E.ctrlKey)switch(E.keyCode){case 37:B&&Calendar.cellClick($._nav_pm);break;case 38:B&&Calendar.cellClick($._nav_py);break;case 39:B&&Calendar.cellClick($._nav_nm);break;case 40:B&&Calendar.cellClick($._nav_ny);break;default:return false}else switch(E.keyCode){case 32:Calendar.cellClick($._nav_now);break;case 27:B&&$.hide();break;case 37:case 38:case 39:case 40:if(B){var D=$.date.getDate()-1,C=$.currentDateEl,_=null,A=(E.keyCode==37)||(E.keyCode==38);switch(E.keyCode){case 37:(--D>=0)&&(_=$.ar_days[D]);break;case 38:D-=7;(D>=0)&&(_=$.ar_days[D]);break;case 39:(++D<$.ar_days.length)&&(_=$.ar_days[D]);break;case 40:D+=7;(D<$.ar_days.length)&&(_=$.ar_days[D]);break}if(!_){if(A)Calendar.cellClick($._nav_pm);else Calendar.cellClick($._nav_nm);D=(A)?$.date.getMonthDays():1;C=$.currentDateEl;_=$.ar_days[D-1]}Calendar.removeClass(C,"selected");Calendar.addClass(_,"selected");$.date.setDate(_.caldate);$.callHandler();$.currentDateEl=_}break;case 13:if(B){$.callHandler();$.hide()}break;default:return false}return Calendar.stopEvent(E)};Calendar.prototype._init=function(H,I){var A=new Date(),O=I.getFullYear();if(O<this.minYear){O=this.minYear;I.setFullYear(O)}else if(O>this.maxYear){O=this.maxYear;I.setFullYear(O)}this.mondayFirst=H;this.date=new Date(I);var $=I.getMonth(),E=I.getDate(),J=I.getMonthDays();I.setDate(1);var P=I.getDay(),B=H?1:0,D=H?5:6,_=H?6:0;if(H)P=(P>0)?(P-1):6;var G=1,M=this.tbody.firstChild,L=Calendar._MN3[$],R=((A.getFullYear()==O)&&(A.getMonth()==$)),C=A.getDate(),N=I.getWeekNumber(),S=new Array();for(var K=0;K<6;++K){if(G>J){M.className="emptyrow";M=M.nextSibling;continue}var F=M.firstChild;if(this.weekNumbers){F.className="day wn";F.firstChild.data=N;F=F.nextSibling}++N;M.className="daysrow";for(var Q=0;Q<7;++Q){F.className="day";if((!K&&Q<P)||G>J){F.innerHTML="&nbsp;";F.disabled=true;F=F.nextSibling;continue}F.disabled=false;F.firstChild.data=G;if(typeof this.checkDisabled=="function"){I.setDate(G);if(this.checkDisabled(I)){F.className+=" disabled";F.disabled=true}}if(!F.disabled){S[S.length]=F;F.caldate=G;F.ttip="_";if(G==E){F.className+=" selected";this.currentDateEl=F}if(R&&(G==C)){F.className+=" today";F.ttip+=Calendar._TT["PART_TODAY"]}if(P==D||P==_)F.className+=" weekend"}++G;((++P)^7)||(P=0);F=F.nextSibling}M=M.nextSibling}this.ar_days=S;this.title.firstChild.data=Calendar._MN[$]+", "+O};Calendar.prototype.setDate=function($){if(!$.equalsTo(this.date))this._init(this.mondayFirst,$)};Calendar.prototype.refresh=function(){this._init(this.mondayFirst,this.date)};Calendar.prototype.setMondayFirst=function($){this._init($,this.date);this._displayWeekdays()};Calendar.prototype.setDisabledHandler=function($){this.checkDisabled=$};Calendar.prototype.setRange=function(_,$){this.minYear=_;this.maxYear=$};Calendar.prototype.callHandler=function(){if(this.onSelected)this.onSelected(this,this.date.print(this.dateFormat))};Calendar.prototype.callCloseHandler=function(){if(this.onClose)this.onClose(this);this.hideShowCovered()};Calendar.prototype.destroy=function(){var $=this.element.parentNode;$.removeChild(this.element);Calendar._C=null};Calendar.prototype.reparent=function(_){var $=this.element;$.parentNode.removeChild($);_.appendChild($)};Calendar._checkCalendar=function(_){if(!window.calendar)return false;var $=Calendar.is_ie?Calendar.getElement(_):Calendar.getTargetElement(_);for(;$!=null&&$!=calendar.element;$=$.parentNode);if($==null){window.calendar.callCloseHandler();return Calendar.stopEvent(_)}};Calendar.prototype.show=function(){var A=this.table.getElementsByTagName("tr");for(var $=A.length;$>0;){var _=A[--$];Calendar.removeClass(_,"rowhilite");var D=_.getElementsByTagName("td");for(var C=D.length;C>0;){var B=D[--C];Calendar.removeClass(B,"hilite");Calendar.removeClass(B,"active")}}this.element.style.display="block";this.hidden=false;if(this.isPopup){window.calendar=this;Calendar.addEvent(document,"keydown",Calendar._keyEvent);Calendar.addEvent(document,"keypress",Calendar._keyEvent);Calendar.addEvent(document,"mousedown",Calendar._checkCalendar)}this.hideShowCovered()};Calendar.prototype.hide=function(){if(this.isPopup){Calendar.removeEvent(document,"keydown",Calendar._keyEvent);Calendar.removeEvent(document,"keypress",Calendar._keyEvent);Calendar.removeEvent(document,"mousedown",Calendar._checkCalendar)}this.element.style.display="none";this.hidden=true;this.hideShowCovered()};Calendar.prototype.showAt=function(A,$){var _=this.element.style;_.left=A+"px";_.top=$+"px";this.show()};Calendar.prototype.showAtElement=function(B,$){var E=Calendar.getAbsolutePos(B);if(!$||typeof $!="string"){this.showAt(E.x,E.y+B.offsetHeight);return true}this.show();var _=this.element.offsetWidth,A=this.element.offsetHeight;this.hide();var C=$.substr(0,1),D="l";if($.length>1)D=$.substr(1,1);switch(C){case"T":E.y-=A;break;case"B":E.y+=B.offsetHeight;break;case"C":E.y+=(B.offsetHeight-A)/2;break;case"t":E.y+=B.offsetHeight-A;break;case"b":break}switch(D){case"L":E.x-=_;break;case"R":E.x+=B.offsetWidth;break;case"C":E.x+=(B.offsetWidth-_)/2;break;case"r":E.x+=B.offsetWidth-_;break;case"l":break}this.showAt(E.x,E.y)};Calendar.prototype.setDateFormat=function($){this.dateFormat=$};Calendar.prototype.setTtDateFormat=function($){this.ttDateFormat=$};Calendar.prototype.parseDate=function(I,D){var G=0,B=-1,$=0,C=I.split(/\W+/);if(!D)D=this.dateFormat;var H=D.split(/\W+/),A=0,F=0;for(A=0;A<C.length;++A){if(H[A]=="D"||H[A]=="DD")continue;if(H[A]=="d"||H[A]=="dd")$=parseInt(C[A],10);if(H[A]=="m"||H[A]=="mm")B=parseInt(C[A],10)-1;if((H[A]=="y")||(H[A]=="yy")){G=parseInt(C[A],10);(G<100)&&(G+=(G>29)?1900:2000)}if(H[A]=="M"||H[A]=="MM")for(F=0;F<12;++F)if(Calendar._MN[F].substr(0,C[A].length).toLowerCase()==C[A].toLowerCase()){B=F;break}}if(G!=0&&B!=-1&&$!=0){this.setDate(new Date(G,B,$));return}G=0;B=-1;$=0;for(A=0;A<C.length;++A)if(C[A].search(/[a-zA-Z]+/)!=-1){var E=-1;for(F=0;F<12;++F)if(Calendar._MN[F].substr(0,C[A].length).toLowerCase()==C[A].toLowerCase()){E=F;break}if(E!=-1){if(B!=-1)$=B+1;B=E}}else if(parseInt(C[A],10)<=12&&B==-1)B=C[A]-1;else if(parseInt(C[A],10)>31&&G==0){G=parseInt(C[A],10);(G<100)&&(G+=(G>29)?1900:2000)}else if($==0)$=C[A];if(G==0){var _=new Date();G=_.getFullYear()}if(B!=-1&&$!=0)this.setDate(new Date(G,B,$))};Calendar.prototype.hideShowCovered=function(){function B(_,A){var $=_.style[A];if(!$)if(document.defaultView&&typeof(document.defaultView.getComputedStyle)=="function")$=document.defaultView.getComputedStyle(_,"").getPropertyValue(A);else if(_.currentStyle)$=_.currentStyle[A];else $=_.style[A];return $}var K=new Array("applet","iframe","select"),J=this.element,D=Calendar.getAbsolutePos(J),H=D.x,E=J.offsetWidth+H,L=D.y,I=J.offsetHeight+L;for(var A=K.length;A>0;){var M=document.getElementsByTagName(K[--A]),C=null;for(var G=M.length;G>0;){C=M[--G];D=Calendar.getAbsolutePos(C);var _=D.x,N=C.offsetWidth+_,F=D.y,$=C.offsetHeight+F;if(this.hidden||(_>E)||(N<H)||(F>I)||($<L)){if(!C.__msh_save_visibility)C.__msh_save_visibility=B(C,"visibility");C.style.visibility=C.__msh_save_visibility}else{if(!C.__msh_save_visibility)C.__msh_save_visibility=B(C,"visibility");C.style.visibility="hidden"}}}};Calendar.prototype._displayWeekdays=function(){var A=this.mondayFirst?0:1,$=this.mondayFirst?6:0,B=this.mondayFirst?5:6,C=this.firstdayname;for(var _=0;_<7;++_){C.className="day name";if(!_){C.ttip=this.mondayFirst?Calendar._TT["SUN_FIRST"]:Calendar._TT["MON_FIRST"];C.navtype=100;C.calendar=this;Calendar._add_evs(C)}if(_==$||_==B)Calendar.addClass(C,"weekend");C.firstChild.data=Calendar._DN3[_+1-A];C=C.nextSibling}};Calendar.prototype._hideCombos=function(){this.monthsCombo.style.display="none";this.yearsCombo.style.display="none"};Calendar.prototype._dragStart=function(A){if(this.dragging)return;this.dragging=true;var $,_;if(Calendar.is_ie){_=window.event.clientY+document.body.scrollTop;$=window.event.clientX+document.body.scrollLeft}else{_=A.clientY+window.scrollY;$=A.clientX+window.scrollX}var B=this.element.style;this.xOffs=$-parseInt(B.left);this.yOffs=_-parseInt(B.top);with(Calendar){addEvent(document,"mousemove",calDragIt);addEvent(document,"mouseover",stopEvent);addEvent(document,"mouseup",calDragEnd)}};Date._MD=new Array(31,28,31,30,31,30,31,31,30,31,30,31);Date.SECOND=1000;Date.MINUTE=60*Date.SECOND;Date.HOUR=60*Date.MINUTE;Date.DAY=24*Date.HOUR;Date.WEEK=7*Date.DAY;Date.prototype.getMonthDays=function($){var _=this.getFullYear();if(typeof $=="undefined")$=this.getMonth();if(((0==(_%4))&&((0!=(_%100))||(0==(_%400))))&&$==1)return 29;else return Date._MD[$]};Date.prototype.getWeekNumber=function(){var $=new Date(this.getFullYear(),this.getMonth(),this.getDate(),0,0,0),B=new Date(this.getFullYear(),0,1,0,0,0),A=$-B,_=B.getDay();(_>3)&&(_-=4)||(_+=3);return Math.round(((A/Date.DAY)+_)/7)};Date.prototype.equalsTo=function($){return((this.getFullYear()==$.getFullYear())&&(this.getMonth()==$.getMonth())&&(this.getDate()==$.getDate()))};Date.prototype.print=function(_){var D=new String(_),m=this.getMonth(),$=this.getDate(),C=this.getFullYear(),A=this.getWeekNumber(),w=this.getDay(),s=new Array();s["d"]=$;s["dd"]=($<10)?("0"+$):$;s["m"]=1+m;s["mm"]=(m<9)?("0"+(1+m)):(1+m);s["y"]=C;s["yy"]=new String(C).substr(2,2);s["w"]=A;s["ww"]=(A<10)?("0"+A):A;with(Calendar){s["D"]=_DN3[w];s["DD"]=_DN[w];s["M"]=_MN3[m];s["MM"]=_MN[m]}var B=/(.*)(\W|^)(d|dd|m|mm|y|yy|MM|M|DD|D|w|ww)(\W|$)(.*)/;while(B.exec(D)!=null)D=RegExp.$1+RegExp.$2+s[RegExp.$3]+RegExp.$4+RegExp.$5;return D};window.calendar=null

// ** Translated by ATang ** I18N
Calendar._DN = new Array
("日",
 "一",
 "二",
 "三",
 "四",
 "五",
 "六",
 "日");
Calendar._MN = new Array
("一月",
 "二月",
 "三月",
 "四月",
 "五月",
 "六月",
 "七月",
 "八月",
 "九月",
 "十月",
 "十一月",
 "十二月");

// tooltips
Calendar._TT = {};
Calendar._TT["TOGGLE"] = "切换周开始的一天";
Calendar._TT["PREV_YEAR"] = "上一年 (按住出菜单)";
Calendar._TT["PREV_MONTH"] = "上一月 (按住出菜单)";
Calendar._TT["GO_TODAY"] = "到今日";
Calendar._TT["NEXT_MONTH"] = "下一月 (按住出菜单)";
Calendar._TT["NEXT_YEAR"] = "下一年 (按住出菜单)";
Calendar._TT["SEL_DATE"] = "选择日期";
Calendar._TT["DRAG_TO_MOVE"] = "拖动";
Calendar._TT["PART_TODAY"] = " (今日)";
Calendar._TT["MON_FIRST"] = "首先显示星期一";
Calendar._TT["SUN_FIRST"] = "首先显示星期日";
Calendar._TT["CLOSE"] = "关闭";
Calendar._TT["TODAY"] = "今日";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "y-mm-dd";
Calendar._TT["TT_DATE_FORMAT"] = "D, M d";

Calendar._TT["WK"] = "周";

///////-zh.js over

/////-setup.js
var oldLink = null;
// code to change the active stylesheet
function setActiveStyleSheet(link, title) {
  var i, a, main;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
      a.disabled = true;
      if(a.getAttribute("title") == title) a.disabled = false;
    }
  }
  if (oldLink) oldLink.style.fontWeight = 'normal';
  oldLink = link;
  link.style.fontWeight = 'bold';
  return false;
}

// This function gets called when the end-user clicks on some date.
function selected(cal, date) {
  cal.sel.value = date; // just update the date in the input field.
  if (cal.dateClicked && (cal.sel.id == "sel1" || cal.sel.id == "sel3"))
    // if we add this call we close the calendar on single-click.
    // just to exemplify both cases, we are using this only for the 1st
    // and the 3rd field, while 2nd and 4th will still require double-click.
    cal.callCloseHandler();
}

// And this gets called when the end-user clicks on the _selected_ date,
// or clicks on the "Close" button.  It just hides the calendar without
// destroying it.
function closeHandler(cal) {
  cal.hide();                        // hide the calendar
}

// This function shows the calendar under the element having the given id.
// It takes care of catching "mousedown" signals on document and hiding the
// calendar if the click was outside.
function showCalendar(id, format) {
  var el = document.getElementById(id);
  if (calendar != null) {
    // we already have some calendar created
    calendar.hide();                 // so we hide it first.
  } else {
    // first-time call, create the calendar.
    var cal = new Calendar(false, null, selected, closeHandler);
    // uncomment the following line to hide the week numbers
    // cal.weekNumbers = false;
    calendar = cal;                  // remember it in the global var
    cal.setRange(1900, 2070);        // min/max year allowed.
    cal.create();
  }
  calendar.setDateFormat(format);    // set the specified date format
  calendar.parseDate(el.value);      // try to parse the text in field
  calendar.sel = el;                 // inform it what input field we use

  // the reference element that we pass to showAtElement is the button that
  // triggers the calendar.  In this example we align the calendar bottom-right
  // to the button.
  calendar.showAtElement(el.nextSibling, "Br");        // show the calendar

  return false;
}

var MINUTE = 60 * 1000;
var HOUR = 60 * MINUTE;
var DAY = 24 * HOUR;
var WEEK = 7 * DAY;

// If this handler returns true then the "date" given as
// parameter will be disabled.  In this example we enable
// only days within a range of 10 days from the current
// date.
// You can use the functions date.getFullYear() -- returns the year
// as 4 digit number, date.getMonth() -- returns the month as 0..11,
// and date.getDate() -- returns the date of the month as 1..31, to
// make heavy calculations here.  However, beware that this function
// should be very fast, as it is called for each day in a month when
// the calendar is (re)constructed.
function isDisabled(date) {
  var today = new Date();
  return (Math.abs(date.getTime() - today.getTime()) / DAY) > 10;
}

function flatSelected(cal, date) {
  var el = document.getElementById("preview");
  el.innerHTML = date;
}

function showFlatCalendar() {
  var parent = document.getElementById("display");

  // construct a calendar giving only the "selected" handler.
  var cal = new Calendar(false, null, flatSelected);

  // hide week numbers
  cal.weekNumbers = false;

  // We want some dates to be disabled; see function isDisabled above
  cal.setDisabledHandler(isDisabled);
  cal.setDateFormat("DD, M d");

  // this call must be the last as it might use data initialized above; if
  // we specify a parent, as opposite to the "showCalendar" function above,
  // then we create a flat calendar -- not popup.  Hidden, though, but...
  cal.create(parent);

  // ... we can show it here.
  cal.show();
}