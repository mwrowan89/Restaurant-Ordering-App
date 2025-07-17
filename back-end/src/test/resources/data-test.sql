-- Sample data for testing
-- This provides minimal test data for integration tests

-- Sample Users
INSERT INTO users (username, password, first, last, phone, email, roles) 
VALUES ('testuser', 'password', 'Test', 'User', '1234567890', 'test@example.com', 'USER');

INSERT INTO users (username, password, first, last, phone, email, roles) 
VALUES ('admin', 'adminpassword', 'Admin', 'User', '0987654321', 'admin@example.com', 'ADMIN');

-- Sample Menu Items
INSERT INTO menuitems (name, description, category, price, available) 
VALUES ('Classic Burger', 'A classic beef burger', 'burger', 10.99, TRUE);

INSERT INTO menuitems (name, description, category, price, available) 
VALUES ('Cheese Pizza', 'Traditional cheese pizza', 'pizza', 12.99, TRUE);

INSERT INTO menuitems (name, description, category, price, available) 
VALUES ('Ice Cream', 'Vanilla ice cream', 'dessert', 5.99, TRUE);

-- Sample Orders
INSERT INTO orders (userid, ordertime, status) 
VALUES ('testuser', CURRENT_TIMESTAMP, 'PENDING');

-- Sample Items
INSERT INTO items (orderid, itemid, price, firstname) 
VALUES ('1', '1', 10.99, 'Test');
