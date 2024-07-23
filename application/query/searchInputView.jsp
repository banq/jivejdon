<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notPresent name="query">
    <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table class="table table-striped">
    <tbody>
    <tr>
        <td align="middle">
          <html:form action="/query/search.shtml" method="get"
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
        <form action="https://www.baidu.com/s" class="search" method="get">
            <input type=text name=wd  value="<bean:write name="query"/>  site:jdon.com" size="40">
            <input type="submit" value="百度本道场"> 
            </form>
    </td></tr>
    </tbody>
</table>



