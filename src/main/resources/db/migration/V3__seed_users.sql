INSERT INTO users
(role, nickname, login_id, password, name, gender, birth_date, address, phone, email)
VALUES
    ('admin', 'admin1', 'admin',  '$2a$10$wFz4bTnVq4GgY2vYxPzG2e2r8eGg2Q3bUO6E0s8m3qvZ9z8nZq9sS', '관리자', 'male', '2000-01-01', 'Seoul', '010-0000-0000', 'admin@example.com'),
    ('user',  'user1',  'user',   '$2a$10$wFz4bTnVq4GgY2vYxPzG2e2r8eGg2Q3bUO6E0s8m3qvZ9z8nZq9sS', '사용자', 'female','2000-01-02', 'Seoul', '010-1111-1111', 'user1@example.com');