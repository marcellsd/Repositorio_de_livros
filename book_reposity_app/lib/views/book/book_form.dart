import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import '../../models/author.dart';
import '../../models/publisher.dart';
import '../../providers/publisher/publishers_provider.dart';
import '/providers/author/authors_provider.dart';
import '../../models/book_model.dart';
import '../../providers/book/books_provider.dart';

class BookForm extends StatefulWidget {
  const BookForm({super.key});

  @override
  State<BookForm> createState() => _BookFormState();
}

class _BookFormState extends State<BookForm> {
  final _titleController = TextEditingController();
  final _isbnController = TextEditingController();
  final _numberOfPagesController = TextEditingController();
  final _editionController = TextEditingController();
  final List<Author> _authorsList = [];
  Publisher? _selectedPublisher;
  DateTime? _publicationDate;
  Book? _book;

  var _isEditing = false;
  var _isLoadingMode = true;
  final _formKey = GlobalKey<FormState>();

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    if (_isLoadingMode) {
      _book = ModalRoute.of(context)!.settings.arguments as Book?;
      if (_book != null) {
        _titleController.text = _book!.title;
        _isbnController.text = _book!.isbn;
        _numberOfPagesController.text = _book!.numberOfPages.toString();
        _editionController.text = _book!.edition.toString();
        _book!.authors.forEach((author) {
          _authorsList.add(author);
        });
        _publicationDate = _book!.publicationDate;
        _selectedPublisher = _book!.publisher;
        _isEditing = true;
      }
      _isLoadingMode = false;
    }
  }

  void _validateForm() {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    if (_authorsList.isEmpty) {
      buildAlertDialog("Selecione ao menos um autor");
      return;
    }

    if (_publicationDate == null) {
      buildAlertDialog("Selecione uma data de publicação!");
      return;
    }

    if (_selectedPublisher == null) {
      buildAlertDialog("Selecione uma editora!");
      return;
    }
    Book book;

    book = Book(
        id: _isEditing ? _book!.id : "",
        title: _titleController.text,
        authors: _authorsList,
        publisher: _selectedPublisher!,
        isbn: _isbnController.text,
        numberOfPages: int.parse(_numberOfPagesController.text),
        publicationDate: _publicationDate!,
        edition: int.parse(_editionController.text));

    if (_isEditing) {
      Provider.of<BooksProvider>(context, listen: false)
          .updateBook(book)
          .then((result) => Navigator.of(context).pop(result));
    } else {
      Provider.of<BooksProvider>(context, listen: false)
          .saveBook(book)
          .then((_) => Navigator.of(context).pop<bool>(true));
    }
  }

  void buildAlertDialog(String message) {
    showDialog(
        context: context,
        builder: ((context) => AlertDialog(
              content: Text(
                message,
                style: const TextStyle(fontSize: 18, color: Colors.black),
              ),
              actions: [
                TextButton(
                  onPressed: () => Navigator.of(context).pop(),
                  child: const Center(
                    child: Text(
                      "Ok",
                      style: TextStyle(fontSize: 16),
                    ),
                  ),
                )
              ],
            )));
  }

  @override
  Widget build(BuildContext context) {
    var authorsProvider = context.read<AuthorsProvider>();
    var publishersProvider = context.read<PublishersProvider>();
    return Scaffold(
      appBar: AppBar(
        title: Text(
          _isEditing ? "Editando livro" : "Criando livro",
          style: const TextStyle(color: Colors.white, fontSize: 18),
        ),
        actions: [
          IconButton(onPressed: _validateForm, icon: const Icon(Icons.save))
        ],
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 15),
          child: Card(
            child: Form(
                key: _formKey,
                child: Column(
                  children: [
                    const Text(
                      "Formulário do livro",
                      style: TextStyle(
                          fontFamily: "Acme",
                          fontSize: 24,
                          color: Colors.black),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    SizedBox(
                      height: 50,
                      child: TextFormField(
                        cursorColor: Colors.black,
                        style: Theme.of(context).textTheme.headline3,
                        controller: _titleController,
                        decoration: InputDecoration(
                          labelText: "Título do livro",
                          hintText: "Insira o título do livro",
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15),
                          ),
                        ),
                        validator: (value) {
                          if (value!.isEmpty) {
                            return "Insira um título";
                          } else {
                            return null;
                          }
                        },
                      ),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    SizedBox(
                      height: 50,
                      child: TextFormField(
                        cursorColor: Colors.black,
                        style: Theme.of(context).textTheme.headline3,
                        controller: _editionController,
                        decoration: InputDecoration(
                          labelText: "Edição do livro",
                          hintText: "Insira a edição do livro",
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15),
                          ),
                        ),
                        validator: (value) {
                          if (value!.isEmpty) {
                            return "Insira a edição do livro";
                          } else {
                            return null;
                          }
                        },
                      ),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    SizedBox(
                      height: 50,
                      child: TextFormField(
                        cursorColor: Colors.black,
                        style: Theme.of(context).textTheme.headline3,
                        controller: _isbnController,
                        decoration: InputDecoration(
                          labelText: "Isbn do livro",
                          hintText: "Insira o isbn do livro",
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15),
                          ),
                        ),
                        validator: (value) {
                          if (value!.isEmpty) {
                            return "Insira o isbn do livro";
                          } else {
                            return null;
                          }
                        },
                      ),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    SizedBox(
                      height: 50,
                      child: TextFormField(
                        cursorColor: Colors.black,
                        style: Theme.of(context).textTheme.headline3,
                        controller: _numberOfPagesController,
                        decoration: InputDecoration(
                          labelText: "Número de páginas",
                          hintText: "Insira o número de páginas",
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15),
                          ),
                        ),
                        validator: (value) {
                          if (value!.isEmpty) {
                            return "Insira o número de páginas";
                          } else {
                            return null;
                          }
                        },
                      ),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    const Divider(
                      color: Colors.black,
                      thickness: 2,
                    ),
                    Row(
                      children: [
                        const Text(
                          "Selecione a data de publicação: ",
                          style: TextStyle(fontSize: 16, color: Colors.black),
                        ),
                        Text(
                          DateFormat("dd/MM/yyyy")
                              .format(_publicationDate ?? DateTime.now()),
                          style: const TextStyle(
                              color: Colors.black, fontSize: 14),
                        ),
                        IconButton(
                            onPressed: () => showDatePicker(
                                  context: context,
                                  keyboardType: TextInputType.datetime,
                                  initialDate: DateTime.now(),
                                  firstDate: DateTime(1850, 1, 1),
                                  lastDate: DateTime(2050, 12, 31),
                                  builder: (context, child) => Theme(
                                      data: ThemeData().copyWith(
                                          colorScheme: const ColorScheme.light(
                                              primary: Colors.blue,
                                              surface: Colors.black)),
                                      child: child!),
                                ).then((selectedDate) {
                                  setState(() {
                                    _publicationDate = selectedDate;
                                  });
                                }),
                            icon: const Icon(Icons.date_range)),
                      ],
                    ),
                    const Divider(
                      color: Colors.black,
                      thickness: 2,
                    ),
                    const Text(
                      "Autores",
                      style: TextStyle(
                          color: Colors.black,
                          fontFamily: "Acme",
                          fontSize: 18),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    Container(
                      padding: const EdgeInsets.symmetric(
                          vertical: 0, horizontal: 10),
                      height: 120,
                      child: Card(
                        child: ListView.builder(
                          itemCount: authorsProvider.authors.length,
                          itemBuilder: ((context, index) => ListTile(
                              selected: _authorsList.any((author) =>
                                  author.id ==
                                  authorsProvider.authors[index].id),
                              selectedTileColor: Colors.blue,
                              dense: true,
                              title: Text(
                                authorsProvider.authors[index].name,
                                style: const TextStyle(
                                    color: Colors.black, fontSize: 14),
                              ),
                              onTap: () {
                                final selectedAuthor =
                                    authorsProvider.authors[index];
                                setState(() {
                                  if (_authorsList.any((author) =>
                                      author.id == selectedAuthor.id)) {
                                    _authorsList.removeWhere((author) =>
                                        selectedAuthor.id == author.id);
                                  } else {
                                    _authorsList.add(selectedAuthor);
                                  }
                                });
                              })),
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    const Divider(
                      color: Colors.black,
                      thickness: 2,
                    ),
                    const Text(
                      "Editoras",
                      style: TextStyle(
                          color: Colors.black,
                          fontFamily: "Acme",
                          fontSize: 18),
                    ),
                    const SizedBox(
                      height: 15,
                    ),
                    SizedBox(
                      height: 120,
                      child: Card(
                        child: ListView.builder(
                          itemCount: publishersProvider.publishers.length,
                          itemBuilder: ((context, index) => ListTile(
                              selected: _selectedPublisher != null
                                  ? _selectedPublisher!.id ==
                                      publishersProvider.publishers[index].id
                                  : false,
                              title: Text(
                                publishersProvider.publishers[index].name,
                                style: const TextStyle(
                                    color: Colors.black, fontSize: 14),
                              ),
                              selectedTileColor: Colors.blue,
                              onTap: () {
                                setState(() {
                                  if (_selectedPublisher !=
                                      publishersProvider.publishers[index]) {
                                    _selectedPublisher =
                                        publishersProvider.publishers[index];
                                  } else {
                                    _selectedPublisher = null;
                                  }
                                });
                              })),
                        ),
                      ),
                    ),
                  ],
                )),
          ),
        ),
      ),
    );
  }
}
