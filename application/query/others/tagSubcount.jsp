<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page session="false" %>
  

<%@ page contentType="text/html; charset=UTF-8" %>
<logic:greaterThan name="tagForm" property="subscriptionCount" value="0">
<a href="<%=request.getContextPath() %>/social/contentfollower.shtml?subscribedId=<bean:write name="tagForm" property="tagID" />&subject=<bean:write name="tagForm" property="title" />" target="_blank"  rel="nofollow" class="whitelink">
  <bean:write name="tagForm" property="subscriptionCount"/>人关注
</a>
</logic:greaterThan>