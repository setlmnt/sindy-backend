CREATE INDEX idx_address_city ON addresses (city);
CREATE INDEX idx_address_neighborhood ON addresses (neighborhood);
CREATE INDEX idx_address_zip_code ON addresses (zip_code);

CREATE INDEX idx_email_from ON emails (email_from);
CREATE INDEX idx_email_to ON emails (email_to);
CREATE INDEX idx_email_owner ON emails (owner);
CREATE INDEX idx_email_status ON emails (status);
CREATE INDEX idx_email_subject ON emails (subject);

CREATE INDEX idx_place_of_birth_city ON places_of_birth (city);
CREATE INDEX idx_place_of_birth_state ON places_of_birth (state);

CREATE INDEX idx_syndicate_cnpj ON syndicate (cnpj);
CREATE INDEX idx_syndicate_email ON syndicate (email);
CREATE INDEX idx_syndicate_name ON syndicate (name);

CREATE INDEX idx_user_email ON users (email);
CREATE INDEX idx_user_role ON users (role);

CREATE INDEX idx_associate_cpf ON associates (cpf);
CREATE INDEX idx_associate_email ON associates (email);
CREATE INDEX idx_associate_name ON associates (name);
CREATE INDEX idx_associate_rg ON associates (rg);
CREATE INDEX idx_associate_union_card ON associates (union_card);

CREATE INDEX idx_file_associate_id ON files (associate_id);
