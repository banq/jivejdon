<%@ page contentType="text/javascript; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page session="false" %>
<logic:forward name="imageShow" />
<%
//response.sendRedirect(request.getContextPath() + "/img/" + request.getParameter("id") + "/" + request.getParameter("type"));
response.sendRedirect( "/img/" + request.getParameter("id") + "/" + request.getParameter("type"));
%>
