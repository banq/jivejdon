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
              <div class="box">
                <div class="row">                  
                    <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="i" length="2" offset="0">                  
                      <div class="col-md-6">
                        <div class="linkblock">	
                           <div class="box">        
                           <div class="wrap-vid">
                              <div class="thumbn">
                                 <img src="//cdn.jdon.com/simgs/thumb/<%=1 + i%>.jpg" border="0" class="thumbnail" loading="lazy">  
                              </div>  
                              <div class="widget">			 
                                 <div class="info">	
                                    <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag"> 
                                      <bean:write name="threadTag" property="title"/>
                                    </a>                               
			                           </div>  
                              </div>
                           </div>                                                      
	                         </div>
                        </div>        
                      </div>  
                    </logic:iterate>
                </div>    
                <div class="row">                                                  
                    <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="i" length="2" offset="2">                  
                      <div class="col-md-6">
                        <div class="linkblock">	
                           <div class="box">       
                           <div class="wrap-vid">
                              <div class="thumbn">
                                 <img src="//cdn.jdon.com/simgs/thumb/<%=3 + i%>.jpg" border="0" class="thumbnail" loading="lazy">  
                              </div>  
                              <div class="widget">			 
                                 <div class="info">	
                                    <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag"> 
                                      <bean:write name="threadTag" property="title"/>
                                      </a>
			                           </div>  
                              </div>
                           </div>                                                      
	                         </div>
                        </div>
                      </div>    
                    </logic:iterate>                  
                </div>
              </div>            
            </logic:equal>
          </logic:iterate>
        </div>

        <div class="box">
          <blockquote><em class="smallgray">猜你喜欢</em></blockquote>
          <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
          <ins class="adsbygoogle"
               style="display:block"
               data-ad-format="autorelaxed"
               data-ad-client="ca-pub-7573657117119544"
               data-ad-slot="7669317912"></ins>
          <script>
              (adsbygoogle = window.adsbygoogle || []).push({});
          </script>
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
			      <div class="thumbn"><img src="//static.jdon.com/simgs/forum/ddd-book.png" class="thumbnail" loading="lazy"></div> 
						<p><br>本站原创<br><a href="/54881" target="_blank">《复杂软件设计之道：领域驱动设计全面解析与实战》</a></p>
          </div>
        </div>
      </div>
      <!---- Start Widget digList---->
      <div class="widget">
        <div class="wid-vid">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
            <!-- 页面右侧上部336x280 20-06-18 -->
            <ins class="adsbygoogle"
                 style="display:block"
                 data-ad-client="ca-pub-7573657117119544"
                 data-ad-slot="6751585519"
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
                  <div id="digList"></div>
              </ul>
          </div>
        </div>
      </div>

      <!-- Start Widget -->
      <div class="widget">
        <div class="wid-vid">
          <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
          <!-- 右侧中部300x600 20-06-18 -->
          <ins class="adsbygoogle"
               style="display:block"
               data-ad-client="ca-pub-7573657117119544"
               data-ad-slot="3352261515"
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
                <blockquote><em class="smallgray">其他人在看</em></blockquote>
                <div id="othersonline"></div>
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

  <%--
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-html" prefix="html" %>
--%>
  <%@include file="../common/IncludeBottomBody.jsp"%>

  <%-- include LAB.js --%>
  <%-- <%@ include file="../account/loginAJAX.jsp" %> --%>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
<script src="https://static.jdon.com/common/login2.js"></script>
<script>
  var start = 0;
  var count = 5;  
  var allCount = 0;
  <logic:notEmpty name="threadsInMemallCount">
    allCount = <bean:write name="threadsInMemallCount"/>; 
  </logic:notEmpty>
</script>
<script src="https://static.jdon.com/common/messageList9.js"></script>
<script>        
  $(document).ready(function() { 
      scrollLoadByElementId('/query/threadDigList',"digList");            
      $('.reblogfrom').each(function(i, obj) {        
        scrollLoadByElementId('/forum/thread.shtml?threadId='+ obj.id,obj.id); 
      });
      $('.reblogto').each(function(i, obj) {        
        scrollLoadByElementId('/forum/thread.shtml?threadId='+ obj.id,obj.id); 
       });
      scrollAppendByElementId('/query/threadNewDigList.shtml',"othersonline","pageEnd",returnAllCount,sumStart,returnStart);
  });      
</script>

  </body>
  </html>
</logic:empty>
