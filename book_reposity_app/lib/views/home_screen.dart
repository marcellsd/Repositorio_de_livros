import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../providers/auth_provider.dart';
import '../providers/author/authors_provider.dart';
import '../providers/book/books_provider.dart';
import '../providers/bookstore/bookstore_provider.dart';
import '../providers/publisher/publishers_provider.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    Future.wait([
      Future.delayed(const Duration(seconds: 1)),
    ]).then((_) {
      Provider.of<AuthorsProvider>(context, listen: false).getAuthors();
      Provider.of<PublishersProvider>(context, listen: false).getPublishers();
      Provider.of<BookstoresProvider>(context, listen: false).getBookstores();
      Provider.of<BooksProvider>(context, listen: false).getBooks().then((_) {
        setState(() {
          _isLoading = false;
        });
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    var user = Provider.of<AuthProvider>(context, listen: false);

    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Bem vindo, ${user.username}",
          style: const TextStyle(color: Colors.white, fontSize: 18),
        ),
        actions: [
          IconButton(
              onPressed: () {
                user.logout();
                Navigator.of(context).pushReplacementNamed("/auth-screen");
              },
              icon: const Icon(Icons.logout)),
        ],
      ),
      body: _isLoading
          ? const Center(
              child: CircularProgressIndicator(),
            )
          : Padding(
              padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
              child: Center(
                child: SingleChildScrollView(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      InkWell(
                        onTap: () => Navigator.of(context)
                            .pushNamed("/author-menu-screen"),
                        child: Container(
                          alignment: Alignment.center,
                          height: 160,
                          width: double.infinity,
                          decoration: BoxDecoration(
                            color: Colors.orange,
                            borderRadius: BorderRadius.circular(15),
                          ),
                          child: Text(
                            "Autor",
                            style: Theme.of(context).textTheme.headline5,
                          ),
                        ),
                      ),
                      const SizedBox(
                        height: 10,
                      ),
                      InkWell(
                        onTap: () => Navigator.of(context)
                            .pushNamed("/book-menu-screen"),
                        child: Container(
                          alignment: Alignment.center,
                          height: 160,
                          width: double.infinity,
                          decoration: BoxDecoration(
                            color: Colors.purple,
                            borderRadius: BorderRadius.circular(15),
                          ),
                          child: Text(
                            "Livro",
                            style: Theme.of(context).textTheme.headline5,
                          ),
                        ),
                      ),
                      const SizedBox(
                        height: 10,
                      ),
                      InkWell(
                        onTap: () => Navigator.of(context)
                            .pushNamed("/publisher-menu-screen"),
                        child: Container(
                          alignment: Alignment.center,
                          height: 160,
                          width: double.infinity,
                          decoration: BoxDecoration(
                            color: Colors.blue,
                            borderRadius: BorderRadius.circular(15),
                          ),
                          child: Text(
                            "Editora",
                            style: Theme.of(context).textTheme.headline5,
                          ),
                        ),
                      ),
                      const SizedBox(
                        height: 10,
                      ),
                      InkWell(
                        onTap: () => Navigator.of(context)
                            .pushNamed("/bookstore-menu-screen"),
                        child: Container(
                          alignment: Alignment.center,
                          height: 160,
                          width: double.infinity,
                          decoration: BoxDecoration(
                            color: Colors.green,
                            borderRadius: BorderRadius.circular(15),
                          ),
                          child: Text(
                            "Livraria",
                            style: Theme.of(context).textTheme.headline5,
                          ),
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
    );
  }
}
