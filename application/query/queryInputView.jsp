<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" media="all" href="/common/js/calendar-win2k-cold-1.css" title="win2k-cold-1"/>
<script type="text/javascript" src="/common/js/calendar.js"></script>
<bean:parameter name="query" id="query" value=""/>

<html:form action="/query/threadViewQuery.shtml" method="post" onsubmit="return loadWLJSWithP(this, checkPost);">
    <html:hidden name="queryForm" property="queryType" value="HOT2"/>
    <table class="table table-striped">
        <tbody>
        <tr>
            <td bgcolor="#ffffff" align="middle">

                在
                <html:select name="queryForm" property="forumId">
                    <html:option value="">所有道场</html:option>
                    <html:optionsCollection name="queryForm" property="forums"
                                            value="forumId" label="name"/>
                </html:select>查询
                <br/>
                从<html:text styleId="begin_date_b" name="queryForm" property='fromDate'
                            size="20" maxlength="20"
            /><img width="22" height="21" src="/images/show-calendar.gif"
                   onclick="return showCalendar('begin_date_b', 'y-m-d');">
                <br>到<html:text styleId="end_date_b" name="queryForm" property='toDate'
                                size="20" maxlength="20"
            /><img width="22" height="21" src="/images/show-calendar.gif"
                   onclick="return showCalendar('end_date_b', 'y-m-d');">
                <br>
                回复数不少于<input type="text" name="messageReplyCountWindow" size="4"
                             value="10"/>
                <html:submit value=" 查询热门主题" property="btnsearch"/>
            </td>
        </tr>
        </tbody>
    </table>

</html:form>
<br>
<html:form action="/query/threadViewQuery.shtml" method="post" onsubmit="return checkPost(this);">
    <html:hidden name="queryForm" property="queryType" value="messageQueryAction"/>
    <table class="table table-striped">
        <tbody>
        <tr>
            <td bgcolor="#ffffff" align="middle">

                在 <html:select name="queryForm" property="forumId">
                <option value="">所有道场</option>
                <html:optionsCollection name="queryForm" property="forums" value="forumId"
                                        label="name"/>
            </html:select>查询
                <br>用户名<html:text name="queryForm" property="username"/>
                <br>从<html:text styleId="begin_date_b2" name="queryForm"
                                property='fromDate' size="20" maxlength="20"
            /><img width="22" height="21" src="/images/show-calendar.gif"
                   onclick="return showCalendar('begin_date_b2', 'y-m-d');">
                <br>到<html:text styleId="end_date_b2" name="queryForm" property='toDate'
                                size="20" maxlength="20"
            /><img width="22" height="21" src="/images/show-calendar.gif"
                   onclick="return showCalendar('end_date_b2', 'y-m-d');">
                <br>
                <html:submit value=" 查询 " property="btnsearch" style="width:60"/>

            </td>
        </tr>
        </tbody>
    </table>

</html:form>

<p><br></p>


<script language="JavaScript" type="text/javascript">
    //loadWLJSWithP(theForm, checkPost)
    var checkPost = function (theForm) {
        if ((theForm.fromDate.value == "") || (theForm.toDate.value == "")) {
            Dialog.alert(" 请完整输入条件 起始日期 终止日期 和 用户名",
                {windowParameters: {className: "mac_os_x", width: 250, height: 200}, okLabel: "   确定  "});
            return false;
        }
        return true;
    }
</script>
</center>



