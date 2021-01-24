<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="content-language" content="zh-CN"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta name="robots" content="noindex">
        <title>
            论坛使用指南
        </title>
        <link rel="stylesheet" href="/common/jivejdon5.css" type="text/css"/>
        <body>


            <table border="0" cellpadding="0" cellspacing="0" width="500" align='center'>
                <tr>
                    <td>


                        <p>本道场社区融合了博客和微博等功能：
                        </p>
                        <p>点按标题可进入原文；发帖时内容第一行如有原文http://开头的网址，那么该网址将作为帖子标题的链接。</p>
                        <p>发帖：请在开始的一行内请扼要描述本帖主题。</p>
                        <p>关注：<a href="http://www.jdon.com/followus.html" target="_blank">
                                <b>按此进入关注详细介绍
                                </b>
                            </a>
                        </p>
                        <p>分享：将按钮<a href="javascript:(function(){window.open('http://www.jdon.com/<%=request.getContextPath() %>/importUrl.jsp?Referer=http://www.jdon.com&subject='+encodeURIComponent(document.title)+'&url='+encodeURIComponent(location.href),'_blank','width=580,height=310');})()" alt="享道！">
                                <img style="cursor:move" src="images/share.gif" alt="享道" border="0" width="50" height="16"/></a>拖到你浏览器(Chrome Firefox)的书签栏，
                                                                      通过此按钮将你看到的好文章分享转贴到本道场。
                        </p>
                        <p>标签功能：发表新主题文章时，会出现标签输入，如果已经存在，会自动弹出可选标签，最好选择已经存在的标签，否则就不要输入标签，由管理员来补充。</p>
                        <p>支持新浪微博直接登入，用户名和密码自动生成，如果你修改了用户名，下次只能用该用户名登录，如果再次用微博等帐号登入，原来用户名密码将又被系统重新生成。</p>
                        <p>社交媒体：</p>
                        <p>
                            <a href="http://weibo.com/ijdon" target="_blank">新浪微博：@解道jdon weibo.com/ijdon</a>
                        </p>
                        <p>
                            <a href="https://twitter.com/jdon_com" target="_blank">twitter：@解道jdon twitter.com/jdon_com</a>
                        </p>
                        <p>
                            <a href="https://www.facebook.com/jivejdon" target="_blank">facebook (www.facebook.com/jivejdon)</a>
                        </p>

                        <p></p>
                    </td>
                </tr>
            </table>
        </body>
    </head>
</html>
