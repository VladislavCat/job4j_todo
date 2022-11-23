CREATE TABLE IF NOT EXISTS tasks (
   id SERIAL PRIMARY KEY,
   description TEXT NOT NULL,
   user_id INT NOT NULL REFERENCES users(id_user),
   created TIMESTAMP NOT NULL,
   done BOOLEAN NOT NULL
);