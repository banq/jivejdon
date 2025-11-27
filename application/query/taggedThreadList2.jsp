<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
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


<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" offset="<%=offset%>" length="<%=count%>" >
  
      <%
 if(((Integer)i) % 2==0){ 
 %>
 <div class="row">	
 <%}%>
 <div class="col-md-6" style="padding:0px">
 <li class="box">	
  <div class="linkblock">
     <div class="box">	        
        <div class="vid-name" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">     
          <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" onclick="showDialog('dialog2', '<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html#messageListBody');return false;" class="hover-preload"><h3><bean:write name="forumThread" property="name"/></h3></a>
        </div>
          <div class="info" style="display:flex; align-items:flex-start; justify-content:space-between; margin-top: 10px">              
            <span style="flex:1; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical;overflow:hidden">           
              <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[150]" />
             </span>  
              <img src="/simgs/thumb/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(3)%>.jpg" alt="icon" loading="lazy" style="width:55px; height:55px; margin-left:12px; flex-shrink:0; object-fit:cover; border-radius:6px;">
            </div>
         </div>
 

 </div>	
</li>  
</div>




<%
  if(((Integer)i) % 2==1){ 
 %>
  </div>
 <%}%>


</logic:iterate>
