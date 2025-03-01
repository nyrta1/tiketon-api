package com.wcod.tiketondemo.data.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wcod.tiketondemo.data.models.UserEntity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("refresh_expires_in")
    private String refreshExpiresIn;

    @JsonProperty("token_type")
    private static final String TOKEN_TYPE = "Bearer";

    @JsonProperty("token_type")
    private String tokenType = TOKEN_TYPE;

    @JsonProperty("user")
    private UserEntity user;
}
