<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page isELIgnored="false" %>
<%@ page session="false" %>

<logic:notEmpty name="forumThread">
<logic:notEmpty name="forumThread" property="rootMessage">
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
<bean:define id="body" name="forumMessage" property="messageVO.body" />

<li class="box">
      <div style="display: flex; flex-wrap: wrap; align-items: baseline; gap: 10px;">   
         <h3 class="vid-name"><a href="<%=com.jdon.jivejdon.util.ToolsUtil.getAppURL(request)%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" class="hover-preload"><bean:write name="forumThread" property="name"/></a></h3>
      
          <div class="info" style="display: flex; flex-wrap: wrap; align-items: center; gap: 10px;">
            
             <logic:greaterThan name="forumMessage" property="messageVO.bodyLengthK" value="1">
                <span class="smallgray"><bean:write name="forumMessage" property="messageVO.bodyLengthK"/>K</span>
             </logic:greaterThan>     

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

		    
             
            </div>
      </div>  
</li>

 </logic:notEmpty>
</logic:notEmpty>    
