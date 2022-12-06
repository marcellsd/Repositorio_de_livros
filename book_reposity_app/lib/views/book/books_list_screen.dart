import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../providers/author/authors_provider.dart';
import '../../providers/book/books_provider.dart';
import '../../providers/publisher/publishers_provider.dart';
import '../../widgets/book/book_item.dart';

class BooksListScreen extends StatefulWidget {
  const BooksListScreen({super.key});

  @override
  State<BooksListScreen> createState() => _BooksListScreenState();
}

class _BooksListScreenState extends State<BooksListScreen> {
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    Future.wait([
      Future.delayed(const Duration(seconds: 1)),
    ]).then((_) {
      Provider.of<AuthorsProvider>(context, listen: false).getAuthors();
      Provider.of<PublishersProvider>(context, listen: false).getPublishers();
      Provider.of<BooksProvider>(context, listen: false).getBooks().then((_) {
        setState(() {
          _isLoading = false;
        });
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    var booksProvider = context.watch<BooksProvider>();
    return Scaffold(
        appBar: AppBar(
          title: const Text("Livros",
              style: TextStyle(color: Colors.white, fontSize: 18)),
          actions: [
            IconButton(
                onPressed: () async {
                  final bool? result = await Navigator.of(context)
                      .pushNamed<bool>("/book-form-screen", arguments: null);

                  if (!mounted) return;
                  if (!result!) {
                    ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                        content: Text("Operação não autorizada!")));
                  }
                },
                icon: const Icon(Icons.add))
          ],
        ),
        body: _isLoading
            ? const Center(
                child: CircularProgressIndicator(),
              )
            : Padding(
                padding: const EdgeInsets.all(10),
                child: booksProvider.books.isNotEmpty
                    ? ListView.separated(
                        separatorBuilder: ((context, index) => const Divider(
                              height: 10,
                            )),
                        itemCount: booksProvider.books.length,
                        itemBuilder: ((context, index) => GestureDetector(
                            onTap: () => showModalBottomSheet(
                                isScrollControlled: true,
                                context: context,
                                shape: const RoundedRectangleBorder(
                                    borderRadius: BorderRadius.vertical(
                                        top: Radius.circular(15))),
                                builder: ((context) {
                                  return SizedBox(
                                    height: 150,
                                    child: Column(
                                      children: [
                                        TextButton(
                                          onPressed: () {},
                                          child: const Text(
                                            "Detalhes",
                                            style: TextStyle(
                                                fontSize: 18,
                                                fontFamily: "Acme"),
                                          ),
                                        ),
                                        TextButton(
                                          onPressed: () async {
                                            final bool? result =
                                                await Navigator.of(context)
                                                    .pushNamed<bool>(
                                                        "/book-form-screen",
                                                        arguments: booksProvider
                                                            .books[index]);

                                            if (!mounted) return;
                                            if (!result!) {
                                              ScaffoldMessenger.of(context)
                                                  .showSnackBar(const SnackBar(
                                                      content: Text(
                                                          "Operação não autorizada!")));
                                            }
                                          },
                                          child: const Text(
                                            "Editar",
                                            style: TextStyle(
                                                fontSize: 18,
                                                fontFamily: "Acme",
                                                color: Colors.green),
                                          ),
                                        ),
                                        TextButton(
                                          onPressed: () => Provider.of<
                                                      BooksProvider>(context,
                                                  listen: false)
                                              .removeBook(
                                                  booksProvider.books[index].id)
                                              .then((_) =>
                                                  Navigator.of(context).pop()),
                                          child: const Text(
                                            "Deletar",
                                            style: TextStyle(
                                                fontSize: 18,
                                                fontFamily: "Acme",
                                                color: Colors.red),
                                          ),
                                        ),
                                      ],
                                    ),
                                  );
                                })),
                            child: BookItem(booksProvider.books[index]))),
                      )
                    : const Center(
                        child: Text(
                          "Nenhum livro cadastrado!",
                          style: TextStyle(
                              color: Colors.black,
                              fontSize: 30,
                              fontFamily: "Acme"),
                        ),
                      )));
  }
}
