<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html:errors/>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>
</title>
     <%@ include file="../../../common/headerBody.jsp" %>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires" CONTENT="0">   

    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />

<script type='text/javascript'>
 var errorM = null;
<logic:messagesPresent>
 <html:messages id="error">
     errorM = "<bean:write name="error" />";
 </html:messages>
</logic:messagesPresent>

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
      errorM = "<bean:write name="error" />";
  </logic:iterate>
</logic:present>
        
  function killUpdate()
        {       
        if (errorM != null){
           window.parent.myalert(errorM);
        }else{                     
           updateAccountAttachment();
           closeThisWindow();            
           window.top.location.reload();
         }
  }
        
 function updateAccountAttachment(){
     var pars = 'method=updateAccountAttachment&userId=<bean:write name="accountFaceFileForm" property="parentId"/>' ;
     new Ajax.Updater('updateAccountAttachment', '<%=request.getContextPath()%>/account/protected/upload/updateAccountAttachment.shtml', { method: 'get', parameters: pars });
   
 }
        
function closeThisWindow(){ 
  window.top.killUploadWindow();
}        
</script>        
</head>
<body onload='killUpdate();'>
<span id='updateAccountAttachment'></span>

</body>
</html>
