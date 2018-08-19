<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
   <div class="box"> 
  <div class="linkblock" itemscope itemtype="http://schema.org/BlogPosting">
  <div class="row">
        <div class="col-sm-12">
       <div class="box">
             <bean:define id="body" name="forumMessage" property="messageVO.body" />
              <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" target="_blank" >
             <h3  class="vid-name"><bean:write name="forumThread" property="name" /></h3></a>      
      
         <div class="info">			 
              <span><i class="fa fa-calendar"></i>
                <bean:define id="cdate" name="forumThread" property="creationDate" ></bean:define>
        <%String cdateS = (String)pageContext.getAttribute("cdate"); %><%=cdateS.substring(2, 11) %>
                      </span>
			 <logic:notEqual name="forumThread" property="state.messageCount" value="0">
              <span><i class="fa fa-comment"></i> <bean:write name="forumThread" property="state.messageCount" />
                      </span>
		       </logic:notEqual>  
              <span><i class="fa fa-eye"></i><bean:write name="forumThread" property="viewCount" />
                      </span>
			   <logic:notEqual name="forumMessage" property="digCount" value="0">
                       <span><i class="fa fa-heart"></i>
                      <bean:write name="forumMessage" property="digCount"/>
					   </span>
                      </logic:notEqual>     
			 <span><i class="fa fa-user"></i><a href="<%=request.getContextPath()%>/blog/<bean:write name="forumThread" property="rootMessage.account.username"/>" class="smallgray"><bean:write name="forumThread" property="rootMessage.account.username" /></a>
               </span>
                      
            </div>
          <div class="wrap-vid">
              <div class="thumbn">
           
       <logic:notEmpty name="forumMessage" property="messageVO.thumbnailUrl">
          <img  src="<bean:write name="forumMessage" property="messageVO.thumbnailUrl"/>" border='0' class="thumbnail" height="150" />
       </logic:notEmpty>
              </div>
              <p><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />. <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" target="_blank" class="smallgray">详细</a></p>
      </div>
  
             </div>
  </div>
</div>
</div>
</div>
</logic:iterate>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="smallgray" align="center"><div class="tres" >
        <MultiPagesREST:pager actionFormName="threadListForm" page="/approval" >
          <MultiPagesREST:prev name=" 上一页 " />
          <MultiPagesREST:index displayCount="3" />
          <MultiPagesREST:next  name=" 下一页 " />
        </MultiPagesREST:pager>
      </div></td>
  </tr>
</table>
