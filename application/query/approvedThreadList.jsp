<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='1' >
    <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
  <bean:define id="thumbthreadId" name="forumThread" property="threadId" toScope="application"/>
     <div class="box"> 
  <div class="linkblock" itemscope itemtype="http://schema.org/BlogPosting">
  <div class="row">
        <div class="col-sm-12">
       <div class="box">
            <div class="zoom-container">
				<div>
               <logic:notEmpty name="forumMessage" property="messageUrlVO.thumbnailUrl">
                  <logic:match name="forumMessage" property="messageUrlVO.thumbnailUrl" value="/simgs/thumb/">
                    <img id="home-thumbnai" src="" border='0' class="thumbnail" width="610"/>
                  </logic:match>
                  <logic:notMatch name="forumMessage" property="messageUrlVO.thumbnailUrl" value="/simgs/thumb/">
                  <img src="<bean:write name="forumMessage" property="messageUrlVO.thumbnailUrl"/>" border='0' class="thumbnail" width="610" loading="lazy"/>
                  </logic:notMatch>
               </logic:notEmpty>
               <logic:empty name="forumMessage" property="messageUrlVO.thumbnailUrl">
                  <img id="home-thumbnai" src="" border='0' class="thumbnail" width="610" loading="lazy"/>
               </logic:empty>
                  
            </div>
			</div>
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
			 <span><i class="fa fa-user"></i><a href="<%=request.getContextPath()%>/blog/<bean:write name="forumThread" property="rootMessage.account.username"/>" class="smallgray"><bean:write name="forumThread" property="rootMessage.account.username" /></a>
               </span>
                      
            </div>
        
          <div class="wrap-vid">
              <p><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />. <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" target="_blank" class="smallgray">详细</a></p>
      </div>
  
             </div>
  </div>
</div>
</div>
</div>
</logic:iterate>

