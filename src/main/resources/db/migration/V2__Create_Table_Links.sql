
create table CIUSKY_LINK
(
    ID BIGINT not null references CIUSKY on delete cascade,
    LINK_ID BIGINT auto_increment,
    URL CHARACTER VARYING(2000),
    primary key (ID, LINK_ID)
);