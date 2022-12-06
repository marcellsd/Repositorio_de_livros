import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../providers/author/authors_provider.dart';
import '../../widgets/author/author_item.dart';

class AuthorsListScreen extends StatefulWidget {
  const AuthorsListScreen({super.key});

  @override
  State<AuthorsListScreen> createState() => _AuthorsListScreenState();
}

class _AuthorsListScreenState extends State<AuthorsListScreen> {
  bool _isLoading = true;
  @override
  void initState() {
    super.initState();
    Future.wait([
      Future.delayed(const Duration(seconds: 1)),
    ]).then((_) => Provider.of<AuthorsProvider>(context, listen: false)
            .getAuthors()
            .then((_) {
          setState(() {
            _isLoading = false;
          });
        }));
  }

  @override
  Widget build(BuildContext context) {
    var authorProvider = context.watch<AuthorsProvider>();
    return Scaffold(
        appBar: AppBar(
          title: const Text("Autores",
              style: TextStyle(color: Colors.white, fontSize: 18)),
          actions: [
            IconButton(
                onPressed: () => Navigator.of(context)
                    .pushNamed("/author-form-screen", arguments: null),
                icon: const Icon(Icons.add))
          ],
        ),
        body: _isLoading
            ? const Center(
                child: CircularProgressIndicator(),
              )
            : Padding(
                padding: const EdgeInsets.all(10),
                child: authorProvider.authors.isNotEmpty
                    ? ListView.separated(
                        separatorBuilder: ((context, index) => const Divider(
                              height: 10,
                            )),
                        itemCount: authorProvider.authors.length,
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
                                            final bool? result = await Navigator
                                                    .of(context)
                                                .pushNamed<bool>(
                                                    "/author-form-screen",
                                                    arguments: authorProvider
                                                        .authors[index]);
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
                                                      AuthorsProvider>(context,
                                                  listen: false)
                                              .removeAuthor(authorProvider
                                                  .authors[index].id)
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
                            child: AuthorItem(authorProvider.authors[index]))),
                      )
                    : const Center(
                        child: Text(
                          "Nenhum autor cadastrado!",
                          style: TextStyle(
                              color: Colors.black,
                              fontSize: 30,
                              fontFamily: "Acme"),
                        ),
                      )));
  }
}
