import 'dart:convert';

import 'package:flutter/widgets.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../../models/author.dart';

class AuthorsProvider extends ChangeNotifier {
  final List<Author> _authors = [];

  final baseUrl = "http://10.0.2.2:8080/api/author";

  List<Author> get authors => _authors;

  String? _token;

  Future<void> getAuthors() async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    try {
      final response = await http.get(Uri.parse(baseUrl), headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $_token',
      });

      if (response.statusCode == 200) {
        var authorsData = jsonDecode(response.body) as List;
        _authors.clear();
        for (var author in authorsData) {
          var newAuthor = Author.fromJson(author);
          _authors.add(newAuthor);
        }
      }
      notifyListeners();
    } catch (e) {
      rethrow;
    }
  }

  Future<bool> saveAuthor(Author author) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    List<int> bookIds = [];
    if (author.books.isNotEmpty) {
      for (var book in author.books) {
        bookIds.add(int.parse(book.id));
      }
    }

    final response = await http.post(
      Uri.parse(baseUrl),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $_token',
      },
      body: jsonEncode(
        author.toJson(bookIds),
      ),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      author.id = data["id"].toString();
      _authors.add(author);
      notifyListeners();
      return true;
    }
    return false;
  }

  Future<bool> updateAuthor(Author author) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");

    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    List<int> bookIds = [];
    if (author.books.isNotEmpty) {
      for (var book in author.books) {
        bookIds.add(int.parse(book.id));
      }
    }

    final response = await http.patch(Uri.parse("$baseUrl/${author.id}"),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $_token',
        },
        body: jsonEncode(author.toJson(bookIds)));

    if (response.statusCode == 200) {
      _authors.removeWhere((oldAuthor) => oldAuthor.id == author.id);
      _authors.add(author);
      notifyListeners();
      return true;
    }

    return false;
  }

  Future<bool> removeAuthor(String id) async {
    final response = await http.delete(Uri.parse("$baseUrl/$id"), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $_token',
    });

    if (response.statusCode == 204) {
      _authors.removeWhere((author) => author.id == id);
      notifyListeners();
      return true;
    }
    return false;
  }
}
