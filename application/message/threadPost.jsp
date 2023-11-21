
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
    <div class="col-lg-3">
      <div class="form-group">
        <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_0"  value=''/>
      </div>
    </div>
    <div class="col-lg-3">
      <div class="form-group">
        <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_1"  value=''/>
      </div>
    </div>
    <div class="col-lg-3">
      <div class="form-group">
        <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_2"  value=''/>
      </div>
    </div>
    <div class="col-lg-3">
      <div class="form-group">

        <input class="form-control" type="text" name="tagTitle" size="15" maxlength="25" id="searchV_3"  value=''/>
        <logic:notEmpty name="messageForm" property="forumThread.tags">
          <logic:iterate id="threadTag" name="messageForm" property="forumThread.tags" indexId="i">
            <script>
                document.getElementById('searchV_<bean:write name="i"/>').value = '<bean:write name="threadTag" property="title" />';
            </script>
          </logic:iterate>
        </logic:notEmpty>
        <span id='json_info'></span>


      </div>
    </div>
  </div>
</div>
  

  <div class="row">
	<div class="col-lg-6">       
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
    <div class="col-lg-6">
    </div>
</div>
    

<div class="row">
	<div class="col-lg-12">       
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
     load(getContextPath() +'/forum/forumListJSON.shtml', function(xhr) {
     var dataArray = JSON.parse(xhr.responseText);  
         for (var i in dataArray){  
            var optn = document.createElement('option');
           optn.text = dataArray[i].name;
           optn.value = dataArray[i].forumId;          
           document.getElementById('forumId_select').options.add(optn);
         
        }  
    });

function forwardNewPage(fmainurl, fmainPars, anchor){  
     // infoDiagClose();       
      var url = fmainurl + fmainPars + "#" + anchor;   
     // window.alert("url=" + url);    
      window.location.href =  url;
      
}
});
</script>    
