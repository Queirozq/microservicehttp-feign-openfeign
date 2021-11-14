package com.Queirozq.onlinestore.service.api;

import com.Queirozq.onlinestore.service.external.session.UserSessionClient;
import com.Queirozq.onlinestore.service.external.session.UserSessionValidatorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SessionValidationFilter implements Filter {

    private final UserSessionClient userSessionClient;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String sessionIdHeader = httpServletRequest.getHeader("X-Session-Id");
        if (sessionIdHeader == null) {
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value());
        } else {
            UUID sessionId = UUID.fromString(sessionIdHeader);
            UserSessionValidatorResponse userSessionValidatorResponse = userSessionClient.validateSession(sessionId);
            if (!userSessionValidatorResponse.isValid()) {
                httpServletResponse.sendError(HttpStatus.FORBIDDEN.value());
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}
