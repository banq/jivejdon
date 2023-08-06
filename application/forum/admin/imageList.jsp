<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
		<title>My JSP 'images.jsp' starting page</title>
        <link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" /> 
		<script type="text/JavaScript">
			function delConfirm(){
  				if (confirm( '删除该图片 ! \n\n 你肯定吗?  '))
  				{
    				document.forms[0].method.value="delete";
    				document.forms[0].submit();
    				return true;
  				}else{
    				return false;
  				}
			}
			function ReSizeImg(cName,w,h){
   				 var reImgs = document.getElementsByTagName("img");
    			for (j=0;j<reImgs.length;j++){
        			if (reImgs[j].className==cName && (reImgs[j].height>h || reImgs[j].width>w)) {
            			if (reImgs[j].height==reImgs[j].width) {
                			reImgs[j].height=h;reImgs[j].width=w;
            			} else if (reImgs[j].height>reImgs[j].width) {
                			reImgs[j].height=h;
            			} else if (reImgs[j].height<reImgs[j].width){
                			reImgs[j].width=w;
            			}
        			}
    			}
			}
		</script>
		
	</head>

	<body>
		上传文件管理
		<br>
		<table width="100%" align="center">
        <tr>

		<% int count = 1; %>
			<logic:iterate indexId="i" id="image" name="imageListForm"
				property="list">
                  <td>
				
					<table  width="110">
						<tr>
							<td align="center">
								<bean:write name="image" property="name" />
							</td>
						</tr>
						<tr>
							<td align="center">
									<img
										src="<%=request.getContextPath()%>/img/uploadShowAction.shtml?id=<bean:write name="image" property="imageId" />&oid=<bean:write name="image" property="oid" />"
										class="product_img" onload='ReSizeImg("product_img",200,180);'  >
							</td>
						</tr>
						<tr>
							<td align="center">
								<html:link page="/forum/admin/imageSaveAction.shtml?action=edit"
									paramId="imageId" paramName="image" paramProperty="imageId">
									编辑
								</html:link>
								|
								<html:link
									page="/forum/admin/imageSaveAction.shtml?action=delete"
									paramId="imageId" paramName="image" paramProperty="imageId"
									onclick="return delConfirm()">
									删除
								</html:link>
							</td>
						</tr>
					</table>
				 </td>
					<%
				if ((count = count % 3) == 0)
					out.print("</tr><tr>");
				count = count + 1;
				
				%>
			
			</logic:iterate>
			</tr></table> 
		<MultiPages:pager actionFormName="imageListForm"
			page="/forum/admin/imageAction.shtml">
			<MultiPages:prev>前页</MultiPages:prev>
			<MultiPages:index></MultiPages:index>
			<MultiPages:next>后页</MultiPages:next>
		</MultiPages:pager>
	</body>
</html>
