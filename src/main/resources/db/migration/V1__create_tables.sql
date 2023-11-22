-- Addresses Table
CREATE TABLE addresses (
    id BIGINT NOT NULL AUTO_INCREMENT,
    city VARCHAR(255) NOT NULL,
    complement VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    neighborhood VARCHAR(255) NOT NULL,
    number VARCHAR(255),
    street VARCHAR(255) NOT NULL,
    updated_at DATETIME(6),
    zip_code VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Affiliations Table
CREATE TABLE affiliations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    father_name VARCHAR(255) NOT NULL,
    mother_name VARCHAR(255) NOT NULL,
    updated_at DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Dependents Table
CREATE TABLE dependents (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    female_children INTEGER NOT NULL,
    male_children INTEGER NOT NULL,
    minor_children INTEGER NOT NULL,
    other_dependents INTEGER NOT NULL,
    updated_at DATETIME(6),
    spouse VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Images Table
CREATE TABLE images (
    id BIGINT NOT NULL AUTO_INCREMENT,
    archive_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    original_name VARCHAR(255) NOT NULL,
    size BIGINT NOT NULL,
    updated_at DATETIME(6),
    url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Local Offices Table
CREATE TABLE local_offices (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    name VARCHAR(255) NOT NULL,
    updated_at DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Places of Birth Table
CREATE TABLE places_of_birth (
     id BIGINT NOT NULL AUTO_INCREMENT,
     city VARCHAR(255) NOT NULL,
     created_at DATETIME(6) NOT NULL,
     deleted BIT NOT NULL,
     deleted_at DATETIME(6),
     state VARCHAR(255) NOT NULL,
     updated_at DATETIME(6),
     PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Work Records Table
CREATE TABLE work_records (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    number BIGINT NOT NULL,
    series VARCHAR(255) NOT NULL,
    updated_at DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Associates Table
CREATE TABLE associates (
    id BIGINT NOT NULL AUTO_INCREMENT,
    association_at DATE NOT NULL,
    birth_at DATE NOT NULL,
    cpf VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    is_literate BIT NOT NULL,
    is_voter BIT NOT NULL,
    marital_status ENUM ('DIVORCED', 'MARRIED', 'NEVER_MARRIED', 'WIDOWED') NOT NULL,
    name VARCHAR(255) NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    profession VARCHAR(255) NOT NULL,
    rg VARCHAR(255) NOT NULL UNIQUE,
    union_card BIGINT NOT NULL UNIQUE,
    updated_at DATETIME(6),
    workplace VARCHAR(255) NOT NULL,
    address_id BIGINT,
    affiliation_id BIGINT NOT NULL,
    dependents_id BIGINT,
    local_office_id BIGINT,
    image_id BIGINT,
    place_of_birth_id BIGINT NOT NULL,
    work_record_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_associates_cpf (cpf),
    INDEX idx_associates_union_card (union_card),
    INDEX idx_associates_rg (rg),
    INDEX idx_associates_address (address_id),
    INDEX idx_associates_affiliation (affiliation_id),
    INDEX idx_associates_dependents (dependents_id),
    INDEX idx_associates_local_office (local_office_id),
    INDEX idx_associates_image (image_id),
    INDEX idx_associates_place_of_birth (place_of_birth_id),
    INDEX idx_associates_work_record (work_record_id),
    FOREIGN KEY (address_id) REFERENCES addresses (id),
    FOREIGN KEY (affiliation_id) REFERENCES affiliations (id),
    FOREIGN KEY (dependents_id) REFERENCES dependents (id),
    FOREIGN KEY (local_office_id) REFERENCES local_offices (id),
    FOREIGN KEY (image_id) REFERENCES images (id),
    FOREIGN KEY (place_of_birth_id) REFERENCES places_of_birth (id),
    FOREIGN KEY (work_record_id) REFERENCES work_records (id)
) ENGINE=InnoDB;

-- Monthly Fees Table
CREATE TABLE monthly_fees (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    fee_value DECIMAL(38,2) NOT NULL,
    total_amount DECIMAL(38,2) NOT NULL,
    total_months_paid INTEGER NOT NULL,
    updated_at DATETIME(6),
    associate_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_monthly_fees_associate (associate_id),
    FOREIGN KEY (associate_id) REFERENCES associates (id)
) ENGINE=InnoDB;

-- Monthly Fee Dates Table
CREATE TABLE monthly_fee_dates (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    deleted BIT NOT NULL,
    deleted_at DATETIME(6),
    month INTEGER NOT NULL,
    updated_at DATETIME(6),
    year INTEGER NOT NULL,
    monthly_fee_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_monthly_fee_dates_fee (monthly_fee_id),
    FOREIGN KEY (monthly_fee_id) REFERENCES monthly_fees (id)
) ENGINE=InnoDB;