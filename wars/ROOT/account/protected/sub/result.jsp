<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../../../blog/header.jsp" %>


   <div class="mainarea_right"> 
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">
   <logic:messagesNotPresent>
    <logic:empty name="errors">
     <br><br>
    关注设置操作成功！
      
      
    </logic:empty>
  </logic:messagesNotPresent></div>
  <logic:notEmpty name="errors">
     <bean:write name="errors"/>
  </logic:notEmpty> 
		   
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>
              
                  <html:errors/>
                <html:link page="/account/protected/sub/default.jsp">返回</html:link>
                </div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
	  
	  
   </div> 

<%@ include file="../../../blog/footer.jsp" %>

