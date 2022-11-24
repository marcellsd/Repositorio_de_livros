import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../models/user_model.dart';

class AuthProvider extends ChangeNotifier {
  String _token = '';
  String _username = '';
  var baseUrl = "http://192.168.0.153:8080/api/user";

  String get token => _token;

  String get username => _username;

  Future<void> initializeToken() async {
    final prefs = await SharedPreferences.getInstance();
    final String? user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _username = userData["username"];
      _token = userData["token"];
    }
    notifyListeners();
  }

  Future<UserModel?> registerUser(UserModel newUser) async {
    try {
      final response = await http.post(
        Uri.parse(baseUrl),
        headers: <String, String>{"Content-Type": "application/json"},
        body: jsonEncode(
          {
            "username": newUser.username,
            "password": newUser.password,
            "isAuthor": newUser.isAuthor,
            "isPublisher": newUser.isPublisher
          },
        ),
      );

      if (response.statusCode == 201) {
        var data = json.decode(response.body);
        var user = UserModel(data["username"], data["password"],
            data["isAuthor"], data["isPublisher"]);
        return user;
      } else {
        return null;
      }
    } catch (e) {
      rethrow;
    }
  }

  Future<String> authenticateUser(String username, String password) async {
    try {
      final response = await http.post(
        Uri.parse("$baseUrl/auth"),
        headers: <String, String>{"Content-Type": "application/json"},
        body: jsonEncode(
          {
            "username": username,
            "password": password,
          },
        ),
      );

      if (response.statusCode == 200) {
        var data = json.decode(response.body);
        final prefs = await SharedPreferences.getInstance();
        await prefs.setString("user",
            jsonEncode({"username": data["username"], "token": data["token"]}));
        _token = data["token"];
        _username = data["username"];
        notifyListeners();
        return _token;
      }
      return '';
    } catch (e) {
      rethrow;
    }
  }

  Future<bool> isAuth() async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _username = userData["username"];
      _token = userData["token"];
      notifyListeners();
      if (_token != "") {
        return true;
      }
      return false;
    }
    return false;
  }

  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    prefs.remove("user");
    _token = "";
    _username = "";
  }
}
