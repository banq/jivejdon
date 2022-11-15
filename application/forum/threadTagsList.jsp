<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

   <div class="box">
      <div class="linkblock">	
         <div class="row">        
           <div class="col-md-12">
            <div class="wrap-vid">
              <div class="widget">			 
                 <logic:iterate id="forumThread1" name="threadListForm" property="list" length="4">
                 <div class="info">
                     <a href="<%=request.getContextPath()%>/<bean:write name="forumThread1" property="threadId"/>" target="_blank">
                                    <bean:write name="forumThread1" property="name" /></a>
                 </div> 
                 </logic:iterate>
              </div>
            </div>      
	         </div>
          </div>        
        </div>
   </div> 


