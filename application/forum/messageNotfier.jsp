<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<div id="isNewMessage" style="display:none"></div>
<script>
 <logic:present name="principal" >
     var messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";
  </logic:present>
 
<logic:notPresent name="principal" >
  var messageChkURL = "<%=request.getContextPath() %>/forum/checknewmessage.shtml";
  username = readCookie("username");
  if (username != null){//active auto login
     messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";   
  }
</logic:notPresent>

new Ajax.Updater("isNewMessage",  messageChkURL,  {method: 'get', evalScripts: true}); 

</script>