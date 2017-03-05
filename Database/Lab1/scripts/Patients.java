@Entity
public class Patients extends Model {
        ...
    @ManyToMany
    @JoinTable(name = "PAYMENT_PATIENT",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_id", referencedColumnName = "payment_id"))
    List<Payments> payments;
         ...
}

@Entity
public class Payments extends Model {
        ...
    @ManyToMany(mappedBy = "payments")
    List<Patients> patients = new ArrayList<>();
        ...
}
