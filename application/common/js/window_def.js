String.prototype.parseColor=function(){var A="#";if(this.slice(0,4)=="rgb("){var _=this.slice(4,this.length-1).split(","),$=0;do A+=parseInt(_[$]).toColorPart();while(++$<3)}else if(this.slice(0,1)=="#"){if(this.length==4)for($=1;$<4;$++)A+=(this.charAt($)+this.charAt($)).toLowerCase();if(this.length==7)A=this.toLowerCase()}return(A.length==7?A:(arguments[0]||this))};Element.collectTextNodes=function(_){return $A($(_).childNodes).collect(function($){return($.nodeType==3?$.nodeValue:($.hasChildNodes()?Element.collectTextNodes($):""))}).flatten().join("")};Element.collectTextNodesIgnoreClass=function(_,A){return $A($(_).childNodes).collect(function($){return($.nodeType==3?$.nodeValue:(($.hasChildNodes()&&!Element.hasClassName($,A))?Element.collectTextNodesIgnoreClass($,A):""))}).flatten().join("")};Element.setContentZoom=function(_,A){_=$(_);_.setStyle({fontSize:(A/100)+"em"});if(Prototype.Browser.WebKit)window.scrollBy(0,0);return _};Element.getInlineOpacity=function(_){return $(_).style.opacity||""};Element.forceRerendering=function(_){try{_=$(_);var A=document.createTextNode(" ");_.appendChild(A);_.removeChild(A)}catch(B){}};Array.prototype.call=function(){var $=arguments;this.each(function(_){_.apply(this,$)})};var Effect={_elementDoesNotExistError:{name:"ElementDoesNotExistError",message:"The specified DOM element does not exist, but is required for this effect to operate"},tagifyText:function(_){if(typeof Builder=="undefined")throw("Effect.tagifyText requires including script.aculo.us' builder.js library");var A="position:relative";if(Prototype.Browser.IE)A+=";zoom:1";_=$(_);$A(_.childNodes).each(function($){if($.nodeType==3){$.nodeValue.toArray().each(function(B){_.insertBefore(Builder.node("span",{style:A},B==" "?String.fromCharCode(160):B),$)});Element.remove($)}})},multiple:function(_,B){var D;if(((typeof _=="object")||(typeof _=="function"))&&(_.length))D=_;else D=$(_).childNodes;var C=Object.extend({speed:0.1,delay:0},arguments[2]||{}),A=C.delay;$A(D).each(function(_,$){new B(_,Object.extend(C,{delay:$*C.speed+A}))})},PAIRS:{"slide":["SlideDown","SlideUp"],"blind":["BlindDown","BlindUp"],"appear":["Appear","Fade"]},toggle:function(_,A){_=$(_);A=(A||"appear").toLowerCase();var B=Object.extend({queue:{position:"end",scope:(_.id||"global"),limit:1}},arguments[2]||{});Effect[_.visible()?Effect.PAIRS[A][1]:Effect.PAIRS[A][0]](_,B)}},Effect2=Effect;Effect.Transitions={linear:Prototype.K,sinoidal:function($){return(-Math.cos($*Math.PI)/2)+0.5},reverse:function($){return 1-$},flicker:function($){var $=((-Math.cos($*Math.PI)/4)+0.75)+Math.random()/4;return($>1?1:$)},wobble:function($){return(-Math.cos($*Math.PI*(9*$))/2)+0.5},pulse:function($,_){_=_||5;return(Math.round(($%(1/_))*_)==0?(($*_*2)-Math.floor($*_*2)):1-(($*_*2)-Math.floor($*_*2)))},none:function($){return 0},full:function($){return 1}};Effect.ScopedQueue=Class.create();Object.extend(Object.extend(Effect.ScopedQueue.prototype,Enumerable),{initialize:function(){this.effects=[];this.interval=null},_each:function($){this.effects._each($)},add:function(_){var A=new Date().getTime(),$=(typeof _.options.queue=="string")?_.options.queue:_.options.queue.position;switch($){case"front":this.effects.findAll(function($){return $.state=="idle"}).each(function($){$.startOn+=_.finishOn;$.finishOn+=_.finishOn});break;case"with-last":A=this.effects.pluck("startOn").max()||A;break;case"end":A=this.effects.pluck("finishOn").max()||A;break}_.startOn+=A;_.finishOn+=A;if(!_.options.queue.limit||(this.effects.length<_.options.queue.limit))this.effects.push(_);if(!this.interval)this.interval=setInterval(this.loop.bind(this),15)},remove:function($){this.effects=this.effects.reject(function(_){return _==$});if(this.effects.length==0){clearInterval(this.interval);this.interval=null}},loop:function(){var A=new Date().getTime();for(var _=0,$=this.effects.length;_<$;_++)this.effects[_]&&this.effects[_].loop(A)}});Effect.Queues={instances:$H(),get:function($){if(typeof $!="string")return $;if(!this.instances[$])this.instances[$]=new Effect.ScopedQueue();return this.instances[$]}};Effect.Queue=Effect.Queues.get("global");Effect.DefaultOptions={transition:Effect.Transitions.sinoidal,duration:1,fps:100,sync:false,from:0,to:1,delay:0,queue:"parallel"};Effect.Base=function(){};Effect.Base.prototype={position:null,start:function(options){function codeForEvent(_,$){return((_[$+"Internal"]?"this.options."+$+"Internal(this);":"")+(_[$]?"this.options."+$+"(this);":""))}if(options.transition===false)options.transition=Effect.Transitions.linear;this.options=Object.extend(Object.extend({},Effect.DefaultOptions),options||{});this.currentFrame=0;this.state="idle";this.startOn=this.options.delay*1000;this.finishOn=this.startOn+(this.options.duration*1000);this.fromToDelta=this.options.to-this.options.from;this.totalTime=this.finishOn-this.startOn;this.totalFrames=this.options.fps*this.options.duration;eval("this.render = function(pos){ "+"if(this.state==\"idle\"){this.state=\"running\";"+codeForEvent(options,"beforeSetup")+(this.setup?"this.setup();":"")+codeForEvent(options,"afterSetup")+"};if(this.state==\"running\"){"+"pos=this.options.transition(pos)*"+this.fromToDelta+"+"+this.options.from+";"+"this.position=pos;"+codeForEvent(options,"beforeUpdate")+(this.update?"this.update(pos);":"")+codeForEvent(options,"afterUpdate")+"}}");this.event("beforeStart");if(!this.options.sync)Effect.Queues.get(typeof this.options.queue=="string"?"global":this.options.queue.scope).add(this)},loop:function(A){if(A>=this.startOn){if(A>=this.finishOn){this.render(1);this.cancel();this.event("beforeFinish");if(this.finish)this.finish();this.event("afterFinish");return}var _=(A-this.startOn)/this.totalTime,$=Math.round(_*this.totalFrames);if($>this.currentFrame){this.render(_);this.currentFrame=$}}},cancel:function(){if(!this.options.sync)Effect.Queues.get(typeof this.options.queue=="string"?"global":this.options.queue.scope).remove(this);this.state="finished"},event:function($){if(this.options[$+"Internal"])this.options[$+"Internal"](this);if(this.options[$])this.options[$](this)},inspect:function(){var $=$H();for(property in this)if(typeof this[property]!="function")$[property]=this[property];return"#<Effect:"+$.inspect()+",options:"+$H(this.options).inspect()+">"}};Effect.Parallel=Class.create();Object.extend(Object.extend(Effect.Parallel.prototype,Effect.Base.prototype),{initialize:function($){this.effects=$||[];this.start(arguments[1])},update:function($){this.effects.invoke("render",$)},finish:function($){this.effects.each(function(_){_.render(1);_.cancel();_.event("beforeFinish");if(_.finish)_.finish($);_.event("afterFinish")})}});Effect.Event=Class.create();Object.extend(Object.extend(Effect.Event.prototype,Effect.Base.prototype),{initialize:function(){var $=Object.extend({duration:0},arguments[0]||{});this.start($)},update:Prototype.emptyFunction});Effect.Opacity=Class.create();Object.extend(Object.extend(Effect.Opacity.prototype,Effect.Base.prototype),{initialize:function(_){this.element=$(_);if(!this.element)throw(Effect._elementDoesNotExistError);if(Prototype.Browser.IE&&(!this.element.currentStyle.hasLayout))this.element.setStyle({zoom:1});var A=Object.extend({from:this.element.getOpacity()||0,to:1},arguments[1]||{});this.start(A)},update:function($){this.element.setOpacity($)}});Effect.Move=Class.create();Object.extend(Object.extend(Effect.Move.prototype,Effect.Base.prototype),{initialize:function(_){this.element=$(_);if(!this.element)throw(Effect._elementDoesNotExistError);var A=Object.extend({x:0,y:0,mode:"relative"},arguments[1]||{});this.start(A)},setup:function(){this.element.makePositioned();this.originalLeft=parseFloat(this.element.getStyle("left")||"0");this.originalTop=parseFloat(this.element.getStyle("top")||"0");if(this.options.mode=="absolute"){this.options.x=this.options.x-this.originalLeft;this.options.y=this.options.y-this.originalTop}},update:function($){this.element.setStyle({left:Math.round(this.options.x*$+this.originalLeft)+"px",top:Math.round(this.options.y*$+this.originalTop)+"px"})}});Effect.MoveBy=function(_,A,$){return new Effect.Move(_,Object.extend({x:$,y:A},arguments[3]||{}))};Effect.Scale=Class.create();Object.extend(Object.extend(Effect.Scale.prototype,Effect.Base.prototype),{initialize:function(_,A){this.element=$(_);if(!this.element)throw(Effect._elementDoesNotExistError);var B=Object.extend({scaleX:true,scaleY:true,scaleContent:true,scaleFromCenter:false,scaleMode:"box",scaleFrom:100,scaleTo:A},arguments[2]||{});this.start(B)},setup:function(){this.restoreAfterFinish=this.options.restoreAfterFinish||false;this.elementPositioning=this.element.getStyle("position");this.originalStyle={};["top","left","width","height","fontSize"].each(function($){this.originalStyle[$]=this.element.style[$]}.bind(this));this.originalTop=this.element.offsetTop;this.originalLeft=this.element.offsetLeft;var $=this.element.getStyle("font-size")||"100%";["em","px","%","pt"].each(function(_){if($.indexOf(_)>0){this.fontSize=parseFloat($);this.fontSizeType=_}}.bind(this));this.factor=(this.options.scaleTo-this.options.scaleFrom)/100;this.dims=null;if(this.options.scaleMode=="box")this.dims=[this.element.offsetHeight,this.element.offsetWidth];if(/^content/.test(this.options.scaleMode))this.dims=[this.element.scrollHeight,this.element.scrollWidth];if(!this.dims)this.dims=[this.options.scaleMode.originalHeight,this.options.scaleMode.originalWidth]},update:function(_){var $=(this.options.scaleFrom/100)+(this.factor*_);if(this.options.scaleContent&&this.fontSize)this.element.setStyle({fontSize:this.fontSize*$+this.fontSizeType});this.setDimensions(this.dims[0]*$,this.dims[1]*$)},finish:function($){if(this.restoreAfterFinish)this.element.setStyle(this.originalStyle)},setDimensions:function(A,_){var $={};if(this.options.scaleX)$.width=Math.round(_)+"px";if(this.options.scaleY)$.height=Math.round(A)+"px";if(this.options.scaleFromCenter){var B=(A-this.dims[0])/2,C=(_-this.dims[1])/2;if(this.elementPositioning=="absolute"){if(this.options.scaleY)$.top=this.originalTop-B+"px";if(this.options.scaleX)$.left=this.originalLeft-C+"px"}else{if(this.options.scaleY)$.top=-B+"px";if(this.options.scaleX)$.left=-C+"px"}}this.element.setStyle($)}});Effect.Highlight=Class.create();Object.extend(Object.extend(Effect.Highlight.prototype,Effect.Base.prototype),{initialize:function(_){this.element=$(_);if(!this.element)throw(Effect._elementDoesNotExistError);var A=Object.extend({startcolor:"#ffff99"},arguments[1]||{});this.start(A)},setup:function(){if(this.element.getStyle("display")=="none"){this.cancel();return}this.oldStyle={};if(!this.options.keepBackgroundImage){this.oldStyle.backgroundImage=this.element.getStyle("background-image");this.element.setStyle({backgroundImage:"none"})}if(!this.options.endcolor)this.options.endcolor=this.element.getStyle("background-color").parseColor("#ffffff");if(!this.options.restorecolor)this.options.restorecolor=this.element.getStyle("background-color");this._base=$R(0,2).map(function($){return parseInt(this.options.startcolor.slice($*2+1,$*2+3),16)}.bind(this));this._delta=$R(0,2).map(function($){return parseInt(this.options.endcolor.slice($*2+1,$*2+3),16)-this._base[$]}.bind(this))},update:function($){this.element.setStyle({backgroundColor:$R(0,2).inject("#",function(A,B,_){return A+(Math.round(this._base[_]+(this._delta[_]*$)).toColorPart())}.bind(this))})},finish:function(){this.element.setStyle(Object.extend(this.oldStyle,{backgroundColor:this.options.restorecolor}))}});Effect.ScrollTo=Class.create();Object.extend(Object.extend(Effect.ScrollTo.prototype,Effect.Base.prototype),{initialize:function(_){this.element=$(_);this.start(arguments[1]||{})},setup:function(){Position.prepare();var $=Position.cumulativeOffset(this.element);if(this.options.offset)$[1]+=this.options.offset;var _=window.innerHeight?window.height-window.innerHeight:document.body.scrollHeight-(document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight);this.scrollStart=Position.deltaY;this.delta=($[1]>_?_:$[1])-this.scrollStart},update:function($){Position.prepare();window.scrollTo(Position.deltaX,this.scrollStart+($*this.delta))}});Effect.Fade=function(_){_=$(_);var A=_.getInlineOpacity(),B=Object.extend({from:_.getOpacity()||1,to:0,afterFinishInternal:function($){if($.options.to!=0)return;$.element.hide().setStyle({opacity:A})}},arguments[1]||{});return new Effect.Opacity(_,B)};Effect.Appear=function(_){_=$(_);var A=Object.extend({from:(_.getStyle("display")=="none"?0:_.getOpacity()||0),to:1,afterFinishInternal:function($){$.element.forceRerendering()},beforeSetup:function($){$.element.setOpacity($.options.from).show()}},arguments[1]||{});return new Effect.Opacity(_,A)};Effect.Puff=function(A){A=$(A);var _={opacity:A.getInlineOpacity(),position:A.getStyle("position"),top:A.style.top,left:A.style.left,width:A.style.width,height:A.style.height};return new Effect.Parallel([new Effect.Scale(A,200,{sync:true,scaleFromCenter:true,scaleContent:true,restoreAfterFinish:true}),new Effect.Opacity(A,{sync:true,to:0})],Object.extend({duration:1,beforeSetupInternal:function($){Position.absolutize($.effects[0].element)},afterFinishInternal:function($){$.effects[0].element.hide().setStyle(_)}},arguments[1]||{}))};Effect.BlindUp=function(_){_=$(_);_.makeClipping();return new Effect.Scale(_,0,Object.extend({scaleContent:false,scaleX:false,restoreAfterFinish:true,afterFinishInternal:function($){$.element.hide().undoClipping()}},arguments[1]||{}))};Effect.BlindDown=function(_){_=$(_);var A=_.getDimensions();return new Effect.Scale(_,100,Object.extend({scaleContent:false,scaleX:false,scaleFrom:0,scaleMode:{originalHeight:A.height,originalWidth:A.width},restoreAfterFinish:true,afterSetup:function($){$.element.makeClipping().setStyle({height:"0px"}).show()},afterFinishInternal:function($){$.element.undoClipping()}},arguments[1]||{}))};Effect.SwitchOff=function(_){_=$(_);var A=_.getInlineOpacity();return new Effect.Appear(_,Object.extend({duration:0.4,from:0,transition:Effect.Transitions.flicker,afterFinishInternal:function($){new Effect.Scale($.element,1,{duration:0.3,scaleFromCenter:true,scaleX:false,scaleContent:false,restoreAfterFinish:true,beforeSetup:function($){$.element.makePositioned().makeClipping()},afterFinishInternal:function($){$.element.hide().undoClipping().undoPositioned().setStyle({opacity:A})}})}},arguments[1]||{}))};Effect.DropOut=function(A){A=$(A);var _={top:A.getStyle("top"),left:A.getStyle("left"),opacity:A.getInlineOpacity()};return new Effect.Parallel([new Effect.Move(A,{x:0,y:100,sync:true}),new Effect.Opacity(A,{sync:true,to:0})],Object.extend({duration:0.5,beforeSetup:function($){$.effects[0].element.makePositioned()},afterFinishInternal:function($){$.effects[0].element.hide().undoPositioned().setStyle(_)}},arguments[1]||{}))};Effect.Shake=function(A){A=$(A);var _={top:A.getStyle("top"),left:A.getStyle("left")};return new Effect.Move(A,{x:20,y:0,duration:0.05,afterFinishInternal:function($){new Effect.Move($.element,{x:-40,y:0,duration:0.1,afterFinishInternal:function($){new Effect.Move($.element,{x:40,y:0,duration:0.1,afterFinishInternal:function($){new Effect.Move($.element,{x:-40,y:0,duration:0.1,afterFinishInternal:function($){new Effect.Move($.element,{x:40,y:0,duration:0.1,afterFinishInternal:function($){new Effect.Move($.element,{x:-20,y:0,duration:0.05,afterFinishInternal:function($){$.element.undoPositioned().setStyle(_)}})}})}})}})}})}})};Effect.SlideDown=function(_){_=$(_).cleanWhitespace();var B=_.down().getStyle("bottom"),A=_.getDimensions();return new Effect.Scale(_,100,Object.extend({scaleContent:false,scaleX:false,scaleFrom:window.opera?0:1,scaleMode:{originalHeight:A.height,originalWidth:A.width},restoreAfterFinish:true,afterSetup:function($){$.element.makePositioned();$.element.down().makePositioned();if(window.opera)$.element.setStyle({top:""});$.element.makeClipping().setStyle({height:"0px"}).show()},afterUpdateInternal:function($){$.element.down().setStyle({bottom:($.dims[0]-$.element.clientHeight)+"px"})},afterFinishInternal:function($){$.element.undoClipping().undoPositioned();$.element.down().undoPositioned().setStyle({bottom:B})}},arguments[1]||{}))};Effect.SlideUp=function(_){_=$(_).cleanWhitespace();var A=_.down().getStyle("bottom");return new Effect.Scale(_,window.opera?0:1,Object.extend({scaleContent:false,scaleX:false,scaleMode:"box",scaleFrom:100,restoreAfterFinish:true,beforeStartInternal:function($){$.element.makePositioned();$.element.down().makePositioned();if(window.opera)$.element.setStyle({top:""});$.element.makeClipping().show()},afterUpdateInternal:function($){$.element.down().setStyle({bottom:($.dims[0]-$.element.clientHeight)+"px"})},afterFinishInternal:function($){$.element.hide().undoClipping().undoPositioned().setStyle({bottom:A});$.element.down().undoPositioned()}},arguments[1]||{}))};Effect.Squish=function($){return new Effect.Scale($,window.opera?1:0,{restoreAfterFinish:true,beforeSetup:function($){$.element.makeClipping()},afterFinishInternal:function($){$.element.hide().undoClipping()}})};Effect.Grow=function(A){A=$(A);var G=Object.extend({direction:"center",moveTransition:Effect.Transitions.sinoidal,scaleTransition:Effect.Transitions.sinoidal,opacityTransition:Effect.Transitions.full},arguments[1]||{}),_={top:A.style.top,left:A.style.left,height:A.style.height,width:A.style.width,opacity:A.getInlineOpacity()},E=A.getDimensions(),F,B,C,D;switch(G.direction){case"top-left":F=B=C=D=0;break;case"top-right":F=E.width;B=D=0;C=-E.width;break;case"bottom-left":F=C=0;B=E.height;D=-E.height;break;case"bottom-right":F=E.width;B=E.height;C=-E.width;D=-E.height;break;case"center":F=E.width/2;B=E.height/2;C=-E.width/2;D=-E.height/2;break}return new Effect.Move(A,{x:F,y:B,duration:0.01,beforeSetup:function($){$.element.hide().makeClipping().makePositioned()},afterFinishInternal:function($){new Effect.Parallel([new Effect.Opacity($.element,{sync:true,to:1,from:0,transition:G.opacityTransition}),new Effect.Move($.element,{x:C,y:D,sync:true,transition:G.moveTransition}),new Effect.Scale($.element,100,{scaleMode:{originalHeight:E.height,originalWidth:E.width},sync:true,scaleFrom:window.opera?1:0,transition:G.scaleTransition,restoreAfterFinish:true})],Object.extend({beforeSetup:function($){$.effects[0].element.setStyle({height:"0px"}).show()},afterFinishInternal:function($){$.effects[0].element.undoClipping().undoPositioned().setStyle(_)}},G))}})};Effect.Shrink=function(A){A=$(A);var E=Object.extend({direction:"center",moveTransition:Effect.Transitions.sinoidal,scaleTransition:Effect.Transitions.sinoidal,opacityTransition:Effect.Transitions.none},arguments[1]||{}),_={top:A.style.top,left:A.style.left,height:A.style.height,width:A.style.width,opacity:A.getInlineOpacity()},D=A.getDimensions(),B,C;switch(E.direction){case"top-left":B=C=0;break;case"top-right":B=D.width;C=0;break;case"bottom-left":B=0;C=D.height;break;case"bottom-right":B=D.width;C=D.height;break;case"center":B=D.width/2;C=D.height/2;break}return new Effect.Parallel([new Effect.Opacity(A,{sync:true,to:0,from:1,transition:E.opacityTransition}),new Effect.Scale(A,window.opera?1:0,{sync:true,transition:E.scaleTransition,restoreAfterFinish:true}),new Effect.Move(A,{x:B,y:C,sync:true,transition:E.moveTransition})],Object.extend({beforeStartInternal:function($){$.effects[0].element.makePositioned().makeClipping()},afterFinishInternal:function($){$.effects[0].element.hide().undoClipping().undoPositioned().setStyle(_)}},E))};Effect.Pulsate=function(_){_=$(_);var D=arguments[1]||{},A=_.getInlineOpacity(),B=D.transition||Effect.Transitions.sinoidal,C=function($){return B(1-Effect.Transitions.pulse($,D.pulses))};C.bind(B);return new Effect.Opacity(_,Object.extend(Object.extend({duration:2,from:0,afterFinishInternal:function($){$.element.setStyle({opacity:A})}},D),{transition:C}))};Effect.Fold=function(A){A=$(A);var _={top:A.style.top,left:A.style.left,width:A.style.width,height:A.style.height};A.makeClipping();return new Effect.Scale(A,5,Object.extend({scaleContent:false,scaleX:false,afterFinishInternal:function($){new Effect.Scale(A,1,{scaleContent:false,scaleY:false,afterFinishInternal:function($){$.element.hide().undoClipping().setStyle(_)}})}},arguments[1]||{}))};Effect.Morph=Class.create();Object.extend(Object.extend(Effect.Morph.prototype,Effect.Base.prototype),{initialize:function(_){this.element=$(_);if(!this.element)throw(Effect._elementDoesNotExistError);var C=Object.extend({style:{}},arguments[1]||{});if(typeof C.style=="string"){if(C.style.indexOf(":")==-1){var B="",A="."+C.style;$A(document.styleSheets).reverse().each(function($){if($.cssRules)cssRules=$.cssRules;else if($.rules)cssRules=$.rules;$A(cssRules).reverse().each(function($){if(A==$.selectorText){B=$.style.cssText;throw $break}});if(B)throw $break});this.style=B.parseStyle();C.afterFinishInternal=function($){$.element.addClassName($.options.style);$.transforms.each(function(_){if(_.style!="opacity")$.element.style[_.style]=""})}}else this.style=C.style.parseStyle()}else this.style=$H(C.style);this.start(C)},setup:function(){function $($){if(!$||["rgba(0, 0, 0, 0)","transparent"].include($))$="#ffffff";$=$.parseColor();return $R(0,2).map(function(_){return parseInt($.slice(_*2+1,_*2+3),16)})}this.transforms=this.style.map(function(C){var D=C[0],A=C[1],B=null;if(A.parseColor("#zzzzzz")!="#zzzzzz"){A=A.parseColor();B="color"}else if(D=="opacity"){A=parseFloat(A);if(Prototype.Browser.IE&&(!this.element.currentStyle.hasLayout))this.element.setStyle({zoom:1})}else if(Element.CSS_LENGTH.test(A)){var _=A.match(/^([\+\-]?[0-9\.]+)(.*)$/);A=parseFloat(_[1]);B=(_.length==3)?_[2]:null}var E=this.element.getStyle(D);return{style:D.camelize(),originalValue:B=="color"?$(E):parseFloat(E||0),targetValue:B=="color"?$(A):A,unit:B}}.bind(this)).reject(function($){return(($.originalValue==$.targetValue)||($.unit!="color"&&(isNaN($.originalValue)||isNaN($.targetValue))))})},update:function(A){var B={},_,$=this.transforms.length;while($--)B[(_=this.transforms[$]).style]=_.unit=="color"?"#"+(Math.round(_.originalValue[0]+(_.targetValue[0]-_.originalValue[0])*A)).toColorPart()+(Math.round(_.originalValue[1]+(_.targetValue[1]-_.originalValue[1])*A)).toColorPart()+(Math.round(_.originalValue[2]+(_.targetValue[2]-_.originalValue[2])*A)).toColorPart():_.originalValue+Math.round(((_.targetValue-_.originalValue)*A)*1000)/1000+_.unit;this.element.setStyle(B,true)}});Effect.Transform=Class.create();Object.extend(Effect.Transform.prototype,{initialize:function($){this.tracks=[];this.options=arguments[1]||{};this.addTracks($)},addTracks:function($){$.each(function($){var _=$H($).values().first();this.tracks.push($H({ids:$H($).keys().first(),effect:Effect.Morph,options:{style:_}}))}.bind(this));return this},play:function(){return new Effect.Parallel(this.tracks.map(function(_){var A=[$(_.ids)||$$(_.ids)].flatten();return A.map(function($){return new _.effect($,Object.extend({sync:true},_.options))})}).flatten(),this.options)}});Element.CSS_PROPERTIES=$w("backgroundColor backgroundPosition borderBottomColor borderBottomStyle "+"borderBottomWidth borderLeftColor borderLeftStyle borderLeftWidth "+"borderRightColor borderRightStyle borderRightWidth borderSpacing "+"borderTopColor borderTopStyle borderTopWidth bottom clip color "+"fontSize fontWeight height left letterSpacing lineHeight "+"marginBottom marginLeft marginRight marginTop markerOffset maxHeight "+"maxWidth minHeight minWidth opacity outlineColor outlineOffset "+"outlineWidth paddingBottom paddingLeft paddingRight paddingTop "+"right textIndent top width wordSpacing zIndex");Element.CSS_LENGTH=/^(([\+\-]?[0-9\.]+)(em|ex|px|in|cm|mm|pt|pc|\%))|0$/;String.prototype.parseStyle=function(){var $=document.createElement("div");$.innerHTML="<div style=\""+this+"\"></div>";var A=$.childNodes[0].style,_=$H();Element.CSS_PROPERTIES.each(function($){if(A[$])_[$]=A[$]});if(Prototype.Browser.IE&&this.indexOf("opacity")>-1)_.opacity=this.match(/opacity:\s*((?:0|1)?(?:\.\d*)?)/)[1];return _};Element.morph=function($,_){new Effect.Morph($,Object.extend({style:_},arguments[2]||{}));return $};["getInlineOpacity","forceRerendering","setContentZoom","collectTextNodes","collectTextNodesIgnoreClass","morph"].each(function($){Element.Methods[$]=Element[$]});Element.Methods.visualEffect=function(_,A,B){s=A.dasherize().camelize();effect_class=s.charAt(0).toUpperCase()+s.substring(1);new Effect[effect_class](_,B);return $(_)};Element.addMethods()

var Window=Class.create();
Window.keepMultiModalWindow=false;
Window.hasEffectLib=(typeof Effect!='undefined');
Window.resizeEffectDuration=0.4;
Window.prototype={
initialize:function(){
var id;
var optionIndex=0;
if(arguments.length>0){
if(typeof arguments[0]=="string"){
id=arguments[0];
optionIndex=1;}
else
id=arguments[0]?arguments[0].id:null;}
if(!id)
id="window_"+new Date().getTime();
if($(id))
alert("Window "+id+" is already registered in the DOM! Make sure you use setDestroyOnClose() or destroyOnClose: true in the constructor");
this.options=Object.extend({
className:"dialog",
blurClassName:null,
minWidth:100,
minHeight:20,
resizable:true,
closable:true,
minimizable:true,
maximizable:true,
draggable:true,
userData:null,
showEffect:(Window.hasEffectLib?Effect.Appear:Element.show),
hideEffect:(Window.hasEffectLib?Effect.Fade:Element.hide),
showEffectOptions:{},
hideEffectOptions:{},
effectOptions:null,
parent:document.body,
title:"&nbsp;",
url:null,
onload:Prototype.emptyFunction,
width:200,
height:300,
opacity:1,
recenterAuto:true,
wiredDrag:false,
closeCallback:null,
destroyOnClose:false,
gridX:1,
gridY:1},arguments[optionIndex]||{});
if(this.options.blurClassName)
this.options.focusClassName=this.options.className;
if(typeof this.options.top=="undefined"&&typeof this.options.bottom=="undefined")
this.options.top=this._round(Math.random()*500,this.options.gridY);
if(typeof this.options.left=="undefined"&&typeof this.options.right=="undefined")
this.options.left=this._round(Math.random()*500,this.options.gridX);
if(this.options.effectOptions){
Object.extend(this.options.hideEffectOptions,this.options.effectOptions);
Object.extend(this.options.showEffectOptions,this.options.effectOptions);
if(this.options.showEffect==Element.Appear)
this.options.showEffectOptions.to=this.options.opacity;}
if(Window.hasEffectLib){
if(this.options.showEffect==Effect.Appear)
this.options.showEffectOptions.to=this.options.opacity;
if(this.options.hideEffect==Effect.Fade)
this.options.hideEffectOptions.from=this.options.opacity;}
if(this.options.hideEffect==Element.hide)
this.options.hideEffect=function(){Element.hide(this.element);if(this.options.destroyOnClose)this.destroy();}.bind(this)
if(this.options.parent!=document.body)
this.options.parent=$(this.options.parent);
this.element=this._createWindow(id);
this.element.win=this;
this.eventMouseDown=this._initDrag.bindAsEventListener(this);
this.eventMouseUp=this._endDrag.bindAsEventListener(this);
this.eventMouseMove=this._updateDrag.bindAsEventListener(this);
this.eventOnLoad=this._getWindowBorderSize.bindAsEventListener(this);
this.eventMouseDownContent=this.toFront.bindAsEventListener(this);
this.eventResize=this._recenter.bindAsEventListener(this);
this.topbar=$(this.element.id+"_top");
this.bottombar=$(this.element.id+"_bottom");
this.content=$(this.element.id+"_content");
Event.observe(this.topbar,"mousedown",this.eventMouseDown);
Event.observe(this.bottombar,"mousedown",this.eventMouseDown);
Event.observe(this.content,"mousedown",this.eventMouseDownContent);
Event.observe(window,"load",this.eventOnLoad);
Event.observe(window,"resize",this.eventResize);
Event.observe(window,"scroll",this.eventResize);
Event.observe(this.options.parent,"scroll",this.eventResize);
if(this.options.draggable){
var that=this;[this.topbar,this.topbar.up().previous(),this.topbar.up().next()].each(function(element){
element.observe("mousedown",that.eventMouseDown);
element.addClassName("top_draggable");});[this.bottombar.up(),this.bottombar.up().previous(),this.bottombar.up().next()].each(function(element){
element.observe("mousedown",that.eventMouseDown);
element.addClassName("bottom_draggable");});}
if(this.options.resizable){
this.sizer=$(this.element.id+"_sizer");
Event.observe(this.sizer,"mousedown",this.eventMouseDown);}
this.useLeft=null;
this.useTop=null;
if(typeof this.options.left!="undefined"){
this.element.setStyle({left:parseFloat(this.options.left)+'px'});
this.useLeft=true;}
else{
this.element.setStyle({right:parseFloat(this.options.right)+'px'});
this.useLeft=false;}
if(typeof this.options.top!="undefined"){
this.element.setStyle({top:parseFloat(this.options.top)+'px'});
this.useTop=true;}
else{
this.element.setStyle({bottom:parseFloat(this.options.bottom)+'px'});
this.useTop=false;}
this.storedLocation=null;
this.setOpacity(this.options.opacity);
if(this.options.zIndex)
this.setZIndex(this.options.zIndex)
if(this.options.destroyOnClose)
this.setDestroyOnClose(true);
this._getWindowBorderSize();
this.width=this.options.width;
this.height=this.options.height;
this.visible=false;
this.constraint=false;
this.constraintPad={top:0,left:0,bottom:0,right:0};
if(this.width&&this.height)
this.setSize(this.options.width,this.options.height);
this.setTitle(this.options.title)
Windows.register(this);},
destroy:function(){
this._notify("onDestroy");
Event.stopObserving(this.topbar,"mousedown",this.eventMouseDown);
Event.stopObserving(this.bottombar,"mousedown",this.eventMouseDown);
Event.stopObserving(this.content,"mousedown",this.eventMouseDownContent);
Event.stopObserving(window,"load",this.eventOnLoad);
Event.stopObserving(window,"resize",this.eventResize);
Event.stopObserving(window,"scroll",this.eventResize);
Event.stopObserving(this.content,"load",this.options.onload);
if(this._oldParent){
var content=this.getContent();
var originalContent=null;
for(var i=0;i<content.childNodes.length;i++){
originalContent=content.childNodes[i];
if(originalContent.nodeType==1)
break;
originalContent=null;}
if(originalContent)
this._oldParent.appendChild(originalContent);
this._oldParent=null;}
if(this.sizer)
Event.stopObserving(this.sizer,"mousedown",this.eventMouseDown);
if(this.options.url)
this.content.src=null
if(this.iefix)
Element.remove(this.iefix);
Element.remove(this.element);
Windows.unregister(this);},
setCloseCallback:function(callback){
this.options.closeCallback=callback;},
getContent:function(){
return this.content;},
setContent:function(id,autoresize,autoposition){
var element=$(id);
if(null==element)throw "Unable to find element '"+id+"' in DOM";
this._oldParent=element.parentNode;
var d=null;
var p=null;
if(autoresize)
d=Element.getDimensions(element);
if(autoposition)
p=Position.cumulativeOffset(element);
var content=this.getContent();
this.setHTMLContent("");
content=this.getContent();
content.appendChild(element);
element.show();
if(autoresize)
this.setSize(d.width,d.height);
if(autoposition)
this.setLocation(p[1]-this.heightN,p[0]-this.widthW);},
setHTMLContent:function(html){
if(this.options.url){
this.content.src=null;
this.options.url=null;
var content="<div id=\"" + this.getId() + "_content\" class=\"" + this.options.className + "_content\"> </div>";
$(this.getId()+"_table_content").innerHTML=content;
this.content=$(this.element.id+"_content");}
this.getContent().innerHTML=html;},
setAjaxContent:function(url,options,showCentered,showModal){
this.showFunction=showCentered?"showCenter":"show";
this.showModal=showModal||false;
options=options||{};
this.setHTMLContent("");
this.onComplete=options.onComplete;
if(!this._onCompleteHandler)
this._onCompleteHandler=this._setAjaxContent.bind(this);
options.onComplete=this._onCompleteHandler;
new Ajax.Request(url,options);
options.onComplete=this.onComplete;},
_setAjaxContent:function(originalRequest){
Element.update(this.getContent(),originalRequest.responseText);
if(this.onComplete)
this.onComplete(originalRequest);
this.onComplete=null;
this[this.showFunction](this.showModal)},
setURL:function(url){
if(this.options.url)
this.content.src=null;
this.options.url=url;
var content="<iframe frameborder='0' name='"+this.getId()+"_content'  id='"+this.getId()+"_content' src='"+url+"' width='"+this.width+"' height='"+this.height+"'> </iframe>";
$(this.getId()+"_table_content").innerHTML=content;
this.content=$(this.element.id+"_content");},
getURL:function(){
return this.options.url?this.options.url:null;},
refresh:function(){
if(this.options.url)
$(this.element.getAttribute('id')+'_content').src=this.options.url;},
setCookie:function(name,expires,path,domain,secure){
name=name||this.element.id;
this.cookie=[name,expires,path,domain,secure];
var value=WindowUtilities.getCookie(name)
if(value){
var values=value.split(',');
var x=values[0].split(':');
var y=values[1].split(':');
var w=parseFloat(values[2]),h=parseFloat(values[3]);
var mini=values[4];
var maxi=values[5];
this.setSize(w,h);
if(mini=="true")
this.doMinimize=true;
else if(maxi=="true")
this.doMaximize=true;
this.useLeft=x[0]=="l";
this.useTop=y[0]=="t";
this.element.setStyle(this.useLeft?{left:x[1]}:{right:x[1]});
this.element.setStyle(this.useTop?{top:y[1]}:{bottom:y[1]});}},
getId:function(){
return this.element.id;},
setDestroyOnClose:function(){
this.options.destroyOnClose=true;},
setConstraint:function(bool,padding){
this.constraint=bool;
this.constraintPad=Object.extend(this.constraintPad,padding||{});
if(this.useTop&&this.useLeft)
this.setLocation(parseFloat(this.element.style.top),parseFloat(this.element.style.left));},
_initDrag:function(event){
if(Event.element(event)==this.sizer&&this.isMinimized())
return;
if(Event.element(event)!=this.sizer&&this.isMaximized())
return;
if(Prototype.Browser.IE&&this.heightN==0)
this._getWindowBorderSize();
this.pointer=[this._round(Event.pointerX(event),this.options.gridX),this._round(Event.pointerY(event),this.options.gridY)];
if(this.options.wiredDrag)
this.currentDrag=this._createWiredElement();
else
this.currentDrag=this.element;
if(Event.element(event)==this.sizer){
this.doResize=true;
this.widthOrg=this.width;
this.heightOrg=this.height;
this.bottomOrg=parseFloat(this.element.getStyle('bottom'));
this.rightOrg=parseFloat(this.element.getStyle('right'));
this._notify("onStartResize");}
else{
this.doResize=false;
var closeButton=$(this.getId()+'_close');
if(closeButton&&Position.within(closeButton,this.pointer[0],this.pointer[1])){
this.currentDrag=null;
return;}
this.toFront();
if(!this.options.draggable)
return;
this._notify("onStartMove");}
Event.observe(document,"mouseup",this.eventMouseUp,false);
Event.observe(document,"mousemove",this.eventMouseMove,false);
WindowUtilities.disableScreen('__invisible__','__invisible__',this.overlayOpacity);
document.body.ondrag=function(){return false;};
document.body.onselectstart=function(){return false;};
this.currentDrag.show();
Event.stop(event);},
_round:function(val,round){
return round==1?val:val=Math.floor(val/round)*round;},
_updateDrag:function(event){
var pointer=[this._round(Event.pointerX(event),this.options.gridX),this._round(Event.pointerY(event),this.options.gridY)];
var dx=pointer[0]-this.pointer[0];
var dy=pointer[1]-this.pointer[1];
if(this.doResize){
var w=this.widthOrg+dx;
var h=this.heightOrg+dy;
dx=this.width-this.widthOrg
dy=this.height-this.heightOrg
if(this.useLeft)
w=this._updateWidthConstraint(w)
else
this.currentDrag.setStyle({right:(this.rightOrg-dx)+'px'});
if(this.useTop)
h=this._updateHeightConstraint(h)
else
this.currentDrag.setStyle({bottom:(this.bottomOrg-dy)+'px'});
this.setSize(w,h);
this._notify("onResize");}
else{
this.pointer=pointer;
if(this.useLeft){
var left=parseFloat(this.currentDrag.getStyle('left'))+dx;
var newLeft=this._updateLeftConstraint(left);
this.pointer[0]+=newLeft-left;
this.currentDrag.setStyle({left:newLeft+'px'});}
else
this.currentDrag.setStyle({right:parseFloat(this.currentDrag.getStyle('right'))-dx+'px'});
if(this.useTop){
var top=parseFloat(this.currentDrag.getStyle('top'))+dy;
var newTop=this._updateTopConstraint(top);
this.pointer[1]+=newTop-top;
this.currentDrag.setStyle({top:newTop+'px'});}
else
this.currentDrag.setStyle({bottom:parseFloat(this.currentDrag.getStyle('bottom'))-dy+'px'});
this._notify("onMove");}
if(this.iefix)
this._fixIEOverlapping();
this._removeStoreLocation();
Event.stop(event);},
_endDrag:function(event){
WindowUtilities.enableScreen('__invisible__');
if(this.doResize)
this._notify("onEndResize");
else
this._notify("onEndMove");
Event.stopObserving(document,"mouseup",this.eventMouseUp,false);
Event.stopObserving(document,"mousemove",this.eventMouseMove,false);
Event.stop(event);
this._hideWiredElement();
this._saveCookie()
document.body.ondrag=null;
document.body.onselectstart=null;},
_updateLeftConstraint:function(left){
if(this.constraint&&this.useLeft&&this.useTop){
var width=this.options.parent==document.body?WindowUtilities.getPageSize().windowWidth:this.options.parent.getDimensions().width;
if(left<this.constraintPad.left)
left=this.constraintPad.left;
if(left+this.width+this.widthE+this.widthW>width-this.constraintPad.right)
left=width-this.constraintPad.right-this.width-this.widthE-this.widthW;}
return left;},
_updateTopConstraint:function(top){
if(this.constraint&&this.useLeft&&this.useTop){
var height=this.options.parent==document.body?WindowUtilities.getPageSize().windowHeight:this.options.parent.getDimensions().height;
var h=this.height+this.heightN+this.heightS;
if(top<this.constraintPad.top)
top=this.constraintPad.top;
if(top+h>height-this.constraintPad.bottom)
top=height-this.constraintPad.bottom-h;}
return top;},
_updateWidthConstraint:function(w){
if(this.constraint&&this.useLeft&&this.useTop){
var width=this.options.parent==document.body?WindowUtilities.getPageSize().windowWidth:this.options.parent.getDimensions().width;
var left=parseFloat(this.element.getStyle("left"));
if(left+w+this.widthE+this.widthW>width-this.constraintPad.right)
w=width-this.constraintPad.right-left-this.widthE-this.widthW;}
return w;},
_updateHeightConstraint:function(h){
if(this.constraint&&this.useLeft&&this.useTop){
var height=this.options.parent==document.body?WindowUtilities.getPageSize().windowHeight:this.options.parent.getDimensions().height;
var top=parseFloat(this.element.getStyle("top"));
if(top+h+this.heightN+this.heightS>height-this.constraintPad.bottom)
h=height-this.constraintPad.bottom-top-this.heightN-this.heightS;}
return h;},
_createWindow:function(id){
var className=this.options.className;
var win=document.createElement("div");
win.setAttribute('id',id);
win.className="dialog";
var content;
if(this.options.url)
content="<iframe frameborder=\"0\" name=\"" + id + "_content\"  id=\"" + id + "_content\" src=\"" + this.options.url + "\"> </iframe>";
else
content="<div id=\"" + id + "_content\" class=\"" +className + "_content\"> </div>";
var closeDiv=this.options.closable?"<div class='"+className+"_close' id='"+id+"_close' onclick='Windows.close(\""+ id +"\", event)'> </div>":"";
var minDiv=this.options.minimizable?"<div class='"+className+"_minimize' id='"+id+"_minimize' onclick='Windows.minimize(\""+ id +"\", event)'> </div>":"";
var maxDiv=this.options.maximizable?"<div class='"+className+"_maximize' id='"+id+"_maximize' onclick='Windows.maximize(\""+ id +"\", event)'> </div>":"";
var seAttributes=this.options.resizable?"class='"+className+"_sizer' id='"+id+"_sizer'":"class='"+className+"_se'";
var blank="../themes/default/blank.gif";
win.innerHTML=closeDiv+minDiv+maxDiv+"\<table id='"+id +"_row1' class=\"top table_window\">\<tr>\<td class='"+className +"_nw'></td>\<td class='"+className +"_n'><div id='"+id +"_top' class='"+className +"_title title_window'>"+this.options.title +"</div></td>\<td class='"+className +"_ne'></td>\</tr>\</table>\<table id='"+id +"_row2' class=\"mid table_window\">\<tr>\<td class='"+className +"_w'></td>\<td id='"+id +"_table_content' class='"+className +"_content' valign='top'>" +content +"</td>\<td class='"+className +"_e'></td>\</tr>\</table>\<table id='"+id +"_row3' class=\"bot table_window\">\<tr>\<td class='"+className +"_sw'></td>\<td class='"+className +"_s'><div id='"+id +"_bottom' class='status_bar'><span style='float:left; width:1px; height:1px'></span></div></td>\<td " +seAttributes +"></td>\</tr>\</table>\
";
Element.hide(win);
this.options.parent.insertBefore(win,this.options.parent.firstChild);
Event.observe($(id+"_content"),"load",this.options.onload);
return win;},
changeClassName:function(newClassName){
var className=this.options.className;
var id=this.getId();
$A(["_close","_minimize","_maximize","_sizer","_content"]).each(function(value){this._toggleClassName($(id+value),className+value,newClassName+value)}.bind(this));
this._toggleClassName($(id+"_top"),className+"_title",newClassName+"_title");
$$("#"+id+" td").each(function(td){td.className=td.className.sub(className,newClassName);});
this.options.className=newClassName;},
_toggleClassName:function(element,oldClassName,newClassName){
if(element){
element.removeClassName(oldClassName);
element.addClassName(newClassName);}},
setLocation:function(top,left){
top=this._updateTopConstraint(top);
left=this._updateLeftConstraint(left);
var e=this.currentDrag||this.element;
e.setStyle({top:top+'px'});
e.setStyle({left:left+'px'});
this.useLeft=true;
this.useTop=true;},
getLocation:function(){
var location={};
if(this.useTop)
location=Object.extend(location,{top:this.element.getStyle("top")});
else
location=Object.extend(location,{bottom:this.element.getStyle("bottom")});
if(this.useLeft)
location=Object.extend(location,{left:this.element.getStyle("left")});
else
location=Object.extend(location,{right:this.element.getStyle("right")});
return location;},
getSize:function(){
return{width:this.width,height:this.height};},
setSize:function(width,height,useEffect){
width=parseFloat(width);
height=parseFloat(height);
if(!this.minimized&&width<this.options.minWidth)
width=this.options.minWidth;
if(!this.minimized&&height<this.options.minHeight)
height=this.options.minHeight;
if(this.options. maxHeight&&height>this.options. maxHeight)
height=this.options. maxHeight;
if(this.options. maxWidth&&width>this.options. maxWidth)
width=this.options. maxWidth;
if(this.useTop&&this.useLeft&&Window.hasEffectLib&&Effect.ResizeWindow&&useEffect){
new Effect.ResizeWindow(this,null,null,width,height,{duration:Window.resizeEffectDuration});}else{
this.width=width;
this.height=height;
var e=this.currentDrag?this.currentDrag:this.element;
e.setStyle({width:width+this.widthW+this.widthE+"px"})
e.setStyle({height:height+this.heightN+this.heightS+"px"})
if(!this.currentDrag||this.currentDrag==this.element){
var content=$(this.element.id+'_content');
content.setStyle({height:height+'px'});
content.setStyle({width:width+'px'});}}},
updateHeight:function(){
this.setSize(this.width,this.content.scrollHeight,true);},
updateWidth:function(){
this.setSize(this.content.scrollWidth,this.height,true);},
toFront:function(){
if(this.element.style.zIndex<Windows.maxZIndex)
this.setZIndex(Windows.maxZIndex+1);
if(this.iefix)
this._fixIEOverlapping();},
getBounds:function(insideOnly){
if(!this.width||!this.height||!this.visible)
this.computeBounds();
var w=this.width;
var h=this.height;
if(!insideOnly){
w+=this.widthW+this.widthE;
h+=this.heightN+this.heightS;}
var bounds=Object.extend(this.getLocation(),{width:w+"px",height:h+"px"});
return bounds;},
computeBounds:function(){
if(!this.width||!this.height){
var size=WindowUtilities._computeSize(this.content.innerHTML,this.content.id,this.width,this.height,0,this.options.className)
if(this.height)
this.width=size+5
else
this.height=size+5}
this.setSize(this.width,this.height);
if(this.centered)
this._center(this.centerTop,this.centerLeft);},
show:function(modal){
this.visible=true;
if(modal){
if(typeof this.overlayOpacity=="undefined"){
var that=this;
setTimeout(function(){that.show(modal)},10);
return;}
Windows.addModalWindow(this);
this.modal=true;
this.setZIndex(Windows.maxZIndex+1);
Windows.unsetOverflow(this);}
else
if(!this.element.style.zIndex)
this.setZIndex(Windows.maxZIndex+1);
if(this.oldStyle)
this.getContent().setStyle({overflow:this.oldStyle});
this.computeBounds();
this._notify("onBeforeShow");
if(this.options.showEffect!=Element.show&&this.options.showEffectOptions)
this.options.showEffect(this.element,this.options.showEffectOptions);
else
this.options.showEffect(this.element);
this._checkIEOverlapping();
WindowUtilities.focusedWindow=this
this._notify("onShow");},
showCenter:function(modal,top,left){
this.centered=true;
this.centerTop=top;
this.centerLeft=left;
this.show(modal);},
isVisible:function(){
return this.visible;},
_center:function(top,left){
var windowScroll=WindowUtilities.getWindowScroll(this.options.parent);
var pageSize=WindowUtilities.getPageSize(this.options.parent);
if(typeof top=="undefined")
top=(pageSize.windowHeight-(this.height+this.heightN+this.heightS))/2;
top+=windowScroll.top
if(typeof left=="undefined")
left=(pageSize.windowWidth-(this.width+this.widthW+this.widthE))/2;
left+=windowScroll.left
this.setLocation(top,left);
this.toFront();},
_recenter:function(event){
if(this.centered){
var pageSize=WindowUtilities.getPageSize(this.options.parent);
var windowScroll=WindowUtilities.getWindowScroll(this.options.parent);
if(this.pageSize&&this.pageSize.windowWidth==pageSize.windowWidth&&this.pageSize.windowHeight==pageSize.windowHeight&&
this.windowScroll.left==windowScroll.left&&this.windowScroll.top==windowScroll.top)
return;
this.pageSize=pageSize;
this.windowScroll=windowScroll;
if($('overlay_modal'))
$('overlay_modal').setStyle({height:(pageSize.pageHeight+'px')});
if(this.options.recenterAuto)
this._center(this.centerTop,this.centerLeft);}},
hide:function(){
this.visible=false;
if(this.modal){
Windows.removeModalWindow(this);
Windows.resetOverflow();}
this.oldStyle=this.getContent().getStyle('overflow')||"auto"
this.getContent().setStyle({overflow:"hidden"});
this.options.hideEffect(this.element,this.options.hideEffectOptions);
if(this.iefix)
this.iefix.hide();
if(!this.doNotNotifyHide)
this._notify("onHide");},
close:function(){
if(this.visible){
if(this.options.closeCallback&&!this.options.closeCallback(this))
return;
if(this.options.destroyOnClose){
var destroyFunc=this.destroy.bind(this);
if(this.options.hideEffectOptions.afterFinish){
var func=this.options.hideEffectOptions.afterFinish;
this.options.hideEffectOptions.afterFinish=function(){func();destroyFunc()}}
else
this.options.hideEffectOptions.afterFinish=function(){destroyFunc()}}
Windows.updateFocusedWindow();
this.doNotNotifyHide=true;
this.hide();
this.doNotNotifyHide=false;
this._notify("onClose");}},
minimize:function(){
if(this.resizing)
return;
var r2=$(this.getId()+"_row2");
if(!this.minimized){
this.minimized=true;
var dh=r2.getDimensions().height;
this.r2Height=dh;
var h=this.element.getHeight()-dh;
if(this.useLeft&&this.useTop&&Window.hasEffectLib&&Effect.ResizeWindow){
new Effect.ResizeWindow(this,null,null,null,this.height-dh,{duration:Window.resizeEffectDuration});}else{
this.height-=dh;
this.element.setStyle({height:h+"px"});
r2.hide();}
if(!this.useTop){
var bottom=parseFloat(this.element.getStyle('bottom'));
this.element.setStyle({bottom:(bottom+dh)+'px'});}}
else{
this.minimized=false;
var dh=this.r2Height;
this.r2Height=null;
if(this.useLeft&&this.useTop&&Window.hasEffectLib&&Effect.ResizeWindow){
new Effect.ResizeWindow(this,null,null,null,this.height+dh,{duration:Window.resizeEffectDuration});}
else{
var h=this.element.getHeight()+dh;
this.height+=dh;
this.element.setStyle({height:h+"px"})
r2.show();}
if(!this.useTop){
var bottom=parseFloat(this.element.getStyle('bottom'));
this.element.setStyle({bottom:(bottom-dh)+'px'});}
this.toFront();}
this._notify("onMinimize");
this._saveCookie()},
maximize:function(){
if(this.isMinimized()||this.resizing)
return;
if(Prototype.Browser.IE&&this.heightN==0)
this._getWindowBorderSize();
if(this.storedLocation!=null){
this._restoreLocation();
if(this.iefix)
this.iefix.hide();}
else{
this._storeLocation();
Windows.unsetOverflow(this);
var windowScroll=WindowUtilities.getWindowScroll(this.options.parent);
var pageSize=WindowUtilities.getPageSize(this.options.parent);
var left=windowScroll.left;
var top=windowScroll.top;
if(this.options.parent!=document.body){
windowScroll={top:0,left:0,bottom:0,right:0};
var dim=this.options.parent.getDimensions();
pageSize.windowWidth=dim.width;
pageSize.windowHeight=dim.height;
top=0;
left=0;}
if(this.constraint){
pageSize.windowWidth-=Math.max(0,this.constraintPad.left)+Math.max(0,this.constraintPad.right);
pageSize.windowHeight-=Math.max(0,this.constraintPad.top)+Math.max(0,this.constraintPad.bottom);
left+=Math.max(0,this.constraintPad.left);
top+=Math.max(0,this.constraintPad.top);}
var width=pageSize.windowWidth-this.widthW-this.widthE;
var height=pageSize.windowHeight-this.heightN-this.heightS;
if(this.useLeft&&this.useTop&&Window.hasEffectLib&&Effect.ResizeWindow){
new Effect.ResizeWindow(this,top,left,width,height,{duration:Window.resizeEffectDuration});}
else{
this.setSize(width,height);
this.element.setStyle(this.useLeft?{left:left}:{right:left});
this.element.setStyle(this.useTop?{top:top}:{bottom:top});}
this.toFront();
if(this.iefix)
this._fixIEOverlapping();}
this._notify("onMaximize");
this._saveCookie()},
isMinimized:function(){
return this.minimized;},
isMaximized:function(){
return(this.storedLocation!=null);},
setOpacity:function(opacity){
if(Element.setOpacity)
Element.setOpacity(this.element,opacity);},
setZIndex:function(zindex){
this.element.setStyle({zIndex:zindex});
Windows.updateZindex(zindex,this);},
setTitle:function(newTitle){
if(!newTitle||newTitle=="")
newTitle="&nbsp;";
Element.update(this.element.id+'_top',newTitle);},
getTitle:function(){
return $(this.element.id+'_top').innerHTML;},
setStatusBar:function(element){
var statusBar=$(this.getId()+"_bottom");
if(typeof(element)=="object"){
if(this.bottombar.firstChild)
this.bottombar.replaceChild(element,this.bottombar.firstChild);
else
this.bottombar.appendChild(element);}
else
this.bottombar.innerHTML=element;},
_checkIEOverlapping:function(){
if(!this.iefix&&(navigator.appVersion.indexOf('MSIE')>0)&&(navigator.userAgent.indexOf('Opera')<0)&&(this.element.getStyle('position')=='absolute')){
new Insertion.After(this.element.id,'<iframe id="'+this.element.id+'_iefix" '+'style="display:none;position:absolute;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);" '+'src="javascript:false;" frameborder="0" scrolling="no"></iframe>');
this.iefix=$(this.element.id+'_iefix');}
if(this.iefix)
setTimeout(this._fixIEOverlapping.bind(this),50);},
_fixIEOverlapping:function(){
Position.clone(this.element,this.iefix);
this.iefix.style.zIndex=this.element.style.zIndex-1;
this.iefix.show();},
_getWindowBorderSize:function(event){
var div=this._createHiddenDiv(this.options.className+"_n")
this.heightN=Element.getDimensions(div).height;
div.parentNode.removeChild(div)
var div=this._createHiddenDiv(this.options.className+"_s")
this.heightS=Element.getDimensions(div).height;
div.parentNode.removeChild(div)
var div=this._createHiddenDiv(this.options.className+"_e")
this.widthE=Element.getDimensions(div).width;
div.parentNode.removeChild(div)
var div=this._createHiddenDiv(this.options.className+"_w")
this.widthW=Element.getDimensions(div).width;
div.parentNode.removeChild(div);
var div=document.createElement("div");
div.className="overlay_"+this.options.className;
document.body.appendChild(div);
var that=this;
setTimeout(function(){that.overlayOpacity=($(div).getStyle("opacity"));div.parentNode.removeChild(div);},10);
if(Prototype.Browser.IE){
this.heightS=$(this.getId()+"_row3").getDimensions().height;
this.heightN=$(this.getId()+"_row1").getDimensions().height;}
if(Prototype.Browser.WebKit&&Prototype.Browser.WebKitVersion<420)
this.setSize(this.width,this.height);
if(this.doMaximize)
this.maximize();
if(this.doMinimize)
this.minimize();},
_createHiddenDiv:function(className){
var objBody=document.body;
var win=document.createElement("div");
win.setAttribute('id',this.element.id+"_tmp");
win.className=className;
win.style.display='none';
win.innerHTML='';
objBody.insertBefore(win,objBody.firstChild);
return win;},
_storeLocation:function(){
if(this.storedLocation==null){
this.storedLocation={useTop:this.useTop,useLeft:this.useLeft,
top:this.element.getStyle('top'),bottom:this.element.getStyle('bottom'),
left:this.element.getStyle('left'),right:this.element.getStyle('right'),
width:this.width,height:this.height};}},
_restoreLocation:function(){
if(this.storedLocation!=null){
this.useLeft=this.storedLocation.useLeft;
this.useTop=this.storedLocation.useTop;
if(this.useLeft&&this.useTop&&Window.hasEffectLib&&Effect.ResizeWindow)
new Effect.ResizeWindow(this,this.storedLocation.top,this.storedLocation.left,this.storedLocation.width,this.storedLocation.height,{duration:Window.resizeEffectDuration});
else{
this.element.setStyle(this.useLeft?{left:this.storedLocation.left}:{right:this.storedLocation.right});
this.element.setStyle(this.useTop?{top:this.storedLocation.top}:{bottom:this.storedLocation.bottom});
this.setSize(this.storedLocation.width,this.storedLocation.height);}
Windows.resetOverflow();
this._removeStoreLocation();}},
_removeStoreLocation:function(){
this.storedLocation=null;},
_saveCookie:function(){
if(this.cookie){
var value="";
if(this.useLeft)
value+="l:"+(this.storedLocation?this.storedLocation.left:this.element.getStyle('left'))
else
value+="r:"+(this.storedLocation?this.storedLocation.right:this.element.getStyle('right'))
if(this.useTop)
value+=",t:"+(this.storedLocation?this.storedLocation.top:this.element.getStyle('top'))
else
value+=",b:"+(this.storedLocation?this.storedLocation.bottom:this.element.getStyle('bottom'))
value+=","+(this.storedLocation?this.storedLocation.width:this.width);
value+=","+(this.storedLocation?this.storedLocation.height:this.height);
value+=","+this.isMinimized();
value+=","+this.isMaximized();
WindowUtilities.setCookie(value,this.cookie)}},
_createWiredElement:function(){
if(!this.wiredElement){
if(Prototype.Browser.IE)
this._getWindowBorderSize();
var div=document.createElement("div");
div.className="wired_frame "+this.options.className+"_wired_frame";
div.style.position='absolute';
this.options.parent.insertBefore(div,this.options.parent.firstChild);
this.wiredElement=$(div);}
if(this.useLeft)
this.wiredElement.setStyle({left:this.element.getStyle('left')});
else
this.wiredElement.setStyle({right:this.element.getStyle('right')});
if(this.useTop)
this.wiredElement.setStyle({top:this.element.getStyle('top')});
else
this.wiredElement.setStyle({bottom:this.element.getStyle('bottom')});
var dim=this.element.getDimensions();
this.wiredElement.setStyle({width:dim.width+"px",height:dim.height+"px"});
this.wiredElement.setStyle({zIndex:Windows.maxZIndex+30});
return this.wiredElement;},
_hideWiredElement:function(){
if(!this.wiredElement||!this.currentDrag)
return;
if(this.currentDrag==this.element)
this.currentDrag=null;
else{
if(this.useLeft)
this.element.setStyle({left:this.currentDrag.getStyle('left')});
else
this.element.setStyle({right:this.currentDrag.getStyle('right')});
if(this.useTop)
this.element.setStyle({top:this.currentDrag.getStyle('top')});
else
this.element.setStyle({bottom:this.currentDrag.getStyle('bottom')});
this.currentDrag.hide();
this.currentDrag=null;
if(this.doResize)
this.setSize(this.width,this.height);}},
_notify:function(eventName){
if(this.options[eventName])
this.options[eventName](this);
else
Windows.notify(eventName,this);}};
var Windows={
windows:[],
modalWindows:[],
observers:[],
focusedWindow:null,
maxZIndex:0,
overlayShowEffectOptions:{duration:0.5},
overlayHideEffectOptions:{duration:0.5},
addObserver:function(observer){
this.removeObserver(observer);
this.observers.push(observer);},
removeObserver:function(observer){
this.observers=this.observers.reject(function(o){return o==observer});},
notify:function(eventName,win){
this.observers.each(function(o){if(o[eventName])o[eventName](eventName,win);});},
getWindow:function(id){
return this.windows.detect(function(d){return d.getId()==id});},
getFocusedWindow:function(){
return this.focusedWindow;},
updateFocusedWindow:function(){
this.focusedWindow=this.windows.length>=2?this.windows[this.windows.length-2]:null;},
register:function(win){
this.windows.push(win);},
addModalWindow:function(win){
if(this.modalWindows.length==0){
WindowUtilities.disableScreen(win.options.className,'overlay_modal',win.overlayOpacity,win.getId(),win.options.parent);}
else{
if(Window.keepMultiModalWindow){
$('overlay_modal').style.zIndex=Windows.maxZIndex+1;
Windows.maxZIndex+=1;
WindowUtilities._hideSelect(this.modalWindows.last().getId());}
else
this.modalWindows.last().element.hide();
WindowUtilities._showSelect(win.getId());}
this.modalWindows.push(win);},
removeModalWindow:function(win){
this.modalWindows.pop();
if(this.modalWindows.length==0)
WindowUtilities.enableScreen();
else{
if(Window.keepMultiModalWindow){
this.modalWindows.last().toFront();
WindowUtilities._showSelect(this.modalWindows.last().getId());}
else
this.modalWindows.last().element.show();}},
register:function(win){
this.windows.push(win);},
unregister:function(win){
this.windows=this.windows.reject(function(d){return d==win});},
closeAll:function(){
this.windows.each(function(w){Windows.close(w.getId())});},
closeAllModalWindows:function(){
WindowUtilities.enableScreen();
this.modalWindows.each(function(win){if(win)win.close()});},
minimize:function(id,event){
var win=this.getWindow(id)
if(win&&win.visible)
win.minimize();
Event.stop(event);},
maximize:function(id,event){
var win=this.getWindow(id)
if(win&&win.visible)
win.maximize();
Event.stop(event);},
close:function(id,event){
var win=this.getWindow(id);
if(win)
win.close();
if(event)
Event.stop(event);},
blur:function(id){
var win=this.getWindow(id);
if(!win)
return;
if(win.options.blurClassName)
win.changeClassName(win.options.blurClassName);
if(this.focusedWindow==win)
this.focusedWindow=null;
win._notify("onBlur");},
focus:function(id){
var win=this.getWindow(id);
if(!win)
return;
if(this.focusedWindow)
this.blur(this.focusedWindow.getId())
if(win.options.focusClassName)
win.changeClassName(win.options.focusClassName);
this.focusedWindow=win;
win._notify("onFocus");},
unsetOverflow:function(except){
this.windows.each(function(d){d.oldOverflow=d.getContent().getStyle("overflow")||"auto";d.getContent().setStyle({overflow:"hidden"})});
if(except&&except.oldOverflow)
except.getContent().setStyle({overflow:except.oldOverflow});},
resetOverflow:function(){
this.windows.each(function(d){if(d.oldOverflow)d.getContent().setStyle({overflow:d.oldOverflow})});},
updateZindex:function(zindex,win){
if(zindex>this.maxZIndex){
this.maxZIndex=zindex;
if(this.focusedWindow)
this.blur(this.focusedWindow.getId())}
this.focusedWindow=win;
if(this.focusedWindow)
this.focus(this.focusedWindow.getId())}};
var Dialog={
dialogId:null,
onCompleteFunc:null,
callFunc:null,
parameters:null,
confirm:function(content,parameters){
if(content&&typeof content!="string"){
Dialog._runAjaxRequest(content,parameters,Dialog.confirm);
return}
content=content||"";
parameters=parameters||{};
var okLabel=parameters.okLabel?parameters.okLabel:"Ok";
var cancelLabel=parameters.cancelLabel?parameters.cancelLabel:"Cancel";
parameters=Object.extend(parameters,parameters.windowParameters||{});
parameters.windowParameters=parameters.windowParameters||{};
parameters.className=parameters.className||"alert";
var okButtonClass="class ='"+(parameters.buttonClass?parameters.buttonClass+" ":"")+" ok_button'"
var cancelButtonClass="class ='"+(parameters.buttonClass?parameters.buttonClass+" ":"")+" cancel_button'"
var content="\<div class='" +parameters.className +"_message'>" +content  +"</div>\<div class='" +parameters.className +"_buttons'>\<input type='button' value='" +okLabel +"' onclick='Dialog.okCallback()' " +okButtonClass +"/>\<input type='button' value='" +cancelLabel +"' onclick='Dialog.cancelCallback()' " +cancelButtonClass +"/>\</div>\
";
return this._openDialog(content,parameters)},
alert:function(content,parameters){
if(content&&typeof content!="string"){
Dialog._runAjaxRequest(content,parameters,Dialog.alert);
return}
content=content||"";
parameters=parameters||{};
var okLabel=parameters.okLabel?parameters.okLabel:"Ok";
parameters=Object.extend(parameters,parameters.windowParameters||{});
parameters.windowParameters=parameters.windowParameters||{};
parameters.className=parameters.className||"alert";
var okButtonClass="class ='"+(parameters.buttonClass?parameters.buttonClass+" ":"")+" ok_button'"
var content="\<div class='" +parameters.className +"_message'>" +content  +"</div>\<div class='" +parameters.className +"_buttons'>\<input type='button' value='" +okLabel +"' onclick='Dialog.okCallback()' " +okButtonClass +"/>\</div>";
return this._openDialog(content,parameters)},
info:function(content,parameters){
if(content&&typeof content!="string"){
Dialog._runAjaxRequest(content,parameters,Dialog.info);
return}
content=content||"";
parameters=parameters||{};
parameters=Object.extend(parameters,parameters.windowParameters||{});
parameters.windowParameters=parameters.windowParameters||{};
parameters.className=parameters.className||"alert";
var content="<div id='modal_dialog_message' class='"+parameters.className+"_message'>"+content+"</div>";
if(parameters.showProgress)
content+="<div id='modal_dialog_progress' class='"+parameters.className+"_progress'>  </div>";
parameters.ok=null;
parameters.cancel=null;
return this._openDialog(content,parameters)},
setInfoMessage:function(message){$('modal_dialog_message').update(message);},
closeInfo:function(){
Windows.close(this.dialogId);},
_openDialog:function(content,parameters){
var className=parameters.className;
if(!parameters.height&&!parameters.width){
parameters.width=WindowUtilities.getPageSize(parameters.options.parent||document.body).pageWidth/2;}
if(parameters.id)
this.dialogId=parameters.id;
else{
var t=new Date();
this.dialogId='modal_dialog_'+t.getTime();
parameters.id=this.dialogId;}
if(!parameters.height||!parameters.width){
var size=WindowUtilities._computeSize(content,this.dialogId,parameters.width,parameters.height,5,className)
if(parameters.height)
parameters.width=size+5
else
parameters.height=size+5}
parameters.effectOptions=parameters.effectOptions;
parameters.resizable=parameters.resizable||false;
parameters.minimizable=parameters.minimizable||false;
parameters.maximizable=parameters.maximizable||false;
parameters.draggable=parameters.draggable||false;
parameters.closable=parameters.closable||false;
var win=new Window(parameters);
win.getContent().innerHTML=content;
win.showCenter(true,parameters.top,parameters.left);
win.setDestroyOnClose();
win.cancelCallback=parameters.onCancel||parameters.cancel;
win.okCallback=parameters.onOk||parameters.ok;
return win;},
_getAjaxContent:function(originalRequest){
Dialog.callFunc(originalRequest.responseText,Dialog.parameters)},
_runAjaxRequest:function(message,parameters,callFunc){
if(message.options==null)
message.options={}
Dialog.onCompleteFunc=message.options.onComplete;
Dialog.parameters=parameters;
Dialog.callFunc=callFunc;
message.options.onComplete=Dialog._getAjaxContent;
new Ajax.Request(message.url,message.options);},
okCallback:function(){
var win=Windows.focusedWindow;
if(!win.okCallback||win.okCallback(win)){
$$("#"+win.getId()+" input").each(function(element){element.onclick=null;})
win.close();}},
cancelCallback:function(){
var win=Windows.focusedWindow;
$$("#"+win.getId()+" input").each(function(element){element.onclick=null})
win.close();
if(win.cancelCallback)
win.cancelCallback(win);}}/*
Based on Lightbox JS:Fullsize Image Overlays
by Lokesh Dhakar-http:
For more information on this script,visit:
http:
Licensed under the Creative Commons Attribution 2.5 License-http:(basically,do anything you want,just leave my name and link)*/
if(Prototype.Browser.WebKit){
var array=navigator.userAgent.match(new RegExp(/AppleWebKit\/([\d\.\+]*)/));
Prototype.Browser.WebKitVersion=parseFloat(array[1]);}
var WindowUtilities={
getWindowScroll:function(parent){
var T,L,W,H;
parent=parent||document.body;
if(parent!=document.body){
T=parent.scrollTop;
L=parent.scrollLeft;
W=parent.scrollWidth;
H=parent.scrollHeight;}
else{
var w=window;
with(w.document){
if(w.document.documentElement&&documentElement.scrollTop){
T=documentElement.scrollTop;
L=documentElement.scrollLeft;}else if(w.document.body){
T=body.scrollTop;
L=body.scrollLeft;}
if(w.innerWidth){
W=w.innerWidth;
H=w.innerHeight;}else if(w.document.documentElement&&documentElement.clientWidth){
W=documentElement.clientWidth;
H=documentElement.clientHeight;}else{
W=body.offsetWidth;
H=body.offsetHeight}}}
return{top:T,left:L,width:W,height:H};},
getPageSize:function(parent){
parent=parent||document.body;
var windowWidth,windowHeight;
var pageHeight,pageWidth;
if(parent!=document.body){
windowWidth=parent.getWidth();
windowHeight=parent.getHeight();
pageWidth=parent.scrollWidth;
pageHeight=parent.scrollHeight;}
else{
var xScroll,yScroll;
if(window.innerHeight&&window.scrollMaxY){
xScroll=document.body.scrollWidth;
yScroll=window.innerHeight+window.scrollMaxY;}else if(document.body.scrollHeight>document.body.offsetHeight){
xScroll=document.body.scrollWidth;
yScroll=document.body.scrollHeight;}else{
xScroll=document.body.offsetWidth;
yScroll=document.body.offsetHeight;}
if(self.innerHeight){
windowWidth=self.innerWidth;
windowHeight=self.innerHeight;}else if(document.documentElement&&document.documentElement.clientHeight){
windowWidth=document.documentElement.clientWidth;
windowHeight=document.documentElement.clientHeight;}else if(document.body){
windowWidth=document.body.clientWidth;
windowHeight=document.body.clientHeight;}
if(yScroll<windowHeight){
pageHeight=windowHeight;}else{
pageHeight=yScroll;}
if(xScroll<windowWidth){
pageWidth=windowWidth;}else{
pageWidth=xScroll;}}
return{pageWidth:pageWidth,pageHeight:pageHeight,windowWidth:windowWidth,windowHeight:windowHeight};},
disableScreen:function(className,overlayId,overlayOpacity,contentId,parent){
WindowUtilities.initLightbox(overlayId,className,function(){this._disableScreen(className,overlayId,overlayOpacity,contentId)}.bind(this),parent||document.body);},
_disableScreen:function(className,overlayId,overlayOpacity,contentId){
var objOverlay=$(overlayId);
var pageSize=WindowUtilities.getPageSize(objOverlay.parentNode);
if(contentId&&Prototype.Browser.IE){
WindowUtilities._hideSelect();
WindowUtilities._showSelect(contentId);}
objOverlay.style.height=(pageSize.pageHeight+'px');
objOverlay.style.display='none';
if(overlayId=="overlay_modal"&&Window.hasEffectLib&&Windows.overlayShowEffectOptions){
objOverlay.overlayOpacity=overlayOpacity;
new Effect.Appear(objOverlay,Object.extend({from:0,to:overlayOpacity},Windows.overlayShowEffectOptions));}
else
objOverlay.style.display="block";},
enableScreen:function(id){
id=id||'overlay_modal';
var objOverlay=$(id);
if(objOverlay){
if(id=="overlay_modal"&&Window.hasEffectLib&&Windows.overlayHideEffectOptions)
new Effect.Fade(objOverlay,Object.extend({from:objOverlay.overlayOpacity,to:0},Windows.overlayHideEffectOptions));
else{
objOverlay.style.display='none';
objOverlay.parentNode.removeChild(objOverlay);}
if(id!="__invisible__")
WindowUtilities._showSelect();}},
_hideSelect:function(id){
if(Prototype.Browser.IE){
id=id==null?"":"#"+id+" ";
$$(id+'select').each(function(element){
if(!WindowUtilities.isDefined(element.oldVisibility)){
element.oldVisibility=element.style.visibility?element.style.visibility:"visible";
element.style.visibility="hidden";}});}},
_showSelect:function(id){
if(Prototype.Browser.IE){
id=id==null?"":"#"+id+" ";
$$(id+'select').each(function(element){
if(WindowUtilities.isDefined(element.oldVisibility)){
try{
element.style.visibility=element.oldVisibility;}catch(e){
element.style.visibility="visible";}
element.oldVisibility=null;}
else{
if(element.style.visibility)
element.style.visibility="visible";}});}},
isDefined:function(object){
return typeof(object)!="undefined"&&object!=null;},
initLightbox:function(id,className,doneHandler,parent){
if($(id)){
Element.setStyle(id,{zIndex:Windows.maxZIndex+1});
Windows.maxZIndex++;
doneHandler();}
else{
var objOverlay=document.createElement("div");
objOverlay.setAttribute('id',id);
objOverlay.className="overlay_"+className
objOverlay.style.display='none';
objOverlay.style.position='absolute';
objOverlay.style.top='0';
objOverlay.style.left='0';
objOverlay.style.zIndex=Windows.maxZIndex+1;
Windows.maxZIndex++;
objOverlay.style.width='100%';
parent.insertBefore(objOverlay,parent.firstChild);
if(Prototype.Browser.WebKit&&id=="overlay_modal"){
setTimeout(function(){doneHandler()},10);}
else
doneHandler();}},
setCookie:function(value,parameters){
document.cookie=parameters[0]+"="+escape(value)+((parameters[1])?"; expires="+parameters[1].toGMTString():"")+((parameters[2])?"; path="+parameters[2]:"")+((parameters[3])?"; domain="+parameters[3]:"")+((parameters[4])?"; secure":"");},
getCookie:function(name){
var dc=document.cookie;
var prefix=name+"=";
var begin=dc.indexOf("; "+prefix);
if(begin==-1){
begin=dc.indexOf(prefix);
if(begin!=0)return null;}else{
begin+=2;}
var end=document.cookie.indexOf(";",begin);
if(end==-1){
end=dc.length;}
return unescape(dc.substring(begin+prefix.length,end));},
_computeSize:function(content,id,width,height,margin,className){
var objBody=document.body;
var tmpObj=document.createElement("div");
tmpObj.setAttribute('id',id);
tmpObj.className=className+"_content";
if(height)
tmpObj.style.height=height+"px"
else
tmpObj.style.width=width+"px"
tmpObj.style.position='absolute';
tmpObj.style.top='0';
tmpObj.style.left='0';
tmpObj.style.display='none';
tmpObj.innerHTML=content;
objBody.insertBefore(tmpObj,objBody.firstChild);
var size;
if(height)
size=$(tmpObj).getDimensions().width+margin;
else
size=$(tmpObj).getDimensions().height+margin;
objBody.removeChild(tmpObj);
return size;}}



Effect.ResizeWindow = Class.create();
Object.extend(Object.extend(Effect.ResizeWindow.prototype, Effect.Base.prototype), {
  initialize: function(win, top, left, width, height) {
    this.window = win;
    this.window.resizing = true;
    
    var size = win.getSize();
    this.initWidth    = parseFloat(size.width);
    this.initHeight   = parseFloat(size.height);

    var location = win.getLocation();
    this.initTop    = parseFloat(location.top);
    this.initLeft   = parseFloat(location.left);

    this.width    = width != null  ? parseFloat(width)  : this.initWidth;
    this.height   = height != null ? parseFloat(height) : this.initHeight;
    this.top      = top != null    ? parseFloat(top)    : this.initTop;
    this.left     = left != null   ? parseFloat(left)   : this.initLeft;

    this.dx     = this.left   - this.initLeft;
    this.dy     = this.top    - this.initTop;
    this.dw     = this.width  - this.initWidth;
    this.dh     = this.height - this.initHeight;
    
    this.r2      = $(this.window.getId() + "_row2");
    this.content = $(this.window.getId() + "_content");
        
    this.contentOverflow = this.content.getStyle("overflow") || "auto";
    this.content.setStyle({overflow: "hidden"});
    
    // Wired mode
    if (this.window.options.wiredDrag) {
      this.window.currentDrag = win._createWiredElement();
      this.window.currentDrag.show();
      this.window.element.hide();
    }

    this.start(arguments[5]);
  },
  
  update: function(position) {
    var width  = Math.floor(this.initWidth  + this.dw * position);
    var height = Math.floor(this.initHeight + this.dh * position);
    var top    = Math.floor(this.initTop    + this.dy * position);
    var left   = Math.floor(this.initLeft   + this.dx * position);

    if (window.ie) {
      if (Math.floor(height) == 0)  
        this.r2.hide();
      else if (Math.floor(height) >1)  
        this.r2.show();
    }      
    this.r2.setStyle({height: height});
    this.window.setSize(width, height);
    this.window.setLocation(top, left);
  },
  
  finish: function(position) {
    // Wired mode
    if (this.window.options.wiredDrag) {
      this.window._hideWiredElement();
      this.window.element.show();
    }

    this.window.setSize(this.width, this.height);
    this.window.setLocation(this.top, this.left);
    this.r2.setStyle({height: null});
    
    this.content.setStyle({overflow: this.contentOverflow});
      
    this.window.resizing = false;
  }
});

Effect.ModalSlideDown = function(element) {
  var windowScroll = WindowUtilities.getWindowScroll();    
  var height = element.getStyle("height");  
  element.setStyle({top: - (parseFloat(height) - windowScroll.top) + "px"});
  
  element.show();
  return new Effect.Move(element, Object.extend({ x: 0, y: parseFloat(height) }, arguments[1] || {}));
};


Effect.ModalSlideUp = function(element) {
  var height = element.getStyle("height");
  return new Effect.Move(element, Object.extend({ x: 0, y: -parseFloat(height) }, arguments[1] || {}));
};

PopupEffect = Class.create();
PopupEffect.prototype = {    
  initialize: function(htmlElement) {
    this.html = $(htmlElement);      
    this.options = Object.extend({className: "popup_effect", duration: 0.4}, arguments[1] || {});
    
  },
  show: function(element, options) { 
    var position = Position.cumulativeOffset(this.html);      
    var size = this.html.getDimensions();
    var bounds = element.win.getBounds();
    this.window =  element.win;      
    // Create a div
    if (!this.div) {
      this.div = document.createElement("div");
      this.div.className = this.options.className;
      this.div.style.height = size.height + "px";
      this.div.style.width  = size.width  + "px";
      this.div.style.top    = position[1] + "px";
      this.div.style.left   = position[0] + "px";   
      this.div.style.position = "absolute"
      document.body.appendChild(this.div);
    }                                                   
    if (this.options.fromOpacity)
      this.div.setStyle({opacity: this.options.fromOpacity})
    this.div.show();          
    var style = "top:" + bounds.top + ";left:" +bounds.left + ";width:" + bounds.width +";height:" + bounds.height;
    if (this.options.toOpacity)
      style += ";opacity:" + this.options.toOpacity;
    
    new Effect.Morph(this.div ,{style: style, duration: this.options.duration, afterFinish: this._showWindow.bind(this)});    
  },

  hide: function(element, options) {     
    var position = Position.cumulativeOffset(this.html);      
    var size = this.html.getDimensions();    
    this.window.visible = true; 
    var bounds = this.window.getBounds();
    this.window.visible = false; 

    this.window.element.hide();

    this.div.style.height = bounds.height;
    this.div.style.width  = bounds.width;
    this.div.style.top    = bounds.top;
    this.div.style.left   = bounds.left;
    
    if (this.options.toOpacity)
      this.div.setStyle({opacity: this.options.toOpacity})

    this.div.show();                                 
    var style = "top:" + position[1] + "px;left:" + position[0] + "px;width:" + size.width +"px;height:" + size.height + "px";

    if (this.options.fromOpacity)
      style += ";opacity:" + this.options.fromOpacity;
    new Effect.Morph(this.div ,{style: style, duration: this.options.duration, afterFinish: this._hideDiv.bind(this)});    
  },
  
  _showWindow: function() {
    this.div.hide();
    this.window.element.show(); 
  },
  
  _hideDiv: function() {
    this.div.hide();
  }
}

TooltipManager = {
  options: {cssClassName: 'tooltip', delayOver: 200, delayOut: 1000, shiftX: 2, shiftY: 2,
            className: 'alphacube', width: 200, height: null, 
            draggable: false, minimizable: false, maximizable: false, showEffect: Element.show, hideEffect: Element.hide},
  ajaxInfo: null,
  elements: null,
  showTimer: null,
  hideTimer: null,

  init: function(cssClassName, ajaxInfo, tooltipOptions) {
    TooltipManager.options = Object.extend(TooltipManager.options, tooltipOptions || {});
    //dele by banq
    //cssClassName = TooltipManager.options.cssClassName || "tooltip";
    TooltipManager.ajaxInfo = ajaxInfo;
    TooltipManager.elements = $$("." + cssClassName);
    TooltipManager.elements.each(function(element) {
      element = $(element)
      var info = TooltipManager._getInfo(element);
      if (info.ajax) {
        element.ajaxId = info.id;
        element.ajaxInfo = ajaxInfo;
        element.frameWidth = tooltipOptions.width;
        element.frameHeight = tooltipOptions.height;;
      }
      else {
        element.tooltipElement = $(info.id);        
      }
      element.observe("mouseover", TooltipManager._mouseOver);
      element.observe("mouseout", TooltipManager._mouseOut);
    });
    Windows.addObserver(this);
  },
  
  showNow: function(element) {   
    if (TooltipManager.showTimer) 
      clearTimeout(TooltipManager.showTimer);
    
    TooltipManager.showTimer = setTimeout(function() {TooltipManager._showTooltip(element)}, TooltipManager.options.delayOver)           
  },
  
  addHTML: function(element, tooltipElement) {
    element = $(element);
    tooltipElement = $(tooltipElement);
    element.tooltipElement = tooltipElement;
    
    element.observe("mouseover", TooltipManager._mouseOver);
    element.observe("mouseout", TooltipManager._mouseOut);
  },
  
  addAjax: function(element, ajaxInfo) {
    element = $(element);
    element.ajaxInfo = ajaxInfo;
    element.observe("mouseover", TooltipManager._mouseOver);
    element.observe("mouseout", TooltipManager._mouseOut);    
  },
//add by banq    
  addAjax: function(element, ajaxInfo, width, height) {  	
  
    element = $(element);
    element.ajaxInfo = ajaxInfo;    
    element.frameWidth = width;
    element.frameHeight = height;
   
    element.observe("mouseover", TooltipManager._mouseOver);
    element.observe("mouseout", TooltipManager._mouseOut);

  },    
        
    
  addURL: function(element, url, width, height) {
    element = $(element);
    element.url = url;
    element.frameWidth = width;
    element.frameHeight = height;
    element.observe("mouseover", TooltipManager._mouseOver);
    element.observe("mouseout", TooltipManager._mouseOut);    
  },
    
  close: function() {
    if (TooltipManager.tooltipWindow)
      TooltipManager.tooltipWindow.hide();
  },
  
  preloadImages: function(path, images, extension) {
    if (!extension)
      extension = ".gif";
      
    //preload images
    $A(images).each(function(i) {
      var image = new Image(); 
      image.src= path + "/" + i + extension; 
    });
  },
  
  _showTooltip: function(element) {
    if (this.element == element)
      return;
    // Get original element
    while (element && (!element.tooltipElement && !element.ajaxInfo && !element.url)) 
      element = element.parentNode;
    this.element = element;
    
    TooltipManager.showTimer = null;
    if (TooltipManager.hideTimer)
      clearTimeout(TooltipManager.hideTimer);
    
    var position = Position.cumulativeOffset(element);
    var dimension = element.getDimensions();

    if (! this.tooltipWindow)
      this.tooltipWindow = new Window("__tooltip__", TooltipManager.options);
      
    this.tooltipWindow.hide();
    this.tooltipWindow.setLocation(position[1] + dimension.height + TooltipManager.options.shiftY, position[0] + TooltipManager.options.shiftX);

    Event.observe(this.tooltipWindow.element, "mouseover", function(event) {TooltipManager._tooltipOver(event, element)});
    Event.observe(this.tooltipWindow.element, "mouseout", function(event) {TooltipManager._tooltipOut(event, element)});
    
    
    // Reset width/height for computation
    this.tooltipWindow.height = TooltipManager.options.height;
    this.tooltipWindow.width = TooltipManager.options.width;

    // Ajax content
    if (element.ajaxInfo) {
      //add by banq
      if (element.frameWidth){    	 	  
    	    this.tooltipWindow.height = element.frameHeight;
            this.tooltipWindow.width = element.frameWidth;
      }
      
      var p = element.ajaxInfo.options.parameters;
      var saveParam = p;
      
      // Set by CSS
      if (element.ajaxId) {
        if (p)
          p += "&" + element.ajaxId;
        else
          p =   element.ajaxId;
      }
      element.ajaxInfo.options.parameters = p || "";
      this.tooltipWindow.setHTMLContent("");
      //this.tooltipWindow.setAjaxContent(element.ajaxInfo.url, element.ajaxInfo.options);
      //add by banq
      this.tooltipWindow.setAjaxContent(element.ajaxInfo.url, element.ajaxInfo.options, false, false);      
      element.ajaxInfo.options.parameters = saveParam;    
    } 
    // URL content
    else if (element.url) {
      this.tooltipWindow.setURL(element.url);
      this.tooltipWindow.setSize(element.frameWidth, element.frameHeight);

      // Set tooltip size
      this.tooltipWindow.height = element.frameHeight;
      this.tooltipWindow.width = element.frameWidth;
      //add by banq
      this.tooltipWindow.setLocation(element.offsetTop + TooltipManager.options.shiftY, element.offsetLeft + TooltipManager.options.shiftX);
 
    }
    // HTML content
    else
      this.tooltipWindow.setHTMLContent(element.tooltipElement.innerHTML);

    if (!element.ajaxInfo) {      
      this.tooltipWindow.show();
      this.tooltipWindow.toFront();
      this.tooltipWindow.updateHeight();
    }
  },
  
   _refreshheight: function(element) {
   alert("hello");
    if (this.tooltipWindow) {
      this.tooltipWindow.updateHeight();
  
    }
  },
  
  _hideTooltip: function(element) {
    if (this.tooltipWindow) {
      this.tooltipWindow.hide();
      this.element = null;
    }
  },
  
  _mouseOver: function (event) {
    var element = Event.element(event);
    if (TooltipManager.showTimer) 
      clearTimeout(TooltipManager.showTimer);
    
    TooltipManager.showTimer = setTimeout(function() {TooltipManager._showTooltip(element)}, TooltipManager.options.delayOver)
  },
  
  _mouseOut: function(event) {
    var element = Event.element(event);
    if (TooltipManager.showTimer) {
      clearTimeout(TooltipManager.showTimer);
      TooltipManager.showTimer = null;
      return;
    }
    if (TooltipManager.tooltipWindow)
      TooltipManager.hideTimer = setTimeout(function() {TooltipManager._hideTooltip(element)}, TooltipManager.options.delayOut)
  },
  
  _tooltipOver: function(event, element) {
    if (TooltipManager.hideTimer) {
      clearTimeout(TooltipManager.hideTimer);
      TooltipManager.hideTimer = null;
    }
  },
  
  _tooltipOut: function(event, element) {
    if (TooltipManager.hideTimer == null)
      TooltipManager.hideTimer = setTimeout(function() {TooltipManager._hideTooltip(element)}, TooltipManager.options.delayOut)
  },
  
  _getInfo: function(element) {
    // Find html_ for static content
    var id = element.className.split(' ').detect(function(name) {return name.indexOf("html_") == 0});
    var ajax = true;
    if (id)
      ajax = false;
    else 
      // Find ajax_ for ajax content
      id = element.className.split(' ').detect(function(name) {return name.indexOf("ajax_") == 0});
    
    id = id.substr(id.indexOf('_')+1, id.length)
    return id ? {ajax: ajax, id: id} : null;
  }
  
};

