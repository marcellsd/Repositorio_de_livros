import 'address.dart';
import 'book_model.dart';

class Publisher {
  String id;
  String name;
  List<Book> books;
  Address? address;

  Publisher(
      {required this.id,
      required this.name,
      this.address,
      required this.books});

  factory Publisher.fromJson(Map<String, dynamic> json) {
    List<Book> booksList = [];
    if (json["books"] != null) {
      booksList = List<Book>.from(
          json["books"].map((book) => Book.fromJson(book)).toList());
    }

    Address? newAddress;
    if (json["address"] != null) {
      newAddress = Address.fromJson(json["address"]);
    }
    return Publisher(
        id: json["id"].toString(),
        name: json["name"],
        books: booksList,
        address: newAddress);
  }

  Map<String, dynamic> toJson() {
    return {
      "name": name,
      "hqAddress": address!.hqAddress,
      "webSiteAddress": address!.webSiteAddress
    };
  }
}
