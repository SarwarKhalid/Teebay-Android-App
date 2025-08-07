import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import '../../routes/routes.dart';
import '../services/auth_service.service.dart';

class AuthMiddleware extends GetMiddleware {
  int setPriority;

  @override
  int? get priority => setPriority;

  AuthMiddleware({required this.setPriority});

  @override
  RouteSettings? redirect(String? route) {
    final isLoggedIn = Get.find<AuthService>().isLoggedIn;
    if (!isLoggedIn) {
      Get.log('Redirecting to login page');
      return const RouteSettings(name: Routes.login);
    }
    return null;
  }
}
