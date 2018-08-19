<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>

<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">

<bean:define id="title"  value=" 精华帖" />
<%@ include file="../common/IncludeTop.jsp" %>

<script>
function digMessage(id)
{            
	var pars = 'messageId='+id;   
    new Ajax.Updater('digNumber_'+id, getContextPath() +'/query/updateDigCount.shtml', { method: 'get', parameters: pars });
    $('textArea_'+id).update("赞");
    
}

function viewcount(threadId, sId)
{            	
	 var pars = 'thread=' + threadId + "&sId=" + sId;   
   new Ajax.Updater({success: 'viewcount'}, getContextPath() +'/query/viewThread.shtml', { method: 'get', parameters: pars });
}

</script>

<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
    <div class="row">
      <!-- /////////////////左边 -->
      <div id="main-content" class="col-md-8">
        <div class="box"> 
        <!-- 填写 -->
			
<center> <h3>推荐热点精华</h3> </center>

<div class="tres" >        
     符合查询主题共有<b><bean:write name="threadListForm" property="allCount"/></b>贴  
<MultiPagesREST:pager actionFormName="threadListForm" page="/approval" >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager> 

      </div>

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />
   <div class="box"> 
  <div class="linkblock" itemscope itemtype="http://schema.org/BlogPosting">
  <div class="row">
        <div class="col-sm-12">
       <div class="box">
             <bean:define id="body" name="forumMessage" property="messageVO.body" />
              <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" target="_blank" >
             <h3  class="vid-name"><bean:write name="forumThread" property="name" /></h3></a>      
      
         <div class="info">			 
              <span><i class="fa fa-calendar"></i>
                <bean:define id="cdate" name="forumThread" property="creationDate" ></bean:define>
        <%String cdateS = (String)pageContext.getAttribute("cdate"); %><%=cdateS.substring(2, 11) %>
                      </span>
              <logic:notEqual name="forumThread" property="state.messageCount" value="0">
              <span><i class="fa fa-comment"></i> <bean:write name="forumThread" property="state.messageCount" />
                      </span>
		       </logic:notEqual>  
              <span><i class="fa fa-eye"></i><bean:write name="forumThread" property="viewCount" />
                      </span>
			   <logic:notEqual name="forumMessage" property="digCount" value="0">
                       <span><i class="fa fa-heart"></i>
                      <bean:write name="forumMessage" property="digCount"/>
					   </span>
                      </logic:notEqual>     
			 <span><i class="fa fa-user"></i><a href="<%=request.getContextPath()%>/blog/<bean:write name="forumThread" property="rootMessage.account.username"/>" class="smallgray"><bean:write name="forumThread" property="rootMessage.account.username" /></a>
               </span>
                      
            </div>
          <div class="wrap-vid">
              <div class="thumbn">
           
       <logic:notEmpty name="forumMessage" property="messageVO.thumbnailUrl">
          <img  src="<bean:write name="forumMessage" property="messageVO.thumbnailUrl"/>" border='0' width="150" height="150" />
       </logic:notEmpty>
              </div>
              <p><bean:write name="forumThread" property="rootMessage.messageVO.shortBody[80]" />. <a href="<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId"/>" target="_blank" class="smallgray">详细</a></p>
      </div>
  
             </div>
  </div>
</div>
</div>
</div>
</logic:iterate>
<div>
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 自动调整尺寸 -->
<ins class="adsbygoogle"
     style="display:block"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="9040920314"
     data-ad-format="auto"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
</div>

<div class="tres" >        
     符合查询主题共有<b><bean:write name="threadListForm" property="allCount"/></b>贴  
<MultiPagesREST:pager actionFormName="threadListForm" page="/approval" >
<MultiPagesREST:prev name=" 上一页 " />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name=" 下一页 " />
</MultiPagesREST:pager> 

      </div>
   
        </div>
      </div>  
      <!-- /////////////////右边 -->
       <div id="sidebar" class="col-md-4">
        <!---- Start Widget ---->
        <div class="widget wid-follow">
          <div class="heading"></div>
          <div class="content">
            <ul class="list-inline">
              <form role="form" class="form-horizontal" method="post" action="/query/searchAction.shtml">
						 <input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
					 </form>
            </ul>           
          </div>
        </div>
        <!---- Start Widget ---->
        <div class="widget wid-post">
          <div class="heading"></div>
          <div class="content">
              <div class="post wrap-vid">
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 页上左336 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:336px;height:280px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="6751585519"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
            </div>
          </div>
        </div>
        <!---- Start Widget ---->
        <div class="widget">
              <div class="wid-vid">
         <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="autorelaxed"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="7669317912"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>
				    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 推荐贴右侧300x600 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:300px;height:600px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3352261515"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
              </div>
        </div>
    
    </div>
  </div>
</div>  	
	

</logic:greaterThan>
</logic:present>



<%@include file="../common/IncludeBottom.jsp"%>


