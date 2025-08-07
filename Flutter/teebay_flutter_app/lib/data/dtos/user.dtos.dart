// lib/data/dtos/user.dtos.dart

class LoginRequest {
  final String email;
  final String password;

  LoginRequest({required this.email, required this.password});

  Map<String, dynamic> toJson() {
    return {'email': email, 'password': password};
  }
}

class LoginResponse {
  final String message;
  final UserDto user;

  LoginResponse({required this.message, required this.user});

  factory LoginResponse.fromJson(Map<String, dynamic> json) {
    return LoginResponse(
      message: json['message'],
      user: UserDto.fromJson(json['user']),
    );
  }
}

class UserDto {
  final int id;
  final String email;
  final String firstName;
  final String lastName;
  final String address;
  final String firebaseToken;
  final String password; // Remove if not needed
  final DateTime dateJoined;

  UserDto({
    required this.id,
    required this.email,
    required this.firstName,
    required this.lastName,
    required this.address,
    required this.firebaseToken,
    required this.password,
    required this.dateJoined,
  });

  factory UserDto.fromJson(Map<String, dynamic> json) {
    return UserDto(
      id: json['id'],
      email: json['email'],
      firstName: json['first_name'],
      lastName: json['last_name'],
      address: json['address'],
      firebaseToken: json['firebase_console_manager_token'],
      password: json['password'],
      dateJoined: DateTime.parse(json['date_joined']),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'email': email,
      'first_name': firstName,
      'last_name': lastName,
      'address': address,
      'firebase_console_manager_token': firebaseToken,
      'password': password,
      'date_joined': dateJoined.toIso8601String(),
    };
  }
}
