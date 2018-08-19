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
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>
</title>
<%--  
<meta http-equiv="refresh" content="0;url=<html:rewrite page="/message/upload/uploadAction.shtml" paramId="parentId" paramName="upLoadFileForm" paramProperty="parentId" />">
 --%>
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
           window.parent.location.href="<html:rewrite page="/message/upload/uploadAction.shtml" paramId="parentId" paramName="upLoadFileForm" paramProperty="parentId" />"; }
        }
        
</script>        
</head>
<body onload='killUpdate();'>

</body>
</html>
