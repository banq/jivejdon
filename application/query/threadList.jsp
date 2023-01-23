<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
<logic:notEmpty name="noheader">        
<logic:equal name="i" value="1">
        <div class="box">
            <div class="linkblock">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="box">
          <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
        <!-- 自适应主广告 -->
		<ins class="adsbygoogle"
     		style="display:block"
     		data-ad-client="ca-pub-7573657117119544"
     		data-ad-slot="5184711902"
     		data-ad-format="auto"
     		data-full-width-responsive="true"></ins>
		<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
          </script>                        
                        </div>
                    </div>
                </div>
            </div>
        </div>
</logic:equal>
</logic:notEmpty>
<%@ include file="threadListCore.jsp" %>
</logic:iterate>
