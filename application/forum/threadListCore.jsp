<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<div class="list-group">
<div class="box">
            <div class="linkblock">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="box">
      <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544"
     crossorigin="anonymous"></script>
<ins class="adsbygoogle"
     style="display:block"
     data-ad-format="fluid"
     data-ad-layout-key="-ev-1p-5j-ot+26n"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="3378777426"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>         
                        </div>
                    </div>
                </div>
            </div>
</div>
 <logic:iterate indexId="i" id="forumThread" name="threadListForm"  property="list">
 <article>
    <%@ include file="threadListCore2.jsp" %>
 </article>
 </logic:iterate>


</div>
	
    