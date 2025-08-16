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
         <h3 class="vid-name" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
          <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" onclick="showDialog('dialog2', '<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html#messageListBody');return false;" class="hover-preload"><bean:write name="forumThread" property="name"/></a>
        </h3>
      
         <div class="info">			 
              <span class="smallgray"><i class="fa fa-calendar"></i>
                <bean:write name="forumMessage" property="modifiedDate3"/>
              </span>
			 <logic:notEqual name="forumThread" property="state.messageCount" value="0">
              <span class="smallgray"><i class="fa fa-comment"></i> <bean:write name="forumThread" property="state.messageCount" />
                      </span>
		       </logic:notEqual>  
              <span class="smallgray"><i class="fa fa-eye"></i><bean:write name="forumThread" property="viewCount" />
                      </span>
			   <logic:notEqual name="forumMessage" property="digCount" value="0">
                       <span class="smallgray"><i class="fa fa-heart"></i>
                      <bean:write name="forumMessage" property="digCount"/>
					   </span>
                      </logic:notEqual>     

            <logic:greaterThan name="forumMessage" property="messageVO.bodyLengthK" value="1">
                <span class="smallgray"><bean:write name="forumMessage" property="messageVO.bodyLengthK"/>K</span>
            </logic:greaterThan>   
                      
            </div>
          <div class="wrap-vid" style="display: flex; align-items: center;">              
                <logic:notEmpty name="forumMessage" property="messageUrlVO.imageUrl">                  
                  <div>
                    <img src="<bean:write name="forumMessage" property="messageUrlVO.imageUrl"/>" border='0' class="img-thumbnail" loading="lazy" onerror="this.src='/simgs/thumb/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(3)%>.jpg'"/>    
                  </div>
                  <div>
                     <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />. 
                  </div>
                </logic:notEmpty>
                 
                <logic:notEmpty name="forumMessage" property="messageUrlVO.thumbnailUrl">                  
                  <div class="thumbn">
                    <img src="<bean:write name="forumMessage" property="messageUrlVO.thumbnailUrl"/>" border='0' class="img-thumbnail" loading="lazy" width="45" height="45"/>                  
                  </div>  
                   <span class="smallgray" style="margin-left: 2px; flex: 1;white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                    <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />. 
                  </span>
                          
                </logic:notEmpty>              
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