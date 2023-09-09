<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>



<div class="box">
      <div class="linkblock">	
          <div>                    
           <img id="home-thumbnai" src="https://static.jdon.com/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(49)%>.jpg" border="0" class="thumbnail" style="width: 100%" loading="lazy"/>                  
          </div>
          <div class="wrap-vid">
              <div class="widget">		  

<ul>
<logic:notEmpty name="tagID0">    
                              
 <logic:iterate id="forumThread0" name="tagID0" length="4">

 <li class="info">
    <a href="<%=request.getContextPath()%>/<bean:write name="forumThread0" property="threadId"/>.html" target="_blank">
     <bean:write name="forumThread0" property="name" /></a>
 </li>
            
 </logic:iterate>
             
</logic:notEmpty>
</ul>

</div>
</div>
</div>
</div>



<div class="box">
      <div class="linkblock">	
         <div>                    
           <img id="home-thumbnai" src="https://static.jdon.com/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(49)%>.jpg" border="0" class="thumbnail" style="width: 100%" loading="lazy"/>                  
          </div>      
          <div class="wrap-vid">
              <div class="widget">		 

<ul>   
<logic:notEmpty name="tagID1">    
                            
                 <logic:iterate id="forumThread1" name="tagID1" length="4">
                 <li class="info">
                     <a href="<%=request.getContextPath()%>/<bean:write name="forumThread1" property="threadId"/>.html" target="_blank">
                                    <bean:write name="forumThread1" property="name" /></a>
                 </li> 
                 </logic:iterate>
             
</logic:notEmpty>
</ul>

</div>
</div>
</div>
</div>





