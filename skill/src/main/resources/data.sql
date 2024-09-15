INSERT INTO category (category_id, category_name)
VALUES ('c1', 'Programming'),
       ('c2', 'AI'),
       ('c3', 'Productivity');

INSERT INTO skill (skill_id, skill_name, category_id)
        VALUES('s1', 'java', 'c1');
INSERT INTO skill (skill_id, skill_name, category_id)
        VALUES('s2', 'python', 'c1');
INSERT INTO skill (skill_id, skill_name, category_id)
        VALUES('s3', 'NLP', 'c2');
INSERT INTO skill (skill_id, skill_name, category_id)
        VALUES('s4', 'microsoft office', 'c3');


INSERT INTO skill_detail (skill_detail_id, skill_name, proficiency_level, skill_id)
        VALUES(1, 'java', 'strong', 's1');
INSERT INTO skill_detail (skill_detail_id, skill_name, proficiency_level, skill_id)
        VALUES(2, 'python', 'very strong', 's2');
INSERT INTO skill_detail (skill_detail_id, skill_name, proficiency_level, skill_id)
        VALUES(3, 'NLP', 'medium', 's3');
INSERT INTO skill_detail (skill_detail_id, skill_name, proficiency_level, skill_id)
        VALUES(4, 'microsoft office', 'strong', 's4');

create sequence skill_detail_sequence_id start with(select max(skill_detail_id) + 1 from skill_detail);
