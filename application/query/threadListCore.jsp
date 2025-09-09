<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>

<logic:notEmpty name="forumThread">
<logic:notEmpty name="forumThread" property="rootMessage">

<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
<bean:define id="body" name="forumMessage" property="messageVO.body" />
<%
  if(i % 3==0){ 
 %>
 <div class="row">	
 <%}%>

 <div class="col-lg-4" style="padding:0px">
 <li class="box">	
  <div class="linkblock">
     <div class="box">	             
         <h3 class="vid-name" >
          <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" onclick="showDialog('dialog2', '<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html#messageListBody');return false;" class="hover-preload"><bean:write name="forumThread" property="name"/></a>
        </h3>
         <div class="info smallgray" style="display:flex; align-items:center; justify-content:space-between; overflow:hidden;">              
            <span style="flex:1; overflow:hidden; text-overflow:ellipsis; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical;">           
              <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />. 
             </span>  
              <img src="https://static.jdon.com/simgs/thumb/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(3)%>.jpg" alt="icon"  style="width:70px; height:70px; margin-left:12px; flex-shrink:0; object-fit:cover; border-radius:6px;">
            </div>
	 </div>	
  </div>	
</li>  
</div>

 
<% i = i+1;%>
<%
  if(i % 3==0){ 
 %>
  </div>
 <%}%>

  
 </logic:notEmpty>
 </logic:notEmpty>    