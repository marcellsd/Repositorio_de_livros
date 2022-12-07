import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import '../../models/book_model.dart';

class BookItem extends StatelessWidget {
  Book book;

  BookItem(this.book);
  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(15),
      child: ListTile(
        minVerticalPadding: 10,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
        leading: const Text(
          "Título:",
        ),
        tileColor: Colors.grey,
        textColor: Colors.black,
        title: Text(
          book.title,
          style: const TextStyle(fontSize: 16),
        ),
        subtitle: Text(
          "edição: ${book.edition}",
          style: const TextStyle(fontSize: 14),
        ),
        dense: true,
        trailing: Text("Publicação: ${DateFormat(
          "dd/MM/yyyy",
        ).format(book.publicationDate)}"),
      ),
    );
  }
}
