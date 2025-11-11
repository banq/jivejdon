<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
response.setHeader("Cache-Control", "public, s-maxage=86400, max-age=86400");
response.setDateHeader("Expires", System.currentTimeMillis() + 86400000);
response.setHeader("X-Robots-Tag", "noindex, nofollow");
%>

<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>

<%
String offset = "0";
if (request.getParameter("offset")!=null){
   offset = request.getParameter("offset");
}
String count = "5";
if (request.getParameter("count")!=null){
   count = request.getParameter("count");
}
%>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >


<%int j = 0;%>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" offset="<%=offset%>" length="<%=count%>">
   <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
   <bean:define id="thumbthreadId" name="forumThread" property="threadId"/>
   
<%
  j = j % 2;
  if(j ==0){ 
 %>
 <div class="row">	
   <div class="col-md-6" style="padding:0px">
 <%}else{%>
  <div class="col-md-6" style="padding:0px">
 <%}%>


 <div class="box">	
  <div>
    <div class="box">	           
    
             <bean:define id="body" name="forumMessage" property="messageVO.body" />

         <h3 class="vid-name" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
          <a href="<%=com.jdon.jivejdon.util.ToolsUtil.getAppURL(request)%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html"   class="hover-preload"><bean:write name="forumThread" property="name"/></a>
        </h3>
      
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

            <div class="info" style="display:flex; align-items:flex-start; justify-content:space-between; margin-top: 10px">
              <span style="flex:1; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical;overflow:hidden">      
                 <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />
              </span>
          </div>
   	</div>	
	</div>	
</div>  


 
<%
  j = j+1;
  if(j % 2==0){ 
 %>
  </div>
  </div>
 <%}else{%>
</div>
 <%}%>

</logic:iterate>
