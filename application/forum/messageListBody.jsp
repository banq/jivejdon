<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
<logic:equal name="i" value="0">
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
</logic:equal>

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
          <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
          <ins class="adsbygoogle"
               style="display:block; text-align:center;"
               data-ad-layout="in-article"
               data-ad-format="fluid"
               data-ad-client="ca-pub-7573657117119544"
               data-ad-slot="6913243852"></ins>
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
              <logic:equal name="forumMessage" property="root" value="true">    
                <logic:notEmpty name="forumMessage" property="reBlogVO.threadFroms">
                  <logic:iterate id="threadFrom" name="forumMessage" property="reBlogVO.threadFroms">
                    <div class="reblogfrom"> 
                      <a href="<%=request.getContextPath()%>/<bean:write name="threadFrom" property="threadId"/>">
                         <bean:write name="threadFrom" property="name"/>
                      </a>
                    </div>
                  </logic:iterate>  
                </logic:notEmpty>
                <logic:notEmpty name="forumMessage" property="reBlogVO.threadTos">
                  <logic:iterate id="threadTo" name="forumMessage" property="reBlogVO.threadTos">
                    <div class="reblogto"> 
                      <a href="<%=request.getContextPath()%>/<bean:write name="threadTo" property="threadId"/>">
                         <bean:write name="threadTo" property="name"/>
                      </a>
                    </div>
                  </logic:iterate>
                </logic:notEmpty>              
              </logic:equal>
              <p></p>
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
  <div class="post_titletag">
    <div class="row">
      <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="tagsi">                  
          <div class="col-md-3">
            <div class="linkblock">	
               <div class="box">
                   <img src="https://static.jdon.com/simgs/thumb/<%=1 + (int) (Math.random() * 4)%>.jpg" border="0" class="thumbnail" loading="lazy"> 
                   <h3> <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag"> 
                          #<bean:write name="threadTag" property="title"/>
                          </a>
                   </h3>  
	             </div>
            </div>        
          </div>                    
      </logic:iterate>
    </div>
  </div>

</logic:equal>
</div>
</div>
<logic:equal name="forumMessage" property="root" value="false">
  </div>
</logic:equal>
</div>
</div>
</div>
	
