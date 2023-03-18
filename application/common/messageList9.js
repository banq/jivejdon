function digMessage(id) {
  var pars = "messageId=" + id;
  load(
    getContextPath() + "/query/updateDigCount.shtml?" + pars,
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
    .script("https://cdn.jdon.com/common/ckeditor/ckeditor.js")
    .wait()
    .script("https://cdn.jdon.com/common/ckeditor/sample.js")
    .wait(function () {
      initSample();
      lazyloaded = true;
    });
}

function scrollLoadByElementId(url, nextPageContent) {
  var loading = false;
  document.getElementById(nextPageContent).innerHTML = " ";
  $(window).on("scroll", function () {
    var hT = $("#" + nextPageContent).offset().top,
      hH = $("#" + nextPageContent).outerHeight(),
      wH = $(window).height(),
      wS = $(window).scrollTop();
    if (
      wS > hT + hH - wH &&
      !loading &&
      document.getElementById(nextPageContent).innerHTML == " "
    ) {
      loading = true;
      var surl = url.indexOf("?") == -1 ? url + "?" : url + "&";
      load(surl, function (xhr) {
        document.getElementById(nextPageContent).innerHTML = xhr.responseText;
        loading = false;
      });
    }
  });
}
function sumStart() {
  start = start + count;
}
function returnStart() {
  return "start=" + start + "&count=" + count;
}
function returnAllCount() {
  return start < allCount ? true : false;
}

function scrollAppendByElementId(
  url,
  nextPageContent,
  endPage,
  returnAllCount,
  sumStart,
  returnStart
) {
  var loading = false;
  $(window).on("scroll", function () {
    var hT = $("#" + nextPageContent).offset().top,
      hH = $("#" + nextPageContent).outerHeight(),
      wH = $(window).height(),
      wS = $(window).scrollTop();
    var hT2 = $("#" + endPage).offset().top,
      hH2 = $("#" + endPage).outerHeight();
    if (wS > hT + hH - wH && !loading && wS < hT2 + hH2 - wH) {
      if (returnAllCount()) {
        loading = true;
        var surl = url.indexOf("?") == -1 ? url + "?" : url + "&";
        load(surl + returnStart(), function (xhr) {
          document.getElementById(nextPageContent).innerHTML =
            document.getElementById(nextPageContent).innerHTML +
            xhr.responseText;
          loading = false;
          sumStart();
        });
      }
    }
  });
}
