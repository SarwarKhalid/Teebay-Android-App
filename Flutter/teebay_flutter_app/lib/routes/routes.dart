// lib/routes/routes.dart

import 'package:get/get.dart';
import '../features/auth/pages/login.page.dart';

class Routes {
  static const login = '/login';
}

class AppPages {
  static final pages = <GetPage>[
    GetPage(name: Routes.login, page: () => LoginPage()),
  ];
}
