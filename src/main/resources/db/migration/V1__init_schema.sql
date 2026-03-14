CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rule (
    id BIGSERIAL PRIMARY KEY,
    article_separator VARCHAR(255),
    term_separator VARCHAR(255),
    definition_separator VARCHAR(255)
);

CREATE TABLE electronic_dictionary (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    path_to_file VARCHAR(500),
    language VARCHAR(50),
    type VARCHAR(50) NOT NULL,
    created_by BIGINT REFERENCES users(id),
    rule_id BIGINT REFERENCES rule(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE thesaurus (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    path_to_file VARCHAR(500),
    language VARCHAR(50),
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE internet_resource (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(500) NOT NULL,
    language VARCHAR(50)
);
