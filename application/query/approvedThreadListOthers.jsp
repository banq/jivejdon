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
</ul>   

<%@ include file="threadList.jsp" %>

<div id="nextPageContent"></div>  


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
							    <div id="digList" class="scrolldiv"></div>   
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

    
    </div>
  </div>
</div>  	
	

<%@ include file="../common/IncludeBottomBody.jsp" %> 



<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="pageallCount" name="threadListForm" property="allCount" />
<%  
    int pageStartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
    int pageCountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
    int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
    int pageNo = (pageAllcountInt / pageCountInt);
    if(pageAllcountInt % pageCountInt !=0){ 
        pageNo = pageNo + 1;
    }    
%>
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
scrollLoader('/query/approvedListOtherNoheader.shtml');   
</script>   


<script src="https://static.jdon.com/common/js/jquery.lazyload-any.js"></script>
<script>       
    $('.lazyload').lazyload();
</script>
<script type="text/javascript"> 
$(function() { 
    var elm = $('.scrolldiv'); 
    var startPos = $(elm).offset().top; 
    if (window.matchMedia('(min-width: 992px)').matches) { 
    $.event.add(window, "scroll", function() { 
        var p = $(window).scrollTop(); 
        $(elm).css('position',((p) > startPos) ? 'fixed' : 'static'); 
        $(elm).css('top',((p) > startPos) ? '20px' : ''); 
    }); 
    };
}); 
</script>

</body>
</html>
