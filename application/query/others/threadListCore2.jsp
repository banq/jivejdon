<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
  <div class="box"> 
    <div class="linkblock">
     <div class="row">
      <div class="col-sm-12">
        <div class="box">
             <bean:define id="body" name="forumMessage" property="messageVO.body" />

         <section> 
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
			       <%
             com.jdon.jivejdon.domain.model.ForumThread thread = (com.jdon.jivejdon.domain.model.ForumThread)pageContext.getAttribute("forumThread");
             int bodylength = thread.getRootMessage().getMessageVO().getBody().length();             
             int bl = bodylength/1024;
             if (bl >0){
             %>
               <span class="fa fa-print"> <%=bl %>k </span>
             <% }%>
                      
            </div>

          </section>
  
    </div>
  </div>
</div>
</div>
</div>
