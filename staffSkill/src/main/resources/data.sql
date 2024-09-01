INSERT INTO staff (id, full_name, manager_id, role) VALUES
 ('201', 'John Doe', '301', 'Software Engineer'),
 ('202', 'Jane Smith', '302', 'Project Manager');

INSERT INTO staff_skill (id, expiry, skill_id, skill_name, level_of_skill, staff_id, staff_full_name) VALUES
 ('1', '2025-12-31', '101', 'Java Programming', 'Expert', '201', 'John Doe'),
 ('2', '2024-06-30', '102', 'Project Management', 'Intermediate', '202', 'Jane Smith');