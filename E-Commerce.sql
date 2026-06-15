CREATE DATABASE amesing_store;
USE amesing_store;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'customer'
);

-- Insert an admin user (password: admin123)
INSERT INTO users (fullname, email, password, role) VALUES
('Admin User', 'admin@amesing.com', 'admin123', 'admin');

CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO categories (name) VALUES
('Electronics'),
('Clothing'),
('Home & Kitchen'),
('Books');

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    image VARCHAR(255),
    stock INT DEFAULT 0,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

INSERT INTO products (name, description, price, image, stock, category_id) VALUES
('Wireless Headphones', 'Noise-cancelling over-ear headphones', 79.99, 'headphones.jpg', 50, 1),
('Smart Watch', 'Fitness tracker with heart rate monitor', 129.99, 'watch.jpg', 30, 1),
('USB-C Hub', '7-in-1 multiport adapter', 34.99, 'hub.jpg', 100, 1),
('Bluetooth Speaker', 'Portable waterproof speaker', 49.99, 'speaker.jpg', 20, 1);

CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    total DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'Pending',       -- Pending, Shipped, Delivered, Cancelled
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE wishlist (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_wish (user_id, product_id)
);




ALTER TABLE products ADD COLUMN original_price DECIMAL(10,2) DEFAULT NULL;
ALTER TABLE categories ADD COLUMN image VARCHAR(255) DEFAULT NULL;

-- Set original prices for some products (for discounts)
UPDATE products SET original_price = 99.99 WHERE id = 1;
UPDATE products SET original_price = 149.99 WHERE id = 2;
UPDATE products SET original_price = 49.99 WHERE id = 4;

-- Add category images (place corresponding JPG files in webapp/images/)
UPDATE categories SET image = 'electronics.jpg' WHERE id = 1;
UPDATE categories SET image = 'clothing.jpg' WHERE id = 2;
UPDATE categories SET image = 'home_kitchen.jpg' WHERE id = 3;
UPDATE categories SET image = 'books.jpg' WHERE id = 4;




-- Address fields in users table
ALTER TABLE users ADD COLUMN phone VARCHAR(20);
ALTER TABLE users ADD COLUMN street VARCHAR(255);
ALTER TABLE users ADD COLUMN village VARCHAR(255);
ALTER TABLE users ADD COLUMN city VARCHAR(100);
ALTER TABLE users ADD COLUMN landmark VARCHAR(255);
ALTER TABLE users ADD COLUMN pincode VARCHAR(10);

-- Order snapshot fields
ALTER TABLE orders ADD COLUMN shipping_address TEXT;
ALTER TABLE orders ADD COLUMN payment_method VARCHAR(20);

select * from users;
select * from products;

ALTER TABLE users ADD COLUMN profile_image VARCHAR(255) DEFAULT NULL;







-- =============================================
-- 50 Sample Products for Amesing Store
-- (Run this after the original schema is created)
-- =============================================

USE amesing_store;

-- Category IDs:
-- 1 = Electronics
-- 2 = Clothing
-- 3 = Home & Kitchen
-- 4 = Books

-- -------------------------------------------------
-- ELECTRONICS (cat_id = 1) – 15 products
-- -------------------------------------------------
INSERT INTO products (name, description, price, original_price, image, stock, category_id) VALUES
('Laptop Pro 15"', 'Powerful laptop with 16GB RAM, 512GB SSD', 899.99, 1099.99, 'laptop.jpg', 25, 1),
('Wireless Earbuds', 'True wireless earbuds with noise cancelling', 59.99, 79.99, 'earbuds.jpg', 100, 1),
('4K Ultra HD Monitor', '27-inch 4K IPS monitor, HDMI/DP', 349.99, 399.99, 'monitor.jpg', 15, 1),
('Smartphone X200', '6.7" AMOLED, 128GB storage, 5G', 699.99, 799.99, 'smartphone.jpg', 30, 1),
('Bluetooth Speaker', 'Portable waterproof speaker, 20h battery', 49.99, NULL, 'speaker.jpg', 60, 1),
('Mechanical Keyboard', 'RGB backlit, blue switches', 79.99, 99.99, 'keyboard.jpg', 40, 1),
('Wireless Mouse', 'Ergonomic design, silent clicks', 29.99, NULL, 'mouse.jpg', 80, 1),
('USB-C Hub 7-in-1', 'Multiport adapter with HDMI, USB3.0', 34.99, NULL, 'hub.jpg', 120, 1),
('Portable SSD 1TB', 'External solid state drive, USB 3.2', 99.99, 129.99, 'ssd.jpg', 55, 1),
('Webcam HD 1080p', 'Auto-focus, built-in microphone', 44.99, NULL, 'webcam.jpg', 70, 1),
('Tablet 10.4"', 'Octa-core, 64GB, WiFi', 199.99, 249.99, 'tablet.jpg', 20, 1),
('Gaming Mouse Pad', 'Large RGB mousepad, non-slip', 24.99, NULL, 'mousepad.jpg', 90, 1),
('Laptop Stand', 'Adjustable aluminium stand, foldable', 34.99, NULL, 'laptopstand.jpg', 65, 1),
('Noise Cancelling Headphones', 'Over-ear, 30h battery', 89.99, 119.99, 'headphones.jpg', 40, 1),
('Smartwatch Fitness', 'Heart rate, SpO2, GPS', 149.99, 179.99, 'smartwatch.jpg', 35, 1);

-- -------------------------------------------------
-- CLOTHING (cat_id = 2) – 12 products
-- -------------------------------------------------
INSERT INTO products (name, description, price, original_price, image, stock, category_id) VALUES
('Classic T-Shirt', '100% cotton, crew neck', 19.99, NULL, 'tshirt.jpg', 200, 2),
('Denim Jacket', 'Vintage wash, button front', 59.99, 79.99, 'denimjacket.jpg', 45, 2),
('Slim Fit Chinos', 'Stretch cotton, slim leg', 39.99, NULL, 'chinos.jpg', 80, 2),
('Running Shoes', 'Lightweight, breathable mesh', 79.99, 99.99, 'runningshoes.jpg', 60, 2),
('Wool Sweater', 'Merino wool, ribbed knit', 49.99, NULL, 'sweater.jpg', 70, 2),
('Summer Dress', 'Floral print, A-line', 34.99, 44.99, 'dress.jpg', 50, 2),
('Leather Belt', 'Genuine leather, 35mm width', 29.99, NULL, 'belt.jpg', 100, 2),
('Polo Shirt', 'Pique cotton, 3-button placket', 24.99, NULL, 'poloshirt.jpg', 120, 2),
('Winter Parka', 'Insulated, waterproof, hood', 129.99, 159.99, 'parka.jpg', 25, 2),
('Casual Sneakers', 'Canvas upper, rubber sole', 44.99, NULL, 'sneakers.jpg', 90, 2),
('Beanie Hat', 'Acrylic knit, ribbed cuff', 14.99, NULL, 'beanie.jpg', 150, 2),
('Sports Shorts', 'Dry-fit, elastic waistband', 19.99, NULL, 'shorts.jpg', 110, 2);

-- -------------------------------------------------
-- HOME & KITCHEN (cat_id = 3) – 12 products
-- -------------------------------------------------
INSERT INTO products (name, description, price, original_price, image, stock, category_id) VALUES
('Non-Stick Pan Set', '3-piece, granite coating', 49.99, 69.99, 'panset.jpg', 40, 3),
('Robot Vacuum Cleaner', 'Smart mapping, auto charge', 249.99, 299.99, 'robotvacuum.jpg', 18, 3),
('Stainless Steel Water Bottle', 'Insulated, 750ml', 24.99, NULL, 'waterbottle.jpg', 150, 3),
('Air Fryer 5.5L', 'Digital touch screen, rapid air', 79.99, 99.99, 'airfryer.jpg', 35, 3),
('Knife Set 6-Piece', 'Stainless steel, wooden block', 39.99, NULL, 'knifeset.jpg', 50, 3),
('Electric Kettle', '1.7L, auto shut-off', 29.99, NULL, 'kettle.jpg', 80, 3),
('Memory Foam Pillow', 'Orthopedic contour, washable', 34.99, NULL, 'pillow.jpg', 60, 3),
('LED Desk Lamp', 'Touch control, 5 brightness levels', 27.99, NULL, 'desklamp.jpg', 90, 3),
('Throw Blanket', 'Fleece, 50x70 inch', 22.99, NULL, 'blanket.jpg', 100, 3),
('Cookware Set 10-Piece', 'Ceramic non-stick, oven safe', 119.99, 149.99, 'cookwareset.jpg', 20, 3),
('Bath Towel Set', '100% cotton, 6 pieces', 34.99, NULL, 'towels.jpg', 70, 3),
('Wall Clock Modern', 'Silent sweep, 12 inch', 19.99, NULL, 'clock.jpg', 55, 3);

-- -------------------------------------------------
-- BOOKS (cat_id = 4) – 11 products (total = 15+12+12+11 = 50)
-- -------------------------------------------------
 insert INTO products (name, description, price, original_price, image, stock, category_id) VALUES
('Java: The Complete Reference', '13th Edition, comprehensive guide', 44.99, 54.99, 'javabook.jpg', 30, 4),
('Clean Code', 'A handbook of agile software craftsmanship', 34.99, NULL, 'cleancode.jpg', 25, 4),
('The Alchemist', 'Paulo Coelho, magical fable', 14.99, NULL, 'alchemist.jpg', 50, 4),
('Atomic Habits', 'James Clear – tiny changes, remarkable results', 19.99, 24.99, 'atomichabits.jpg', 60, 4),
('Design Patterns', 'Elements of Reusable Object-Oriented Software', 49.99, NULL, 'designpatterns.jpg', 15, 4),
('Cooking for Two', '120+ recipes perfectly portioned', 22.99, NULL, 'cooking.jpg', 40, 4),
('Mystery of the Blue Train', 'Agatha Christie – a Hercule Poirot mystery', 12.99, NULL, 'bluetrain.jpg', 35, 4),
('Cloud Atlas', 'David Mitchell – novel', 15.99, NULL, 'cloudatlas.jpg', 28, 4),
('Python Crash Course', 'Hands-on project-based introduction', 29.99, 39.99, 'pythonbook.jpg', 45, 4),
('The Great Gatsby', 'F. Scott Fitzgerald – classic', 9.99, NULL, 'gatsby.jpg', 70, 4),
('Sapiens', 'Yuval Noah Harari – a brief history of humankind', 18.99, NULL, 'sapiens.jpg', 55, 4);

select * from products;



select * from categories;
select * from users;