package com.cursos.ec.mssofkatransaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ide;

    @Column(name = "number", length = 100, nullable = false)
    private String number;

    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @Column(name = "balance", precision = 6, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(name = "customerId", nullable = false)
    private Integer customerId;

    @Column(name = "status_record", length = 10, nullable = false)
    private String statusRecord;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }

}
