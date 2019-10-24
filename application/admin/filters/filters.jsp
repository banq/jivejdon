<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 过滤器设置" />
<%@ include file="../header.jsp" %>
 
<p>过滤器动态的重新格式化消息内容。使用下面的表单安装和自定义过滤器。

</p>
<script language="JavaScript" type="text/javascript">
var filterInfo = new Array(
<logic:iterate indexId="i" id="descriptor" name="filtersForm" property="availableDescriptors" >

    new Array(    
    "<bean:write name="descriptor"  property="beanClass.name"/>",
    "<bean:write name="descriptor"  property="value(version)"/>",
    "<bean:write name="descriptor"  property="value(author)"/>",
    "<bean:write name="descriptor"  property="shortDescription"/>",
    "true"
    )
    <logic:greaterThan name="filtersForm" property="filtersSize" value="<%=Integer.toString(i.intValue()+1)%>" >
		,
    </logic:greaterThan>

</logic:iterate>
);

function properties(theForm) {
    var className = theForm.classname.options[theForm.classname.selectedIndex].value;
    var selected = 0;
    for (selected=0; selected<filterInfo.length; selected++) {
        if (filterInfo[selected][0] == className) {
            var version = filterInfo[selected][1];
            var author = filterInfo[selected][2];
            var description = filterInfo[selected][3];
            var cacheable = filterInfo[selected][4];
            theForm.version.value = ((version=="null")?"":version);
            theForm.author.value = ((author=="null")?"":author);
            theForm.description.value = ((description=="null")?"":description);
            if (cacheable == "true") {
                theForm.cacheable.value = "Yes";
            } else {
                theForm.cacheable.value = "No";
            }
            break;
        }
    }
}
</script>
<b>当前过滤器</b>
<p>
<logic:greaterThan name="filtersForm" property="filterCount" value="0" >
<ul>
	<table bgcolor="#999999" cellpadding="0" cellspacing="0" border="0">
    <tr><td>
    <table cellpadding="4" cellspacing="1" border="0" width="100%">
	<tr bgcolor="#FFFFCC">
	<td align="center"><b>顺序</b></td>
	<td align="center"><b>名称</b></td>
	<td align="center"><b>描述</b></td>
	<logic:greaterThan name="filtersForm" property="filterCount" value="1" >
      <td align="center"><b>移动</b></td>
    </logic:greaterThan>   
	<td align="center"><b>编辑</b></td>
	<td align="center"><b>删除</b></td>
    </tr>
    
    <logic:iterate indexId="i" id="descriptor" name="filtersForm" property="descriptors" type="java.beans.BeanDescriptor">
      <tr bgcolor="#ffffff">
        <td><%=Integer.toString(i.intValue()+1)%></td>
        <td bgcolor="#ffffff" nowrap><bean:write name="descriptor"  property="displayName"/></td>
        <td><bean:write name="descriptor"  property="shortDescription"/></td>
        <logic:greaterThan name="filtersForm" property="filterCount" value="1" >
        <td>
            <logic:greaterThan name="filtersForm" property="filterCount" value="<%=Integer.toString(i.intValue()+1)%>" >
               <html:link page="/admin/filters/filtersAction.shtml?method=changePos&down=true" paramId="filterIndex" paramName="i"
                ><img src="../images/arrow_down.gif" width="13" height="9" alt="将此过虑器下移" border="0">
                </html:link>
            </logic:greaterThan>  
             <logic:lessEqual name="filtersForm" property="filterCount" value="<%=Integer.toString(i.intValue()+1)%>" >
                <html:img page="/images/blank.gif" width="13" height="9" border="0" alt=""/>
            </logic:lessEqual>  
            
            <logic:notEqual name="i" value="0" >
               <html:link page="/admin/filters/filtersAction.shtml?method=changePos&up=true"  paramId="filterIndex" paramName="i"
                ><img src="../images/arrow_up.gif" width="13" height="9" alt="将此过滤器上移" border="0">
                </html:link>
            </logic:notEqual>
            <logic:equal name="i" value="0" >
                 <html:img page="/images/blank.gif" width="13" height="9" border="0" alt=""/>
            </logic:equal>

        </td>
        </logic:greaterThan>   
        <td align="center">
        <%
        com.jdon.jivejdon.presentation.form.FiltersForm filtersForm = (com.jdon.jivejdon.presentation.form.FiltersForm)request.getAttribute("filtersForm");        
        Integer propDesLength = new Integer((filtersForm.getPropertyDescriptors(descriptor)).length);
        request.setAttribute("propDesLength", propDesLength);
        %>
         <%-- this line happened error: No getter method for property propDesLength(java.beans.BeanDescriptor@e84763) of bean filtersForm 
        <bean:define id="propDesLength2" name="filtersForm" type="java.lang.Integer" property='<%="propDesLength("+ descriptor + ")" %>'/>
         --%>
        
        <logic:greaterThan name="propDesLength" value="0" >
            <html:link page="/admin/filters/filtersAction.shtml?method=edit"  paramId="pos" paramName="i"
            ><img src="../images/button_edit.gif" width="17" height="17" alt="编辑过滤器属性" border="0"
            ></html:link>
         </logic:greaterThan>
         <logic:lessThan name="propDesLength" value="0" >
            &nbsp;
         </logic:lessThan>     
        </td>
        <td align="center">
            <html:link page="/admin/filters/filtersAction.shtml?method=remove"  paramId="pos" paramName="i"
            ><img src="../images/button_delete.gif" width="17" height="17" alt="删除此过滤器" border="0"
            ></html:link>
        </td>
    </tr>   
    
    <!--  显示"编辑过滤器属性"页面 --> 
    <bean:parameter id="method" name="method" value="" />
    <logic:equal name="method" value="edit" >
    <logic:equal name="filtersForm" property="pos" value='<%=""+i+""%>' >

    <html:form  action="/admin/filters/filtersAction.shtml">
    <input type="hidden" name="method" value="saveProperties">
    <input type="hidden" name="filterIndex" value="<%= i %>">
    <tr bgcolor="#ffffff">
        <td>&nbsp;</td>
        <bean:define id="filterCount" name="filtersForm" property="filterCount"  type="java.lang.Integer"/>
        <%
          String cols = (filterCount.intValue() > 1)?"5":"4"; 
        %>
        <td colspan="<%=cols%>" >
            <table cellpadding="2" cellspacing="0" border="0" width="100%">
            
         <% int color = 0;      
        java.beans.PropertyDescriptor[] propertyDescriptors = filtersForm.getPropertyDescriptors(descriptor);
        request.setAttribute("propertyDescriptors", propertyDescriptors);
         %>
          <logic:iterate id="propertyDescriptor" name="propertyDescriptors" type="java.beans.PropertyDescriptor" >
            <tr bgcolor=<%= (color++%2==0)?"#f4f5f7":"#ffffff" %>>
                <td width="60%">
                <bean:write name="propertyDescriptor" property="displayName"/>
                <br>
                <i><bean:write name="propertyDescriptor" property="shortDescription"/></i>
                </td>
                <td width="20%">&nbsp;</td>
                <td width="20%" nowrap>
                     
                     <%=com.jdon.jivejdon.domain.model.message.output.beanutil.FiltersUtils.getPropertyHTML(filtersForm.getFiltersIndexed(i),  propertyDescriptor ) %>
                </td>
            </tr>
         </logic:iterate>
            <tr>
                <td colspan="4" align="right">
                    <input type="submit" value="Save Properties">
                </td>
            </tr>
            </table>
        </td>
    </tr>
    </html:form >
    </logic:equal>
    </logic:equal>
    </logic:iterate>
    <!--  显示"编辑过滤器属性"页面 结束-->     
    
</logic:greaterThan>
 </table>
    </td></tr>
    </table>
   
</ul>    
    

<p>

<html:form action="/admin/filters/filtersAction.shtml">
<input type="hidden" name="method" value="install">
<span>
<b>安装过滤器</b>
</span>
<ul>
	<table bgcolor="#999999" cellpadding="0" cellspacing="0" border="0" width="1%">
    <tr><td>
	<table cellpadding="4" cellspacing="1" border="0" width="100%">
	<tr bgcolor="#FFFFCC">
	<td align="center"><b>可用过滤器</b></td>
    </tr>
	<tr bgcolor="#ffffff">
	<td>
        <table cellpadding="1" cellspacing="0" border="0">
        <tr>
            <td width="48%" valign="top">
            <select size="8" name="classname" onchange="properties(this.form);"">
            
            <logic:iterate id="undescriptor" name="filtersForm" property="unInstalledDescriptors" type="java.beans.BeanDescriptor">
             <option value="<%= undescriptor.getBeanClass().getName() %>"><%= undescriptor.getDisplayName() %>
            </logic:iterate>

            </select>
            </td>
            <td width="2%"> <html:img page="/images/blank.gif" width="5" height="1" border="0" alt=""/></td>
            <td width="48%" valign="top">
                <table cellpadding="2" cellspacing="0" border="0" width="100%">
                <tr>
                    <td>版本</td>
                    <td><input type="text" size="20" name="version" style="width:100%"></td>
                </tr>
                <tr>
                    <td>作者</td>
                    <td><input type="text" size="20" name="author" style="width:100%"></td>
                </tr>
                <tr>
                    <td valign="top">缓存性</td>
                    <td><input type="text" size="3" name="cacheable" value=''></td>
                </tr>
                <tr>
                    <td valign="top">描述</td>
                    <td><textarea name="description" cols="20" rows="5" wrap="virtual"></textarea></td>
                </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="3" align="center">
            <input type="submit" value="安装...">
            </td>
        </tr>
        </table>
    </td>
    </tr>
    </table>
    </td></tr>
    </table>
</ul>
</html:form>

<p>

<html:form action="/admin/filters/filtersAction.shtml">
<input type="hidden" name="method" value="addFilter">

<span>
<b>添加过虑器</b>
</span>
<ul>
    <table cellpadding="2" cellspacing="0" border="0">
    <tr>
    	<td>类名:</td>
    	<td><input type="text" name="newClassname" value="" size="30" maxlength="100"></td>
    	<td><input type="submit" value="添加过虑器"></td>
    </tr>
    </table>
</ul>
</html:form>
    

<%@include file="../footer.jsp"%> 
