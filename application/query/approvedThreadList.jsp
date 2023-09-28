<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list">
   <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
   <bean:define id="thumbthreadId" name="forumThread" property="threadId"/>
   
<%
  if(i % 2==0){ 
 %>
 <div class="row">	
 <%}%>

 <div class="col-md-6">
 <div class="box">	
  <div class="linkblock">
    <div class="box">	           
      <div class="zoom-container">
			      	<div>
               <% String thumbthreadIdS = ((Long)pageContext.getAttribute("thumbthreadId")).toString(); %>               
                <logic:notEmpty name="forumMessage" property="messageUrlVO.imageUrl">        
                  <figure>          
                    <img id="home-thumbnai" src="<bean:write name="forumMessage" property="messageUrlVO.imageUrl"/>" border="0" class="thumbnail" fetchpriority="high"/>
                  </figure>
                </logic:notEmpty>
                <logic:empty name="forumMessage" property="messageUrlVO.imageUrl">        
                    <img id="home-thumbnai" src="/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(49)%>.jpg" border="0" class="thumbnail" fetchpriority="high"/> 
                </logic:empty>                        
              </div>
			      </div>
             <bean:define id="body" name="forumMessage" property="messageVO.body" />

         <section class="widget">
         <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"  target="_blank"><bean:write name="forumThread" property="name"/></a></h3>
      
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
                     
            </div>
        
          <div class="wrap-vid">
              <p><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />. <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" target="_blank" class="smallgray">详细</a></p>
          </div>
      </section>
   	</div>	
	</div>	
</div>  
</div>

 
<% i = i+1;%>
<%
  if(i % 2==0){ 
 %>
  </div>
 <%}%>



</logic:iterate>
