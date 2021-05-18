<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:define id="forumMessage" name="threadForm" property="rootMessage"  />
<bean:define id="forumThread" name="forumMessage" property="forumThread" />
<%@ include file="../query/threadListCore.jsp" %>