<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title" value=" 道场查询"/>
<%@ include file="../common/IncludeTop.jsp" %>

<!-- second query result -->
<logic:present name="messageListForm">
    <logic:greaterThan name="messageListForm" property="allCount" value="0">

        <div class="tres">
            符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴
          <MultiPages:pager actionFormName="messageListForm" page="/message/threadViewQuery.shtml" name="paramMaps">
                <MultiPages:prev name=" 上一页 "/>
                <MultiPages:index displayCount="3"/>
                <MultiPages:next name=" 下一页 "/>
            </MultiPages:pager>
        </div>

        <table class="table table-striped">
            <tbody>
            <logic:iterate indexId="i" id="forumMessage" name="messageListForm" property="list">

                <logic:notEmpty name="forumMessage" property="forumThread">
                    <bean:define id="forumThread" name="forumMessage" property="forumThread"/>
                    <bean:define id="forum" name="forumMessage" property="forum"/>
                    <bean:define id="account" name="forumMessage" property="account"/>
                    <tr>
                        <td valign="top">
                            <logic:notEmpty name="account"
                                            property="uploadFile">
                                <logic:equal name="account"
                                             property="roleName"
                                             value="User">
                                    <img src="/img/account/<bean:write name="account" property="userId"/>"
                                         border="0" width="50" height="50"
                                         class="post_author_pic"/>
                                </logic:equal>
                                <logic:equal name="account"
                                             property="roleName"
                                             value="SinaUser">
                                    <img src="<bean:write name="account" property="uploadFile.description"/>"
                                         border="0" width="50" height="50"
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
                                <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" />.html'
                                   target="_blank">
                                    <bean:write name="forumThread" property="name"/></a>
                                <div id="xsnazzy">
                                    <b class="xtop"><b
                                            class="xb1"></b><b
                                            class="xb2"></b><b
                                            class="xb3"></b><b
                                            class="xb4"></b></b>
                                    <div class="xboxcontent">
                                        <bean:write name="forumMessage" property="messageVO.shortBody[100]"
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

                </logic:notEmpty>
            </logic:iterate>
            </tbody>
        </table>


        <div class="tres">
            符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴
            <a href="JavaScript:void(0);" onmouseover="loadWLJSWithP(this, initTooltipWL)"
               class="tooltip html_tooltip_content_go">Go</a>
          <MultiPages:pager actionFormName="messageListForm" page="/message/threadViewQuery.shtml"
                            name="paramMaps">
                <MultiPages:prev name=" 上一页 "/>
                <MultiPages:index displayCount="3"/>
                <MultiPages:next name=" 下一页 "/>
            </MultiPages:pager>
        </div>


    </logic:greaterThan>
</logic:present>


<%@ include file="searchInputView.jsp" %>


<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp" %>


