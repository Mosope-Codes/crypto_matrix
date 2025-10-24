ALTER TABLE key_matrices 
DROP COLUMN matrix_data;

ALTER TABLE key_matrices 
ADD COLUMN public_key_matrix VARCHAR(255) NOT NULL,
ADD COLUMN private_key_matrix VARCHAR(255) NOT NULL;