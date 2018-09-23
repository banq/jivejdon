<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<!-- 1=关注你了 2=已关注 3=相互关注  0 都没关注-->
<bean:parameter name="id" id="id" value=""/>
<logic:present name="Integer">
 <logic:equal name="Integer" value="1">
    关注你了 &nbsp;
      <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=3&subscribeId=<bean:write name="id"/>"
                  target="_blank" title="加关注"  rel="nofollow">            
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本主题" border="0" /><span class="blackgray">+关注</span>
       </a>    
 </logic:equal>  
 <logic:equal name="Integer" value="2">
    已关注&nbsp;
      <a href="/account/protected/sub/delSub.shtml?service=subscriptionService&method=deleteSubscription&id=<bean:write name="id"/>"
                  target="_blank"  rel="nofollow">            
                 <span class="blackgray">取消关注</span>
       </a>    
        
 </logic:equal>  
 <logic:equal name="Integer" value="3">
    相互关注&nbsp;
      <a href="/account/protected/sub/delSub.shtml?service=subscriptionService&method=deleteSubscription&id=<bean:write name="id"/>"
                  target="_blank"  rel="nofollow">            
                 <span class="blackgray">取消关注</span>
       </a>    
 </logic:equal>  
 <logic:equal name="Integer" value="0">
  <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=3&subscribeId=<bean:write name="id"/>"
                  target="_blank" title="加关注"  rel="nofollow">            
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本主题" border="0" /><span class="blackgray">+关注</span>
                </a>
 </logic:equal>        
</logic:present>
