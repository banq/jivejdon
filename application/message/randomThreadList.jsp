<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="noheader" name="noheader"  value=""/>


<logic:empty name="threadListForm" property="list">
<% 
  response.sendError(204);  
  %>
</logic:empty>

<bean:define id="title" value="随机" />
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<%

int pagestartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
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
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="alternate" type="application/rss+xml" title="<bean:write name="title" /> " href="/rss" /> 

<main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-lg-8">
				<div class="box">	
<ul class="nav nav-tabs">
  <li ><a href="<%=request.getContextPath()%>/threads/">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval/">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/threadDigSortedList/">最佳</a></li>	
  <li><a href="<%=request.getContextPath()%>/maxPopThreads/">精华</a></li>
  <li class="active"><a href="javascript:location.reload()" >
  <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
  </a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" ><i class="fa fa-search"></i></a></li>
</ul>          


<div class="list-group">


 <logic:iterate indexId="i" id="forumThread" name="threadListForm"  property="list">
 
 <logic:equal name="forumThread" property="state.messageCount" value="0">
 
<logic:notEmpty name="forumThread">
     <logic:notEmpty name="forumThread" property="rootMessage">
     <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
     <bean:define id="body" name="forumMessage" property="messageVO.body" />
     

     <logic:equal name="forumMessage" property="digCount" value="0">

     <div class="box">
              <section> 
              <div class="wrap-vid">              
                     <a href="/message/messageDeleAction.shtml?messageId=<bean:write name="forumMessage" property="messageId"/>"  target="_blank">删除</a>
              </div>
              <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"><bean:write name="forumThread" property="name"/></a></h3>
           
              <div class="info">			 
                   <span class="smallgray"><i class="fa fa-calendar"></i>
                     <bean:define id="cdate" name="forumThread" property="creationDate" ></bean:define>
                     <%String cdateS = (String)pageContext.getAttribute("cdate"); %>
                     <time datetime="<%=cdateS.substring(2, 11) %>"><%=cdateS.substring(2, 11) %></time>
                   </span>
                     <logic:notEqual name="forumThread" property="state.messageCount" value="0">
                   <span class="smallgray"><i class="fa fa-comment"></i> <bean:write name="forumThread" property="state.messageCount" />
                           </span>
                      </logic:notEqual>  
                   <span class="smallgray"><i class="fa fa-eye"></i><bean:write name="forumThread" property="viewCount" />
                           </span>
                       <logic:notEqual name="forumMessage" property="digCount" value="0">
                            <span class="smallgray"><i class="fa fa-heart"></i>
                           <bean:write name="forumMessage" property="digCount"/>
                                 </span>
                           </logic:notEqual>     
     
                 <logic:greaterThan name="forumMessage" property="messageVO.bodyLengthK" value="1">
                     <span class="smallgray"><bean:write name="forumMessage" property="messageVO.bodyLengthK"/>K</span>
                 </logic:greaterThan>     
                   
                   <br><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />.             
                 </div>
     
               </section>
     </div>
     
    </logic:equal>    
      </logic:notEmpty>
     </logic:notEmpty>    
     
    </logic:equal>     
 </logic:iterate>

</div>
 

      
	    	         
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
							   
          </div>
        </div>
 
    </div>

</aside>      
	</div>
</div>
</main>
 
<%@ include file="../common/IncludeBottomBody.jsp" %> 
  

</body>
</html>

