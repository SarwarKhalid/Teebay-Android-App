## Architecture

![](https://static-file-service.macro.com/file/7a4842bf-80d1-47d9-8725-b1b5900ae593)

The app is designed with Clean Architecture in mind. Its separated into 3 main layers: 

## Core

The core layer is "Framework Independent". It contains the abstractions that describe the app. Domain contains all the use cases the user/app might use. The data layer uses the repository pattern. Model represents the core data objects used by the app. The core layer should not know anything about the outer layers. They are purely abstractions without any implementations. A custom Result class is used to encapsulate results and passed to callers.

A repository handles only one kind of data such  as users (UserRepository) or products (ProductRepository). A usecase can have 1 or more repositories.

## Framework

This layer contains all the non UI code. It contains the implementations for the database and network modules. The implementations for the **data sources** found in the **data layer** are here. For network calls I used Retrofit and for the database I used RoomDB. There's only 1 table: users. It can have only 1 row (the logged in user).

For dependency injection I used Dagger-Hilt. The relevant components are found inside **di** folders.

## UI/Presentation

The UI follows the classic MVVM architecture. Each screen is grouped along with all its dependent components like Viewmodels, UI states and events. Navigation is handled in a manner where each route and its corresponding information is encapsulated in one place. The UI follows unidirectional data flow: Viewmodels pass state down to UI, UI passes events via callbacks to Viewmodels. UI is made using Jetpack Compose which is a declarative UI library.

Each screen uses these 4 fundamental components:

  - Screen
  - Viewmodel
  - UI State
  - UI Events

## Navigation

![](https://static-file-service.macro.com/file/81930294-c403-4deb-9285-1c27fd62abea)

Navigation routing is setup in AppNavGraph. Each route is encapsulated in its own function and found under the package **navigation**. Navigation is done via Jetpack Navigation library. 

## Features

## Part 1: Authentication

- Login
- Signup
- Biometric auth (Not done)

*Relevant screens: login, signup*

Login: On initialization logs out current user. On submit, saves user to DB via the UserRepository and navigates to **MyProducts.** User can choose  move to sign up. 

Signup: On submit, retrieves FCM token from TokenUseCase and registers user to the server. On successful sign up, moves user to **MyProducts.** 

## Part 2: Products

- Add product
- Edit product 
- Delete product

*Relevant screens: Home, AddProduct, EditProduct*

**My Products** displays each product in a card. A floating action button takes the user to the **Add Products** page where there is a multi screen form to input the product details.  Clicking on an added product allows user to edit it. Products can be deleted. ProductUseCase is primarily responsible for handling these interactions. Products are not cached and always fetched from server to avoid stale data. 

**My Products:** An API call to **GET /api/products/** is made to retrieve all products and filtered by taking products whose seller ID is equal to the current logged in user's ID.

**Add Product:** An API call to **POST /api/products/** is made to upload the product to the server.

**Edit Product:** An API call to **PATCH api/products/{id}/** is made to edit the product on the server.

**Delete product:**  An API call to **DELETE /api/products/{id}/** is made to delete the product on the server.

## Part 3: Buy, Rent and Sell

*Relevant screens: AllProducts, ProductDetails*

**All Products**  displays the other user's products in a card. Clicking a product takes the user to the **Product Details** page where they can buy or rent the product. When a user's product is sold/lent they get a notification. Clicking on the notification will open **My Products**  and scroll to the product which will be highlighted in yellow.

**Dashboard**  shows the user's *bought*, *sold*, *rented* and *lent* products list.

**All products**: An API call to **GET /api/products/** is made to retrieve all products and filtered by taking products whose seller ID is not equal to the current logged in user's ID.

**Buy product:** An API call to **POST api/transactions/purchases/** is made.

**Rent product:** An API call to **POST api/transactions/rentals/** is made.

**Notifications:** The service FireBaseMessagingService() is setup to receive notifications from Firebase. When a notification is received the data is parsed to extract the transaction ID and whether the product was bought or rented. An API call to **GET api/transactions/purchases/{id}** (bought) or **GET api/transactions/rentals/{id}** (rented) is made to retrieve the transaction details. Then if the seller ID equals the user's ID the notification is shown to the user.

- **Dashboard:** To load the user's purchased products these are the steps(same steps are followed to load the user's sold, rented and lent products):
  1. Get all products from server. This list is called allProducts.
  2. Get all purchase transactions from server and filter them taking only the ones whose buyer ID matches the user's ID. This list is called purchasedProducts.
  3. For each Product in allProducts, if that product's ID exists in purchasedProducts then adds it to result list ***n*** times where ***n*** is how many times that product ID appears in the purchasedProducts list.
- Using this approach only 2 API calls is needed to populate the selected tab.

### Part 4: TODOS

All of these are things I had to skip due to lack of time:

- Categories are hardcoded and not fetched from the server.
- Error messages are generic and does not provide specific errors.
- All inputs fields are strings(some should be numbers)
- Turn rent options into constants
- Add loading states (Done partially)
- Add buyer/seller/date info in dashboard
- Add Toasts for results
- Implement input validation
- Add more unit tests

### Part 4: Bugs:

-  When logging in to new device a new FCM token is generated but cant save to server due to no API**.** therefore user can't receive notifications.
- If FCM token changes (such as when user reinstalls app), then user wont receive notificaions.
- Push notification sends transaction ID not product ID. Needs fix on backend.
- When app is in background, the notification is received but it does not go through my FireBaseMessagingService therefore onMessageReceived() is not called.(Change required on console).

