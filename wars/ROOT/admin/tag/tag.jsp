<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<bean:define id="title"  value=" 标签编辑" />
<%@ include file="../header.jsp" %>


<html:form action="/admin/tag/tagSaveAction.shtml" method="post">
<html:hidden name="tagForm"  property="action"/>
<html:hidden name="tagForm"  property="tagID"/>
ID: <bean:write  name="tagForm" property="tagID" />
<br>
Title: <html:text name="tagForm"  property="title"></html:text>
<br>

<html:submit>确定</html:submit>
<br>
 本标签主题数：<bean:write  name="tagForm" property="assonum" />
 <br>
 <html:link page="/query/taggedThreadList.shtml" paramId="tagID" paramName="tagForm" paramProperty="tagID" target="_blank">
             本标签主题列表</html:link>
             
             
</html:form>
<br><br><br>
 <html:link page="/admin/tag/tagSaveAction.shtml?action=delete" paramId="tagID" paramName="tagForm" paramProperty="tagID" >
             删除本标签</html:link>

<%@include file="../footer.jsp"%>