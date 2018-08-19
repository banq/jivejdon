<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="foot">
<%@include file="../../common/IncludeBottomBody.jsp"%>
</div> 
 
<script>
function openShortmessageWindow(name, url){
   if (!isLogin){//login defined in .common/security.jsp        
         loadWLJS(loginAlert);
        return false;
    }
   openPopUpWindow(name, url);
}

var loginAlert = function(){
        Dialog.alert("请先登陆", 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});
   
}
 
</script>
</body> 
</html> 