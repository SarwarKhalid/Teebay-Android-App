import '../dtos/user.dtos.dart';
import 'base_repository.dart';
import 'package:get_storage/get_storage.dart';

class UserRepository extends BaseRepository {
  static const _userKey = 'auth_user';

  final box = GetStorage();
  UserDto? _currentUser;

  UserDto? get currentUser => _currentUser;
  bool get isLoggedIn => _currentUser != null;

  Future<LoginResponse> login(LoginRequest request) async {
    final response = await postRequest<Map<String, dynamic>>(
      'api/users/login/',
      request.toJson(),
    );
    final result = LoginResponse.fromJson(response.body!);
    await saveUser(result.user);
    return result;
  }

  Future<void> logout() async {
    _currentUser = null;
    await box.remove(_userKey);
  }

  Future<void> saveUser(UserDto user) async {
    _currentUser = user;
    await box.write(_userKey, user.toJson());
  }

  Future<void> loadUser() async {
    final userData = box.read<Map<String, dynamic>>(_userKey);
    if (userData != null) {
      _currentUser = UserDto.fromJson(userData);
    }
  }
}
