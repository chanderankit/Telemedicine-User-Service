package com.telemedicine.user.retrofit.notifications;

import com.telemedicine.user.enums.ErrorCodes;
import com.telemedicine.user.exception.BusinessException;
import com.telemedicine.user.model.payload.request.NotificationRequest;
import com.telemedicine.user.model.payload.response.NotificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;

@Component
@Slf4j
public class NotificationGateway {
    @Autowired
    private NotificationClient notificationClient;
    public NotificationResponse sendNotification(NotificationRequest request, String type) throws IOException {
        Response<NotificationResponse> response;
        try {
            log.info("-------------NotificationGateway:sendNotification::sending mail to -> {}---", request.getTo());
            response = notificationClient.sendNotification(request, type).execute();
        } catch (Exception e) {
            log.error("-------------NotificationGateway:sendNotification--------");
            throw new BusinessException(ErrorCodes.CONNECTION_FAILED, HttpStatus.BAD_REQUEST);
        }

        if (response.isSuccessful()) {
            return response.body();
        }
        log.error("-------------NotificationGateway:sendNotification--------");
        throw new BusinessException(ErrorCodes.CONNECTION_FAILED, HttpStatus.BAD_REQUEST);
    }

}
