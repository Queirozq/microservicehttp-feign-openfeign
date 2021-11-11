package com.Queirozq.usersession.service;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserSessionValidatorResponse {

    private boolean valid;
    private UUID sessionId;
}
