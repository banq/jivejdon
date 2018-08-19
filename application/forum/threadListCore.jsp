<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="list-group">

 <logic:iterate indexId="i" id="forumThread" name="threadListForm"
                   property="list">
       <bean:define id="forumMessage" name="forumThread"
                             property="rootMessage"></bean:define>
      	<a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" class="list-group-item" target="_blank">
		<h4 class="list-group-item-heading">
			<bean:write name="forumThread" property="name"/>
		</h4>
		
		<p class="list-group-item-text smallgray">       	
			    <span class="fa fa-user">
					<bean:write name="forumMessage" property="account.username"/>
				</span>
               
			 
		     <span class="fa fa-calendar">
                <bean:define id="cdate" name="forumThread" property="creationDate" ></bean:define>
        <%String cdateS = (String)pageContext.getAttribute("cdate"); %><%=cdateS.substring(2, 11) %>
              </span>
			
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
                <logic:notEmpty name="forumThread" property="state.lastPost">
                    <bean:define id="lastPost" name="forumThread"
                                 property="state.lastPost"/>
					<span class="badge">
                     <bean:write name="lastPost" property="account.username"/>
				     于<bean:write name="lastPost" property="modifiedDate3"/>
					</span>
               </logic:notEmpty>
            </logic:greaterThan>
		  
	    </p>
    </logic:iterate>

</div>
	