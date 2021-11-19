package com.Queirozq.onlinestore.service.external.session;

import com.Queirozq.onlinestore.service.external.ActuatorHealthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class UserSessionClientFallbackFactory implements FallbackFactory<UserSessionClient> {
    @Override
    public UserSessionClient create(Throwable cause) {
        return new UserSessionClient() {
            @Override
            public UserSessionValidatorResponse validateSession(Map<String, Object> queryMap, Map<String, Object> headerMap) {
                log.info("[Fallback] validateSession");
                String sessionId = (String) queryMap.get("sessionId");
                return new UserSessionValidatorResponse(false, sessionId);
            }

            @Override
            public ActuatorHealthResponse health() {
                return new ActuatorHealthResponse("Down");
            }
        };
    }
}
