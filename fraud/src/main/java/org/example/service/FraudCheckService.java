package org.example.service;

import org.example.entity.FraudCheckHistory;
import org.example.repository.FraudCheckHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class FraudCheckService {

    @Autowired
    FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public boolean isFraudlentCustomer(Integer customerId){
        if (fraudCheckHistoryRepository.findById(customerId).isPresent()) {
            return true;
        }

        fraudCheckHistoryRepository.save(FraudCheckHistory.builder()
                .id(customerId)
                .isFraudster(true)
                .createdAt(LocalDateTime.now())
                .build());
        return false;
    }
}
