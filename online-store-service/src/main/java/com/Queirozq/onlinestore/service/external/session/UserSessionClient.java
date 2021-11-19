package com.Queirozq.onlinestore.service.external.session;

import com.Queirozq.onlinestore.service.external.BaseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "user-session-service", fallbackFactory = UserSessionClientFallbackFactory.class)
public interface UserSessionClient extends BaseClient {

    @GetMapping("/user-sessions/validate")
    UserSessionValidatorResponse validateSession(@RequestParam Map<String, Object> queryMap,
                                      @RequestHeader Map<String, Object> headerMap);


    default UserSessionValidatorResponse validateSession(UUID sessionId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("sessionId", sessionId.toString());
        Map<String, Object> headerMap = new HashMap<>();
        return validateSession(queryMap, headerMap);
    }
}
