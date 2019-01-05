<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<% 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%>

<%
String userId = request.getParameter("userId");
String parentId = request.getParameter("parentId");
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>上传文件</title>
     <%@ include file="../../../common/headerBody.jsp" %>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires" CONTENT="0">   
  </head>
<body>
<html:errors />

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>


<logic:equal name="accountFaceFileForm" property="authenticated" value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="accountFaceFileForm" property="authenticated" value="true">

<logic:notEmpty name="accountFaceFileForm" property="id">     
<img src="<%=request.getContextPath() %>/img/showUserFace.shtml?oid=<bean:write name="accountFaceFileForm" property="userId"/>&id=<bean:write name="accountFaceFileForm" property="id"/>"  border='0' />
</logic:notEmpty>

<!-- This iframe is used as a place for the post to load -->
<iframe id='target_upload' name='target_upload' src='' style='display: none'></iframe>

<p><span align='left' class='status-text' id='updateStatusMsg'></span>
<div id="progressBar" style="display: none;">
            <div id="theMeter">
                <div id="progressBarText"></div>

                <div id="progressBarBox">
                    <div id="progressBarBoxContent"></div>
                </div>
            </div>
</div>


<html:form action="/account/protected/upload/saveUserFaceAction.shtml" enctype="multipart/form-data"
 onsubmit="return uploadValid($F('uploadFile'))"
 target="target_upload">

<input type="hidden" name="action" value="create" />
<input type="hidden" name="id"/>
<input type="hidden" name="userId" value="<%=userId %>" />
<input type="hidden" name="parentId" value="<%=parentId %>" />

附件:<html:file property="theFile" size="30" styleId="uploadFile"/> 
<html:submit property="submit" value="上传"/> <br>


<SCRIPT language = "Javascript">
<!--
function closeThisWindow(){ 
  window.top.killUploadWindow();
}
function uploadValid(field){
	if (field.toLowerCase().indexOf("http://") > -1){
        alert("必须提供你硬盘上文件上传");
         return false;
	}
     if (isImage(field)){
       return startProgress();
    }else{
    	alert("对不起，上传附件文件的类型不在允许的类型之中");
       return false;
   }
}
-->
//-->
</script>

</table>
<br>
<SCRIPT language = "Javascript">



function isImage(field){
    if ((field == null) || (field == "")) {
        return false;  
     }
     <logic:iterate id="imageType" name="accountFaceFileForm" property="imagesTypes" >
     if (field.toLowerCase().indexOf(".<bean:write name="imageType"/>") > -1){
         return true;
     }
     
   </logic:iterate> 
     return false;  
}
</SCRIPT>


1.上传后Ctrl-F5刷新浏览器可见最新图片 <br/>

2.支持类型jpg,gif,png <br/>

3.上传附件最大:100 (K)<!-- 见struts-config-upload.xml中配置  --> <br />

4.如果上传图片的显示区域过大会被服务器压缩 
</div>
</html:form>


</logic:equal>   

<%-- upload progress..not normal now.. --%>
<script src='<html:rewrite page="/common/js/upload.js"/>'> </script>
<script src='<html:rewrite page="/dwr/interface/UploadMonitor.js"/>'> </script>
<script src='<html:rewrite page="/dwr/engine.js"/>'> </script>
<script src='<html:rewrite page="/dwr/util.js"/>'> </script>
       
<script language="javascript">
      function updateStatusMessage(message){
          DWRUtil.setValue('updateStatusMsg', message);
      }
</script>


</body>
</html>