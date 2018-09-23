<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title" value=" 评论列表 "/>
<%@ include file="../common/IncludeTop.jsp" %>

<script>
    if (top !== self) top.location = self.location;
    contextpath = "<%=request.getContextPath()%>";
</script>
<script language="javascript"
        src="<html:rewrite page="/forum/js/threadList.js"/>"></script>
<link href="<html:rewrite page="/forum/css/mListAll_css.jsp"/>" rel="stylesheet"
      type="text/css"/>

<table cellpadding="3" cellspacing="0" border="0" width="1036" align="center">
    <tr>
        <td>
            <div class="tres">
                <MultiPagesREST:pager actionFormName="messageListForm"
                                      page="/messages">
                    <MultiPagesREST:prev name=" 上一页 "/>
                    <MultiPagesREST:index displayCount="3"/>
                    <MultiPagesREST:next name=" 下一页 "/>
                </MultiPagesREST:pager>

            </div>

        </td>
        <td align="right">
            &nbsp;<a href="#post"><img src="/images/newtopic.gif" width="113"
                                       height="20" border="0"/></a>
        </td>
    </tr>
</table>

<div style="width:1036px; margin:0 auto;">
    <div style="width:700px;background-color:#FFFFFF;margin-left: auto;margin-right: 20px; float: left;">

        <div class="messageListAll">
            <logic:iterate id="forumMessage" name="messageListForm"
                           property="list" indexId="i">
            <bean:define id="forumThread" name="forumMessage"
                         property="forumThread"/>
            <bean:define id="forum" name="forumMessage" property="forum"/>
            <bean:define id="account" name="forumMessage" property="account"/>
            <table>
                <tr>
                    <td valign="top">
                        <logic:notEmpty name="account" property="uploadFile">
                            <logic:equal name="account" property="roleName"
                                         value="User">
                                <img src="/img/account/<bean:write name="account" property="userId"/>"
                                     border='0' width="50" height="50"
                                     class="post_author_pic"/>
                            </logic:equal>
                            <logic:equal name="account" property="roleName"
                                         value="SinaUser">
                                <img src="<bean:write name="account" property="uploadFile.description"/>"
                                     border='0' width="50" height="50"
                                     class="post_author_pic"/>
                            </logic:equal>
                            <logic:equal name="account" property="roleName"
                                         value="TecentUser">
                                <img src="<bean:write name="account" property="uploadFile.description"/>"
                                     border='0' width="50" height="50"
                                     class="post_author_pic"/>
                            </logic:equal>

                        </logic:notEmpty>

                        <logic:empty name="account" property="uploadFile">
                            <img src="/images/nouserface_1.gif" width="50"
                                 height="50" border="0">
                        </logic:empty>

                    </td>
                    <td>
                        <div id="info">
                            <b>
                                &nbsp;&nbsp;<bean:write name="account"
                                                        property="username"/>
                            </b>
                            <logic:equal name="forumMessage" property="root"
                                         value="true">
                            <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" />'
                               target="_blank">
                                </logic:equal>
                                <logic:equal name="forumMessage" property="root"
                                             value="false">
                                <a href='<%=request.getContextPath()%>/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'
                                   target="_blank" rel="nofollow">
                                    </logic:equal>
                                    原帖</a>
                                <div id="xsnazzy">
                                    <b class="xtop"><b class="xb1"></b><b
                                            class="xb2"></b><b
                                            class="xb3"></b><b class="xb4"></b></b>
                                    <div class="xboxcontent">
                                        <bean:write name="forumMessage"
                                                    property="messageVO.body"
                                                    filter="false"/>
                                        <bean:define id="adIndex" name="i"
                                                     toScope="request"/>

                                    </div>
                                    <b class="xbottom"><b class="xb4"></b><b
                                            class="xb3"></b><b
                                            class="xb2"></b><b class="xb1"></b></b>
                                </div>

                                <p><span class="blackgray"><bean:write
                                        name="forumMessage"
                                        property="modifiedDate3"/></span>

                        </div>
                    </td>
                </tr>
            </table>
            <p>

                </logic:iterate>
        </div>

    </div>
    <div style=" width:300px; float: left;">
        <p></p>
        <div class="frame-yy">
            <script async
                    src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
            <!-- 推荐贴右侧300x600 -->
            <ins class="adsbygoogle"
                 style="display:inline-block;width:300px;height:600px"
                 data-ad-client="ca-pub-7573657117119544"
                 data-ad-slot="3352261515"></ins>
            <script>
                (adsbygoogle = window.adsbygoogle || []).push({});
            </script>
        </div>
        <div class="gen-2"></div>
        <p><br/></p>
        <div class="frame-yy">
        </div>

        <p><br/></p>
    </div>
</div>


<table bgcolor="#cccccc" cellpadding="1" cellspacing="0" border="0" width="1036"
       align="center">
    <tr>
        <td>
            <table bgcolor="#FFFFCC" cellpadding="3" cellspacing="0" border="0"
                   width="100%">
                <tr>
                    <td>

                    </td>
                    <td align="right">
                        <div class="tres">
                            <MultiPagesREST:pager
                                    actionFormName="messageListForm"
                                    page="/messages">
                                <MultiPagesREST:prev name=" 上一页 "/>
                                <MultiPagesREST:index displayCount="3"/>
                                <MultiPagesREST:next name=" 下一页 "/>
                            </MultiPagesREST:pager>

                        </div>

                    </td>

                </tr>
            </table>
        </td>
    </tr>
</table>


<%@include file="../common/IncludeBottom.jsp" %>
