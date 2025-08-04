import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';
import 'data/repositories/user.repository.dart';
import 'routes/routes.dart';
import 'shared/services/auth_service.dart';

void main() async {
  await init();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Teebay',
      initialRoute: Routes.login,
      getPages: AppPages.pages,
    );
  }
}

Future<void> init() async {
  await GetStorage.init();
  await Get.putAsync(() => AuthService.init(UserRepository()));
  Get.log('App initialized');
}
