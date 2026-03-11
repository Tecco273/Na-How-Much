package com.soft.backapp.scheduler;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.soft.backapp.service.PasswordResetService;

import lombok.RequiredArgsConstructor;

@Component
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class PasswordResetCleanUp {
    @Autowired
    private final PasswordResetService passwordResetService;
    private static final int EXPIRATION_MINUTES = 10;

    @Scheduled(fixedRate = 60000)
    public void deleteExpiredCodes(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -EXPIRATION_MINUTES);
        List<Long> ids = passwordResetService.getDeletableIds(calendar.getTime());
        for (Long id : ids) {
            passwordResetService.deletePasswordReset(id);
        }{

        }
    }

}
