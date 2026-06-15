# 🛒 Amesing Store

A full‑stack e‑commerce web application built with **Jakarta EE 9+**, **JSP**, **MySQL**, and **Apache Tomcat 10.1**. It offers a complete online shopping experience with customer features (browsing, cart, checkout, orders, reviews, wishlist) and a powerful admin panel.

![Teal & Coral Theme](https://img.shields.io/badge/Theme-Teal_&_Coral-008080?style=flat)

---

## 📋 Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Future Enhancements](#future-enhancements)
- [License](#license)

---

## ✨ Features

### 👤 Customer
- User registration / login with **profile picture upload**
- Browse products by **category** (with images) and **keyword search**
- Product detail page with **discount badges**, **suggested products**, and **reviews**
- **Shopping cart** with quantity management and real‑time stock validation
- **Buy Now** button to skip cart and go directly to checkout
- **Wishlist** management (add / remove)
- Checkout with **editable address**, multiple **payment methods** (COD, UPI, Card)
- **Order history** with status badges (Pending, Shipped, Delivered, Cancelled)
- Submit reviews **only after purchasing** the product
- User **account settings** with profile picture update

### 🛡️ Admin
- **Dashboard** with total orders, total products, pending shipments, and recent tables
- **Product management** – add, edit, delete, set original price (for discounts)
- **Category management** – add with image upload, delete
- **Order management** – view all orders, update status
- **Role‑based access** – admin pages protected by a filter

---

## 🧰 Tech Stack

| Layer               | Technology                          |
|---------------------|-------------------------------------|
| **Backend**         | Java 11+, Jakarta Servlet 5.0, JSP 3.0, JSTL 2.0 |
| **Frontend**        | HTML5, CSS3 (Teal & Coral)          |
| **Database**        | MySQL 8.0, JDBC                     |
| **Build**           | Apache Maven 3.8+                   |
| **Server**          | Apache Tomcat 10.1.x                |
| **IDE**             | Eclipse (Enterprise Java Developers)|

---

## 📦 Prerequisites
- JDK 11 or higher (tested with JDK 22)
- Apache Tomcat 10.1+
- MySQL 8.0+
- Eclipse IDE with Maven plugin (or any Jakarta‑aware IDE)

---

## 🚀 Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/AmesingStore.git
   cd AmesingStore
   ```

2. **Set up the database**
   - Create a MySQL database:
     ```sql
     CREATE DATABASE amesing_store;
     ```
   - Run the complete SQL script provided in `database/amesing_store.sql` (or the one below in the repo). It creates all tables and inserts sample data (including admin user).

3. **Configure database credentials**
   - Open `src/main/java/com/amesingstore/util/DBConnection.java`
   - Update `USER` and `PASS` with your MySQL username and password.

4. **Add product and banner images**
   - Place image files (e.g., `headphones.jpg`, `banner1.jpg`, `default-avatar.png`) inside `src/main/webapp/images/`.  
   - The SQL script references specific file names – you can use any placeholder images.

5. **Build and deploy**
   - **Option A – Eclipse:**
     - Import as **Existing Maven Project**.
     - Right‑click → **Run As → Maven install**.
     - Add project to a Tomcat 10.1 server and start.
   - **Option B – Command line:**
     ```bash
     mvn clean package
     ```
     Copy the generated `target/AmesingStore.war` to Tomcat’s `webapps/` folder.

6. **Access the application**
   - Open `http://localhost:8080/AmesingStore/home`
   - **Admin login:** `admin@amesing.com` / `admin123`

---

## 📁 Project Structure

```
AmesingStore/
├── pom.xml
├── src/main/java/com/amesingstore/
│   ├── util/
│   │   └── DBConnection.java
│   ├── model/
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   ├── Review.java
│   │   └── WishlistItem.java
│   ├── dao/
│   │   ├── UserDAO.java
│   │   ├── ProductDAO.java
│   │   ├── CategoryDAO.java
│   │   ├── CartDAO.java
│   │   ├── OrderDAO.java
│   │   ├── ReviewDAO.java
│   │   └── WishlistDAO.java
│   └── controller/
│       ├── HomeServlet.java
│       ├── ProductServlet.java
│       ├── SearchServlet.java
│       ├── LoginServlet.java
│       ├── RegisterServlet.java
│       ├── LogoutServlet.java
│       ├── AddToCartServlet.java
│       ├── CartServlet.java
│       ├── UpdateCartServlet.java
│       ├── RemoveFromCartServlet.java
│       ├── CheckoutServlet.java
│       ├── PlaceOrderServlet.java
│       ├── OrderHistoryServlet.java
│       ├── OrderDetailServlet.java
│       ├── WishlistServlet.java
│       ├── AddToWishlistServlet.java
│       ├── RemoveFromWishlistServlet.java
│       ├── SubmitReviewServlet.java
│       ├── UpdateAddressServlet.java
│       ├── AccountSettingsServlet.java
│       ├── AdminAuthFilter.java
│       └── admin/
│           ├── AdminDashboardServlet.java
│           ├── AdminProductsServlet.java
│           ├── AdminEditProductServlet.java
│           ├── AdminDeleteProductServlet.java
│           ├── AdminOrdersServlet.java
│           ├── AdminUpdateOrderServlet.java
│           └── AdminCategoriesServlet.java
├── src/main/webapp/
│   ├── WEB-INF/web.xml
│   ├── css/style.css
│   ├── images/          (product & banner images)
│   ├── header.jsp
│   ├── footer.jsp
│   ├── index.jsp
│   ├── product.jsp
│   ├── login.jsp
│   ├── register.jsp
│   ├── cart.jsp
│   ├── checkout.jsp
│   ├── orderConfirmation.jsp
│   ├── orderHistory.jsp
│   ├── orderDetail.jsp
│   ├── wishlist.jsp
│   ├── searchResults.jsp
│   ├── account.jsp
│   ├── error.jsp
│   └── admin/
│       ├── header.jsp
│       ├── footer.jsp
│       ├── dashboard.jsp
│       ├── products.jsp
│       ├── editProduct.jsp
│       ├── orders.jsp
│       └── categories.jsp
└── database/
    └── amesing_store.sql    (complete schema + sample data)
```

---

## 🧪 Usage
1. **As a customer:**
   - Register an account (or use any test account).
   - Browse products by category, use the search bar.
   - Click on a product to see details, reviews, and add to cart / wishlist.
   - Proceed to checkout, edit address if needed, select payment method, and place order.
   - View order history and write reviews after purchase.
2. **As an admin:**
   - Login with `admin@amesing.com / admin123`.
   - Access `/admin/dashboard` to see statistics.
   - Manage products, categories, and orders from the admin header.

---

## 🔮 Future Enhancements
- Integration with real payment gateways (Razorpay, Stripe)
- Email notifications (order confirmation, shipping updates)
- Responsive mobile design with Bootstrap
- Advanced filters (price range, brand, rating)
- User‑side order cancellation
- Inventory alerts for low stock

---

## 📄 License
This project is for educational purposes. You are free to use and modify it.

---

**Happy shopping! 🛍️**
