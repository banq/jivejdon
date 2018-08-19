<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%
    String title = "搜索所有贴 ";
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
            theForm.action = '<%=request.getContextPath()%>/query/searchThreadAction.shtml'
        } else if (theForm.view[1].checked) {
            theForm.action = '<%=request.getContextPath()%>/query/searchAction.shtml'

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
            <html:form action="/query/searchThreadAction.shtml" method="post"
                       styleClass="search" onsubmit="changeAction(this);">
                <input type="text" name="query"
                       value="<bean:write name="query"/>" id="queryId"
                       onfocus="javascript:loadAcJS(this.id)" size="40"/>
                <html:submit value="道场搜索"/>
                <input type="radio" name="view"/>查询主题(不包括回贴)
                <input type="radio" name="view" checked="checked"/>查询帖

            </html:form>
        </td>
    </tr>
    </tbody>
</table>


<!-- second query result -->
<logic:present name="messageListForm">
    <logic:greaterThan name="messageListForm" property="allCount" value="0">

        <script>
            if (top !== self) top.location = self.location;
            contextpath = "<%=request.getContextPath()%>";
        </script>
        <script language="javascript"
                src="<html:rewrite page="/forum/js/threadList.js"/>"></script>
        <link href="<html:rewrite page="/forum/css/mListAll_css.jsp"/>"
              rel="stylesheet"
              type="text/css"/>

        <div class="tres">
            <MultiPagesREST:pager actionFormName="messageListForm"
                                  page="/messages">
                <MultiPagesREST:prev name=" 上一页 "/>
                <MultiPagesREST:index displayCount="3"/>
                <MultiPagesREST:next name=" 下一页 "/>
            </MultiPagesREST:pager>

        </div>

        <table class="table table-striped">
            <tbody>
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
                <tr>
                    <td valign="top">
                        <logic:notEmpty name="account"
                                        property="uploadFile">
                            <logic:equal name="account"
                                         property="roleName"
                                         value="User">
                                <img src="/img/account/<bean:write name="account" property="userId"/>"
                                     border='0' width="50" height="50"
                                     class="post_author_pic"/>
                            </logic:equal>
                            <logic:equal name="account"
                                         property="roleName"
                                         value="SinaUser">
                                <img src="<bean:write name="account" property="uploadFile.description"/>"
                                     border='0' width="50" height="50"
                                     class="post_author_pic"/>
                            </logic:equal>
                            <logic:equal name="account"
                                         property="roleName"
                                         value="TecentUser">
                                <img src="<bean:write name="account" property="uploadFile.description"/>"
                                     border='0' width="50" height="50"
                                     class="post_author_pic"/>
                            </logic:equal>

                        </logic:notEmpty>

                        <logic:empty name="account"
                                     property="uploadFile">
                            <img src="/images/nouserface_1.gif"
                                 width="50"
                                 height="50" border="0">
                        </logic:empty>

                    </td>
                    <td>
                        <div class="box col-md-12">
                            <b>
                                &nbsp;&nbsp;<bean:write name="account"
                                                        property="username"/>
                            </b>
                            <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" />'
                               target="_blank">
                                <bean:write name="forumThread" property="name"/></a>
                            <div id="xsnazzy">
                                <b class="xtop"><b
                                        class="xb1"></b><b
                                        class="xb2"></b><b
                                        class="xb3"></b><b
                                        class="xb4"></b></b>
                                <div class="xboxcontent">

                                    <bean:write
                                            name="messageSearchSpec"
                                            property="messageVO.body"
                                            filter="false"/>
                                    <bean:define id="adIndex"
                                                 name="i"
                                                 toScope="request"/>

                                </div>
                                <b class="xbottom"><b
                                        class="xb4"></b><b
                                        class="xb3"></b><b
                                        class="xb2"></b><b
                                        class="xb1"></b></b>
                            </div>

                            <p><span class="blackgray"><bean:write
                                    name="forumMessage"
                                    property="modifiedDate3"/></span>

                        </div>
                    </td>
                </tr>


            </logic:iterate>

            </tbody>
        </table>

        <div class="tres">
            <MultiPagesREST:pager
                    actionFormName="messageListForm"
                    page="/messages">
                <MultiPagesREST:prev name=" 上一页 "/>
                <MultiPagesREST:index displayCount="3"/>
                <MultiPagesREST:next name=" 下一页 "/>
            </MultiPagesREST:pager>

        </div>


    </logic:greaterThan>
</logic:present>


<%@include file="../common/IncludeBottom.jsp" %>

