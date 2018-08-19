<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../../../blog/header.jsp" %>
<script type="text/javascript" src="<html:rewrite page="/account/protected/js/account.jsp"/>"></script>


   <div class="mainarea_right"> 
   
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">我关注的道场</div> 
		    <div class="title_right"> <a href="<%=request.getContextPath()%>/message/post.jsp">
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
		    <div class="title_left">我关注的主题</div> 
		    <div class="title_right"> <a href="<%=request.getContextPath()%>/message/post.jsp">
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
		    <div class="title_left">我关注的标签</div> 		   
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
		    <div class="title_left">我关注的作者</div> 		   
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
    new Ajax.Updater('forum', '<%=request.getContextPath()%>/account/protected/sub/subForumList.shtml', { method: 'get', parameters: pars  });
}

  function loadSubThreadList(){
        var pars = "noheader=on&userId=<bean:write name="accountProfileForm" property="account.userId"/>" ;
        new Ajax.Updater('content', '<%=request.getContextPath()%>/account/protected/sub/subThreadList.shtml', { method: 'get', parameters: pars  });
   }
  
   function loadSubTagList(){
        var pars = "noheader=on&userId=<bean:write name="accountProfileForm" property="account.userId"/>" ;
        new Ajax.Updater('content_reply', '<%=request.getContextPath()%>/account/protected/sub/subTagList.shtml', { method: 'get', parameters: pars  });
   }
   
    function loadAuthor(){
        var pars = "noheader=on&userId=<bean:write name="accountProfileForm" property="account.userId"/>" ;
        new Ajax.Updater('author', '<%=request.getContextPath()%>/account/protected/sub/subAccountList.shtml', { method: 'get', parameters: pars  });
   }
   
   loadSubForumList();
   loadSubThreadList();
   loadSubTagList();
   loadAuthor();
</script>


<%@ include file="../../../blog/footer.jsp" %>