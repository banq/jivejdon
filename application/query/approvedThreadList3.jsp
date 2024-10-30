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
<script>
  const ids = []; // 初始化一个空数组
</script>

<aside>
<logic:iterate  id="threadTag" name="tagsListForm"  property="list" offset="<%=offset%>" length="<%=count%>">


 <div class="row">	

 
 <div class="col-lg-12">
 <div class="box">	
  <div class="linkblock">
   <div class="box">
     <div class="row">
     <div class="col-lg-4">
    <div style="position: relative;"  class="zoom-container">           
       <a href='<%=request.getContextPath() %>/tag/<bean:write name="threadTag" property="tagID"/>/'  title="<bean:write name="threadTag" property="title" />">    
       <%
       String imgeUrl = imageUrls[i];
       if (imgeUrl==null)
          imgeUrl = "/simgs/thumb2/"+java.util.concurrent.ThreadLocalRandom.current().nextInt(5)+".jpg";

       %>     
        <img id="home-thumbnai" src='<%=imgeUrl%>' border="0" class="img-thumbnail img-responsive" style="height:230px;width:100%"  loading="lazy" onerror="this.src='/simgs/thumb2/<%=java.util.concurrent.ThreadLocalRandom.current().nextInt(5)%>.jpg'"/>
       </a>
      <div style="position: absolute;top:0px;right:0px">
       <div class="tagcloud">
        <a href='<%=request.getContextPath() %>/tag/<bean:write name="threadTag" property="tagID"/>/'  class="tag-cloud-link">
		    <bean:write name="threadTag" property="title" /></a>
	      
       </div>
       </div> 
      </div>
    </div>
<section class="widget" id='ajax_<bean:write name="threadTag" property="tagID"/>'>  
  <br><br><br><br><br><br>     
  </section>  
                 
  <script>
    ids.push(<bean:write name="threadTag" property="tagID"/>);
  </script>
       
    
	</div>	
  </div>  
</div>	
</div>  
</div>
 
  </div>


<%i++;%>
</logic:iterate>
</aside>

<script>
  async function fetchData() {
    for (const id of ids) {
            try {
               const response = await fetch(`/query/tt/\${id}`);
               if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const html = await response.text();
                document.getElementById(`ajax_\${id}`).innerHTML = html;
            } catch (error) {
                console.error('Fetch error:', error);
            }
    }
  }

  fetchData();    

</script>