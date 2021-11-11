package com.Queirozq.onlinestore.service.external.session;

import feign.Param;
import feign.RequestLine;

public interface UserSessionClient {

    @RequestLine("GET /user-sessions/validate?sessionId={sessionId}")
    UserSessionValidatorResponse validateSession(@Param("sessionId") String uuid);
}
