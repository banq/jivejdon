var _wow_data_host = 'data3.wowzio.com';    var _wow_getComputedStyle = function(_elem, _style) {
      var computedStyle;
      if (typeof _elem.currentStyle != 'undefined'){
        if (_style == 'borderRightWidth' || _style == 'borderLeftWidth') {
          var _which = (_style=='borderRightWidth')?'Right':'Left';
          return _wow_getBorderWidth(_elem, _which);
        } else {
          computedStyle = _elem.currentStyle;
          return _wow_grabLength(_elem, computedStyle[_style]);
        }
      }else {
        computedStyle = document.defaultView.getComputedStyle(_elem, null); 
        return _wow_grabLength(_elem, computedStyle[_style]);
      }
    };
    
    var _wow_grabLength = function(styleEl, len) {
        if ((typeof len != 'undefined') && len != '0px' && len != '0em' && len != '0pt' && len != '0') {
          var temp = document.createElement("DIV");
          temp.style.width = len;
          styleEl.parentNode.appendChild(temp);
          len = Math.round(temp.offsetWidth);
          styleEl.parentNode.removeChild(temp);
          return len;
        } else {
          return 0;
        }
    };

    var _wow_getBorderWidth = function(styleEl, which) {
        var borderRegex = /thin|medium|thick/i;
        var borderWidth = styleEl.currentStyle["border" + which + "Width"];
        if(styleEl.currentStyle["border" + which + "Style"] != "none" && 
                ((/Top|Bottom/i.test(which) && styleEl.offsetHeight > styleEl.clientHeight) || 
                (/Right|Left/i.test(which) && styleEl.offsetWidth > styleEl.clientWidth))) {
            if(!borderRegex.test(borderWidth)) {
                return _wow_grabLength(borderWidth);
            } else if(borderRegex.test(borderWidth)) {
                var temp = document.createElement("DIV");
                temp.style.width = "10px";
                temp.style.border = borderWidth + " " + styleEl.currentStyle["border" + which + "Style"] + " #000000";
                styleEl.parentNode.appendChild(temp);
                borderWidth = Math.round((temp.offsetWidth-10)/2);
                styleEl.parentNode.removeChild(temp);
                return borderWidth;
            }
        } else {
            return "0";
        }
    };
var _wow_outer_el = document.getElementById('wow_wwrapbcec');if (!_wow_outer_el) { _wow_outer_el = document.getElementById('wow_wwrapauto');}var padLeft = _wow_getComputedStyle(_wow_outer_el.parentNode, 'paddingLeft');var padRight = _wow_getComputedStyle(_wow_outer_el.parentNode, 'paddingRight');var borderRight = _wow_getComputedStyle(_wow_outer_el.parentNode, 'borderRightWidth');var borderLeft = _wow_getComputedStyle(_wow_outer_el.parentNode, 'borderLeftWidth');var _wow_par_width = _wow_outer_el.parentNode.offsetWidth-padLeft-padRight-borderLeft-borderRight-2;_wow_par_width = Math.min(_wow_par_width, 350);if (_wow_par_width<=50) {_wow_par_width=200;}      function _wow_getAutoFitHeight(w) {
        var maxw = Math.min(w,300);
        var addw = 300 - maxw;
        var h = 300 + addw;
        return h;
      }var _wow_par_height = _wow_getAutoFitHeight(_wow_par_width);    var _wow_getComputedStyle = function(_elem, _style) {
      var computedStyle;
      if (typeof _elem.currentStyle != 'undefined'){
        if (_style == 'borderRightWidth' || _style == 'borderLeftWidth') {
          var _which = (_style=='borderRightWidth')?'Right':'Left';
          return _wow_getBorderWidth(_elem, _which);
        } else {
          computedStyle = _elem.currentStyle;
          return _wow_grabLength(_elem, computedStyle[_style]);
        }
      }else {
        computedStyle = document.defaultView.getComputedStyle(_elem, null); 
        return _wow_grabLength(_elem, computedStyle[_style]);
      }
    };
    
    var _wow_grabLength = function(styleEl, len) {
        if ((typeof len != 'undefined') && len != '0px' && len != '0em' && len != '0pt' && len != '0') {
          var temp = document.createElement("DIV");
          temp.style.width = len;
          styleEl.parentNode.appendChild(temp);
          len = Math.round(temp.offsetWidth);
          styleEl.parentNode.removeChild(temp);
          return len;
        } else {
          return 0;
        }
    };

    var _wow_getBorderWidth = function(styleEl, which) {
        var borderRegex = /thin|medium|thick/i;
        var borderWidth = styleEl.currentStyle["border" + which + "Width"];
        if(styleEl.currentStyle["border" + which + "Style"] != "none" && 
                ((/Top|Bottom/i.test(which) && styleEl.offsetHeight > styleEl.clientHeight) || 
                (/Right|Left/i.test(which) && styleEl.offsetWidth > styleEl.clientWidth))) {
            if(!borderRegex.test(borderWidth)) {
                return _wow_grabLength(borderWidth);
            } else if(borderRegex.test(borderWidth)) {
                var temp = document.createElement("DIV");
                temp.style.width = "10px";
                temp.style.border = borderWidth + " " + styleEl.currentStyle["border" + which + "Style"] + " #000000";
                styleEl.parentNode.appendChild(temp);
                borderWidth = Math.round((temp.offsetWidth-10)/2);
                styleEl.parentNode.removeChild(temp);
                return borderWidth;
            }
        } else {
            return "0";
        }
    };
var _wow_pad = 3;var _wow_padded_width = _wow_par_width - _wow_pad;var _wow_outer_css_extra = '<style>';_wow_outer_css_extra += '#wow_wwrapbcec, #wow_wwrapauto {margin: 0;  padding: 0;  width: '+_wow_par_width+'px; clear:both; border: 1px solid #444; line-height: 0px !important; }';_wow_outer_css_extra += '#wow_signbcec, #wow_signauto {background: #ccc url(http://static.wowzio.net/images/sigbackgrad2.gif) repeat-x top left; margin: 0; text-align: right; font-size: 10px; width: '+ _wow_par_width +'px; height:14px; padding: 2px 0 0 0;}';var _wow_leftSigWidth = _wow_par_width - 51;_wow_outer_css_extra += '#wow_sl, #wow_slbcec {width: '+ _wow_leftSigWidth +'px !important; height:14px !important;}';_wow_outer_css_extra += '</style>';document.write("<style> #wow_wwrap, #wow_wwrapauto, #wow_wwrapbcec { margin: 0; padding: 0; width: autopx; clear:both; border: 1px solid #444444; background: #ccc url(http://static.wowzio.net/images/ajax_loader2_gray.gif) center center no-repeat; line-height: 0px; } a#wow_sl, a#wow_sr, a#wow_slbcec, a#wow_srbcec { font: normal 100% arial,sans-serif; text-decoration: none; display: inline !important; } #wow_sl, #wow_slbcec { font-weight: bold !important; float:left; display:block; color: #555 !important; zoom: 1; overflow:hidden; width: -51px !important; height: 14px !important; line-height: 14px !important; } #wow_sr, #wow_srbcec { font: bold 9.5px tahoma,arial,sans-serif !important; text-indent: -5000px; float:right; width: 51px; display:block; overflow:hidden; background: url(http://static.wowzio.net/images/wowziologogray41x10.png) 6px 1px no-repeat; } *html #wow_sr, *html #wow_srbcec { background-image: url(http://static.wowzio.net/images/wowziologogray41x10.gif); } #wow_sr:hover, #wow_srbcec:hover { background-image: url(http://static.wowzio.net/images/wowziologo41x10.png); } *html #wow_sr:hover, *html #wow_srbcec:hover { background-image: url(http://static.wowzio.net/images/wowziologo41x10.gif); } #wow_sl a:hover, #wow_slbcec a:hover { text-decoration: underline; } #wow_sign, #wow_sign, #wow_signbcec { background: #ccc url(http://static.wowzio.net/images/sigbackgrad2.gif) repeat-x top left; margin: 0 !important; text-align: left !important; font-size: 10px !important; width: px; height: 14px; padding: 2px 0 0 0; position: relative; } .cp_wid_iframe { margin: 0 0 0px; } a.wow_bl, a.wow_bl:visited { color: #555 !important; background:transparent !important; text-decoration: none !important; font: bold 9.5px tahoma,arial,sans-serif !important; margin: 0 1px !important; } a.bl_f { margin-left: 4px !important; } a.wow_bl:hover { background: transparent !important; } .wowclear { clear: both; line-height: 0 !important; height: 0 !important; border: 0 !important; font-size: 0 !important; margin: 0 !important; } </style>");
document.write(_wow_outer_css_extra);
var _wow_iframe_url = 'http://' + _wow_data_host + '/widgets/module?wtype=activity&ti=Live+Activity+Feed&tc=FFFFFF&hc=FFFFFF&bc=000000&txc=CCCCCC&lc=FFFFFF&ret=frame&out=json&ni=10&tx=DESIGNER_DARK&ap=yes&ii=yes&ir=yes&ts=0&sb=yes&sv=3&cid=27&ef=fade&th=activityflow&ids=38574&isWidgetBuilder=no&csshash=bcec'+'&x=2&h='+_wow_par_height+'&w='+_wow_par_width+'&autowidth='+_wow_par_width;document.write("<iframe allowtransparency='true' width='"+_wow_par_width+"' height='"+_wow_par_height+"' margin='0' scrolling='no' marginheight='0' marginwidth='0' frameborder='0' src='"+_wow_iframe_url+"' name='wowiframebcec' id='wowiframebcec' class='cp_wid_iframe' ></iframe>");
  var wow_ip = '125.76.216.102';
  var wow_ids = '38574';
  var wow_host = 'www.wowzio.com';    var wow_client_host = document.location.host;
    var wow_client_title = "&ti="+escape(document.title); // escape preferred over encodeURIComponent because it encodes single quotes
    if (document.referrer) {
      var wow_client_referrer = "&ref="+encodeURIComponent(document.referrer);
    } else {
      var wow_client_referrer = '';
    }
    
    var wow_blogids = "&blogid="+wow_ids;
    var wow_client_url = document.location.href;
    wow_client_url = wow_client_url.replace(document.location.hash, '');
    wow_client_url = encodeURIComponent(wow_client_url);
    wow_ip = "";
    var wow_beacon_img = "<img style='position:absolute;top:0;left:0;' src='http://data7.wowzio.com/activity/beacon?url=" + wow_client_url + wow_ip + wow_blogids + wow_client_title + wow_client_referrer + "'>";document.write(wow_beacon_img);
  var wow_counter_img = "<img style='position:absolute;top:0;left:0;display:none;' src='http://data7.wowzio.com/widgets/counter'>";
  document.write(wow_counter_img);      window.setTimeout( function () {
        var wow_sml = document.getElementById('sml_bcec');
        if (wow_sml) {
          wow_sml.href = 'http://www.wowzio.com/widgets/seewidgets?ids=38574&cid=27&r=s';
        }

        var wow_gwl = document.getElementById('gwl_bcec');
        if (wow_gwl) {
          wow_gwl.href = 'http://www.wowzio.com/widgets/designer?wtype=activity&w=auto&h=362&ti=Live+Activity+Feed&tc=FFFFFF&hc=FFFFFF&bc=000000&txc=CCCCCC&lc=FFFFFF&ret=frame&out=json&ni=10&tx=DESIGNER_DARK&ap=yes&ii=yes&ir=yes&ts=0&sb=yes&sv=3&cid=27&ef=fade&ids=38574&r=s';
        }

        var wow_sr = document.getElementById('wow_srbcec');
        if (!wow_sr) {
          wow_sr = document.getElementById('wow_sr');
        }
        if (wow_sr) {
          wow_sr.href = 'http://www.wowzio.com/?r=s';
        }
      }, 500);      var wow_signHeightCheckbcec = function () {
        var wow_sigParEl = document.getElementById('wow_signbcec');
        if (!wow_sigParEl || wow_sigParEl.offsetHeight<13) {return false;} 
        return true;
      };

      var wow_linkChecksbcec = function() {
        var gwl = document.getElementById('gwl_bcec');
        if (!gwl) {return false;}
        if (gwl.offsetHeight<8) {return false;}
        if (gwl.href.indexOf('http://www.wowzio.com/widgets/designer')<0) {return false;}
        if (gwl.style.display == 'none' || gwl.style.visibility == 'hidden') {return false;}

        // check the link to wowzio.com
        var wowsr = document.getElementById('wow_srbcec');
        if (!wowsr) {return false;}
        if (wowsr.offsetHeight<8) {return false;}
        // if (wowsr.href.indexOf('wowzio.com')<0) { return false; }
        if (wowsr.style.display == 'none' || wowsr.style.visibility == 'hidden'){return false;}

        return true;
      };      window.setTimeout(
        function () {
          if (wow_signHeightCheckbcec() && wow_linkChecksbcec()) {          } else {var el = document.createElement('div'); el.innerHTML="<img src='http://data7.wowzio.com/widgets/ping?ids=38574&wtype=activity&csshash=bcec&notice=sigb'>"; el.style.position='absolute';          }
      }, 4000
    );