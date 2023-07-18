package com.telemedicine.user.retrofit.authentication;

import com.telemedicine.user.enums.ErrorCodes;
import com.telemedicine.user.exception.BusinessException;
import com.telemedicine.user.model.payload.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;

@Component
@Slf4j
public class AuthGateway {
    @Autowired
    private AuthClient authClient;
    public Token generateToken(Token request) throws IOException {
        Response<Token> response;
        try {
            log.info("-------------AuthGateway:generateToken: generating token for user -> {}-with role-- > {}-----", request.getUserId(), request.getRole());
            response = authClient.generateToken(request).blockingGet();
        } catch (Exception e) {
            throw new BusinessException(ErrorCodes.CONNECTION_FAILED, HttpStatus.BAD_REQUEST);
        }

        if (response.isSuccessful()) {
            return response.body();
        }
        throw new BusinessException(ErrorCodes.CONNECTION_FAILED, HttpStatus.BAD_REQUEST);
    }
}
