<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page session="false" %>

  
<%  
int expire = 5 * 60 * 60;
long modelLastModifiedDate = com.jdon.jivejdon.presentation.action.util.ForumUtil.getForumsLastModifiedDate(this.getServletContext());
if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
	return ;
}

%>

<%@ page contentType="application/json; charset=UTF-8" %>
[
  <logic:iterate id="threadTag" name="TAGS" length="1" >
  "<bean:write name="threadTag" property="title" />"
  </logic:iterate>
  <logic:iterate id="threadTag" name="TAGS" offset="1" >,
  "<bean:write name="threadTag" property="title" />"
  </logic:iterate>
]