DELETE FROM user_roles;
DELETE FROM role_permissions;
DELETE FROM PRICES;
DELETE FROM BRAND;
DELETE FROM users;
DELETE FROM roles;
DELETE FROM permissions;

INSERT INTO BRAND (BRAND_ID, BRAND_NAME) VALUES (1, 'ZARA');
INSERT INTO users (id, username, password, is_enabled, account_no_expired, account_no_locked, credential_no_expired) VALUES (1, 'ADMIN', '$2a$10$AwBc8cZfghF3qTfa9dei1uI8gVtWPRccli6//zPjQmydF3StGKLpC', true, true, true, true);
INSERT INTO users (id, username, password, is_enabled, account_no_expired, account_no_locked, credential_no_expired) VALUES (2, 'USER', '$2a$10$AwBc8cZfghF3qTfa9dei1uI8gVtWPRccli6//zPjQmydF3StGKLpC', true, true, true, true);
INSERT INTO roles (id, role_name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, role_name) VALUES (2, 'USER');
INSERT INTO permissions (id, name) VALUES (1, 'READ_PRICES');
INSERT INTO permissions (id, name) VALUES (2, 'WRITE_PRICES');
INSERT INTO permissions (id, name) VALUES (3, 'MANAGE_USERS');

INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES (1, PARSEDATETIME('2020-06-14 00:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2020-12-31 23:59:59', 'yyyy-MM-dd HH:mm:ss'), 1, 35455, 0, 35.50, 'EUR');
INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES (1, PARSEDATETIME('2020-06-14 15:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2020-06-14 18:30:00', 'yyyy-MM-dd HH:mm:ss'), 2, 35455, 1, 25.45, 'EUR');
INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES (1, PARSEDATETIME('2020-06-15 00:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2020-06-15 11:00:00', 'yyyy-MM-dd HH:mm:ss'), 3, 35455, 1, 30.50, 'EUR');
INSERT INTO PRICES (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR) VALUES (1, PARSEDATETIME('2020-06-15 16:00:00', 'yyyy-MM-dd HH:mm:ss'), PARSEDATETIME('2020-12-31 23:59:59', 'yyyy-MM-dd HH:mm:ss'), 4, 35455, 1, 38.95, 'EUR');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);

INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 3);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1);