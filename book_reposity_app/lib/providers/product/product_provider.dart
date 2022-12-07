import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../../models/product.dart';

class ProductsProvider extends ChangeNotifier {
  final List<Product> _products = [];
  final baseUrl = "http://10.0.2.2:8080/api/product";

  String? _token;

  List<Product> get products => _products;

  Future<void> getProducts() async {
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
        final productsData = jsonDecode(response.body) as List;
        _products.clear();
        for (var product in productsData) {
          var newProduct = Product.fromJson(product);
          _products.add(newProduct);
        }
        notifyListeners();
      }
    } catch (error) {
      rethrow;
    }
  }

  Future<bool> saveProduct(Product product) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");
    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    final response = await http.post(
      Uri.parse(baseUrl),
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': 'Bearer $_token',
      },
      body: jsonEncode(
        product.toJson(
            int.parse(product.book.id), int.parse(product.bookstore.id)),
      ),
    );

    if (response.statusCode == 200) {
      final productData = jsonDecode(response.body);
      product.id = productData["id"].toString();
      _products.add(product);
      notifyListeners();
      return true;
    }
    return false;
  }

  Future<bool> updateProduct(Product product) async {
    final prefs = await SharedPreferences.getInstance();
    final user = prefs.getString("user");

    if (user != null) {
      final userData = jsonDecode(user);
      _token = userData["token"];
    }

    try {
      final response = await http
          .patch(Uri.parse("$baseUrl/${product.id}"),
              headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Bearer $_token',
              },
              body: jsonEncode(product.toJson(
                  int.parse(product.book.id), int.parse(product.bookstore.id))))
          .catchError((error) => throw error);

      if (response.statusCode == 200) {
        _products.removeWhere((oldProduct) => oldProduct.id == product.id);
        _products.add(product);
        notifyListeners();
        return true;
      }
      return false;
    } catch (error) {
      rethrow;
    }
  }

  Future<bool> removeProduct(String id) async {
    final response = await http.delete(Uri.parse("$baseUrl/$id"), headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer $_token',
    });

    if (response.statusCode == 204) {
      _products.removeWhere((product) => product.id == id);
      notifyListeners();
      return true;
    }
    return false;
  }
}
