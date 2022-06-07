 <%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
 <bean:define id="forumMessage" name="forumThread"
                property="rootMessage"></bean:define>
   <logic:notEmpty name="forumMessage" property="account">
     <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" class="list-group-item">
		<h3 class="list-group-item-heading">        
			<bean:write name="forumThread" property="name"/>
		</h3>
		<p class="list-group-item-text smallgray">       	
			    <span class="fa fa-user">
					<bean:write name="forumMessage" property="account.username"/>
				</span>
               
			 
		     <span class="fa fa-calendar">
               <bean:write name="forumMessage" property="modifiedDate3"/>
              </span>
            
            <%
             com.jdon.jivejdon.domain.model.ForumThread thread = (com.jdon.jivejdon.domain.model.ForumThread)pageContext.getAttribute("forumThread");
             int bodylength = thread.getRootMessage().getMessageVO().getBody().length();
             java.text.DecimalFormat df  = new java.text.DecimalFormat("#.000");
             int bl = bodylength/1024;
             if (bl >0){
             %>
               <span class="fa fa-print"> <%=bl %>k </span>
             <% }%>
			
			 <logic:notEqual name="forumThread" property="state.messageCount" value="0">
              <span><i class="fa fa-comment"></i> <bean:write name="forumThread" property="state.messageCount" />
                      </span>
		       </logic:notEqual>  
			
              <span class="fa fa-eye">
				  <bean:write name="forumThread" property="viewCount" />
                      </span>
			
			   <logic:notEqual name="forumMessage" property="digCount" value="0">
                       <span class="fa fa-heart">
                      <bean:write name="forumMessage" property="digCount"/>
					   </span>
               </logic:notEqual>     
			
		        
		       <logic:greaterThan name="forumThread"
                                   property="state.messageCount" value="0">
                <logic:notEmpty name="forumThread" property="state.latestPost">
                    <bean:define id="latestPost" name="forumThread"
                                 property="state.latestPost"/>
					<span class="badge">
                     <bean:write name="latestPost" property="account.username"/>
				     于<bean:write name="latestPost" property="modifiedDate3"/>
					</span>
               </logic:notEmpty>
            </logic:greaterThan>
		  
	    </p>
        </a>
   </logic:notEmpty>