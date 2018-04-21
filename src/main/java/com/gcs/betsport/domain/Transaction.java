package com.gcs.betsport.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.gcs.betsport.domain.enumeration.State;

/**
 * Transaction entity.
 * @author The UrServices team.
 */
@ApiModel(description = "Transaction entity. @author The UrServices team.")
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "transaction_time", nullable = false)
    private ZonedDateTime transactionTime;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private CashDesk cashDesk;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Transaction payment;

    @ManyToOne
    private Transaction openingTransaction;

    @ManyToOne
    private TransactionType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Transaction code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getTransactionTime() {
        return transactionTime;
    }

    public Transaction transactionTime(ZonedDateTime transactionTime) {
        this.transactionTime = transactionTime;
        return this;
    }

    public void setTransactionTime(ZonedDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Transaction transactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public State getState() {
        return state;
    }

    public Transaction state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Transaction employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public CashDesk getCashDesk() {
        return cashDesk;
    }

    public Transaction cashDesk(CashDesk cashDesk) {
        this.cashDesk = cashDesk;
        return this;
    }

    public void setCashDesk(CashDesk cashDesk) {
        this.cashDesk = cashDesk;
    }

    public Player getPlayer() {
        return player;
    }

    public Transaction player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Transaction getPayment() {
        return payment;
    }

    public Transaction payment(Transaction transaction) {
        this.payment = transaction;
        return this;
    }

    public void setPayment(Transaction transaction) {
        this.payment = transaction;
    }

    public Transaction getOpeningTransaction() {
        return openingTransaction;
    }

    public Transaction openingTransaction(Transaction transaction) {
        this.openingTransaction = transaction;
        return this;
    }

    public void setOpeningTransaction(Transaction transaction) {
        this.openingTransaction = transaction;
    }

    public TransactionType getType() {
        return type;
    }

    public Transaction type(TransactionType transactionType) {
        this.type = transactionType;
        return this;
    }

    public void setType(TransactionType transactionType) {
        this.type = transactionType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", transactionTime='" + getTransactionTime() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", amount=" + getAmount() +
            ", state='" + getState() + "'" +
            "}";
    }
}
