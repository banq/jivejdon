<%@ page session="false" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="noheaderfooter" name="noheaderfooter" value=""/>
<logic:empty name="noheaderfooter">
  <bean:define id="forumThread" name="messageListForm" property="oneModel"/>
  <bean:define id="forum" name="forumThread" property="forum"/>
  <bean:define id="title" name="forumThread" property="name"/>
  <bean:define id="pagestart" name="messageListForm" property="start"/>
  <bean:define id="pagecount" name="messageListForm" property="count"/>
  <%
    int pagestartInt = ((Integer) pageContext.getAttribute("pagestart")).intValue();
    int pagecountInt = ((Integer) pageContext.getAttribute("pagecount")).intValue();
    int currentPageNo = 1;
    if (pagecountInt > 0)
      currentPageNo = (pagestartInt / pagecountInt) + 1;
    String titleStr = (String) pageContext.getAttribute("title");
    if (titleStr == null)
      response.sendError(404);
    if (currentPageNo > 1)
      titleStr = titleStr + "  - 第" + currentPageNo + "页";
    pageContext.setAttribute("title", titleStr);
    pageContext.setAttribute("currentPageNo", currentPageNo);
  %>
  <%@ include file="header.jsp" %>
</logic:empty>
<logic:notEmpty name="noheaderfooter">
  <%
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(0, request, response);
  %>
</logic:notEmpty>
<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
  <div class="row">
    <!-- /////////////////左边 -->
    <div id="main-content" class="col-md-8">
      <div class="box">

        <logic:empty name="forumThread">
          <bean:define id="forumThread" name="messageListForm" property="oneModel"/>
          <bean:define id="forum" name="forumThread" property="forum"/>
        </logic:empty>

        <!--  内容-->
        <div id="messageListBody">
          <logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i">
            <%@include file="messageListBody.jsp" %>
          </logic:iterate>            
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
              <logic:notEmpty name="forumMessage" property="reBlogVO.threadFroms">          
                <logic:iterate id="threadFrom" name="forumMessage" property="reBlogVO.threadFroms">                
                  <div class="reblogfrom" id='<bean:write name="threadFrom" property="threadId"/>'></div>                  
                </logic:iterate>                  
              </logic:notEmpty>
              <logic:notEmpty name="forumMessage" property="reBlogVO.threadTos">
                <logic:iterate id="threadTo" name="forumMessage" property="reBlogVO.threadTos">
                  <div class="reblogto" id='<bean:write name="threadTo" property="threadId"/>'></div>
                </logic:iterate>
              </logic:notEmpty>              
          </logic:equal>

          <div class="box">
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
          </div>

        </div>

        <div id="pageEnd"></div>

        <!-- 导航区  -->
        <div class="post_pages_end">
          <div class="table-button-left">
            <div class="table-button-right">
              <logic:greaterThan name="messageListForm" property="numPages" value="1">
                <div class="tres">
                  有<b><bean:write name="messageListForm" property="numPages"/></b>页 <a href="JavaScript:void(0);" onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>
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
      </div>
      <%if (request.getSession(false) != null){%>
       <div class="box">
        <jsp:include page="../message/messagePostReply2.jsp" flush="true">
          <jsp:param name="forumId" value="${forumThread.forum.forumId}"/>
          <jsp:param name="pmessageId" value="${forumThread.rootMessage.messageId}"/>
          <jsp:param name="parentMessageSubject" value="${forumThread.name}"/>
        </jsp:include>
       </div>
      <%}%>
    </div>
    <!-- /////////////////右边 -->
    <div id="sidebar" class="col-md-4">
      <!-- Start Widget -->
      <div class="widget wid-follow">
        <div class="content">
          <ul class="list-inline">
            <form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
              <input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
            </form>
          </ul>
        </div>
      </div>
      <div class="widget wid-post">
        <div class="content">
          <div class="wrap-vid">
          </div>
        </div>
      </div>
      <!---- Start Widget digList---->
      <div class="widget">
        <div class="wid-vid">
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
        </div>
      </div>
      <!-- Start Widget -->
      <div class="widget wid-post">
        <div class="content">
          <div class="post wrap-vid">
              <ul>                  
                  <logic:notEmpty name="forumThread" property="tags">       
                    <logic:iterate id="threadTag" name="forumThread" property="tags"> 
                       <div class="threadTagList" id='<bean:write name="threadTag" property="tagID"/>'></div>          
                    </logic:iterate>
                  </logic:notEmpty>                          
                     
              </ul>
          </div>
        </div>
      </div>

      <!-- Start Widget -->
      <div class="widget">
        <div class="wid-vid">
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
        </div>
      </div>

	    <!-- Start Widget newList -->
			<div class="widget">
        <div class="content">
          <div class="post wrap-vid">
              <ul>
                <logic:notEmpty name="forumThread" property="tags">          
                  <div class="widget_tag_cloud">
                   <div class="tagcloud">
                     <logic:iterate id="threadTag" name="forumThread" property="tags"> 
                        <a href="<%=request.getContextPath()%>/tags/<bean:write name="threadTag" property="tagID"/>" class="tag-cloud-link"><bean:write name="threadTag" property="title"/></a>
                           &nbsp;&nbsp; 
                     </logic:iterate>
                   </div>
                  </div>
                </logic:notEmpty>
                                
                <div id="tagcloud"></div>
              </ul>
          </div>
        </div>				
			</div>

    </div>
  </div>    
</div>


<logic:empty name="noheaderfooter">
  <div id="reply" style="display:none">

    <input type="hidden" id="replySubject" name="replySubject" value="<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>"/>
  </div>

  <%@include file="../common/IncludeBottomBody.jsp"%>

	<div id="to_top" style="display:block; width:20px; height:20px;position:fixed;  bottom:50px; right:40px; border-radius:10px 10px 10px 10px;   text-decoration:none;" onClick="document.documentElement.scrollTop = document.body.scrollTop =0"><a href="#" title="返回顶部"><i class="fa fa-arrow-circle-up"></i></a></div>

   <noscript id="deferred-styles">
	  <link rel="stylesheet" href="https://cdn.jdon.com/js/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="https://cdn.jdon.com/common/js/styles/style.css" type="text/css">
   </noscript>
   <script>
	var loadDeferredStyles = function() {
	  var addStylesNode = document.getElementById("deferred-styles");
	  var replacement = document.createElement("div");
	  replacement.innerHTML = addStylesNode.textContent;
	  document.body.appendChild(replacement)
	  addStylesNode.parentElement.removeChild(addStylesNode);
	};
	var raf = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
		window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
	if (raf) raf(function() { window.setTimeout(loadDeferredStyles, 0); });
	else window.addEventListener('load', loadDeferredStyles);
  </script> 


  <%-- include LAB.js --%>
  <%-- <%@ include file="../account/loginAJAX.jsp" %> --%>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
<script src="https://cdn.jdon.com/common/login2.js"></script>
<script>
  var start = 0;
  var count = 5;  
  var allCount = 0;
  <logic:notEmpty name="threadsInMemallCount">
    allCount = <bean:write name="threadsInMemallCount"/>; 
  </logic:notEmpty>
</script>
<script src="https://cdn.jdon.com/common/messageList9.js"></script>
<script>        
  $(document).ready(function() { 
             
      $('.reblogfrom').each(function(i, obj) {        
        scrollLoadByElementId('/forum/thread.shtml?threadId='+ obj.id,obj.id); 
      });
      $('.reblogto').each(function(i, obj) {        
        scrollLoadByElementId('/forum/thread.shtml?threadId='+ obj.id,obj.id); 
       });
       $('.threadTagList').each(function(i, obj) {        
        scrollLoadByElementId('/forum/threadTagList.shtml?threadId=<bean:write name="forumThread" property="threadId"/>&tagID='+ obj.id,obj.id); 
       });             
      scrollLoadByElementId('https://cdn.jdon.com/tags/tagcloud',"tagcloud");  
      //scrollAppendByElementId('/query/threadNewDigList.shtml',"othersonline","pageEnd",returnAllCount,sumStart,returnStart);
  });      
</script>

  </body>
  </html>
</logic:empty>