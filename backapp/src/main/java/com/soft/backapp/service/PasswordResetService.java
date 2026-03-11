package com.soft.backapp.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.soft.backapp.model.PasswordReset;
import com.soft.backapp.repository.PasswordResetRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetRepository passwordResetRepository;

    public void savePasswordReset(PasswordReset passwordReset){
        passwordResetRepository.deleteByUserEmail(passwordReset.getUserEmail());
        passwordResetRepository.save(passwordReset);
    }

    public void deletePasswordReset(Long id){
        passwordResetRepository.deleteById(id);
    }

    public List<Long> getDeletableIds(Date curDate){
        return passwordResetRepository.findDeletableIds(curDate);
    }

    public Optional<PasswordReset> getByEmail(String email){
        return passwordResetRepository.findByUserEmail(email);
    }
}
