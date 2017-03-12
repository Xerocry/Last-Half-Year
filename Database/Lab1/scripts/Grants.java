
@Entity
public class Grants extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grant_id")
    Long grantId;

    @Column(name = "grant_sum")
    Long sum;

    @Column(name = "grant_date", nullable = false)
    LocalDate date;

    @Column(name = "paid_up")
    Boolean paidUp;

    @ManyToOne
    @Column(name = "service_id")
    Services service;

    @ManyToOne
    @Column(name = "drug_id")
    Drugs drug;

    @ManyToOne
    @Column(name = "doctor_id")
    Doctors doctor;

    @ManyToOne
    @Column(name = "patient_id")
    Patients patient;

    public Grants(LocalDate date, Boolean paidUp, Doctors doctor, Patients patient) {
        this.date = date;
        this.paidUp = paidUp;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Long getSum()     {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getPaidUp() {
        return paidUp;
    }

    public void setPaidUp(Boolean paidUp) {
        this.paidUp = paidUp;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Drugs getDrug() {
        return drug;
    }

    public void setDrug(Drugs drug) {
        this.drug = drug;
    }

    public Doctors getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctors doctor) {
        this.doctor = doctor;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }
}
