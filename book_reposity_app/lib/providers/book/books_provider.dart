import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../../models/book_model.dart';
import '../../utils/environment.dart';

class BooksProvider extends ChangeNotifier {
  final List<Book> _books = [];
  final baseUrl = "${Environment().config.apiUrl}/book";

  String? _token;

  List<Book> get books => _books;

  Future<void> getBooks() async {
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
        final booksData = jsonDecode(response.body) as List;
        _books.clear();
        for (var book in booksData) {
          var newBook = Book.fromJson(book);
          _books.add(newBook);
        }
        notifyListeners();
      }
    } catch (error) {
      rethrow;
    }
  }

  Future<bool> saveBook(Book book) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    List<int> authorsId = [];
    if (book.authors.isNotEmpty) {
      for (var author in book.authors) {
        authorsId.add(int.parse(author.id));
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
        book.toJson(authorsId, int.parse(book.publisher!.id)),
      ),
    );

    if (response.statusCode == 200) {
      final bookData = jsonDecode(response.body);
      book.id = bookData["id"].toString();
      _books.add(book);
      notifyListeners();
      return true;
    }
    return false;
  }

  Future<bool> updateBook(Book book) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");

    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    List<int> authorsId = [];
    if (book.authors.isNotEmpty) {
      for (var author in book.authors) {
        authorsId.add(int.parse(author.id));
      }
    }
    try {
      final response = await http
          .patch(Uri.parse("$baseUrl/${book.id}"),
              headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Bearer $_token',
              },
              body: jsonEncode(
                  book.toJson(authorsId, int.parse(book.publisher!.id))))
          .catchError((error) => throw error);

      if (response.statusCode == 200) {
        _books.removeWhere((oldBook) => oldBook.id == book.id);
        _books.add(book);
        notifyListeners();
        return true;
      }
      return false;
    } catch (error) {
      rethrow;
    }
  }

  Future<bool> removeBook(String id) async {
    final response = await http.delete(Uri.parse("$baseUrl/$id"), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $_token',
    });

    if (response.statusCode == 204) {
      _books.removeWhere((book) => book.id == id);
      notifyListeners();
      return true;
    }
    return false;
  }
}
