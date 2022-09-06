<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<bean:parameter id="noheader" name="noheader"  value=""/>
<logic:notEqual name="noheader" value="on">

<bean:parameter name="queryType" id="queryType" value=""/>
<bean:parameter name="tagID" id="tagID" value=""/>
<%
String titleStr = (String)request.getAttribute("TITLE");
pageContext.setAttribute("title", titleStr);
%>
      <%@ include file="../common/IncludeTop.jsp" %>
		 
	<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-md-12">
        <div class="box"> 
        <!-- 填写 -->
			
       <center>
        <logic:notEmpty  name="TITLE">
        <h3>#<bean:write  name="TITLE"/></h3>
      </logic:notEmpty>
        <% 
            if (request.getSession(false) != null){
            %>
                 <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="tagID" /> " target="_blank"  rel="nofollow"><i class="fa fa-heart"></i></a>    
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本标签" border="0" /></a>                                                         
            <%
            }
        %>
      
        
       <%--&nbsp;&nbsp;--%>
		<%--<a href="//www.jdon.com/rss/tag/<bean:write name="tagID" />"><i class="fa fa-feed"></i></a>--%>
    <%--&nbsp;&nbsp;--%>
        <%--<a href="<%=request.getContextPath()%>/tags">更多分类</a>--%>
		  
      </center>

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

  </div>
</div>  
	
<%@ include file="../common/IncludeBottomBody.jsp" %> 
  
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />
<bean:define id="pageallCount" name="threadListForm" property="allCount" />
<%-- <%  
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
scrollLoader('/query/taggedThreadList.shtml?tagID=<bean:write name="tagID" />');   
</script>    --%>
</body>
</html>
</logic:notEqual>    