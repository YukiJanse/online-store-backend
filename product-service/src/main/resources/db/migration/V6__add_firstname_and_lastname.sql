ALTER TABLE users
ADD COLUMN first_name VARCHAR(50) DEFAULT 'firstname',
ADD COLUMN last_name VARCHAR(50) DEFAULT 'lastname';