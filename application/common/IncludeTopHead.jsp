<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><logic:notEmpty  name="title"><bean:write name="title" /></logic:notEmpty> - 极道</title>
<link rel="stylesheet" href="/js/jdon.css">
<style>
    html { font-size: 62.5%; -webkit-text-size-adjust: 100%; }
    body { margin: 0; padding: 0; font-family: "Microsoft YaHei", sans-serif; font-size: 1.6rem; color: #333; background-color: #E6E6E6; line-height: 2.2rem; }
    .container { margin: 0 auto; padding: 0 15px; }
    .row { margin: 0 -15px; }
    .row:after, .container:after { content: " "; display: table; clear: both; }
    .col-lg-12, .col-lg-8, .col-lg-4 { position: relative; min-height: 1px; padding: 0 15px; }
    @media (min-width: 768px) { .container { width: 750px; } }
    @media (min-width: 992px) { .container { width: 970px; } }
    @media (min-width: 1200px) {
        .container { width: 128rem; }
        .col-lg-8 { float: left; width: 66.66666667%; }
        .col-lg-4 { float: left; width: 33.33333333%; }
        .col-lg-12 { float: left; width: 100%; }
    }
    .navbar { min-height: 50px; margin-bottom: 20px; background-color: #000; }
</style> 
<link rel="preload" href="/js/fonts/icomoon.woff" as="font" type="font/woff" crossorigin>
<link rel="preload" href="/js/jquery-bootstrap2.js" as="script">
<script defer src="/js/jquery-bootstrap2.js"></script> 
