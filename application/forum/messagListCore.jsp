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
        </div>

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
      <div class="box">
        <div class="box-header header-natural">
        </div>
        <div class="box-content">
          <div class="row">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
            <!-- 关键词推广 -->
            <ins class="adsbygoogle"
                 style="display:block"
                 data-ad-client="ca-pub-7573657117119544"
                 data-ad-slot="6351391093"
                 data-ad-format="link"
                 data-full-width-responsive="true"></ins>
            <script>
                (adsbygoogle = window.adsbygoogle || []).push({});
            </script>
          </div>
        </div>
      </div>
      <div class="box">
        <jsp:include page="../message/messagePostReply2.jsp" flush="true">
          <jsp:param name="forumId" value="${forumThread.forum.forumId}"/>
          <jsp:param name="pmessageId" value="${forumThread.rootMessage.messageId}"/>
          <jsp:param name="parentMessageSubject" value="${forumThread.name}"/>
        </jsp:include>
      </div>
      <div class="box">
        <div class="box-header header-natural">
        </div>
        <div class="box-content">
          <div class="row">
            <div id="approved" class="approved"></div>
          </div>
        </div>
      </div>
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
      <!---- Start Widget digList---->
      <div class="widget">
        <div class="wid-vid">
          <ul>
            <div id="digList"></div>
          </ul>
        </div>
      </div>
      <!-- Start Widget -->
      <div class="widget wid-post">
        <div class="content">
          <div class="post wrap-vid">
            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
            <!-- 页上左336 -->
            <ins class="adsbygoogle"
                 style="display:inline-block;width:336px;height:280px"
                 data-ad-client="ca-pub-7573657117119544"
                 data-ad-slot="6751585519"></ins>
            <script>
                (adsbygoogle = window.adsbygoogle || []).push({});
            </script>
          </div>
        </div>
      </div>
      <!-- Start Widget urlList -->
      <div class="widget">
        <div class="wid-vid">
          <ul>
            <div id="threadNewList"></div>
          </ul>
        </div>
      </div>
      <!-- Start Widget -->
      <div class="widget">
        <div class="wid-vid">
          <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
          <ins class="adsbygoogle"
               style="display:block"
               data-ad-format="autorelaxed"
               data-ad-client="ca-pub-7573657117119544"
               data-ad-slot="7669317912"></ins>
          <script>
              (adsbygoogle = window.adsbygoogle || []).push({});
          </script>
        </div>
      </div>
      <!-- Start Widget poplist -->
      <div class="widget">
        <div class="wid-vid">
          <ul>
            <div id="poplist"></div>
          </ul>
        </div>
      </div>
      <!-- Start Widget -->
      <div class="widget">
        <div class="wid-vid">
          <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
          <!-- 推荐贴右侧300x600 -->
          <ins class="adsbygoogle"
               style="display:inline-block;width:300px;height:600px"
               data-ad-client="ca-pub-7573657117119544"
               data-ad-slot="3352261515"></ins>
          <script>
              (adsbygoogle = window.adsbygoogle || []).push({});
          </script>
        </div>
      </div>
      <!-- Start Widget -->
      <div class="widget">
        <div class="wid-vid">
          <ul>
            <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="tagsi">
              <span id="tagthreads_<bean:write name="threadTag" property="tagID"/>"></span>
            </logic:iterate>

          </ul>
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
  <%@ page contentType="text/html; charset=UTF-8" %>
  <!-- Bootstrap Core CSS -->
  <link rel="stylesheet" href="https://static.jdon.com/js/bootstrap.min.css"  type="text/css">
  <!-- Custom Fonts -->
  <link rel="stylesheet" href="https://static.jdon.com/js/font-awesome-4.4.0/css/font-awesome.min.css"  type="text/css">
  <!-- Custom CSS -->
  <link rel="stylesheet" href="https://static.jdon.com/common/js/styles/style.css">
  <%@include file="../common/IncludeBottomBody.jsp"%>

  <%-- include LAB.js --%>
  <%@ include file="../account/loginAJAX.jsp" %>
  <script src="https://static.jdon.com/common/messageList7.js"></script>
  <script>
      load('https://cdn.jdon.com/query/threadDigList', function (xhr) {
          document.getElementById("digList").innerHTML = xhr.responseText;
      });
      load('https://cdn.jdon.com/query/threadNewList', function (xhr) {
          document.getElementById("threadNewList").innerHTML = xhr.responseText;
      });
      load('https://cdn.jdon.com/query/popularList', function (xhr) {
          document.getElementById("poplist").innerHTML = xhr.responseText;
      });
      window.onload = function () {
          <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="tagsi">
          <logic:equal name="tagsi" value="0">
          load('https://cdn.jdon.com/query/approved/<bean:write name="threadTag" property="tagID"/>', function (xhr) {
              document.getElementById('approved').innerHTML = xhr.responseText;
          });
          </logic:equal>
          <logic:notEqual name="tagsi" value="0">
          tagthreads(10, 160, 10, <bean:write name="threadTag" property="tagID"/>);
          </logic:notEqual>
          </logic:iterate>
      }
  </script>

  </body>
  </html>
</logic:empty>
    
    
    
