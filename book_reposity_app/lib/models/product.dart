import 'dart:ffi';

import 'book_model.dart';
import 'bookstore.dart';

class Product {
  String id;
  Book book;
  Float price;
  int quantity;
  Bookstore bookstore;

  Product(
      {required this.id,
      required this.book,
      required this.price,
      required this.quantity,
      required this.bookstore});

  factory Product.fromJson(Map json) {
    Book newBook = Book.fromJson(json["book"]);

    Bookstore newBookstore = Bookstore.fromJson(json["bookstore"]);

    return Product(
        id: json["id"].toString(),
        book: newBook,
        price: json["price"],
        quantity: json["quantity"],
        bookstore: newBookstore);
  }

  Map<String, dynamic> toJson(int bookId, int bookstoreId) {
    return {
      "bookId": bookId,
      "price": price,
      "quantity": quantity,
      "booktoreId": bookstoreId
    };
  }
}
