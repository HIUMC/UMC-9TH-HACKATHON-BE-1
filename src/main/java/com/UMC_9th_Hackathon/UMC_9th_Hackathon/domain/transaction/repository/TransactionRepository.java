package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.repository;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByIdAndMember(Long id, Member member);

    List<Transaction> findAllByTransactionDateAndMember(LocalDate date, Member member);

    List<Transaction> findAllByTransactionDateBetweenAndMember(LocalDate startDate, LocalDate endDate, Member member);

    void deleteAllByMember(Member member);
}
