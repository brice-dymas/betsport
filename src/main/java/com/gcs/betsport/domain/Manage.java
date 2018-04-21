package com.gcs.betsport.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.gcs.betsport.domain.enumeration.State;

/**
 * Manage entity.
 * @author The UrServices team.
 */
@ApiModel(description = "Manage entity. @author The UrServices team.")
@Entity
@Table(name = "manage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Manage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "management_date", nullable = false)
    private ZonedDateTime managementDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @ManyToOne
    private Employee cashier;

    @ManyToOne
    private CashDesk cashDesk;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getManagementDate() {
        return managementDate;
    }

    public Manage managementDate(ZonedDateTime managementDate) {
        this.managementDate = managementDate;
        return this;
    }

    public void setManagementDate(ZonedDateTime managementDate) {
        this.managementDate = managementDate;
    }

    public State getState() {
        return state;
    }

    public Manage state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Employee getCashier() {
        return cashier;
    }

    public Manage cashier(Employee employee) {
        this.cashier = employee;
        return this;
    }

    public void setCashier(Employee employee) {
        this.cashier = employee;
    }

    public CashDesk getCashDesk() {
        return cashDesk;
    }

    public Manage cashDesk(CashDesk cashDesk) {
        this.cashDesk = cashDesk;
        return this;
    }

    public void setCashDesk(CashDesk cashDesk) {
        this.cashDesk = cashDesk;
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
        Manage manage = (Manage) o;
        if (manage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), manage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Manage{" +
            "id=" + getId() +
            ", managementDate='" + getManagementDate() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
