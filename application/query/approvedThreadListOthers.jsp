<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">

<bean:define id="title"  value=" 精华帖" />
<%@ include file="../common/IncludeTop.jsp" %>

<script>
function digMessage(id)
{            
	var pars = 'messageId='+id;   
    new Ajax.Updater('digNumber_'+id, getContextPath() +'/query/updateDigCount.shtml', { method: 'get', parameters: pars });
    $('textArea_'+id).update("赞");
    
}

function viewcount(threadId, sId)
{            	
	 var pars = 'thread=' + threadId + "&sId=" + sId;   
   new Ajax.Updater({success: 'viewcount'}, getContextPath() +'/query/viewThread.shtml', { method: 'get', parameters: pars });
}

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

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
<%@ include file="threadListCore.jsp" %>
</logic:iterate>


          <div class="pagination">
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
	

</logic:greaterThan>
</logic:present>



<%@include file="../common/IncludeBottom.jsp"%>


