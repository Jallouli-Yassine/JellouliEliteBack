package tn.jallouli.elite.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tn.jallouli.elite.response.HttpResponseUtil;

import java.io.IOException;

//error handling eli baad bch nhotouh fl parti security config
@Component
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        HttpResponseUtil.sendErrorResponse(request, response, HttpStatus.UNAUTHORIZED.value(), "Unauthorized", authException.getMessage());
    }
}
