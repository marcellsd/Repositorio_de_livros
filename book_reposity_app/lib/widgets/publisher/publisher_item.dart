import 'package:flutter/material.dart';

import '../../models/publisher.dart';

class PublisherItem extends StatelessWidget {
  Publisher publisher;

  PublisherItem(this.publisher);
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
          publisher.name,
          style: const TextStyle(fontSize: 16),
        ),
        dense: true,
        trailing: Text("Livros publicados: ${publisher.books.length}x"),
      ),
    );
  }
}
