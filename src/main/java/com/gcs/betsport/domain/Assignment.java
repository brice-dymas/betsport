package com.gcs.betsport.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.gcs.betsport.domain.enumeration.State;

/**
 * Assignment entity.
 * @author The UrServices team.
 */
@ApiModel(description = "Assignment entity. @author The UrServices team.")
@Entity
@Table(name = "assignment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @NotNull
    @Column(name = "assigned_time", nullable = false)
    private ZonedDateTime assignedTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Period period;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public Assignment assignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
        return this;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public ZonedDateTime getAssignedTime() {
        return assignedTime;
    }

    public Assignment assignedTime(ZonedDateTime assignedTime) {
        this.assignedTime = assignedTime;
        return this;
    }

    public void setAssignedTime(ZonedDateTime assignedTime) {
        this.assignedTime = assignedTime;
    }

    public State getState() {
        return state;
    }

    public Assignment state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Assignment employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Period getPeriod() {
        return period;
    }

    public Assignment period(Period period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Period period) {
        this.period = period;
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
        Assignment assignment = (Assignment) o;
        if (assignment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assignment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assignment{" +
            "id=" + getId() +
            ", assignedDate='" + getAssignedDate() + "'" +
            ", assignedTime='" + getAssignedTime() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
