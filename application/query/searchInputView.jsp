<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notPresent name="query">
    <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table class="table table-striped">
    <tbody>
    <tr>
        <td align="middle">
          <html:form action="/message/searchAction.shtml" method="post"
                     styleClass="search">
                <input type="text" name="query"
                       value="<bean:write name="query"/>" id="queryId" size="40"/>
                <html:submit value="道场搜索"/>
            </html:form>
        </td>
    </tr>
    </tbody>
</table>

<table class="table table-striped">
    <tbody>
    <tr><td align="middle">
        <form method="get" action="https://www.baidu.com/s" class="search">
            <input type="hidden" name="ct" value="2097152">
            <input type="hidden" name="si" value="www.jdon.com">
            <input type="text" name="wd" value="<bean:write name="query"/>" size="40">
            <input type="submit" value="百度道场">
        </form>
    </td></tr>
    </tbody>
</table>
