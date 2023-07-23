<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!-- Support for non-traditional but simple message -->

<logic:present name="message"> <b><font color="BLUE">
  <bean:write name="message" />
  </font></b> </logic:present>

<!-- Support for non-traditional but simpler use of errors... -->
<logic:present name="errors">

    <table cellpadding="0" cellspacing="0" border="0"  align="center">
      <tr>
        <td valign="top" ><B><FONT color=RED> <BR>
          <bean:write name="errors" />
          </FONT></B></td>
      </tr>
    </table>
  
</logic:present>

