package com.xerocry.domain;

import io.ebean.Model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by raskia on 2/23/2017.
 */
@Entity
@Table(name = "DOCTORS")
public class Doctors extends Model {

    @Id
    @Column(name = "doctor_id")
    Long id;

/*    @OneToMany(mappedBy = "Doctors")
    @Column(name = "treatment_id")
    List<Treatment> treatmentId = new ArrayList<>();*/

    @Column(name = "years_of_expirience")
    Integer experience;

    @Column(length=50, name = "skill_desc")
    String skills;

    @Column(nullable = false, name = "hire_date")
    LocalDate hireDate;

    @ManyToOne(optional = false)
    @Column(name = "depart_id")
    Departments departId;

    public Doctors(Integer experience, String skills, LocalDate hireDate) {
        this.experience = experience;
        this.skills = skills;
        this.hireDate = hireDate;
    }

    /* public List<Treatment> getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(List<Treatment> treatmentId) {
        this.treatmentId = treatmentId;
    }*/

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Departments getDepartId() {
        return departId;
    }

    public void setDepartId(Departments departId) {
        this.departId = departId;
    }
}
