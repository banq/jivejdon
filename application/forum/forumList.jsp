<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value="编程道场Coding Dojo" />
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="alternate" type="application/rss+xml" title="<bean:write name="title" />" href="/rss" />
<meta http-equiv="refresh" content="3600">
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
</script>

 <main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
           <aside>
           <div id="sidebar" class="col-md-4">
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
						   <ul>
								 <div>
                                   <jsp:include page="/query/threadRandomDigList.shtml?count=15" flush="true"></jsp:include>
                                 </div>    
							</ul>
						</div>
				</div>
		
            </div>		
            </aside>	

           
			<!-- /////////////////右边 -->
            <div id="main-content" class="col-md-8">
				<div class="box">		
                     <table class="table table-striped">
	 <thead>
		 <tr>
		 <th>道场</th>
		 <th>主题</th>
		</tr>
	</thead>
	<tbody>
		
	

<logic:iterate indexId="i"   id="forum" name="forumListForm" property="list" >
        <tr>
            <td>
              
                 <a href="<%=request.getContextPath()%>/forum/<bean:write name="forum" property="forumId" />/">                
                       <b><span class="threadTitle"><bean:write name="forum" property="name" /></span></b>
                 </a>
               
                 <% 
                 if (request.getSession(false) != null){
                     %>
                      <a title="关注本道场" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=0&subscribeId=<bean:write name="forum" property="forumId" />"  >
                      <img src="/images/user_add.gif" width="18" height="18" alt="关注本道场" border="0" /></a>                                                         
                     <%
                 }
                 %>

                <br>
                <span class="home_content" ><bean:write name="forum" property="description" filter="false"/></span>
            </td>
            <td>
               <span class="home_content" > <bean:write name="forum" property="forumState.threadCount" /> </span>
            </td>
        </tr>

</logic:iterate>

       </tbody>
        </table>
    </div>	
    </div>
        
	

	   </div>
</div>
</main>

<div class="row">
    <div class="col-sm-12">
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
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
 </div>
<%@include file="../common/IncludeBottom.jsp"%> 



