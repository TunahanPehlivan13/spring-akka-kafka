CREATE KEYSPACE IF NOT EXISTS emails_keyspace
 WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE IF NOT EXISTS emails_keyspace.email (
  email text PRIMARY KEY,
  count int,
);