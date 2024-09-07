CREATE TABLE staff (
                       staff_id VARCHAR PRIMARY KEY,
                       full_name VARCHAR NOT NULL,
                       manager_id VARCHAR NOT NULL,
                       role VARCHAR NOT NULL
);

CREATE TABLE staff_skill (
                        staff_skill_id VARCHAR PRIMARY KEY,
                        staff_skill_name VARCHAR NOT NULL,
                        expiry DATE,
                        level_of_skill VARCHAR NOT NULL,
                        staff_id VARCHAR NOT NULL,
                        notes TEXT,
                        staff_full_name VARCHAR,
                        FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);