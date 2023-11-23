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
<div class="box">
         <section> 
         <div class="wrap-vid">              
                <logic:notEmpty name="forumMessage" property="messageUrlVO.imageUrl">                  
                  <div class="thumbn">
                       <img src="<bean:write name="forumMessage" property="messageUrlVO.imageUrl"/>" border='0' class="thumbnail" loading="lazy" width="80" height="70" onerror="this.src='/simgs/thumb/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(9)%>.jpg'"/>    
                  </div>
                </logic:notEmpty>
                 
                <logic:notEmpty name="forumMessage" property="messageUrlVO.thumbnailUrl">                  
                  <div class="thumbn">
                    <img src="<bean:write name="forumMessage" property="messageUrlVO.thumbnailUrl"/>" border='0' class="thumbnail" loading="lazy" width="80" height="70"/>                  
                  </div>  
                </logic:notEmpty>              
          </div>
         <h4 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"><bean:write name="forumThread" property="name"/></a></h4>
      
         <div class="info">			 
             <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[80]" />.             
          </div>

          </section>
</div>

 </logic:notEmpty>
 </logic:notEmpty>    

