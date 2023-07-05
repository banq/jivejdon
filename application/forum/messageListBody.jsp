<%@ page session="false" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>

<logic:empty name="forumThread">
   <bean:define id="forumThread" name="messageListForm" property="oneModel"/>
   <bean:define id="forum" name="forumThread" property="forum"/>
</logic:empty>

<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i">

<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
<%-- <logic:equal name="i" value="0">
  <logic:notEmpty name="principal">
    <logic:equal name="loginAccount" property="roleName" value="Admin">
      <p> <a
        href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce_all','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">全公告</a>
      <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','sticky_all','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">全置顶</a>
      <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','sticky','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">置顶</a>
      <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">公告</a>
      <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce_all','delete','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">取消</a>
    </logic:equal>
  </logic:notEmpty>
</logic:equal> --%>

<div class="row">
  <div class="col-md-12">
    <div class="box">
      <logic:equal name="forumMessage" property="root" value="false">
      <div class="frame-yy">
      </logic:equal>
        <header>
        <div class="post_header">
          <div class="post_title">
            <logic:equal name="forumMessage" property="root" value="true">
              <div class="post_titlename">
                <logic:notEmpty name="forumMessage" property="messageUrlVO.linkUrl">
                  <a href="<bean:write name="forumMessage" property="messageUrlVO.linkUrl" filter="false"/>" target="_blank" title="原始链接"><h1 class="bige20"><bean:write name="forumMessage" property="messageVO.subject"/>
                    </a>
                  </logic:notEmpty>
                <logic:empty name="forumMessage" property="messageUrlVO.linkUrl">
                      <h1 class="bige20"><bean:write name="forumMessage" property="messageVO.subject"/></h1>
                  </logic:empty>
              </div>
            </logic:equal>

            <div class="post_title2">
              <div class="post_titleauthor info">
               <logic:equal name="forumMessage" property="root" value="true">
                    <i class="fa fa-calendar"></i>
                    <bean:define id="cdate" name="forumThread" property="creationDate"></bean:define>
                    <%String cdateS = (String) pageContext.getAttribute("cdate"); %><%=cdateS.substring(2, 11) %>
                  <span></span>
                </logic:equal>
				<span></span>  
                <logic:notEmpty name="forumMessage" property="account">
					<i class="fa fa-user"></i>
                <bean:write name="forumMessage" property="account.username"/>
				  <%-- <a href='<%=request.getContextPath()%>/blog/<bean:write name="forumMessage" property="account.username"/>' class="smallgray" rel="no">
                    
                  </a> --%>
                </logic:notEmpty>      
              </div>

              <logic:equal name="forumMessage" property="root" value="false">
                <div class="post_titledate">
                  <div class="smallgray" id='creationDate_<bean:write name="forumMessage" property="messageId"/>'>
                    <bean:write name="forumMessage" property="creationDate"/>
                  </div>
                </div>
              </logic:equal>

              <div class="post_titleother">
                <logic:equal name="messageListForm" property='<%= "authenticated[" + i + "]" %>' value="true">
                  <a href='<html:rewrite page="/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId" />'>
                    编辑
                  </a>
                  <logic:equal name="forumMessage" property="root" value="true">
                    <a href='<html:rewrite
            page="/message/updateViewAction.shtml?action=edit"
            paramId="threadId" paramName="forumMessage"
            paramProperty="forumThread.threadId" />'>编辑标题 </a>

                    <a href='<html:rewrite
            page="/message/tag/thread.shtml?action=edit"
            paramId="threadId" paramName="forumMessage"
            paramProperty="forumThread.threadId" />'>编辑标签 </a>

                    <a href='<html:rewrite
            page="/message/reblogLink.shtml?action=edit"
            paramId="threadId" paramName="forumMessage"
            paramProperty="forumThread.threadId" />'>编辑互链 </a>
                  </logic:equal>
                </logic:equal>
              </div>
            </div>
          </div>
        </div>
        </header>   
        
        <logic:equal name="forumMessage" property="root" value="true"><div>
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
<ins class="adsbygoogle"
     style="display:block; text-align:center;"
     data-ad-layout="in-article"
     data-ad-format="fluid"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3121124104"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>        
         </div></logic:equal>     

       
              <div class="article">
                <main id='body_<bean:write name="forumMessage" property="messageId"/>'>
                  <p>
                     <bean:write name="forumMessage" property="messageVO.body" filter="false"/>
                  <br>
                  </p>
                </main>
              </div>    

<logic:equal name="forumMessage" property="root" value="true">


              <div class="diggArea list-inline  top-social" >
                <DIV class=diggNum id="digNumber_<bean:write name="forumMessage" property="messageId"/>">
                  <logic:notEqual name="forumMessage" property="digCount" value="0">
                    <bean:write name="forumMessage" property="digCount"/>
                  </logic:notEqual>
                </DIV>
	            <DIV class="diggLink top8"
                     id="textArea_<bean:write name="forumMessage" property="messageId"/>"><a
                    href="javascript:digMessage('<bean:write name="forumMessage" property="messageId"/>')"><i class="fa fa-thumbs-o-up"></i></a>
                </DIV> 
              </div>
              <div style="margin: 0 auto;width: 85px">
	             <ul class="list-inline  top-social">
		            <li><a href="javascript:shareto('sina')"><i class="fa fa-weibo"></i></a></li>
		            <li><a href="javascript:shareto('weixin')"><i class="fa fa-weixin"></i></a></li>
		            <li><a href="javascript:shareto('qzone')"><i class="fa fa-qq"></i></a></li>		
	              </ul>
              </div>

 
   <!-- 导航区  -->
        <div class="post_pages_end">
          <div class="table-button-left">
            <div class="table-button-right">
              <logic:greaterThan name="messageListForm" property="numPages" value="1">
                <div class="tres">
                  有<b><bean:write name="messageListForm" property="numPages"/></b>页 
                  <MultiPagesREST:pager actionFormName="messageListForm" page="" paramId="thread" paramName="forumThread" paramProperty="threadId">
                    <MultiPagesREST:prev name=" 上一页 "/>
                    <MultiPagesREST:index displayCount="3"/>
                    <MultiPagesREST:next name=" 下一页 "/>
                  </MultiPagesREST:pager>
                </div>
              </logic:greaterThan>
            </div>
          </div>
        </div>             

              <div class="box">
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="autorelaxed"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3540211914"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>
              </div>

              <logic:notEmpty name="forumMessage" property="reBlogVO.threadFroms">          
                <logic:iterate id="threadFrom" name="forumMessage" property="reBlogVO.threadFroms">                
                  <div class="reblogfrom" id='<bean:write name="threadFrom" property="threadId"/>'></div>                  
                </logic:iterate>         
     
<script defer>
document.addEventListener("DOMContentLoaded", function(event) { 
  $(document).ready(function() {              
      $('.reblogfrom').each(function(i, obj) {        
        scrollLoadByElementId('/forum/thread.shtml?othread=<bean:write name="forumThread" property="threadId"/>&threadId='+ obj.id,obj.id); 
      });   
  });            
});  
</script>                        
              </logic:notEmpty>


              <logic:notEmpty name="forumMessage" property="reBlogVO.threadTos">
                <logic:iterate id="threadTo" name="forumMessage" property="reBlogVO.threadTos">
                  <div class="reblogto" id='<bean:write name="threadTo" property="threadId"/>'></div>
                </logic:iterate>
    
<script defer>
document.addEventListener("DOMContentLoaded", function(event) { 
  $(document).ready(function() {                
      $('.reblogto').each(function(i, obj) {        
        scrollLoadByElementId('/forum/thread.shtml?othread=<bean:write name="forumThread" property="threadId"/>&threadId='+ obj.id,obj.id); 
       });
  });            
});  
</script>                
              </logic:notEmpty>   
              



</logic:equal>

              
<logic:equal name="forumMessage" property="root" value="false">
  </div>
</logic:equal>
</div>
</div>
</div>
	
 </logic:iterate>   