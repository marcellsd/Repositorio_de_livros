import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../providers/book/books_provider.dart';
import '../../providers/publisher/publishers_provider.dart';
import '../../widgets/publisher/publisher_item.dart';

class PublishersListScreen extends StatefulWidget {
  const PublishersListScreen({super.key});

  @override
  State<PublishersListScreen> createState() => _PublishersListScreenState();
}

class _PublishersListScreenState extends State<PublishersListScreen> {
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    Future.wait([
      Future.delayed(const Duration(seconds: 1)),
    ]).then((_) {
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
    var publishersProvider = context.watch<PublishersProvider>();
    return Scaffold(
        appBar: AppBar(
          title: const Text("Editoras",
              style: TextStyle(color: Colors.white, fontSize: 18)),
          actions: [
            IconButton(
                onPressed: () async {
                  final bool? result = await Navigator.of(context)
                      .pushNamed<bool>("/publisher-form-screen",
                          arguments: null);

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
                child: publishersProvider.publishers.isNotEmpty
                    ? ListView.separated(
                        separatorBuilder: ((context, index) => const Divider(
                              height: 10,
                            )),
                        itemCount: publishersProvider.publishers.length,
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
                                                    "/publisher-form-screen",
                                                    arguments:
                                                        publishersProvider
                                                            .publishers[index]);

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
                                          onPressed: () =>
                                              Provider.of<PublishersProvider>(
                                                      context,
                                                      listen: false)
                                                  .removePublisher(
                                                      publishersProvider
                                                          .publishers[index].id)
                                                  .then((_) =>
                                                      Navigator.of(context)
                                                          .pop()),
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
                            child: PublisherItem(
                                publishersProvider.publishers[index]))),
                      )
                    : const Center(
                        child: Text(
                          "Nenhuma editora cadastrada!",
                          style: TextStyle(
                              color: Colors.black,
                              fontSize: 30,
                              fontFamily: "Acme"),
                        ),
                      )));
  }
}
