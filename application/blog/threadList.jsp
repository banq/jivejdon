<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="header.jsp" %>
   <div class="mainarea_right"> 
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">
		     <span class="topsel">
		          我的主题
		    </span>
		    
		    <a href="<%=request.getContextPath()%>/blog/messages/<bean:write name="accountProfileForm" property="account.username"/>" class="post-tag">
		        我的评论
		    </a>
		    </div> 
		    <div class="title_right"> 
  
		   <a href="<%=request.getContextPath()%>/message/post.jsp">
            <img src="<%=request.getContextPath() %>/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div>
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>
                
<!-- second query result -->
<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >

  <bean:define id="forumMessage" name="forumThread" property="rootMessage"/>
  <bean:define id="forum" name="forumMessage" property="forum" />
  	<bean:define id="account" name="forumThread" property="rootMessage.account" />
	<table cellpadding="5" cellspacing="5" border="0">
		<tr>
			<td>
				
       <div class="b_content_title2">
         <a href="<%=request.getContextPath() %>/message/messageListOwner.shtml?thread=<bean:write name="forumThread" property="threadId"/>" target="_blank"><bean:write name="forumMessage" property="messageVO.subject"/>
        </a>
        <span>(<bean:write name="forumMessage" property="creationDate" />)</span>
      
        </div>
        
       

      <div class="b_content_body">
      <bean:write name="forumMessage" property="messageVO.shortBody[100]" filter="false"/>
     </div>

       <div class="b_content_other">
        <div class="b_content_other_left">         
             &nbsp;阅<bean:write name="forumThread" property="viewCount" />
    &nbsp;评<bean:write name="forumThread" property="state.messageCount" />
        <logic:greaterThan name="forumThread" property="state.messageCount" value="0">
           <logic:notEmpty name="forumThread" property="state.latestPost">
            <bean:define id="latestPost" name="forumThread" property="state.latestPost"/>
                   &nbsp;最近更新：
            <bean:write name="latestPost" property="account.username" />
            &nbsp; <bean:write name="latestPost" property="modifiedDate3" />
          </logic:notEmpty>
         </logic:greaterThan>       
         

        </div>
      </div>
      <div class="b_content_line"> </div>

</td>
			</tr>
		</table>
 <p>
      	
</logic:iterate>


<div class="title_right">
<div class="tres" >    
     共有<b><bean:write name="threadListForm" property="allCount"/></b>贴  
<MultiPagesREST:pager actionFormName="threadListForm" page="/blog" paramId="username" paramName="accountProfileForm" paramProperty="account.username">
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager>
</div>
</div>

</logic:greaterThan>
</logic:present>
                
                
                
                </div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
	 
	  <div class="box_mode_2"> 
	    
	  </div> 
   </div> 
</div> 

<%@ include file="footer.jsp" %>