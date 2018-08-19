<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<bean:parameter name="queryType" id="queryType" value=""/>
<bean:parameter name="tagID" id="tagID" value=""/>


<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">

<%
String titleStr = (String)request.getAttribute("TITLE");
pageContext.setAttribute("title", titleStr);
%>
<%@ include file="header.jsp" %>

<div data-role="header">
<logic:notEmpty  name="TITLE">
  <h3><bean:write  name="TITLE"  />
  </h3>
</logic:notEmpty>
</div><!-- /header -->


<div data-role="content">	
		
		<ul data-role="listview" data-inset="true" data-filter="true">

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
    
    <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
    <li><a href="<%=request.getContextPath()%>/mobile/<bean:write name="forumThread" property="threadId"/>"  target="_blank" data-ajax="false" >
            <h3> <bean:write name="forumThread" property="name" /></h3>
<p>
<bean:write name="forumThread" property="state.messageCount" />评论
&nbsp;&nbsp;
<bean:write name="forumThread" property="viewCount" />浏览
&nbsp;&nbsp;
<logic:greaterThan name="forumThread" property="state.messageCount" value="0">
    <logic:notEmpty name="forumThread" property="state.lastPost">                        
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>                        
            最近更新：
            <bean:write name="lastPost" property="account.username" />
            &nbsp;
            <bean:write name="lastPost" property="modifiedDate3" />          
    </logic:notEmpty>
</logic:greaterThan>        

 </p>             
   </a>
   
	<span class="ui-li-count">
             <%
             com.jdon.jivejdon.model.ForumThread thread = (com.jdon.jivejdon.model.ForumThread)pageContext.getAttribute("forumThread");
             int bodylength = thread.getRootMessage().getMessageVO().getBody().length();
             java.text.DecimalFormat df  = new java.text.DecimalFormat("#.000");
             
             %>
             <%=bodylength %>
    </span>   
   </li> 
   
</logic:iterate>

  
</ul>


<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="allCount" name="threadListForm" property="allCount" />
<%
String tagIDS = (String)pageContext.getAttribute("tagID");
String preurl = request.getContextPath() + "/mobile/tags/" + tagIDS;
%>
<%@ include file="pagination.jsp" %>        
        

</logic:greaterThan>
</logic:present>

        
</div><!-- /content -->
 <%@include file="js.jsp"%>  
<%@include file="footer.jsp"%> 


