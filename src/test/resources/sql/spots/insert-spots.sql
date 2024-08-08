INSERT INTO TB_USERS (id, username, password, role) VALUES (100, 'test1@mail.com', '$2b$12$s.xCXqoanJDCm83.2BC61.DxKsmVqheJas2f7yQBvkPxe6Olgt762', 'ROLE_ADMIN');
INSERT INTO TB_USERS (id, username, password, role) VALUES (101, 'test2@mail.com', '$2b$12$s.xCXqoanJDCm83.2BC61.DxKsmVqheJas2f7yQBvkPxe6Olgt762', 'ROLE_CUSTOMER');
INSERT INTO TB_USERS (id, username, password, role) VALUES (102, 'test3@mail.com', '$2b$12$s.xCXqoanJDCm83.2BC61.DxKsmVqheJas2f7yQBvkPxe6Olgt762', 'ROLE_CUSTOMER');

INSERT INTO TB_SPOTS (id, code, status) VALUES (10, 'A-01', 'FREE');
INSERT INTO TB_SPOTS (id, code, status) VALUES (20, 'A-02', 'FREE');
INSERT INTO TB_SPOTS (id, code, status) VALUES (30, 'A-03', 'OCCUPIED');
INSERT INTO TB_SPOTS (id, code, status) VALUES (40, 'A-04', 'FREE');