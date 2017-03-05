package com.xerocry.domain;

import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by raskia on 2/23/2017.
 */
@Entity
public class DiseasesTypes extends Model {

    @Id
    Long type_id;

    @Column(length = 50, name = "dis_type")
    String disType;

    /*@OneToMany(mappedBy = "Diseases")
    List<Diseases> diseases;*/

    public DiseasesTypes(String disType) {
        this.disType = disType;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    /*public List<Diseases> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Diseases> diseases) {
        this.diseases = diseases;
    }*/
}
