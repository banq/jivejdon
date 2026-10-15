<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>安全验证</title>
    <script>
        var referer = window.location.href;
        var approvalKey = 'refererCaptchaApproved:' + referer;

        function addCaptchaField(form, name, value) {
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = name;
            input.value = value;
            form.appendChild(input);
        }

        window.refererCaptchaCallback=function(res){
            if(res&&res.ret===0){
                var form = document.createElement('form');
                form.method = 'post';
                form.action = '<%=request.getContextPath()%>/captcha/refer.shtml';
                addCaptchaField(form, 'referer', referer);
                addCaptchaField(form, 'ticket', res.ticket);
                addCaptchaField(form, 'randstr', res.randstr);
                document.body.appendChild(form);
                form.submit();
            }
        };
    </script>
    <script>
        window.onload = function() {
            try {
                var expiresAt = Number(localStorage.getItem(approvalKey));
                if (expiresAt > Date.now()) {
                    return;
                }
                if (expiresAt) {
                    localStorage.removeItem(approvalKey);
                }
            } catch (error) {
                console.warn('Unable to read captcha approval.', error);
            }

            var captchaScript = document.createElement('script');
            captchaScript.src = 'https://ssl.captcha.qq.com/TCaptcha.js';
            captchaScript.onload = function() {
                if (window.TencentCaptcha) {
                    new TencentCaptcha('2050847547', window.refererCaptchaCallback).show();
                } else {
                    console.error('Tencent captcha script loaded without TencentCaptcha.');
                }
            };
            captchaScript.onerror = function() {
                console.error('Unable to load Tencent captcha script.');
            };
            document.head.appendChild(captchaScript);
        };
    </script>
</head>
<body>
<a href="/captcha/refer.shtml">test</a>
</body>
</html>
