import 'dart:convert';

import 'package:flutter/widgets.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../models/author.dart';

class AuthorsProvider extends ChangeNotifier {
  final List<Author> _authors = [];

  final baseUrl = "http://192.168.0.153:8080/api/author/";

  List<Author> get authors => _authors;

  String? _token;

  Future<List<Author>> getAuthors() async {
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
        List<Author> authorsList = [];
        for (var author in authorsData) {
          var newAuthor = Author.fromJson(author);
          authorsList.add(newAuthor);
        }
        return authorsList;
      } else {
        return [];
      }
    } catch (e) {
      rethrow;
    }
  }
}
