package org.ebudoskyi.houserent.service;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTCookiesService {
    @Value("${security.jwt.cookie.name:jwt}")
    private String cookieName;

    @Value("${security.jwt.cookie.max-age:3600}")
    private int maxAge;


    public Cookie createCookie(String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    public Cookie clearCookie() {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // expire immediately
        return cookie;
    }
}
