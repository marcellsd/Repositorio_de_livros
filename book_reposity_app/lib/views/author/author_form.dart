import 'package:book_reposity_app/models/author.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../models/book_model.dart';
import '../../providers/author/authors_provider.dart';
import '../../widgets/book/book_item.dart';

class AuthorForm extends StatefulWidget {
  @override
  State<AuthorForm> createState() => _AuthorFormState();
}

class _AuthorFormState extends State<AuthorForm> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  Author? _author;
  late bool _isEditing;
  bool _isLoadingMode = true;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    if (_isLoadingMode) {
      _author = ModalRoute.of(context)!.settings.arguments as Author?;
      _isEditing = _author != null ? true : false;
      _isLoadingMode = false;
    }

    _nameController.text = _isEditing ? _author!.name : "";
  }

  void _validateForm() {
    if (!_formKey.currentState!.validate()) {
      return;
    }
    Author? author;
    _isEditing
        ? author = Author(_author!.id, _nameController.text, _author!.books)
        : author = Author("", _nameController.text, []);

    if (_isEditing) {
      Provider.of<AuthorsProvider>(context, listen: false)
          .updateAuthor(author)
          .then((result) => Navigator.of(context).pop(result));
    } else {
      Provider.of<AuthorsProvider>(context, listen: false)
          .saveAuthor(author)
          .then((result) => Navigator.of(context).pop(result));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          _isEditing ? "Editando autor" : "Criando autor",
          style: const TextStyle(color: Colors.white, fontSize: 18),
        ),
        actions: [
          IconButton(onPressed: _validateForm, icon: const Icon(Icons.save))
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 5),
        child: Card(
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 10),
            child: Form(
                key: _formKey,
                child: Column(
                  children: [
                    const Text(
                      "Formulário do autor",
                      style: TextStyle(
                          fontFamily: "Acme",
                          fontSize: 24,
                          color: Colors.black),
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    TextFormField(
                      cursorColor: Colors.black,
                      style: Theme.of(context).textTheme.headline3,
                      keyboardType: TextInputType.name,
                      controller: _nameController,
                      decoration: InputDecoration(
                        labelText: "Nome do autor",
                        hintText: "Insira o nome do autor",
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                        ),
                      ),
                      validator: (value) {
                        if (value!.isEmpty) {
                          return "Insira um nome";
                        } else {
                          return null;
                        }
                      },
                    ),
                    const SizedBox(
                      height: 20,
                    ),
                    if (_isEditing)
                      Expanded(
                        child: ListView.separated(
                            separatorBuilder: ((context, index) =>
                                const SizedBox(
                                  height: 5,
                                )),
                            itemCount: _author!.books.length,
                            itemBuilder: ((context, index) =>
                                BookItem(_author!.books[index]))),
                      ),
                  ],
                )),
          ),
        ),
      ),
    );
  }
}
