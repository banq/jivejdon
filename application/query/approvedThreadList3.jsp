<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<logic:iterate indexId="i" id="forumThread" name="threadListForm" property="list" offset="1">
  <%@ include file="threadListCore.jsp" %>
</logic:iterate>


<table cellpadding="3" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="smallgray" align="center">
      <div class="tres">
        <MultiPagesREST:pager actionFormName="threadListForm" page="/approval">
          <MultiPagesREST:prev name=" 上一页 "/>
          <MultiPagesREST:index displayCount="3"/>
          <MultiPagesREST:next name=" 下一页 "/>
        </MultiPagesREST:pager>
      </div>
    </td>
  </tr>
</table>
