<%@ page contentType="text/html; charset=UTF-8" %>

<script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>
<script>


    window.callback = function(res){
        console.log(res)
        // res（未通过验证）= {ret: 1, ticket: null}
        // res（验证成功） = {ret: 0, ticket: "String", randstr: "String"}
        if(res.ret === 0){

            document.getElementById("registerCode").value=res.ticket;
            document.getElementById("randstr").value=res.randstr;
            //alert(document.getElementById("registerCode").value)   // 票据
            //alert(document.getElementById("randstr").value)   // 票据
            verified = true;
            document.getElementById('myForm').submit();
        }
    }

</script>

<logic:notPresent name="query">
    <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table class="table table-striped">
    <tbody>
    <tr>
        <td align="middle">
          <form method="post"  id="myForm" class="search">
            <input type="text" name="query"  value="<bean:write name="query"/>" id="queryId" size="40"/>
            <input type="hidden" id="registerCode" name="registerCode"  >
            <input type="hidden" id="randstr" name="randstr">    
        
       
              <%
              String randstr = null;
              HttpSession session = request.getSession(false);
              if (session != null) 
                   randstr = (String) session.getAttribute("randstr");

              if(randstr == null){
              %>
               <input type="button"  id="TencentCaptcha"
               data-appid="2050847547"
               data-cbfn="callback" value="道场搜索">
              <%
              }else{
              %>
               <input type="submit"  value="道场搜索">
              <% } 
               %>
            </form>            
        </td>
    </tr>
    </tbody>
</table>

<table class="table table-striped">
    <tbody>
    <tr><td align="middle">
        <form action="https://cn.bing.com/search" class="search" method="get">
            <input type=text name=q  value="<bean:write name="query"/>  site:www.jdon.com" size="40">
            <input type="submit" value="Bing搜"> 
            </form>
    </td></tr>
    </tbody>
</table>


<script defer>  
    document.addEventListener("DOMContentLoaded", function(event) { 
    $(document).ready(function() {
      $(document).one('mousemove touchstart', function() {
        $("#myForm").attr("action", "/captcha/search.shtml");
      });
     });
    });
    </script>