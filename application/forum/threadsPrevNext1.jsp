<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<script>
  var prevThreadUrl;
  var nextThreadUrl;
</script>
<table cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr><td colspan="3"><html:img page="/images/blank.gif" width="1" height="5" border="0"/></td></tr>
<tr>
    <td width="1%" nowrap>

<logic:empty name="ThreadsPrevNext" >
   ERROR:  ThreadsPrevNext is null! check your ThreadPrevNexListAction!
</logic:empty>

<%
java.util.ListIterator iter = (java.util.ListIterator)request.getAttribute("ThreadsPrevNext");
if (iter.hasPrevious()){
   com.jdon.jivejdon.domain.model.ForumThread forumThreadPrev = (com.jdon.jivejdon.domain.model.ForumThread)iter.previous();
   request.setAttribute("forumThreadPrev", forumThreadPrev);
  // advance the iterator pointer back to the original index   
   iter.next();
  %>
   <logic:notEmpty name="forumThreadPrev" >
     <script>
        prevThreadUrl = '<%=request.getContextPath()%>/<bean:write name="forumThreadPrev" property="threadId"/>.html';
     </script>
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
        <bean:define id="forumThread" name="messageListForm" property="oneModel" />
       
<table><tr><td>       
<div class="box1"> 
<a title="网上收藏本主题" href="JavaScript:void(0);" onclick="loadWLJS(mark)" >&nbsp;&nbsp;</a>
</div>
<div class="box2"">
<a title="手机条码扫描浏览本页" href="JavaScript:void(0);" onclick='loadWLJS(qtCode)'  >&nbsp;&nbsp;</a>
</div>
<div class="box3">
<a title="关注本主题" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=1&subscribeId=<bean:write name="forumThread" property="threadId" />"  rel="nofollow">&nbsp;&nbsp;</a>
</div>
<div class="box4">
<a title="加入本帖到收藏夹" href="JavaScript:void(0);" onclick="addfavorite('<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>')" >&nbsp;&nbsp;</a>
</div>
</td></tr></table>       
       
    </td>
    <td width="1%" nowrap>
<%
if (iter.hasNext())
   iter.next();
if (iter.hasNext()){
   com.jdon.jivejdon.domain.model.ForumThread forumThreadNext = (com.jdon.jivejdon.domain.model.ForumThread)iter.next();
   request.setAttribute("forumThreadNext", forumThreadNext);
  %>
    <logic:notEmpty name="forumThreadNext" >
     <script>
        nextThreadUrl = '<%=request.getContextPath()%>/<bean:write name="forumThreadNext" property="threadId"/>.html';
     </script>
    
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
