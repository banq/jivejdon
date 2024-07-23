<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>


<%
    String title = "搜索主题贴 ";
    if (request.getAttribute("query") != null) {
        title += (String) request.getAttribute("query");
    }
    pageContext.setAttribute("title", title);
%>
<%@ include file="../common/IncludeTop.jsp" %>






<logic:notPresent name="query">
    <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table class="table table-striped">
    <tbody>
    <tr>
        <td align="middle">
          <html:form action="/query/search.shtml" method="post"
                     styleClass="search" >
                <input type="text" name="query"
                       value="<bean:write name="query"/>" id="queryId" size="40"/>
                <html:submit value="道场搜索"/>

            </html:form>
        </td>
    </tr>
    </tbody>
</table>

<table class="table table-striped">
    <tbody>
<tr><td align="middle">
    <form action="https://www.baidu.com/s" class="search" method="get">
        <input type=text name=wd  value="<bean:write name="query"/>  site:jdon.com" size="40">
        <input type="submit" value="百度本道场"> 
        </form>
</td></tr>
    </tbody>
</table>
<!-- second query result -->
<logic:present name="messageListForm">
    <logic:greaterThan name="messageListForm" property="allCount" value="0">
        <div class="tres">

          <MultiPages:pager actionFormName="messageListForm"
                            page="/query/searchThreadAction.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上页 "/>

            <MultiPages:next name=" 下页 "/>
          </MultiPages:pager>
          
        </div>
        <ul class="box" style="list-style-type:none;padding:0">
            <logic:iterate indexId="i" id="messageSearchSpec"
                           name="messageListForm" property="list">

                <bean:define id="forumMessage"
                             name="messageSearchSpec"
                             property="message"/>
                <bean:define id="forumThread" name="forumMessage"
                             property="forumThread"/>

               <%@ include file="./others/threadListCore.jsp" %> 
            </logic:iterate>

        </ul>   

        <div class="tres">

          <MultiPages:pager actionFormName="messageListForm"
                            page="/query/searchThreadAction.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上页 "/>

            <MultiPages:next name=" 下页 "/>
          </MultiPages:pager>
        </div>


    </logic:greaterThan>
</logic:present>


<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp" %>

