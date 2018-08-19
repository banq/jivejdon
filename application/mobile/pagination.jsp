<%@ page contentType="text/html; charset=UTF-8" %>
<%--
<bean:define id="pagecount" name="messageListForm" property="count" />
<bean:define id="allCount" name="messageListForm" property="allCount" />
<bean:define id="pageId" name="forumThread" property="threadId" />
<%
String preurl = request.getContextPath() + "/mobile/"+ pageContext.getAttribute("pageId");
%>
 --%>
<%

int pagestartInt = 0;
if (request.getParameter("start") != null){
   pagestartInt =Integer.parseInt(request.getParameter("start"));
}
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();

int currentPageNo = 0;
if (pagecountInt > 0){
	currentPageNo = pagestartInt / pagecountInt;	
}
int allCountInt = ((Integer)pageContext.getAttribute("allCount")).intValue();
int numRepliesStart = allCountInt - 1 * pagecountInt;
int nextPageStart = 0;
if (currentPageNo * pagecountInt < numRepliesStart)
	nextPageStart = (currentPageNo + 1) * pagecountInt;

int prePageNo = 0;
if (currentPageNo > 1)
  prePageNo = (currentPageNo-1)* pagecountInt;

int allPageNo = (int)(allCountInt/pagecountInt);
if (allCountInt % pagecountInt > 0 )  
   allPageNo = allPageNo + 1;

allPageNo = allPageNo -1;
%>

<div  data-position="fixed">
  <div class="ui-grid-a">
    <div class="ui-block-a">
      <% if (currentPageNo == 1) {%>
      <a href="<%=preurl%>" data-role="button" 	 data-ajax="false" >上一页</a>
      <% } %>
      <% if (currentPageNo > 1) {%>
      <a href="<%=preurl%>/<%=prePageNo%>" data-role="button"  data-ajax="false" 	data-icon="arrow-l">上一页</a>
      <% } %>
      <% if (currentPageNo == 0) {%>
      <a href="<%=preurl%>/<%=allPageNo* pagecountInt%>" data-role="button"  data-ajax="false" 	data-icon="arrow-l">末页</a>
      <% } %>
    </div>
    <div class="ui-block-b">
      <% if (currentPageNo * pagecountInt < numRepliesStart) {%>
      <a href="<%=preurl%>/<%=nextPageStart%>" data-role="button"  data-ajax="false" data-icon="arrow-r"	>下一页</a>
      <% } %>
      <% if (currentPageNo * pagecountInt >= numRepliesStart) {%>
      <a href="<%=preurl%>" data-role="button"  data-ajax="false" 	data-icon="arrow-r"	>首页</a>
      <% } %>
    </div>
  </div>
</div>
<%-- 
<script>

<% if (currentPageNo == 1) {%>    
  $(document).bind('swiperight', function () {
	  document.location.href="<%=preurl%>";
   });	       
<% } %>
	<% if (currentPageNo > 1) {%>    
  $(document).bind('swiperight', function () {
      document.location.href="<%=preurl%>/<%=prePageNo%>";
   });	    
<% } %>
<% if (currentPageNo == 0) {%>    
  $(document).bind('swiperight', function () {
      document.location.href="<%=preurl%>/<%=allPageNo* pagecountInt%>";
   });	
<% } %>
 	<% if (currentPageNo * pagecountInt < numRepliesStart) {%>    
   $(document).bind('swipeleft', function () {
      document.location.href="<%=preurl%>/<%=nextPageStart%>";
   });	    
<% } %>
 	<% if (currentPageNo * pagecountInt >= numRepliesStart) {%>    
    $(window).bind('swipeleft', function () {
      document.location.href="<%=preurl%>";
   });	
<% } %>   


</script>
--%>