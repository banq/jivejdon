<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>


<html:errors />

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
  <table cellpadding="0" cellspacing="0" border="0"  align="center"> 
<tr> 
    <td valign="top" > 
    <B><FONT color=RED>
      <BR><bean:write name="error" />      
    </FONT></B>
     <script>
       alert(<bean:write name="error" />);
     </script>  
    
    </td></tr></table>
  </logic:iterate>
</logic:present>
