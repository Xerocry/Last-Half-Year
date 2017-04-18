public class LoadExampleData {

    private static boolean runOnce;

    private static EbeanServer server = Ebean.getServer(null);

    private static PatientsGenerator patientGen;
    private static DepartmentsGenerator departGen;
    private static DiseasesGenerator disGen;
    private static DiseasesTypesGenerator disTypesGen;
    private static DoctorsGenerator doctorsGen;
    private static DrugsGenerator drugsGen;
    private static GrantsGenerator grantsGen;
    private static PaymentsGenerator paymentsGen;
    private static ServicesGenerator servicesGen;
    private static TreatmentGenerator treatmentGen;

    public static synchronized void load(int num) throws IOException {
        patientGen = new PatientsGenerator();
        departGen = new DepartmentsGenerator();
        disGen = new DiseasesGenerator();
        disTypesGen = new DiseasesTypesGenerator();
        doctorsGen = new DoctorsGenerator();
        drugsGen = new DrugsGenerator();
        grantsGen = new GrantsGenerator();
        paymentsGen = new PaymentsGenerator();
        servicesGen = new ServicesGenerator();
        treatmentGen = new TreatmentGenerator();

        if (runOnce) {
            return;
        }

        final LoadExampleData me = new LoadExampleData();

        server.execute(me::deleteAll);
        generateSome(num);
        runOnce = true;
    }

    private void deleteAll() {
        Ebean.execute(() -> {
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

    private static boolean generateSome(int genAmount) {
        IntegerRangeRandomizer intRandomizer = new IntegerRangeRandomizer(0, genAmount);

        server.execute(() -> {
            List<Departments> departments = new ArrayList<>();
            for (int i = 0; i < genAmount/10; i++) {
                Departments o = new Departments(departGen.random.nextObject(Departments.class));
                departments.add(o);
                o.save();

            }
            System.out.println(departments);

            List<Doctors> doctors = new ArrayList<>();
            for (int i = 0; i < genAmount; i++) {
                Doctors o = new Doctors(doctorsGen.random.nextObject(Doctors.class));
                o.setDepartId(departments.get(new IntegerRangeRandomizer(0, genAmount/10).getRandomValue()));
                doctors.add(o);
                o.save();
            }
            System.out.println(doctors);

            List<Patients> patients = new ArrayList<>();
            for (int i = 0; i < genAmount*5; i++) {
                Patients o = new Patients(patientGen.random.nextObject(Patients.class));
                patients.add(o);
                o.save();

            }
            System.out.println(patients);

            List<DiseasesTypes> diseasesTypes = new ArrayList<>();
            for (int i = 0; i < genAmount; i++) {
                DiseasesTypes o = new DiseasesTypes(disTypesGen.random.nextObject(DiseasesTypes.class));
                diseasesTypes.add(o);
                o.save();

            }
            System.out.println(diseasesTypes);

            List<Diseases> diseases = new ArrayList<>();
            for (int i = 0; i < genAmount; i++) {
                Diseases o = new Diseases(disGen.random.nextObject(Diseases.class));
                o.setDisType(diseasesTypes.get(intRandomizer.getRandomValue()));
                diseases.add(o);
                o.save();
            }
            System.out.println(diseases);

            List<Drugs> drugs = new ArrayList<>();
            for (int i = 0; i < genAmount; i++) {
                Drugs o = new Drugs(drugsGen.random.nextObject(Drugs.class));
                o.setTypeId(diseasesTypes.get(intRandomizer.getRandomValue()));
                switch ((int) (Math.random() * 3 + 1)) {
                    case 1: o.save();

                    case 2: o.save();

                    case 3: {
                        if (drugs.isEmpty()) {
                            break;
                        }
                        Set<Drugs> drugRestr = new HashSet<>();
                        drugRestr.add(drugs.get(new IntegerRangeRandomizer(0, drugs.size()-1).getRandomValue()));
                        o.setRestrictionsColl(drugRestr);
                        o.save();
                    }
                    default: o.save();
                }
                drugs.add(o);
            }
            System.out.println(drugs);

            List<Services> services = new ArrayList<>();
            for (int i = 0; i < genAmount; i++) {
                Services o = new Services(servicesGen.random.nextObject(Services.class));
                o.save();
                services.add(o);
            }
            System.out.println(services);

            List<Grants> grants = new ArrayList<>();
            for (int i = 0; i < genAmount; i++) {
                Grants o = new Grants(grantsGen.random.nextObject(Grants.class));
                Drugs drug = drugs.get(intRandomizer.getRandomValue());
                Services service = services.get(intRandomizer.getRandomValue());
                o.setSum(Integer.toUnsignedLong(drug.getPrice() + service.getPrice()));
                o.setDoctor(doctors.get(intRandomizer.getRandomValue()));
                o.setPatient(patients.get(new IntegerRangeRandomizer(0, genAmount*5).getRandomValue()));
                o.setDrug(drug);
                o.setService(service);
                o.save();
                grants.add(o);
            }
            System.out.println(grants);

            List<Payments> payments = new ArrayList<>();
            for (int i = 0; i < genAmount; i++) {
                Payments o = new Payments(paymentsGen.random.nextObject(Payments.class));
                Set<Patients> patientsTo = new HashSet<>();
                patientsTo.add(patients.get(new IntegerRangeRandomizer(0, genAmount*5).getRandomValue()));
                o.setPatients(patientsTo);
                o.save();
                payments.add(o);
            }
            System.out.println(payments);

            List<Treatment> treatments = new ArrayList<>();
            for (int i = 0; i < genAmount * 10; i++) {
                Treatment o = new Treatment(treatmentGen.random.nextObject(Treatment.class));

                o.setPatientId(patients.get(new IntegerRangeRandomizer(0, genAmount*5).getRandomValue()));
                o.setDoctorId(doctors.get(intRandomizer.getRandomValue()));
                o.setDiseaseId(diseases.get(intRandomizer.getRandomValue()));

                Integer drugsToNum = intRandomizer.getRandomValue();
                Set<Drugs> drugsTo = new HashSet<>();
                for (int j = 0; j < drugsToNum; j++) {
                    Drugs drug = drugs.get(intRandomizer.getRandomValue());
                    while (checkRestriction(drug, drugsTo)) {
                        drug = drugs.get(intRandomizer.getRandomValue());
                    }
                    drugsTo.add(drugs.get(intRandomizer.getRandomValue()));
                }
                o.setDrugs(drugsTo);

                Integer servicesToNum = intRandomizer.getRandomValue();
                Set<Services> servicesTo = new HashSet<>();
                for (int j = 0; j < servicesToNum; j++) {
                    servicesTo.add(services.get(intRandomizer.getRandomValue()));
                }
                o.setServices(servicesTo);
                o.save();
                treatments.add(o);
            }
            System.out.println(treatments);

        });
        return true;
    }

    public static boolean checkRestriction(Drugs drug1, Set<Drugs> drug2) {
        for (Drugs dr: drug2) {
            if (dr.equals(drug1)) {
                return true;
            }
        }
        return false;
    }
}
