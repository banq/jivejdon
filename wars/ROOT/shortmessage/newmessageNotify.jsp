<div id="isNewMessage" style="display:none">

<script>

function loadNewMjs2(checkURL){
  if (typeof(checkNewMessage) == 'undefined') {
     $LAB
     .script('<html:rewrite page="/forum/js/newMessage.js"/>').wait()
     .wait(function(){
           checkNewMessage(checkURL);
     })     
  }else
      checkNewMessage(checkURL);
}

if(typeof(Ajax) != "undefined")
     loadNewMjs2("<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml");
</script>
  

</div>
 