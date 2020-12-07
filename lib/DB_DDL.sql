CREATE TABLE CUSTOMER(
      oid int NOT NULL PRIMARY KEY,
      name varchar(20) NOT NULL,
      phonenumber varchar(20) NOT NULL,
      count int NOT NULL
);

CREATE TABLE STAFF(
    oid int NOT NULL PRIMARY KEY,
    id varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    name varchar(20) NOT NULL,
    "check" varchar(20) NOT NULL
);

CREATE TABLE "Table"(
    oid int NOT NULL PRIMARY KEY,
    "number" int NOT NULL,
    places int NOT NULL
);

CREATE TABLE RESERVATION(
    oid int NOT NULL PRIMARY KEY,
    covers int NOT NULL,
    "date" DATE NOT NULL,
    "time" TIMESTAMP NOT NULL,
    table_id int NOT NULL,
    customer_id int NOT NULL,
    arrivaltime TIMESTAMP,
    leavetime TIMESTAMP,
    staffid varchar(20) NOT NULL
);

CREATE TABLE WALKIN(
    oid int NOT NULL PRIMARY KEY,
    covers int NOT NULL,
    "date" DATE NOT NULL,
    "time" TIMESTAMP NOT NULL,
    table_id int NOT NULL,
    leavetime TIMESTAMP,
    staffid varchar(20) NOT NULL
);

CREATE TABLE OID(
    last_id int NOT NULL
);

CREATE SEQUENCE CUSTOMEROID
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
    
CREATE SEQUENCE RESTOID
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;
    
CREATE SEQUENCE STAFFOID
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

CREATE SEQUENCE TABLEOID
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

INSERT INTO OID values(1);

INSERT INTO staff values(staffoid.nextval,'test','pass','Å×½ºÆ®','true');