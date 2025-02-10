<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<logic:iterate  id="forumThread" name="threadListForm" property="list" length="1">
   <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
   <bean:define id="thumbthreadId" name="forumThread" property="threadId"/>
   

 <div class="row">	


 <div class="col-lg-12">
 <div class="box">	
  <div class="linkblock">
    <div class="box">	  
      <div class="row">	          
      <div class="col-lg-4">
			      	<div class="zoom-container">
              <a href="<%=domainUrl%>/<bean:write name="forumThread" property="threadId"/>.html"   title="<bean:write name="forumThread" property="name"/>">
                <logic:notEmpty name="forumMessage" property="messageUrlVO.imageUrl">        
                    <img id="home-thumbnai" src="<bean:write name="forumMessage" property="messageUrlVO.imageUrl"/>" border="0" class="img-thumbnail img-responsive" style="height:230px;width:100%"  loading="lazy" onerror="this.src='https://static.jdon.com/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(5)%>.jpg'"/>
                </logic:notEmpty>
                <logic:empty name="forumMessage" property="messageUrlVO.imageUrl">        
                    <img id="home-thumbnai" src="https://static.jdon.com/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(5)%>.jpg" border="0" class="img-thumbnail img-responsive" style=""  loading="lazy" /> 
                </logic:empty>      
               </a>                   
              </div>
			      </div>
             <bean:define id="body" name="forumMessage" property="messageVO.body" />

         <section class="widget">
         <h2><a href="<%=domainUrl%>/<bean:write name="forumThread" property="threadId"/>.html"   class="hover-preload"><bean:write name="forumThread" property="name"/></a></h2>
      
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
                     
            </div>
        
          <div class="wrap-vid">
              <p><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" />. </p>
          </div>
      </section>
   	</div>	
   	</div>	 
	</div>	
</div>  
</div>

 

  </div>

</logic:iterate>

<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="fluid"
     data-ad-layout-key="-ev-1p-5j-ot+26n"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3378777426"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>


<%int j = 0;%>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" offset="1" >
   <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
   <bean:define id="thumbthreadId" name="forumThread" property="threadId"/>
   
<%
  j = j % 2;
  if(j ==0){ 
 %>
 <div class="row">	
 <%}%>

 <div class="col-md-6">
 <div class="box">	
  <div class="linkblock">
    <div>	           
    
             <bean:define id="body" name="forumMessage" property="messageVO.body" />

         <section class="widget">
         <h3 class="vid-name"><a href="<%=com.jdon.jivejdon.util.ToolsUtil.getAppURL(request)%>/<bean:write name="forumThread" property="threadId"/>.html"   class="hover-preload"><bean:write name="forumThread" property="name"/></a></h3>
      
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
                     
            </div>
           
      </section>
   	</div>	
	</div>	
</div>  
</div>

 
<%
  j = j+1;
  if(j % 2==0){ 
 %>
  </div>
 <%}%>



</logic:iterate>
