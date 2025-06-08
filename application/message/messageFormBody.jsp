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
      <html:text styleClass="form-control" property="subject" styleId="replySubject"  size="30" maxlength="50" tabindex="5"
                     onfocus="if(value=='文中加入本站链接、热门标题'){value=''}"
                     onblur="if (value ==''){value='文中加入本站链接、热门标题'}" 
                     onkeyup="limitInputLength(this, 18, 30)"
                     onchange="limitInputLength(this, 18, 30)">
      </html:text>
 <div>
    <span id="subjectLengthTip">已输入 0 个字符</span>
  </div>

    </div>
  </div>
</div>

<script>
function limitInputLength(input, minChineseLength, maxChineseLength) {
    let value = input.value;
    let length = 0;
    for (let i = 0; i < value.length; i++) {
        if (/[\u4e00-\u9fa5]/.test(value[i])) {
            length += 1;
        } else {
            length += 0.5;
        }
        if (length > maxChineseLength) {
            input.value = value.substring(0, i);
            break;
        }
    }
    // 实时显示字数
    document.getElementById('subjectLengthTip').innerText = '已输入 ' + Math.floor(length) + ' 个字符';

    if (length < minChineseLength) {
        input.setCustomValidity("内容不能少于 " + minChineseLength + " 个字符（中文算1，英文算0.5）");
    } else {
        input.setCustomValidity("");
    }
}
// 页面加载后初始化一次
document.addEventListener('DOMContentLoaded', function() {
    var input = document.getElementById('replySubject');
    limitInputLength(input, 18, 30);
});
</script>

<script src="/common/form.js"></script>
<script src="/common/ckeditor/ckeditor.js"></script>
<script src="/common/ckeditor/sample.js"></script>

<script>
    initSample();
    // CKEDITOR.config.height = 480;
    CKEDITOR.config.autoGrow_minHeight = 480;
</script>

