<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, max-age=0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>安全验证</title>
    <script>
        var referer = window.location.href;
        var approvalStorageKey = 'JDON_CAPTCHA_APPROVAL';
        var actionReturnStorageKey = 'JDON_CAPTCHA_ACTION_RETURN';

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
            try {
                sessionStorage.setItem(actionReturnStorageKey, '1');
            } catch (error) {
                console.warn('Unable to save captcha return marker.', error);
            }
            document.body.appendChild(form);
            form.submit();
        }

        window.refererCaptchaCallback = function(res) {
            if (res && res.ret === 0) {
                submitForm({
                    referer: referer,
                    ticket: res.ticket,
                    randstr: res.randstr
                });
            }
        };

        window.onload = function() {
            var approvalToken = null;
            var returnedFromCaptchaAction = false;
            try {
                approvalToken = localStorage.getItem(approvalStorageKey);
            } catch (error) {
                console.warn('Unable to read captcha approval token.', error);
            }

            try {
                returnedFromCaptchaAction =
                    sessionStorage.getItem(actionReturnStorageKey) === '1';
                sessionStorage.removeItem(actionReturnStorageKey);
            } catch (error) {
                console.warn('Unable to read captcha return marker.', error);
            }
            try {
                returnedFromCaptchaAction = returnedFromCaptchaAction ||
                    new URL(document.referrer).pathname.replace(/\/+$/, '').endsWith('/captcha/refer.shtml');
            } catch (error) {
                console.warn('Unable to determine captcha return location.', error);
            }

            if (approvalToken && !returnedFromCaptchaAction) {
                submitForm({
                    referer: referer,
                    approvalToken: approvalToken
                });
                return;
            }
            if (approvalToken && returnedFromCaptchaAction) {
                return;
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
<body></body>
</html>
