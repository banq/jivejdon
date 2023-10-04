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

<main>
<div id="page-content" class="single-page container">
  <div class="row">
    <!-- /////////////////左边 -->
    <div id="main-content" class="col-md-8">
      <div class="box">

        <!--  内容-->
        <div id="messageListBody">
   
      
        <!-- 导航区  -->
              <logic:greaterThan name="messageListForm" property="numPages" value="1">
                <ul class="pagination pull-right">
                  有<b><bean:write name="messageListForm" property="numPages"/></b>页 
                  <MultiPagesREST:pager actionFormName="messageListForm" page="" paramId="thread" paramName="forumThread" paramProperty="threadId">
                    <MultiPagesREST:prev name=" 上一页 "/>
                    <MultiPagesREST:index displayCount="6"/>
                    <MultiPagesREST:next name=" 下一页 "/>
                  </MultiPagesREST:pager>
                </ul>
              </logic:greaterThan>
           

          <%@include file="messageListBody.jsp" %>
                  
        </div>

        <div id="pageEnd"></div>
  
        <div id="nextPageContent"></div> 

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
			<div class="widget">
        <div class="wid-vid">
              
                <logic:notEmpty name="forumThread" property="tags">          
                  <div class="widget_tag_cloud">
                   <div class="tagcloud">
                     <logic:iterate id="threadTag" name="forumThread" property="tags"> 
                        <a href="<%=request.getContextPath()%>/tag-<bean:write name="threadTag" property="tagID"/>/" class="tag-cloud-link"><bean:write name="threadTag" property="title"/></a>
                           &nbsp;&nbsp; 
                     </logic:iterate>
                   </div>
                  </div>
                </logic:notEmpty> 
                <logic:empty name="forumThread" property="tags">    
                  <div id="tagHotList"><br><br><br><br><br><br><br><br><br><br></div>   
							      <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/tagHotList', function (xhr) {				
  	                                  document.getElementById("tagHotList").innerHTML = xhr.responseText;
			                     });
							      </script> -->
							     </div>
                </logic:empty>          
       
        </div>				
			</div>

     <div class="widget">
        <div class="wid-vid">
        
                 <logic:notEmpty name="forumThread" property="tags">          
                  <div id="threadTagList"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>             
                   <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/forum/threadTagList.shtml?othread=<bean:write name="forumThread" property="threadId"/>&threadId=<bean:write name="forumThread" property="threadId"/>', function (xhr) {				
  	                                  document.getElementById("threadTagList").innerHTML = xhr.responseText;
			                     });
							     </script> -->
							   </div>	
                 </logic:notEmpty>		
                 <logic:empty name="forumThread" property="tags">    
                  <div id="digList"><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br></div>   
							     <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/threadDigList', function (xhr) {				
  	                                  document.getElementById("digList").innerHTML = xhr.responseText;
			                     });
							      </script> -->
							     </div>
                </logic:empty>   				   
          
        </div>
      </div>     


      <!-- Start Widget -->
     <div class="widget">
        <div class="wid-vid">
          
                <div class="scrolldiv">
							   <div style="width: 300px"> 
<!-- 自适应主广告 -->
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

  <div id="reply" style="display:none">
    <input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
    <input type="hidden" id="replySubject" name="replySubject" value="<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>">
  </div>

</main>

<%@include file="../common/IncludeBottomBody.jsp"%>

 <logic:greaterThan name="messageListForm" property="allCount" value="1">         

<bean:parameter name="thread" id="thread" value=""/>
<bean:define id="pagestart" name="messageListForm" property="start" />
<bean:define id="pagecount" name="messageListForm" property="count" />
<bean:define id="pageallCount" name="messageListForm" property="allCount" />
<%  
    int pageStartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
    int pageCountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
    int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
    int pageNo = (pageAllcountInt / pageCountInt);
    if(pageAllcountInt % pageCountInt !=0){ 
        pageNo = pageNo + 1;
    }    
%>
<script defer>

document.addEventListener("DOMContentLoaded", function(event) { 
  
function scrollLoader(url){
  var start = "<%=pageStartInt+pageCountInt%>";
  var loading = false;
  $(window).scroll(function() {
    var hT = $('#nextPageContent').offset().top,
       hH = $('#nextPageContent').outerHeight(),
       wH = $(window).height(),
       wS = $(this).scrollTop();       
    if (wS > (hT+hH-wH) && !loading){           
         loading = true;          
         if (start <= <%=pageAllcountInt%> ){                  
           surl = (url.indexOf("?")==-1)?(url+"?"):(url+"&");           
           load(surl +'start=' + start +'&count=<%=pageCountInt%>&noheader=on', function (xhr) {
               document.getElementById("nextPageContent").innerHTML = document.getElementById("nextPageContent").innerHTML + xhr.responseText;               
               start = start/1 + <%=pageCountInt%>;                              
               loading = false;
           });          
         }   
    }
   });
}
scrollLoader('/forum/messageListBodyNoheader.shtml?thread=<bean:write name="thread"/>&othread=<bean:write name="forumThread" property="threadId"/>');   

});
</script>   
</logic:greaterThan>
  </body>
  </html>
