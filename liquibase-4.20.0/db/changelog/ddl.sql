CREATE TABLE person (
    id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    full_name varchar(100) NOT NULL,
    year_of_birth int4 NOT NULL,
    CONSTRAINT person_age_check CHECK ((year_of_birth > 0)),
    CONSTRAINT person_pkey PRIMARY KEY (id),
    CONSTRAINT unique_constrain UNIQUE (full_name)
);

CREATE TABLE book (
    id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    name varchar(150) NOT NULL,
    autor varchar(100) NOT NULL,
    year_of_printing int4 NOT NULL,
    time_of_taking timestamp NULL,
    person_id int4 NULL,
    CONSTRAINT book_age_check CHECK ((year_of_printing > 0)),
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT constraint_first FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE SET NULL
);