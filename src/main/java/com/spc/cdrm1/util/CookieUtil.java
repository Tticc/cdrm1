package com.spc.cdrm1.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/**
	 * 设置不会过期的cookie
	 * @author Wen, Changying
	 * @param res
	 * @param name
	 * @param value
	 * @date 2019年7月22日
	 */
	public static void setCookie(HttpServletResponse res, String name, String value) {
		setCookie(res,name,value,-1);
	}
	/**
	 * 设置 cookie
	 * @author Wen, Changying
	 * @param res
	 * @param name
	 * @param value
	 * @param maxAge
	 * @date 2019年7月20日
	 */
	public static void setCookie(HttpServletResponse res, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setDomain("localhost");
        res.addCookie(cookie);
	}
	
	/**
	 * 根据 name 获取 cookie
	 * @author Wen, Changying
	 * @param req
	 * @param name
	 * @return
	 * @date 2019年7月20日
	 */
	public static Cookie getCookie(HttpServletRequest req, String name) {
		 Map<String, Cookie> cookieMap = getCookieMap(req);
		 if(cookieMap.containsKey(name)) {
			 return cookieMap.get(name);
		 }
		 return null;
	}
	
	/**
	 * 将cookie封装成Map
	 * @author Wen, Changying
	 * @param req
	 * @return
	 * @date 2019年7月20日
	 */
	private static Map<String, Cookie> getCookieMap(HttpServletRequest req) {
		Map<String, Cookie> cookieMap = new HashMap<>();
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			int len = cookies.length;
            for (int i = 0; i < len; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
		return cookieMap;
	}
}
