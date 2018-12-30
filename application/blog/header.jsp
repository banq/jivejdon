<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%     
int expire = 24 * 60 * 60;

  long modelLastModifiedDate = com.jdon.jivejdon.presentation.action.util.ForumUtil.getForumsLastModifiedDate(this.getServletContext());
if (!com.jdon.jivejdon.util.ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
	return ;
}

%>  

<%@ include file="../../common/security.jsp" %>
<%@ include file="../../common/loginAccount.jsp" %>

<logic:present name="loginAccount" >   
  <% 
//urlrewrite.xml:
//	  <rule>
//	<from>^/blog/(.*)$</from>
//	<!-- note: here must be userId, actually userId'value is username'value, need a model key  -->
//	<to>/blog/index.shtml?userId=$1&amp;username=$1</to>
//</rule>
  com.jdon.jivejdon.model.Account account = (com.jdon.jivejdon.model.Account)request.getAttribute("loginAccount");
  String userId = request.getParameter("userId");
  String username = request.getParameter("username");
  if (userId != null || username != null ){
	  //userId vaule maybe username or userId.
	  if ( account.getUsername().equals(username) ||  account.getUserId().equals(userId)){
	      request.setAttribute("isOwner", "true");
		  com.jdon.jivejdon.presentation.form.AccountProfileForm accountProfileForm = new com.jdon.jivejdon.presentation.form.AccountProfileForm();
		  accountProfileForm.setAccount(account);
		  request.setAttribute("accountProfileForm", accountProfileForm);  
	  }
  }else if (userId == null && username == null){ 
	  request.setAttribute("isOwner", "true"); 
	  com.jdon.jivejdon.presentation.form.AccountProfileForm accountProfileForm = new com.jdon.jivejdon.presentation.form.AccountProfileForm();
	  accountProfileForm.setAccount(account);
	  request.setAttribute("accountProfileForm", accountProfileForm);  
  
    } %>
</logic:present>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><bean:write name="accountProfileForm" property="account.username"/>的博客</title> 
<%
    String domainUrl = com.jdon.util.RequestUtil.getAppURL(request);
%>
<link rel="canonical" href="<%=domainUrl %>/blog/<bean:write name="accountProfileForm" property="account.username"/>" />
<link href="/common/jivejdon5.css" rel="stylesheet" type="text/css" />
<link href="/common/blog/themes/default/style/blog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://cdn.jdon.com/common/js/prototype.js"></script>
<script type="text/javascript" src="<html:rewrite page="/account/protected/js/account.jsp"/>"></script>
<script >

var wtitlename;
var url;
function openPopUpWindow(wtitlename, url){
   this.wtitlename = wtitlename;
   this.url=url;
   loadWLJS(openPopUpBlogW);
}     

popupW=null; 
var openPopUpBlogW = function(){
    if (popupW == null) {             
       popupW = new Window({className: "mac_os_x", width:600, height:380, title: wtitlename}); 
       popupW.setURL(url);
       popupW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myW) {    	  
          if (myW == popupW){        	        	
            popupW = null;   
            Windows.removeObserver(this);
          }
        }
       }
     Windows.addObserver(myObserver);
     } 
 }     
 

</script>
</head> 

<body > 
<%@ include file="../account/loginAJAX.jsp" %>

<div class="topbar"> 
   <div class="topbar_inner"> 
      <div class="topbar_inner_left"><a href="<html:rewrite page="/"/>" target="_blank"><html:img page="/images/jdonsmall.gif" border="0" width="120" height="35" alt="jdon.com" /></a></div> 
      <div class="topbar_inner_right"> 
    <logic:present name="principal" >
         欢迎<a href="<%=request.getContextPath()%>/blog/<bean:write name="principal" />"></a><bean:write name="principal" /></a> 
         <a href="javascript:openPopUpWindow('注册资料修改','<%=request.getContextPath()%>/account/protected/editAccountForm.shtml?action=edit&username=<bean:write name="principal" />')"> 注册资料修改 </a> | 
         <logic:notPresent name="isOwner" >
           <a href="<%=request.getContextPath() %>/blog/<bean:write name="principal" />"><bean:write name="principal" />的博客</a> | 
         </logic:notPresent>
         <html:link page="/jasslogin?logout"> 退出 </html:link>
    </logic:present>
     <logic:notPresent name="principal"  >
     <span onMouseOver="loadWLJS(loginW)">
        <a href="javascript:void(0);" onclick='loginW;'>登录</a> | <html:link page="/account/newAccountForm.shtml">注册</html:link>
        </span>  
     </logic:notPresent>
      
    </div> 
   </div> 
</div> 
<div class="headbar"> 
   <div class="headbar_list1"><bean:write name="accountProfileForm" property="account.username"/></div> 
</div> 
<div class="menubar"> 
   <div class="menubar_left">
     <ul class="menuitem">
       <li></li>
       <li>博客空间</li>
       <li></li>
     </ul>
   </div>
  <html:form action="/message/searchAction.shtml" method="post" styleId="searchform">
    <div class="menubar_right">
    <input name="count" type="hidden" value="1"/>
   <input name="userId" type="hidden" value="<bean:write name="accountProfileForm" property="account.userId"/>"/>
    <input name="query" value="请输入搜索关键字" type="text" style="width:140px;" class="inputstyle" onFocus="if (searchform.query.value=='请输入搜索关键字'){searchform.query.value=''}"/>
   <img src="<html:rewrite page="/blog/themes/default/images/search.gif" />" align="absmiddle" style="cursor:pointer" onClick="if((searchform.query.value=='') || (searchform.query.value=='请输入搜索关键字')){alert('请输入搜索关键字！');searchform.query.focus();}else{searchform.submit();}"></div> 
</html:form> 
</div> 
<div class="secondbar"></div> 
 <script language="JavaScript" type="text/javascript">
 var uploadW;
 ////loadWLJSWithP(url, openUploadWindow)
var openUploadWindow = function(url){                
    if (uploadW == null) {
       uploadW = new Window({className: "mac_os_x", width:450, height:300, title: " Upload "}); 
       uploadW.setURL(url);
       uploadW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myuploadW) {    	  
          if (myuploadW == uploadW){        	
            appendUploadUrl();
            uploadW = null;
            Windows.removeObserver(this);
          }
         }
        }
        Windows.addObserver(myObserver);
    } else
	  uploadW.showCenter();
   }     
   
   function killUploadWindow(){
      if (uploadW != null){  
           uploadW.close();                           
     }
     
     window.location.reload();
   }
   
     function myalert(errorM){
        if (errorM == null) return;
        infoDiagClose();
        Dialog.alert(errorM, 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});
  }
 </script>
<div class="mainarea"> 
   <div class="mainarea_left">      
    <div class="box_mode_1"> 
	     <div class="title"> 
	        <div class="title_left"><a href="<%=request.getContextPath()%>/blog/<bean:write name="accountProfileForm" property="account.username"/>">博主首页</a></div> 
	        <div class="title_right"><a href="/">首页</a></div> 
	     </div> 
	     
		 <div class="content"> 
              <div class="b_photo">
                <logic:notEmpty name="accountProfileForm" property="account.uploadFile">
                  <logic:equal name="accountProfileForm" property="account.roleName" value="User">
     				  <img src="/img/account/<bean:write name="accountProfileForm" property="account.userId"/>"  border='0' class="post_author_pic"/>
				  </logic:equal>
                  <logic:equal name="accountProfileForm" property="account.roleName" value="SinaUser">                 
                     <img  src="<bean:write name="accountProfileForm" property="account.uploadFile.description"/>"  border='0' class="post_author_pic"/>
                  </logic:equal>
                  <logic:equal name="accountProfileForm" property="account.roleName" value="TecentUser">                 
                      <img  src="<bean:write name="accountProfileForm" property="account.uploadFile.description"/>"  border='0' class="post_author_pic"/>
                  </logic:equal>                                    
				  				  
				</logic:notEmpty>
				
			    <logic:empty name="accountProfileForm" property="account.uploadFile">
				  <img src="<html:rewrite page="/blog/themes/default/images/nouserface_1.gif"/>" width="85" height="85" border="0" >
				</logic:empty>
				
				<logic:present name="isOwner" >
				    <br>
					<a href="javascript:loadWLJSWithP('<%=request.getContextPath()%>/account/protected/upload/uploadUserFaceAction.shtml?parentId=<bean:write name="accountProfileForm" property="account.userId"/>', openUploadWindow)"
               				tabindex="4">上传头像</a>	
                </logic:present>                    				
               </div>
              <div class="b_name">
              <span  class='Users ajax_userId=<bean:write name="accountProfileForm" property="account.userId"/>' ><bean:write name="accountProfileForm" property="account.username"/>
              	
              <br/>              	
              <logic:notPresent name="principal" >
               <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=3&subscribeId=<bean:write name="accountProfileForm" property="account.userId"/>"
                  target="_blank" title="加关注"  rel="nofollow">            
                 <img src="/images/user_add.gif" width="18" height="18" alt="关注本主题" border="0" /><span class="blackgray">+关注</span>
                </a>                
              </logic:notPresent>
              <logic:present name="principal" >
                     <div id="isFollowing"></div>                        
                    <script>
                    var pars = 'id=' +<bean:write name="accountProfileForm" property="account.userId"/> ;
                    new Ajax.Updater(isFollowing, '<%=request.getContextPath() %>/account/protected/sub/checkSub.shtml?service=subscriptionService&method=checkSubscription', { method: 'get', parameters: pars });                    
                    </script>
              
              </logic:present>
              
              
              <table align="center"  cellpadding="5" cellspacing="5" border="0">
              <tr><td>
              <logic:notEmpty name="accountProfileForm" property="account.subscriptionCount">
                <logic:greaterThan name="accountProfileForm" property="account.subscriptionCount" value="0">
                <a href="<%=request.getContextPath()%>/social/following.shtml?subscribeType=3&userId=<bean:write name="accountProfileForm" property="account.userId"/>"  target="_blank"  rel="nofollow">
                 <bean:write name="accountProfileForm" property="account.subscriptionCount"/>
                 </a>
                </logic:greaterThan>
              </logic:notEmpty>  
              <br>关注
              </td><td>
              <logic:notEmpty name="accountProfileForm" property="account.subscribedCount">
                <logic:greaterThan name="accountProfileForm" property="account.subscribedCount" value="0">
                <a href="<%=request.getContextPath()%>/social/follower.shtml?subscribeType=3&userId=<bean:write name="accountProfileForm" property="account.userId"/>"  target="_blank"  rel="nofollow">
                 <bean:write name="accountProfileForm" property="account.subscribedCount"/>
                 </a>
                </logic:greaterThan>
              </logic:notEmpty>  
              <br>粉丝
              </td><td>
              <logic:notEmpty name="accountProfileForm" property="account.messageCount">
                <logic:greaterThan name="accountProfileForm" property="account.messageCount" value="0">
                <a href="<%=request.getContextPath()%>/blog/messages/<bean:write name="accountProfileForm" property="account.username"/>"  target="_blank"  rel="nofollow">
                 <bean:write name="accountProfileForm" property="account.messageCount"/>
                 </a>
                </logic:greaterThan>
              </logic:notEmpty>  
              <br>帖数
              </td></tr>              
              </table>
                         
                              
              </span></div>
              <div class="b_op">
              <span onMouseOver="loadWLJS(nof)">
               <logic:notPresent name="isOwner" >
                    <a href="javascript:void(0);" onClick="openShortmessageWindow('发消息','<html:rewrite page="/account/protected/shortmessageAction.shtml" paramId="messageTo"  paramName="accountProfileForm"  paramProperty="account.username" />');"
                   >发消息</a>
               </logic:notPresent>
               <logic:present name="isOwner" >
                  
                   <a href="javascript:openPopUpWindow('写消息','<%=request.getContextPath()%>/account/protected/shortmessageAction.shtml')"
               				tabindex="1">写信息</a>
                   <a href="javascript:openPopUpWindow('收件箱','<%=request.getContextPath()%>/account/protected/receiveListAction.shtml?count=10')"
               				tabindex="2">收件箱</a>		
               	<a href="javascript:openPopUpWindow('发信箱','<%=request.getContextPath()%>/account/protected/sendListAction.shtml?count=10')"
               				tabindex="3">发送箱</a>
               	<a href="javascript:openPopUpWindow('草稿箱','<%=request.getContextPath()%>/account/protected/draftListAction.shtml?count=10')"
               				tabindex="4">草稿箱</a>	
              
               </logic:present>
                   </span>
                              
               </div>	
	  </div> 
	  </div> 
      <div class="box_mode_1"> 
	     <div class="title"> 
	       <div class="title_left">个人资料</div> 
	        <div class="title_right">
	        <logic:present name="isOwner" >
	          <a href="javascript:void(0);" onClick="openPopUpWindow('编辑个人公开资料','<html:rewrite page="/account/protected/accountProfileForm.shtml?action=edit" paramId="userId" paramName="accountProfileForm"  paramProperty="account.userId" />')">[编辑]</a>     	       
	        </logic:present>  
	        </div> 
	     </div> 
		 <div class="content"> 
            <div class="list_div">
<logic:iterate id="property" name="accountProfileForm" property="propertys" indexId="i">
<logic:notEmpty name="property"  property="name" >
<ul><li>
   <bean:write name="property"  property="name" />:
   <bean:write name="accountProfileForm"  property='<%= "property[" + i + "].value" %>'   filter="false" />
</li></ul>
</logic:notEmpty>
</logic:iterate>  
              </div>	
           </div> 
	  </div> 
      <div class="box_mode_1">
	     <div class="title">
	     <div class="title_left">
	         我的关注
	     </div>
	     <div class="title_right">
	     <logic:present name="isOwner" >
	          <html:link page="/account/protected/sub/default.jsp" >[编辑]</html:link>    	       
	        </logic:present>  
	     </div>
	     </div>
		 <div class="content">
 	 <div class="list_div">
		 	  <ul><li>
		 <a href="/social/interest.shtml?userId=<bean:write name="accountProfileForm" property="account.userId"/>" rel="nofollow">
	               近期关注情况
	      </a>
		 </li></ul>
		</div>		 
		 </div>
	  </div>
      <div class="box_mode_1"> 
	     <div class="title"> 
	     <div class="title_left"></div> 
	     <div class="title_right"></div> 
	     </div> 
		 <div class="content"> 
		 </div> 
	  </div> 
      
</div> 