
CREATE SCHEMA IF NOT EXISTS customer_schema;

CREATE TABLE customer_schema.customer_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    postal_code VARCHAR(10),
    prefecture VARCHAR(50),
    city VARCHAR(100),
    street_address VARCHAR(200),
    building VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_customer_profiles_user_id ON customer_schema.customer_profiles(user_id);

COMMENT ON TABLE customer_schema.customer_profiles IS 'Customer profile information including contact details and address';
COMMENT ON COLUMN customer_schema.customer_profiles.id IS 'Primary key - unique profile identifier';
COMMENT ON COLUMN customer_schema.customer_profiles.user_id IS 'Foreign key reference to user in auth service';
COMMENT ON COLUMN customer_schema.customer_profiles.name IS 'Customer full name';
COMMENT ON COLUMN customer_schema.customer_profiles.phone_number IS 'Customer phone number';
COMMENT ON COLUMN customer_schema.customer_profiles.postal_code IS 'Postal code';
COMMENT ON COLUMN customer_schema.customer_profiles.prefecture IS 'Prefecture/state';
COMMENT ON COLUMN customer_schema.customer_profiles.city IS 'City';
COMMENT ON COLUMN customer_schema.customer_profiles.street_address IS 'Street address';
COMMENT ON COLUMN customer_schema.customer_profiles.building IS 'Building name/number';
COMMENT ON COLUMN customer_schema.customer_profiles.created_at IS 'Timestamp when profile was created';
COMMENT ON COLUMN customer_schema.customer_profiles.updated_at IS 'Timestamp when profile was last updated';
