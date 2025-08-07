import 'package:get/get.dart';
import 'package:teebay_flutter_app/data/repositories/user.repository.dart';
import '../../../data/dtos/user.dtos.dart';

class AuthService extends GetxService {
  final UserRepository userRepository;
  UserDto? get currentUser => userRepository.currentUser;
  bool get isLoggedIn => userRepository.isLoggedIn;

  AuthService._init(this.userRepository);

  static Future<AuthService> init(UserRepository userRepository) async {
    await userRepository.loadUser();
    return AuthService._init(userRepository);
  }

  Future<LoginResponse> login(LoginRequest request) async {
    final response = await userRepository.login(request);
    return response;
  }

  Future<void> logout() async {
    await userRepository.logout();
  }

  Future<void> loadUser() async {
    await userRepository.loadUser();
  }
}