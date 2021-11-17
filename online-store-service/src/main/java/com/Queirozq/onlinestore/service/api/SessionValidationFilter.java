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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
            CompletableFuture<UserSessionValidatorResponse> responseFuture = userSessionClient.validateSession(sessionId);
            // Coisas doidas aqui
            UserSessionValidatorResponse userSessionValidatorResponse = null;
            try {
                userSessionValidatorResponse = responseFuture.get();
            } catch (Exception e) {
                throw new RuntimeException("Exception while validating the session", e);
            }
            if (!userSessionValidatorResponse.isValid()) {
                httpServletResponse.sendError(HttpStatus.FORBIDDEN.value());
            } else {
                chain.doFilter(request, response);
            }
        }
    }
}
