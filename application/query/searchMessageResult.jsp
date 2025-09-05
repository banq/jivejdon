<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setHeader("X-Robots-Tag", "noindex, nofollow");
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
  <li><a href="<%=request.getContextPath()%>/random/threadRandomList.shtml" rel="nofollow">
  <svg stroke="currentColor" fill="currentColor" stroke-width="0" viewBox="0 0 512 512" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z"></path></svg>
  </a></li>
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
