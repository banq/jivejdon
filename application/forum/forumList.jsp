<%@ page session="false"  %>
<%@page isELIgnored="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%          
if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCacheForum(1 * 10 * 60, this.getServletContext(), request, response)) {
     return ;
 }	
%>
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <link rel="preconnect" href="https://pagead2.googlesyndication.com" crossorigin>
  <link rel="dns-prefetch" href="https://tpc.googlesyndication.com" >
  <link rel="dns-prefetch" href="https://googleads.g.doubleclick.net" >
  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
   <bean:define id="title"  value="极道场Dojo" />
   <%@include file="../common/IncludeTopHead.jsp"%>
   <meta name="Description" content="极道Jdon：IT科技、生物极客、软硬件技术道场">

<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>
</head>
<body>
<%@ include file="../common/body_header.jsp" %>
 <main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
            <div id="main-content" class="col-lg-8">
				<div class="box">		
                     <div class="box">
	                        <div class="box">

   
  
<logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
        <bean:define id="currentForumId" name="forum" property="forumId" type="java.lang.Long" />
        <div>
              
                 <h1 style="font-size: 2.2rem">
                 <a href="/forum/<bean:write name="forum" property="forumId" />/rss"><i class="fa fa-feed"></i></a>  
                 <a href="<%=request.getContextPath()%>/forum/<bean:write name="forum" property="forumId" />/" class="hover-preload">                
                       <b><span class="threadTitle"><bean:write name="forum" property="name" /></span></b>
                 </a>
                 </h1>
               
                 <%-- <%if (request.getSession(false) != null && request.getUserPrincipal() != null){%>
                      <a title="关注本道场" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=0&subscribeId=<bean:write name="forum" property="forumId" />"  >
                      <img src="/images/user_add.gif" width="18" height="18" alt="关注本道场" border="0" /></a>                                                         
                     <%
                 }
                 %> --%>

                <p class="home_content" ><bean:write name="forum" property="description" filter="false"/></p>
                <div class="linkblock">
                  <jsp:include page="/query/threadNewList.jsp" flush="false">
                    <jsp:param name="forumId" value="<%= currentForumId.toString() %>" />
                    <jsp:param name="count" value="5" />
                  </jsp:include>
                </div>
        </div>
</logic:iterate>

       </div>
        </div>
    </div>	
    </div>
        
 
	  
	 <!-- /////////////////右边 -->
<aside>      
       <div id="sidebar" class="col-lg-4">
<div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(150, 1, 1, 0.1); background-color: white; overflow: hidden; padding-left: 0; padding-right: 0">
        <!---- Start Widget ---->
        <div class="widget wid-follow">
          <div class="content">
            <ul class="list-inline">
              <form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
						 <input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
					 </form>
            </ul>           
          </div>
        </div>
     


        <!---- Start Widget ---->
       <div class="widget">
           <div class="wid-vid">                     
                 <div id="digList"><jsp:include page="/query/threadDigList.shtml?count=10" flush="true" /></div>
						</div>    
          </div>
</div>
 


</div></aside>  

	   </div>
</div>
</main>


<%@ include file="../common/IncludeBottomBody.jsp" %> 


<script type="speculationrules">
     {
       "prefetch": [{
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


</body>
</html>
