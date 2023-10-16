<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%-- 这里是因为被tagsList.jsp的bean:include调用，只能是gbk，不能为正常utf-8，否则乱码 --%>    
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<logic:empty name="threadTag">
  <%response.setStatus(HttpServletResponse.SC_NOT_FOUND);%>
</logic:empty>
<logic:notEmpty name="threadTag">

<bean:parameter id="count" name="count" value="4"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>
<div class="widget">
<ul>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
 <bean:define id="body" name="forumMessage" property="messageVO.body" />
<logic:equal name="i" value="0">
<li class="info">
	<h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"><bean:write name="forumThread" property="name"/></a></h3>
      
         <div class="info">			 
              <span><i class="fa fa-calendar"></i>
                <bean:define id="cdate" name="forumThread" property="creationDate" ></bean:define>
                <%String cdateS = (String)pageContext.getAttribute("cdate"); %>
                <time datetime="<%=cdateS.substring(2, 11) %>"><%=cdateS.substring(2, 11) %></time>
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

            <logic:greaterThan name="forumMessage" property="messageVO.bodyLengthK" value="1">
                <span><bean:write name="forumMessage" property="messageVO.bodyLengthK"/>K</span>
            </logic:greaterThan>     
		    
              <br><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />.             
            </div>
</li>
</logic:equal>
<logic:greaterThan name="i" value="0">
<li class="info">
	  <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" title="<bean:write name="forumThread" property="rootMessage.messageVO.shortBody[80]" />" ><bean:write name="forumThread" property="name"/></a></h3>
</li>
</logic:greaterThan>
</logic:iterate>
</ul>
</div>
</logic:notEmpty>