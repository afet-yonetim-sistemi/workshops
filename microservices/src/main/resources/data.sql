-- Insert sample categories
INSERT INTO categories (name, description, parent_id)
VALUES ('Electronics', 'All electronic devices', NULL),
       ('Computers', 'Laptops, desktops, and accessories', 1),
       ('Smartphones', 'Mobile phones and smart devices', 1),
       ('Clothing', 'Apparel and fashion items', NULL),
       ('Men''s Clothing', 'Clothing for men', 4),
       ('Women''s Clothing', 'Clothing for women', 4);

-- Insert sample products
INSERT INTO products (name, description, price, sku, stock_quantity,
                      active, created_at, updated_at, category_id,
                      dimensions, weight)
VALUES ('MacBook Pro', 'Apple 16-inch MacBook Pro', 2399.99, 'MBA-PRO-16-2023', 50,
        true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2,
        '35.57 x 24.81 x 1.62 cm', 2.1),
       ('iPhone 15 Pro', 'Apple iPhone 15 Pro, 256GB', 1199.99, 'IPHONE-15-PRO-256', 100,
        true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3,
        '14.8 x 7.85 x 0.8 cm', 0.22),
       ('Men''s Casual Shirt', 'Blue cotton casual shirt', 49.99, 'SHIRT-MENS-BLUE-M', 200,
        true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5,
        '40 x 30 x 2 cm', 0.3),
       ('Women''s Running Shoes', 'Nike Women''s Running Shoes', 129.99, 'SHOE-NIKE-WOMENS-7', 75,
        true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6,
        '30 x 15 x 10 cm', 0.4);

-- Insert product images
INSERT INTO product_images (product_id, url, alt, is_primary, sort_order)
VALUES (1, '/images/macbook-pro.jpg', 'MacBook Pro', true, 1),
       (2, '/images/iphone-15-pro.jpg', 'iPhone 15 Pro', true, 1),
       (3, '/images/mens-shirt.jpg', 'Men''s Casual Shirt', true, 1),
       (4, '/images/womens-running-shoes.jpg', 'Women''s Running Shoes', true, 1);

-- Insert sample customers
INSERT INTO customers (first_name, last_name, email, phone,
                       password, is_active, email_verified,
                       last_login, verification_token)
VALUES ('John', 'Doe', 'john.doe@example.com', '+1-555-123-4567',
        'hashed_password_123', true, true,
        CURRENT_TIMESTAMP, 'token_123'),
       ('Jane', 'Smith', 'jane.smith@example.com', '+1-555-987-6543',
        'hashed_password_456', true, true,
        CURRENT_TIMESTAMP, 'token_456');

-- Insert sample addresses
INSERT INTO addresses (customer_id, street, city, state,
                       country, zip_code, address_type, is_default)
VALUES (1, '123 Main St', 'New York', 'NY',
        'United States', '10001', 'BILLING', true),
       (2, '456 Elm St', 'San Francisco', 'CA',
        'United States', '94110', 'SHIPPING', true);

-- Insert sample orders
INSERT INTO orders (customer_id, customer_email, customer_phone,
                    order_status, address, city, state, country, zip_code,
                    shipping_cost, tax_amount, total_amount,
                    estimated_delivery, method, tracking_number)
VALUES (1, 'john.doe@example.com', '+1-555-123-4567',
        1, '123 Main St', 'New York', 'NY', 'United States', '10001',
        10.99, 25.50, 1685.48,
        DATEADD(DAY, 5, CURRENT_TIMESTAMP), 'STANDARD', 'TRACK1234567'),
       (2, 'jane.smith@example.com', '+1-555-987-6543',
        2, '456 Elm St', 'San Francisco', 'CA', 'United States', '94110',
        15.99, 30.75, 1375.73,
        DATEADD(DAY, 3, CURRENT_TIMESTAMP), 'EXPRESS', 'TRACK7654321');

-- Insert sample order items
INSERT INTO order_items (order_id, product_id, quantity, price, special_instructions)
VALUES (1, 1, 1, 2399.99, 'Handle with care'),
       (1, 2, 1, 1199.99, 'Gift wrap if possible'),
       (2, 3, 2, 49.99, 'No special instructions'),
       (2, 4, 1, 129.99, 'Size 7 preferred');

-- Insert sample payments
INSERT INTO payments (order_id, amount, currency, payment_provider,
                      card_holder_name, card_number, cvv,
                      expiry_month, expiry_year, status,
                      transaction_id, payment_date, error_message)
VALUES (1, 1685.48, 'USD', 'STRIPE',
        'John Doe', '4111111111111111', '123',
        '12', '2025', 'SUCCESS',
        'CH_123456789', CURRENT_TIMESTAMP, NULL),
       (2, 1375.73, 'USD', 'PAYPAL',
        'Jane Smith', '5500000000000004', '456',
        '09', '2024', 'SUCCESS',
        'PP_987654321', CURRENT_TIMESTAMP, NULL);

-- Sample credit cards
INSERT INTO credit_cards (customer_id, card_holder_name, card_number,
                          card_type, cvv, expiry_month, expiry_year,
                          is_active, is_default, created_at, updated_at)
VALUES (1, 'John Doe', '4111111111111111',
        'VISA', '123', '12', '2025',
        true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'Jane Smith', '5500000000000004',
        'MASTERCARD', '456', '09', '2024',
        true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Sample email logs
INSERT INTO email_logs (recipient, subject, template, template_data,
                        sent_at, status, retry_count, error_message)
VALUES ('john.doe@example.com', 'Order Confirmation', 'order_confirmation',
        '{"order_id": 1, "total_amount": 1685.48}',
        CURRENT_TIMESTAMP, 'DELIVERED', 0, NULL),
       ('jane.smith@example.com', 'Shipping Update', 'shipping_update',
        '{"order_id": 2, "tracking_number": "TRACK7654321"}',
        CURRENT_TIMESTAMP, 'DELIVERED', 0, NULL);