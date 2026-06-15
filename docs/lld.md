# AmesingStore — Low-Level Design (LLD)

---

## 1. Package & Class Structure

```
com.amesingstore
├── util
│   └── DBConnection.java              ← static getConnection()
├── model
│   ├── User.java
│   ├── Product.java
│   ├── Category.java
│   ├── CartItem.java
│   ├── WishlistItem.java
│   ├── Order.java
│   ├── OrderItem.java
│   └── Review.java
├── dao
│   ├── UserDAO.java
│   ├── ProductDAO.java
│   ├── CategoryDAO.java
│   ├── CartDAO.java
│   ├── WishlistDAO.java
│   ├── OrderDAO.java
│   └── ReviewDAO.java
└── controller
    ├── AdminAuthFilter.java           ← @WebFilter("/admin/*")
    ├── LoginServlet.java              ← POST /login
    ├── LogoutServlet.java             ← GET  /logout
    ├── RegisterServlet.java           ← POST /register
    ├── HomeServlet.java               ← GET  /home
    ├── ProductServlet.java            ← GET  /product
    ├── SearchServlet.java             ← GET  /search
    ├── CartServlet.java               ← GET  /cart
    ├── AddToCartServlet.java          ← POST /addToCart
    ├── UpdateCartServlet.java         ← POST /updateCart
    ├── RemoveFromCartServlet.java     ← POST /removeFromCart
    ├── WishlistServlet.java           ← GET  /wishlist
    ├── AddToWishlistServlet.java      ← POST /addToWishlist
    ├── RemoveFromWishlistServlet.java ← POST /removeFromWishlist
    ├── CheckoutServlet.java           ← GET  /checkout
    ├── PlaceOrderServlet.java         ← POST /placeOrder
    ├── OrderHistoryServlet.java       ← GET  /orderHistory
    ├── OrderDetailServlet.java        ← GET  /orderDetail
    ├── AccountSettingsServlet.java    ← GET+POST /account
    ├── UpdateAddressServlet.java      ← POST /updateAddress
    ├── BuyNowServlet.java             ← POST /buyNow
    ├── SubmitReviewServlet.java       ← POST /submitReview
    └── admin
        ├── AdminDashboardServlet.java    ← GET  /admin/dashboard
        ├── AdminProductsServlet.java     ← GET  /admin/products
        ├── AdminEditProductServlet.java  ← GET+POST /admin/editProduct
        ├── AdminDeleteProductServlet.java← POST /admin/deleteProduct
        ├── AdminCategoriesServlet.java   ← GET+POST /admin/categories
        ├── AdminDeleteCategoryServlet.java← POST /admin/deleteCategory
        ├── AdminOrdersServlet.java       ← GET  /admin/orders
        └── AdminUpdateOrderServlet.java  ← POST /admin/updateOrder
```

---

## 2. Class Diagram — Model Layer

```mermaid
classDiagram
    class User {
        +int id
        +String fullname
        +String email
        +String password
        +String role
        +String phone
        +String street
        +String village
        +String city
        +String landmark
        +String pincode
        +String profileImage
        +getId() int
        +getRole() String
    }

    class Product {
        +int id
        +String name
        +String description
        +double price
        +double originalPrice
        +String image
        +int stock
        +int categoryId
        +String categoryName
        +getDiscountPercent() int
    }

    class Category {
        +int id
        +String name
        +String image
    }

    class CartItem {
        +int id
        +int userId
        +int productId
        +int quantity
        +String productName
        +double price
    }

    class WishlistItem {
        +int id
        +int userId
        +int productId
        +String productName
        +double price
        +String image
    }

    class Order {
        +int id
        +int userId
        +double total
        +String status
        +Timestamp orderDate
        +String paymentMethod
        +String shippingAddress
        +String customerName
        +List~OrderItem~ items
    }

    class OrderItem {
        +int id
        +int orderId
        +int productId
        +int quantity
        +double price
        +String productName
    }

    class Review {
        +int id
        +int userId
        +int productId
        +int rating
        +String comment
        +Timestamp reviewDate
        +String userName
    }

    Order "1" --> "many" OrderItem : contains
    User "1" --> "many" Order : places
    User "1" --> "many" CartItem : owns
    User "1" --> "many" WishlistItem : saves
    User "1" --> "many" Review : writes
    Product "1" --> "many" CartItem : in
    Product "1" --> "many" WishlistItem : in
    Product "1" --> "many" OrderItem : sold_as
    Product "1" --> "many" Review : receives
    Category "1" --> "many" Product : groups
```

---

## 3. Class Diagram — DAO Layer

```mermaid
classDiagram
    class DBConnection {
        -String URL
        -String USER
        -String PASS
        +getConnection() Connection$
    }

    class UserDAO {
        +validate(email, password) User
        +getUserById(userId) User
        +register(user) boolean
        +updateProfile(user) void
        +updateAddress(user) void
    }

    class ProductDAO {
        +getAllProducts() List~Product~
        +getProductsByCategory(categoryId) List~Product~
        +searchProducts(keyword) List~Product~
        +getProductById(id) Product
        +addProduct(p) void
        +updateProduct(p) void
        +deleteProduct(id) void
        +updateStock(productId, newStock) boolean
        +reduceStock(productId, quantity) void
        -mapProduct(rs) Product
    }

    class CategoryDAO {
        +getAllCategories() List~Category~
        +addCategory(name, image) void
        +deleteCategory(id) void
    }

    class CartDAO {
        +addToCart(userId, productId, qty) void
        +getCartItems(userId) List~CartItem~
        +updateQuantity(cartId, qty) void
        +removeItem(cartId) void
        +clearCart(userId) void
    }

    class WishlistDAO {
        +addToWishlist(userId, productId) void
        +removeFromWishlist(userId, productId) void
        +getWishlistByUser(userId) List~WishlistItem~
        +isInWishlist(userId, productId) boolean
    }

    class OrderDAO {
        +placeOrder(userId, cartItems, payment, address) int
        +getOrdersByUser(userId) List~Order~
        +getOrderById(orderId) Order
        +updateOrderStatus(orderId, status) void
        +getAllOrders() List~Order~
    }

    class ReviewDAO {
        +addReview(review) void
        +getReviewsByProduct(productId) List~Review~
        +hasUserPurchasedProduct(userId, productId) boolean
    }

    UserDAO ..> DBConnection : uses
    ProductDAO ..> DBConnection : uses
    CategoryDAO ..> DBConnection : uses
    CartDAO ..> DBConnection : uses
    WishlistDAO ..> DBConnection : uses
    OrderDAO ..> DBConnection : uses
    ReviewDAO ..> DBConnection : uses
    OrderDAO ..> ProductDAO : calls reduceStock
    OrderDAO ..> CartDAO : calls clearCart
```

---

## 4. Sequence Diagram — Login Flow

```mermaid
sequenceDiagram
    actor U as User
    participant JSP as login.jsp
    participant S as LoginServlet
    participant D as UserDAO
    participant DB as MySQL

    U->>JSP: Open login page
    U->>S: POST /login (email, password)
    S->>D: validate(email, password)
    D->>DB: SELECT * FROM users WHERE email=? AND password=?
    DB-->>D: ResultSet
    alt user found
        D-->>S: User object
        S->>S: session.setAttribute("user", user)
        S-->>U: redirect /home
    else not found
        D-->>S: null
        S-->>JSP: forward (error="Invalid email or password")
        JSP-->>U: show error message
    end
```

---

## 5. Sequence Diagram — Registration Flow

```mermaid
sequenceDiagram
    actor U as User
    participant JSP as register.jsp
    participant S as RegisterServlet
    participant D as UserDAO
    participant DB as MySQL
    participant FS as FileSystem

    U->>S: POST /register (multipart form)
    S->>S: request.getPart("profileImage")
    alt image uploaded
        S->>FS: write file to /images/
    end
    S->>S: build User POJO
    S->>D: register(user)
    D->>DB: INSERT INTO users (...)
    alt success
        DB-->>D: rows > 0
        D-->>S: true
        S-->>U: redirect login.jsp?registered=true
    else duplicate email
        DB-->>D: SQLIntegrityConstraintViolationException
        D-->>S: false
        S-->>JSP: forward (error="Email already exists")
        JSP-->>U: show error
    end
```

---

## 6. Sequence Diagram — Home / Browse Products

```mermaid
sequenceDiagram
    actor U as User
    participant S as HomeServlet
    participant PD as ProductDAO
    participant CD as CategoryDAO
    participant DB as MySQL
    participant JSP as index.jsp

    U->>S: GET /home  (optional ?category=id)
    S->>S: read "category" param
    alt category param present
        S->>PD: getProductsByCategory(id)
        PD->>DB: SELECT products WHERE category_id=?
    else no filter
        S->>PD: getAllProducts()
        PD->>DB: SELECT products JOIN categories
    end
    DB-->>PD: ResultSet
    PD-->>S: List~Product~
    S->>CD: getAllCategories()
    CD->>DB: SELECT * FROM categories
    DB-->>CD: ResultSet
    CD-->>S: List~Category~
    S->>S: request.setAttribute("products", ...)
    S->>S: request.setAttribute("categories", ...)
    S->>JSP: forward
    JSP-->>U: rendered product grid + sidebar
```

---

## 7. Sequence Diagram — Add to Cart

```mermaid
sequenceDiagram
    actor U as User
    participant S as AddToCartServlet
    participant CD as CartDAO
    participant DB as MySQL

    U->>S: POST /addToCart (productId, qty)
    S->>S: check session user
    alt not logged in
        S-->>U: redirect login.jsp
    else logged in
        S->>CD: addToCart(userId, productId, qty)
        CD->>DB: SELECT id,quantity FROM cart WHERE user_id=? AND product_id=?
        alt row exists
            DB-->>CD: existing row
            CD->>DB: UPDATE cart SET quantity=newQty WHERE id=?
        else new item
            CD->>DB: INSERT INTO cart (user_id, product_id, quantity)
        end
        DB-->>CD: OK
        CD-->>S: done
        S-->>U: redirect /cart
    end
```

---

## 8. Sequence Diagram — Checkout & Place Order

```mermaid
sequenceDiagram
    actor U as User
    participant CS as CheckoutServlet
    participant PS as PlaceOrderServlet
    participant CD as CartDAO
    participant UD as UserDAO
    participant OD as OrderDAO
    participant PD as ProductDAO
    participant DB as MySQL

    U->>CS: GET /checkout
    CS->>CS: auth check
    CS->>CD: getCartItems(userId)
    CD->>DB: SELECT cart items
    DB-->>CD: rows
    CD-->>CS: List~CartItem~
    CS->>PD: getProductById(each item)
    PD->>DB: SELECT stock
    DB-->>PD: stock value
    alt stock insufficient
        CS-->>U: forward cart.jsp with error
    else stock OK
        CS-->>U: forward checkout.jsp
        U->>PS: POST /placeOrder (address fields + paymentMethod)
        PS->>UD: updateAddress(user)
        UD->>DB: UPDATE users SET address fields
        PS->>CD: getCartItems(userId)
        CD->>DB: SELECT cart
        DB-->>CD: items
        CD-->>PS: List~CartItem~
        PS->>OD: placeOrder(userId, items, payment, address)
        OD->>DB: setAutoCommit(false)
        OD->>DB: INSERT INTO orders (...)
        DB-->>OD: generated orderId
        loop each CartItem
            OD->>DB: INSERT INTO order_items (...)
            OD->>PD: reduceStock(productId, qty)
            PD->>DB: UPDATE products SET stock=stock-qty WHERE stock>=qty
            alt stock underflow
                DB-->>PD: 0 rows updated
                PD-->>OD: throw SQLException
                OD->>DB: ROLLBACK
                OD-->>PS: throw exception
                PS-->>U: forward checkout.jsp with error
            end
        end
        OD->>CD: clearCart(userId)
        CD->>DB: DELETE FROM cart WHERE user_id=?
        OD->>DB: COMMIT
        OD-->>PS: orderId
        PS-->>U: redirect orderConfirmation.jsp?orderId=X
    end
```

---

## 9. Sequence Diagram — Order History & Detail

```mermaid
sequenceDiagram
    actor U as User
    participant OH as OrderHistoryServlet
    participant OD as OrderDetailServlet
    participant DAO as OrderDAO
    participant DB as MySQL

    U->>OH: GET /orderHistory
    OH->>OH: auth check
    OH->>DAO: getOrdersByUser(userId)
    DAO->>DB: SELECT * FROM orders WHERE user_id=? ORDER BY order_date DESC
    DB-->>DAO: rows
    DAO-->>OH: List~Order~
    OH-->>U: forward orderHistory.jsp

    U->>OD: GET /orderDetail?orderId=X
    OD->>OD: auth check
    OD->>DAO: getOrderById(orderId)
    DAO->>DB: SELECT * FROM orders WHERE id=?
    DB-->>DAO: order row
    DAO->>DB: SELECT order_items JOIN products WHERE order_id=?
    DB-->>DAO: item rows
    DAO-->>OD: Order (with items list)
    OD-->>U: forward orderDetail.jsp
```

---

## 10. Sequence Diagram — Admin Dashboard

```mermaid
sequenceDiagram
    actor A as Admin
    participant F as AdminAuthFilter
    participant S as AdminDashboardServlet
    participant OD as OrderDAO
    participant PD as ProductDAO
    participant DB as MySQL

    A->>F: GET /admin/dashboard
    F->>F: check session role == "admin"
    alt not admin
        F-->>A: redirect login.jsp
    else is admin
        F->>S: chain.doFilter → doGet()
        S->>OD: getAllOrders()
        OD->>DB: SELECT orders JOIN users ORDER BY date DESC
        DB-->>OD: all orders
        OD-->>S: List~Order~
        S->>S: compute totalOrders, pendingCount, recentOrders(top 5)
        S->>PD: getAllProducts()
        PD->>DB: SELECT products JOIN categories
        DB-->>PD: all products
        PD-->>S: List~Product~
        S->>S: sort by id desc, take top 5
        S->>S: setAttributes (totalOrders, totalProducts, pendingCount, ...)
        S-->>A: forward admin/dashboard.jsp
    end
```

---

## 11. Sequence Diagram — Admin Edit Product

```mermaid
sequenceDiagram
    actor A as Admin
    participant S as AdminEditProductServlet
    participant PD as ProductDAO
    participant CD as CategoryDAO
    participant DB as MySQL

    A->>S: GET /admin/editProduct?id=X (edit) or no id (add)
    alt edit mode (id present)
        S->>PD: getProductById(id)
        PD->>DB: SELECT product WHERE id=?
        DB-->>PD: product row
        PD-->>S: Product object
    end
    S->>CD: getAllCategories()
    CD->>DB: SELECT categories
    DB-->>CD: rows
    CD-->>S: List~Category~
    S-->>A: forward editProduct.jsp (pre-filled form or blank)

    A->>S: POST /admin/editProduct (form fields)
    S->>S: build Product POJO from params
    alt id param present (update)
        S->>PD: updateProduct(p)
        PD->>DB: UPDATE products SET ... WHERE id=?
    else new product (insert)
        S->>PD: addProduct(p)
        PD->>DB: INSERT INTO products (...)
    end
    DB-->>PD: OK
    S-->>A: redirect /admin/products
```

---

## 12. Sequence Diagram — Review Submission

```mermaid
sequenceDiagram
    actor U as User
    participant S as SubmitReviewServlet
    participant RD as ReviewDAO
    participant DB as MySQL

    U->>S: POST /submitReview (productId, rating, comment)
    S->>S: auth check (session user)
    S->>RD: hasUserPurchasedProduct(userId, productId)
    RD->>DB: SELECT COUNT(*) FROM order_items JOIN orders WHERE user_id=? AND product_id=?
    DB-->>RD: count
    alt count > 0 (verified buyer)
        RD-->>S: true
        S->>S: build Review POJO
        S->>RD: addReview(review)
        RD->>DB: INSERT INTO reviews (user_id, product_id, rating, comment)
        DB-->>RD: OK
        S-->>U: redirect /product?id=X
    else not a buyer
        RD-->>S: false
        S-->>U: redirect product page with error
    end
```

---

## 13. AdminAuthFilter Logic Flow

```mermaid
flowchart TD
    A[Incoming Request to /admin/*] --> B{Session exists?}
    B -- No --> E[Redirect to login.jsp]
    B -- Yes --> C{user attr in session?}
    C -- No --> E
    C -- Yes --> D{user.role == admin?}
    D -- No --> E
    D -- Yes --> F[chain.doFilter → continue to Servlet]
```

---

## 14. CartDAO Upsert Logic Flow

```mermaid
flowchart TD
    A[addToCart called] --> B[SELECT id,qty FROM cart WHERE user_id=? AND product_id=?]
    B --> C{Row found?}
    C -- Yes --> D[newQty = existing + requested]
    D --> E[UPDATE cart SET quantity=newQty WHERE id=?]
    C -- No --> F[INSERT INTO cart user_id, product_id, quantity]
    E --> G[Done]
    F --> G
```

---

## 15. OrderDAO Transaction Flow

```mermaid
flowchart TD
    A[placeOrder called] --> B[con.setAutoCommit = false]
    B --> C[Calculate total from cartItems]
    C --> D[INSERT INTO orders status=Pending]
    D --> E[Get generated orderId]
    E --> F[For each CartItem]
    F --> G[INSERT INTO order_items batch]
    G --> H[reduceStock productId qty]
    H --> I{Stock row updated?}
    I -- No rows --> J[throw SQLException Insufficient stock]
    J --> K[con.rollback]
    I -- Yes --> L{More items?}
    L -- Yes --> F
    L -- No --> M[clearCart userId]
    M --> N[con.commit]
    N --> O[return orderId]
    K --> P[propagate exception to caller]
```

---

## 16. Servlet URL Mapping Summary

| URL Pattern | HTTP Method | Servlet | Auth Required |
|---|---|---|---|
| `/home` | GET | HomeServlet | No |
| `/login` | POST | LoginServlet | No |
| `/register` | POST | RegisterServlet | No |
| `/logout` | GET | LogoutServlet | No |
| `/product` | GET | ProductServlet | No |
| `/search` | GET | SearchServlet | No |
| `/cart` | GET | CartServlet | Yes (user) |
| `/addToCart` | POST | AddToCartServlet | Yes (user) |
| `/updateCart` | POST | UpdateCartServlet | Yes (user) |
| `/removeFromCart` | POST | RemoveFromCartServlet | Yes (user) |
| `/wishlist` | GET | WishlistServlet | Yes (user) |
| `/addToWishlist` | POST | AddToWishlistServlet | Yes (user) |
| `/removeFromWishlist` | POST | RemoveFromWishlistServlet | Yes (user) |
| `/checkout` | GET | CheckoutServlet | Yes (user) |
| `/placeOrder` | POST | PlaceOrderServlet | Yes (user) |
| `/buyNow` | POST | BuyNowServlet | Yes (user) |
| `/orderHistory` | GET | OrderHistoryServlet | Yes (user) |
| `/orderDetail` | GET | OrderDetailServlet | Yes (user) |
| `/account` | GET+POST | AccountSettingsServlet | Yes (user) |
| `/updateAddress` | POST | UpdateAddressServlet | Yes (user) |
| `/submitReview` | POST | SubmitReviewServlet | Yes (user) |
| `/admin/dashboard` | GET | AdminDashboardServlet | Yes (admin) |
| `/admin/products` | GET | AdminProductsServlet | Yes (admin) |
| `/admin/editProduct` | GET+POST | AdminEditProductServlet | Yes (admin) |
| `/admin/deleteProduct` | POST | AdminDeleteProductServlet | Yes (admin) |
| `/admin/categories` | GET+POST | AdminCategoriesServlet | Yes (admin) |
| `/admin/deleteCategory` | POST | AdminDeleteCategoryServlet | Yes (admin) |
| `/admin/orders` | GET | AdminOrdersServlet | Yes (admin) |
| `/admin/updateOrder` | POST | AdminUpdateOrderServlet | Yes (admin) |

---

## 17. Key SQL Queries Reference

### UserDAO
```sql
-- Login
SELECT * FROM users WHERE email=? AND password=?

-- Register
INSERT INTO users (fullname, email, password, phone, street, village, city, landmark, pincode, profile_image)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)

-- Update address
UPDATE users SET phone=?, street=?, village=?, city=?, landmark=?, pincode=? WHERE id=?
```

### ProductDAO
```sql
-- All products with category
SELECT p.*, c.name as category_name FROM products p
LEFT JOIN categories c ON p.category_id = c.id

-- Reduce stock (atomic check)
UPDATE products SET stock = stock - ? WHERE id=? AND stock >= ?

-- Search
SELECT p.*, c.name as category_name FROM products p
LEFT JOIN categories c ON p.category_id = c.id WHERE p.name LIKE ?
```

### CartDAO
```sql
-- Upsert check
SELECT id, quantity FROM cart WHERE user_id=? AND product_id=?

-- Get cart with prices
SELECT c.id, c.product_id, c.quantity, p.name, p.price
FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id=?
```

### OrderDAO
```sql
-- Place order header
INSERT INTO orders (user_id, total, status, payment_method, shipping_address)
VALUES (?, ?, 'Pending', ?, ?)

-- Order items batch
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?,?,?,?)

-- User orders
SELECT * FROM orders WHERE user_id=? ORDER BY order_date DESC

-- Admin: all orders with customer name
SELECT o.*, u.fullname AS customer_name FROM orders o
JOIN users u ON o.user_id = u.id ORDER BY o.order_date DESC
```

### ReviewDAO
```sql
-- Verified purchase check
SELECT COUNT(*) FROM order_items oi
JOIN orders o ON oi.order_id = o.id
WHERE o.user_id=? AND oi.product_id=?

-- Product reviews
SELECT r.*, u.fullname FROM reviews r
JOIN users u ON r.user_id = u.id
WHERE r.product_id=? ORDER BY r.review_date DESC
```

---

*LLD — AmesingStore v1.0-SNAPSHOT — May 2026*
