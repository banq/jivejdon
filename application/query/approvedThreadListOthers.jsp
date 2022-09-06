<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">

<bean:define id="title"  value=" 推荐文章 " />
<%@ include file="../common/IncludeTop.jsp" %>
    
<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-md-8">
        <div class="box"> 
<ul class="nav nav-tabs">        
  <li><a href="<%=request.getContextPath()%>/threads">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/forum/threadDigSortedList">最佳</a></li>            
  <li><a href="<%=request.getContextPath()%>/forum/maxPopThreads">最热</a></li>
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
</logic:notEqual>   
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
<logic:notEqual name="noheader" value="on">       
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
        <div class="widget wid-post">
            <div class="content">
                <div class="wrap-vid">
                </div>
            </div>
        </div>        
        <!---- Start tags ---->
        <div class="widget wid-post">
             <div class="info">
                <jsp:include page="/query/tagHotList.shtml" flush="true"></jsp:include>
             </div>
        </div>

        <!---- Start Widget ---->
        <div class="widget wid-post">
          <div class="content">
              <div class="post wrap-vid">
                  
              </div>
          </div>
        </div>
        <!---- Start Widget ---->
        <div class="widget">
              <div class="wid-vid">
                  

              </div>
        </div>

           <div class="widget">
               <div class="wid-vid">
                   
               </div>
           </div>
    
    </div>
  </div>
</div>  	
	

<%@ include file="../common/IncludeBottomBody.jsp" %> 
  
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="pageallCount" name="threadListForm" property="allCount" />
<%--
    int pageStartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
    int pageCountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
    int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
    int pageNo = (pageAllcountInt / pageCountInt);
    if(pageAllcountInt % pageCountInt !=0){ 
        pageNo = pageNo + 1;
    }    

<script>
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
scrollLoader('/query/approvedListOther.shtml');   
</script>   
--%>
</body>
</html>
</logic:notEqual>    


