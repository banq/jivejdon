<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>

<bean:define id="title" value=" 错误" />
<%@ include file="../../common/IncludeTop.jsp"%>
<center>
     <br><br>
   操作出现问题
  
  <logic:notEmpty name="errors">
     <bean:write name="errors"/>
  </logic:notEmpty> 
		   

<br>
<br>
<br>
<p>返回<html:link page="/"> 首页</html:link>
</center>
<%@include file="../../common/IncludeBottom.jsp"%>

