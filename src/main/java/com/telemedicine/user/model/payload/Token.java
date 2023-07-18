package com.telemedicine.user.model.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Token {
    @NotNull(message = "userId is required for token generation and validation", groups = {TokenGeneration.class, TokenValidation.class})
    private String userId;
    @NotNull(message = "roles are required", groups = TokenGeneration.class)
    private String role;
    @NotNull(message = "accessToken is required", groups = TokenValidation.class)
    private String accessToken;
    @NotNull(message = "userAgent is required for token generation and validation", groups = TokenGeneration.class)
    private String userAgent;
    public Token(String accessToken) {
        this.accessToken = accessToken;
    }
    public interface TokenGeneration {
    }
    public interface TokenValidation {
    }
}
