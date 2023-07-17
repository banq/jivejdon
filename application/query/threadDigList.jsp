<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>


<div class="box">
      <div class="linkblock">	
         <div>                    
           <img id="home-thumbnai" src="https://static.jdon.com/simgs/thumb2/<%=1 + (int) (Math.random() * 49)%>.jpg" border="0" class="thumbnail" style="width: 100%" loading="lazy"/>                  
         </div>
          <div class="wrap-vid">
              <div class="widget">		 

<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
<section>
	<div class="info"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" target="_blank">
             <bean:write name="forumThread" property="name" /></a>
      </div>
</section>      
</logic:iterate>

</div>
</div>
</div>
</div>

