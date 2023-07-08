<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<style>
.widget_tag_cloud {
	color: #333333;
	box-shadow: 0px 0px 3px 0px rgba(0, 0, 0, 0.11);
	padding: 1.875rem 1.563rem 2.5rem;
	margin: 0 0 2.5rem;
}
.tagcloud {
	margin: 0;
	overflow: hidden;
	padding: 0;
}
.tagcloud .tag-cloud-link {
    background-color: #41872d;
    border: 1px solid #e9e9e9;    
    display: inline-block;
    padding: 0.188rem 1.25rem;
    margin: 0 0.188rem 0.625rem;
    color:#FFF;
    text-decoration: none;
}

</style>
<div class="widget_tag_cloud">
<div class="tagcloud">

<logic:iterate indexId="i"   id="ThreadTag" name="tagsHotListForm" property="list" length='<%=coutlength%>' >
  <section>
    <a href="<%=request.getContextPath()%>/tag-<bean:write name="ThreadTag" property="tagID"/>/" class="tag-cloud-link"><bean:write name="ThreadTag" property="title"/></a>
                     &nbsp;&nbsp; 
  </section>                   
</logic:iterate>

</div>
</div>

