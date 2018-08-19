package com.jdon.jivejdon.presentation.form.feed;

import com.jdon.jivejdon.presentation.form.SkinUtils;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class QQDemo02 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String userip = "222.211.206.246";
        String randstr = "@Avw";
        String registerCodeIn =
                "t02z3OPGm0cKpq39Ro6XAe5lTlqErkemmI6ZpydPcLGtDoKZmR6EtVZuaz9uidoOlFLgBcQZz38FPRnkvEfhaKME2RkPSiPZVSaJ4ARoR9DRxXdgxq1ieSULg**";
        if (!SkinUtils.verifyQQRegisterCode(registerCodeIn, randstr,
                userip))
            System.out.println("error=");
        else
            System.out.println("ok=");
//        String url = "https://ssl.captcha.qq" +
//                ".com/ticket/verify?aid=2050847547&AppSecretKey" +
//                "=0gRRG_bSpnphbFfz7q125mQ**&Randstr=" + randstr + "&UserIP" +
//                "=" + userip + "&Ticket=" + registerCodeIn;
//        String json = loadJSON(url);
//        System.out.println(json);
//        JSONObject jsonObj=JSONObject.fromObject(json);
//        System.out.println("response="+jsonObj.getString("response"));
    }

    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(),"utf-8"));//防止乱码
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
