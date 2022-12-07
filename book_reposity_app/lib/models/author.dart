import 'book_model.dart';

class Author {
  String id;
  String name;
  List<Book> books;

  Author(this.id, this.name, this.books);

  factory Author.fromJson(Map json) {
    List<Book> booksList = [];
    if (json["books"] != null) {
      booksList = List<Book>.from(
          json["books"].map((book) => Book.fromJson(book)).toList());
    }
    return Author(json["id"].toString(), json["name"], booksList);
  }

  Map<String, dynamic> toJson(List<int> booksId) {
    return {"name": name, "booksId": booksId};
  }
}
