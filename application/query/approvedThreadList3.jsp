<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page isELIgnored="false" %>
<%
String offset = "0";
if (request.getParameter("offset")!=null){
   offset = request.getParameter("offset");
}
String count = "5";
if (request.getParameter("count")!=null){
   count = request.getParameter("count");
}
%>
<main>
<logic:iterate indexId="i" id="threadTag" name="tagsListForm"  property="list" offset="<%=offset%>" length="<%=count%>">

<%
  if(i % 2==0){ 
 %>
 <div class="row">	
 <%}%>

 
 <div class="col-md-6">
 <div class="box">	
  <div class="linkblock">
 
    <div style="position: relative;">           
       <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" title="<bean:write name="threadTag" property="title" />">         
        <img id="home-thumbnai" src="/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(49)%>.jpg" border="0" class="thumbnail" style="width: 100%" loading="lazy"/>                  
       </a>
      <div style="position: absolute;bottom: 0px;">
       <div class="tagcloud">
        <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" class="tag-cloud-link">
		    <bean:write name="threadTag" property="title" /></a>
	      <a href="/tag-<bean:write name="threadTag" property="tagID"/>/rss"><i class="fa fa-feed"></i></a>
       </div>
       </div> 
      </div>
	<div id='ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
  <br><br><br><br><br><br>     
  </div>  
  <div class="lazyload" >
	    <!-- 
        <script>
         load('/query/tt/${threadTag.tagID}', function(xhr) {
  	       document.getElementById('ajax_tagID=<bean:write name="threadTag" property="tagID"/>').innerHTML = xhr.responseText;
         });
        </script>
        -->
	  
  </div>  
	</div>	
</div>  
</div>
 
<% i = i+1;%>
<%
  if(i % 2==0){ 
 %>
  </div>
 <%}%>


</logic:iterate>
</main>
