CREATE TABLE staff (
                       id VARCHAR PRIMARY KEY,
                       fullName VARCHAR PRIMARY KEY,
                       managerId VARCHAR PRIMARY KEY,
                       role VARCHAR PRIMARY KEY,
);

CREATE TABLE staff_skill (
                        id VARCHAR PRIMARY KEY,
                        expiry DATE,
                        skillId VARCHAR NOT NULL,
                        skillName VARCHAR NOT NULL,
                        levelOfSkill VARCHAR NOT NULL,
                        staffId VARCHAR NOT NULL,
                        notes TEXT,
                        staffFullName VARCHAR
);