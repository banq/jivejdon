<%@ page contentType="text/html; charset=UTF-8" %>

<div id="isNewMessage" style="display:none"></div>
<script>
var messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";     
 new Ajax.PeriodicalUpdater('isNewMessage', messageChkURL,
  { method: 'get',
    frequency: 300, 
    decay: 2,
    evalScripts: true});
 </script> 
</body>
</html>