<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<logic:messagesNotPresent>
    <logic:empty name="errors">
    <script>
        window.top.alert("保存OK");
     </script>
   </logic:empty>
</logic:messagesNotPresent>