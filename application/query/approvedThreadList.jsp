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
<%
java.util.List nums = new java.util.ArrayList();
int[] randomArr = new int[5];
int idx = 0;
while (idx < 5) {
    nums.add(idx);
    idx++;
}
java.util.Collections.shuffle(nums);
idx = 0;
while (idx < 5) {
    randomArr[idx] = ((Integer)nums.get(idx)).intValue();
    idx++;
}
int randomIdx = 0;
%>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<div itemscope itemtype="https://schema.org/ItemList">
<logic:iterate  id="forumThread" name="threadListForm" property="list" length="1">
   <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
   <bean:define id="thumbthreadId" name="forumThread" property="threadId"/>
   
 <div itemprop="itemListElement" itemscope itemtype="https://schema.org/ListItem">
 <div class="row">	


 <div class="col-lg-12">
 <div>	
  <div>
    <div>	  
      <div class="row">	          

        <bean:define id="body" name="forumMessage" property="messageVO.body" />
        <div class="col-lg-8">
        <div class="box">	  
          <div class="box">  
            <div class="vid-name" style="margin-top: 2px;padding-bottom: 10px;"><a href="<%=domainUrl%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" class="hover-preload" itemprop="url"><h1 itemprop="name"><bean:write name="forumThread" property="name"/></h1></a></div>
          
           <div class="info">		
            <logic:iterate id="threadTag" name="forumThread" property="tags" >
                <a href="<%=domainUrl %>/tag/<bean:write name="threadTag" property="tagID"/>/" target="_blank" >
                  #<bean:write name="threadTag" property="title" /></a>
            </logic:iterate>       
            </div>
            
          
          <div style="letter-spacing: 0.02em;color: #3c1616; display:flex; align-items:flex-start; justify-content:space-between; margin-top: 10px">
              <span style="line-height:2.5rem;flex:1; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical;overflow:hidden">      
                 <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[150]" />
              </span>
          </div>

          <div class="info">			 
              <span class="smallgray"><i class="fa fa-calendar"></i>
                <bean:write name="forumMessage" property="modifiedDate3"/>
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
                <span class="smallgray"><i class="fa fa-arrow-circle-o-down"></i><bean:write name="forumMessage" property="messageVO.bodyLengthK"/>K</span>
            </logic:greaterThan>   
                                 
          </div>
         </div>  
         </div>         
        </div>  
        <div class="col-lg-4">
            <div class="box">
			      	<div style="position: relative;" class="zoom-container">
                  <img id="home-thumbnai" src="/simgs/thumb2/<%=(randomIdx < 5) ? randomArr[randomIdx++] : (int)(Math.random()*5)%>.jpg" border="0" class="img-thumbnail img-responsive" style="height:auto;width:100%" loading="lazy" /> 

                  <div style="position: absolute;top: 0px">
                  <div class="tagcloud">
                      <a href='<%=domainUrl%>/forum/<bean:write name="forumThread" property="forum.forumId"/>/'  class="tag-cloud-link">
		                      <bean:write name="forumThread" property="forum.name"/>
                      </a>
                      </div>
                 </div> 
                                  
              </div>
            </div>  
			  </div>        
   	</div>	
   	</div>	 
	</div>	
</div>  
</div>

 

  </div>

</logic:iterate>
</div>

