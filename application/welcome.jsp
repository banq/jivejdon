<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, max-age=0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>Jdon.com安全验证</title>
    <script>
        var referer = window.location.href;
        var approvalStorageKey = 'JDON_CAPTCHA_APPROVAL';

        function addCaptchaField(form, name, value) {
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = name;
            input.value = value;
            form.appendChild(input);
        }

        function submitForm(fields) {
            var form = document.createElement('form');
            form.method = 'post';
            form.action = '/captcha/refer.shtml';
            Object.keys(fields).forEach(function(name) {
                addCaptchaField(form, name, fields[name]);
            });
            document.body.appendChild(form);
            form.submit();
        }

        document.addEventListener('DOMContentLoaded', function() {
            var approvalToken = null;
            try {
                approvalToken = localStorage.getItem(approvalStorageKey);
            } catch (error) {
                console.warn('Unable to read captcha approval token.', error);
            }

            if (approvalToken) {
                submitForm({
                    referer: referer,
                    approvalToken: approvalToken
                });
                return;
            }

            const a = document.createElement('a');
            a.href = '/captcha/' + 'refer.shtml';

            const h3 = document.createElement('h3');
            h3.textContent = '请点击验证你是否是真人？或从首页jdon.com访问本文';

            a.appendChild(h3);
            document.body.appendChild(a);
        });
    </script>
</head>
<body>
</body>
</html>
