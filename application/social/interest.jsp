<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../blog/header.jsp" %>



   <div class="mainarea_right">
   
     <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">近期关注的标签</div> 		   
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content_reply>正在读取，请等待...</div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
    
   
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">近期关注过的板块</div> 
		    <div class="title_right"> <a href="<%=request.getContextPath()%>/message/postThread.jsp">
            <img src="/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=forum >正在读取，请等待...</div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
	  
	  
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">近期关注过的主题</div> 
		    <div class="title_right"> <a href="<%=request.getContextPath()%>/message/postThread.jsp">
            <img src="/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>正在读取，请等待...</div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
	  
	  
	   <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">近期关注过的作者</div> 		   
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=author>正在读取，请等待...</div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
   </div> 
</div> 

<script>

function loadSubForumList(){
    var pars = "noheader=on&userId=<bean:write name="accountProfileForm" property="account.userId"/>" ;
    new Ajax.Updater('forum', '<%=request.getContextPath()%>/social/subForumList.shtml', { method: 'get', parameters: pars  });
}

  function loadSubThreadList(){
        var pars = "noheader=on&userId=<bean:write name="accountProfileForm" property="account.userId"/>" ;
        new Ajax.Updater('content', '<%=request.getContextPath()%>/social/subThreadList.shtml', { method: 'get', parameters: pars  });
   }
  
   function loadSubTagList(){
        var pars = "noheader=on&userId=<bean:write name="accountProfileForm" property="account.userId"/>" ;
        new Ajax.Updater('content_reply', '<%=request.getContextPath()%>/social/subTagList.shtml', { method: 'get', parameters: pars  });
   }
   
    function loadAuthor(){
        var pars = "noheader=on&userId=<bean:write name="accountProfileForm" property="account.userId"/>" ;
        new Ajax.Updater('author', '<%=request.getContextPath()%>/social/subAccountList.shtml', { method: 'get', parameters: pars  });
   }
   
   loadSubForumList();
   loadSubThreadList();
   loadSubTagList();
   loadAuthor();
</script>

<script type="text/JavaScript">
function editAction(listForm, radioName){
	if (!checkSelect(listForm,radioName)){
      alert("请选择一个条目");
      return false;      
    }else{    	
     eval("document"+"."+listForm).action="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?action=edit";
     return true;
    }
}

function checkSelect(listForm,radioName){
	 var isChecked = false;

	   if (eval("document"+"."+listForm+"."+radioName).checked){
	          isChecked = true;
	    }else{
	      for (i=0;i<eval("document"+"."+listForm+"."+radioName).length;i++){
	         if (eval("document"+"."+listForm+"."+radioName+ "["+i+"]").checked){
	           isChecked = true;
	           break;
	          }
	      }
	    }
	  return isChecked;
}

function delAction(listForm,radioName){
    if (!checkSelect(listForm,radioName)){
      alert("请选择一个条目");      
    }else{
       if (confirm( '删除该关注设置 ! \n\nAre you sure ? '))
        {
    	   eval("document"+"."+listForm).action="<%=request.getContextPath()%>/account/protected/sub/subSaveAction.shtml?action=delete";
           return true;
         }
    }
    return false;
}

</script>   
<%@ include file="../blog/footer.jsp" %>