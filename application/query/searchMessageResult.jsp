<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>


<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script> 
<%
    String title = "搜索所有贴 ";
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
         <form action="https://www.baidu.com/baidu">
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

        <script>
            if (top !== self) top.location = self.location;
            contextpath = "<%=request.getContextPath()%>";
        </script>
        <script language="javascript"
                src="/forum/js/threadList.js"></script>
      <link href="/common/mListAll.css" rel="stylesheet" type="text/css"/>

        <div class="tres">
          <MultiPages:pager actionFormName="messageListForm"
                            page="/query/searchAction.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上一页 "/>

            <MultiPages:next name=" 下一页 "/>
          </MultiPages:pager>

        </div>

        <main>
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
                            
                            <section>

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

                                    <bean:write
                                        name="messageSearchSpec"
                                        property="body"
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

                            </section>
                           

                        </div>
                    </td>
                </tr>


            </logic:iterate>
             
            </tbody>
        </table>
        </main>    
        
        <div class="tres">
          <MultiPages:pager actionFormName="messageListForm"
                            page="/query/searchAction.shtml"
                            paramId="query" paramName="query">
            <MultiPages:prev name=" 上一页 "/>

            <MultiPages:next name=" 下一页 "/>
          </MultiPages:pager>

        </div>


    </logic:greaterThan>
       
</logic:present>


<%@include file="../common/IncludeBottom.jsp" %>

