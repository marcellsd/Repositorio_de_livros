import 'package:flutter/material.dart';

import '/models/bookstore.dart';

class BookstoreItem extends StatelessWidget {
  Bookstore _bookstore;

  BookstoreItem(this._bookstore);
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
          _bookstore.name,
          style: const TextStyle(fontSize: 16),
        ),
        dense: true,
      ),
    );
  }
}
