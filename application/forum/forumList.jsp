<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value="编程道场Coding Dojos" />
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="alternate" type="application/rss+xml" title="<bean:write name="title" />" href="/rss/messages" />
<meta http-equiv="refresh" content="3600">
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
</script>
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

<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-md-8">
				<div class="box">		
                     <table class="table table-striped">
	 <thead>
		 <tr>
		 <th>道场名</th>
		 <th>主题数</th>
		 <th>更新</th>
		</tr>
	</thead>
	<tbody>
		
	

<logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
        <tr id='<bean:write name="forum" property="forumId" />_<bean:write name="forum" property="modifiedDate" />'>
            <td>
                 <a href="<%=request.getContextPath()%>/forum/<bean:write name="forum" property="forumId" />">                
                       <b><span class="threadTitle"><bean:write name="forum" property="name" /></span></b>
                 </a>
                   <%--<a title="关注本道场" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=0&subscribeId=<bean:write name="forum" property="forumId" />"  rel="nofollow">--%>
                      <%--<img src="/images/user_add.gif" width="18" height="18" alt="关注本道场" border="0" /></a>                                     --%>

                <br>
                <span class="home_content" ><bean:write name="forum" property="description" filter="false"/></span>
            </td>
            <td>
               <span class="home_content" > <bean:write name="forum" property="forumState.threadCount" /> </span>
            </td>
            <td>
       
            <bean:define id="messageForm" name="forum" property="forumState.latestPost" />
  <logic:notEmpty name="messageForm" property="creationDate">
                          <span class="home_content" >
                          <span  class='ForumLastPost ajax_forumId=<bean:write name="forum" property="forumId"/>' >
                          <bean:define id="cdate" name="messageForm" property="modifiedDate"></bean:define>
                           <%String cdateS = (String) pageContext.getAttribute("cdate"); %><%=cdateS.substring(2, 11) %>
                          </span></span> 
  
                           <%-- <br>
                    <span class="home_content" >作者:</span>
    <a href='<%=request.getContextPath()%>/<bean:write name="messageForm" property="forumThread.threadId"/>' >
                    <span  class='Users ajax_userId=<bean:write name="messageForm" property="account.userId"/>' >
                    <bean:write name="messageForm" property="account.username" /></span></a>                       
                         --%>
            </logic:notEmpty>


            </td>
        </tr>

</logic:iterate>

</tbody>
</table>
               </div>	
			</div>
			<!-- /////////////////右边 -->
             <div id="sidebar" class="col-md-4">
				<%-- <!---- Start Widget ---->
				<div class="widget wid-follow">
					<div class="content">
						<ul class="list-inline">
<form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
								<input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
							</form>
						</ul>						
					</div>
				</div> --%>

                 <!---- Start tags ---->
                 <div class="widget wid-post">
                     <div class="info">
                         <jsp:include page="/query/tagHotList.shtml" flush="true"></jsp:include>
                     </div>
                 </div>
			
				<!---- Start Widget ---->
				<div class="widget">
					    <div class="wid-vid">
						   <ul>
								 <div>
                  <jsp:include page="/query/threadNewList.shtml?count=17" flush="true"></jsp:include>
                 </div>    
							</ul>
							</div>
				</div>
		
		</div>
	
	   </div>
</div>

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

<%@include file="../common/IncludeBottom.jsp"%> 


