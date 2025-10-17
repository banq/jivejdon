<%@ page session="false"  %>
<%@page isELIgnored="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%          
if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCacheForum(5 * 60 * 60, this.getServletContext(), request, response)) {
     return ;
 }	
%>
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
  <link rel="preconnect" href="https://pagead2.googlesyndication.com" crossorigin>
  <link rel="preconnect" href="https://tpc.googlesyndication.com" crossorigin>
  <link rel="preconnect" href="https://googleads.g.doubleclick.net" crossorigin>   
   <bean:define id="title"  value="教程道场Coding Dojo" />
   <%@include file="../common/IncludeTopHead.jsp"%>
   <meta name="Description" content="软件技术设计与编程道场">
<meta name="Keywords" content="前后端,语言平台,领域驱动设计,领域建模,逻辑方法,SpringBoot和SpringCloud,平台架构,DevOps,Github工具,企业软件,架构设计,大科技、自然科学,产品领域,科普,大语言模型,数据科学,认知偏见">
<link rel="sitemap" type="application/xml" title="Sitemap" href="<%=domainUrl%>/sitemap.xml">

<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>
</head>
<body>
<%@ include file="../common/body_header.jsp" %>
 <main>
  <script>
    const ids = []; // 初始化一个空数组
  </script>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
            <div id="main-content" class="col-lg-8 custom-col-left">
				<div class="box">		
                     <table class="table table-striped">
	<tbody>

   
  
<logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
        <tr>
            <td>
              
                 <h1 style="font-size: 2.2rem">
                 <a href="/forum/<bean:write name="forum" property="forumId" />/rss"><i class="fa fa-feed"></i></a>  
                 <a href="<%=request.getContextPath()%>/forum/<bean:write name="forum" property="forumId" />/" class="hover-preload">                
                       <b><span class="threadTitle"><bean:write name="forum" property="name" /></span></b>
                 </a>
                 </h1>
               
                 <%if (request.getSession(false) != null && request.getUserPrincipal() != null){%>
                      <a title="关注本道场" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=0&subscribeId=<bean:write name="forum" property="forumId" />"  >
                      <img src="/images/user_add.gif" width="18" height="18" alt="关注本道场" border="0" /></a>                                                         
                     <%
                 }
                 %>

                <br>
                <span class="home_content" ><bean:write name="forum" property="description" filter="false"/></span>
                <div id="threadNewList_<bean:write name="forum" property="forumId"/>" class="linkblock">
                  <br><br><br><br><br><br>     
                </div>  
               
  <script>
    ids.push(<bean:write name="forum" property="forumId"/>);
  </script>
       
         
                <div class="box">

<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="fluid"
     data-ad-layout-key="-ev-1p-5j-ot+26n"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3378777426"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>
                </div>
            </td>
            
        </tr>

</logic:iterate>

       </tbody>
        </table>
    </div>	
    </div>
        
 
	  
<script>
 async function fetchData() {
    for (const id of ids) {
            try {
              const params = new URLSearchParams({
                forumId: id,        
                count: 5 
               });
               const response = await fetch(`/query/threadNewList.shtml?\${params.toString()}`);
               if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const html = await response.text();
                document.getElementById(`threadNewList_\${id}`).innerHTML = html;
            } catch (error) {
                console.error('Fetch error:', error);
            }
    }
  }

  fetchData();    
      </script>


	 <!-- /////////////////右边 -->
<aside>      
       <div id="sidebar" class="col-lg-4 custom-col-right">
<div class="scrolldiv"><div class="box" style="max-width:360px;border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1); background-color: white; overflow: hidden; padding-left: 0; padding-right: 0">
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
							    
							       <div id="digList" class="linkblock"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>   
							    <script defer>
					               document.addEventListener("DOMContentLoaded", function(event) { 
                                 $(document).ready(function() {      
                                             $('#digList').load("/query/threadDigList");                                
                                 });            
                         });  
                  </script>           
							    </div>    
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


</body>
</html>



