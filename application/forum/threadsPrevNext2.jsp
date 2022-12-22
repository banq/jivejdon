<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<table cellpadding="0" cellspacing="2" border="0" width="971" align="center">
<tr>
    <td width="1%" nowrap valign="top">
   
<%
java.util.ListIterator iter2 = (java.util.ListIterator)request.getAttribute("ThreadsPrevNext2");
if (iter2.hasPrevious()){
   com.jdon.jivejdon.domain.model.ForumThread forumThreadPrev = (com.jdon.jivejdon.domain.model.ForumThread)iter2.previous();
   request.setAttribute("forumThreadPrev", forumThreadPrev);
  // advance the iterator pointer back to the original index   
   iter2.next();
  %>
  <logic:notEmpty name="forumThreadPrev" >
  
     <a href="<%=request.getContextPath()%>/<bean:write name="forumThreadPrev" property="threadId"/>.html"
      title="<bean:write name="forumThreadPrev" property="name"/>" class="forum">
    <html:img page="/images/prev.gif" width="10" height="10" hspace="2" altKey="forumThreadPrev.name" />     
     <span class="smallgray">上一主题</span>
     </span>          
    </a>
    
   </logic:notEmpty>    
<%} else {
 %>&nbsp;<%
} %>

    </td>
    <td width="98%" align="center">

<table cellpadding="0" cellspacing="2" border="0">
<tr>
    <logic:notEmpty name="forumThread">
	<td nowrap>
        &nbsp;
        <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>.html">
                     <html:img page="/images/back_to.gif" width="12" height="12" alt="Go back to the topic " border="0"/><span class="smallgray">返回本主题</span>
        </a>
    </td>
    </logic:notEmpty>	
	<td nowrap>
        &nbsp;
        <a href="<%=request.getContextPath()%>/<bean:write name="forum" property="forumId" />" title="返回主题列表">        
                      <html:img page="/images/back_to.gif" width="12" height="12" alt="Go back to the topic listing" border="0"/><span class="smallgray">返回主题列表</span>
        </a>
    </td>

    <td nowrap>&nbsp;&nbsp;</td>
	<td><a href="#top">
     	<html:img page="/images/up_to.gif" width="12" height="12" alt="返回页首" border="0"/><span class="smallgray">返回页首</span></a>
    </td>
</tr>
</table>

    </td>
    <td width="1%" nowrap valign="top">
<%    
if (iter2.hasNext())
    iter2.next();
if (iter2.hasNext()){
   com.jdon.jivejdon.domain.model.ForumThread forumThreadNext = (com.jdon.jivejdon.domain.model.ForumThread)iter2.next();
   request.setAttribute("forumThreadNext", forumThreadNext);
  %>
  <logic:notEmpty name="forumThreadNext" >
     <a href="<%=request.getContextPath()%>/<bean:write name="forumThreadNext" property="threadId"/>.html"
      title="<bean:write name="forumThreadNext" property="name"/>" class="forum">
    <html:img page="/images/next.gif" width="10" height="10" hspace="2" altKey="forumThreadNext.name"/>
     <span class="smallgray">下一主题</span>
     </span>
    </a>
  </logic:notEmpty>  
<%  } else { %>
    &nbsp;
<%  } %>    

    </td>
</tr>
</table>

<%-- reset for threadPrevNext3.jsp --%>
<%

if (iter2.hasPrevious()){
    iter2.previous();
    if (iter2.hasPrevious()){
       iter2.previous();
    }
}

%>