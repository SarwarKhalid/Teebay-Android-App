import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:teebay_flutter_app/data/dtos/user.dtos.dart';
import 'package:teebay_flutter_app/shared/services/auth_service.dart';
import '../../../data/repositories/base_repository.dart'
    show ServerResponseException;
import '../../../data/repositories/user.repository.dart';
import '../forms/login.schema.dart';

class LoginController extends GetxController {
  final emailController = TextEditingController();
  final passwordController = TextEditingController();

  String? _emailValidationError;
  String? _passwordValidationError;

  final isLoading = false.obs;

  final AuthService authService;

  LoginController(this.authService);

  @override
  void onClose() {
    emailController.dispose();
    passwordController.dispose();
    super.onClose();
  }

  bool _validateForm() {
    _emailValidationError = LoginSchema.validateEmail(emailController.text);
    _passwordValidationError = LoginSchema.validatePassword(
      passwordController.text,
    );
    return _emailValidationError == null && _passwordValidationError == null;
  }

  Future<void> login() async {
    isLoading.value = true;
    final email = emailController.text.trim();
    final password = passwordController.text.trim();
    if (!_validateForm()) {
      Get.snackbar(
        'Login Failed',
        "${_emailValidationError ?? ''}, ${_passwordValidationError ?? ''}",
      );
    } else {
      try {
        final result = await authService.login(
          LoginRequest(email: email, password: password),
        );
        Get.log('Login Success: ${result.toString()}');
        Get.snackbar(
          'Login Successful',
          'Welcome back ${result.user.firstName}',
        );
        // Navigate to MyProducts
        // Get.offAllNamed(Routes.home);
      } catch (e) {
        Get.log('Login Exception: ${e.toString()}');
        if (e is ServerResponseException) {
          Get.snackbar('Login Failed', e.message);
        } else {
          Get.snackbar('Error', 'Unexpected error occurred');
        }
      }
    }
    isLoading.value = false;
  }
}
