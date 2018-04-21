package com.gcs.betsport.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.gcs.betsport.domain.enumeration.State;

/**
 * CashDesk entity.
 * @author The UrServices team.
 */
@ApiModel(description = "CashDesk entity. @author The UrServices team.")
@Entity
@Table(name = "cash_desk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CashDesk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "title", nullable = false)
    private String title;

    @DecimalMin(value = "0")
    @Column(name = "cash_amount", precision=10, scale=2)
    private BigDecimal cashAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CashDesk title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public CashDesk cashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
        return this;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public State getState() {
        return state;
    }

    public CashDesk state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
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
        CashDesk cashDesk = (CashDesk) o;
        if (cashDesk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cashDesk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CashDesk{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", cashAmount=" + getCashAmount() +
            ", state='" + getState() + "'" +
            "}";
    }
}
