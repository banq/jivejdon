<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page session="false" %>


<%@ page contentType="application/json; charset=UTF-8" %>
[
  <logic:iterate id="threadTag" name="TAGS" length="1" >
  "<bean:write name="threadTag" property="title" />"
  </logic:iterate>
  <logic:iterate id="threadTag" name="TAGS" offset="1" >,
  "<bean:write name="threadTag" property="title" />"
  </logic:iterate>
]