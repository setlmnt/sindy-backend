-- Índices para a tabela addresses
CREATE INDEX idx_addresses_city ON addresses (city);
CREATE INDEX idx_addresses_neighborhood ON addresses (neighborhood);
CREATE INDEX idx_addresses_zip_code ON addresses (zip_code);

-- Índices para a tabela syndicate
CREATE INDEX idx_syndicate_name ON syndicate (name);

-- Índices para a tabela users
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_name ON users (name);

-- Índices para a tabela otps
CREATE INDEX idx_otps_code ON otps (code);

-- Índices para a tabela associates
CREATE INDEX idx_associates_cpf ON associates (cpf);
CREATE INDEX idx_associates_email ON associates (email);
CREATE INDEX idx_associates_rg ON associates (rg);
CREATE INDEX idx_associates_name ON associates (name);
CREATE INDEX idx_associates_union_card ON associates (union_card);

-- Índices para a tabela templates
CREATE INDEX idx_templates_name ON templates (name);

-- Índices para a tabela communication_histories
CREATE INDEX idx_communication_histories_sender_email ON communication_histories (sender_email);
CREATE INDEX idx_communication_histories_sender_name ON communication_histories (sender_name);
CREATE INDEX idx_communication_histories_status ON communication_histories (status);
