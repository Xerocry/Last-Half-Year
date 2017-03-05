public class LoadExampleData {

    private static boolean runOnce;

    private static EbeanServer server = Ebean.getServer(null);

    public static synchronized void load() {

        if (runOnce) {
            return;
        }

        final LoadExampleData me = new LoadExampleData();

        server.execute(() -> {
            me.deleteAll();
            me.insertPatients();
            me.createTreatment("Treat1", LocalDate.now(), LocalDate.of(2015, 12, 02));
            me.createTreatment("Treat2", LocalDate.now(), LocalDate.of(2016, 11, 02));
        });
        runOnce = true;
    }

    public void deleteAll() {
        Ebean.execute(() -> {
            // orm update use bean name and bean properties
            server.createUpdate(Departments.class, "delete from departments").execute();
            server.createUpdate(Diseases.class, "delete from diseases").execute();
            server.createUpdate(Doctors.class, "delete from doctors").execute();
            server.createUpdate(Drugs.class, "delete from drugs").execute();
            server.createUpdate(DiseasesTypes.class, "delete from diseasesTypes").execute();
            server.createUpdate(Grants.class, "delete from grants").execute();
            server.createUpdate(Patients.class, "delete from patients").execute();
            server.createUpdate(Services.class, "delete from services").execute();
            server.createUpdate(Treatment.class, "delete from treatment").execute();
        });
    }


    public void insertPatients(){
        server.execute(()->{
           new Patients( "Andrey", LocalDate.now(), Patients.Gender.MALE).save();
           new Patients( "Marina", LocalDate.now(), Patients.Gender.FEMALE).save();
           new Patients( "Derek", LocalDate.now(), Patients.Gender.MALE).save();
        });
    }



    public void insertDoctors(){
        server.execute(()->{
            new Doctors(5,"Can heal", LocalDate.of(1995, 03, 12)).save();
        });
    }

    private static Departments insertDepartment(String name) {
        Departments department = new Departments(name);
        Ebean.save(department);
        return department;
    }

    public Doctors createDoctor(String skills, int exp, LocalDate hiredDate) {
        Departments department = insertDepartment("Depart" + UUID.randomUUID().toString());
        Doctors doctor = new Doctors(exp, skills, hiredDate);
        doctor.setDepartId(department);
        Ebean.save(doctor);
        return doctor;
    }

    public static DiseasesTypes insertType(String type) {
        DiseasesTypes disType = new DiseasesTypes(type);
        Ebean.save(disType);
        return disType;
    }

    public Diseases createDisease(String name) {
        Diseases dis = new Diseases(insertType("type"+UUID.randomUUID().toString()), name);
        Ebean.save(dis);
        return dis;
    }


    public static Patients createPatient(LocalDate regDate, String city, String name, LocalDate birthDate, Patients.Gender gender) {
        Patients patient = new Patients(name, birthDate, gender);
        if(regDate != null){
            patient.setRegDate(regDate);
        }
        if (city != null) {
            patient.setCity(city);
        }
        Ebean.save(patient);
        return patient;
    }

    public void createTreatment(String treatment, LocalDate endDate, LocalDate startDate) {
        Treatment treatment1 = new Treatment(startDate);
        treatment1.setDoctorId(createDoctor(UUID.randomUUID().toString(), 10, LocalDate.of(1995, 10, 1)));
        treatment1.setPatientId(createPatient(LocalDate.now(), "Piter", "Andrey", LocalDate.now(), Patients.Gender.MALE));
        treatment1.setDiseaseId(createDisease("dis" + UUID.randomUUID().toString()));
        if (treatment != null) {
            treatment1.setTreatment(treatment);
        }
        if (endDate != null) {
            treatment1.setEndDate(endDate);
        }
        Ebean.save(treatment1);
    }
}
