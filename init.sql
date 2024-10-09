CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(50) NOT NULL
    );

-- Insert frontdesk user
INSERT INTO users (username, password) VALUES ('frontdesk', 'hej123') ON DUPLICATE KEY UPDATE password = 'hej123';