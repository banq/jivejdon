<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:define id="reblogMessage" name="reglogThread" property="rootMessage" />
   <div class="box"> 
  <div class="linkblock" itemscope itemtype="http://schema.org/BlogPosting">
  <div class="row">
        <div class="col-sm-12">
       <div class="box">
             <bean:define id="body" name="reblogMessage" property="messageVO.body" />

         <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="reglogThread" property="threadId"/>"><bean:write name="reglogThread" property="name"/></a></h3>
      
         <div class="info">			 
              <span><i class="fa fa-calendar"></i>
                <bean:define id="cdate" name="reglogThread" property="creationDate" ></bean:define>
        <%String cdateS = (String)pageContext.getAttribute("cdate"); %><%=cdateS.substring(2, 11) %>
                      </span>
			 <logic:notEqual name="reglogThread" property="state.messageCount" value="0">
              <span><i class="fa fa-comment"></i> <bean:write name="reglogThread" property="state.messageCount" />
                      </span>
		       </logic:notEqual>  
              <span><i class="fa fa-eye"></i><bean:write name="reglogThread" property="viewCount" />
                      </span>
			   <logic:notEqual name="reblogMessage" property="digCount" value="0">
                       <span><i class="fa fa-heart"></i>
                      <bean:write name="reblogMessage" property="digCount"/>
					   </span>
                      </logic:notEqual>     
			 <span><i class="fa fa-user"></i><bean:write name="reglogThread" property="rootMessage.account.username" />
               </span>
                      
            </div>
          <div class="wrap-vid">
              <div class="thumbn">

                <logic:notEmpty name="reblogMessage" property="messageUrlVO.thumbnailUrl">
                  <img src="<bean:write name="reblogMessage" property="messageUrlVO.thumbnailUrl"/>" border='0' class="thumbnail" loading="lazy"/>
                </logic:notEmpty>
              </div>
              <p><bean:write name="reglogThread" property="rootMessage.messageVO.shortBody[100]" />. <a href="<%=request.getContextPath()%>/<bean:write name="reglogThread" property="threadId"/>" target="_blank" class="smallgray">详细</a></p>
      </div>
  
             </div>
  </div>
</div>
</div>
</div>