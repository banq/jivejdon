<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title" value=" 流贴 "/>
<%@ include file="../common/IncludeTop.jsp" %>
<meta http-equiv="refresh" content="3600">
<script>
    if (top !== self) top.location = self.location;
    contextpath = "<%=request.getContextPath()%>";
</script>
<link href="<html:rewrite page="/common/mListAll.css"/>" rel="stylesheet"
      type="text/css"/>


<div class="box">
            <logic:iterate id="forumMessage" name="messageListForm"
                           property="list" indexId="i">
            <bean:define id="forumThread" name="forumMessage"
                         property="forumThread"/>
            <bean:define id="forum" name="forumMessage" property="forum"/>
            <bean:define id="account" name="forumMessage" property="account"/>
         
                        <div id="info">
                            <bean:write name="forumMessage"
                                                    property="messageVO.subject"/>
                              <span class="blackgray">
                                &nbsp;&nbsp;
                                <bean:write
                                        name="forumMessage"
                                        property="modifiedDate3"/>
                                &nbsp;&nbsp;<bean:write name="account"
                                                        property="username"/>
                              </span>
                              
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

                              

                        </div>
                   
            <p>

                </logic:iterate>
        </div>
    <div class="box">
     <div class="tres">
    <MultiPagesREST:pager actionFormName="messageListForm" page="/forum/messageListAll" >
                        <MultiPagesREST:prev name=" 上页 " />
                        <MultiPagesREST:index displayCount="3" />
                        <MultiPagesREST:next  name=" 下页 " />
     </MultiPagesREST:pager>
          

        </div>
    </div>
    </div>
  
</div>


<%@include file="../common/IncludeBottom.jsp" %>
