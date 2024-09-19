INSERT INTO staff (staff_id, full_name, manager_id, role) VALUES
 ('201', 'John Lol', '301', 'Software Engineer'),
 ('202', 'Jane Smith', '302', 'Project Manager');

INSERT INTO staff_skill (staff_skill_id, staff_skill_name, expiry, level_of_skill, staff_id, notes) VALUES
  (1, 'Java Programming', '2025-12-31', 'Expert', '201', 'this skill is good'),
  (2, 'Project Management', '2024-06-30', 'Intermediate', '202', 'this is a good skill to have');

create sequence staff_skill_sequence_id start with(select max(staff_skill_id) + 1 from staff_skill);

