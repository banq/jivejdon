<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../blog/header.jsp" %>

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
		   <a href="<%=request.getContextPath()%>/message/post.jsp?to=<bean:write name="accountProfileForm" property="account.username"/>">
            <img src="/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>
                <h3>粉丝表</h3>
<!-- second query result -->
<logic:present name="accountListForm">
<logic:greaterThan name="accountListForm" property="allCount" value="0">

<logic:iterate indexId="i"   id="account" name="accountListForm" property="list" >

  
  <div class="linkblock">  
<table width="100%"><tr><td width="50%" align="center">
         <a href='<%=request.getContextPath()%>/blog/<bean:write name="account" property="username" />'>          
           <logic:notEmpty name="account" property="uploadFile">
            <logic:equal name="account" property="roleName" value="User"> 
               <img  src="/img/account/<bean:write name="account" property="userId"/>"  border='0' class="post_author_pic" />
            </logic:equal>
            <logic:equal name="account" property="roleName" value="SinaUser">                 
               <img  src="<bean:write name="account" property="uploadFile.description"/>"  border='0' class="post_author_pic" />
            </logic:equal>
            <logic:equal name="account" property="roleName" value="TecentUser">                 
               <img  src="<bean:write name="account" property="uploadFile.description"/>"  border='0' class="post_author_pic"/>
            </logic:equal>                                    
           </logic:notEmpty>
           <logic:empty name="account" property="uploadFile">
           	    <img  src="<%=request.getContextPath() %>/images/emavatar.png"  border='0' width="75" height="75" />
           </logic:empty>                     

	
         </a>	
</td>
<td>
  <span onmouseover="loadWLJSWithP(this, initUsersW)"  class='Users ajax_userId=<bean:write name="account" property="userId"/>' id="users" >                      
              <bean:write name="account" property="username" />
  <br>        
      文章：<bean:write name="account" property="messageCount"/>
   <br>
     注册：<bean:write name="account" property="creationDate"/>      	
   <br>
关注  
    <logic:greaterThan name="account" property="subscriptionCount" value="0">
      <a href="<%=request.getContextPath()%>/social/following.shtml?subscribeType=3&userId=<bean:write name="account" property="userId"/>"  target="_blank"  rel="nofollow">
           <span class="smallgray"><bean:write name="account" property="subscriptionCount"/></span>
      </a>
   </logic:greaterThan>             
&nbsp;| 粉丝 
    <logic:greaterThan name="account" property="subscribedCount" value="0">
      <a href="<%=request.getContextPath()%>/social/follower.shtml?subscribeType=3&userId=<bean:write name="account" property="userId"/>"  target="_blank"  rel="nofollow">
            <span class="smallgray"><bean:write name="account" property="subscribedCount"/></span>
      </a>
    </logic:greaterThan>
             
&nbsp; | 微博              
    <logic:greaterThan name="account" property="messageCount" value="0">
       <a href="<%=request.getContextPath()%>/blog/messages/<bean:write name="account" property="username"/>"  target="_blank"  rel="nofollow">
           <span class="smallgray"><bean:write name="account" property="messageCount"/></span>
                 </a>
    </logic:greaterThan>
   <br> 	
   <a href="<%=request.getContextPath()%>/blog/<bean:write name="account" property="username"/>" >
       博客：<span rel="nofollow"><bean:write name="account" property="username"/>.jdon.com</span>
   </a>
     <br>
   <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=3&subscribeId=<bean:write name="account" property="userId"/>"
                  target="_blank" title="加关注"  rel="nofollow">            
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本主题" border="0" /><span class="blackgray">+关注</span>
     </a>         
   </td>
   </tr></table>       

</div>        	
<p></p> 
      	
</logic:iterate>

<div class="title_right">
	<bean:parameter id="userIdParam" name="userId"  value=""/>
<div class="tres" >    
     共有<b><bean:write name="accountListForm" property="allCount"/></b>位  
 <MultiPages:pager actionFormName="accountListForm"
					page="/social/follower.shtml?subscribeType=3" paramId="userId" paramName="userIdParam">
					<MultiPages:prev name="[上页]" />
					<MultiPages:index />
					<MultiPages:next name="[下页]" />
 </MultiPages:pager>
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

<%@ include file="../blog/footer.jsp" %>