function viewcount(threadId, sId) {
    var pars = 'thread=' + threadId + "&sId=" + sId;
    load(getContextPath() + '/query/viewThread.shtml?' + pars, function (xhr) {

    });
}

function digMessage(id) {
    var pars = 'messageId=' + id;
    load(getContextPath() + '/query/updateDigCount.shtml?' + pars, function (xhr) {
        document.getElementById('digNumber_' + id).innerHTML = xhr.responseText;
        document.getElementById('textArea_' + id).innerHTML = "  ";
    });

}


function tagthreads(length, tablewidth, count, tagID) {
 window.onload = function() {
    $.ajax({
        url: 'https://cdn.jdon.com/query/tagThreads/' + tagID,
        success: function (response) {
            $('#tagthreads_' + tagID).html(response);
        }
    });
 }

//    load(getContextPath() +'/query/tagThreads/'+tagID, function(xhr) {
//  	       document.getElementById('tagthreads_'+tagID).innerHTML = xhr.responseText;
//			});
//    cross domain
}

function approveList() {
    load('https://cdn.jdon.com/query/approved', function (xhr) {
        document.getElementById('approved').innerHTML = xhr.responseText;
    });
}

var formSubmitcheck = false;
var subjectold = "";

function checkPost(theForm) {

    if (document.getElementById('forumId_select') != null
        && document.getElementById('forumId_select').value == "") {
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
        } else
            alert("发言重复");
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
        .script("https://cdn.jdon.com/common/ckeditor/ckeditor.js").wait()
        .script("https://cdn.jdon.com/common/ckeditor/sample.js")
        .wait(function () {
            initSample();
            lazyloaded = true;
        })
}    


