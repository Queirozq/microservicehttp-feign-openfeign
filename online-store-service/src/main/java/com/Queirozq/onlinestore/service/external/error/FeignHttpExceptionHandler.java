package com.Queirozq.onlinestore.service.external.error;

import feign.Response;

public interface FeignHttpExceptionHandler {
    Exception handle(Response response);
}
