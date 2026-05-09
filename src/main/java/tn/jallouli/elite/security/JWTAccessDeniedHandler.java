package tn.jallouli.elite.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tn.jallouli.elite.response.HttpResponseUtil;

import java.io.IOException;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        HttpResponseUtil.sendErrorResponse(request, response, HttpStatus.FORBIDDEN.value(), "Forbidden", "You do not have permission to access this resource");
    }
}