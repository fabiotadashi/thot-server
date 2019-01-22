ALTER TABLE TB_USER ADD PASSWORD VARCHAR(255);
ALTER TABLE TB_USER_AUD ADD PASSWORD VARCHAR(255);

create table USER_ROLE_LIST
(
	USER_ID BIGINT not null,
	ROLE_LIST INTEGER,
	constraint FKLQB868DHPATXI3E1M1NU3UKR5
		foreign key (USER_ID) references TB_USER
);

create table USER_ROLE_LIST_AUD
(
	REV INTEGER not null,
	USER_ID BIGINT not null,
	ROLE_LIST INTEGER not null,
	REVTYPE TINYINT,
	primary key (REV, USER_ID, ROLE_LIST),
	constraint FKOX6XYY64FYQ0Y3DVV5VE53A0H
		foreign key (REV) references REVINFO
);

