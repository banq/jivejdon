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
      <html:text styleClass="form-control" property="subject" styleId="replySubject" size="50" maxlength="50" tabindex="5"
                     onfocus="if(value=='文中加入本站链接、热门标题'){value=''}"
                     onblur="if (value ==''){value='文中加入本站链接、热门标题'}" onkeydown="limitInputLength(this, 50)"></html:text>

<script>
function limitInputLength(input, maxChineseLength) {
    let value = input.value; // 获取输入框的值
    let length = 0; // 初始化长度计数器

    // 遍历每个字符，计算总长度
    for (let i = 0; i < value.length; i++) {
        // 如果是中文字符（Unicode 范围：\u4e00-\u9fa5），长度 +1
        if (/[\u4e00-\u9fa5]/.test(value[i])) {
            length += 1;
        }
        // 如果是英文字符（包括字母、数字、标点等），长度 +0.5
        else {
            length += 0.5;
        }

        // 如果长度超过最大限制，截断字符串并退出循环
        if (length > maxChineseLength) {
            input.value = value.substring(0, i);
            break;
        }
    }
                 
}
</script>
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

