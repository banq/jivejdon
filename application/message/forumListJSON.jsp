<%@ page session="false" %>

<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="application/json; charset=UTF-8" %>


[ <logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
<logic:greaterThan name="i" value="0">,</logic:greaterThan>{ "forumId": "<bean:write name="forum"  property="forumId"/>", "name": "<bean:write name="forum"  property="name"/>"}
</logic:iterate>]
