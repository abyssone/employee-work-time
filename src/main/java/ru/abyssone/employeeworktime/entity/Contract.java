package ru.abyssone.employeeworktime.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dateOfConclusion;
    private LocalDate entryIntoForceDate;
    private LocalDate expirationDate;
    private String position;
    //private WorkTimeModel workTimeModel;

    protected Contract() {
    }

    //todo: убрать
    public Contract(LocalDate dateOfConclusion, LocalDate entryIntoForceDate, LocalDate expirationDate, String position) {
        this.dateOfConclusion = dateOfConclusion;
        this.entryIntoForceDate = entryIntoForceDate;
        this.expirationDate = expirationDate;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfConclusion() {
        return dateOfConclusion;
    }

    public void setDateOfConclusion(LocalDate dateOfConclusion) {
        this.dateOfConclusion = dateOfConclusion;
    }

    public LocalDate getEntryIntoForceDate() {
        return entryIntoForceDate;
    }

    public void setEntryIntoForceDate(LocalDate entryIntoForceDate) {
        this.entryIntoForceDate = entryIntoForceDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
