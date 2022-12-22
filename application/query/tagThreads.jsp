<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:size id="threadCount" name="threadListForm" property="list" />
<logic:notEqual name="threadCount" value="0">

    <bean:parameter id="tablewidth" name="tablewidth" value="155"/>
    <bean:parameter id="tagID" name="tagID" value=""/>


    <bean:parameter id="count" name="count" value="8"/>
    <%
        String coutlength = (String)pageContext.getAttribute("count");
    %>

    <logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
        <li class="fa fa-circle-o"> <a  class="smallgray" href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"
               title="<bean:write name="forumThread" property="name" />" target="_blank">
                <bean:write name="forumThread" property="name" /></a>
        </li>
    </logic:iterate>



</logic:notEqual>