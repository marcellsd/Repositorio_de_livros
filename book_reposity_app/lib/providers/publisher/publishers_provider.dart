import 'dart:convert';

import 'package:flutter/material.dart';
import "package:http/http.dart" as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../../models/publisher.dart';

class PublishersProvider extends ChangeNotifier {
  final List<Publisher> _publishers = [];
  String baseUrl = "http://10.0.2.2:8080/api/publisher";

  String? _token;

  List<Publisher> get publishers => _publishers;

  Future<void> getPublishers() async {
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
      var publisherData = jsonDecode(response.body) as List;
      _publishers.clear();
      for (var publisher in publisherData) {
        var newPublisher = Publisher.fromJson(publisher);
        _publishers.add(newPublisher);
      }
      notifyListeners();
    }
  }

  Future<bool> savePublisher(Publisher publisher) async {
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
        body: jsonEncode(publisher.toJson()));

    if (response.statusCode == 200) {
      var publisherData = jsonDecode(response.body);
      publisher.id = publisherData["id"].toString();
      _publishers.add(publisher);
      notifyListeners();
      return true;
    }
    return false;
  }

  Future<bool> updatePublisher(Publisher publisher) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    final response = await http.patch(Uri.parse("$baseUrl/${publisher.id}"),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': 'Bearer $_token',
        },
        body: jsonEncode(publisher.toJson()));

    if (response.statusCode == 200) {
      _publishers
          .removeWhere((oldPublisher) => oldPublisher.id == publisher.id);
      _publishers.add(publisher);
      notifyListeners();
      return true;
    }
    return false;
  }

  Future<bool> removePublisher(String id) async {
    final response = await http.delete(
      Uri.parse("$baseUrl/$id"),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $_token',
      },
    );

    if (response.statusCode == 204) {
      _publishers.removeWhere((publisher) => publisher.id == id);
      notifyListeners();
      return true;
    }
    return false;
  }
}
