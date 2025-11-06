
<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:parameter id="forumId" name="forumId" value="" />
    
<div class="box">	
<div class="comment">

<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>
    
<html:form action="/message/postSaveAction.sthml" method="post" target="target_new" styleId="messageNew"  onsubmit="return checkPost(this);" >

  <jsp:include page="messageFormBody.jsp" flush="true">
    <jsp:param name="reply" value="false"/>
  </jsp:include>


  <div class="row">
    <div class="col-sm-3">
      <div class="form-group">
        <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_0"  value=''/>
      </div>
    </div>
    <div class="col-sm-3">
      <div class="form-group">
        <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_1"  value=''/>
      </div>
    </div>
    <div class="col-sm-3">
      <div class="form-group">
        <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_2"  value=''/>
      </div>
    </div>
    <div class="col-sm-3">
      <div class="form-group">

        <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_3"  value=''/>
        <logic:present name="messageForm" property="forumThread.tags">
          <logic:iterate id="threadTag" name="messageForm" property="forumThread.tags" indexId="i">
            <script>
                document.getElementById('searchV_<bean:write name="i"/>').value = '<bean:write name="threadTag" property="title" />';
            </script>
          </logic:iterate>
        </logic:present>
        <span id='json_info'></span>


      </div>
    </div>
  </div>
</div>
  

  <div class="row">
	<div class="col-sm-6">       
        <div class="form-group">            
<logic:notEmpty name="forumId">
  <input type="hidden" name="forum.forumId" value="<bean:write name="forumId"/>" id="forumId_select"/>
</logic:notEmpty>
<logic:empty name="forumId">
       <select class="form-control" name="forum.forumId" id="forumId_select" ></select>   
</logic:empty>
        

<html:hidden property="messageId" />
<html:hidden property="action" value="create"/>
        </div>
    </div>
    <div class="col-sm-6">
    </div>
</div>

<div class="row">
  <div class="col-sm-12">
    <div class="form-group">
      <input class="form-control" type="text" name="token" size="50" maxlength="50" id="inputBox"  value=''/>
      <div id="resultDiv"></div>
       
    </div>
  </div>
</div>

<div class="row">
	<div class="col-sm-12">       
        <div class="form-group"> 
              <button type="submit" class="btn btn-4 btn-block" name="formButton" id="formSubmitButton" >发布</button>
        </div>
      </div>
</div>
</html:form>


  </div>
  <div id="textassit2" style="display:none"></div>
</div>  
    

<script>
document.addEventListener("DOMContentLoaded", function(event) { 
     load(getContextPath() +'/message/forumListJSON.shtml', function(xhr) {
     var dataArray = JSON.parse(xhr.responseText);  
         for (var i in dataArray){  
            var optn = document.createElement('option');
           optn.text = dataArray[i].name;
           optn.value = dataArray[i].forumId;          
           document.getElementById('forumId_select').options.add(optn);
         
        }  
const select = document.getElementById("forumId_select");
if (select.options.length > 0) {
    select.selectedIndex = select.options.length - 1; // 选中最后一个选项
}
    });


function forwardNewPage(fmainurl, fmainPars, anchor){  
     // infoDiagClose();       
      var url = fmainurl + fmainPars + "#" + anchor;   
     // window.alert("url=" + url);    
      window.location.href =  url;
      
}


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
