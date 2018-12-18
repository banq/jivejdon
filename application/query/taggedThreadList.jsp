<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<bean:parameter name="queryType" id="queryType" value=""/>
<bean:parameter name="tagID" id="tagID" value=""/>
<logic:present name="threadListForm">
    <logic:greaterThan name="threadListForm" property="allCount" value="0">
    <%
String titleStr = (String)request.getAttribute("TITLE");
pageContext.setAttribute("title", titleStr);
%>
      <%@ include file="../common/IncludeTop.jsp" %>
		 
	<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-md-8">
        <div class="box"> 
        <!-- 填写 -->
			
       <center>
        <logic:notEmpty  name="TITLE">
        <h3>#<bean:write  name="TITLE"/></h3>
      </logic:notEmpty>
        <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="tagID" /> " target="_blank"  rel="nofollow"><i class="fa fa-heart"></i></a>     
    <%--     &nbsp;&nbsp;
		<a href="<%=request.getContextPath()%>/rss/tag/<bean:write name="tagID" />"><i class="fa fa-feed"></i></a>
		--%> 
    &nbsp;&nbsp;
        <a href="<%=request.getContextPath()%>/tags">更多分类</a>
		  
      </center>
     
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
<%@ include file="threadListCore.jsp" %>
</logic:iterate>
	<div>
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
	<div class="tres" > 共有<b>
            <bean:write name="threadListForm" property="allCount"/>
            </b>贴
            <MultiPagesREST:pager actionFormName="threadListForm" page="/tags"  paramId="tagID" paramName="tagID" >
              <MultiPagesREST:prev name=" 上一页 " />
              <MultiPagesREST:index displayCount="3" />
              <MultiPagesREST:next  name=" 下一页 " />
            </MultiPagesREST:pager>
          </div>
        </div>
      </div>  
      <!-- /////////////////右边 -->
       <div id="sidebar" class="col-md-4">
        <!---- Start Widget ---->
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
        <!---- Start Widget ---->
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
	   <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 推荐贴右侧300x600 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:300px;height:600px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3352261515"></ins>
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
    <script>

var initTagsW = function (e){          
 TooltipManager.init('Tags', 
  {url: getContextPath() +'/query/tt.shtml?tablewidth=300&count=20', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:300});   
TooltipManager.showNow(e);   
}

</script>
  
    <%@include file="../common/IncludeBottom.jsp"%>
