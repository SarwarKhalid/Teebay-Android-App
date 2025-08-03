import 'package:ez_validator/ez_validator.dart';

class LoginSchema {
  static String? validateEmail(String? value) {
    return EzValidator()
        .email('Please enter a valid email')
        .required('Email is required')
        .validate(value);
  }

  static String? validatePassword(String? value) {
    return EzValidator()
        .minLength(6, 'Password must be at least 6 characters')
        .required('Password is required')
        .validate(value);
  }
}
