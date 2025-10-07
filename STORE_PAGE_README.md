# AutoParts Pro - Store Page Implementation

## Overview
This document describes the complete Store Page implementation for the AutoParts Pro Java Swing application.

## Architecture

### Package Structure
```
com/
├── model/
│   ├── Product.java          # Product entity
│   └── User.java              # User entity with UserRole enum
├── service/
│   ├── ProductService.java    # Product business logic & mock data
│   ├── AuthService.java       # Authentication service
│   └── UserSession.java       # Singleton for user session management
├── UI/
│   ├── store/
│   │   ├── StoreScreen.java   # Main store screen
│   │   ├── StoreHeader.java   # Dynamic header component
│   │   ├── StoreSidebar.java  # Filter sidebar
│   │   ├── ProductCard.java   # Product card component
│   │   └── StoreFooter.java   # Newsletter footer
│   ├── login/
│   │   ├── LoginScreen.java   # Login UI
│   │   └── LoginController.java
│   └── register/
│       └── RegisterScreen.java # Registration UI
└── Main/
    ├── App.java               # Application entry point
    ├── AppFrame.java          # Main application frame
    └── Screen.java            # Abstract base screen class
```

## Features Implemented

### 1. Store Page Layout
- **Grid Layout**: 4 columns × 3 rows (12 products per page)
- **Empty State**: Shows "No products found" when filters return no results
- **Responsive**: Product cards automatically arranged in grid

### 2. Filtering System
#### Categories Filter
- Engine, Brakes, Wheels & Tires, Suspension, Electrical
- Transmission, Exhaust, Body parts, Interior, Fluids & Chemicals
- All categories selected by default
- Real-time filtering

#### Brands Filter
- 10 brands displayed: Bosch, Brembo, Michelin, Monroe, ACDelco, NGK, Denso, Gates, Continental, Moog
- Checkbox selection
- Real-time filtering

#### Price Range Filter
- Slider control: $0 - $10,000
- Dynamic label showing current range
- Filters on slider release

#### Clear All Button
- Clears all category and brand selections
- Resets price to maximum

### 3. Sorting System
- **New**: Shows newest products first (based on `isNew` flag)
- **Price Ascending**: Lowest to highest price
- **Price Descending**: Highest to lowest price
- **Rating**: Highest rated products first

Toggle buttons with visual feedback (dark background when active)

### 4. Pagination
- Shows 12 products per page
- Page navigation: Previous, 1, 2, 3, ..., 67, 68, Next
- Current page highlighted
- Smart ellipsis for large page ranges
- Buttons enabled/disabled based on current position

### 5. Dynamic Header
#### Guest Mode (Not Logged In)
- Products button → Navigate to Store
- Login button → Navigate to Login page
- Register button → Navigate to Register page
- Cart icon → Navigate to Login page (must login first)

#### User Mode (Logged In)
- Products button → Navigate to Store
- User icon + Username display
- Cart icon → (Currently shows "Coming soon" message)

#### Admin Mode (Admin Logged In)
- Products button → Navigate to Store
- User icon + "Admin" display
- Cart icon → (Currently shows "Coming soon" message)

### 6. Footer Section
- Blue background (#2563EB)
- "Stay Updated with Latest Deals" heading
- Email subscription input field
- Subscribe button with confirmation

### 7. Navigation
- **Products Button**: Always navigates back to Store page
- **Login/Register**: Proper navigation between auth screens
- **Post-Login**: Automatically navigates to Store page
- **Post-Register**: Auto-login and navigate to Store page

## Mock Data

### Products (50 items)
- Generated with realistic automotive part names
- Categories and brands distributed evenly
- Random prices: $50 - $1000
- Random ratings: 3.0 - 5.0 stars
- First 10 products marked as "new"

### Users
#### Regular User
- **Email**: user123@gmail.com
- **Password**: Password123
- **Username**: JohnDoe

#### Admin
- **Email**: admin@autoparts.com
- **Password**: admin123
- **Username**: Admin

## Technical Implementation

### Service Layer
- **ProductService**: Singleton pattern, manages product data and filtering
- **AuthService**: Singleton pattern, manages user authentication
- **UserSession**: Singleton pattern, tracks current logged-in user

### UI Components
- **StoreScreen**: Main coordinator, handles filtering, sorting, pagination
- **ProductCard**: Reusable product display component (180×240px)
- **StoreHeader**: Dynamic header that changes based on user session
- **StoreSidebar**: Filter controls with callback listeners
- **StoreFooter**: Newsletter subscription section

### State Management
- UserSession maintains global login state
- StoreScreen maintains local state for:
  - Current product list
  - Current page number
  - Current sort option
  - Products per page count

## How to Run

1. **Navigate to project directory**:
   ```bash
   cd "c:\SEMESTER 3 - DS HCMIU\Principles of Database Management\Code\Eccommerce-for-automotives-pdm-project\Eccommerce-for-automotives-pdm-project"
   ```

2. **Compile and run with Maven**:
   ```bash
   mvn clean compile exec:java
   ```

   Or manually:
   ```bash
   mvn clean package
   java -cp target/classes com.Main.App
   ```

3. **Using IDE**: Run `com.Main.App` class directly

## Testing Scenarios

### Guest User Flow
1. App starts → Store page displays
2. Click filters → Products update in real-time
3. Change sort → Products reorder
4. Click pagination → Navigate between pages
5. Click Login → Navigate to Login page
6. Click Register → Navigate to Register page
7. Click Cart → Redirect to Login page

### Login Flow
1. Click Login button
2. Enter credentials (user123@gmail.com / Password123)
3. Submit → Navigate to Store page
4. Header now shows "JohnDoe" with profile icon
5. Cart icon available (shows "Coming soon")

### Admin Flow
1. From Store, click Login
2. Enter admin credentials (admin@autoparts.com / admin123)
3. Submit → Navigate to Store page
4. Header shows "Admin" with profile icon

### Registration Flow
1. Click Register button
2. Fill in username, email, password, confirm password
3. Submit → Auto-login and navigate to Store page
4. Header shows new username

### Filter Testing
1. Uncheck all categories → "No products found"
2. Select Engine only → Shows only engine parts
3. Adjust price slider to $100 → Shows only parts under $100
4. Click "Clear All" → All filters reset

## Database Integration (Future)

The current implementation uses mock in-memory data. To connect to a database:

1. **Update ProductService**:
   ```java
   // Replace initMockData() with database queries
   private void loadFromDatabase() {
       // JDBC connection
       // SELECT * FROM products
       // Map ResultSet to Product objects
   }
   ```

2. **Update AuthService**:
   ```java
   // Replace HashMap with database queries
   public User authenticate(String email, String password) {
       // SELECT * FROM users WHERE email = ? AND password = ?
       // Hash passwords in production!
   }
   ```

3. **Add JDBC dependencies to pom.xml**:
   ```xml
   <dependency>
       <groupId>com.mysql</groupId>
       <artifactId>mysql-connector-j</artifactId>
       <version>8.0.33</version>
   </dependency>
   ```

## Window Sizing

- **AppFrame**: 1024×768 pixels (resizable)
- **Store Content**: Optimized for 1024px width
- **Product Grid**: 760px (4 cards × 180px + gaps)
- **Sidebar**: 200px width
- **Header**: 70px height
- **Footer**: 200px height

## Known Limitations

1. **Cart Functionality**: Not yet implemented (shows placeholder message)
2. **Product Images**: No image display (shows gray placeholder)
3. **Search Bar**: Display only, not functional
4. **Hamburger Menu**: Not implemented
5. **Profile Page**: Not accessible from header icon

## Next Steps

1. Implement shopping cart functionality
2. Add product detail pages
3. Implement search functionality
4. Add product image support
5. Create admin dashboard
6. Integrate with actual database
7. Add password hashing
8. Implement order management

## Code Quality

- Clean separation of concerns (Model-View-Service)
- Singleton pattern for services
- Reusable UI components
- Event-driven architecture
- Null-safe operations
- Proper resource management

## Credits

Developed for: Principles of Database Management Course
Institution: DS HCMIU
Project: E-Commerce for Automotives (PDM Project)
