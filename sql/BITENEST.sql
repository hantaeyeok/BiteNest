-- 1. RECIPE_CATEGORY1_TB ���̺� ����
CREATE TABLE RECIPE_CATEGORY1_TB (
    CATEGORY1_CD NUMBER PRIMARY KEY,  -- 1�� ī�װ��� �ڵ� (�⺻ Ű)
    CATEGORY1_NAME VARCHAR2(255) NOT NULL  -- 1�� ī�װ��� �̸�
);

-- 2. RECIPE_CATEGORY2_TB ���̺� ����
CREATE TABLE RECIPE_CATEGORY2_TB (
    CATEGORY2_CD NUMBER PRIMARY KEY,  -- 2�� ī�װ��� �ڵ� (�⺻ Ű)
    CATEGORY2_NAME VARCHAR2(255) NOT NULL,  -- 2�� ī�װ��� �̸�
    CATEGORY1_CD NUMBER,  -- 1�� ī�װ��� �ڵ� (�ܷ� Ű)
    CONSTRAINT FK_CATEGORY2_CATEGORY1 FOREIGN KEY (CATEGORY1_CD) REFERENCES RECIPE_CATEGORY1_TB (CATEGORY1_CD)  -- �ܷ� Ű ���� ����
);

-- 3. RECIPE_TB ���̺� ����
CREATE TABLE RECIPE_TB (
    RECIPE_CD NUMBER PRIMARY KEY,  -- ������ �ڵ� (�⺻ Ű)
    USER_NO NUMBER NULL,  -- ����� ��ȣ (NULL ���)
    RECIPE_NM VARCHAR2(255) NOT NULL,  -- ������ �̸�
    RECIPE_DESCRIPTION VARCHAR2(255),  -- ������ ����
    ESTIMATED_TIME NUMBER,  -- ���� �ҿ� �ð�
    COOKING_SERVINGS NUMBER,  -- ���� �ο�
    RECIPE_MAIN_IMAGE VARCHAR2(255),  -- ���� �̹��� ���
    CATEGORY1_CD NUMBER,  -- 1�� ī�װ��� �ڵ�
    CATEGORY2_CD NUMBER,  -- 2�� ī�װ��� �ڵ�
    CREATED_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- ���� �Ͻ�
    UPDATED_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- ���� �Ͻ�
    CONSTRAINT FK_RECIPE_CATEGORY1 FOREIGN KEY (CATEGORY1_CD) REFERENCES RECIPE_CATEGORY1_TB (CATEGORY1_CD),  -- 1�� ī�װ��� �ܷ� Ű
    CONSTRAINT FK_RECIPE_CATEGORY2 FOREIGN KEY (CATEGORY2_CD) REFERENCES RECIPE_CATEGORY2_TB (CATEGORY2_CD)  -- 2�� ī�װ��� �ܷ� Ű
);

-- 4. RECIPE_INGREDIENT_TB ���̺� ����
CREATE TABLE RECIPE_INGREDIENT_TB (
    INGREDIENT_CD NUMBER PRIMARY KEY,  -- ��� �ڵ� (�⺻ Ű)
    INGREDIENT_NM VARCHAR2(255) NOT NULL  -- ��� �̸�
);

-- 5. RECIPE_INGREDIENT_TYPE_TB ���̺� ����
CREATE TABLE RECIPE_INGREDIENT_TYPE_TB (
    TYPE_CD NUMBER PRIMARY KEY,  -- ��� ���� �ڵ� (�⺻ Ű)
    RECIPE_CD NUMBER,  -- ������ �ڵ�
    INGREDIENT_CD NUMBER,  -- ��� �ڵ�
    INGREDIENT_AMT VARCHAR2(255),  -- ��� ��
    INGREDIENT_TYPE VARCHAR2(255),  -- ��� ���� (��: �����, �����)
    CONSTRAINT FK_RECIPE_INGREDIENT_RECIPE FOREIGN KEY (RECIPE_CD) REFERENCES RECIPE_TB (RECIPE_CD),  -- ������ ���̺� �ܷ� Ű
    CONSTRAINT FK_RECIPE_INGREDIENT FOREIGN KEY (INGREDIENT_CD) REFERENCES RECIPE_INGREDIENT_TB (INGREDIENT_CD)  -- ��� ���̺� �ܷ� Ű
);

-- 6. RECIPE_STEP_TB ���̺� ����
CREATE TABLE RECIPE_STEP_TB (
    STEP_CD NUMBER PRIMARY KEY,  -- ���� �ܰ� �ڵ� (�⺻ Ű)
    RECIPE_CD NUMBER,  -- ������ �ڵ�
    STEP_ORD NUMBER NOT NULL,  -- ���� ����
    INSTRUCTION VARCHAR2(255),  -- ���� ����
    IMAGE_URL VARCHAR2(255),  -- ���� �̹��� ���
    CONSTRAINT FK_RECIPE_STEP_RECIPE FOREIGN KEY (RECIPE_CD) REFERENCES RECIPE_TB (RECIPE_CD)  -- ������ ���̺� �ܷ� Ű
);

-- 7. TIP_TB ���̺� ����
CREATE TABLE TIP_TB (
    TIP_CD NUMBER PRIMARY KEY,  -- �� �ڵ� (�⺻ Ű)
    RECIPE_CD NUMBER,  -- ������ �ڵ�
    TIP_CONTENT VARCHAR2(255),  -- �� ����
    TIP_ORD NUMBER,  -- �� ����
    CONSTRAINT FK_TIP_RECIPE FOREIGN KEY (RECIPE_CD) REFERENCES RECIPE_TB (RECIPE_CD)  -- ������ ���̺� �ܷ� Ű
);

-- Ŀ�� ���ɾ�� ���� ���� �ݿ�
COMMIT;
DESC RECIPE_CATEGORY2_TB;
-- �ܷ� Ű ���� ���� ����
ALTER TABLE RECIPE_TB DROP CONSTRAINT FK_RECIPE_CATEGORY1;
ALTER TABLE RECIPE_TB DROP CONSTRAINT FK_RECIPE_CATEGORY2;
ALTER TABLE RECIPE_INGREDIENT_TYPE_TB DROP CONSTRAINT FK_RECIPE_INGREDIENT_RECIPE;
ALTER TABLE RECIPE_INGREDIENT_TYPE_TB DROP CONSTRAINT FK_RECIPE_INGREDIENT;
ALTER TABLE RECIPE_STEP_TB DROP CONSTRAINT FK_RECIPE_STEP_RECIPE;
ALTER TABLE TIP_TB DROP CONSTRAINT FK_TIP_RECIPE;

-- ���̺� ���� (������ ��츸)
DROP TABLE RECIPE_CATEGORY1_TB CASCADE CONSTRAINTS;
DROP TABLE RECIPE_CATEGORY2_TB CASCADE CONSTRAINTS;
DROP TABLE RECIPE_TB CASCADE CONSTRAINTS;
DROP TABLE RECIPE_INGREDIENT_TB CASCADE CONSTRAINTS;
DROP TABLE RECIPE_INGREDIENT_TYPE_TB CASCADE CONSTRAINTS;
DROP TABLE RECIPE_STEP_TB CASCADE CONSTRAINTS;
DROP TABLE TIP_TB CASCADE CONSTRAINTS;

-- Ŀ�� ����
COMMIT;

-- 1. RECIPE_CATEGORY2_TB ���̺��� �ܷ� Ű ����
ALTER TABLE RECIPE_CATEGORY2_TB DROP CONSTRAINT FK_CATEGORY2_CATEGORY1;


-- 2. RECIPE_CATEGORY2_TB ���̺��� CATEGORY1_CD �÷� ����
ALTER TABLE RECIPE_CATEGORY2_TB DROP COLUMN CATEGORY1_CD;

-- 1. 1�� ī�װ��� ���̺� ������ ����
INSERT INTO RECIPE_CATEGORY1_TB (CATEGORY1_CD, CATEGORY1_NAME) VALUES (101, '�ѽ�');
INSERT INTO RECIPE_CATEGORY1_TB (CATEGORY1_CD, CATEGORY1_NAME) VALUES (102, '���');
INSERT INTO RECIPE_CATEGORY1_TB (CATEGORY1_CD, CATEGORY1_NAME) VALUES (103, '�߽�');
INSERT INTO RECIPE_CATEGORY1_TB (CATEGORY1_CD, CATEGORY1_NAME) VALUES (104, '�Ͻ�');
INSERT INTO RECIPE_CATEGORY1_TB (CATEGORY1_CD, CATEGORY1_NAME) VALUES (105, '����Ʈ');

-- 2. 2�� ī�װ��� ���̺� ������ ���� (���������� ����)
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (201, '���');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (202, '������');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (203, '���̷�');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (204, '����');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (205, '���');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (206, '�Ľ�Ÿ');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (207, '������ũ');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (208, '������');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (209, '����');
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_CD, CATEGORY2_NAME) VALUES (210, '����Ʈ');

-- 3. �⺻ ��� ���̺� ������ ����
INSERT INTO RECIPE_INGREDIENT_TB (INGREDIENT_CD, INGREDIENT_NM) VALUES (301, '��������');
INSERT INTO RECIPE_INGREDIENT_TB (INGREDIENT_CD, INGREDIENT_NM) VALUES (302, '����');
INSERT INTO RECIPE_INGREDIENT_TB (INGREDIENT_CD, INGREDIENT_NM) VALUES (303, '����');
INSERT INTO RECIPE_INGREDIENT_TB (INGREDIENT_CD, INGREDIENT_NM) VALUES (304, '������');
INSERT INTO RECIPE_INGREDIENT_TB (INGREDIENT_CD, INGREDIENT_NM) VALUES (305, '����');

-- 4. ������ ���̺� ������ ����
INSERT INTO RECIPE_TB (
    RECIPE_CD, USER_NO, RECIPE_NM, RECIPE_DESCRIPTION, ESTIMATED_TIME, COOKING_SERVINGS, RECIPE_MAIN_IMAGE, CATEGORY1_CD, CATEGORY2_CD, CREATED_DT, UPDATED_DT
) VALUES (
    1, NULL, '��ġ�', '��ġ�� �������⸦ �ְ� ���� �ѱ� ���� � �丮.', 30, 4, '/images/recipe/kimchi_jjigae.jpg', 101, 201, SYSDATE, SYSDATE
);

INSERT INTO RECIPE_TB (
    RECIPE_CD, USER_NO, RECIPE_NM, RECIPE_DESCRIPTION, ESTIMATED_TIME, COOKING_SERVINGS, RECIPE_MAIN_IMAGE, CATEGORY1_CD, CATEGORY2_CD, CREATED_DT, UPDATED_DT
) VALUES (
    2, NULL, '�����Ұ���', '���� �������⸦ ���� �Դ� ���� �ѽ�.', 25, 3, '/images/recipe/bulgogi.jpg', 101, 202, SYSDATE, SYSDATE
);

INSERT INTO RECIPE_TB (
    RECIPE_CD, USER_NO, RECIPE_NM, RECIPE_DESCRIPTION, ESTIMATED_TIME, COOKING_SERVINGS, RECIPE_MAIN_IMAGE, CATEGORY1_CD, CATEGORY2_CD, CREATED_DT, UPDATED_DT
) VALUES (
    3, NULL, '�Ľ�Ÿ', '�ż��� �丶�� �ҽ��� ����� ���İ�Ƽ �丮.', 20, 2, '/images/recipe/spaghetti.jpg', 102, 206, SYSDATE, SYSDATE
);

-- 5. ��� ���� ���̺� ������ ����
INSERT INTO RECIPE_INGREDIENT_TYPE_TB (
    TYPE_CD, RECIPE_CD, INGREDIENT_CD, INGREDIENT_AMT, INGREDIENT_TYPE
) VALUES (
    1, 1, 301, '200g', '�����'  -- ��ġ��� ����� ��������
);

INSERT INTO RECIPE_INGREDIENT_TYPE_TB (
    TYPE_CD, RECIPE_CD, INGREDIENT_CD, INGREDIENT_AMT, INGREDIENT_TYPE
) VALUES (
    2, 1, 302, '1��', '�����'  -- ��ġ��� ����� ����
);

INSERT INTO RECIPE_INGREDIENT_TYPE_TB (
    TYPE_CD, RECIPE_CD, INGREDIENT_CD, INGREDIENT_AMT, INGREDIENT_TYPE
) VALUES (
    3, 2, 301, '300g', '�����'  -- �����Ұ����� ����� ��������
);

-- 6. ���� �ܰ� ���̺� ������ ����
INSERT INTO RECIPE_STEP_TB (
    STEP_CD, RECIPE_CD, STEP_ORD, INSTRUCTION, IMAGE_URL
) VALUES (
    1, 1, 1, '��ġ�� �������⸦ ���� �ְ� �����ϴ�.', '/images/recipe/kimchi_jjigae_step1.jpg'
);

INSERT INTO RECIPE_STEP_TB (
    STEP_CD, RECIPE_CD, STEP_ORD, INSTRUCTION, IMAGE_URL
) VALUES (
    2, 1, 2, '���� �ְ� 30�а� ���Դϴ�.', '/images/recipe/kimchi_jjigae_step2.jpg'
);

INSERT INTO RECIPE_STEP_TB (
    STEP_CD, RECIPE_CD, STEP_ORD, INSTRUCTION, IMAGE_URL
) VALUES (
    3, 2, 1, '��信 �������⸦ 30�а� ���ϴ�.', '/images/recipe/bulgogi_step1.jpg'
);

-- 7. �� ���̺� ������ ����
INSERT INTO TIP_TB (
    TIP_CD, RECIPE_CD, TIP_CONTENT, TIP_ORD
) VALUES (
    1, 1, '�������⸦ ���� ���ƾ� �� ���� ���� ���ϴ�.', 1
);

INSERT INTO TIP_TB (
    TIP_CD, RECIPE_CD, TIP_CONTENT, TIP_ORD
) VALUES (
    2, 2, '���⸦ ���� ������ ǳ�̰� �� �������ϴ�.', 1
);

COMMIT;
SELECT 
    R.RECIPE_CD,  -- ������ �ڵ�
    R.RECIPE_NM,  -- ������ �̸�
    R.RECIPE_DESCRIPTION,  -- ������ ����
    R.ESTIMATED_TIME,  -- ���� ���� �ð�
    R.COOKING_SERVINGS,  -- ���� �ο�
    R.RECIPE_MAIN_IMAGE,  -- ������ ���� �̹��� ���
    C1.CATEGORY1_NAME AS CATEGORY1,  -- 1�� ī�װ��� �̸�
    C2.CATEGORY2_NAME AS CATEGORY2,  -- 2�� ī�װ��� �̸�
    I.INGREDIENT_NM,  -- ��� �̸�
    IT.INGREDIENT_AMT,  -- ��� ��
    IT.INGREDIENT_TYPE,  -- ��� ���� (��: �����, �����)
    S.STEP_ORD,  -- ���� �ܰ� ����
    S.INSTRUCTION,  -- ���� ����
    S.IMAGE_URL AS STEP_IMAGE,  -- ���� �ܰ� �̹��� ���
    T.TIP_CONTENT,  -- �� ����
    T.TIP_ORD  -- �� ����
FROM 
    RECIPE_TB R
LEFT JOIN RECIPE_CATEGORY1_TB C1 ON R.CATEGORY1_CD = C1.CATEGORY1_CD  -- 1�� ī�װ��� ����
LEFT JOIN RECIPE_CATEGORY2_TB C2 ON R.CATEGORY2_CD = C2.CATEGORY2_CD  -- 2�� ī�װ��� ����
LEFT JOIN RECIPE_INGREDIENT_TYPE_TB IT ON R.RECIPE_CD = IT.RECIPE_CD  -- ��� ���� ���̺� ����
LEFT JOIN RECIPE_INGREDIENT_TB I ON IT.INGREDIENT_CD = I.INGREDIENT_CD  -- ��� ���̺� ����
LEFT JOIN RECIPE_STEP_TB S ON R.RECIPE_CD = S.RECIPE_CD  -- ���� �ܰ� ���̺� ����
LEFT JOIN TIP_TB T ON R.RECIPE_CD = T.RECIPE_CD  -- �� ���̺� ����
ORDER BY 
    R.RECIPE_CD,  -- ������ �ڵ庰 ����
    IT.TYPE_CD,  -- ��� ������ ����
    S.STEP_ORD,  -- ���� �ܰ� ������ ����
    T.TIP_ORD;  -- �� ������ ����


desc RECIPE_CATEGORY2_TB;
DESC RECIPE_TB;
INSERT INTO RECIPE_CATEGORY2_TB (CATEGORY2_NAME, CATEGORY2_CD) VALUES ('������',5);
COMMIT;

CREATE SEQUENCE recipe_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE ingredient_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE type_seq START WITH 1 INCREMENT BY 1;
SELECT * FROM user_constraints WHERE table_name = 'RECIPE_INGREDIENT_TYPE_TB' AND constraint_name = 'SYS_C007323';
DROP SEQUENCE ingredient_seq;
DROP SEQUENCE ingredient_seq;
CREATE SEQUENCE ingredient_seq
  START WITH 30
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;
  COMMIT;
  
  SELECT TYPE_SEQ.CURRVAL FROM DUAL;
SELECT MAX(TYPE_CD) FROM RECIPE_INGREDIENT_TYPE_TB;
-- ���� �������� NEXTVAL ȣ��
SELECT TYPE_SEQ.NEXTVAL FROM DUAL;

-- ���̺��� �ִ� TYPE_CD �� Ȯ��
SELECT MAX(TYPE_CD) FROM RECIPE_INGREDIENT_TYPE_TB;
DROP SEQUENCE TYPE_SEQ;

CREATE SEQUENCE TYPE_SEQ
  START WITH(SELECT MAX(TYPE_CD) + 1 FROM RECIPE_INGREDIENT_TYPE_TB)
  INCREMENT BY 1;

SELECT MAX(TYPE_CD) FRCREATE SEQUENCE TYPE_SEQ
 CREATE SEQUCE TYPE_SEQ
  START WITH 4
  INCREMENT BY 1;
OM RECIPE_INGREDIENT_TYPE_TB;

CREATE SEQUENCE TYPE_SEQ
  START WITH 4
  INCREMENT BY 1;
COMMIT;
CREATE SEQUENCE STEP_SEQ
  START WITH 10
  INCREMENT BY 1;
COMMIT;

DROP SEQUENCE STEP_SEQ;
CREATE SEQUENCE TIP_SEQ
  START WITH 10
  INCREMENT BY 1;
COMMIT;

SELECT * FROM RECIPE_TB;

        SELECT 
	            -- ������ �⺻ ����
	            r.RECIPE_CD AS recipeCD,
	            r.RECIPE_NM AS recipeNM,
	            r.RECIPE_DESCRIPTION AS recipeDescription,
	            r.ESTIMATED_TIME AS estimatedTime,
	            r.COOKING_SERVINGS AS cookingServings,
	            r.RECIPE_MAIN_IMAGE AS recipeMainImage,
	            
	            -- ī�װ��� �̸� ���� ��������
	            c1.CATEGORY1_NAME AS category1Name,
	            c2.CATEGORY2_NAME AS category2Name,
	
	            -- ��� ����
	            i.INGREDIENT_CD AS ingredients_ingredientCD,
	            i.INGREDIENT_NM AS ingredients_ingredientNM,
	            it.INGREDIENT_AMT AS ingredients_ingredientAmt,
	            it.INGREDIENT_TYPE AS ingredients_ingredientType,
	
	            -- ���� �ܰ� ����
	            s.STEP_CD AS steps_stepCD,
	            s.STEP_ORD AS steps_stepORD,
	            s.INSTRUCTION AS steps_instruction,
	            s.IMAGE_URL AS steps_imageURL,
	
	            -- �� ����
	            t.TIP_CD AS tips_tipCD,
	            t.TIP_CONTENT AS tips_tipContent,
	            t.TIP_ORD AS tips_tipORD

        FROM 
	            RECIPE_TB r
	            -- ī�װ���1�� ī�װ���2 ����
	            LEFT JOIN RECIPE_CATEGORY1_TB c1 ON r.CATEGORY1_CD = c1.CATEGORY1_CD
	            LEFT JOIN RECIPE_CATEGORY2_TB c2 ON r.CATEGORY2_CD = c2.CATEGORY2_CD
	            -- ���, ���Ÿ�� ���̺� ����
	            LEFT JOIN RECIPE_INGREDIENT_TYPE_TB it ON r.RECIPE_CD = it.RECIPE_CD
	            LEFT JOIN RECIPE_INGREDIENT_TB i ON it.INGREDIENT_CD = i.INGREDIENT_CD
	            -- ���� �ܰ� ���̺� ����
	            LEFT JOIN RECIPE_STEP_TB s ON r.RECIPE_CD = s.RECIPE_CD
	            -- �� ���̺� ����
	            LEFT JOIN TIP_TB t ON r.RECIPE_CD = t.RECIPE_CD

        WHERE 
            	r.RECIPE_CD = 20

        ORDER BY 
            	s.STEP_ORD, t.TIP_ORD;
                
                
 SELECT INGREDIENT_NM, COUNT(*) 
FROM RECIPE_INGREDIENT_TB
GROUP BY INGREDIENT_NM
HAVING COUNT(*) > 1;

DELETE FROM RECIPE_INGREDIENT_TB
WHERE INGREDIENT_CD IN (
    SELECT INGREDIENT_CD
    FROM (
        SELECT INGREDIENT_CD,
               ROW_NUMBER() OVER (PARTITION BY INGREDIENT_NM ORDER BY INGREDIENT_CD) AS row_num
        FROM RECIPE_INGREDIENT_TB
    )
    WHERE row_num > 1
);

commit;

sql
�ڵ� ����
CREATE TABLE RECIPE_COMMENT_TB (
    COMMENT_CD         NUMBER(10)     PRIMARY KEY,         -- ��� ID (Primary Key)
    RECIPE_CD          NUMBER(10)     NOT NULL,            -- ������ ID (Foreign Key�� ���� ����)
    USER_NO            NUMBER(10)     NOT NULL,            -- ����� ID (Foreign Key�� ���� ����)
    PARENT_COMMENT_CD  NUMBER(10)     DEFAULT 0,           -- �θ� ��� ID (0�̸� �θ� ���, �ٸ� ���̸� ����)
    COMMENT_TEXT       VARCHAR2(1000) NOT NULL,            -- ��� ����
    IMAGE_URL          VARCHAR2(255),                      -- ��� �̹��� URL (������)
    CREATED_DT         TIMESTAMP      DEFAULT SYSDATE,     -- ���� ��¥
    UPDATED_DT         TIMESTAMP,                          -- ���� ��¥
    CONSTRAINT FK_PARENT_COMMENT
        FOREIGN KEY (PARENT_COMMENT_CD)
        REFERENCES RECIPE_COMMENT_TB (COMMENT_CD)
        ON DELETE CASCADE
);

CREATE TABLE COMMENT_LIKE_TB (
    LIKE_ID       NUMBER(10)     PRIMARY KEY,          -- ���ƿ� ID (Primary Key)
    COMMENT_CD    NUMBER(10)     NOT NULL,             -- ��� ID (Foreign Key)
    USER_NO       NUMBER(10)     NOT NULL,             -- ����� ID (Foreign Key)
    CREATED_DT    TIMESTAMP      DEFAULT SYSDATE,      -- ���ƿ� ���� ��¥
    CONSTRAINT FK_COMMENT_LIKE
        FOREIGN KEY (COMMENT_CD)
        REFERENCES RECIPE_COMMENT_TB (COMMENT_CD)
        ON DELETE CASCADE,
);

drop table COMMENT_LIKE_TB;

CREATE SEQUENCE COMMENT_SEQ
START WITH 1       -- ���� ��
INCREMENT BY 1     -- 1�� ����
NOCACHE            -- ĳ���� ������� ���� (�ʿ�� CACHE N ��� ����)
NOCYCLE;           -- �ִ� �� ���� �� �ٽ� �������� ����

commit;

CREATE SEQUENCE LIKE_SEQ
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- COMMENT_LIKE_TB ���̺��� USER_NO �ܷ� Ű ���� ���� ����
ALTER TABLE COMMENT_LIKE_TB
DROP CONSTRAINT FK_USER_LIKE;

-- RECIPE_COMMENT_TB ���̺��� USER_NO �ܷ� Ű ���� ���� ���� (�ʿ��� ���)
ALTER TABLE RECIPE_COMMENT_TB
DROP CONSTRAINT FK_RECIPE_COMMENT_USER; 

-- ��� ���ƿ� ���̺� ����
DROP TABLE COMMENT_LIKE_TB CASCADE CONSTRAINTS;

-- ��� ���̺� ����
DROP TABLE RECIPE_COMMENT_TB CASCADE CONSTRAINTS;

-- ��� ���ƿ� ������ ����
DROP SEQUENCE COMMENT_LIKE_SEQ;

-- ��� ���̺� ������ ����
DROP SEQUENCE COMMENT_SEQ;



-- ��� ���̺� ������ ����
CREATE SEQUENCE COMMENT_SEQ
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- ��� ���̺� ����
CREATE TABLE RECIPE_COMMENT_TB (
    COMMENT_CD       NUMBER           NOT NULL, -- ��� �⺻Ű
    RECIPE_CD        NUMBER           NOT NULL, -- ������ �⺻Ű
    USER_NO          NUMBER           NOT NULL, -- �ۼ��� ���� ��ȣ
    PARENT_COMMENT_CD NUMBER           NULL,     -- �θ� ��� ��ȣ (������ ��� �θ� ��� ��ȣ)
    COMMENT_TEXT     VARCHAR2(255)    NOT NULL, -- ��� ����
    IMAGE_URL        VARCHAR2(225)    NULL,     -- �̹��� URL (÷�� �̹��� ���)
    CREATED_DT       TIMESTAMP        NOT NULL, -- �ۼ���
    UPDATED_DT       TIMESTAMP        NULL,     -- ������
    CONSTRAINT PK_RECIPE_COMMENT PRIMARY KEY (COMMENT_CD) -- �⺻Ű ��������
);

-- ��� ���ƿ� ���̺� ������ ����
CREATE SEQUENCE COMMENT_LIKE_SEQ
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

-- ��� ���ƿ� ���̺� ����
CREATE TABLE COMMENT_LIKE_TB (
    LIKE_CD          NUMBER           NOT NULL, -- ���ƿ� �⺻Ű
    COMMENT_CD       NUMBER           NOT NULL, -- ��� �⺻Ű (����)
    USER_NO          NUMBER           NOT NULL, -- ���� ��ȣ
    LIKED_DT         TIMESTAMP        NOT NULL, -- ���ƿ��� �Ͻ�
    CONSTRAINT PK_COMMENT_LIKE PRIMARY KEY (LIKE_CD), -- �⺻Ű ��������
    CONSTRAINT FK_COMMENT_LIKE_COMMENT FOREIGN KEY (COMMENT_CD) REFERENCES RECIPE_COMMENT_TB (COMMENT_CD) ON DELETE CASCADE -- ��� ���̺� �ܷ�Ű ��������
);

commit;



CREATE TABLE RECIPE_BOOKMARK_TB (
    BOOKMARK_CD NUMBER NOT NULL,            -- �ϸ�ũ �⺻Ű
    RECIPE_CD   NUMBER NOT NULL,            -- ������ �⺻Ű (����)
    USER_ID     VARCHAR2(30) NOT NULL,      -- ���� ID
    CREATED_DT  TIMESTAMP DEFAULT SYSDATE,  -- �����Ͻ�
    CONSTRAINT PK_RECIPE_BOOKMARK PRIMARY KEY (BOOKMARK_CD),
    CONSTRAINT FK_BOOKMARK_RECIPE FOREIGN KEY (RECIPE_CD) REFERENCES RECIPE_TB (RECIPE_CD) ON DELETE CASCADE
);
commit;


CREATE SEQUENCE BOOKMARK_SEQ
START WITH 1          -- �������� ���� ��
INCREMENT BY 1        -- ���� ������ ���� (1�� ����)
NOCACHE               -- �̸� ������ ���� ĳ������ ����
NOCYCLE; 


ALTER TABLE RECIPE_CATEGORY1_TB RENAME COLUMN CATEGORY1_NAME TO CATEGORY1_NM;
ALTER TABLE RECIPE_CATEGORY2_TB RENAME COLUMN CATEGORY2_NAME TO CATEGORY2_NM;
COMMIT;
DESC RECIPE_CATEGORY1_TB;
DESC RECIPE_CATEGORY2_TB;


SELECT * FROM RECIPE_TB;
SELECT * FROM RECIPE_INGREDIENT_TB;
SELECT
SELECT INGREDIENT_NM, COUNT(*) FROM RECIPE_INGREDIENT_TB GROUP BY INGREDIENT_NM HAVING COUNT(*) > 1;

CREATE INDEX idx_ingredient_nm ON RECIPE_INGREDIENT_TB (INGREDIENT_NM);
ALTER TABLE RECIPE_INGREDIENT_TB ADD CONSTRAINT unique_ingredient_nm UNIQUE (INGREDIENT_NM);
DELETE FROM RECIPE_INGREDIENT_TB
 WHERE INGREDIENT_CD IN (
   SELECT MIN(INGREDIENT_CD) 
     FROM RECIPE_INGREDIENT_TB
    GROUP BY INGREDIENT_NM
    HAVING COUNT(*) > 1
);
SELECT index_name, column_name
FROM user_ind_columns
WHERE table_name = 'RECIPE_INGREDIENT_TB';


SELECT * FROM RECIPE_BOOKMARK_TB;


     SELECT BOOKMARK_CD, RECIPE_CD, USER_ID, CREATED_DT
        FROM RECIPE_BOOKMARK_TB
        WHERE RECIPE_CD = 49
        ORDER BY CREATED_DT DESC;
        
        select * from RECIPE_COMMENT_TB;
        SELECT * FROM COMMENT_LIKE_TB;
        
        CREATE TABLE COOMET_LIKE_TB 
INSERT INTO COMMENT_LIKE_TB (LIKE_CD, COMMENT_CD, USER_NO, LIKED_DT)
VALUES (SEQ_COMMENT_LIKE.NEXTVAL, :commentCd, :userNo, SYSTIMESTAMP);   

CREATE TABLE COMMENT_LIKE_TB (
    LIKE_CD NUMBER NOT NULL,
    COMMENT_CD NUMBER NOT NULL,
    USER_NO NUMBER NOT NULL,
    LIKED_DT TIMESTAMP DEFAULT SYSTIMESTAMP,
    PRIMARY KEY (LIKE_CD),
    FOREIGN KEY (COMMENT_CD) REFERENCES RECIPE_COMMENT_TB(COMMENT_CD));
    
    CREATE SEQUENCE COMMENT_LIKE_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE;
    DROP SEQUENCE SEQ_COMMENT_LIKE;
    CREATE SEQUENCE COMMENT_LIKE_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE;
    se
    
    SELECT * FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'COMMENT_LIKE_SEQ';
commit;
    
    
    