CREATE TABLE IF NOT EXISTS organisations (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    UNIQUE(name)
);
CREATE TABLE IF NOT EXISTS users (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    organisation_id INTEGER NOT NULL,
    UNIQUE(username, email),
    CONSTRAINT fk_organisation_user_id
    FOREIGN KEY (organisation_id)
    REFERENCES organisations(id)
    ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS roles (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    UNIQUE(name)
);
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id INTEGER,
    permissions ENUM('USER_READ', 'ORGANISATION_READ', 'ROLE_READ', 'USER_WRITE', 'ORGANISATION_WRITE', 'ROLE_WRITE'),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_role_id_permissions PRIMARY KEY(role_id, permissions)
);
CREATE TABLE IF NOT EXISTS users_roles (
    user_id INTEGER,
    role_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_user_role_id PRIMARY KEY(user_id, role_id)
);