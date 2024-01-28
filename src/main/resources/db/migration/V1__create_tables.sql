CREATE TABLE addresses
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at   DATETIME(6)  NOT NULL,
    deleted      BIT          NOT NULL,
    deleted_at   DATETIME(6)  NULL,
    updated_at   DATETIME(6)  NULL,
    city         VARCHAR(255) NOT NULL,
    complement   VARCHAR(255) NULL,
    neighborhood VARCHAR(255) NOT NULL,
    number       VARCHAR(255) NULL,
    street       VARCHAR(255) NULL,
    zip_code     VARCHAR(255) NOT NULL
);

CREATE TABLE affiliations
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at  DATETIME(6)  NOT NULL,
    deleted     BIT          NOT NULL,
    deleted_at  DATETIME(6)  NULL,
    updated_at  DATETIME(6)  NULL,
    father_name VARCHAR(255) NOT NULL,
    mother_name VARCHAR(255) NOT NULL
);

CREATE TABLE dependents
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at       DATETIME(6)  NOT NULL,
    deleted          BIT          NOT NULL,
    deleted_at       DATETIME(6)  NULL,
    updated_at       DATETIME(6)  NULL,
    female_children  INT          NULL,
    male_children    INT          NULL,
    minor_children   INT          NULL,
    other_dependents INT          NULL,
    spouse           VARCHAR(255) NULL
);

CREATE TABLE emails
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6)  NOT NULL,
    deleted    BIT          NOT NULL,
    deleted_at DATETIME(6)  NULL,
    updated_at DATETIME(6)  NULL,
    email_from VARCHAR(255) NOT NULL,
    email_to   VARCHAR(255) NOT NULL,
    owner      VARCHAR(255) NOT NULL,
    status     VARCHAR(255) NOT NULL,
    subject    VARCHAR(255) NOT NULL,
    text       TEXT         NOT NULL
);

CREATE TABLE local_offices
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6)  NOT NULL,
    deleted    BIT          NOT NULL,
    deleted_at DATETIME(6)  NULL,
    updated_at DATETIME(6)  NULL,
    name       VARCHAR(255) NOT NULL
);

CREATE TABLE places_of_birth
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6)  NOT NULL,
    deleted    BIT          NOT NULL,
    deleted_at DATETIME(6)  NULL,
    updated_at DATETIME(6)  NULL,
    city       VARCHAR(255) NOT NULL,
    state      VARCHAR(255) NOT NULL
);

CREATE TABLE syndicate
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at      DATETIME(6)  NOT NULL,
    deleted         BIT          NOT NULL,
    deleted_at      DATETIME(6)  NULL,
    updated_at      DATETIME(6)  NULL,
    cnpj            VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    foundation_date DATE         NULL,
    name            VARCHAR(255) NOT NULL,
    phone           VARCHAR(255) NOT NULL,
    address_id      BIGINT       NULL,
    CONSTRAINT uk_syndicate_address
        UNIQUE (address_id),
    CONSTRAINT uk_syndicate_cnpj
        UNIQUE (cnpj),
    CONSTRAINT fk_syndicate_address
        FOREIGN KEY (address_id) REFERENCES addresses (id)
);

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6)   NOT NULL,
    deleted    BIT           NOT NULL,
    deleted_at DATETIME(6)   NULL,
    updated_at DATETIME(6)   NULL,
    email      VARCHAR(255)  NOT NULL,
    password   VARCHAR(255)  NOT NULL,
    role       ENUM ('USER') NOT NULL,
    name       VARCHAR(255)  NULL,
    CONSTRAINT uk_user_name
        UNIQUE (name)
);

CREATE TABLE otps
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6)  NOT NULL,
    deleted    BIT          NOT NULL,
    deleted_at DATETIME(6)  NULL,
    updated_at DATETIME(6)  NULL,
    code       VARCHAR(255) NOT NULL,
    user_id    BIGINT       NULL,
    CONSTRAINT fk_otp_user
        FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE work_records
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME(6)  NOT NULL,
    deleted    BIT          NOT NULL,
    deleted_at DATETIME(6)  NULL,
    updated_at DATETIME(6)  NULL,
    number     BIGINT       NOT NULL,
    series     VARCHAR(255) NOT NULL
);

CREATE TABLE associates
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at        DATETIME(6)                                              NOT NULL,
    deleted           BIT                                                      NOT NULL,
    deleted_at        DATETIME(6)                                              NULL,
    updated_at        DATETIME(6)                                              NULL,
    association_at    DATE                                                     NOT NULL,
    birth_at          DATE                                                     NOT NULL,
    cpf               VARCHAR(255)                                             NOT NULL,
    email             VARCHAR(255)                                             NULL,
    is_literate       BIT                                                      NOT NULL,
    is_paid           BIT                                                      NOT NULL,
    is_voter          BIT                                                      NOT NULL,
    marital_status    ENUM ('DIVORCED', 'MARRIED', 'NEVER_MARRIED', 'WIDOWED') NOT NULL,
    name              VARCHAR(255)                                             NOT NULL,
    nationality       VARCHAR(255)                                             NOT NULL,
    phone             VARCHAR(255)                                             NULL,
    profession        VARCHAR(255)                                             NOT NULL,
    rg                VARCHAR(255)                                             NOT NULL,
    union_card        BIGINT                                                   NOT NULL,
    workplace         VARCHAR(255)                                             NOT NULL,
    address_id        BIGINT                                                   NULL,
    affiliation_id    BIGINT                                                   NOT NULL,
    dependents_id     BIGINT                                                   NULL,
    local_office_id   BIGINT                                                   NULL,
    place_of_birth_id BIGINT                                                   NOT NULL,
    file_id           BIGINT                                                   NULL,
    work_record_id    BIGINT                                                   NOT NULL,
    CONSTRAINT uk_associate_dependents
        UNIQUE (dependents_id),
    CONSTRAINT uk_associate_rg
        UNIQUE (rg),
    CONSTRAINT uk_associate_work_record
        UNIQUE (work_record_id),
    CONSTRAINT uk_associate_union_card
        UNIQUE (union_card),
    CONSTRAINT uk_associate_address
        UNIQUE (address_id),
    CONSTRAINT uk_associate_place_of_birth
        UNIQUE (place_of_birth_id),
    constraint uk_associate_file
        unique (file_id),
    constraint uk_associate_cpf
        unique (cpf),
    constraint uk_associate_local_office
        unique (affiliation_id),
    CONSTRAINT fk_associate_work_record
        FOREIGN KEY (work_record_id) REFERENCES work_records (id),
    CONSTRAINT fk_associate_local_office
        FOREIGN KEY (local_office_id) REFERENCES local_offices (id),
    CONSTRAINT fk_associate_place_of_birth
        FOREIGN KEY (place_of_birth_id) REFERENCES places_of_birth (id),
    CONSTRAINT fk_associate_address
        FOREIGN KEY (address_id) REFERENCES addresses (id),
    CONSTRAINT fk_associate_affiliation
        FOREIGN KEY (affiliation_id) REFERENCES affiliations (id),
    CONSTRAINT fk_associate_dependents
        FOREIGN KEY (dependents_id) REFERENCES dependents (id)
);

CREATE TABLE files
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at    DATETIME(6)  NOT NULL,
    deleted       BIT          NOT NULL,
    deleted_at    DATETIME(6)  NULL,
    updated_at    DATETIME(6)  NULL,
    archive_name  VARCHAR(255) NOT NULL,
    content_type  VARCHAR(255) NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    size          BIGINT       NOT NULL,
    url           VARCHAR(255) NOT NULL,
    associate_id  BIGINT       NULL,
    CONSTRAINT fk_file_associate
        FOREIGN KEY (associate_id) REFERENCES associates (id)
);

ALTER TABLE associates
    ADD CONSTRAINT fk_associate_file
        FOREIGN KEY (file_id) REFERENCES files (id);

CREATE TABLE monthly_fees
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at         DATETIME(6)    NOT NULL,
    deleted            BIT            NOT NULL,
    deleted_at         DATETIME(6)    NULL,
    updated_at         DATETIME(6)    NULL,
    fee_value          DECIMAL(38, 2) NOT NULL,
    final_date         DATE           NOT NULL,
    initial_date       DATE           NOT NULL,
    registration_value DECIMAL(38, 2) NULL,
    total_fee_value    DECIMAL(38, 2) NOT NULL,
    total_months_paid  BIGINT         NOT NULL,
    associate_id       BIGINT         NOT NULL,
    CONSTRAINT fk_fee_associate
        FOREIGN KEY (associate_id) REFERENCES associates (id)
);
