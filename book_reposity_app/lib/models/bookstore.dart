import 'product.dart';

class Bookstore {
  String id;
  String name;
  List<Product>? products;

  Bookstore({required this.id, required this.name, this.products});

  factory Bookstore.fromJson(Map<String, dynamic> json) {
    List<Product> productsList = [];
    if (json["products"] != null) {
      productsList = List<Product>.from(json["products"]
          .map((product) => Product.fromJson(product))
          .toList());
    }

    return Bookstore(
        id: json["id"].toString(), name: json["name"], products: productsList);
  }

  Map<String, dynamic> toJson() {
    return {"name": name};
  }
}
