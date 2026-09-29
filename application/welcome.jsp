<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>安全验证</title>
    <script>
        window.refererCaptchaCallback=function(res){
            if(res&&res.ret===0){
                document.getElementById('ticket').value=res.ticket;
                document.getElementById('randstr').value=res.randstr;
                document.getElementById('captchaForm').submit();
            }
        };
    </script>
    <script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>
    <script>
        window.onload=function(){
            if(window.TencentCaptcha){
                new TencentCaptcha('2050847547',window.refererCaptchaCallback).show();
            }
        };
    </script>
</head>
<body>
    <form id="captchaForm" method="post" action="/captcha/referer.shtml">
        <input type="hidden" name="referer" value="http://127.0.0.1:8080/">
        <input type="hidden" id="ticket" name="ticket">
        <input type="hidden" id="randstr" name="randstr">
    </form>
</body>
</html>
