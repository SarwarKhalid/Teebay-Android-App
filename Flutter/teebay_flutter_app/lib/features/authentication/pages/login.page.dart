import 'package:flutter/material.dart';
import 'package:get/get.dart';
import '../controllers/login.controller.dart';
import '../widgets/login_form.widget.dart';

class LoginPage extends StatelessWidget {
  
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    // Dependency Injection
    final controller = Get.put(LoginController());

    return Scaffold(
      appBar: AppBar(
        title: const Text('Login'),
        centerTitle: true,
      ),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16),
          child: LoginForm(controller: controller),
        ),
      ),
    );
  }
}
