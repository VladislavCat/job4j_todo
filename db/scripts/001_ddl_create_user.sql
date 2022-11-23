CREATE TABLE IF NOT EXISTS users (
  id_user SERIAL PRIMARY KEY,
  username VARCHAR unique NOT NULL,
  password TEXT NOT NULL
);