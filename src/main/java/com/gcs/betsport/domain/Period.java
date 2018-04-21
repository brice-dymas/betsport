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
 * Period entity.
 * @author The UrServices team.
 */
@ApiModel(description = "Period entity. @author The UrServices team.")
@Entity
@Table(name = "period")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "begining_hour", nullable = false)
    private ZonedDateTime beginingHour;

    @NotNull
    @Column(name = "ending_hour", nullable = false)
    private ZonedDateTime endingHour;

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

    public ZonedDateTime getBeginingHour() {
        return beginingHour;
    }

    public Period beginingHour(ZonedDateTime beginingHour) {
        this.beginingHour = beginingHour;
        return this;
    }

    public void setBeginingHour(ZonedDateTime beginingHour) {
        this.beginingHour = beginingHour;
    }

    public ZonedDateTime getEndingHour() {
        return endingHour;
    }

    public Period endingHour(ZonedDateTime endingHour) {
        this.endingHour = endingHour;
        return this;
    }

    public void setEndingHour(ZonedDateTime endingHour) {
        this.endingHour = endingHour;
    }

    public State getState() {
        return state;
    }

    public Period state(State state) {
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
        Period period = (Period) o;
        if (period.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), period.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Period{" +
            "id=" + getId() +
            ", beginingHour='" + getBeginingHour() + "'" +
            ", endingHour='" + getEndingHour() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
