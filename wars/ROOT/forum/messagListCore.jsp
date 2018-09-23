<%@ page session="false"  %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter  id="noheaderfooter" name="noheaderfooter" value=""/>
<logic:empty name="noheaderfooter">
  <bean:define id="forumThread" name="messageListForm" property="oneModel" />
  <bean:define id="forum" name="forumThread" property="forum" />
  <bean:define id="title" name="forumThread" property="name" />
  <bean:define id="pagestart" name="messageListForm" property="start" />
  <bean:define id="pagecount" name="messageListForm" property="count" />  
  <%
int pagestartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
int currentPageNo = 1;
if (pagecountInt > 0) 
	currentPageNo = (pagestartInt / pagecountInt) + 1;
String titleStr = (String)pageContext.getAttribute("title");
if (titleStr == null)
	response.sendError(404); 	  
if (currentPageNo > 1)
	titleStr = titleStr + "  - 第"+ currentPageNo + "页";
pageContext.setAttribute("title", titleStr);
pageContext.setAttribute("currentPageNo", currentPageNo);
%>
  <%@ include file="header.jsp" %>  
</logic:empty>	  
 <logic:notEmpty name="noheaderfooter">
    <%
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(0, request, response);
%>
</logic:notEmpty>	  
<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-md-8">
				<div class="box">	
	  
	                 <logic:empty name="forumThread">
      <bean:define id="forumThread" name="messageListForm" property="oneModel" />
      <bean:define id="forum" name="forumThread" property="forum" />
    </logic:empty>    
	  
                      <!--  内容-->
					 <div id="messageListBody" >
                    <logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i">
                        <%@include file="messageListBody.jsp"%>
                     </logic:iterate>
					</div>
						 
		              <!-- 导航区  -->    						
                       <div class="post_pages_end" >
      <div class="table-button-left">        
        <div class="table-button-right">
        <logic:greaterThan name="messageListForm" property="numPages" value="1">                                      
               <div class="tres">
                 有<b><bean:write name="messageListForm" property="numPages"/></b>页 <a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a> 
                <MultiPagesREST:pager actionFormName="messageListForm" page="" paramId="thread" paramName="forumThread" paramProperty="threadId">
                  <MultiPagesREST:prev name=" 上一页 " />
                  <MultiPagesREST:index displayCount="3" />
                  <MultiPagesREST:next  name=" 下一页 " />
                </MultiPagesREST:pager>
              </div>
              </logic:greaterThan>
        </div>
    </div>
</div>
	
			</div>
			   <div class="box">
					<div class="box-header header-natural">
					</div>
					<div class="box-content">
						<div class="row">
						 <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 自动调整尺寸 -->
<ins class="adsbygoogle"
     style="display:block"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="9040920314"
     data-ad-format="auto"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>						
						</div>
					</div>
				</div>
			   <div class="box">
					<div class="box-header header-natural">
					</div>
					<div class="box-content">
						<div class="row">
							<div id="approved"  class="approved" ></div>
						</div>
					</div>
				</div>
			</div>	
			<!-- /////////////////右边 -->
			 <div id="sidebar" class="col-md-4">
				<!-- Start Widget -->
				<div class="widget wid-follow">
					<div class="heading"></div>
					<div class="content">
						<ul class="list-inline">
							<form role="form" class="form-horizontal" method="post" action="/query/searchAction.shtml">
								<input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
							</form>
						</ul>						
					</div>
				</div>
				<!-- Start Widget -->
				<div class="widget wid-post">
					<div class="heading"></div>
					<div class="content">
					    <div class="post wrap-vid">
	<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 页上左336 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:336px;height:280px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="6751585519"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
						</div>
					</div>
				</div>
				<!-- Start Widget -->
				<div class="widget">
					    <div class="wid-vid">
						   <ul>
							<logic:iterate id="threadTag" name="forumThread" property="tags" indexId="tagsi">
								<span id="tagthreads_<bean:write name="threadTag" property="tagID"/>"></span>
                           </logic:iterate>     
							
							</ul>
							</div>
				</div>
				 	<!-- Start Widget -->
			 	<div class="widget">
					    <div class="wid-vid">
						   <ul>                   
							<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 推荐贴右侧300x600 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:300px;height:600px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3352261515"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
							</ul>
							</div>
				</div>
		
		</div>
	</div>
</div>

	  
<logic:empty name="noheaderfooter">
  <div id="reply" style="display:none">

    <input type="hidden" id="replySubject" name="replySubject"  value="<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>"/> 
</div>
      
<%-- include LAB.js --%>   
<%@ include file="../account/loginAJAX.jsp" %>
    
<script> 
$LAB
.script("https://cdn.jdon.com/common/js/prototype.js").wait()
.script("https://cdn.jdon.com/common/messageList4.js").wait()
.wait(function(){
	//below need prototype.js
	 var sId = "";
	 <logic:notEmpty name="forumThread" property="state.lastPost">
	    sId = '<bean:write name="forumThread" property="state.lastPost.modifiedDate2" />';
	 </logic:notEmpty>
	 viewcount('<bean:write name="forumThread" property="threadId" />', sId);
	 //stickyList();
	
	 <logic:notEmpty name="forumThread" property="tags">
	    ajaxcrossdomain();
	 </logic:notEmpty>
	
    <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="tagsi">
        tagthreads(10,160,10,<bean:write name="threadTag" property="tagID"/>);
   </logic:iterate>
	 
	 if (isDisplayNeedLoad('approved')){
	    approveList();
	 }else{ 	 
	   Event.observe(window, 'scroll', function() {
			setTimeout(function(){
			 if (isDisplayNeedLoad('approved')){	
						    approveList();
	         }			
			},1500);
	   });
	 }	
});      
</script> 

<%@include file="footer.jsp"%>
</logic:empty>
