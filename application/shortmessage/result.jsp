<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:define id="title" value="系统消息" />
<link rel="stylesheet" href="<html:rewrite page="/shortmessage/shortmsg_css.jsp"/>"
	type="text/css">
<center>
 <html:errors />
<logic:present name="errors">
  <logic:iterate id="error" name="errors">
  <table cellpadding="0" cellspacing="0" border="0"  align="center"> 
<tr> 
    <td valign="top" > 
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
    
    </td></tr></table>
  </logic:iterate>
</logic:present>

   <logic:messagesNotPresent>

		<div id="result">
			<ul>
				<li>
					<a href="<%=request.getContextPath()%>/shortmessage.jsp" target="_parent">操作完成!返回主界面</a>
				</li>
				<li>
					<a
						href="<%=request.getContextPath()%>/account/protected/receiveListAction.shtml?count=5"
						tabindex="2">返回收件箱</a>
				</li>
				<li>
					<a
						href="<%=request.getContextPath()%>/account/protected/sendListAction.shtml?count=5"
						tabindex="3">返回发送箱</a>
				</li>
				<li>
					<a
						href="<%=request.getContextPath()%>/account/protected/draftListAction.shtml?count=5"
						tabindex="4">返回草稿箱</a>
				</li>
			</ul>
		</div>
	</logic:messagesNotPresent>
</center>
