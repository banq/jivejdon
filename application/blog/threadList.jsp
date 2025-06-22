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
		    
		    <a href="<%=request.getContextPath()%>/blog/userFollowingThreads.shtml" class="post-tag">
		        我的订阅
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
         <a href="<%=request.getContextPath() %>/<bean:write name="forumThread" property="threadId"/>.html" target="_blank"><bean:write name="forumMessage" property="messageVO.subject"/>
        </a>
        <span>(<bean:write name="forumMessage" property="creationDate" />)</span>
        <logic:present name="isOwner" >
          <span>     <a href="<%=request.getContextPath() %>/message/messageListOwner.shtml?thread=<bean:write name="forumThread" property="threadId"/>" target="_blank">编辑</a></span>
        </logic:present>
        </div>
        
       

      <div class="b_content_body">
      <bean:write name="forumMessage" property="messageVO.shortBody[50]" filter="false"/>
     </div>

       <div class="b_content_other">
        <div class="b_content_other_left">         
             &nbsp;阅<bean:write name="forumThread" property="viewCount" />
    &nbsp;
           <logic:greaterThan name="forumMessage" property="messageVO.bodyLengthK" value="1">
                <span><bean:write name="forumMessage" property="messageVO.bodyLengthK"/>K</span>
            </logic:greaterThan>     
  <logic:notEqual name="forumMessage" property="digCount" value="0">
                       <span class="smallgray">
                      <bean:write name="forumMessage" property="digCount"/>赞
					   </span>
                      </logic:notEqual>     

        </div>
      </div>
      

</td>
			</tr>
		</table>
 <p>
      	
</logic:iterate>


<div class="title_right">
<div class="tres" >    
     共有<b><bean:write name="threadListForm" property="allCount"/></b>贴  
<MultiPagesREST:pager actionFormName="threadListForm" page="/blog" paramId="username" paramName="accountProfileForm" paramProperty="account.username">
<MultiPagesREST:prev name=" 上页 " />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name=" 下页 " />
</MultiPagesREST:pager>
</div>
</div>

</logic:greaterThan>
</logic:present>
                
                
                
                </div>
             </div>

          	 
		 </div> 
	  </div> 
	 
	  <div class="box_mode_2"> 
	    
	  </div> 
   </div> 
</div> 

<%@ include file="footer.jsp" %>