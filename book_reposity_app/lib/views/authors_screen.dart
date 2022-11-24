import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../models/author.dart';
import '../providers/authors_provider.dart';
import '../widgets/author_item.dart';

class AuthorsScreen extends StatefulWidget {
  const AuthorsScreen({super.key});

  @override
  State<AuthorsScreen> createState() => _AuthorsScreenState();
}

class _AuthorsScreenState extends State<AuthorsScreen> {
  late Future<List<Author>> _authorsFuture;
  @override
  void initState() {
    super.initState();

    _authorsFuture =
        Provider.of<AuthorsProvider>(context, listen: false).getAuthors();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            title: const Text("Autores",
                style: TextStyle(color: Colors.white, fontSize: 18))),
        body: FutureBuilder(
          future: _authorsFuture,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else if (snapshot.connectionState == ConnectionState.active) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            } else {
              return Padding(
                padding: const EdgeInsets.all(10),
                child: ListView.separated(
                    separatorBuilder: ((context, index) => const Divider(
                          height: 10,
                        )),
                    itemCount: snapshot.data!.length,
                    itemBuilder: ((context, index) =>
                        AuthorItem(snapshot.data![index]))),
              );
            }
          },
        ));
  }
}