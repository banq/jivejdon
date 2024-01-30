package com.jdon.jivejdon.presentation.action.admin;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.block.IPBanListManagerIF;
import com.jdon.util.UtilValidate;

public class BanIPAction extends Action {
	private final static Logger logger = LogManager.getLogger(BanIPAction.class);
	public final static String IPLIST = "ips";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String actionS = request.getParameter("action");
		if ((actionS != null) && (actionS.equalsIgnoreCase("delete"))) {
			delBanIpFromList(request);
		} else {//no delete,maybe list or add
			String ip = request.getParameter("ip");
			if (!UtilValidate.isEmpty(ip)) { // it is add IP to List
				addBanIp(request, ip);
			} 
		}
		Collection ips = getBanIpList(request);
		request.setAttribute(IPLIST, ips);// save request for jsp out
		return actionMapping.findForward("forward");
	}
	
	private Collection getBanIpList(HttpServletRequest request){
		logger.debug("enter getBanIpList");
		Collection ipsres =  new ArrayList();
		try {
			IPBanListManagerIF iPBanListManager = (IPBanListManagerIF) WebAppUtil.getComponentInstance("iPBanListManager", 
					this.servlet.getServletContext());
			ipsres = iPBanListManager.getAllBanIpList();
		} catch (Exception e) {
			logger.error(e.toString());
		}	
		return ipsres;
	}
	
	private void delBanIpFromList(HttpServletRequest request){
		logger.debug("enter delBanIpFromList");
		String[] ips = request.getParameterValues("ip");
		for (int i=0; i< ips.length; i++){
			delIP(request, ips[i]);	
		}
	}
	
	private void delIP(HttpServletRequest request, String ip){
		try {
			IPBanListManagerIF iPBanListManager = (IPBanListManagerIF) WebAppUtil.getComponentInstance("iPBanListManager", 
					this.servlet.getServletContext());
			iPBanListManager.deleteBannedIp(ip);
		} catch (Exception e) {
			logger.error(e.toString());
		}		
	}
	
	private void addBanIp(HttpServletRequest request, String ip){
		try {
			IPBanListManagerIF iPBanListManager = (IPBanListManagerIF) WebAppUtil.getComponentInstance("iPBanListManager", 
					this.servlet.getServletContext());
			iPBanListManager.addBannedIp(ip);
			
			logger.warn(ip + " was blocked");

		} catch (Exception e) {
			logger.error(e.toString());
		}
		
	}
	

}
