package com.Queirozq.onlinestore.service.external.session;

import lombok.Data;

@Data
public class UserSessionValidatorResponse {
    private boolean isValid;
    private String sessionId;
}
