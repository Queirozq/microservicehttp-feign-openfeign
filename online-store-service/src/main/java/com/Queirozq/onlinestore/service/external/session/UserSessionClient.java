package com.Queirozq.onlinestore.service.external.session;

import com.Queirozq.onlinestore.service.external.BaseClient;
import feign.HeaderMap;
import feign.QueryMap;
import feign.RequestLine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface UserSessionClient extends BaseClient {

    @RequestLine("GET /user-sessions/validate")
    UserSessionValidatorResponse validateSession(@QueryMap ValidateSessionRequest validateSessionRequest,
                                                 @HeaderMap Map<String, Object> headerMap);


    default UserSessionValidatorResponse validateSession(UUID sessionId) {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("X-Source", "validateSession(UUID)");
        return validateSession(new ValidateSessionRequest(sessionId.toString()), headerMap);
    }
}
