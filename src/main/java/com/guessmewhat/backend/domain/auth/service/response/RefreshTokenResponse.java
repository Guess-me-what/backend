package com.guessmewhat.backend.domain.auth.service.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record RefreshTokenResponse (

    String accessToken

){}
