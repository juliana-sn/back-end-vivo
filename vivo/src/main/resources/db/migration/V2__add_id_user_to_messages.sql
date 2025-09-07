ALTER TABLE messages
ADD COLUMN id_user INTEGER;

ALTER TABLE messages
ADD CONSTRAINT messages_id_user_nn CHECK (id_user IS NOT NULL);

ALTER TABLE messages
ADD CONSTRAINT messages_id_user_fk FOREIGN KEY (id_user) REFERENCES users(id_user);
