DROP TABLE IF EXISTS product;

CREATE TABLE product(
    product_id INT NOT NULL AUTO_INCREMENT,
    gtin CHAR(13) UNIQUE NOT NULL,
    product VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255),
    price FLOAT NOT NULL,
    stock INT NOT NULL,
    category_id INT NOT NULL,
    status TINYINT NOT NULL,
    PRIMARY KEY (product_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);
