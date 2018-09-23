<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.github.qcloudsms.*"%>
<%@ page import="java.util.UUID" %>
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.ErrorBlockerIF,com.jdon.jivejdon.manager.email.*"%>
<%@ page import="java.util.ArrayList" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

    ErrorBlockerIF errorBlocker = (ErrorBlockerIF)
            WebAppUtil.getComponentInstance("errorBlocker",
                    this.getServletConfig().getServletContext());
    if (errorBlocker.checkRate(request.getRemoteAddr(), 3)){
        return;
    }

    String phoneNumber = request.getParameter("phoneNumber");
    if (phoneNumber ==null || phoneNumber.length()==0)
        return;

    int appid = 1400107174;
    String appkey = "f36b5851352d7ae5032451f8854fba43";
    String phoneNumbers[] = new String[]{phoneNumber};
	// 短信模板ID，需要在短信应用中申请
    int templateId = 150191; 
	// NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
// 签名
    String smsSign = "解道软件"; 
    HttpSession session1 = request.getSession();
    String SMSCODE = (String)session1.getAttribute("SMSCODE");
    if (SMSCODE == null){
       return;
    }
    ArrayList<String> params = new ArrayList<String>();
    params.add(SMSCODE);
    try {
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		SmsSingleSenderResult result = ssender.sendWithParam("86",
                phoneNumbers[0], templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
		out.print("发送完成");
        System.out.print("发送完成" + phoneNumbers[0] + " 验证码：" + SMSCODE);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

