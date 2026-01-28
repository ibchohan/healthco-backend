CREATE TABLE doctor_availability (
     id BIGSERIAL PRIMARY KEY,
     doctor_id BIGINT NOT NULL,
     day_of_week VARCHAR(10) NOT NULL,
     start_time TIME NOT NULL,
     end_time TIME NOT NULL,
     created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
     created_by VARCHAR NOT NULL DEFAULT 'SYSTEM',
     modified_by VARCHAR NOT NULL DEFAULT 'SYSTEM',

     CONSTRAINT fk_availability_doctor
         FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
