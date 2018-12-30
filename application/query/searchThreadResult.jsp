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


<script type="text/javascript" src="/common/js/prototype.js"></script>
<script language="javascript" src="/common/js/autocomplete.js"></script>

<script type="text/JavaScript">
    function changeAction(theForm) {

        if (theForm.view[0].checked) {
            theForm.action = '<%=request.getContextPath()%>/message/searchThreadAction.shtml'
        } else if (theForm.view[1].checked) {
            theForm.action = '<%=request.getContextPath()%>/message/searchAction.shtml'

        }
    }

    function loadAcJS(thisId) {
        if (typeof(ac) == 'undefined') {
            $LAB
                .script('/common/js/autocomplete.js')
                .wait(function () {
                    ac(thisId, '<%=request.getContextPath()%>');
                })
        } else
            ac(thisId, '<%=request.getContextPath()%>');
    }

</script>


<logic:notPresent name="query">
    <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table class="table table-striped">
    <tbody>
    <tr>
        <td align="middle">
            <html:form action="/message/searchThreadAction.shtml" method="post"
                       styleClass="search" onsubmit="changeAction(this);">
                <input type="text" name="query"
                       value="<bean:write name="query"/>" id="queryId"
                       onfocus="javascript:loadAcJS(this.id)" size="40"/>
                <html:submit value="道场搜索"/>
                <input type="radio" name="view" checked="checked"/>查询主题(不包括回贴)
                <input type="radio" name="view"/>查询帖

            </html:form>
        </td>
    </tr>
    </tbody>
</table>

<!-- second query result -->
<logic:present name="messageListForm">
    <logic:greaterThan name="messageListForm" property="allCount" value="0">
        <div class="tres">

          <MultiPages:pager actionFormName="messageListForm"
                            page="/message/searchThreadAction.shtml"
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
                        <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>"
                           target="_blank"><b><bean:write name="forumThread"
                                                          property="name"/></b></a>
                        <p>
                        <span class="tpc_content">
                <bean:write name="messageSearchSpec" property="messageVO.body"
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
                            page="/message/searchThreadAction.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上一页 "/>

            <MultiPages:next name=" 下一页 "/>
          </MultiPages:pager>
        </div>


    </logic:greaterThan>
</logic:present>


<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp" %>

