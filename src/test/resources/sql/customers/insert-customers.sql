INSERT INTO TB_USERS (id, username, password, role) VALUES (100, 'test1@mail.com', '$2b$12$s.xCXqoanJDCm83.2BC61.DxKsmVqheJas2f7yQBvkPxe6Olgt762', 'ROLE_ADMIN');
INSERT INTO TB_USERS (id, username, password, role) VALUES (101, 'test2@mail.com', '$2b$12$s.xCXqoanJDCm83.2BC61.DxKsmVqheJas2f7yQBvkPxe6Olgt762', 'ROLE_CUSTOMER');
INSERT INTO TB_USERS (id, username, password, role) VALUES (102, 'test3@mail.com', '$2b$12$s.xCXqoanJDCm83.2BC61.DxKsmVqheJas2f7yQBvkPxe6Olgt762', 'ROLE_CUSTOMER');
INSERT INTO TB_USERS (id, username, password, role) VALUES (103, 'test4@mail.com', '$2b$12$s.xCXqoanJDCm83.2BC61.DxKsmVqheJas2f7yQBvkPxe6Olgt762', 'ROLE_CUSTOMER');

INSERT INTO TB_CUSTOMERS(id,name,cpf,user_id) VALUES (10, 'Maria Rocha', '90325894078', 101);
INSERT INTO TB_CUSTOMERS(id,name,cpf,user_id) VALUES (20, 'John Doe', '19273528007', 102);