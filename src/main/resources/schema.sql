
CREATE TABLE IF NOT EXISTS PAUTA (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  theme VARCHAR(100) NOT NULL,
  session_started_time TIMESTAMP NULL,
  ends_in INT NULL
);