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
  response.setHeader("X-Robots-Tag", "noindex");  // 防止被搜索引擎索引
     response.setStatus(HttpServletResponse.SC_GONE);  // 使用 410 表示该页面已被永久删除
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
 

<main>
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-lg-8">
				<div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1)">	
        
        

<div class="list-group">

  <% int size=0;%>
  
 <logic:iterate indexId="i" id="forumThread" name="threadListForm"  property="list">
 
    
  <logic:empty name="forumThread" property="tags">      

<logic:notEmpty name="forumThread">
     <logic:notEmpty name="forumThread" property="rootMessage">
     <bean:define id="forumMessage" name="forumThread" property="rootMessage" />
     <bean:define id="body" name="forumMessage" property="messageVO.body" />
     

     <logic:equal name="forumMessage" property="digCount" value="0">
      <logic:notEqual name="forumMessage" property="account.username" value="banq">
     
      <% size++;%>
     <div class="box">
              <div class="wrap-vid">              
                <h3 class="vid-name">
                  <a href="https://www.jdon.com/message/tag/thread.shtml?action=edit&threadId=<bean:write name="forumThread" property="threadId"/>"  target="_blank" >编辑</a>
                  
                  <span class="pull-right"> 
                  <a href="https://www.jdon.com/message/postSaveAction.shtml?method=delete&messageId=<bean:write name="forumMessage" property="messageId"/>"  target="_blank" class="delajax">删除</a></h3>
                  </span>
              </div>

             
              <div class="info">			 
        
                     <logic:greaterThan name="forumThread" property="state.messageCount" value="5">
                   <span  class="pull-right"><i class="fa fa-comment"></i> <b><bean:write name="forumThread" property="state.messageCount" /></b>
                           </span>
                      </logic:greaterThan>  
                   <span >
                    <logic:greaterThan name="forumThread" property="viewCount" value="1000">
                      
                       <i class="fa fa-eye"></i><b  class="pull-right"><bean:write name="forumThread" property="viewCount" /></b>
                      </logic:greaterThan>     
                           </span>
                       <logic:greaterThan name="forumMessage" property="digCount" value="0">
                            <span  class="pull-right"><i class="fa fa-heart"></i>
                              <b><bean:write name="forumMessage" property="digCount"/></b>
                                 </span>
                           </logic:greaterThan>     
     
                 <logic:greaterThan name="forumMessage" property="messageVO.bodyLengthK" value="1">
                     <span ><bean:write name="forumMessage" property="messageVO.bodyLengthK"/>K</span>
                 </logic:greaterThan>     
                 <h3 class="vid-name"><a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/><bean:write name="forumThread" property="pinyinToken" />.html" target="_blank"><bean:write name="forumThread" property="name"/></a></h3>
           
     
                      
                 </div>

         
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
       <div id="sidebar" class="col-lg-4"><div class="box" style="border-radius: 12px; box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1); background-color: white; overflow: hidden; padding-left: 0; padding-right: 0">
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

</div></aside>      
	</div>
</div>
</main>

<%
if (size ==0)  {
%>
      <script>location.reload();</script>

<%}%>

<div id="responseDiv"></div>
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



  });
  
  
  </script>    

</body>
</html>

