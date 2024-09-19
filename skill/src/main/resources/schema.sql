CREATE TABLE category (
                          category_id VARCHAR PRIMARY KEY,
                          category_name VARCHAR
);

CREATE TABLE skill(
                      skill_id VARCHAR PRIMARY KEY,
                      skill_name VARCHAR NOT NULL,
                      category_id VARCHAR NOT NULL,
                       FOREIGN KEY (category_id) REFERENCES category(category_id)
);
CREATE TABLE skill_detail(
                          skill_detail_id LONG PRIMARY KEY,
                          skill_name VARCHAR NOT NULL,
                          proficiency_level VARCHAR NOT NULL,
                          skill_id VARCHAR NOT NULL,
                          FOREIGN KEY(skill_id) REFERENCES skill(skill_id)
);