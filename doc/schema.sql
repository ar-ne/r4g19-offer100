create table login (
username             VARCHAR(10240)                 not null,
displayname          VARCHAR(10240),
password             VARCHAR(10240),
email                VARCHAR(10240),
tel                  VARCHAR(10240),
join_time            TIMESTAMP,
type                 INTEGER,
avatar               BLOB,
contact              VARCHAR(10240),
verify_type          INTEGER,
primary key (username)
);

create table Log
(
    username    VARCHAR(10240),
    time        TIMESTAMP,
    description VARCHAR(10240),
    notes       VARCHAR(10240),
    id          BIGINT not null,
    primary key (id),
    foreign key (username)
        references login (username)
);

create index Index_5 on Log (
                             username ASC,
                             time ASC
    );

create table personal
(
    username VARCHAR(10240) not null,
    birth    DATE,
    edu      VARCHAR(10240),
    primary key (username),
    foreign key (username)
        references login (username)
);

create table entrepreneurial
(
    username  VARCHAR(10240) not null,
    address   VARCHAR(10240),
    scale     VARCHAR(10240),
    type      VARCHAR(10240),
    field     VARCHAR(10240),
    accu_addr VARCHAR(10240),
    brief     VARCHAR(10240),
    detail    VARCHAR(81920),
    primary key (username),
    foreign key (username)
        references login (username)
);

create table hiring
(
    username          VARCHAR(10240) not null,
    id                BIGINT         not null,
    position          VARCHAR(10240),
    edu_requirement   VARCHAR(10240),
    work_address      VARCHAR(10240),
    interview_address VARCHAR(10240),
    pubtime           VARCHAR(10240),
    salary            VARCHAR(10240),
    detail            VARCHAR(81920),
    primary key (username, id),
    foreign key (username)
        references entrepreneurial (username)
);

create table collection
(
    per_username    VARCHAR(10240) not null,
    hir_username    VARCHAR(10240),
    hir_id          BIGINT         not null,
    valid           INT,
    collection_time TIMESTAMP,
    apply           INT,
    primary key (per_username, hir_id),
    foreign key (per_username)
        references personal (username),
    foreign key (hir_username, hir_id)
        references hiring (username, id)
);

create index Index_7 on collection (
                                    per_username ASC,
                                    hir_username ASC,
                                    hir_id ASC
    );

create index Index_3 on entrepreneurial (
                                         username ASC
    );

create index Index_6 on hiring (
                                username ASC,
                                id ASC
    );

create index Index_1 on login (
                               username ASC
    );

create index Index_2 on personal (
                                  username ASC
    );

create table resume
(
    username   VARCHAR(10240) not null,
    id         BIGINT         not null,
    sex        int,
    age        int,
    school     VARCHAR(10240),
    address    VARCHAR(10240),
    phone      VARCHAR(10240),
    email      VARCHAR(10240),
    introduce  VARCHAR(10240),
    experience VARCHAR(10240),
    primary key (username, id),
    foreign key (username)
        references personal (username)
);

create  index Index_4 on resume (
username ASC,
id ASC
);

create table submission
(
    hir_username VARCHAR(10240) not null,
    res_username VARCHAR(10240) not null,
    hir_id       BIGINT         not null,
    res_id       BIGINT         not null,
    status       INT,
    primary key (hir_username, res_username, hir_id, res_id),
    foreign key (hir_username, hir_id)
        references hiring (username, id),
    foreign key (res_username, res_id)
        references resume (username, id)
);

create  index Index_8 on submission (
hir_username ASC,
res_username ASC,
hir_id ASC,
res_id ASC
);

