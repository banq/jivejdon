<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<div>
<bean:parameter id="start" name="start" value="0"/>
<bean:parameter id="count" name="count" value="1"/>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=count%>' offset="<%=start%>" >
  <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
  <div class="box"> 
    <div class="linkblock">
      <div class="row">
        <div class="col-sm-12">
          
            <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"><bean:write name="forumThread" property="name"/></a></h3>
            <div class="wrap-vid">
              <div class="thumbn">
                <logic:notEmpty name="forumMessage" property="messageUrlVO.thumbnailUrl">
                  <img src="<bean:write name="forumMessage" property="messageUrlVO.thumbnailUrl"/>" border='0' class="thumbnail" loading="lazy"/>
                </logic:notEmpty>
              </div>  
              <div class="widget">			 
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
                </div>  
              </div>
            </div>   
          
        </div>       
      </div>
    </div>
  </div> 
</logic:iterate>
</div>
