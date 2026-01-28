CREATE TABLE appointment_user (
      id BIGSERIAL PRIMARY KEY NOT NULL,
       uuid VARCHAR(10) NOT NULL UNIQUE,
       username VARCHAR NOT NULL,
       password VARCHAR NOT NULL,
       email VARCHAR,
       full_name VARCHAR NOT NULL,
       is_super_admin BOOLEAN NOT NULL DEFAULT FALSE,
       is_deactivated BOOLEAN NOT NULL DEFAULT FALSE,
       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
       created_by VARCHAR NOT NULL DEFAULT 'SYSTEM',
       modified_by VARCHAR NOT NULL DEFAULT 'SYSTEM',
       UNIQUE(username)
);

CREATE TABLE doctors (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR NOT NULL,
     specialization VARCHAR,
     phone VARCHAR,
     email VARCHAR UNIQUE,
     created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
     created_by VARCHAR NOT NULL DEFAULT 'SYSTEM',
     modified_by VARCHAR NOT NULL DEFAULT 'SYSTEM'
);

CREATE TABLE patients (
      id BIGSERIAL PRIMARY KEY,
      name VARCHAR NOT NULL,
      phone VARCHAR,
      email VARCHAR,
      date_of_birth DATE,
      created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
      created_by VARCHAR NOT NULL DEFAULT 'SYSTEM',
      modified_by VARCHAR NOT NULL DEFAULT 'SYSTEM'
);

CREATE TABLE appointments (
      id BIGSERIAL PRIMARY KEY,
      doctor_id BIGINT NOT NULL,
      patient_id BIGINT NOT NULL,
      appointment_time TIMESTAMP NOT NULL,
      status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
      notes TEXT,
      created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
      created_by VARCHAR NOT NULL DEFAULT 'SYSTEM',
      modified_by VARCHAR NOT NULL DEFAULT 'SYSTEM',

      CONSTRAINT fk_appointment_doctor
          FOREIGN KEY (doctor_id) REFERENCES doctors(id),

      CONSTRAINT fk_appointment_patient
          FOREIGN KEY (patient_id) REFERENCES patients(id)
);


CREATE TABLE audit_log (
       id BIGSERIAL PRIMARY KEY NOT NULL,
       event_type INT NOT NULL,
       resource_type INT NULL,
       resource_name VARCHAR NULL,
       resource_id BIGINT NULL,
       user_id BIGINT NULL,
       username VARCHAR NOT NULL,
       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
       created_by VARCHAR NOT NULL DEFAULT 'SYSTEM',
       modified_by VARCHAR NOT NULL DEFAULT 'SYSTEM'
);