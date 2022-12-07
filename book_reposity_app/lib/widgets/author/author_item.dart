import 'package:flutter/material.dart';

import '../../models/author.dart';

class AuthorItem extends StatelessWidget {
  Author author;

  AuthorItem(this.author);
  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(15),
      child: ListTile(
        minVerticalPadding: 10,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
        leading: const Text(
          "Nome:",
        ),
        tileColor: Colors.grey,
        textColor: Colors.black,
        title: Text(
          author.name,
          style: const TextStyle(fontSize: 16),
        ),
        dense: true,
        trailing: Text("Qtd. de livros: ${author.books.length}x"),
      ),
    );
  }
}
