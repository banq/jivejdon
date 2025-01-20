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
		     <a href="<%=request.getContextPath()%>/blog/<bean:write name="accountProfileForm" property="account.username"/>" class="post-tag">
		          我的主题
		    </a>
		    
		      <span class="topsel">
		       我的评论
		     </span>
		    </div> 
		    <div class="title_right"> 
		    <a href="<%=request.getContextPath()%>/message/post.jsp">
            <img src="<%=request.getContextPath() %>/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div>
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>
                
<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">

<logic:iterate indexId="i"   id="forumMessage" name="messageListForm" property="list" >

<logic:notEmpty name="forumMessage" property="forumThread">
  <bean:define id="forumThread" name="forumMessage" property="forumThread"/>
  <bean:define id="forum" name="forumMessage" property="forum" />

  
       <div class="b_content_title2">
         <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" />.html' target="_blank">

           <bean:write name="forumMessage" property="messageVO.subject"/>
        </a>
        <span>(<bean:write name="forumMessage" property="modifiedDate3" />)</span>
        </div>
        

      <div class="b_content_body">
      <bean:write name="forumMessage" property="messageVO.shortBody[50]" filter="false"/>
     </div>

      <div class="b_content_other">
        <div class="b_content_other_left">                  
        </div>
      </div>
      <div class="b_content_line"> </div>
      	
</logic:notEmpty>
</logic:iterate>



<div class="title_right">
<div class="tres" >
     共有<b><bean:write name="messageListForm" property="allCount"/></b>贴       
<MultiPagesREST:pager actionFormName="messageListForm" page="/blog/messages" paramId="username" paramName="accountProfileForm" paramProperty="account.username">
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

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 

	  <div class="box_mode_2"> 
	    
	  </div> 
   </div> 
</div> 

<%@ include file="footer.jsp" %>