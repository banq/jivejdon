package com.jdon.jivejdon.presentation.action.message;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.message.AnemicMessageDTO;
import com.jdon.jivejdon.presentation.form.MessageForm;
import com.jdon.jivejdon.service.ForumMessageService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageEditViewAction extends Action {

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        MessageForm messageForm = (MessageForm)actionForm;
        boolean isAuth  = messageForm.isAuthenticated();
        ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
                .getService("forumMessageService", this.servlet.getServletContext());
        AnemicMessageDTO anemicMessageDTO = forumMessageService.findMessage(messageForm.getMessageId().longValue());
        try {
            PropertyUtils.copyProperties(messageForm, anemicMessageDTO);
            messageForm.setAuthenticated(isAuth);

        } catch (Exception e) {
            throw new Exception(e);
        }
        return actionMapping.findForward(messageForm.getAction());
    }
}
