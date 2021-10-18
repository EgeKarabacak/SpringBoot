INSERT INTO user (dtype, username, password, enabled) VALUES
  ('Administrator', 'admin', 'admin', true);

INSERT INTO user_roles (user_uid, roles) VALUES
  ('1', 'ROLE_ADMIN');
