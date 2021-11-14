package com.Queirozq.onlinestore.service.external.session;

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface UserSessionClient {

    @RequestLine("GET /user-sessions/validate")
    UserSessionValidatorResponse validateSession(@QueryMap Map<String, Object> queryMap);


    default UserSessionValidatorResponse validateSession(UUID sessionId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("sessionId", sessionId.toString());
        return validateSession(queryMap);
    }
}
