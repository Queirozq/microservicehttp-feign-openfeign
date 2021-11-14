package com.Queirozq.onlinestore.service.external.session;

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface UserSessionClient {

    @RequestLine("GET /user-sessions/validate")
    UserSessionValidatorResponse validateSession(@QueryMap ValidateSessionRequest validateSessionRequest);


    default UserSessionValidatorResponse validateSession(UUID sessionId) {
        return validateSession(new ValidateSessionRequest(sessionId.toString()));
    }
}
