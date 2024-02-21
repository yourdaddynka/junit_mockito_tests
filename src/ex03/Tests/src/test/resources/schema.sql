
CREATE TABLE if not exists product
(
    id    INT PRIMARY KEY,
    names VARCHAR(50),
    price DECIMAL(10, 2)
);

CREATE TABLE if not exists Users
(
    id            INT PRIMARY KEY,
    login         VARCHAR(50),
    password      VARCHAR(50),
    successStatus BOOLEAN NOT NULL
);

