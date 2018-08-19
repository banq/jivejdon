<%@ page contentType="text/html; charset=UTF-8"%>
<script language="JavaScript" type="text/javascript">
	<!-- 
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
	//-->
	</script>
	
    	
	<table cellpadding="2" cellspacing="0" border="0">	    
		<tr >
			<td align="right">
				来自
				<br>
			</td>
			<td>
				<html:text property="messageFrom" size="40" maxlength="75"
					tabindex="0" readonly="true" styleId="messageFrom" ></html:text>
				<br>
			</td>
		</tr>
		<tr>
			<td align="right">
				标题
				<br>
			</td>
			<td>
				<html:text property="messageTitle" size="40" maxlength="75"
					tabindex="1" readonly="true" styleId="messageTitle" ></html:text>
				<br>
			</td>
		</tr>
		<tr>
			<td valign="top" align="right">
				内容
				<br>
			</td>
			<td>
				<html:textarea property="filterMessageBody" cols="65" rows="12"
					tabindex="2" readonly="true" styleId="filterMessageBody"></html:textarea>
				<br>
			</td>
		</tr>
	</table>