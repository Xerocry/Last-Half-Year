package com.xerocry.domain;

import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by raskia on 2/23/2017.
 */
@Entity
public class Services extends Model {

    @Id
    @Column(name = "service_id")
    Long serviceId;

    @Column(length = 50, nullable = false, name = "service_name")
    String serviceName;

    Integer price;

    @ManyToMany(mappedBy = "services")
    List<Treatment> treatments;

    public Services(String serviceName, Integer price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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
