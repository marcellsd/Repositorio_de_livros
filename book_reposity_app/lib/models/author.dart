import 'book_model.dart';

class Author {
  String name;
  List<Book>? books;

  Author(this.name, [this.books]);

  Map<String, String> toJson() => {"name": name};

  Author.fromJson(Map<String, String> json) : name = json["name"]!;
}
