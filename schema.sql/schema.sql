CREATE TABLE clothes (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    size VARCHAR(10) NOT NULL,
    color VARCHAR(100) NOT NULL,
    material VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    quantity INTEGER NOT NULL,
    has_pockets BOOLEAN,
    sleeve_type VARCHAR(100),
    has_hood BOOLEAN,
    insulation_type VARCHAR(100),
    length_type VARCHAR(100),
    formal BOOLEAN
);
