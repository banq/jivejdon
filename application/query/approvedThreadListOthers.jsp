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
  <li><a href="<%=request.getContextPath()%>/query/threadViewQuery.shtml" rel="nofollow">搜索</a></li>  
          
          <div class="pagination" style="float: right;">
<MultiPagesREST:pager actionFormName="threadListForm" page="/approval" >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager>
          </div>
</ul>   
            <div class="box">
                <div class="linkblock">
                    <div class="row">
                        <div class="col-sm-12">
                        </div>
                    </div>
                </div>
            </div>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
    <logic:equal name="i" value="3">
        <div class="box">
            <div class="linkblock" itemscope itemtype="http://schema.org/BlogPosting">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="box">
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </logic:equal>    
<%@ include file="threadListCore.jsp" %>
</logic:iterate>
  <div id="nextPageContent"></div>

          <div class="pagination" style="float: right;">
            <MultiPagesREST:pager actionFormName="threadListForm" page="/approval">
              <MultiPagesREST:prev name=" 上一页 "/>
              <MultiPagesREST:next name=" 下一页 "/>
            </MultiPagesREST:pager>
          </div>


        </div>
      </div>  
      <!-- /////////////////右边 -->
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
							  	  load('https://cdn.jdon.com/query/tagHotList', function (xhr) {				
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
							  	  load('https://cdn.jdon.com/query/threadDigList', function (xhr) {				
  	                                  document.getElementById("digList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
							  </div>

							  </ul>
              </div>
          </div>
        </div>
        <!---- Start Widget ---->
        <div class="widget">
              <div class="wid-vid">
                 <ul>
                  <div id="newList"></div>   
							    <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/query/threadNewList.shtml?count=15', function (xhr) {				
  	                                  document.getElementById("newList").innerHTML = xhr.responseText;
			                     });
							  </script> -->
							  </div>							
							</ul>                  

              </div>
        </div>

           <div class="widget">
               <div class="wid-vid">
							<ul>
							    <div id="tagcloud"></div>   
								 <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('https://cdn.jdon.com/tags/tagcloud', function (xhr) {				
  	                                  document.getElementById("tagcloud").innerHTML = xhr.responseText;
			                     });
							     </script> -->
							    </div>
							</ul>
                   
               </div>
           </div>
    
    </div>
  </div>
</div>  	
	

<%@ include file="../common/IncludeBottom.jsp" %> 

<script src="https://cdn.jdon.com/common/js/jquery.lazyload-any.js"></script>
<script>       
    $('.lazyload').lazyload();
</script>

