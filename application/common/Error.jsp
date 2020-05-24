<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="content-language" content="zh-CN" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="robots" content="noindex">
<title>
出错
</title>
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />
</head>
<body>

    <H3>Something happened...</H3>
    <B>可能发生错误，请将本页面地址拷贝告诉管理员！</B>
    <p>返回<html:link page="/" > 首页</html:link>
    <script>
    try{
      if (window.top.setInfoConten)
         window.top.setInfoConten('ERROR: 可能发生错误，请将本页面地址拷贝告诉管理员 ');
      }catch(ex){}
   </script>       
    
 <!-- banner  -->   
<table  border="0" align="center">
  <tr>
    <td><table align='center'>
      <tr>
        <td align="center" >
		<a href="http://www.jdon.com/jdonframework/">Powered by Jdon Framework</a> </td>
        </tr>
    </table></td>
  </tr>
</table>
</p>

</body>
</html>