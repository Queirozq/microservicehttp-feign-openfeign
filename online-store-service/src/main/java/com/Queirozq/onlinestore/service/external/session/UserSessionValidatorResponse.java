package com.Queirozq.onlinestore.service.external.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionValidatorResponse {
    private boolean isValid;
    private String sessionId;
}
