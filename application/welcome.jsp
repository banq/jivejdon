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

        function showCaptcha() {
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

            var cacheCheckUrl = '/captcha/refer.shtml?checkIpCache=1&referer=' +
                encodeURIComponent(referer);
            fetch(cacheCheckUrl, { credentials: 'same-origin', cache: 'no-store' })
                .then(function(response) {
                    if (!response.ok) {
                        throw new Error('IP cache check failed with status ' + response.status);
                    }
                    return response.json();
                })
                .then(function(result) {
                    if (result.cached) {
                        submitForm({
                            referer: referer,
                            ipCacheApproval: '1'
                        });
                        return;
                    }
                    showCaptcha();
                })
                .catch(function(error) {
                    console.warn('Unable to check the IP view cache; requiring captcha.', error);
                    showCaptcha();
                });
        };
    </script>
</head>
<body></body>
</html>
