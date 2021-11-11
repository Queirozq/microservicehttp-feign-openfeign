package com.Queirozq.onlinestore.service.external.session;

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface UserSessionClient {

    @RequestLine("GET /user-sessions/validate")
    UserSessionValidatorResponse validateSession(@QueryMap Map<String, Object> queryMap);
}
