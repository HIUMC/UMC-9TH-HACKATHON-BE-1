package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.entity;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.category.entity.Category;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity.Member;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.transaction.enums.TransactionType;
import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "memo", length = 50)
    private String memo;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
