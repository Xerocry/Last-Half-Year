package com.xerocry.domain;

import io.ebean.Model;
import io.ebean.annotation.EnumValue;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raskia on 2/24/2017.
 */
@Entity
public class Patients extends Model {

    @Id
    @Column(name = "patient_id")
    Long patientId;

    @Column(name = "reg_date")
    LocalDate regDate;

    String city;

    @Column(name = "p_name", nullable = false)
    String name;

    @Column(name = "dob", nullable = false)
    LocalDate birthDate;

    public enum Gender {
        @EnumValue("M")
        MALE,
        @EnumValue("F")
        FEMALE,
    }

    @Column(nullable = false)
    Gender gender;

    @OneToMany(mappedBy = "patientId")
    List<Treatment> treatments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "PAYMENT_PATIENT",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id", referencedColumnName = "payment_id"))
    List<Payments> payments;

    public Patients(String name, LocalDate birthDate, Gender gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void addTreatments(Treatment treatments) {
        this.treatments.add(treatments);
    }

    public List<Payments> getPayments() {
        return payments;
    }

    public void addPayments(Payments payments) {
        this.payments.add(payments);
    }
}
