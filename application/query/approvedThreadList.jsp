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


 <div class="col-lg-12" style="padding:0px">
 <div class="box">	
  <div class="linkblock">
    <div class="box">	  
      <div class="row">	          
      <div class="col-lg-3">
			      	<div style="position: relative;" class="zoom-container">
                <logic:notEmpty name="forumMessage" property="messageUrlVO.imageUrl">        
                    <img id="home-thumbnai" src="<bean:write name="forumMessage" property="messageUrlVO.imageUrl"/>" border="0" class="img-thumbnail img-responsive" style="height:130px;width:100%" loading="lazy" onerror="this.src='/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(5)%>.jpg'"/>
                </logic:notEmpty>
                <logic:empty name="forumMessage" property="messageUrlVO.imageUrl">        
                  <img id="home-thumbnai" src="/simgs/thumb2/<%=(randomIdx < 5) ? randomArr[randomIdx++] : (int)(Math.random()*5)%>.jpg" border="0" class="img-thumbnail img-responsive" style="height:130px;width:100%" loading="lazy" /> 

                  <div style="position: absolute;bottom: 0px;">
                  <div class="tagcloud">
                      <a href='<%=domainUrl%>/forum/<bean:write name="forumThread" property="forum.forumId"/>/'  class="tag-cloud-link">
		                      <bean:write name="forumThread" property="forum.name"/>
                      </a>
                      </div>
                 </div> 
                </logic:empty>      
                                  
              </div>
			      </div>
             <bean:define id="body" name="forumMessage" property="messageVO.body" />
        <div class="col-lg-9">
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
                <span class="smallgray"><bean:write name="forumMessage" property="messageVO.bodyLengthK"/>K</span>
            </logic:greaterThan>   
                     
            </div>
            
<h1 style="font-size: 2.6rem;margin-top: 2px;padding-bottom: 10px;"><a href="<%=domainUrl%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" class="hover-preload" itemprop="url"><span itemprop="name"><bean:write name="forumThread" property="name"/></span></a></h1>
          
          <div class="wrap-vid">
              <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />. 
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

