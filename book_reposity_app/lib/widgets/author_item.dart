import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';

import '../models/author.dart';

class AuthorItem extends StatelessWidget {
  Author author;

  AuthorItem(this.author);
  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(15),
      child: Slidable(
        endActionPane: ActionPane(motion: const ScrollMotion(), children: [
          SlidableAction(
            onPressed: ((context) {}),
            icon: Icons.edit,
            backgroundColor: Colors.yellow,
            foregroundColor: Colors.black,
          ),
          SlidableAction(
            onPressed: ((context) {}),
            icon: Icons.delete,
            backgroundColor: Colors.red,
            foregroundColor: Colors.black,
          ),
        ]),
        child: ListTile(
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
          leading: const Text("Autor:"),
          tileColor: Colors.grey,
          textColor: Colors.black,
          title: Text(author.name),
        ),
      ),
    );
  }
}
