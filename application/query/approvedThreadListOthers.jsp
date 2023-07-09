<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<bean:define id="title"  value=" 新佳文章 " />
<%@ include file="../common/IncludeTop.jsp" %>
    
<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-md-8">
        <div class="box"> 
<ul class="nav nav-tabs">        
  <li><a href="<%=request.getContextPath()%>/threads">最新</a></li>
  <li class="active"><a href="#">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">精华</a></li>
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" >搜索</a></li>   
  <li style="float: right">
        
   </li> 
</ul>   
<ul class="pagination pull-right">
      <MultiPagesREST:pager actionFormName="threadListForm" page="/approval" >
         <MultiPagesREST:prev name=" 上一页 " />
         <MultiPagesREST:next  name=" 下一页 " />
         </MultiPagesREST:pager>
       
</ul>

<%@ include file="threadList.jsp" %>

<ul class="pagination pull-right">
      <MultiPagesREST:pager actionFormName="threadListForm" page="/approval" >
         <MultiPagesREST:prev name=" 上一页 " />
         <MultiPagesREST:next  name=" 下一页 " />
         </MultiPagesREST:pager>
       
</ul>


        </div>
      </div>  
      <!-- /////////////////右边 -->
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
        <!---- Start tags ---->
        <div class="widget">
					    <div class="wid-vid">
							<ul>
							  <div id="tagHotList"></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/tagHotList', function (xhr) {				
  	                                  document.getElementById("tagHotList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
							  </div>

							</ul>
							</div>
				</div>

        <!---- Start Widget ---->
        <div class="widget wid-post">
          <div class="content">
              <div class="post wrap-vid">
                 <ul>
							    <div id="digList"></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/threadDigList', function (xhr) {				
  	                                  document.getElementById("digList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
							  </div>

							  </ul>
              </div>
          </div>
        </div>


        <!---- Start Widget ---->
        <div class="widget wid-post">
          <div class="content">
              <div class="post wrap-vid">
                 <ul>
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
							  </ul>
              </div>
          </div>
        </div>
 
    </div>

</aside>  
</div>
</div>  	
	

<%@ include file="../common/IncludeBottomBody.jsp" %> 


</body>
</html>
