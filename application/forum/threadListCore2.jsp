<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
   <div class="box"> 
  <div class="linkblock" itemscope itemtype="http://schema.org/BlogPosting">
  <div class="row">
        <div class="col-sm-12">
       <div class="box">
             <bean:define id="body" name="forumMessage" property="messageVO.body" />

         <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>"><bean:write name="forumThread" property="name"/></a></h3>
      
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
			 <span><i class="fa fa-user"></i><bean:write name="forumThread" property="rootMessage.account.username" />
               </span>
                      
            </div>
          <div class="wrap-vid">
              <div class="thumbn">

                <logic:notEmpty name="forumMessage" property="messageUrlVO.thumbnailUrl">
                  <img src="<bean:write name="forumMessage" property="messageUrlVO.thumbnailUrl"/>" border='0' class="thumbnail" loading="lazy" width="100" height="70"/>
                </logic:notEmpty>
              </div>
              <p><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />. <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" target="_blank" class="smallgray">详细</a></p>
      </div>
  
             </div>
  </div>
</div>
</div>
</div>