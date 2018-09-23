<%@ page contentType="text/html; charset=UTF-8"%>
<script language="JavaScript" type="text/javascript">
	<!-- 
		function sendAction(url){ 	 		
   			$("smsg_form").action= url;
 			$("smsg_form").submit();
  		}
  
		function delAction(url)
		{
			 if (confirm( 'Delete this message ! \n\nAre you sure ? '))
        		{
              		$("smsg_form").action=url;
              		$("smsg_form").submit();
             		return true;
         	}else{
              return false;
        	}
		}
		function checkPost(theForm) {
    		 	var check = false;
     			 if (($("smsg_form").messageTo.value  != "")
          			&& ($("smsg_form").messageTitle.value  != "")){
          			check = true;
          			return check;
     			 }else{
         			 alert("请输入发送对象和标题！");
          			return check;
      			}
		}
	//-->
	</script>
<table cellpadding="2" cellspacing="0" border="0">
		<tr>
			<td align="right">发往 <br>
			</td>
			<td><html:text property="messageTo" size="12" maxlength="12"
				tabindex="99" styleId="messageTo"></html:text>(用户名，也可在贴中用“@用户名 (空格)”通知它) <br>
			</td>
		</tr>
		<tr>
			<td align="right">标题 <br>
			</td>
			<td><html:text property="messageTitle" size="40" maxlength="75"
				tabindex="100" styleId="messageTitle"></html:text> <br>
			</td>
		</tr>
		<tr>
			<td valign="top" align="right">内容 <br>
			</td>
			<td><html:textarea property="messageBody" cols="60" rows="10"
				tabindex="101" styleId="messageBody"></html:textarea> <br>
			内容最长不超过200个汉字</td>
		</tr>
		<tr>
			<td valign="top" align="right">验证码 <br>
			</td>
			<td><input type="text" name="registerCode" size="10"
				maxlength="50" tabindex="102"> <html:img
				page="/account/registerCodeAction" border="0" /> <br>
			</td>
		</tr>
	</table>