<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<table cellpadding="0" cellspacing="0" border="0" width="971" style="text-align: center">
<tr><td>
    <b>
    <a href="https://www.jdon.com/"
    >首页</a>
    &raquo;
    <html:link page="/forum/" title="返回道场"
    >道场</html:link>
    &raquo;
    <html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId">
            <bean:write name="forum" property="name" />
    </html:link>
    </b>
</td></tr></table>