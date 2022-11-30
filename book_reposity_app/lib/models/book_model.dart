import 'author.dart';
import 'publisher.dart';

class Book {
  String id;
  String title;
  int numberOfPages;
  int edition;
  DateTime publicationDate;
  String isbn;
  List<Author> authors;
  Publisher publisher;

  Book(
      {required this.id,
      required this.title,
      required this.numberOfPages,
      required this.edition,
      required this.publicationDate,
      required this.isbn,
      required this.authors,
      required this.publisher});

  factory Book.fromJson(Map json) {
    List<Author> authorsList = [];
    if (json["authors"] != null) {
      authorsList = List<Author>.from(
          json["authors"].map((author) => Author.fromJson(author)).toList());
    }
    return Book(
        id: json["id"].toString(),
        title: json["title"],
        numberOfPages: int.parse(json["numberOfPages"]),
        edition: int.parse(json["edition"]),
        publicationDate: DateTime.parse(json["publicationDate"].toString()),
        isbn: json["isbn"],
        authors: authorsList,
        publisher: json["publisher"]);
  }

  Map<String, dynamic> toJson(List<int> authorsId, int publisherId) {
    return {
      "title": title,
      "numberOfPages": numberOfPages,
      "edition": edition,
      "isbn": isbn,
      "publicationDate": publicationDate,
      "authorsId": authorsId,
      "publisherId": publisherId
    };
  }
}
