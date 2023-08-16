<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<bean:parameter name="queryType" id="queryType" value=""/>
<bean:parameter name="tagID" id="tagID" value=""/>
<%
String titleStr = (String)request.getAttribute("TITLE");
pageContext.setAttribute("title", titleStr);
%>
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="alternate" type="application/rss+xml" title="Feed订阅" href="/tag-<bean:write name="tagID"/>/rss"/>		 
<main>
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-md-12">
        <div class="box"> 
       <center>
        <logic:notEmpty  name="TITLE">
        <h1 class="tagcloud"><a href="/tag-<bean:write name="tagID"/>/" class="tag-cloud-link"><bean:write  name='TITLE'/></a></h1>
      </logic:notEmpty>
      <div>
        <% 
            if (request.getSession(false) != null){
            %>
                 <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="tagID" /> " target="_blank"  ><i class="fa fa-heart"></i></a>    
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本标签" border="0" /></a>                                                         
            <%
            }
        %>
      
        
       &nbsp;&nbsp;       
		   <a href="/tag-<bean:write name="tagID"/>/rss"><i class="fa fa-feed"></i></a>
       &nbsp;&nbsp;  
       <a href="/query/taggedThreadList.shtml?tagID=<bean:write name="tagID"/>&count=15&r=1">
       <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
       </a>

       </div>

    
		  
      </center>

<% if (request.getParameter("r") == null){ %>  
<ul class="pagination pull-right"> 有<b>
            <bean:write name="threadListForm" property="allCount"/>
            </b>贴
            <MultiPagesREST:pager actionFormName="threadListForm" page="/tags"  paramId="tagID" paramName="tagID" >
              <MultiPagesREST:prev name=" 上一页 " />
              <MultiPagesREST:index displayCount="8" />
              <MultiPagesREST:next  name=" 下一页 " />
            </MultiPagesREST:pager>
</ul>
<% } %>  
<%@ include file="threadList.jsp" %>
<% if (request.getParameter("r") == null){ %>  
<ul class="pagination pull-right"> 有<b>
            <bean:write name="threadListForm" property="allCount"/>
            </b>贴
            <MultiPagesREST:pager actionFormName="threadListForm" page="/tags"  paramId="tagID" paramName="tagID" >
              <MultiPagesREST:prev name=" 上一页 " />
              <MultiPagesREST:index displayCount="8" />
              <MultiPagesREST:next  name=" 下一页 " />
            </MultiPagesREST:pager>
</ul>
<% } %>  

        </div>
      </div>  

  </div>
</div>  
</main>
<aside>
   <div id="tagHotList"></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/tagHotList', function (xhr) {				
  	                                  document.getElementById("tagHotList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
				</div>
</aside>
<%@ include file="../common/IncludeBottomBody.jsp" %> 


</body>
</html>