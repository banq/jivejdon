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

<script type="text/JavaScript">
    function changeAction(theForm) {

        if (theForm.view[0].checked) {
            theForm.action = '<%=request.getContextPath()%>/message/searchThreadAction.shtml'
        } else if (theForm.view[1].checked) {
            theForm.action = '<%=request.getContextPath()%>/message/searchAction.shtml'

        }
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
                       value="<bean:write name="query"/>" id="queryId" size="40"/>
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
                src="//cdn.jdon.com/forum/js/threadList.js"></script>
      <link href="//cdn.jdon.com/common/mListAll.css" rel="stylesheet" type="text/css"/>

        <div class="tres">
          <MultiPages:pager actionFormName="messageListForm"
                            page="/message/searchAction.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上一页 "/>

            <MultiPages:next name=" 下一页 "/>
          </MultiPages:pager>

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
          <MultiPages:pager actionFormName="messageListForm"
                            page="/message/searchAction.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上一页 "/>

            <MultiPages:next name=" 下一页 "/>
          </MultiPages:pager>

        </div>


    </logic:greaterThan>
</logic:present>


<%@include file="../common/IncludeBottom.jsp" %>

