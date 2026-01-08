package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.repository;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    void deleteById(Long id);
}
