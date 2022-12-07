import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../providers/book/books_provider.dart';
import '../../providers/bookstore/bookstore_provider.dart';
import '../../widgets/bookstore/bookstore_item.dart';

class BookstoresListScreen extends StatefulWidget {
  const BookstoresListScreen({super.key});

  @override
  State<BookstoresListScreen> createState() => _BookstoresListScreenState();
}

class _BookstoresListScreenState extends State<BookstoresListScreen> {
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    Future.wait([
      Future.delayed(const Duration(seconds: 1)),
    ]).then((_) {
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
    var bookstoresProvider = context.watch<BookstoresProvider>();
    var booksProvider = context.watch<BooksProvider>();
    return Scaffold(
      appBar: AppBar(
        title: const Text("Livrarias",
            style: TextStyle(color: Colors.white, fontSize: 18)),
        actions: [
          IconButton(
              onPressed: () async {
                final bool? result = await Navigator.of(context)
                    .pushNamed<bool>("/bookstore-form-screen", arguments: null);

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
              child: bookstoresProvider.bookstores.isNotEmpty
                  ? ListView.separated(
                      separatorBuilder: ((context, index) => const Divider(
                            height: 10,
                          )),
                      itemCount: bookstoresProvider.bookstores.length,
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
                                              fontSize: 18, fontFamily: "Acme"),
                                        ),
                                      ),
                                      TextButton(
                                        onPressed: () async {
                                          final bool? result = await Navigator
                                                  .of(context)
                                              .pushNamed<bool>(
                                                  "/bookstore-form-screen",
                                                  arguments: bookstoresProvider
                                                      .bookstores[index]);

                                          if (!mounted) return;
                                          if (!result!) {
                                            ScaffoldMessenger.of(context)
                                                .showSnackBar(const SnackBar(
                                                    content: Text(
                                                        "Operação não autorizada!")));
                                          }
                                          Navigator.of(context).pop();
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
                                                    BookstoresProvider>(context,
                                                listen: false)
                                            .removeBookstore(bookstoresProvider
                                                .bookstores[index].id)
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
                          child: BookstoreItem(
                              bookstoresProvider.bookstores[index]))),
                    )
                  : const Center(
                      child: Text(
                        "Nenhum livraria cadastrada!",
                        style: TextStyle(
                            color: Colors.black,
                            fontSize: 30,
                            fontFamily: "Acme"),
                      ),
                    ),
            ),
    );
  }
}
