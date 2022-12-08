import 'dart:convert';

import 'package:flutter/material.dart';
import "package:http/http.dart" as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../../models/bookstore.dart';
import '../../utils/environment.dart';

class BookstoresProvider extends ChangeNotifier {
  final List<Bookstore> _bookstores = [];
  String baseUrl = "${Environment().config.apiUrl}/bookstore";

  String? _token;

  List<Bookstore> get bookstores => _bookstores;

  Future<void> getBookstores() async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    final response = await http.get(Uri.parse(baseUrl), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $_token',
    });

    if (response.statusCode == 200) {
      var bookstoreData = jsonDecode(response.body) as List;
      _bookstores.clear();
      for (var bookstore in bookstoreData) {
        var newBookstore = Bookstore.fromJson(bookstore);
        _bookstores.add(newBookstore);
      }
      notifyListeners();
    }
  }

  Future<bool> saveBookstore(Bookstore bookstore) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }
    final response = await http.post(Uri.parse(baseUrl),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $_token',
        },
        body: jsonEncode(bookstore.toJson()));

    if (response.statusCode == 200) {
      var bookstoreData = jsonDecode(response.body);
      bookstore.id = bookstoreData["id"].toString();
      _bookstores.add(bookstore);
      notifyListeners();
      return true;
    }
    return false;
  }

  Future<bool> updateBookstore(Bookstore bookstore) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    final response = await http.patch(Uri.parse("$baseUrl/${bookstore.id}"),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $_token',
        },
        body: jsonEncode(bookstore.toJson()));

    if (response.statusCode == 200) {
      _bookstores
          .removeWhere((oldBookstore) => oldBookstore.id == bookstore.id);
      _bookstores.add(bookstore);
      notifyListeners();
      return true;
    }
    return false;
  }

  Future<bool> removeBookstore(String id) async {
    final response = await http.delete(
      Uri.parse("$baseUrl/$id"),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $_token',
      },
    );

    if (response.statusCode == 204) {
      _bookstores.removeWhere((bookstore) => bookstore.id == id);
      notifyListeners();
      return true;
    }
    return false;
  }
}
