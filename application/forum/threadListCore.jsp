<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="list-group">

 <logic:iterate indexId="i" id="forumThread" name="threadListForm"
                   property="list">
     <logic:equal name="i" value="2">
     <span  class="list-group-item">
     <h3 class="list-group-item-heading">
         <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="fluid"
     data-ad-layout-key="-e3+4a-2h-5p+v6"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="4250528285"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>
     </h3>
     </span>
     </logic:equal>
  
    <%@ include file="threadListCore2.jsp" %>
    
 </logic:iterate>

</div>
	