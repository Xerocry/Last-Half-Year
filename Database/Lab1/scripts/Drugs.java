package com.xerocry.domain;

import io.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raskia on 2/23/2017.
 */
@Entity
public class Drugs extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drug_id")
    Long drugId;

    @ManyToOne(optional = false)
    @Column(name = "type_id")
    DiseasesTypes typeId;

    @Column(length = 50, name = "drug_name")
    String drugName;

    Integer price;

    @ManyToMany(mappedBy = "drugs")
    List<Treatment> treatments = new ArrayList<>();

    public Drugs(DiseasesTypes typeId, String drugName, Integer price) {
        this.typeId = typeId;
        this.drugName = drugName;
        this.price = price;
    }

    public DiseasesTypes getTypeId() {
        return typeId;
    }

    public void setTypeId(DiseasesTypes typeId) {
        this.typeId = typeId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }
}
