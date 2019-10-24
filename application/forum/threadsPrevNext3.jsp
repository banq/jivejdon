<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<table cellpadding="0" cellspacing="2" border="0" width="100%" align="center">
<tr>
    <td width="1%" nowrap valign="top">
  &nbsp;
    </td>
    <td width="98%" align="center">

<table cellpadding="0" cellspacing="2" border="0" width="100%">

<%
java.util.ListIterator iter3 = (java.util.ListIterator)request.getAttribute("ThreadsPrevNext2");
if (iter3.hasPrevious()){
%>
    <tr>
	<td>上一篇:
        <%
   com.jdon.jivejdon.domain.model.ForumThread forumThreadPrev = (com.jdon.jivejdon.domain.model.ForumThread)iter3.previous();
   request.setAttribute("forumThreadPrev", forumThreadPrev);
  // advance the iterator pointer back to the original index   
   iter3.next();
  %>
   <logic:notEmpty name="forumThreadPrev" >
     <a href="<%=request.getContextPath()%>/<bean:write name="forumThreadPrev" property="threadId"/>"
      title="<bean:write name="forumThreadPrev" property="name"/>" class="forum">
        <bean:write name="forumThreadPrev" property="shortname"/>
        </a>
   </logic:notEmpty>          
    </td>      
<%} %>

 <%    
if (iter3.hasNext())
   iter3.next();
if (iter3.hasNext()){
%>
	<td align="right">下一篇:
   <%
   com.jdon.jivejdon.domain.model.ForumThread forumThreadNext = (com.jdon.jivejdon.domain.model.ForumThread)iter3.next();
   request.setAttribute("forumThreadNext", forumThreadNext);
  %>
  <logic:notEmpty name="forumThreadNext" >
     <a href="<%=request.getContextPath()%>/<bean:write name="forumThreadNext" property="threadId"/>"
      title="<bean:write name="forumThreadNext" property="name"/>" class="forum">
        <bean:write name="forumThreadNext" property="shortname"/>
    </a>       
  </logic:notEmpty>     
    </td>  
</tr>
<%  } %>    

</table>

    </td>
    <td width="1%" nowrap valign="top">
    &nbsp;
    </td>
</tr>
</table>