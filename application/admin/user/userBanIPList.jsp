<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 屏蔽IP列表" />
<%@ include file="../header.jsp" %>


<p><b>屏蔽IP列表</b>
对于爬虫频繁抓取和反复短时发帖的IP会被自动加入该表(anti spam )
<b>下面列表非实时更新，在进行新增或删除操作后，需要等待一会儿才正确更新。
如发现很多连续IP地址，使用Linux/Iptables彻底查封IP段，减轻本系统负担。
可以输入具体IP 211.11.11.23或者 211.11.11.*
<hr size="0">

<form action="<%=request.getContextPath()%>/admin/user/banIPAction.shtml" method="post">
    <input name="ip" type="text" size="10"/>
    <input type="submit" value="新增屏蔽IP"/>
</form>
<br/>

<form action="" method="POST" name="listForm"  >
<table bgcolor="#999999" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td> 
<table bgcolor="#999999" cellpadding="3" cellspacing="1" border="0" width="100%" id="ejiaA1">
<tr bgcolor="#FFFFCC">
    <td style="text-align: center" nowrap="nowrap"><b>IP地址</b></td>   
       <td style="text-align: center" nowrap="nowrap"><b>操作</b></td>
         <td style="text-align: center" nowrap="nowrap"><b>查询</b></td>         
</tr>

<logic:iterate id="ip" name="ips">
 

<tr bgcolor="#ffffff">
    <td style="text-align: center" ><bean:write name="ip" /></td>    
        
     <td style="text-align: center">
       <input type="checkbox" name="ip" value="<bean:write name="ip"  />" >
    </td>     
     <td style="text-align: center">
        <input type="button" name="query" value="查询IP" onClick="copyIP('<bean:write name="ip" />');" >
    </td>    
</tr>

</logic:iterate>
</table>

</td></tr>
</table>
      <input type="button" name="delete" value="选中所有" onClick="selectAll('ip')" >
      <input type="button" name="delete" value="删除选中的IP" onclick="delAction('ip')"  >
</form>
<br>
<form action="<%=request.getContextPath()%>/admin/user/banIPAction.shtml" method="post">
    <input name="ip" type="text" size="10"/>
    <input type="submit" value="新增屏蔽IP"/>
</form>

<script type="text/javascript">
<!--
function delAction(radioName){
    var isChecked = false;    

   if (eval("document.listForm."+radioName).checked){
          isChecked = true;
    }else{
      for (i=0;i<eval("document.listForm."+radioName).length;i++){
         if (eval("document.listForm."+radioName+ "["+i+"]").checked){
           isChecked = true;
           break;
          }
      }
    }
    if (!isChecked){
      alert("请选择一个条目");      
    }else{
       if (confirm( 'Delete this ip ! \n\nAre you sure ? '))
        {
        	
        	document.listForm.action="<%=request.getContextPath()%>/admin/user/banIPAction.shtml?action=delete";
          document.listForm.submit();
          
         }
    }
    
}


function selectAll(radioName){
    var isChecked = false;
    for (i=0;i<eval("document.listForm."+radioName).length;i++){
        eval("document.listForm."+radioName+ "["+i+"]").checked = true
      }  
}

function copyIP(ipaddress){
   document.ipform.ip.value = ipaddress;
   document.ipform.submit();
  
}


//-->
</script>
<script language="javascript"><!--
//ejiaA1("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");

function ejiaA1(o,a,b,c,d){
  var t=document.getElementById(o).getElementsByTagName("tr");
  for(var i=1;i<t.length;i++){
    t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
    t[i].onclick=function(){
           if(this.x!="1"){
                 this.x="1";//本来打算直接用背景色判断，FF获取到的背景是RGB值，不好判断
                 this.style.backgroundColor=d;
           }else{
                 this.x="0";
                 this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
           }
    }
    t[i].onmouseover=function(){
          if(this.x!="1")this.style.backgroundColor=c;
    }
    t[i].onmouseout=function(){
          if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
    }
  }
}

ejiaA1("ejiaA1","#fff","#F5F5F5","#FFFFCC","#FFFF84");
--></script>
<p>小工具:
   <FORM METHOD=get ACTION="http://www.ip138.com/ips8.asp" target="_blank" name="ipform" >
	<input type="text" name="ip">
	 <INPUT TYPE="hidden" name="action" value="2">
	<input type="submit" value="查询IP出处">
    </FORM>

<%@include file="../footer.jsp"%>


