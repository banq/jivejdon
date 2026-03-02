<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setHeader("X-Robots-Tag", "noindex, nofollow");
    response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

 
<%
    String title = "搜索所有贴 ";
    if (request.getAttribute("query") != null) {
        title += (String) request.getAttribute("query");
    }
    pageContext.setAttribute("title", title);
%>
<%@ include file="../common/IncludeTop.jsp" %>
<meta name="robots" content="noindex">

<div id="page-content" class="single-page container">
		<div class="row">
			<!-- /////////////////左边 -->
			<div id="main-content" class="col-lg-12">
				<div class="box">	

<ul class="nav nav-tabs">
  <li ><a href="<%=request.getContextPath()%>/threads/">最新</a></li>
  <li><a href="<%=request.getContextPath()%>/approval/">新佳</a></li>
  <li><a href="<%=request.getContextPath()%>/threadDigSortedList/">最佳</a></li>	
  <li><a href="<%=request.getContextPath()%>/maxPopThreads/">精华</a></li>
  <li  class="active"><a href="#" ><i class="fa fa-search"></i></a></li>
</ul>


<%@ include file="searchInputView.jsp" %>

<!-- second query result -->
<logic:present name="messageListForm">

    <logic:greaterThan name="messageListForm" property="allCount" value="0">

<div id="main-content" class="col-lg-12">
	<div class="box">


        <div class="tres">
          <MultiPages:pager actionFormName="messageListForm"
                            page="/captcha/search.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上页 "/>

            <MultiPages:next name=" 下页 "/>
          </MultiPages:pager>

        </div>

        <main>
            <ul style="list-style-type:none;padding:0">
            <logic:iterate indexId="i" id="messageSearchSpec"
                           name="messageListForm"
                           property="list">
                <bean:define id="forumMessage" name="messageSearchSpec"
                             property="message"/>
                <bean:define id="forumThread" name="forumMessage"
                             property="forumThread"/>
                <bean:define id="forum" name="forumMessage"
                             property="forum"/>
                <bean:define id="account" name="forumMessage"
                             property="account"/>
                   <li class="row">
                        <div class="col-lg-12">
                            <header>
                            <h3 class="vid-name">
                            <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />.html'
                               target="_blank">
                                <bean:write name="forumThread" property="name"/></a>
                            </h3>    
                            </header>    
                            <div>
                               
                                <div>

                                    <bean:write
                                        name="messageSearchSpec"
                                        property="body"
                                        filter="false"/>
                                    <bean:define id="adIndex"
                                                 name="i"
                                                 toScope="request"/>

                                </div>
                              
                            </div>

                            <p><span class="blackgray"><bean:write
                                    name="forumMessage"
                                    property="modifiedDate3"/></span>


                        </div>
                  
                    </li>


            </logic:iterate>
            </ul>
        </main>    
        
        <div class="tres">
          <MultiPages:pager actionFormName="messageListForm"
                            page="/captcha/search.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上页 "/>

            <MultiPages:next name=" 下页 "/>
          </MultiPages:pager>

        </div>


</div>
</div>
    </logic:greaterThan>
       
</logic:present>
</div>
</div>
</div>
</div>


<%@include file="../common/IncludeBottom.jsp" %>
