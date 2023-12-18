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

String[] imageUrls = (String[])request.getAttribute("Tags_ImageUrls");
int i=0;
%>
<main>
<logic:iterate  id="threadTag" name="tagsListForm"  property="list" offset="<%=offset%>" length="<%=count%>">


 <div class="row">	

 
 <div class="col-lg-12">
 <div class="box">	
  <div class="linkblock">
   <div class="box">
     <div class="row">
    <div style="position: relative;"  class="thumbn col-lg-4">           
       <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" title="<bean:write name="threadTag" property="title" />">    
       <%
       String imgeUrl = imageUrls[i];
       if (imgeUrl==null)
          imgeUrl = "/simgs/thumb2/"+java.util.concurrent.ThreadLocalRandom.current().nextInt(49)+".jpg";

       %>     
        <img id="home-thumbnai" src='<%=imgeUrl%>' border="0" class="thumbnail"  loading="lazy" style="max-width:400px;  width:100%;" onerror="this.src='/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(49)%>.jpg'"/>
       </a>
      <div style="position: absolute;top:0px;right:0px">
       <div class="tagcloud">
        <a href='<%=request.getContextPath() %>/tag-<bean:write name="threadTag" property="tagID"/>/' target="_blank" class="tag-cloud-link">
		    <bean:write name="threadTag" property="title" /></a>
	      <a href="/tag-<bean:write name="threadTag" property="tagID"/>/rss"><i class="fa fa-feed"></i></a>
       </div>
       </div> 
      </div>
	<div id='ajax_<bean:write name="threadTag" property="tagID"/>' class="col">
  <br><br><br><br><br><br>     
  </div>  
  <div class="lazyload" >
	    <!-- 
        <script>
         $('#ajax_<bean:write name="threadTag" property="tagID"/>').load("/query/tt/${threadTag.tagID}");                         
        </script>
        -->
	  
  </div>  
	</div>	
  </div>  
</div>	
</div>  
</div>
 
  </div>


<%i++;%>
</logic:iterate>
</main>
