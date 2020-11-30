drop table if exists CREDIT_CARD;

create table CREDIT_CARD
(
    ID                          UUID NOT NULL DEFAULT RANDOM_UUID(),
    CARD_OWNER_NAME             VARCHAR2(255 CHAR) NOT NULL,
    CREDIT_CARD_NUMBER           VARCHAR2(19 CHAR) NOT NULL,
    ACCOUNT_LIMIT               DECIMAL(20,2) NOT NULL,
    ACCOUNT_BALANCE             DECIMAL(20,2) NOT NULL DEFAULT 0.0,
    ACCOUNT_CURRENCY            CHAR (3) NOT NULL DEFAULT '£',
    CREATED_TIMESTAMP           TIMESTAMP (6) NOT NULL,
    CONSTRAINT PK_CREDIT_CARD PRIMARY KEY (ID),
    CONSTRAINT UK_CREDIT_CARD_NUMBER UNIQUE (CREDIT_CARD_NUMBER)
);

insert into CREDIT_CARD(card_owner_name, credit_card_number, account_limit, created_timestamp)
 values ('shagun', '11-22',10.0,now());

 --commit;