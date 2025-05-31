Disclaimer: I coded this challenge in a very limited time frame. At the time I was very busy with my day job and personal obligations. This app is in no way up to the standards or polish needed for production. I only implemented a handful of features and had to skip unit testing all together. I used ChatGPT for the UI components and other boiler plate code (like data classes). The UI is functional but in no way user friendly. The app does not handle all error cases and does not provide robust user feedback. 

## Architecture

![](https://static-file-service.macro.com/file/7a4842bf-80d1-47d9-8725-b1b5900ae593)

The app is designed with Clean Architecture in mind. Its separated into 3 main layers: 

### Core

The core layer is "Framework Independent". It contains the abstractions that describe the app. Domain contains all the use cases the user/app might use. The data layer uses the repository pattern. Model represents the core data objects used by the app. The core layer should not know anything about the outer layers. They are purely abstractions without any implementations. A custom Result class is used to encapsulate results and passed to callers.

Each repository normally has 2 data sources: remote and local. A repository handles only one kind of data such  as users(UserRepository) or products(ProductRepository). A usecase can have 1 or more repositories.

### Framework

This layer contains all the non UI code. It contains the implementations for the database and network modules. The implementations for the **data sources** found in the **data layer** are here. For network calls I used Retrofit and for the database I used RoomDB. There's only 1 table: users. It can have only 1 row (the logged in user).

For dependency injection I used Dagger-Hilt. The relevant components are found inside di folders.

### UI/Presentation

The UI follows the classic MVVM architecture. Each screen is grouped along with all its dependent components like Viewmodels, UI states etc. Navigation is handled in a manner where each route and its corresponding information is encapsulated in one place. The UI follows unidirectional data flow: Viewmodels pass state down to UI, UI passes events via callbacks to Viewmodels. UI is made using Jetpack Compose which is a declarative UI library.

Each screen uses these 4 fundamental components:

  - Screen
  - Viewmodel
  - UI State
  - UI Events


## Features

### Part 1: Authentication

- Login
- Signup
- Biometric auth (Pending)

Once a user performs login/signup, a network call is made followed by saving the user's information in the database. Afterwards the user is take to the **Home/My Products** page. The app starts at the Home/My Products route and navigates the user to login if they're not logged in.

Relevant screens: login, signup

### Part 2: Products

- Add product
- Edit product (Pending)
- Delete product

**Home/My Products** displays each product in a card. A floating action button takes the user to the **Add Products** page where there is a multi screen form to upload a product to the server. When adding a product only images from the gallery can be added. Camera functionality was skipped. A menu button is given to open the navigation drawer. Users can delete their products. After a delete the product list is fetched from the server again.

Relevant screens: Home, AddProduct

### Part 3: Buy, Rent and Sell

***PENDING***

### Part 4: Limitations

All of these are things I had to skip due to lack of time:

- The app does not cache the user's products on the database. Each time **My Products** page loads it gets the list from the server.
- Categories are hardcoded and not fetched from the server.
- No loading indicators are shown to tell the user content is loading.
- Error messages are generic and does not provide specific errors.
- No unit testing was done.
- Product list can be out of sync if product list is received before other API calls have gone through (like adding products).


