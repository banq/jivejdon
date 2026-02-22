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
<li itemprop="itemListElement" itemscope itemtype="https://schema.org/ListItem">
    <div class="box"> 
         <div class="wrap-vid">              
          <logic:empty name="forumMessage" property="messageUrlVO.thumbnailUrl">                  
            <div class="thumbn">
              <img src="/simgs/thumb/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(3)%>.jpg" border='0' class="img-thumbnail" loading="lazy" width="55" height="55"/>
            </div>
          </logic:empty>
                            
                <logic:notEmpty name="forumMessage" property="messageUrlVO.thumbnailUrl">                  
                  <div class="thumbn">
                    <img src="<bean:write name="forumMessage" property="messageUrlVO.thumbnailUrl"/>" border='0' class="img-thumbnail" loading="lazy" width="55" height="55"/>                  
                  </div>  
                </logic:notEmpty>              
          </div>
         <h4 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html"  title="<bean:write name="forumMessage" property="modifiedDate3"/>" class="hover-preload" itemprop="url"><span itemprop="name" style="color: #333;"><bean:write name="forumThread" property="name"/></span></a></h4>

    </div>
</li>

 </logic:notEmpty>
 </logic:notEmpty>    

