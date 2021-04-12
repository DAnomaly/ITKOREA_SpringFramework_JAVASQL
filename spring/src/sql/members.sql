DROP TABLE board;
DROP TABLE members;

CREATE TABLE members(
	mno NUMBER,					-- 회원번호 (시퀀스 사용)
	mid VARCHAR2(30) NOT NULL,	-- 아이디
	mname VARCHAR2(30),			-- 이름
	memail VARCHAR2(100), 		-- 이메일
	mdate DATE					-- 가입일
);

ALTER TABLE members ADD CONSTRAINT members_pk PRIMARY KEY (mno);
ALTER TABLE members ADD CONSTRAINT members_id_uq UNIQUE (mid);
ALTER TABLE members ADD CONSTRAINT members_email_uq UNIQUE (memail);

CREATE SEQUENCE members_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;