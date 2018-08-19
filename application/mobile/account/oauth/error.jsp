<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>

<bean:define id="title" value=" 错误" />
<%@ include file="../../header.jsp"%>

<div data-role="header">
		<h1><b>注册</b> </h1>
	</div><!-- /header -->

	<div data-role="content">	
<center>
     <br><br>
   操作出现问题
  
  <logic:notEmpty name="errors">
     <bean:write name="errors"/>
  </logic:notEmpty> 
		   

<br>
<br>
<br>
<p>返回<html:link page="/mobile/"> 首页</html:link>
</center>
</div>
<%@include file="../../footer.jsp"%>

