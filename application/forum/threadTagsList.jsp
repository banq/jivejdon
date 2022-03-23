<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:define id="forumMessage" name="threadForm" property="rootMessage"  />
<bean:define id="forumThread" name="forumMessage" property="forumThread" />
<%
int j = 0;
%>
<logic:iterate id="threadTag" name="forumThread" property="tags" indexId="i">    
   <div class="box">
      <div class="linkblock">	
         <div class="row">        
           <div class="col-md-12">
            <div class="wrap-vid">
              <div class="thumbn">
                 <img src="//static.jdon.com/simgs/thumb/<%=1 + i%>.jpg" border="0" class="thumbnail" loading="lazy">  
              </div>  
              <div class="widget">			 
                 <div class="info">	
                    <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag" title="有<bean:write name="threadTag" property="assonum"/>篇"> 
                      <bean:write name="threadTag" property="assonum"/>篇<bean:write name="threadTag" property="title"/>
                    </a>               
                    <p>
                       <logic:iterate id="forumThread1" name="threadListForm" property="list" length="1" offset="<%=Integer.toString(j)%>" >
	                           <div class="info"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread1" property="threadId"/>" target="_blank" class="smallgray">
                                    <bean:write name="forumThread1" property="name" /></a>
                             </div>
                       </logic:iterate>
                    </p>
			           </div>  
              </div>
            </div>      
	         </div>
          </div>        
        </div>
   </div> 
<%j++;%>       
</logic:iterate>
