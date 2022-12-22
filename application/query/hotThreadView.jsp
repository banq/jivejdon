<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title" value=" 道场查询"/>
<%@ include file="../common/IncludeTop.jsp" %>

<bean:parameter name="queryType" id="queryType" value=""/>


<logic:present name="threadListForm">
    <logic:greaterThan name="threadListForm" property="allCount" value="0">

        <div class="tres">
                <%-- request.setAttribute("paramMaps", qForm.getParamMaps());  in ThreadQueryAction --%>
            符合查询主题贴共有<b><bean:write name="threadListForm" property="allCount"/></b>贴
          <MultiPages:pager actionFormName="threadListForm" page="/message/threadViewQuery.shtml"
                            name="paramMaps">
                <MultiPages:prev name=" 上一页 "/>
                <MultiPages:index displayCount="3"/>
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
            <logic:iterate indexId="i" id="forumThread" name="threadListForm" property="list">

                <bean:define id="forumMessage" name="forumThread" property="rootMessage"/>
                <bean:define id="forumThreadDes" name="forumMessage" property="messageVO.shortBody[100]"/>


                <tr bgcolor="#FFFFEC">
                    <td>
                        <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html"
                           target="_blank"><b><bean:write name="forumThread" property="name"/></b>
                        </a>
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
            符合查询主题共有<b><bean:write name="threadListForm" property="allCount"/></b>贴
          <MultiPages:pager actionFormName="threadListForm" page="/message/threadViewQuery.shtml"
                            name="paramMaps">
                <a href="JavaScript:void(0);" class="tooltip html_tooltip_content_go">Go</a>
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


