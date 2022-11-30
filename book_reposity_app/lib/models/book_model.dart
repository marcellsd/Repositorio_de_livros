class Book {
  String id;
  String title;
  int numberOfPages;
  int edition;
  DateTime publicationDate;
  String isbn;

  Book(
      {required this.id,
      required this.title,
      required this.numberOfPages,
      required this.edition,
      required this.publicationDate,
      required this.isbn});

  factory Book.fromJson(Map json) {
    return Book(
      id: json["id"].toString(),
      title: json["title"],
      numberOfPages: int.parse(json["numberOfPages"]),
      edition: int.parse(json["edition"]),
      publicationDate: DateTime.parse(json["publicationDate"].toString()),
      isbn: json["isbn"],
    );
  }
}
