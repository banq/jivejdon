<%@ page session="false" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>

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
                  <a href='<html:rewrite page="/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId" />' rel='nofollow'>
                    编辑
                  </a>
                  <logic:equal name="forumMessage" property="root" value="true">
                    <a href='<html:rewrite
            page="/message/updateViewAction.shtml?action=edit"
            paramId="threadId" paramName="forumMessage"
            paramProperty="forumThread.threadId" />' rel='nofollow'>编辑标题 </a>

                    <a href='<html:rewrite
            page="/message/tag/thread.shtml?action=edit"
            paramId="threadId" paramName="forumMessage"
            paramProperty="forumThread.threadId" />' rel='nofollow'>编辑标签 </a>

                    <a href='<html:rewrite
            page="/message/reblogLink.shtml?action=edit"
            paramId="threadId" paramName="forumMessage"
            paramProperty="forumThread.threadId" />' rel='nofollow'>编辑互链 </a>
                  </logic:equal>
                </logic:equal>
              </div>
            </div>
          </div>
        </div>
        <logic:equal name="forumMessage" property="root" value="true"><div>
							<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
							        <!-- 自适应主广告 -->
									<ins class="adsbygoogle"
     									style="display:block"
     									data-ad-client="ca-pub-7573657117119544"
     									data-ad-slot="5184711902"
     									data-ad-format="auto"
     									data-full-width-responsive="true"></ins>
							<script>
     					  	(adsbygoogle = window.adsbygoogle || []).push({});
          		</script>		
         </div></logic:equal>        
          <div class="post_body">
          <div class="post_bodyin">
            <div class="post_body_content">
              <div class="article">
                <span id='body_<bean:write name="forumMessage" property="messageId"/>'>
                     <bean:write name="forumMessage" property="messageVO.body" filter="false"/>
                </span>
              </div>              
              <p></p>
</div>
</div>
</div>
<logic:equal name="forumMessage" property="root" value="false">
  </div>
</logic:equal>
</div>
</div>
</div>
	
 </logic:iterate>   