import 'package:get/get.dart';

class BaseRepository extends GetConnect {

  BaseRepository() {
    httpClient.baseUrl = 'http://10.0.2.2:8000/';
    httpClient.timeout = const Duration(seconds: 15);
  }

  Future<Response<T>> getRequest<T>(
    String endpoint, {
    Map<String, dynamic>? query,
    bool shouldHandleError = true,
  }) async {
    final response = await get<T>(endpoint, query: query);
    return _handleResponse(response, shouldHandleError);
  }

  Future<Response<T>> postRequest<T>(
    String endpoint,
    dynamic body, {
    bool shouldHandleError = true,
  }) async {
    final response = await post<T>(endpoint, body);
    return _handleResponse(response, shouldHandleError);
  }

  Future<Response<T>> putRequest<T>(
    String endpoint,
    dynamic body, {
    bool shouldHandleError = true,
  }) async {
    final response = await put<T>(endpoint, body);
    return _handleResponse(response, shouldHandleError);
  }

  Future<Response<T>> patchRequest<T>(
    String endpoint,
    dynamic body, {
    bool shouldHandleError = true,
  }) async {
    final response = await patch<T>(endpoint, body);
    return _handleResponse(response, shouldHandleError);
  }

  Future<Response<T>> deleteRequest<T>(
    String endpoint, {
    bool shouldHandleError = true,
  }) async {
    final response = await delete<T>(endpoint);
    return _handleResponse(response, shouldHandleError);
  }

  // Centralized response handler
  Response<T> _handleResponse<T>(
    Response<T> response,
    bool shouldHandleError,
  ) {
    if (!response.isOk && shouldHandleError) {
      final message = _extractErrorMessage(response.body);
      Get.log('API Error : $message');
      throw ServerResponseException(message);
    }
    Get.log('Response Success: ${response.toString()}');
    return response;
  }

  // Custom error extraction logic
  String _extractErrorMessage(dynamic body) {
    try {
      if (body is Map && body.containsKey('error')) {
        return body['error'].toString();
      }
      return 'Something went wrong';
    } catch (_) {
      return 'Unexpected error';
    }
  }
}

// Custom exception for API errors
class ServerResponseException implements Exception {
  final String message;
  ServerResponseException(this.message);

  @override
  String toString() => message;
}
