package com.gcs.betsport.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.gcs.betsport.domain.enumeration.ActionType;

/**
 * TransactionType entity.
 * @author The UrServices team.
 */
@ApiModel(description = "TransactionType entity. @author The UrServices team.")
@Entity
@Table(name = "transaction_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private ActionType action;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TransactionType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActionType getAction() {
        return action;
    }

    public TransactionType action(ActionType action) {
        this.action = action;
        return this;
    }

    public void setAction(ActionType action) {
        this.action = action;
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
        TransactionType transactionType = (TransactionType) o;
        if (transactionType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", action='" + getAction() + "'" +
            "}";
    }
}
