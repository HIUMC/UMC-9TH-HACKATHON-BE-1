package com.UMC_9th_Hackathon.UMC_9th_Hackathon.domain.member.entity;

import com.UMC_9th_Hackathon.UMC_9th_Hackathon.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "member",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nickname"})
)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false, length = 12)
    private String nickname;

    @Column(name = "passwordHash")
    private String passwordHash;
}
