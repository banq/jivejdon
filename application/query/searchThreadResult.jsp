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
<meta name="robots" content="noindex,nofollow">






<logic:notPresent name="query">
    <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table class="table table-striped">
    <tbody>
    <tr>
        <td align="middle">
          <html:form action="/query/searchAction.shtml" method="post"
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
    <form action="https://www.baidu.com/baidu" class="search">
        <input type=text name=word  value="<bean:write name="query"/>" size="40">
        <input type="submit" value="百度本道场">
        <input name=tn type=hidden value="bds">
        <input name=cl type=hidden value="3">
        <input name=ct type=hidden value="2097152">
        <input name=si type=hidden value="www.jdon.com">
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
            <MultiPages:prev name=" 上一页 "/>

            <MultiPages:next name=" 下一页 "/>
          </MultiPages:pager>
          
        </div>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>主题</th>
                <th>作者</th>
                <th>发布时间</th>
            </tr>
            </thead>
            <tbody>
            <logic:iterate indexId="i" id="messageSearchSpec"
                           name="messageListForm" property="list">

                <bean:define id="forumMessage"
                             name="messageSearchSpec"
                             property="message"/>
                <bean:define id="forumThread" name="forumMessage"
                             property="forumThread"/>

                <tr bgcolor="#FFFFEC">
                    <td>
                        <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"
                           target="_blank"><b><bean:write name="forumThread"
                                                          property="name"/></b></a>
                        <p>
                        <span class="article">
                <bean:write name="messageSearchSpec" property="body"
                            filter="false"/>
             </span>
                    </td>
                    <td><bean:define id="rootMessage" name="forumThread"
                                     property="rootMessage"></bean:define>
                        <logic:notEmpty name="rootMessage" property="account">
                          
                                <bean:write name="rootMessage"
                                            property="account.username"/>
                          
                        </logic:notEmpty>
                    </td>
                    <td><bean:write name="forumThread"
                                    property="rootMessage.creationDateForDay"/></td>

                </tr>
            </logic:iterate>

            </tbody>
        </table>


        <div class="tres">

          <MultiPages:pager actionFormName="messageListForm"
                            page="/query/searchThreadAction.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上一页 "/>

            <MultiPages:next name=" 下一页 "/>
          </MultiPages:pager>
        </div>


    </logic:greaterThan>
</logic:present>


<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp" %>

