// bbCode control by
// subBlue design
// www.subBlue.com
var imageTag = false;
var theSelection = false;
var pollOptionCount = -1;

var clientPC = navigator.userAgent.toLowerCase(); // Get client info
var clientVer = parseInt(navigator.appVersion); // Get browser version

var is_ie = ((clientPC.indexOf("msie") != -1) && (clientPC.indexOf("opera") == -1));
var is_nav  = ((clientPC.indexOf('mozilla')!=-1) && (clientPC.indexOf('spoofer')==-1)
	&& (clientPC.indexOf('compatible') == -1) && (clientPC.indexOf('opera')==-1)
	&& (clientPC.indexOf('webtv')==-1) && (clientPC.indexOf('hotjava')==-1));

var is_win   = ((clientPC.indexOf("win")!=-1) || (clientPC.indexOf("16bit") != -1));
var is_mac    = (clientPC.indexOf("mac")!=-1);

// Define the bbCode tags
bbcode = new Array();
bbtags = new Array('[b]','[/b]','[i]','[/i]','[u]','[/u]','[quote]','[/quote]','[code]','[/code]','[list]','[/list]',
	'[img]','[/img]','[url]','[/url]', '[google]', '[/google]', '[youtube]', '[/youtube]', '[flash]', '[/flash]', '[wmv]', '[/wmv]');
imageTag = false;

var openBBtags = new Array();

function tmOpenTag(eltag) {
   if (bbtags[eltag+1] != '') {
      openBBtags[openBBtags.length] = eltag;
      //Add '*' in the button's text:
      eval('document.post.addbbcode'+eltag+'.value += "*"');
   }
}

function tmQuitTag(eltag) {
   for (i = 0; i < openBBtags.length; i++) {
      if (openBBtags[i] == eltag) {
         openBBtags.splice(i, 1);
         //Remove '*' from the button's text:
         buttext = eval('document.post.addbbcode' + eltag + '.value');
         eval('document.post.addbbcode' + eltag + '.value ="' + buttext.substr(0,(buttext.length - 1)) + '"');
      }
   }
}

function tmIsTagOpen(eltag) {
   var tag = 0;
   for (i = 0; i < openBBtags.length; i++) {
      if (openBBtags[i] == eltag) { tag++; }
   }
   if (tag > 0) { return true;   } else { return false; }
}

function tmCloseTags() {
   var count = openBBtags.length;
   for (n = 0; n < count; n++) { bbstyle(openBBtags[openBBtags.length - 1]); }
}

// Shows the help messages in the helpline window
function helpline(help) {
   document.post.helpbox.value = eval(help + "_help");
}


// Replacement for arrayname.length property
function getarraysize(thearray) {
	for (i = 0; i < thearray.length; i++) {
		if ((thearray[i] == "undefined") || (thearray[i] == "") || (thearray[i] == null))
			return i;
		}
	return thearray.length;
}

// Replacement for arrayname.push(value) not implemented in IE until version 5.5
// Appends element to the array
function arraypush(thearray,value) {
	thearray[ getarraysize(thearray) ] = value;
}

// Replacement for arrayname.pop() not implemented in IE until version 5.5
// Removes and returns the last element of an array
function arraypop(thearray) {
	thearraysize = getarraysize(thearray);
	retval = thearray[thearraysize - 1];
	delete thearray[thearraysize - 1];
	return retval;
}

function bbplace(text) {
    var txtarea = document.post.body;
    if (txtarea.createTextRange && txtarea.caretPos) {
        var caretPos = txtarea.caretPos;
        caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == ' ' ? caretPos.text + text + ' ' : caretPos.text + text;
        txtarea.focus();
    } else if (txtarea.selectionStart || txtarea.selectionStart == '0') {
        var startPos = txtarea.selectionStart;
        var endPos = txtarea.selectionEnd;
        txtarea.value = txtarea.value.substring(0, startPos)
                      + text
                      + txtarea.value.substring(endPos, txtarea.value.length);
        txtarea.focus();
        txtarea.selectionStart = startPos + text.length;
        txtarea.selectionEnd = startPos + text.length;
    } else {
        txtarea.value  += text;
        txtarea.focus();
    }
}

function emoticon(text) {
   text = ' ' + text;
   bbfontstyle(text,'');
   return;
}

function bbfontstyle(bbopen,bbclose) {
var txtarea = document.post.body;

//IE
if (document.selection)
  { txtarea.focus();
    sel = document.selection.createRange();
    sel.text = bbopen+sel.text+bbclose;
  }
//Mozilla-Netscape
else if (txtarea.selectionStart || txtarea.selectionStart == '0') {
   var startPos = txtarea.selectionStart;
   var endPos = txtarea.selectionEnd;
   var cursorPos = endPos;
   var scrollTop = txtarea.scrollTop;

   if (startPos != endPos) {
      txtarea.value = txtarea.value.substring(0, startPos)
                 + bbopen
                 + txtarea.value.substring(startPos, endPos)
                 + bbclose
                 + txtarea.value.substring(endPos, txtarea.value.length);
      cursorPos += bbopen.length + bbclose.length;
   }
   else {

         txtarea.value = txtarea.value.substring(0, startPos)
                  + bbopen+' '+bbclose
                  + txtarea.value.substring(endPos, txtarea.value.length);
      cursorPos = startPos + bbopen.length+bbclose.length+1;
   }
   txtarea.focus();
   txtarea.selectionStart = cursorPos;
   txtarea.selectionEnd = cursorPos;
   txtarea.scrollTop = scrollTop;
}
 else {   txtarea.value += bbopen+' '+bbclose;
   txtarea.focus();
    }
}

function bbstyle(eltag) {
   var txtarea = document.post.body;

   if (eltag == -1) { tmCloseTags(); return; }

   //IE
   if (document.selection) {
       txtarea.focus();
       sel = document.selection.createRange();
      if (sel.text.length > 0) { sel.text = bbtags[eltag] + sel.text + bbtags[eltag+1]; }
      else {
            if (!tmIsTagOpen(eltag) || bbtags[eltag+1] == '') {
            sel.text = bbtags[eltag];
            tmOpenTag(eltag);
          }
          else {
               sel.text = bbtags[eltag+1];
               tmQuitTag(eltag);
               }
      }
      txtarea.focus();
   }
   //Mozilla-Netscape
   else if (txtarea.selectionStart || txtarea.selectionStart == '0') {
      var startPos = txtarea.selectionStart;
      var endPos = txtarea.selectionEnd;
      var cursorPos = endPos;
      var scrollTop = txtarea.scrollTop;

      if (startPos != endPos) {
         txtarea.value = txtarea.value.substring(0, startPos)
                       + bbtags[eltag]
                       + txtarea.value.substring(startPos, endPos)
                       + bbtags[eltag+1]
                       + txtarea.value.substring(endPos, txtarea.value.length);
         cursorPos += bbtags[eltag].length + bbtags[eltag+1].length;
      }
      else {
         if (!tmIsTagOpen(eltag) || bbtags[eltag+1] == '') {
            txtarea.value = txtarea.value.substring(0, startPos)
                          + bbtags[eltag]
                          + txtarea.value.substring(endPos, txtarea.value.length);
            tmOpenTag(eltag);
            cursorPos = startPos + bbtags[eltag].length;
         }
         else {
            txtarea.value = txtarea.value.substring(0, startPos)
                          + bbtags[eltag+1]
                          + txtarea.value.substring(endPos, txtarea.value.length);
            tmQuitTag(eltag);
            cursorPos = startPos + bbtags[eltag+1].length;
         }
      }
      txtarea.focus();
      txtarea.selectionStart = cursorPos;
      txtarea.selectionEnd = cursorPos;
      txtarea.scrollTop = scrollTop;
   }
   else {
      if (!tmIsTagOpen(eltag) || bbtags[eltag+1] == '') {
         txtarea.value += bbtags[eltag];
         tmOpenTag(eltag);
      }
      else {
         txtarea.value += bbtags[eltag+1];
         tmQuitTag(eltag);
      }
      txtarea.focus();
   }
}

// Insert at Claret position. Code from
// http://www.faqts.com/knowledge_base/view.phtml/aid/1052/fid/130
function storeCaret(textEl) {
	if (textEl.createTextRange) textEl.caretPos = document.selection.createRange().duplicate();
}

// Depends of jquery.js
function previewMessage()
{
	var f = document.post;

	var p = { 
		text:f.body.value, 
		subject:f.subject.value, 
		html:!f.disable_html.checked, 
		bbcode:!f.disable_bbcode.checked, 
		smilies:!f.disable_smilies.checked 
	};

	$.ajax({
		type:"POST",
		url:CONTEXTPATH + "/jforum" + SERVLET_EXTENSION + "?module=ajax&action=previewPost",
		data:p,
		dataType:"script",
		global:false
	});
}

function incrementPollOptionCount()
{
	pollOptionCount++;
	document.getElementById("pollOptionCount").value = pollOptionCount;
}

function initPollOptionCount()
{
	if (pollOptionCount == -1) {
		var countField = document.getElementById("pollOptionCount");
		
		if (countField != null) {
			pollOptionCount = parseInt(countField.value);
		} 
		else {
			pollOptionCount = 1;
		}
	}
}

function deletePollOption(button)
{
	initPollOptionCount();
	
	var node = button.parentNode;
	while (node != null) {
		if (node.id == "pollOption") {
			node.parentNode.removeChild(node);
			break;
		}

		node = node.parentNode;
	}
}

function addPollOption()
{
	initPollOptionCount();
	incrementPollOptionCount();

	var addOption = document.getElementById("pollOptionWithAdd");
	var deleteOption = document.getElementById("pollOptionWithDelete");
	var newOption = deleteOption.cloneNode(true);
	
	if (is_nav) {
		newOption.style.display = "table-row";
	} 
	else {
		newOption.style.display = "block";
	}
	
	newOption.id = "pollOption";
	
	var newTextField = newOption.getElementsByTagName("input")[0];
	var addTextField = newOption.getElementsByTagName("input")[1];
	
	//copy the active text data to the inserted option
	newTextField.id = "pollOption" + pollOptionCount;
	newTextField.name = "poll_option_" + pollOptionCount;
	newTextField.value = "";
	
	//clear out the last text field and increment the id
	addTextField.id = "pollOption" + pollOptionCount;
	addTextField.name = "poll_option_" + pollOptionCount;
	
	addOption.parentNode.insertBefore(newOption, addOption);
	addTextField.focus();
}


function checkForm() {

   formErrors = false;

   if (document.post.body.value.length < 2) {
      formErrors = "You must enter a message when posting.";
   }

   if (formErrors) {
      alert(formErrors);
      return false;
   } else {
      bbstyle(-1);
      return true;
   }
}

function activateTab(name, currentLi)
{
	$("#tabs10 > ul > li").each(function() {
		var targetName = $(this).attr("target");
		var target = $("#" + targetName);

		if (target.length && name != targetName) {
			target.hide();
			$(this).removeClass("current");
		}
	});


	$("#" + name).show();
	$(currentLi).parent().addClass("current");
}

function limitURLSize()
{
	$(".snap_shots").each(function () {
		var value = $(this).text();
		
		if (value.length > 80) {
			$(this).text(value.substring(0, 50) + "..." + value.substring(value.length - 30));
		}
	});
}
