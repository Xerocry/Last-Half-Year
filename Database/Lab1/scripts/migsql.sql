-- apply changes
create table departments (
  depart_id                     bigserial not null,
  depart_name                   varchar(50) not null,
  constraint pk_departments primary key (depart_id)
);

create table diseases (
  disease_id                    bigserial not null,
  symptoms                      varchar(50),
  dis_type_type_id              bigint not null,
  disease_name                  varchar(50) not null,
  constraint pk_diseases primary key (disease_id)
);

create table diseases_types (
  type_id                       bigserial not null,
  dis_type                      varchar(50),
  constraint pk_diseases_types primary key (type_id)
);

create table doctors (
  doctor_id                     bigserial not null,
  years_of_expirience           integer,
  skill_desc                    varchar(50),
  hire_date                     date not null,
  depart_id_depart_id           bigint not null,
  constraint pk_doctors primary key (doctor_id)
);

create table drugs (
  drug_id                       bigserial not null,
  type_id_type_id               bigint not null,
  drug_name                     varchar(50),
  price                         integer,
  constraint pk_drugs primary key (drug_id)
);

create table grants (
  grant_id                      bigserial not null,
  grant_sum                     bigint,
  grant_date                    date not null,
  paid_up                       boolean,
  service_service_id            bigint,
  drug_drug_id                  bigint,
  doctor_doctor_id              bigint,
  patient_patient_id            bigint,
  constraint pk_grants primary key (grant_id)
);

create table patients (
  patient_id                    bigserial not null,
  reg_date                      date,
  city                          varchar(255),
  p_name                        varchar(255) not null,
  dob                           date not null,
  gender                        varchar(1) not null,
  constraint ck_patients_gender check ( gender in ('M','F')),
  constraint pk_patients primary key (patient_id)
);

create table services (
  service_id                    bigserial not null,
  service_name                  varchar(50) not null,
  price                         integer,
  constraint pk_services primary key (service_id)
);

create table treatment (
  treatment_id                  bigserial not null,
  patient_id_patient_id         bigint not null,
  doctor_id_doctor_id           bigint not null,
  disease_id_disease_id         bigint,
  start_date                    date not null,
  end_date                      date,
  treatment                     varchar(255),
  constraint pk_treatment primary key (treatment_id)
);

create table treatment_drugs (
  treatment_id                  bigint not null,
  drug_id                       bigint not null,
  constraint pk_treatment_drugs primary key (treatment_id,drug_id)
);

create table treatment_services (
  treatment_id                  bigint not null,
  service_id                    bigint not null,
  constraint pk_treatment_services primary key (treatment_id,service_id)
);

alter table diseases add constraint fk_diseases_dis_type_type_id foreign key (dis_type_type_id) references diseases_types (type_id) on delete restrict on update restrict;
create index ix_diseases_dis_type_type_id on diseases (dis_type_type_id);

alter table doctors add constraint fk_doctors_depart_id_depart_id foreign key (depart_id_depart_id) references departments (depart_id) on delete restrict on update restrict;
create index ix_doctors_depart_id_depart_id on doctors (depart_id_depart_id);

alter table drugs add constraint fk_drugs_type_id_type_id foreign key (type_id_type_id) references diseases_types (type_id) on delete restrict on update restrict;
create index ix_drugs_type_id_type_id on drugs (type_id_type_id);

alter table grants add constraint fk_grants_service_service_id foreign key (service_service_id) references services (service_id) on delete restrict on update restrict;
create index ix_grants_service_service_id on grants (service_service_id);

alter table grants add constraint fk_grants_drug_drug_id foreign key (drug_drug_id) references drugs (drug_id) on delete restrict on update restrict;
create index ix_grants_drug_drug_id on grants (drug_drug_id);

alter table grants add constraint fk_grants_doctor_doctor_id foreign key (doctor_doctor_id) references doctors (doctor_id) on delete restrict on update restrict;
create index ix_grants_doctor_doctor_id on grants (doctor_doctor_id);

alter table grants add constraint fk_grants_patient_patient_id foreign key (patient_patient_id) references patients (patient_id) on delete restrict on update restrict;
create index ix_grants_patient_patient_id on grants (patient_patient_id);

alter table treatment add constraint fk_treatment_patient_id_patient_id foreign key (patient_id_patient_id) references patients (patient_id) on delete restrict on update restrict;
create index ix_treatment_patient_id_patient_id on treatment (patient_id_patient_id);

alter table treatment add constraint fk_treatment_doctor_id_doctor_id foreign key (doctor_id_doctor_id) references doctors (doctor_id) on delete restrict on update restrict;
create index ix_treatment_doctor_id_doctor_id on treatment (doctor_id_doctor_id);

alter table treatment add constraint fk_treatment_disease_id_disease_id foreign key (disease_id_disease_id) references diseases (disease_id) on delete restrict on update restrict;
create index ix_treatment_disease_id_disease_id on treatment (disease_id_disease_id);

alter table treatment_drugs add constraint fk_treatment_drugs_treatment foreign key (treatment_id) references treatment (treatment_id) on delete restrict on update restrict;
create index ix_treatment_drugs_treatment on treatment_drugs (treatment_id);

alter table treatment_drugs add constraint fk_treatment_drugs_drugs foreign key (drug_id) references drugs (drug_id) on delete restrict on update restrict;
create index ix_treatment_drugs_drugs on treatment_drugs (drug_id);

alter table treatment_services add constraint fk_treatment_services_treatment foreign key (treatment_id) references treatment (treatment_id) on delete restrict on update restrict;
create index ix_treatment_services_treatment on treatment_services (treatment_id);

alter table treatment_services add constraint fk_treatment_services_services foreign key (service_id) references services (service_id) on delete restrict on update restrict;
create index ix_treatment_services_services on treatment_services (service_id);

