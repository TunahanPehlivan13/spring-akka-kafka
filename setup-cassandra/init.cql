CREATE KEYSPACE IF NOT EXISTS emails_keyspace
 WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE IF NOT EXISTS emails_keyspace.email (
  uuid timeuuid PRIMARY KEY,
  text text
);

CREATE INDEX email_ix ON emails_keyspace.email (text);