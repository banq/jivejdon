<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>

  
<%          
int expire =5 * 24 * 60 * 60;
long modelLastModifiedDate = com.jdon.jivejdon.presentation.action.util.ForumUtil.getForumsLastModifiedDate(this.getServletContext()).getModifiedDate2();
if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
	return ;
}

%>

<logic:iterate indexId="i"   id="forumThread" name="announceList_all">

    <tr bgcolor="#FFFFEC">
        <td>        
             <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <img src="/images/icon_posting_sticky.gif" border="0" width="15" height="16"/><font color="red">【公告】<bean:write name="forumThread" property="name" /></font>
             </span></b></a>          
             
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
              

        </td>
        <td nowrap="nowrap">
            <bean:define id="rootMessage" name="forumThread" property="rootMessage"></bean:define>
            <logic:notEmpty name="rootMessage"  property="account">            
             <a href='<%=request.getContextPath()%>/blog/<bean:write name="rootMessage" property="account.username"/>' target="_blank">           
               <div id="subbox"><span><bean:write name="rootMessage" property="account.username"  /></span>
               </div>
            </a>
            </logic:notEmpty>
        </td>
        <td align="center">
            <bean:write name="forumThread" property="rootMessage.creationDateForDay" /> 
        </td>
        <td align="center">
            <bean:write name="forumThread" property="viewCount" />
        </td>
        <td nowrap="nowrap">
           <logic:notEmpty name="forumThread" property="state.lastPost">                        
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
              <span class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
                <a href='<%=request.getContextPath()%>/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />'  rel="nofollow">
                    <bean:write name="lastPost" property="modifiedDate3" />
                    <br><div id="subbox"><span><bean:write name="lastPost" property="account.username" /></span></div>
                </a></span>
          </logic:notEmpty>
        </td>
    </tr>
</logic:iterate>
    
<logic:iterate indexId="i"   id="forumThread" name="announceList">

    <tr bgcolor="#FFFFEC">
        <td>        
             <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <img src="/images/icon_posting_sticky.gif" border="0" width="15" height="16"/><font color="red">【公告】<bean:write name="forumThread" property="name" /></font>
             </span></b></a>
             
             <%--  stick no need js 
             <logic:greaterEqual  name="forumThread" property="state.messageCount" value="30">
             <script>
               document.write(generateLinkString("<%=request.getContextPath()%>", '<bean:write name="forumThread" property="state.messageCount" />', "<bean:write name="forumThread" property="threadId"/>", "15"));
             </script>
             </logic:greaterEqual>
              --%>
              
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
                       
             <div id="tooltip_content_<bean:write name="forumThread" property="threadId"/>" style="display:none">
               <div class="tooltip_content">
                <span class="tpc_content">
                 <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />
                 </span>
               </div>
             </div>

        </td>
        <td nowrap="nowrap">
            <bean:define id="rootMessage" name="forumThread" property="rootMessage"></bean:define>
            <logic:notEmpty name="rootMessage"  property="account">            
             <a href='<%=request.getContextPath()%>/blog/<bean:write name="rootMessage" property="account.username"/>' target="_blank">           
               <div id="subbox"><span><bean:write name="rootMessage" property="account.username"  /></span>
               </div>
            </a>
             </logic:notEmpty>
        </td>
        <td align="center">
            <bean:write name="forumThread" property="rootMessage.creationDateForDay" /> 
        </td>
        <td align="center">
            <bean:write name="forumThread" property="viewCount" />/<bean:write name="forumThread" property="state.messageCount" />                        
        </td>
        <td nowrap="nowrap">
           <logic:notEmpty name="forumThread" property="state.lastPost">                        
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
              <span class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
                <a href='<%=request.getContextPath()%>/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />'  rel="nofollow">
                    <bean:write name="lastPost" property="modifiedDate3" />
                    <br><div id="subbox"><span><bean:write name="lastPost" property="account.username" /></span></div>
                </a></span>
          </logic:notEmpty>
 
        </td>
    </tr>
</logic:iterate>

<logic:iterate indexId="i"   id="forumThread" name="stickyList_all">

    <tr bgcolor="#FFFFEC">
        <td>        
             <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <img src="/images/icon_posting_sticky.gif" border="0" width="15" height="16"/><font color="blue">【置顶】<bean:write name="forumThread" property="name" /></font>
             </span></b></a>
             
              <%--  stick no need js  
             <logic:greaterEqual  name="forumThread" property="state.messageCount" value="30">
             <script>
               document.write(generateLinkString("<%=request.getContextPath()%>", '<bean:write name="forumThread" property="state.messageCount" />', "<bean:write name="forumThread" property="threadId"/>", "15"));
             </script>
             </logic:greaterEqual>
             --%>
             
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
 

        </td>
        <td nowrap="nowrap">
            <bean:define id="rootMessage" name="forumThread" property="rootMessage"></bean:define>
            <logic:notEmpty name="rootMessage"  property="account">            
             <a href='<%=request.getContextPath()%>/blog/<bean:write name="rootMessage" property="account.username"/>' target="_blank">           
               <div id="subbox"><span><bean:write name="rootMessage" property="account.username"  /></span>
               </div>
            </a>
             </logic:notEmpty>
        </td>
        <td align="center">
            <bean:write name="forumThread" property="rootMessage.creationDateForDay" /> 
        </td>
        <td align="center">
            <bean:write name="forumThread" property="viewCount" />/<bean:write name="forumThread" property="state.messageCount" />                        
        </td>
        
        <td nowrap="nowrap">
           <logic:notEmpty name="forumThread" property="state.lastPost">                        
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
              <span class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
                <a href='<%=request.getContextPath()%>/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />'  rel="nofollow">
                    <bean:write name="lastPost" property="modifiedDate3" />
                    <br><div id="subbox"><span><bean:write name="lastPost" property="account.username" /></span></div>
                </a></span>
          </logic:notEmpty>
    
           
        </td>
    </tr>
</logic:iterate>

    
<logic:iterate indexId="i"   id="forumThread" name="stickyList">

    <tr bgcolor="#FFFFEC">
        <td>        
             <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <img src="/images/icon_posting_sticky.gif" border="0" width="15" height="16"/><font color="blue">【置顶】<bean:write name="forumThread" property="name" /></font>
             </span></b></a>
             
              <%--  stick no need js  
             <logic:greaterEqual  name="forumThread" property="state.messageCount" value="30">
             <script>
               document.write(generateLinkString("<%=request.getContextPath()%>", '<bean:write name="forumThread" property="state.messageCount" />', "<bean:write name="forumThread" property="threadId"/>", "15"));
             </script>
             </logic:greaterEqual>
              --%>
              
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
                

        </td>
        <td nowrap="nowrap">
            <bean:define id="rootMessage" name="forumThread" property="rootMessage"></bean:define>
            <logic:notEmpty name="rootMessage"  property="account">            
             <a href='<%=request.getContextPath()%>/blog/<bean:write name="rootMessage" property="account.username"/>' target="_blank">           
               <div id="subbox"><span><bean:write name="rootMessage" property="account.username"  /></span>
               </div>
            </a>
             </logic:notEmpty>
        </td>
       <td align="center">
            <bean:write name="forumThread" property="rootMessage.creationDateForDay" /> 
        </td>
        <td align="center">
            <bean:write name="forumThread" property="viewCount" />/<bean:write name="forumThread" property="state.messageCount" />                        
        </td>
        <td nowrap="nowrap">
           <logic:notEmpty name="forumThread" property="state.lastPost">                        
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
              <span class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
                <a href='<%=request.getContextPath()%>/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />'  rel="nofollow">
                    <bean:write name="lastPost" property="modifiedDate3" />
                    <br><div id="subbox"><span><bean:write name="lastPost" property="account.username" /></span></div>
                
                </a></span>
          </logic:notEmpty>             
        </td>
    </tr>
</logic:iterate>


