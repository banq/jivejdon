<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(30 * 60, request, response);
	
%>
<logic:iterate indexId="i" id="forumThread" name="threadListForm" property="list" offset="1">
  <logic:equal name="i" value="3">
    <div class="box">
      <div class="linkblock" itemscope itemtype="http://schema.org/BlogPosting">
        <div class="row">
          <div class="col-sm-12">
            <div class="box">
              <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
              <ins class="adsbygoogle"
                   style="display:block"
                   data-ad-format="fluid"
                   data-ad-layout-key="-e3+4a-2h-5p+v6"
                   data-ad-client="ca-pub-7573657117119544"
                   data-ad-slot="4250528285"></ins>
              <script>
                  (adsbygoogle = window.adsbygoogle || []).push({});
              </script>
            </div>
          </div>
        </div>
      </div>
    </div>
  </logic:equal>
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
