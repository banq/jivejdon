package com.jdon.jivejdon.presentation.action.account;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.block.ErrorBlockerIF;

public class SmsQQAction extends Action {
	private final static Logger logger = LogManager.getLogger(SmsQQAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest
			request, HttpServletResponse response) throws Exception {

		ErrorBlockerIF errorBlocker = (ErrorBlockerIF)
				WebAppUtil.getComponentInstance("errorBlocker", this.servlet.getServletContext());
		if (errorBlocker.checkRate(request.getRemoteAddr(), 10)) {
			logger.error("SmsQQAction errorBlocker:" + request.getRemoteAddr());
			return null;
		}

		String sessionId = request.getParameter("sessionId");
		if (sessionId == null || !sessionId.equals(request.getSession().getId())){
			errorBlocker.checkRate(request.getRemoteAddr(), 0);
			logger.error("SmsQQAction sessionId:" + request.getRemoteAddr());
			return null;
		}

		String randstr = (String)request.getSession().getAttribute("randstr");
		if(randstr == null){
			errorBlocker.checkRate(request.getRemoteAddr(), 0);
			logger.error("SmsQQAction randstr:" + request.getRemoteAddr());
			return null;
		}


		String phoneNumber = request.getParameter("phoneNumber");
		if (phoneNumber == null || phoneNumber.length() == 0 || !StringUtils.isNumeric
				(phoneNumber)) {
			logger.error("SmsQQAction phoneNumber is null:" + phoneNumber);
			return null;
		}


		int appid = 1400107174;
		String appkey = "f36b5851352d7ae5032451f8854fba43";
		String phoneNumbers[] = new String[]{phoneNumber};
		// 短信模板ID，需要在短信应用中申请
		int templateId = 150191;
		// NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
// 签名
		String smsSign = "极道软件";
		HttpSession session1 = request.getSession();
		String SMSCODE = (String) session1.getAttribute("SMSCODE");
		if (SMSCODE == null) {
			logger.error("SMSCODE is null:");
			return null;
		}

		ArrayList<String> params = new ArrayList<String>();
		params.add(SMSCODE);
		try {
			SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult result = ssender.sendWithParam("86",
					phoneNumbers[0], templateId, params, smsSign, "", "");  //
			// 签名参数未提供或者为空时，会使用默认签名发送短信
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("发送完成");
			response.getWriter().close();

			logger.error("发送完成" + phoneNumbers[0] + " 验证码：" + SMSCODE);
		} catch (Exception e) {
			logger.error("sms send error:" + e);
		}
		return null;
	}
}
