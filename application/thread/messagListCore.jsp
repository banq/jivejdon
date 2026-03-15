<%@ page session="false" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>


  <bean:define id="forumThread" name="messageListForm" property="oneModel"/>
  <bean:define id="forum" name="forumThread" property="forum"/>
  <bean:define id="title" name="forumThread" property="name"/>
  <bean:define id="pagestart" name="messageListForm" property="start"/>
  <bean:define id="pagecount" name="messageListForm" property="count"/>
  <bean:define id="pageallCount" name="messageListForm" property="allCount" />
  <%
    int pagestartInt = ((Integer) pageContext.getAttribute("pagestart")).intValue();
    int pagecountInt = ((Integer) pageContext.getAttribute("pagecount")).intValue();
    int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
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

<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <logic:empty name="principal"> 
    <link rel="preconnect" href="https://pagead2.googlesyndication.com" crossorigin>
    <link rel="dns-prefetch" href="https://tpc.googlesyndication.com" >
    <link rel="dns-prefetch" href="https://googleads.g.doubleclick.net" >
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
    </logic:empty>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty> - 极道</title> 
    <meta name="keywords" content="<logic:iterate id="threadTag" name="forumThread" property="tags" ><bean:write name="threadTag" property="title" />,</logic:iterate><bean:write name="forumThread" property="token" />">
    <meta name="description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" /> ">  
    <meta name="author" content="<bean:write name="forumThread" property="rootMessage.account.username" />">
    <meta name="og:title" content="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>">
    <meta name="og:description" content=" <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[50]" /> ">       
    <meta property="og:url" content="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />.html"/>
    <meta property="og:release_date" content="<bean:write name="forumThread" property="creationDate" />"/>
    <meta property="og:image" content="<%=domainUrl %>/simgs/jdon100.png"/> 
    <link rel="stylesheet" href="/js/jdon.css">
    <style>
    html { font-size: 62.5%; -webkit-text-size-adjust: 100%; }
    body { margin: 0; padding: 0; font-family: "Microsoft YaHei", sans-serif; font-size: 1.6rem; color: #333; background-color: #E6E6E6; line-height: 2.2rem; }
    .container { margin: 0 auto; padding: 0 15px; }
    .row { margin: 0 -15px; }
    .row:after, .container:after { content: " "; display: table; clear: both; }
    .col-lg-12, .col-lg-8, .col-lg-4 { position: relative; min-height: 1px; padding: 0 15px; }
    @media (min-width: 768px) { .container { width: 750px; } }
    @media (min-width: 992px) { .container { width: 970px; } }
    @media (min-width: 1200px) {
        .container { width: 128rem; }
        .col-lg-8 { float: left; width: 66.66666667%; }
        .col-lg-4 { float: left; width: 33.33333333%; }
        .col-lg-12 { float: left; width: 100%; }
    }
    .navbar { min-height: 50px; margin-bottom: 20px; background-color: #000; }
    </style> 
    <link rel="apple-touch-icon" href="<%=domainUrl %>/simgs/jdon100.png" />
    <link rel="canonical" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />.html">  
    <%if(pagestartInt != 0 ) {%> 
      <%if(pagestartInt-pagecountInt>0 ) {%>  
          <link rel="prev" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />-from-<%=(pagestartInt-pagecountInt)%>.html"/>
      <%}else{%>
          <link rel="prev" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />.html"/>
       <%}%>
  <%}%> 
  <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
          <link rel="next" href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />-from-<%=pagestartInt+pagecountInt%>.html"/>
  <%}%>
    <link rel="preload" href="/js/fonts/icomoon.woff" as="font" type="font/woff" crossorigin>
    <link rel="preload" href="/js/jquery-bootstrap2.js" as="script">  
    <script defer src="/js/jquery-bootstrap2.js"></script> 
</head>
<body>
<%@ include file="../common/body_header.jsp" %>
<%@ include file="../common/header_errors.jsp" %>

<div id="page-content" class="single-page container">
  <div class="row">
    <!-- /////////////////左边 -->
    <div id="main-content" class="col-lg-8 custom-col-left">
      <div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1)">	
        <!--  内容-->       
        <div id="messageListBody">
          <main>
            <%@include file="messageListBody.jsp" %>
          </main>        
        </div>

        <div id="pageEnd"></div>
  
  <!-- 导航区  -->
              <logic:greaterThan name="messageListForm" property="numPages" value="1">
<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
  <ul class="pagination pull-left">
    <li>
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />-from-<%=(pagestartInt-pagecountInt)%>.html" rel="prev" class="btn-page">上页</a>
    <%}else{%>
        <a href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />.html" rel="prev" class="btn-page">上页</a>
     <%}%>
    </li>  
  </ul>
 <%}%>
</div>
<div class="col-lg-4"></div>

<div class="col-lg-4">
  <ul class="pagination pull-right"> 
    <li>
    <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <a href="<%=domainUrl %>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />-from-<%=pagestartInt+pagecountInt%>.html" rel="next" class="btn-page">下页</a>
    <%}%>
  </li>  
</ul>
</div>

</div>
</div>              
              
              </logic:greaterThan>
      </div>
      <logic:present name="principal"> 
       <div class="box">
        <jsp:include page="../message/messagePostReply2.jsp" flush="true">
          <jsp:param name="forumId" value="${forumThread.forum.forumId}"/>
          <jsp:param name="pmessageId" value="${forumThread.rootMessage.messageId}"/>
          <jsp:param name="parentMessageSubject" value="${forumThread.name}"/>
        </jsp:include>
       </div>
      </logic:present>
    </div>

<aside>
    <!-- /////////////////右边 -->
    <div id="sidebar" class="col-lg-4 custom-col-right">
<div class="scrolldiv"><div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1); background-color: white; overflow: hidden; padding-left: 0; padding-right: 0">
      <!-- Start Widget -->
      <div class="widget wid-follow">
        <div class="content">
          <ul class="list-inline">
            <form role="form" class="form-horizontal" method="post" action="<%=domainUrl %>/query/threadViewQuery.shtml">
              <input type="text" value="<bean:write name="forumThread" property="token" />" name="query" id="v_search" class="form-control">
            </form>
          </ul>
        </div>
      </div>
		
     <div class="widget wid-post">
        <div class="content">
               <div id="searchResult">                		
                
                  <div id="threadPreNextList">
                    <logic:notEmpty name="threadPreNextList" >   
                      <ul style="list-style-type:none;padding:0">
                      <%int previewImgCounter = 0;%>
                      <logic:iterate id="forumThreadPreNext" name="threadPreNextList" length="5">
                        <li class="box">
                          <div style="display: flex; align-items: center;"> 
                          <div class="wrap-vid">              
                            <div class="thumbn">
                                <img src="/simgs/thumb/<%=(previewImgCounter % 3)%>.jpg" border='0' class="img-thumbnail" loading="lazy" width="35" height="45"/>                  
                            </div>  
                           </div>
                          <div class="vid-name" style="margin-left: 2px; flex: 1;white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                            <div itemprop="citation" itemscope itemtype="https://schema.org/Article">
                            <a href="<%=domainUrl %>/<bean:write name="forumThreadPreNext" property="threadId"/><bean:write name="forumThreadPreNext" property="pinyinToken" />.html" itemprop="url">
                               <span itemprop="headline"><bean:write name="forumThreadPreNext" property="name"/></span>
                            </a>
                            </div>
                          </div>
                       
    
                 
                          </div>
                          </li>
                      <%previewImgCounter++;%>
                      </logic:iterate>  
                    </ul>
                    </logic:notEmpty>

                  </div>   
                 
							                           
                
              </div> 
        </div>
      </div>     

      <div class="widget wid-post">
        <div class="content">
      <h4>本文话题：</h4>
          <ul class="list-inline">
        
            <logic:iterate id="threadTag" name="forumThread" property="tags" >
              <li class="tagcloud">
                <a href="<%=domainUrl %>/tag/<bean:write name="threadTag" property="tagID"/>/" target="_blank" class="tag-cloud-link">
                  <bean:write name="threadTag" property="title" /></a>
              </li>
           
            </logic:iterate>

          </ul>
        </div>
      </div>

      <!-- Start Widget -->
     <div class="widget wid-post">
        <div class="content">
          
                
							
<div id="digList" class="linkblock">
  <ul style="list-style-type:none;padding:0">
<li class="box">
         <div style="display: flex; align-items: center;"> 
         <div class="wrap-vid">              
          <div class="thumbn">
              <img src="/simgs/thumb/2.jpg" border="0" class="img-thumbnail" loading="lazy" width="35" height="45">
            </div>
          </div>
         <h4 class="vid-name" style="margin-left: 2px; flex: 1;white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><a href="https://www.jdon.com/69062-shenmeshiCon.html" class="hover-preload">什么是Context上下文？</a></h4>

        </div>
</li>

 <li class="box">
         <div style="display: flex; align-items: center;"> 
         <div class="wrap-vid">              
          <div class="thumbn">
                    <img src="/simgs/thumb/3.jpg" border="0" class="img-thumbnail" loading="lazy" width="35" height="45">                  
                  </div>  
                </div>
         <h4 class="vid-name" style="margin-left: 2px; flex: 1;white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><a href="https://www.jdon.com/69903-chouxiangliangzhongfangfa.html" class="hover-preload">抽象两种方法：上下文与类型</a></h4>

        </div>
</li>

 <li class="box">
         <div style="display: flex; align-items: center;"> 
         <div class="wrap-vid">              
          <div class="thumbn">
                    <img src="/simgs/thumb/1.jpg" border="0" class="img-thumbnail" loading="lazy" width="35" height="45">                  
                  </div>  
                </div>
         <h4 class="vid-name" style="margin-left: 2px; flex: 1;white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><a href="https://www.jdon.com/76821-shangxiawengongcheng.html" class="hover-preload">Content与Context一字之差暗藏逆天极道</a></h4>

        </div>
</li>

 <li class="box">
         <div style="display: flex; align-items: center;"> 
         <div class="wrap-vid">              
          <div class="thumbn">
                    <img src="/simgs/thumb/3.jpg" border="0" class="img-thumbnail" loading="lazy" width="35" height="45">                  
                  </div>  
                </div>
         <h4 class="vid-name" style="margin-left: 2px; flex: 1;white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><a href="https://www.jdon.com/79419-yujing-zhuyili.html" class="hover-preload">语境崩塌：你的注意力正被劫持</a></h4>

        </div>
</li>

 <li class="box">
         <div style="display: flex; align-items: center;"> 
         <div class="wrap-vid">              
          <div class="thumbn">
              <img src="/simgs/thumb/2.jpg" border="0" class="img-thumbnail" loading="lazy" width="35" height="45">
            </div>
          </div>
         <h4 class="vid-name" style="margin-left: 2px; flex: 1;white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><a href="https://www.jdon.com/design.htm" class="hover-preload">Context逻辑之道</a></h4>

        </div>
</li>

 </ul>

</div>                  
							 </div>   
         
        </div>
      </div>

    </div>
</div></aside>

</div>
</div>

  <div id="reply" style="display:none">
    <input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
    <input type="hidden" id="replySubject" name="replySubject" value="<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>">
  </div>



<%@include file="../common/IncludeBottomBody.jsp"%>

<script defer>
document.addEventListener("DOMContentLoaded", function() {
    var img = new Image();
    img.src = "/viewThread/count.gif?threadId=<bean:write name='forumThread' property='threadId'/>";
});
</script>

</body>
</html>
