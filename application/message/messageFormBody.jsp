<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<script src="//cdn.jdon.com/common/form.js"></script>
<script src="//cdn.jdon.com/common/ckeditor/ckeditor.js"></script>
<script src="//cdn.jdon.com/common/ckeditor/sample.js"></script>
<div class="box">
  <div class="row">
    <div class="col-md-12">
      <div class="form-group">
      <textarea styleClass="form-control" property="subject" styleId="replySubject" cols="100" rows="2" tabindex="5"
                onfocus="if(value=='标题/评论限140字'){value=''}"
                onblur="if (value ==''){value='标题/评论限140字'}" >标题/评论限140字</textarea>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div class="form-group">
        <textarea styleClass="form-control" property="body" cols="100" rows="40" styleId="formBody" tabindex="6"></textarea>

      </div>
    </div>
  </div>
  <script>
      initSample();
      // CKEDITOR.config.height = 480;
      CKEDITOR.config.autoGrow_minHeight = 480;
  </script>

