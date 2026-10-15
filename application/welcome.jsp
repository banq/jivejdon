<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>安全验证</title>
    <script>
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
                addCaptchaField(form, 'referer', window.location.href);
                addCaptchaField(form, 'ticket', res.ticket);
                addCaptchaField(form, 'randstr', res.randstr);
                document.body.appendChild(form);
                form.submit();
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
</body>
</html>
