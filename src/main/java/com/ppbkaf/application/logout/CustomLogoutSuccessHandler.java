package com.ppbkaf.application.logout;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
