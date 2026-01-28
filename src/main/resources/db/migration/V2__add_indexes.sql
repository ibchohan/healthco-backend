CREATE INDEX idx_appointment_doctor_time
    ON appointments (doctor_id, appointment_time);

CREATE INDEX idx_appointment_patient
    ON appointments (patient_id);

CREATE INDEX idx_appointment_status
    ON appointments (status);
