create table TB_JOB_TITLE
(
  ID BIGINT auto_increment
    primary key,
  TITLE VARCHAR(255)
);

create table TB_USER
(
  ID BIGINT auto_increment
    primary key,
  NAME VARCHAR(255)
);

create table TB_LEAD
(
  ID BIGINT auto_increment
    primary key,
  COMPANY VARCHAR(255),
  DESCRIPTION VARCHAR(255),
  LEAD_STATUS VARCHAR(255),
  VENDOR_ID BIGINT,
  constraint FK6LJX2V6CNWQT3XPSO120NYD20
    foreign key (VENDOR_ID) references TB_USER
);

create table REVINFO
(
	REV INTEGER auto_increment
		primary key,
	REVTSTMP BIGINT
);

create table TB_USER_JOB_TITLE
(
  USER_ID BIGINT not null,
  JOB_TITLE_LIST_ID BIGINT not null,
  constraint FKHKHB5G2Y43GMPCDMKRBG7A64G
    foreign key (JOB_TITLE_LIST_ID) references TB_JOB_TITLE,
  constraint FKNUQ4GPYICVV0VDNUPQV0XF8NB
    foreign key (USER_ID) references TB_USER
);

create table TB_JOB_TITLE_AUD
(
	ID BIGINT not null,
	REV INTEGER not null,
	REVTYPE TINYINT,
	TITLE VARCHAR(255),
	primary key (ID, REV),
	constraint FKCPU4EE3S3M8UR0H280UJFNXDF
		foreign key (REV) references REVINFO
);

create table TB_LEAD_AUD
(
	ID BIGINT not null,
	REV INTEGER not null,
	REVTYPE TINYINT,
	COMPANY VARCHAR(255),
	DESCRIPTION VARCHAR(255),
	LEAD_STATUS VARCHAR(255),
	VENDOR_ID BIGINT,
	primary key (ID, REV),
	constraint FKQKRY1H12IQTSB35TBRBEXTUF8
		foreign key (REV) references REVINFO
);

create table TB_USER_AUD
(
	ID BIGINT not null,
	REV INTEGER not null,
	REVTYPE TINYINT,
	NAME VARCHAR(255),
	primary key (ID, REV),
	constraint FKORAGLDCT5EJP082BGRI3PLWWP
		foreign key (REV) references REVINFO
);

create table TB_USER_JOB_TITLE_AUD
(
	REV INTEGER not null,
	USER_ID BIGINT not null,
	JOB_TITLE_LIST_ID BIGINT not null,
	REVTYPE TINYINT,
	primary key (REV, USER_ID, JOB_TITLE_LIST_ID),
	constraint FKL4EMPDLTQCATPMG6ROLFXF8QL
		foreign key (REV) references REVINFO
);