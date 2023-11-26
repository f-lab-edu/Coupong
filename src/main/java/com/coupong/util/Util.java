package com.coupong.util;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class Util {
    public static String generateRid(){
        String[] uuid = UUID.randomUUID().toString().split("-");
        return String.join("", uuid);
    }

    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }
}
