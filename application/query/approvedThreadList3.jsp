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

int i=0;
%>
<%
String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<%
java.util.List nums = new java.util.ArrayList();
int[] randomArr = new int[5];
int idx = 0;
while (idx < 5) {
    nums.add(idx);
    idx++;
}
java.util.Collections.shuffle(nums);
idx = 0;
while (idx < 5) {
    randomArr[idx] = ((Integer)nums.get(idx)).intValue();
    idx++;
}
int randomIdx = 0;
%>


<logic:iterate  id="threadTag" name="tagsListForm"  property="list" offset="<%=offset%>" length="<%=count%>">


 <div class="row">	

 
 <div class="col-lg-12" style="padding:0px">
 <div class="box">	
  <div class="linkblock">
   <div class="box">
     <div class="row">
     <div class="col-lg-5">
    <div style="position: relative;"  class="zoom-container">           
       <a href='<%=domainUrl%>/tag/<bean:write name="threadTag" property="tagID"/>/'  title="<bean:write name="threadTag" property="title" />">    
        <img id="home-thumbnai" src='https://static.jdon.com/simgs/thumb2/<%=(randomIdx < 5) ? randomArr[randomIdx++] : (int)(Math.random()*5)%>.jpg' border="0" class="img-thumbnail img-responsive" style="height:210px;width:100%"  loading="lazy" />
      </a>
      <div style="position: absolute;top:0px;right:0px">
       <div class="tagcloud">
        <a href='<%=domainUrl%>/tag/<bean:write name="threadTag" property="tagID"/>/'  class="tag-cloud-link">
		    <bean:write name="threadTag" property="title" /></a>
	      
       </div>
       </div> 
      </div>
    </div>
<div class="col-lg-7" id='ajax_<bean:write name="threadTag" property="tagID"/>'>  
  <br><br><br><br><br><br>     
  </div>  
     
	</div>	
  </div>  
</div>	
</div>  
</div>
 
  </div>


<%i++;%>


  <script defer>
          document.addEventListener("DOMContentLoaded", function(event) { 
            $(document).ready(function() {
              $('#ajax_<bean:write name="threadTag" property="tagID"/>').load("/query/tt/<bean:write name="threadTag" property="tagID"/>");   
            });
          });     
        </script>
</logic:iterate>