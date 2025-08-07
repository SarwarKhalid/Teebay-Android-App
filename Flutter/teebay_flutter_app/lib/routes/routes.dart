import 'package:get/get.dart';
import '../features/auth/pages/login.page.dart';
import '../features/my_products/pages/my_products.page.dart';
import '../shared/middlewares/auth_middleware.middleware.dart';

class Routes {
  static const login = '/login';
  static const myProducts = '/my-products';
}

class AppPages {
  static final pages = <GetPage>[
    GetPage(name: Routes.login, page: () => LoginPage()),
     GetPage(
      name: Routes.myProducts,
      page: () => const MyProductsPage(),
      middlewares: [AuthMiddleware(setPriority: 1)],
    ),
  ];
}