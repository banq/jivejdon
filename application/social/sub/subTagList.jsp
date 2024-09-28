<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<logic:iterate id="subscription" name="subscriptionListForm" property="list"  length="5">
	<bean:define id="subscribed" name="subscription" property="subscribed"/>	
	<a href="<%=request.getContextPath()%>/tag/<bean:write name="subscribed" property="subscribeId"/>/" 
              target="_blank" class="post-tag">		
		<bean:write name="subscribed" property="name" />
	</a>
		<span class="blackgray">
	(<bean:write name="subscription" property="creationDate" />)
		</span>
		&nbsp;&nbsp;&nbsp;
</logic:iterate>
