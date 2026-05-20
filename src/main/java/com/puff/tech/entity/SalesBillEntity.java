package com.puff.tech.entity;

import io.micronaut.core.annotation.Generated;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Serdeable
@Introspected
@MappedEntity(value = "SalesBills")
public class SalesBillEntity {
    @Id
    @Generated
    private Integer id;
    // -------- KhataBook Foreign Key --------
    private Integer khataBookId;
    @Relation(Relation.Kind.MANY_TO_ONE)
    private KhataBookEntity khataBook;

    // -------- SalesBill Info --------
    @Size(max = 100)
    private String billNumber;
    private LocalDate billDate;
    private Integer customerId;

    @Relation(Relation.Kind.MANY_TO_ONE)
    private CustomerEntity customer;

    @Size(max = 10)
    private String paymentMode; // cash, card, upi, bank
    private BigDecimal amount;
    private String remarks;
    @Size(max = 255)
    private String photoPath;

    // -------- Timestamps --------
    @DateCreated
    private Instant createdAt;

    @DateUpdated
    private Instant updatedAt;
}
