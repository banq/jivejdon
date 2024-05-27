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
         <section> 
         <div class="wrap-vid">              
                <logic:notEmpty name="forumMessage" property="messageUrlVO.imageUrl">                  
                  <div class="thumbn">
                    <img src="<bean:write name="forumMessage" property="messageUrlVO.imageUrl"/>" border='0' class="img-thumbnail" loading="lazy" width="70" height="70" onerror="this.src='//cdn.jdon.com/simgs/thumb/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(9)%>.jpg'"/>    
                  </div>
                </logic:notEmpty>
                 
                <logic:notEmpty name="forumMessage" property="messageUrlVO.thumbnailUrl">                  
                  <div class="thumbn">
                    <img src="<bean:write name="forumMessage" property="messageUrlVO.thumbnailUrl"/>" border='0' class="img-thumbnail" loading="lazy" width="70" height="70"/>                  
                  </div>  
                </logic:notEmpty>              
          </div>
         <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"><bean:write name="forumThread" property="name"/></a></h3>
      
         <div class="info">			 
              <span class="smallgray"><i class="fa fa-calendar"></i>
                <bean:define id="cdate" name="forumThread" property="creationDate" ></bean:define>
                <%String cdateS = (String)pageContext.getAttribute("cdate"); %>
                <time datetime="<%=cdateS.substring(2, 11) %>"><%=cdateS.substring(2, 11) %></time>
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
		    
              <br><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />.             
            </div>

          </section>
</li>

 </logic:notEmpty>
</logic:notEmpty>    
