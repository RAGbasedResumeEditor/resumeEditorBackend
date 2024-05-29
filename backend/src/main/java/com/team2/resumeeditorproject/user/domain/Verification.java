package com.team2.resumeeditorproject.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="Verification")
@NoArgsConstructor
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vNum;
    private String email;
    private String code;
    private Date createdAt;
    private Date expiresAt;
    private Boolean verified;

    @Builder
    public Verification(Long vNum, String email, String code, Date createdAt, Date expiresAt, Boolean verified) {
        this.vNum = vNum;
        this.email = email;
        this.code = code;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.verified = verified;
    }
}
