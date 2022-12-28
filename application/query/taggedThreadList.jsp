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
		 
	<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-md-12">
        <div class="box"> 
        <!-- 填写 -->
			
       <center>
        <logic:notEmpty  name="TITLE">
        <h3><bean:write  name="TITLE"/></h3>
      </logic:notEmpty>
        <% 
            if (request.getSession(false) != null){
            %>
                 <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="tagID" /> " target="_blank"  rel="nofollow"><i class="fa fa-heart"></i></a>    
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本标签" border="0" /></a>                                                         
            <%
            }
        %>
      
        
       &nbsp;&nbsp;       
		<a href="/tag-<bean:write name="tagID"/>/rss"><i class="fa fa-feed"></i></a>
    <link rel="alternate" type="application/rss+xml" title="Feed订阅" href="/tag-<bean:write name="tagID"/>/rss"/>
    <%--&nbsp;&nbsp;--%>
        <%--<a href="<%=request.getContextPath()%>/tags">更多分类</a>--%>
		  
      </center>

    
<div class="tres"  style="float: right;"> 共有<b>
            <bean:write name="threadListForm" property="allCount"/>
            </b>贴
            <MultiPagesREST:pager actionFormName="threadListForm" page="/tags"  paramId="tagID" paramName="tagID" >
              <MultiPagesREST:prev name=" 上一页 " />
              <MultiPagesREST:index displayCount="3" />
              <MultiPagesREST:next  name=" 下一页 " />
            </MultiPagesREST:pager>
</div>


<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
    <logic:equal name="i" value="3">
        <div class="box">
            <div class="linkblock">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="box">
          <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
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
                </div>
            </div>
        </div>
    </logic:equal>
<%@ include file="threadListCore.jsp" %>
</logic:iterate>

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
	
<%@ include file="../common/IncludeBottom.jsp" %> 
  