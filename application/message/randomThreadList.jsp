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
<div id="responseDiv"></div>
<main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-lg-8">
				<div class="box">	
        
        

<div class="list-group">


 <logic:iterate indexId="i" id="forumThread" name="threadListForm"  property="list">
 

  <logic:empty name="forumThread" property="tags">      

<logic:notEmpty name="forumThread">
     <logic:notEmpty name="forumThread" property="rootMessage">
     <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
     <bean:define id="body" name="forumMessage" property="messageVO.body" />
     

     <logic:equal name="forumMessage" property="digCount" value="0">
      <logic:notEqual name="forumMessage" property="account.username" value="banq">
     

     <div class="box">
              <section> 
              <div class="wrap-vid">              
                     <a href="/message/postSaveAction.shtml?method=delete&messageId=<bean:write name="forumMessage" property="messageId"/>"  target="_blank" class="delajax">删除</a>
                     <a href="/message/tag/thread.shtml?action=edit&threadId=<bean:write name="forumThread" property="threadId"/>"  target="_blank" class="editajax">编辑</a>

              </div>

             
        
                     

              
              
              <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html" target="_blank"><bean:write name="forumThread" property="name"/></a></h3>
           
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
                   
                   <br><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[500]" />.             
                 </div>
     
               </section>
     </div>
     
     </logic:notEqual>
    </logic:equal>    
      </logic:notEmpty>
     </logic:notEmpty>    
     
  </logic:empty>     
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


<script>
  document.addEventListener("DOMContentLoaded", function(event) { 
  
    $(document).ready(function(){
  // 监听链接的点击事件
  $('a.delajax').click(function(event){
    // 阻止链接的默认行为
    event.preventDefault();

    // 获取链接的href属性值，即API端点
    var apiUrl = $(this).attr('href');

    // 发起GET请求
    $.get(apiUrl, function(response) {
      // 将响应写入到div层
      $('#responseDiv').html(response);
    }).fail(function(xhr, status, error) {
      // 处理错误情况
      console.error(error);
    });
  });
});

$(document).ready(function(){
  // 监听链接的点击事件
  $('a.editajax').click(function(event){
    // 阻止链接的默认行为
    event.preventDefault();

    // 获取链接的href属性值，即API端点
    var apiUrl = $(this).attr('href');

    // 发起GET请求
    $.get(apiUrl, function(response) {
      // 将响应写入到div层
      $('#responseDiv').html(response);
    }).fail(function(xhr, status, error) {
      // 处理错误情况
      console.error(error);
    });
  });
});




  });
  
  
  </script>    

</body>
</html>

