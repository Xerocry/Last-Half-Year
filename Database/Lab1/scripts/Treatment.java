
@Entity
@Table(name = "Treatment")
public class Treatment extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_id")
    Long id;

    @ManyToOne(optional = false)
    @Column(name = "patient_id")
    Patients patientId;

    @ManyToOne(optional = false)
    @Column(name = "doctor_id")
    Doctors doctorId;

    @ManyToOne
    @Column(name = "disease_id")
    Diseases diseaseId;

    @Column(name = "start_date", nullable = false)
    LocalDate startDate;

    @Column(name = "end_date")
    LocalDate endDate;

    @Column
    String treatment;

    @ManyToMany
    @JoinTable(name = "TREATMENT_DRUGS",
        joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "treatment_id"),
        inverseJoinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "drug_id"))
    List<Drugs> drugs;

    @ManyToMany
    @JoinTable(name = "TREATMENT_SERVICES",
            joinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "treatment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "service_id"))
    List<Services> services;

    public Treatment(LocalDate startDate) {
//        this.patientId = patientId;
//        this.doctorId = doctorId;
//        this.diseaseId = diseaseId;
        this.startDate = startDate;
    }

    public Patients getPatientId() {
        return patientId;
    }

    public void setPatientId(Patients patientId) {
        this.patientId = patientId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public List<Drugs> getDrugs() {
        return drugs;
    }

    public void addDrugs(Drugs drug) {
        this.drugs.add(drug);
    }

    public Doctors getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Doctors doctorId) {
        this.doctorId = doctorId;
    }

    public Diseases getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Diseases diseaseId) {
        this.diseaseId = diseaseId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public List<Services> getServices() {
        return services;
    }

    public void addServices(Services service) {
        this.services.add(service);
    }
}
