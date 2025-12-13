<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<div class="box">


<div class="row">
  <div class="col-lg-12">
    <div class="form-group">
      <html:textarea styleClass="form-control" property="body" cols="100" rows="40" styleId="formBody" tabindex="6"></html:textarea>

    </div>
  </div>
</div>

<div class="row">
  <div class="col-lg-12">
    <div class="form-group">
      <html:text styleClass="form-control" property="subject" styleId="replySubject"  size="50" maxlength="50" tabindex="5"
                     onfocus="if(value=='文中加入本站链接、热门标题'){value=''}"
                     onblur="if (value ==''){value='文中加入本站链接、热门标题'}" >
      </html:text>
 
    </div>
  </div>
</div>



<script src="/common/form.js"></script>
<script src="/common/ckeditor/ckeditor.js"></script>
<script src="/common/ckeditor/sample.js"></script>

<script>
    initSample();
    // CKEDITOR.config.height = 480;
    CKEDITOR.config.autoGrow_minHeight = 480;
</script>

