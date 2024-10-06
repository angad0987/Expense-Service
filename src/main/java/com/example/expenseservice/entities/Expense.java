package com.example.expenseservice.entities;

import java.util.UUID;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "expense_details")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "external_id")
    private String externalId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "amount")
    private String amount;
    @Column(name = "merchant")
    private String merchant;
    @Column(name = "currency")
    private String currency;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    @PreUpdate
    private void generateExternalId() {
        if (externalId == null) {
            this.externalId = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = new Timestamp(Instant.now().toEpochMilli());
        }
    }

}
