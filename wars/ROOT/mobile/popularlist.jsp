<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@page import="com.jdon.jivejdon.model.ForumThread"%>
<%@ page session="false"  %>
<%@ include file="header.jsp" %>
  <div data-role="header">
    <h1><b>解道jdon.com</b> </h1>
  </div>  
  <div data-role="content">
    <ul data-role="listview" data-inset="true" data-filter="true">
      <bean:parameter id="count" name="count" value="8"/>
      <%
String coutlength = (String)pageContext.getAttribute("count");
%>
      <logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
        <li><a href="<%=request.getContextPath()%>/mobile/<bean:write name="forumThread" property="threadId"/>" data-ajax="false" >
          <h3>
            <bean:write name="forumThread" property="name" />
          </h3>
          <p>
            <bean:write name="forumThread" property="viewCount" />
            阅
            &nbsp;&nbsp;
            <logic:notEqual name="forumThread" property="rootMessage.digCount" value="0">
              <bean:write name="forumThread" property="rootMessage.digCount"/>
              赞
              &nbsp;&nbsp;   
            </logic:notEqual>
            <logic:greaterThan name="forumThread" property="state.messageCount" value="0">
              <logic:notEmpty name="forumThread" property="state.lastPost">
                <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
                <bean:write name="forumThread" property="state.messageCount" />
                评
                &nbsp;&nbsp;
                最近更新：
                <bean:write name="lastPost" property="account.username" />
                &nbsp;
                <bean:write name="lastPost" property="modifiedDate3" />
              </logic:notEmpty>
            </logic:greaterThan>
          </p>
          </a> <span class="ui-li-count">
          <%
             com.jdon.jivejdon.model.ForumThread thread = (com.jdon.jivejdon.model.ForumThread)pageContext.getAttribute("forumThread");
             int bodylength = thread.getRootMessage().getMessageVO().getBody().length();
             java.text.DecimalFormat df  = new java.text.DecimalFormat("#.000");
             
             %>
          <%=bodylength %> </span> </li>
      </logic:iterate>
    </ul>
    <bean:define id="pagecount" name="threadListForm" property="count" />
    <bean:define id="allCount" name="threadListForm" property="allCount" />
    <%
String preurl = request.getContextPath() + "/mobile/new";
%>
    <%@ include file="pagination.jsp" %>
    <center>
    <a href="http://www.jdon.com/designpatterns/" target="_blank" data-role="button" data-mini="true" data-inline="true">设计模式</a>&nbsp;
    <a href="http://www.jdon.com/ddd.html" target="_blank" data-role="button" data-mini="true" data-inline="true">领域驱动设计</a>&nbsp;
    <a href="http://www.jdon.com/design.htm" target="_blank" data-role="button" data-mini="true" data-inline="true">云架构</a>&nbsp;
    <a href="http://www.jdon.com/framework.html" target="_blank" data-role="button" data-mini="true" data-inline="true">开源框架</a>&nbsp;
    <a href="http://www.jdon.com/course.html" target="_blank" data-role="button" data-mini="true" data-inline="true">培训咨询</a></center>
  </div>
  <!-- /content -->
  
	 <%@include file="js.jsp"%>  
  <%@include file="footer.jsp"%>
