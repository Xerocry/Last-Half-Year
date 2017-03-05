package com.xerocry.domain;

import io.ebean.Model;
import io.ebean.annotation.EnumValue;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raskia on 2/27/2017.
 */
public class Payments extends Model {

    @Id
    @Column(name = "payment_id")
    Long paymentId;

    Double discount;

    @Column(nullable = false)
    Double balance;

    public enum State {
        @EnumValue("P")
        PAID,
        @EnumValue("N")
        NOT_PAID,
    }

    @ManyToMany(mappedBy = "payments")
    List<Patients> patients = new ArrayList<>();


    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Patients> getPatients() {
        return patients;
    }

    public void addPatients(Patients patients) {
        this.patients.add(patients);
    }
}
