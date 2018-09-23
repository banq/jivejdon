<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<bean:parameter id="fmt" name="fmt" />

<logic:equal name="fmt" value="728x90">

  <logic:present name="adIndex" scope="request">
     <logic:equal name="adIndex" value="0">
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 728x90 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:728px;height:90px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="2340228034"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
     </logic:equal>
     <logic:equal name="adIndex" value="1">
         
     </logic:equal>
     <logic:equal name="adIndex" value="2">
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 728x90 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:728px;height:90px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="2340228034"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
     </logic:equal>     
     <logic:equal name="adIndex" value="3">
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 728x90 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:728px;height:90px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="2340228034"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
     </logic:equal>
     <logic:equal name="adIndex" value="4">  
     </logic:equal>
 </logic:present>

 <logic:notPresent name="adIndex" scope="request">
 </logic:notPresent>
</logic:equal>

<logic:equal name="fmt" value="336x280_2_b">
</logic:equal>

<logic:equal name="fmt" value="336x280_g">
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- 页面文章结束处 -->
<ins class="adsbygoogle"
     style="display:inline-block;width:336px;height:280px"
     data-ad-client="ca-pub-7573657117119544"
     data-ad-slot="0310358561"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>
</logic:equal>

<logic:equal name="fmt" value="300x250">
<!-- 广告位：300x250 -->
</logic:equal>
<logic:equal name="fmt" value="500x300">
 <!-- 广告位：文章页面底部 --> 
</logic:equal>