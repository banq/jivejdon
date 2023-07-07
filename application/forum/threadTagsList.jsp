<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>



<div class="box">
      <div class="linkblock">	
          <div class="wrap-vid">
              <div class="widget">		  


<logic:notEmpty name="tagID0">    
                              
 <logic:iterate id="forumThread0" name="tagID0" length="4">
    <article>                 
                 <div class="info">
                     <a href="<%=request.getContextPath()%>/<bean:write name="forumThread0" property="threadId"/>.html" target="_blank">
                                    <bean:write name="forumThread0" property="name" /></a>
                 </div>
    </article>              
 </logic:iterate>
             
</logic:notEmpty>

</div>
</div>
</div>
</div>



<div class="box">
      <div class="linkblock">	
       
          <div class="wrap-vid">
              <div class="widget">		 
   
<logic:notEmpty name="tagID1">    
                            
                 <logic:iterate id="forumThread1" name="tagID1" length="4">
                 <div class="info">
                     <a href="<%=request.getContextPath()%>/<bean:write name="forumThread1" property="threadId"/>.html" target="_blank">
                                    <bean:write name="forumThread1" property="name" /></a>
                 </div> 
                 </logic:iterate>
             
</logic:notEmpty>

</div>
</div>
</div>
</div>


<%-- 
<logic:notEmpty name="tagID2">    
                         
                 <logic:iterate id="forumThread2" name="tagID2" length="4">
                 <div class="info">
                     <a href="<%=request.getContextPath()%>/<bean:write name="forumThread2" property="threadId"/>.html" target="_blank">
                                    <bean:write name="forumThread2" property="name" /></a>
                 </div> 
                 </logic:iterate>
             
</logic:notEmpty>

<logic:notEmpty name="tagID3">    
                         
                 <logic:iterate id="forumThread3" name="tagID3" length="4">
                 <div class="info">
                     <a href="<%=request.getContextPath()%>/<bean:write name="forumThread3" property="threadId"/>.html" target="_blank">
                                    <bean:write name="forumThread3" property="name" /></a>
                 </div> 
                 </logic:iterate>
            
</logic:notEmpty> --%>



