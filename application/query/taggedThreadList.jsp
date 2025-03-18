<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<bean:parameter name="queryType" id="queryType" value=""/>
<bean:parameter name="tagID" id="tagID" value=""/>
<logic:empty  name="TITLE">
<%response.sendError(404);%>
</logic:empty>   
<logic:notEmpty  name="TITLE">
<bean:define id="title" name="TITLE" />
<logic:empty name="threadListForm" property="list">
<% 
  response.setHeader("X-Robots-Tag", "noindex");  // 防止被搜索引擎索引
     response.setStatus(HttpServletResponse.SC_GONE);  // 使用 410 表示该页面已被永久删除
  %>
</logic:empty>
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="pageallCount" name="threadListForm" property="allCount" />
<%

int pagestartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
int currentPageNo = 1;

if (pagecountInt > 0) {
	currentPageNo = (pagestartInt / pagecountInt) + 1;
}
String titleStr = (String)pageContext.getAttribute("title");
if (currentPageNo > 1){
	titleStr = titleStr + "  - 第"+ currentPageNo + "页";
}
pageContext.setAttribute("title", titleStr);
%>
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
   <%@include file="../common/IncludeTopHead.jsp"%>

   <meta name="Description" content="有关<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>系列">
<meta name="Keywords" content="<logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty>">
<link rel="canonical" href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/"/>  
<% if (request.getParameter("r") == null){ %>  
<%if(pagestartInt != 0 ) {%> 
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <link rel="prev" href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/<%=(pagestartInt-pagecountInt)%>"/>
    <%}else{%>
        <link rel="prev" href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/"/>
     <%}%>
 <%}%>
 <%if((pagestartInt+pagecountInt) < pageAllcountInt ) {%> 
    <link rel="next" href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/<%=pagestartInt+pagecountInt%>"/>
 <%}%>
<% } %>  

<style>
  dialog {
      max-width: 810px;
      width: 90%;
      padding: 20px;
      border: none;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0,0,0,0.3);
      background-color: white; /* 添加白色背景 */
      overflow: hidden; /* 隐藏所有滚动条 */
            max-height: 90vh; /* 限制最大高度 */
            box-sizing: border-box; /* 确保padding包含在宽度计算中 */
  }
  dialog::backdrop {
      background: rgba(0,0,0,0.4);
  }
  .dialog-content {
      margin-top: 15px;
      width: 100%;
      min-height: 400px;
  }
  .close-btn {
      float: right;
      cursor: pointer;
      background: #41872d;
      color: white;
      border: none;
      padding: 5px 10px;
      border-radius: 3px;
  }
  .vid-name a {
      cursor: pointer;
  }
  iframe {
      width: 100%;
      height: 500px;
      border: none;
  }
</style>
<meta http-equiv="refresh" content="3600">
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>
</head>
<body>
<%@ include file="../common/body_header.jsp" %>

<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
 


<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-lg-12">

        <div class="box"> 
        


       <center>
        
        <h2 class="tagcloud"><a href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/" class="tag-cloud-link"><bean:write  name='TITLE'/></a></h2>
  
      <div>
       
             <%if (request.getSession(false) != null && request.getUserPrincipal() != null){%>
           
                 <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="tagID" /> "><i class="fa fa-heart"></i></a>    
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本标签" border="0" /></a>                                                         
            <%
            }
        %>
      
  
       &nbsp;&nbsp;  
       <a href="<%=domainUrl%>/random/taggedThreadList.shtml?tagID=<bean:write name="tagID"/>&count=15&r=1" rel="nofollow">
       <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
       </a>

       </div>

    
		  
      </center>

<main>
<ul style="list-style-type:none;padding:0">
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
  <%@ include file="threadListCore.jsp" %>
</logic:iterate>
</ul>
</main>



        </div>


 

<% if (request.getParameter("r") == null){ %>  

<div class="box">
<div class="row">

<div class="col-lg-4">
 <%if(pagestartInt != 0 ) {%> 
  <ul class="pagination pull-left">
    <li>
    <%if(pagestartInt-pagecountInt>0 ) {%>  
        <a href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/<%=(pagestartInt-pagecountInt)%>" rel="prev nofollow" class="btn-page">上页</a>
    <%}else{%>
        <a href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/" rel="prev" class="btn-page">上页</a>
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
    <a href="<%=domainUrl%>/tag/<bean:write name="tagID"/>/<%=pagestartInt+pagecountInt%>" rel="next nofollow" class="btn-page">下页</a>
    <%}%>
  </li>  
</ul>
</div>

</div>
</div>
<% } %>  

      </div>  

  </div>
</div>  



<%@ include file="../common/IncludeBottomBody.jsp" %> 


<script type="speculationrules">
  {
    "prerender": [{
      "source": "document",
      "where": {
        "and": [
          { "selector_matches": ".hover-preload" },
          { "not": { "selector_matches": ".do-not-prerender" } }
        ]
      },
      "eagerness": "moderate"
    }]
  }
  </script>
  
   <!-- 添加全局dialog -->
   <dialog id="contentDialog">
    <button class="close-btn" onclick="closeDialog()">关闭</button>
    <div class="dialog-content">
        <iframe id="contentFrame"></iframe>
    </div>
</dialog>

<!-- 页脚部分保持不变 -->

<script>
    function showDialog(dialogId, url) {
        const dialog = document.getElementById('contentDialog');
        const iframe = document.getElementById('contentFrame');
        iframe.src = url;
        dialog.showModal();
    }

    function closeDialog() {
        const dialog = document.getElementById('contentDialog');
        const iframe = document.getElementById('contentFrame');
        iframe.src = ''; // 清空iframe内容
        dialog.close();
    }
</script>

</body>
</html>

</logic:notEmpty>