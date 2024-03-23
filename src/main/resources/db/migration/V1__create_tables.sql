CREATE TABLE addresses
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP(6)  NOT NULL,
    deleted      BOOLEAN       NOT NULL,
    deleted_at   TIMESTAMP(6),
    updated_at   TIMESTAMP(6),
    city         VARCHAR(255) NOT NULL,
    complement   VARCHAR(255),
    neighborhood VARCHAR(255) NOT NULL,
    number       VARCHAR(255),
    street       VARCHAR(255),
    zip_code     VARCHAR(255) NOT NULL
);

CREATE TABLE affiliations
(
    id          BIGSERIAL PRIMARY KEY,
    created_at  TIMESTAMP(6)  NOT NULL,
    deleted     BOOLEAN       NOT NULL,
    deleted_at  TIMESTAMP(6),
    updated_at  TIMESTAMP(6),
    father_name VARCHAR(255) NOT NULL,
    mother_name VARCHAR(255) NOT NULL
);

CREATE TABLE dependents
(
    id               BIGSERIAL PRIMARY KEY,
    created_at       TIMESTAMP(6)  NOT NULL,
    deleted          BOOLEAN       NOT NULL,
    deleted_at       TIMESTAMP(6),
    updated_at       TIMESTAMP(6),
    female_children  INT,
    male_children    INT,
    minor_children   INT,
    other_dependents INT,
    spouse           VARCHAR(255)
);

CREATE TABLE local_offices
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP(6)  NOT NULL,
    deleted    BOOLEAN       NOT NULL,
    deleted_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    name       VARCHAR(255) NOT NULL
);

CREATE TABLE places_of_birth
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP(6)  NOT NULL,
    deleted    BOOLEAN       NOT NULL,
    deleted_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    city       VARCHAR(255) NOT NULL,
    state      VARCHAR(255) NOT NULL
);

CREATE TABLE syndicate
(
    id              BIGSERIAL PRIMARY KEY,
    created_at      TIMESTAMP(6)  NOT NULL,
    deleted         BOOLEAN       NOT NULL,
    deleted_at      TIMESTAMP(6),
    updated_at      TIMESTAMP(6),
    cnpj            VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    foundation_date DATE,
    name            VARCHAR(255) NOT NULL,
    phone           VARCHAR(255) NOT NULL,
    address_id      BIGINT,
    CONSTRAINT uk_syndicate_address UNIQUE (address_id),
    CONSTRAINT uk_syndicate_cnpj UNIQUE (cnpj),
    CONSTRAINT fk_syndicate_address FOREIGN KEY (address_id) REFERENCES addresses (id)
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP(6)   NOT NULL,
    deleted    BOOLEAN           NOT NULL,
    deleted_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    email      VARCHAR(255)  NOT NULL,
    password   VARCHAR(255)  NOT NULL,
    role       VARCHAR(255) NOT NULL CHECK (role = 'USER'),
    name       VARCHAR(255),
    CONSTRAINT uk_user_name UNIQUE (name)
);

CREATE TABLE otps
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP(6)  NOT NULL,
    deleted    BOOLEAN          NOT NULL,
    deleted_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    code       VARCHAR(255) NOT NULL,
    user_id    BIGINT,
    CONSTRAINT fk_otp_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE work_records
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP(6)  NOT NULL,
    deleted    BOOLEAN          NOT NULL,
    deleted_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    number     BIGINT       NOT NULL,
    series     VARCHAR(255) NOT NULL
);

CREATE TABLE associates
(
    id                BIGSERIAL PRIMARY KEY,
    created_at        TIMESTAMP(6)                                              NOT NULL,
    deleted           BOOLEAN                                                      NOT NULL,
    deleted_at        TIMESTAMP(6),
    updated_at        TIMESTAMP(6),
    association_at    DATE                                                     NOT NULL,
    birth_at          DATE                                                     NOT NULL,
    cpf               VARCHAR(255)                                             NOT NULL,
    email             VARCHAR(255),
    is_literate       BOOLEAN                                                     NOT NULL,
    is_paid           BOOLEAN                                                     NOT NULL,
    is_voter          BOOLEAN                                                     NOT NULL,
    marital_status    VARCHAR(255)                                             NOT NULL CHECK (marital_status IN ('DIVORCED', 'MARRIED', 'NEVER_MARRIED', 'WIDOWED')),
    name              VARCHAR(255)                                             NOT NULL,
    nationality       VARCHAR(255)                                             NOT NULL,
    phone             VARCHAR(255),
    profession        VARCHAR(255)                                             NOT NULL,
    rg                VARCHAR(255)                                             NOT NULL,
    union_card        BIGINT                                                   NOT NULL,
    workplace         VARCHAR(255)                                             NOT NULL,
    address_id        BIGINT,
    affiliation_id    BIGINT                                                   NOT NULL,
    dependents_id     BIGINT,
    local_office_id   BIGINT,
    place_of_birth_id BIGINT                                                   NOT NULL,
    file_id           BIGINT,
    work_record_id    BIGINT                                                   NOT NULL,
    CONSTRAINT uk_associate_dependents UNIQUE (dependents_id),
    CONSTRAINT uk_associate_rg UNIQUE (rg),
    CONSTRAINT uk_associate_work_record UNIQUE (work_record_id),
    CONSTRAINT uk_associate_union_card UNIQUE (union_card),
    CONSTRAINT uk_associate_address UNIQUE (address_id),
    CONSTRAINT uk_associate_place_of_birth UNIQUE (place_of_birth_id),
    CONSTRAINT uk_associate_file UNIQUE (file_id),
    CONSTRAINT uk_associate_cpf UNIQUE (cpf),
    CONSTRAINT uk_associate_local_office UNIQUE (affiliation_id),
    CONSTRAINT fk_associate_work_record FOREIGN KEY (work_record_id) REFERENCES work_records (id),
    CONSTRAINT fk_associate_local_office FOREIGN KEY (local_office_id) REFERENCES local_offices (id),
    CONSTRAINT fk_associate_place_of_birth FOREIGN KEY (place_of_birth_id) REFERENCES places_of_birth (id),
    CONSTRAINT fk_associate_address FOREIGN KEY (address_id) REFERENCES addresses (id),
    CONSTRAINT fk_associate_affiliation FOREIGN KEY (affiliation_id) REFERENCES affiliations (id),
    CONSTRAINT fk_associate_dependents FOREIGN KEY (dependents_id) REFERENCES dependents (id)
);

CREATE TABLE files
(
    id            BIGSERIAL PRIMARY KEY,
    created_at    TIMESTAMP(6)  NOT NULL,
    deleted       BOOLEAN       NOT NULL,
    deleted_at    TIMESTAMP(6),
    updated_at    TIMESTAMP(6),
    archive_name  VARCHAR(255) NOT NULL,
    content_type  VARCHAR(255) NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    size          BIGINT       NOT NULL,
    url           VARCHAR(255) NOT NULL,
    associate_id  BIGINT,
    CONSTRAINT fk_file_associate FOREIGN KEY (associate_id) REFERENCES associates (id)
);

ALTER TABLE associates
    ADD CONSTRAINT fk_associate_file FOREIGN KEY (file_id) REFERENCES files (id);

CREATE TABLE monthly_fees
(
    id                 BIGSERIAL PRIMARY KEY,
    created_at         TIMESTAMP(6)    NOT NULL,
    deleted            BOOLEAN         NOT NULL,
    deleted_at         TIMESTAMP(6),
    updated_at         TIMESTAMP(6),
    fee_value          NUMERIC(38, 2) NOT NULL,
    final_date         DATE           NOT NULL,
    initial_date       DATE           NOT NULL,
    registration_value NUMERIC(38, 2),
    total_fee_value    NUMERIC(38, 2) NOT NULL,
    total_months_paid  BIGINT         NOT NULL,
    associate_id       BIGINT         NOT NULL,
    CONSTRAINT fk_fee_associate FOREIGN KEY (associate_id) REFERENCES associates (id)
);

CREATE TABLE templates (
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP(6) NOT NULL,
    deleted      BOOLEAN NOT NULL,
    deleted_at   TIMESTAMP(6),
    updated_at   TIMESTAMP(6),
    name         VARCHAR(255) NOT NULL UNIQUE,
    body         TEXT
);

CREATE TABLE communication_histories (
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP(6) NOT NULL,
    deleted      BOOLEAN NOT NULL,
    deleted_at   TIMESTAMP(6),
    updated_at   TIMESTAMP(6),
    sender_email VARCHAR(255) NOT NULL,
    sender_name  VARCHAR(255) NOT NULL,
    subject      VARCHAR(255),
    message      TEXT NOT NULL,
    status       VARCHAR(50) NOT NULL CHECK (status IN ('S', 'E', 'P'))
);

CREATE TABLE communication_history_templates (
    id                        BIGSERIAL PRIMARY KEY,
    communication_history_id  BIGINT NOT NULL,
    template_id               BIGINT NOT NULL,
    FOREIGN KEY (communication_history_id) REFERENCES communication_histories(id),
    FOREIGN KEY (template_id) REFERENCES templates(id)
);

CREATE TABLE recipients (
    id                        BIGSERIAL PRIMARY KEY,
    created_at                TIMESTAMP(6) NOT NULL,
    deleted                   BOOLEAN NOT NULL,
    deleted_at                TIMESTAMP(6),
    updated_at                TIMESTAMP(6),
    communication_history_id  BIGINT NOT NULL,
    email                     VARCHAR(255) NOT NULL,
    name                      VARCHAR(255) NOT NULL,
    FOREIGN KEY (communication_history_id) REFERENCES communication_histories(id)
);
