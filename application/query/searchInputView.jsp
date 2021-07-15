<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notPresent name="query">
    <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table class="table table-striped">
    <tbody>
    <tr>
        <td align="middle">
          <html:form action="/query/searchAction.shtml" method="get"
                     styleClass="search">
                <input type="text" name="query"
                       value="<bean:write name="query"/>" id="queryId" size="40"/>
                <html:submit value="道场搜索"/>
            </html:form>
            <p><jsp:include page="/query/tagHotList.shtml" flush="true"></jsp:include>
        </td>
    </tr>
    </tbody>
</table>

<table class="table table-striped">
    <tbody>
    <tr><td align="middle">
        <form action="https://www.baidu.com/baidu">
            <input type=text name=word  value="<bean:write name="query"/>" size="40">
            <input type="submit" value="百度本道场">
            <input name=tn type=hidden value="bds">
            <input name=cl type=hidden value="3">
            <input name=ct type=hidden value="2097152">
            <input name=si type=hidden value="www.jdon.com">
            </form>
    </td></tr>
    </tbody>
</table>



