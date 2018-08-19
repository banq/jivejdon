<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%
//http://detectmobilebrowser.com/
String ua=request.getHeader("User-Agent").toLowerCase();
if(ua.matches(".*(android|avantgo|blackberry|blazer|compal|elaine|fennec|hiptop|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile|o2|opera mini|palm( os)?|plucker|pocket|pre\\/|psp|smartphone|symbian|treo|up\\.(browser|link)|vodafone|wap|windows ce; (iemobile|ppc)|xiino).*")||ua.substring(0,4).matches("1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-")){
	response.sendRedirect(request.getContextPath() +"/mobile/login.jsp");
	return;
}
%>

<bean:define id="title"  value=" 用户登录" />
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="content-language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>
<bean:write name="title" />
</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
<link rel="stylesheet" href="/common/portlet.css" type="text/css">

   

<script>
try{
  if (window.parent.callLogin){
     window.parent.callLogin();  
  } 
}catch(e){
   try{
     if (window.top.callLogin){
        window.top.callLogin();  
     }
    }catch(e){}
}

</script>


<table border="0" cellpadding="0" cellspacing="0" width="450" align='center'>
<tr>
<td>
<div class="portlet-container">
<div class="portlet-header-bar">
<div class="portlet-title">
<div style="position: relative; font-size: smaller; padding-top: 5px;"><b>&nbsp;用户登陆&nbsp;</b></div>
</div>
<div class="portlet-small-icon-bar">
</div>
</div><!-- end portlet-header-bar -->
<div class="portlet-top-decoration"><div><div></div></div></div>
<div class="portlet-box">
<div class="portlet-minimum-height">
<div id="p_p_body_2" >
<div id="p_p_content_2_" style="margin-top: 0; margin-bottom: 0;">

<form method="POST" action="<%=request.getContextPath()%>/jasslogin"  onsubmit="return Juge(this);"> 

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
<td align="center">

           <table border="0" cellpadding="0" cellspacing="2">
  <tr>
    <td> 用户 </td>
    <td width="10">&nbsp;</td>
    <td><input type="text" name="j_username" size="20" tabindex="1">
    </td>
    <td width="10">&nbsp;</td>
    <td><table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>自动登陆 </td>
        <td align="right"><input type="checkbox" name="rememberMe"  checked="checked">
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>密码 </td>
    <td width="10">&nbsp;</td>
    <td><input type="password" name="j_password" size="20" tabindex="2">
    </td>
    <td width="10">&nbsp;</td>
    <td><input type="submit" value=" 登陆 " tabindex="3" >
    </td>
  </tr>
  <tr>
    <td align="center" colspan="5">
	<a href="<%=request.getContextPath()%>/account/newAccountForm.shtml"  target="_blank" >
                          新用户注册
                    </a>
                    <a href="<%=request.getContextPath()%>/account/forgetPasswd.jsp" target="_blank">
                          忘记密码?
                    </a>
	</td>	
  </tr>
  <tr>
    <td align="center" colspan="5">
    <p>
      <a href="<%=request.getContextPath()%>/account/oauth/sinaCallAction.shtml"  target="_blank" >
                          <img src='/images/sina.png' width="16" height="16" alt="登录" border="0" />新浪微博
                    </a>
    <a href="<%=request.getContextPath()%>/account/oauth/tecentCallAction.shtml" target="_blank">
                          <img src='/images/qq.gif' width="16" height="16" alt="登录" border="0" />腾讯微博
                    </a>

	</td>	
  </tr>
  
</table>

  

</form>
<p></p>


</td>
</tr>
</table>
</div>
</div>
</div>
</div><!-- end portlet-box -->
<div class="portlet-bottom-decoration-2"><div><div></div></div></div>
</div><!-- End portlet-container -->
</td></tr></table>

<script>


function Juge(theForm)
{  
 if (theForm.j_username.value == "")
  {
     alert("请输入用户名！");
     theForm.j_username.focus();
     return (false);
  }
  if (theForm.j_password.value == "")
  {
     alert("请输入密码！");
     theForm.j_password.focus();
     return (false);
  }
  
}


</script>    

</body>
</html>