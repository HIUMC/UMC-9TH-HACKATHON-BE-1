package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.repository;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT t FROM Transaction t JOIN FETCH t.category " +
            "WHERE t.member = :member AND t.transactionDate BETWEEN :start AND :end")
    List<Transaction> findAllByMemberAndDateRange(
            @Param("member") Member member,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );


}
