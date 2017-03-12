public class Payments extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
