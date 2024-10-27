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
<!DOCTYPE html>
<html lang="zh-CN">
<head>
   <bean:define id="title"  value="教程道场Coding Dojo" />
   <%@include file="../common/IncludeTopHead.jsp"%>
   <meta name="Description" content="软件技术设计与编程道场">
<meta name="Keywords" content="前后端,语言平台,领域驱动设计,领域建模,逻辑方法,SpringBoot和SpringCloud,平台架构,DevOps,Github工具,企业软件,架构设计,大科技、自然科学,产品领域,科普,大语言模型,数据科学,认知偏见">
<link rel="sitemap" type="application/xml" title="Sitemap" href="/sitemap.xml">

<meta http-equiv="refresh" content="3600">
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
                     <table class="table table-striped">
	<tbody>

   

<logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
        <tr>
            <td>
              
                 <h2 class="bige20">
                 <a href="<%=request.getContextPath()%>/forum/<bean:write name="forum" property="forumId" />/" class="hover-preload">                
                       <b><span class="threadTitle"><bean:write name="forum" property="name" /></span></b>
                 </a>
                 </h2>
               
                 <%if (request.getSession(false) != null && request.getUserPrincipal() != null){%>
                      <a title="关注本道场" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=0&subscribeId=<bean:write name="forum" property="forumId" />"  >
                      <img src="/images/user_add.gif" width="18" height="18" alt="关注本道场" border="0" /></a>                                                         
                     <%
                 }
                 %>

                <br>
                <span class="home_content" ><bean:write name="forum" property="description" filter="false"/></span>
                <div id="threadNewList_<bean:write name="forum" property="forumId"/>" class="linkblock">
                </div>  
                  <div class="lazyload" >
                      <!-- 
                    <script>
                       $(document).ready(function() {
                          const request = $.ajax({
                            url: '/query/threadNewList.shtml',
                            data: {
                                forumId: ${forum.forumId},        
                                count: 5  
                            },
                            success: function(data) {
                                $('#threadNewList_<bean:write name="forum" property="forumId"/>').html(data); 
                            },
                        });

                 
                        });
    
                      </script>

                        -->
                    
                  </div>  
         
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
        
	 <!-- /////////////////右边 -->
<aside>      
       <div id="sidebar" class="col-lg-4">
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
        <!---- Start tags ---->
        <div class="widget">
					    <div class="wid-vid">
						
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
<!-- 内容右侧悬浮广告 -->
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


        <!---- Start Widget ---->
       <div class="widget">
           <div class="wid-vid">
							    <div class="scrolldiv">
							     <div style="width: 300px"> 

<!-- 内容右侧悬浮广告 -->
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
          </div>
        </div>
 
    </div>

</aside>  

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



