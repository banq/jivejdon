<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="title"  value=" 注册资料成功" />
<%@ include file="../common/IncludeTop.jsp" %>


<div class="col-md-offset-4 col-md-4">
<logic:messagesNotPresent>
    注册资料操作成功  
    <p>
  <br>如果你是第一次注册新用户，为防止垃圾广告，新用户
  <br>24小时内只能发一次主题贴，带来不便请谅解。    
</logic:messagesNotPresent>
<p>
<html:link page="/" target="_top"><b>按这里回首页</b></html:link>
</div>

<%@include file="../common/IncludeBottom.jsp"%>

