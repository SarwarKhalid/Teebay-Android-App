import 'package:flutter/material.dart';
import 'package:get/get.dart';
import '../controllers/login.controller.dart';
import '../widgets/login_form.widget.dart';

class LoginPage extends StatelessWidget {

 final LoginController controller;

LoginPage({super.key}) : controller = Get.put(LoginController());

  @override
  Widget build(BuildContext context) {
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