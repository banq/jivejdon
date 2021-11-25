<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">

<bean:define id="title"  value=" 精华帖" />
<%@ include file="../common/IncludeTop.jsp" %>
    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
    <script>
        (adsbygoogle = window.adsbygoogle || []).push({
            google_ad_client: "ca-pub-7573657117119544",
            enable_page_level_ads: true
        });
    </script>
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-32868073-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'UA-32868073-1');
    </script>
    
<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-md-8">
        <div class="box"> 
        <!-- 填写 -->
			
<center> <h3>推荐热点精华</h3> </center>

          <div class="pagination">
<MultiPagesREST:pager actionFormName="threadListForm" page="/approval" >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager>
          </div>

            <div class="box">
                <div class="linkblock">
                    <div class="row">
                        <div class="col-sm-12">
                            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <ins class="adsbygoogle"
                                 style="display:block; text-align:center;"
                                 data-ad-layout="in-article"
                                 data-ad-format="fluid"
                                 data-ad-client="ca-pub-7573657117119544"
                                 data-ad-slot="6913243852"></ins>
                            <script>
                                (adsbygoogle = window.adsbygoogle || []).push({});
                            </script>
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
                            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                            <ins class="adsbygoogle"
                                 style="display:block"
                                 data-ad-format="fluid"
                                 data-ad-layout-key="-e3+4a-2h-5p+v6"
                                 data-ad-client="ca-pub-7573657117119544"
                                 data-ad-slot="4250528285"></ins>
                            <script>
                                (adsbygoogle = window.adsbygoogle || []).push({});
                            </script>
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
			      <div class="thumbn"><img src="//static.jdon.com/simgs/forum/ddd-book.png" class="thumbnail" loading="lazy"></div> 
						<p><br>本站原创<br><a href="/54881" target="_blank">《复杂软件设计之道：领域驱动设计全面解析与实战》</a></p>
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
                  <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                  <!-- 页面右侧上部336x280 20-06-18 -->
                  <ins class="adsbygoogle"
                       style="display:block"
                       data-ad-client="ca-pub-7573657117119544"
                       data-ad-slot="6751585519"
                       data-ad-format="auto"
                       data-full-width-responsive="true"></ins>
                  <script>
                      (adsbygoogle = window.adsbygoogle || []).push({});
                  </script>
              </div>
          </div>
        </div>
        <!---- Start Widget ---->
        <div class="widget">
              <div class="wid-vid">
                  <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                  <ins class="adsbygoogle"
                       style="display:block"
                       data-ad-format="autorelaxed"
                       data-ad-client="ca-pub-7573657117119544"
                       data-ad-slot="7669317912"></ins>
                  <script>
                      (adsbygoogle = window.adsbygoogle || []).push({});
                  </script>

              </div>
        </div>

           <div class="widget">
               <div class="wid-vid">
                   <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                   <!-- 右侧中部300x600 20-06-18 -->
                   <ins class="adsbygoogle"
                        style="display:block"
                        data-ad-client="ca-pub-7573657117119544"
                        data-ad-slot="3352261515"
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


