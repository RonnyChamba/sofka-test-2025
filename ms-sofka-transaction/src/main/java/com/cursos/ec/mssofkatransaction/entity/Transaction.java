package com.cursos.ec.mssofkatransaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movimientos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ide")
    private Integer ide;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "value", precision = 6, scale = 2, nullable = false)
    private BigDecimal value;

    @Column(name = "balance", precision = 6, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(name = "available_balance", precision = 6, scale = 2, nullable = false)
    private BigDecimal balanceAvailable;

    @Column(name = "movement_type", length = 20, nullable = false)
    private String movementType;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

}
