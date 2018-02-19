CREATE TABLE USERS
	(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY
	CONSTRAINT USER_PK PRIMARY KEY,
	
	USERNAME VARCHAR(255) UNIQUE,
	PASSWORD VARCHAR(255) NOT NULL
	);

ALTER TABLE IDENTITIES
ADD COLUMN USER_ID INT CONSTRAINT USER_FK REFERENCES USERS(ID) ON DELETE SET NULL;

alter table IDENTITIES
DROP COLUMN USER_ID;