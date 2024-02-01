-- Drop the existing table if it exists
DROP TABLE IF EXISTS emails;

-- Create the templates table
CREATE TABLE templates (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at   DATETIME(6) NOT NULL,
    deleted      BIT NOT NULL,
    deleted_at   DATETIME(6),
    updated_at   DATETIME(6),
    name         VARCHAR(255) NOT NULL UNIQUE,
    body         TEXT
);

-- Create the communication_histories table
CREATE TABLE communication_histories (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at   DATETIME(6) NOT NULL,
    deleted      BIT NOT NULL,
    deleted_at   DATETIME(6),
    updated_at   DATETIME(6),
    sender       VARCHAR(255) NOT NULL,
    subject      VARCHAR(255),
    message      TEXT NOT NULL,
    status       VARCHAR(50) NOT NULL,
    CONSTRAINT CK_valid_status CHECK (status IN ('S', 'E', 'P'))
);

-- Create the communication_history_templates table
CREATE TABLE communication_history_templates (
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
    communication_history_id  BIGINT NOT NULL,
    template_id               BIGINT NOT NULL,
    FOREIGN KEY (communication_history_id) REFERENCES communication_histories(id),
    FOREIGN KEY (template_id) REFERENCES templates(id)
);

-- Create the recipients table
CREATE TABLE recipients (
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at                DATETIME(6) NOT NULL,
    deleted                   BIT NOT NULL,
    deleted_at                DATETIME(6),
    updated_at                DATETIME(6),
    communication_history_id  BIGINT NOT NULL,
    email                     VARCHAR(255) NOT NULL,
    name                      VARCHAR(255) NOT NULL,
    FOREIGN KEY (communication_history_id) REFERENCES communication_histories(id)
);
