-- Creates key_matrices, users, encryptions and decryptions in one migration

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS key_matrices (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  matrix_data VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS users (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  key_matrix_id UUID NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  CONSTRAINT fk_users_key_matrix
    FOREIGN KEY (key_matrix_id)
    REFERENCES key_matrices(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS encryptions (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  plaintext TEXT NOT NULL,
  ciphertext TEXT NOT NULL,
  key_matrix_id UUID NOT NULL,
  user_id UUID NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  CONSTRAINT fk_encryptions_key_matrix
    FOREIGN KEY (key_matrix_id)
    REFERENCES key_matrices(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  CONSTRAINT fk_encryptions_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_encryptions_user_id ON encryptions(user_id);
CREATE INDEX IF NOT EXISTS idx_encryptions_key_matrix_id ON encryptions(key_matrix_id);

CREATE TABLE IF NOT EXISTS decryptions (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  ciphertext TEXT NOT NULL,
  plaintext TEXT NOT NULL,
  key_matrix_id UUID NOT NULL,
  user_id UUID NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  CONSTRAINT fk_decryptions_key_matrix
    FOREIGN KEY (key_matrix_id)
    REFERENCES key_matrices(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  CONSTRAINT fk_decryptions_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_decryptions_user_id ON decryptions(user_id);
CREATE INDEX IF NOT EXISTS idx_decryptions_key_matrix_id ON decryptions(key_matrix_id);