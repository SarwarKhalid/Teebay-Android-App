import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../forms/login.schema.dart';

class LoginController extends GetxController {

  final emailController = TextEditingController();
  final passwordController = TextEditingController();

  String? _emailValidationError;
  String? _passwordValidationError;

  final isLoading = false.obs;

  @override
  void onClose() {
    emailController.dispose();
    passwordController.dispose();
    super.onClose();
  }

  bool _validateForm() {
    _emailValidationError = LoginSchema.validateEmail(emailController.text);
    _passwordValidationError = LoginSchema.validatePassword(passwordController.text);
    return _emailValidationError == null && _passwordValidationError == null;
  }

  Future<void> login() async {
    isLoading.value = true;
    final email = emailController.text.trim();
    if (!_validateForm()) {
      Get.snackbar(
        'Login Failed',
        "${_emailValidationError ?? ''}, ${_passwordValidationError ?? ''}",
      );
    } else {
      try {
        //TODO: Implement network call for login
        Get.snackbar('Success', 'Logged in as $email');
      } catch (e) {
        Get.snackbar('Login Failed', e.toString());
      }
    }
      isLoading.value = false;
  }
}
