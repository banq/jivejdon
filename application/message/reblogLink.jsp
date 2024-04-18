<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="threadForm">
<bean:define id="forum" name="threadForm" property="forum"  />
<logic:notEmpty name="forum">
<bean:define id="title" name="forum" property="name" />
<%@ include file="messageHeader.jsp" %>

<bean:define id="forumThread" name="threadForm"/>



    <bean:define id="ForumMessage" name="threadForm" property="rootMessage"  />

<div class="col-lg-offset-4 col-lg-4">



<logic:equal name="threadForm" property="authenticated"
             value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="threadForm" property="authenticated"
             value="true">
<p>

  

  <div style="text-align: center">

    <h3><bean:write name="forumThread" property="name" /></h3>         
<br>    


<html:form action="/message/reblogLink.shtml">
<br>
<html:hidden property="threadId" /> 

<html:hidden property="action" />

<%
int i = 0;
%>

<div class="box">	
  <div class="row">
    <div class="col-sm-3"></div>
    <div class="col-sm-6">
      <div class="form-group">
  
<logic:iterate id="prop" name="propertysForm" property="propertys" >
 <logic:notEmpty name="prop" property="value">
 链接:<input type="text" name='<%= "property[" + i + "].value" %>' value='<bean:write name="prop" property="value" filter="false" />'    size="40"/></td>
  
 <% i++; %>
  </logic:notEmpty>
</logic:iterate>
 
</div>
</div>
<div class="col-sm-3"></div>
</div>
</div>

</div>
  
<div id="div1">


</div>

<script type='text/javascript'>
var w = <%=i %>;

function setValuesss(){
	 var insert = "<table border=0 width=500 ><tr><td><table  border=0 width=100%>  <tr>    <td>链接</td></tr>    <tr >    <td><input type='text' name='property[" 
		+ w + 	"].value' value='' size='40' /></td>  </tr></table></td></tr></table>";
		  var content = document.getElementById("div1").innerHTML;
		content+= insert;
        document.getElementById("div1").innerHTML=content;     
		w++;
}
</script>


<%if (i == 0){%>
	
	<script type='text/javascript'  >
	  setValuesss();
	  setValuesss();
	</script>
<%}%>

<input type="button" value="增加新输入项" onclick="setValuesss()"/>



<br><br>
<input type="submit" value="确认"/>
<a href="<%=request.getContextPath()%>/message/messageListOwner.shtml?thread=<bean:write name="threadForm" property="threadId"/>">返回</a>
</html:form>

</div>
<p><p><p>

</logic:equal>   

<div class="box">	
  <div class="row">
    <div class="col-sm-3"></div>
    <div class="col-sm-6">
      <div class="form-group">
        <input class="form-control" type="text" name="token" value="<bean:write name="forumThread" property="token" />" size="10" maxlength="25" id="inputBox"  value=''/>
    
         
      </div>
    </div>
    <div class="col-sm-3"></div>
  </div>
  </div>
  
</div>


</logic:notEmpty>
</logic:notEmpty>

<div id="resultDiv"></div>


<script>
  document.addEventListener("DOMContentLoaded", function(event) { 
   
  
  $(document).ready(function() {
      // 当输入框内容发生变化时触发
      $('#inputBox').on('input', function() {
          var userInput = $(this).val(); // 获取用户输入的内容
  
          // 发起 AJAX 请求
          $.ajax({
              url: '/message/searchAction.shtml', // 请求的URL
              type: 'GET', // 请求类型
              data: { query:  userInput }, // 发送给服务器的数据，可以根据需要传递其他参数
              success: function(response) { // 请求成功时执行的回调函数
                  // 处理服务器返回的数据
                  $('#resultDiv').html(response);
              },
              error: function(xhr, status, error) { // 请求失败时执行的回调函数
                  // 处理请求失败的情况
                  console.error('AJAX请求失败:', status, error);
              }
          });
      });
  });
  
  
  });
  
  
  </script>    
  

<%@include file="../common/IncludeBottom.jsp"%>


