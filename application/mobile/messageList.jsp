<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:size id="messageCount" name="messageListForm" property="list" />
<logic:equal name="messageCount" value="0">无此贴 或已经删除</logic:equal>
<logic:greaterThan name="messageCount" value="0">
  <bean:define id="forumThread" name="messageListForm" property="oneModel" />
  <bean:define id="forum" name="forumThread" property="forum" />
  <bean:define id="title" name="forumThread" property="name" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty>
<logic:empty  name="title">
  解道jdon移动版
</logic:empty>
</title>
<link rel="dns-prefetch" href="//pagead2.googlesyndication.com" />
<link rel="dns-prefetch" href="//googleads.g.doubleclick.net" />
<link rel="dns-prefetch" href="//gstatic.com" />
<link rel="dns-prefetch" href="//googleadservices.com" />
<link rel="dns-prefetch" href="//cm.g.doubleclick.net" />
<link rel="dns-prefetch" href="//static.googleadsserving.cn" />
<%@ include file="../common/security.jsp" %>
<script>
	<%
	String furl = "/";
	if (request.getParameter("thread") != null){
      furl ="/" + request.getParameter("thread");      
    }
	%>
</script> 	      
<link rel="stylesheet" href="<%=request.getContextPath()%>/common/jquery.mobile-1.1.0.min.css" />
<%
    String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
    domainUrl = domainUrl + request.getContextPath();
%>    
<link rel="canonical" href="<%=domainUrl %>/<%=request.getParameter("thread")%>" >      

<bean:define id="pagecount" name="messageListForm" property="count" />
<bean:define id="allCount" name="messageListForm" property="allCount" />
<bean:define id="pageId" name="forumThread" property="threadId" />
<% String preurl = request.getContextPath() + "/mobile/"+ pageContext.getAttribute("pageId");%>
<%
int pagestartInt = 0;
if (request.getParameter("start") != null){
   pagestartInt =Integer.parseInt(request.getParameter("start"));
}
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();

int currentPageNo = 0;
if (pagecountInt > 0){
	currentPageNo = pagestartInt / pagecountInt;	
}
int allCountInt = ((Integer)pageContext.getAttribute("allCount")).intValue();
int numRepliesStart = allCountInt - 1 * pagecountInt;
int nextPageStart = 0;
if (currentPageNo * pagecountInt < numRepliesStart)
	nextPageStart = (currentPageNo + 1) * pagecountInt;

int prePageNo = 0;
if (currentPageNo > 1)
  prePageNo = (currentPageNo-1)* pagecountInt;

int allPageNo = (int)(allCountInt/pagecountInt);
if (allCountInt % pagecountInt > 0 )  
   allPageNo = allPageNo + 1;

allPageNo = allPageNo -1;

%>    
<% if (currentPageNo == 1) {%>
<link rel="prev" href="<%=preurl%>" />
<% } %>
<% if (currentPageNo > 1) {%>
<link rel="prev" href="<%=preurl%>/<%=prePageNo%>" />
<% } %>
<% if (currentPageNo * pagecountInt < numRepliesStart) {%>
<link rel="next" href="<%=preurl%>/<%=nextPageStart%>" />
<% } %>
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
  (adsbygoogle = window.adsbygoogle || []).push({
    google_ad_client: "ca-pub-7573657117119544",
    enable_page_level_ads: true
  });
</script>
</head> 
<body> 
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
<%-- <%@ include file="adsense.jsp" %> --%>
<span id="fastclick">
<div data-role="page" id="homeP">
    <logic:iterate id="forumMessage" name="messageListForm" property="list" length="1" indexId="i">
      <div data-role="header">
        <h3>
        	<logic:notEmpty name="forumMessage" property="messageVO.linkUrl">
          		<a href="<bean:write name="forumMessage" property="messageVO.linkUrl" filter="false"/>"  target="_blank">
          		 <bean:write name="forumMessage" property="messageVO.subject"/>
                </a>
            </logic:notEmpty>
            <logic:empty  name="forumMessage" property="messageVO.linkUrl">
              <bean:write name="forumMessage" property="messageVO.subject"/>
            </logic:empty>          
        </h3>
        <a href="<%=request.getContextPath() %>/mobile/new"  data-icon="home" class="ui-btn-left">Home</a> </div>
      <!-- /header -->
      
      <div data-role="content">
        <h3>
        	<logic:notEmpty name="forumMessage" property="messageVO.linkUrl">
          		<a href="<bean:write name="forumMessage" property="messageVO.linkUrl" filter="false"/>"  target="_blank">
          		 <bean:write name="forumMessage" property="messageVO.subject"/>
                </a>
            </logic:notEmpty>
            <logic:empty  name="forumMessage" property="messageVO.linkUrl">
              <bean:write name="forumMessage" property="messageVO.subject"/>
            </logic:empty>          
        </h3>      
      
      <a href="<%=request.getContextPath() %>/mobile/blog/<bean:write name="forumMessage" property="account.username"/>" target="_blank" data-role="button" data-mini="true" data-inline="true">
        <bean:write name="forumMessage" property="account.username"/>
        </a> &nbsp;
        <bean:write name="forumMessage" property="creationDate"/>
        <br>

      <div data-ajax="false">
      <logic:present name="forumMessage" property="reBlogVO.messageFrom">
        <bean:define id="messageFrom" name="forumMessage" property="reBlogVO.messageFrom" />
        转自:<a href="<%=request.getContextPath()%>/mobile/<bean:write name="messageFrom" property="forumThread.threadId"/>" rel="nofollow"><bean:write name="messageFrom" property="forumThread.name"/></a>
      </logic:present>
      
        <br>
        <bean:write name="forumMessage" property="messageVO.body" filter="false"/>        
        <br>
       <logic:notEmpty name="forumMessage" property="reBlogVO.messageTos">
        <logic:iterate id="forumMessageTo" name="forumMessage" property="reBlogVO.messageTos" >
          转发至:<a href="<%=request.getContextPath()%>/mobile/<bean:write name="forumMessageTo" property="forumThread.threadId"/>"><bean:write name="forumMessageTo" property="messageVO.subject"/></a>
        </logic:iterate>
      </logic:notEmpty>           
       <p></p>        
       <logic:equal name="forumMessage" property="root" value="true">
          <center><span id="digNumber_<bean:write name="forumMessage" property="messageId"/>" data-role="button" data-mini="true" data-inline="true" >
          <a href="javascript:digMessage('<bean:write name="forumMessage" property="messageId"/>')">赞</a>          
          <logic:notEqual name="forumMessage" property="digCount" value="0">
            <bean:write name="forumMessage" property="digCount"/>
          </logic:notEqual>
          </span></center>
      </logic:equal>                
        </div>
       <p></p>              
        <fieldset class="ui-grid-b" >
          <div class="ui-block-a"> </div>
          <div class="ui-block-b">
            <logic:equal name="messageListForm" property='<%= "authenticated[" + i + "]" %>' value="true"> <a href="<%=request.getContextPath()%>/mobile/message/messageAction.shtml?action=edit&newPageNo=<%=currentPageNo * pagecountInt%>&messageId=<bean:write  name="forumMessage" property="messageId" />" 
              rel="nofollow" data-role="button" data-rel="dialog" data-transition="pop"  data-theme="b">编辑</a> </logic:equal>
          </div>
          <div class="ui-block-c"> </div>
        </fieldset>
      </div>
    </logic:iterate>

    <div data-role="content" >
        <fieldset class="ui-grid-e" >
          <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="j"> <a href='<%=request.getContextPath() %>/mobile/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" data-role="button"  data-mini="true" data-inline="true">
            <bean:write name="threadTag" property="title" />
            </a> &nbsp; </logic:iterate>
        </fieldset>                
    </div>
    
<logic:equal name="forumMessage" property="root" value="true">
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 自动调整尺寸 -->
<ins class="adsbygoogle"
     style="display:block"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="9040920314"
     data-ad-format="auto"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>     
</logic:equal>	                   
    
<div data-role="content">
  <div class="ui-grid-a">
    <div class="ui-block-a">
      <% if (currentPageNo == 1) {%>
      <a href="<%=preurl%>" data-role="button" 	 data-ajax="false" >上一页</a>
      <% } %>
      <% if (currentPageNo > 1) {%>
      <a href="<%=preurl%>/<%=prePageNo%>" data-role="button"  data-ajax="false" 	data-icon="arrow-l">上一页</a>
      <% } %>
      <% if (currentPageNo == 0 && currentPageNo * pagecountInt < numRepliesStart) {%>
      <a href="<%=preurl%>/<%=allPageNo* pagecountInt%>" data-role="button"  data-ajax="false" 	data-icon="arrow-l">末页</a>
      <% } %>
    </div>
    <div class="ui-block-b">
      <% if (currentPageNo * pagecountInt < numRepliesStart) {%>
      <a href="<%=preurl%>/<%=nextPageStart%>" data-role="button"  data-ajax="false" data-icon="arrow-r"	>下一页</a>
      <% } %>
      <% if (currentPageNo * pagecountInt >= numRepliesStart && currentPageNo >= 1) {%>
      <a href="<%=preurl%>" data-role="button"  data-ajax="false" 	data-icon="arrow-r"	>首页</a>
      <% } %>
    </div>
  </div>
</div>
<script language="javascript" src="/common/mb.js" ></script> 
<script>detectRes("<%=furl%>");</script>   
<%@include file="js.jsp"%>          
<script> 
   var sId = "";
	 <logic:notEmpty name="forumThread" property="state.lastPost">
	    sId = '<bean:write name="forumThread" property="state.lastPost.modifiedDate2" />';
	 </logic:notEmpty>
	 viewcount('<bean:write name="forumThread" property="threadId" />', sId);
   
</script>
<p><br></p>
<p><br></p>

</body>
</html>

</logic:greaterThan>
