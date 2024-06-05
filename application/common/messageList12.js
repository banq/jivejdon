function digMessage(id) {
  var pars = "messageId=" + id;
  load(
    getContextPath() + "/forum/updateDigCount.shtml?" + pars,
    function (xhr) {
      document.getElementById("digNumber_" + id).innerHTML = xhr.responseText;
      document.getElementById("textArea_" + id).innerHTML = "  ";
    }
  );
}

var formSubmitcheck = false;
var subjectold = "";
function checkPost(theForm) {
  if (
    document.getElementById("forumId_select") != null &&
    document.getElementById("forumId_select").value == ""
  ) {
    alert("页面forum错误，请拷贝备份你的发言后，重新刷新本页");
    formSubmitcheck = false;
    return formSubmitcheck;
  }

  var body = theForm.body.value.replace(/(^\s*)|(\s*$)/g, "");
  var subject = theForm.subject.value.replace(/(^\s*)|(\s*$)/g, "");
  if (subject != "") {
    if (subjectold != subject) {
      formSubmitcheck = true;
      theForm.formButton.disabled = true;
    } else alert("发言重复");
    subjectold = subject;
  } else {
    alert("请输入发言标题和发言内容！" + subject + " " + body);
    formSubmitcheck = false;
  }
  return formSubmitcheck;
}

var lazyloaded = false;
function loadCkeditJS() {
  if (lazyloaded) return;
  $LAB
    .script("/common/ckeditor/ckeditor.js")
    .wait()
    .script("/common/ckeditor/sample.js")
    .wait(function () {
      initSample();
      lazyloaded = true;
    });
}

document.addEventListener("DOMContentLoaded", function(event) { 
  
$(document).ready(function() {
 // 当输入框内容发生变化时触发
 $('#v_search').on('input', function() {
     var userInput = $(this).val(); // 获取用户输入的内容

     // 发起 AJAX 请求
     $.ajax({
         url: '/thread/searchAction.shtml', // 请求的URL
         type: 'GET', // 请求类型
         data: { query:  userInput }, // 发送给服务器的数据，可以根据需要传递其他参数
         success: function(response) { // 请求成功时执行的回调函数
             // 处理服务器返回的数据
             $('#searchResult').html(response);
         },
         error: function(xhr, status, error) { // 请求失败时执行的回调函数
             // 处理请求失败的情况
             console.error('AJAX请求失败:', status, error);
         }
     });
 });
});


});



