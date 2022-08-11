DROP TABLE IF EXISTS user_profile;
DROP TABLE IF EXISTS user_credentials;
DROP TABLE IF EXISTS user_svc_account;
DROP TABLE IF EXISTS user_audit;

CREATE TABLE IF NOT EXISTS user_profile (
   id serial not null,
   first_name varchar(255) not null,
   last_name varchar(255) not null,
   gender char(1) not null,
   birthday date not null,
   mobile_no varchar(50),
   email varchar(255) not null,
   email_verified char(1),
   create_time timestamp not null,
   last_access_time timestamp not null,
   salutation varchar(20),
   CONSTRAINT pk_user_profile PRIMARY KEY (id)
);
ALTER SEQUENCE user_profile_id_seq CACHE 10000;

CREATE TABLE IF NOT EXISTS user_credentials (
    user_id varchar(255) NOT NULL,
    password_hash varchar(255) NOT NULL,
    profile_id numeric NOT NULL,
    CONSTRAINT pk_user_credentials PRIMARY KEY (user_id)
);
CREATE INDEX NONCONCURRENTLY IF NOT EXISTS idx_user_credentials_profile_id ON user_credentials (profile_id);

CREATE TABLE IF NOT EXISTS user_svc_account (
    account_id numeric NOT NULL,
    user_id varchar(255) NOT NULL,
    create_time timestamp NOT NULL,
    last_access_time timestamp NOT NULL,
    user_verified char(1),
    svc_name varchar(255),
    svc_description varchar(255),
    CONSTRAINT pk_user_svc_account PRIMARY KEY (user_id, account_id)
);

CREATE TABLE IF NOT EXISTS user_audit (
    id serial NOT NULL,
    account_id numeric NOT NULL,
    user_id varchar(255) NOT NULL,
    action varchar(50),
    description varchar(255) NOT NULL,
    transaction_time timestamp NOT NULL,
    device varchar(255) NULL,
    client_ip varchar(255) NULL,
    location varchar(255) NULL,
    CONSTRAINT pk_user_audit PRIMARY KEY (id)
);
ALTER SEQUENCE user_audit_id_seq CACHE 10000;
CREATE INDEX NONCONCURRENTLY IF NOT EXISTS idx_user_audit ON user_audit (user_id);