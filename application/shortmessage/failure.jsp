<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ page contentType="text/html; charset=UTF-8"%>



<html:errors/>
<logic:present name="errors">
  <logic:iterate id="error" name="errors">
  <table cellpadding="0" cellspacing="0" border="0"  style="text-align: center"> 
<tr> 
    <td valign="top" > 
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
    
    </td></tr></table>
  </logic:iterate>
</logic:present>