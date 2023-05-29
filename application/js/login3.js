/*! LAB.js (LABjs :: Loading And Blocking JavaScript)
    v2.0.3 (c) Kyle Simpson
    MIT License
*/
(function(o){var K=o.$LAB,y="UseLocalXHR",z="AlwaysPreserveOrder",u="AllowDuplicates",A="CacheBust",B="BasePath",C=/^[^?#]*\//.exec(location.href)[0],D=/^\w+\:\/\/\/?[^\/]+/.exec(C)[0],i=document.head||document.getElementsByTagName("head"),L=(o.opera&&Object.prototype.toString.call(o.opera)=="[object Opera]")||("MozAppearance"in document.documentElement.style),q=document.createElement("script"),E=typeof q.preload=="boolean",r=E||(q.readyState&&q.readyState=="uninitialized"),F=!r&&q.async===true,M=!r&&!F&&!L;function G(a){return Object.prototype.toString.call(a)=="[object Function]"}function H(a){return Object.prototype.toString.call(a)=="[object Array]"}function N(a,c){var b=/^\w+\:\/\//;if(/^\/\/\/?/.test(a)){a=location.protocol+a}else if(!b.test(a)&&a.charAt(0)!="/"){a=(c||"")+a}return b.test(a)?a:((a.charAt(0)=="/"?D:C)+a)}function s(a,c){for(var b in a){if(a.hasOwnProperty(b)){c[b]=a[b]}}return c}function O(a){var c=false;for(var b=0;b<a.scripts.length;b++){if(a.scripts[b].ready&&a.scripts[b].exec_trigger){c=true;a.scripts[b].exec_trigger();a.scripts[b].exec_trigger=null}}return c}function t(a,c,b,d){a.onload=a.onreadystatechange=function(){if((a.readyState&&a.readyState!="complete"&&a.readyState!="loaded")||c[b])return;a.onload=a.onreadystatechange=null;d()}}function I(a){a.ready=a.finished=true;for(var c=0;c<a.finished_listeners.length;c++){a.finished_listeners[c]()}a.ready_listeners=[];a.finished_listeners=[]}function P(d,f,e,g,h){setTimeout(function(){var a,c=f.real_src,b;if("item"in i){if(!i[0]){setTimeout(arguments.callee,25);return}i=i[0]}a=document.createElement("script");if(f.type)a.type=f.type;if(f.charset)a.charset=f.charset;if(h){if(r){e.elem=a;if(E){a.preload=true;a.onpreload=g}else{a.onreadystatechange=function(){if(a.readyState=="loaded")g()}}a.src=c}else if(h&&c.indexOf(D)==0&&d[y]){b=new XMLHttpRequest();b.onreadystatechange=function(){if(b.readyState==4){b.onreadystatechange=function(){};e.text=b.responseText+"\n//@ sourceURL="+c;g()}};b.open("GET",c);b.send()}else{a.type="text/cache-script";t(a,e,"ready",function(){i.removeChild(a);g()});a.src=c;i.insertBefore(a,i.firstChild)}}else if(F){a.async=false;t(a,e,"finished",g);a.src=c;i.insertBefore(a,i.firstChild)}else{t(a,e,"finished",g);a.src=c;i.insertBefore(a,i.firstChild)}},0)}function J(){var l={},Q=r||M,n=[],p={},m;l[y]=true;l[z]=false;l[u]=false;l[A]=false;l[B]="";function R(a,c,b){var d;function f(){if(d!=null){d=null;I(b)}}if(p[c.src].finished)return;if(!a[u])p[c.src].finished=true;d=b.elem||document.createElement("script");if(c.type)d.type=c.type;if(c.charset)d.charset=c.charset;t(d,b,"finished",f);if(b.elem){b.elem=null}else if(b.text){d.onload=d.onreadystatechange=null;d.text=b.text}else{d.src=c.real_src}i.insertBefore(d,i.firstChild);if(b.text){f()}}function S(c,b,d,f){var e,g,h=function(){b.ready_cb(b,function(){R(c,b,e)})},j=function(){b.finished_cb(b,d)};b.src=N(b.src,c[B]);b.real_src=b.src+(c[A]?((/\?.*$/.test(b.src)?"&_":"?_")+~~(Math.random()*1E9)+"="):"");if(!p[b.src])p[b.src]={items:[],finished:false};g=p[b.src].items;if(c[u]||g.length==0){e=g[g.length]={ready:false,finished:false,ready_listeners:[h],finished_listeners:[j]};P(c,b,e,((f)?function(){e.ready=true;for(var a=0;a<e.ready_listeners.length;a++){e.ready_listeners[a]()}e.ready_listeners=[]}:function(){I(e)}),f)}else{e=g[0];if(e.finished){j()}else{e.finished_listeners.push(j)}}}function v(){var e,g=s(l,{}),h=[],j=0,w=false,k;function T(a,c){a.ready=true;a.exec_trigger=c;x()}function U(a,c){a.ready=a.finished=true;a.exec_trigger=null;for(var b=0;b<c.scripts.length;b++){if(!c.scripts[b].finished)return}c.finished=true;x()}function x(){while(j<h.length){if(G(h[j])){try{h[j++]()}catch(err){}continue}else if(!h[j].finished){if(O(h[j]))continue;break}j++}if(j==h.length){w=false;k=false}}function V(){if(!k||!k.scripts){h.push(k={scripts:[],finished:true})}}e={script:function(){for(var f=0;f<arguments.length;f++){(function(a,c){var b;if(!H(a)){c=[a]}for(var d=0;d<c.length;d++){V();a=c[d];if(G(a))a=a();if(!a)continue;if(H(a)){b=[].slice.call(a);b.unshift(d,1);[].splice.apply(c,b);d--;continue}if(typeof a=="string")a={src:a};a=s(a,{ready:false,ready_cb:T,finished:false,finished_cb:U});k.finished=false;k.scripts.push(a);S(g,a,k,(Q&&w));w=true;if(g[z])e.wait()}})(arguments[f],arguments[f])}return e},wait:function(){if(arguments.length>0){for(var a=0;a<arguments.length;a++){h.push(arguments[a])}k=h[h.length-1]}else k=false;x();return e}};return{script:e.script,wait:e.wait,setOptions:function(a){s(a,g);return e}}}m={setGlobalDefaults:function(a){s(a,l);return m},setOptions:function(){return v().setOptions.apply(null,arguments)},script:function(){return v().script.apply(null,arguments)},wait:function(){return v().wait.apply(null,arguments)},queueScript:function(){n[n.length]={type:"script",args:[].slice.call(arguments)};return m},queueWait:function(){n[n.length]={type:"wait",args:[].slice.call(arguments)};return m},runQueue:function(){var a=m,c=n.length,b=c,d;for(;--b>=0;){d=n.shift();a=a[d.type].apply(null,d.args)}return a},noConflict:function(){o.$LAB=K;return m},sandbox:function(){return J()}};return m}o.$LAB=J();(function(a,c,b){if(document.readyState==null&&document[a]){document.readyState="loading";document[a](c,b=function(){document.removeEventListener(c,b,false);document.readyState="complete"},false)}})("addEventListener","DOMContentLoaded")})(this);

function GetXmlHttpObject()
{
    var A = null;
    try {
        A = new ActiveXObject("Msxml2.XMLHTTP");
    }
    catch (e) {
        try {
            A = new ActiveXObject("Microsoft.XMLHTTP");
        }
        catch (oc) {
            A = null;
        }
    }
    if (!A && typeof XMLHttpRequest != "undefined") {
        A = new XMLHttpRequest();
    }
    return A;
}


function load(url, callback) {
    var xhr = GetXmlHttpObject();
    xhr.onreadystatechange = ensureReadiness;

    function ensureReadiness() {
        if (xhr.readyState < 4) {
            return;
        }

        if (xhr.status !== 200) {
            return;
        }

        // all is well
        if (xhr.readyState === 4) {
            callback(xhr);
        }
    }

    xhr.open('GET', url, true);
    //xhr.setRequestHeader('Referer', window.location.href);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.send('');

}

var fromFormName ;
var logged = false;

function setLogged(){
    logged = true;
    if (typeof(isLogin) != "undefined")
        isLogin = true;
}

function getContextPath(){
    if (document.getElementById('contextPath') == null) {
        alert("no contextPath");
        return null;
    }
    return document.getElementById('contextPath').value;
}

//login.jsp action
function callLogin() {
    loginW('messageReply');
}
//登录入口
function loginW(fromForm) {
    if (typeof(isLogin) != "undefined")
        if (isLogin) {//isLogin
            setLogged()
            window.location.reload();
            return true;
        }
    fromFormName = fromForm;
    document.getElementById("subbtn").type = "button";
    document.getElementById("subbtn").onclick = function () {
        subLoging()
    };
    $('#login').modal({
        keyboard: true
    })
}

function subLoging() {
    var j_username = document.getElementById("j_username").value;
    var j_password = document.getElementById("j_password").value;
    if (j_username == "" || j_password == "") return;

    $.ajax({
        url: '/account/protected/logged.jsp?j_username=' + j_username + '&j_password=' + j_password,
        success: function (response) {
            if (response.indexOf("if(setLogged)setLogged()") >= 0)
                setLogged();
            else
                alert("登录错误 ");
            if (logged)
                if (fromFormName != null) {
                    document.getElementById(fromFormName).submit();
                } else
                    window.location.reload();
        },
        error: function (request, error) {
            alert("登录错误 ");
        }
    });
};


function delloginCookies() {
    eraseCookie("rememberMe");
    eraseCookie("username");
    eraseCookie("password");
}


function createCookie(name,value,seconds) {
    var dt = new Date();
    if (seconds)
        dt.setTime(dt.getTime() + (seconds * 1000));
    else
        dt.setTime(dt.getTime() - 10000);

    var expires = "; expires=" + dt.toGMTString();
    document.cookie = name + "=" + value + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name, "", -1);
}


function shareto(id){

    var url = encodeURIComponent(window.top.document.location.href);
    var title = encodeURIComponent(window.top.document.title);
    var _gaq = _gaq || [];

    if (id == "fav") {
        addBookmark(document.title);
        return;
    } else if (id == "weixin") {
        _gaq.push(['_trackEvent', 'SocialShare', 'Share', 'weixin', 1]);
        window.open(getContextPath() + '/common/barcode.jsp?fullurl=' + window.top.document.location.href, 'newwindow', 'height=300,width=300,top=0,left=0,toolbar=no,menubar=no,location=no, status=no');
        return;
    } else if (id == "qzone") {
        _gaq.push(['_trackEvent', 'SocialShare', 'Share', 'QZone', 1]);
        window.open(' http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=' + url + "&title=" + title);
        return;
    } else if (id == "sina") {
        _gaq.push(['_trackEvent', 'SocialShare', 'Share', 'SinaT', 1]);
        window.open(" http://service.weibo.com/share/share.php?url=" + url + "&appkey=2879276008&title=" + title, "_blank", "width=615,height=505");
        return;
    } else if (id == "googlebuzz") {
        _gaq.push(['_trackEvent', 'SocialShare', 'Share', 'GoogleBuzz', 1]);
        window.open(' https://plus.google.com/share?url=' + url, '_blank');
        return;
    } else if (id == "mail") {
        _gaq.push(['_trackEvent', 'SocialShare', 'Share', 'Mail', 1]);
        window.open('mailto:?subject=' + title + '&body=' + encodeURIComponent('这是我看到了一篇很不错的文章，分享给你看看！\r\n\r\n') + title + encodeURIComponent('\r\n') + url);
        return;
    }
}

/**
 * [jQuery-lazyload-any]{@link https://github.com/emn178/jquery-lazyload-any}
 *
 * @version 0.3.1
 * @author Yi-Cyuan Chen [emn178@gmail.com]
 * @copyright Yi-Cyuan Chen 2014-2016
 * @license MIT
 */
(function ($, window, document) {
    var KEY = 'jquery-lazyload-any';
    var EVENT = 'appear';
    var SELECTOR_KEY = KEY + '-' + EVENT;
    var SELECTOR = ':' + SELECTOR_KEY;
    var SCROLLER_KEY = KEY + '-scroller';
    var DISPLAY_KEY = KEY + '-display';
    var WATCH_KEY = KEY + '-watch';
    var DIV = $('<div/>');
    var screenHeight, screenWidth, init = false, observations = $();
  
    $.expr[':'][SELECTOR_KEY] = function (element) {
      return $(element).data(SELECTOR_KEY) !== undefined;
    };
  
    function test() {
      var element = $(this);
      if (element.is(':visible') && visible(element)) {
        element.trigger(EVENT);
      }
    }
  
    function visible(element) {
      var rect = element[0].getBoundingClientRect();
      var x1 = -element.data(KEY).threshold;
      var y1 = x1;
      var y2 = screenHeight - y1;
      var x2 = screenWidth - x1;
      return (rect.top >= y1 && rect.top <= y2 || rect.bottom >= y1 && rect.bottom <= y2) &&
        (rect.left >= x1 && rect.left <= x2 || rect.right >= x1 && rect.right <= x2);
    }
  
    function resize() {
      screenHeight = window.innerHeight || document.documentElement.clientHeight;
      screenWidth = window.innerWidth || document.documentElement.clientWidth;
      detect();
    }
  
    function detect() {
      observations = observations.filter(SELECTOR);
      if (this.nodeType == 1) {
        $(this).find(SELECTOR).each(test);
      } else {
        observations.each(test);
      }
    }
  
    function show() {
      var element = $(this);
      var options = element.data(KEY);
      var content = element.data('lazyload');
      if (!content) {
        var script = element.children().filter('script[type="text/lazyload"]').get(0);
        content = $(script).html();
      }
      if (!content) {
        var comment = element.contents().filter(function () {
          return this.nodeType === 8;
        }).get(0);
        content = comment && $.trim(comment.data);
      }
      var newElement = DIV.html(content).contents();
      element.replaceWith(newElement);
      if ($.isFunction(options.load)) {
        options.load.call(newElement, newElement);
      }
    }
  
    function watch() {
      var element = $(this);
      if (!(watchScroller(element) | watchDisplay(element))) {
        return;
      }
      if (element.data(WATCH_KEY)) {
        return;
      }
      element.data(WATCH_KEY, 1);
      element.bind(EVENT, clearWatch);
    }
  
    function watchScroller(element) {
      if (element.data(SCROLLER_KEY)) {
        return false;
      }
      var overflow = element.css('overflow');
      if (overflow != 'scroll' && overflow != 'auto') {
        return false;
      }
      element.data(SCROLLER_KEY, 1);
      element.bind('scroll', detect);
      return true;
    }
  
    function watchDisplay(element) {
      if (element.data(DISPLAY_KEY)) {
        return;
      }
      var display = element.css('display');
      if (display != 'none') {
        return;
      }
      element.data(DISPLAY_KEY, 1);
      element._bindShow(detect);
      return true;
    }
  
    function clearWatch() {
      var element = $(this);
      if (element.find(SELECTOR).length === 0) {
        element.removeData(SCROLLER_KEY).removeData(DISPLAY_KEY).removeData(WATCH_KEY);
        element.unbind('scroll', detect).unbind(EVENT, clearWatch)._unbindShow(detect);
      }
    }
  
    function refresh(selector) {
      var elements = selector === undefined ? observations : $(selector);
      elements.each(function () {
        var element = $(this);
        if (!element.is(SELECTOR)) {
          return;
        }
        element.parents().each(watch);
      });
    }
  
    $.fn.lazyload = function (options) {
      var opts = {
        threshold: 0,
        trigger: EVENT
      };
      $.extend(opts, options);
      var trigger = opts.trigger.split(' ');
      this.data(SELECTOR_KEY, $.inArray(EVENT, trigger) != -1).data(KEY, opts);
      this.bind(opts.trigger, show);
      this.each(test);
      this.parents().each(watch);
      this.each(function () {
        observations = observations.add(this);
      });
  
      if (!init) {
        init = true;
        resize();
        $(document).ready(function () {
          $(window).bind('resize', resize).bind('scroll', detect);
        });
      }
      return this;
    };
  
    $.lazyload = {
      check: detect,
      refresh: refresh
    };
  
    // SHOW EVENT
    (function () {
      var EVENT = 'show';
      var SELECTOR_KEY = KEY + '-' + EVENT;
      var SELECTOR = ':' + SELECTOR_KEY;
      var interval = 50, timer, observations = $();
  
      $.expr[':'][SELECTOR_KEY] = function (element) {
        return $(element).data(SELECTOR_KEY) !== undefined;
      };
  
      function test() {
        var element = $(this);
        var status = element.css('display') != 'none';
        if (element.data(SELECTOR_KEY) != status) {
          element.data(SELECTOR_KEY, status);
          if (status) {
            element.trigger(EVENT);
          }
        }
      }
  
      function detect() {
        observations = observations.filter(SELECTOR);
        observations.each(test);
        if (observations.length === 0) {
          timer = clearInterval(timer);
        }
      }
  
      $.fn._bindShow = function (handler) {
        this.bind(EVENT, handler);
        this.data(SELECTOR_KEY, this.css('display') != 'none');
        observations = observations.add(this);
        if (interval && !timer) {
          timer = setInterval(detect, interval);
        }
      };
  
      $.fn._unbindShow = function (handler) {
        this.unbind(EVENT, handler);
        this.removeData(SELECTOR_KEY);
      };
  
      $.lazyload.setInterval = function (value) {
        if (value == interval || !$.isNumeric(value) || value < 0) {
          return;
        }
        interval = value;
        timer = clearInterval(timer);
        if (interval > 0) {
          timer = setInterval(detect, interval);
        }
      };
    })();
  })(jQuery, window, document);
  

document.addEventListener("DOMContentLoaded", function(event) { 

    $('.lazyload').lazyload();
    
    $(function() { 
        var elm = $('.scrolldiv'); 
        var startPos = $(elm).offset().top; 
        if (window.matchMedia('(min-width: 992px)').matches) { 
        $.event.add(window, "scroll", function() { 
            var p = $(window).scrollTop(); 
            $(elm).css('position',((p) > startPos) ? 'fixed' : 'static'); 
            $(elm).css('top',((p) > startPos) ? '20px' : ''); 
        }); 
        };
    }); 
    
    });