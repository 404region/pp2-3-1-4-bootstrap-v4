--Table users
CREATE TABLE users (
    id bigint not null AUTO_INCREMENT PRIMARY_KEY,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL
)

ENGINE=InnoDb

-- Table roles
CREATE TABLE roles (
    id bigint not null AUTO_INCREMENT PRIMARY_KEY,
    name varchar(100) NOT NULL
)
ENGINE=InnoDb


-- Table user_roles
CREATE TABLE user_roles (
    user_id bigint not null,
    role_id bigint not null,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
)
ENGINE=InnoDb


--Insert Data
INSERT INTO users values (1, 'proselyte', '');
INSERT INTO roles values (1, 'ROLE_USER');
INSERT INTO roles values (1, 'ROLE_ADMIN');

INSERT INTO user_roles() VALUES (1,2)
