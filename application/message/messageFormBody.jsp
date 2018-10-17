<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<script src="/common/form.js"></script>
<script src="../common/ckeditor/ckeditor.js"></script>
<div class="box"> 
<div class="row">
	<div class="col-md-12">       
        <div class="form-group"> 
   <html:text styleClass="form-control" property="subject" styleId="replySubject" size="80" maxlength="80" tabindex="5" />
        </div>
    </div>
</div>
    
<div class="row">
	<div class="col-md-12">       
        <div class="form-group"> 
	<html:textarea styleClass="form-control" property="body" cols="100" rows="40"  styleId="formBody" tabindex="6" ></html:textarea>

        </div>
    </div>
</div>

	
<script>
    CKEDITOR.replace( 'formBody', {
        height: 480,
        language: 'zh-cn',
        extraPlugins: 'bbcode,base64image,autosave',  
 	// Remove unused plugins.
        removePlugins: 'filebrowser,format,horizontalrule,pastetext,pastefromword,scayt,showborderstable,tabletools,tableselection,wsc',
					// Remove unused buttons.
        removeButtons: 'Table,paragraph,Anchor,BGColor,Font,Strike,Subscript,Superscript',
					// Width and height are not supported in the BBCode format, so object resizing is disabled.
		disableObjectResizing: true,
                
        toolbar :
            [
                 [ 'Bold', 'Italic', 'Underline', 'Image','base64image','Styles','NumberedList','BulletedList','CodeSnippet','Link','Unlink','Maximize','Source' ]
            ]
            } );
    
</script>
    
<div class="row">
<!--  autocomplete.js -->
<link rel="stylesheet" href="/common/jivejdon5.css"  type="text/css">
	<script>
	
function loadAcJS(thisId){
  if (typeof(ac) == 'undefined') {
     $LAB
     .script('/common/js/prototype.js')
     .script('/common/js/autocomplete.js')
     .wait(function(){
          ac(thisId,'<%=request.getContextPath()%>');
     })     
  }else
      ac(thisId,'<%=request.getContextPath()%>');
}
		
		
	</script>
    <div class="col-md-3">       
        <div class="form-group"> 
	<input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_0" onfocus="javascript:loadAcJS(this.id)" value='' />
        </div>
    </div>
    <div class="col-md-3">       
        <div class="form-group"> 
	<input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_1" onfocus="javascript:loadAcJS(this.id)"   value=''/>	
        </div>
    </div>
    <div class="col-md-3">       
        <div class="form-group">     
    <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_2" onfocus="javascript:loadAcJS(this.id)"  value=''/>
        </div>
    </div>
    <div class="col-md-3">       
        <div class="form-group"> 

    <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_3" onfocus="javascript:loadAcJS(this.id)"  value=''/>
    <logic:notEmpty name="messageForm" property="forumThread.tags">
         <logic:iterate id="threadTag" name="messageForm" property="forumThread.tags" indexId="i">
           <script>
             document.getElementById('searchV_<bean:write name="i"/>').value ='<bean:write name="threadTag" property="title" />' 
           </script>
        </logic:iterate>
     </logic:notEmpty>        
		<span id='json_info'></span>

<div id="insertImage" style='display: none'>
<img src="/images/logout.gif" width="23" height="22" border="0" />
</div>
        </div>
    </div>
</div>
</div>
   
