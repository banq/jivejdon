/*
 * Copyright 2003-2005 the original author or authors. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *  
 */
package com.jdon.jivejdon.presentation.form;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.jdon.util.StringUtil;



public class SkinUtils {
    private final static String module = SkinUtils.class.getName();
    public static String ProblemCODE = "ProblemAnswer";
    private static String RegisterCODE = "CODE";

    public static String getRegisterCode(HttpServletRequest request, HttpServletResponse response) {
        String registerCode = "1234";
        try {
            HttpSession session = request.getSession();
            registerCode = StringUtil.getPassword(4, "0123456789");
            session.setAttribute(RegisterCODE, registerCode);
        } catch (Exception ex) {
            System.err.println(" getRegisterCode error : " + registerCode + ": " + ex);
        }
        return registerCode;
    }

    public static boolean verifyQQMobileNumber(HttpServletRequest request,
                                               String registerCode){
                HttpSession session1 = request.getSession();
        String SMSCODE = (String)session1.getAttribute("SMSCODE");
        if (SMSCODE == null){
           return false;
        }
        if(registerCode.equals(SMSCODE)){
           return true;
        }
        return false;
    }

    public static boolean verifyQQRegisterCode(String registerCodeIn,
            String randstr, String userip) {
        boolean isTrue = false;
        try {
            String url = "https://ssl.captcha.qq" +
                    ".com/ticket/verify?aid=2050847547&AppSecretKey" +
                    "=0gRRG_bSpnphbFfz7q125mQ**&Randstr=" + randstr + "&UserIP" +
                    "=" + userip + "&Ticket=" + registerCodeIn;
            String json = loadJSON(url);
            JSONObject jsonObj = JSONObject.parseObject(json);
            String result = jsonObj.getString("response");
            if (result.equals("1"))
                isTrue = true;
            else
                System.err.println("QQRegisterCode=" + result + " ip=" + userip);
        } catch (Exception ex) {
            System.err.println(" QQverifyRegisterCode : " + ex);
        }

        return isTrue;
    }

    public static boolean verifyRegisterCode(String registerCodeIn,
                                             HttpServletRequest request) {
        boolean isTrue = false;
        String registerCode = "1234";
        try {
            HttpSession session = request.getSession();
            registerCode = (String) session.getAttribute(RegisterCODE);
            if ((registerCode != null) && (registerCodeIn != null) && (registerCodeIn.equalsIgnoreCase(registerCode)))
                isTrue = true;
        } catch (Exception ex) {
            System.err.println(" verifyRegisterCode : " + ex);
        }

        return isTrue;
    }

    public static boolean verifyProblemAnswer(int ans, HttpServletRequest request) {
        boolean isTrue = false;
        Integer registerCode = 0;
        try {
            HttpSession session = request.getSession();
            registerCode = (Integer) session.getAttribute(ProblemCODE);
            session.setAttribute(ProblemCODE, -1);
            if ((registerCode != null) && (registerCode == ans) && (registerCode != -1))
                isTrue = true;
        } catch (Exception ex) {
            System.err.println(" verifyProblemAnswer : " + ex);
        }

        return isTrue;
    }

    public static boolean saveProblemAnswer(int ans, HttpServletRequest request) {
        boolean isTrue = false;
        try {
            HttpSession session = request.getSession();
            Integer registerCode = (Integer) session.getAttribute(ProblemCODE);
            if (registerCode == null) {
                session.setAttribute(ProblemCODE, ans);
                isTrue = true;
            }
        } catch (Exception ex) {
            System.err.println(" saveProblemAnswer : " + ex);
        }
        return isTrue;
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(), "utf-8"));//防止乱码
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
}
