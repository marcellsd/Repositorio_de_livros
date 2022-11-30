import 'package:book_reposity_app/models/author.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../providers/author/authors_provider.dart';

class AuthorForm extends StatefulWidget {
  @override
  State<AuthorForm> createState() => _AuthorFormState();
}

class _AuthorFormState extends State<AuthorForm> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  late bool _isEditing;
  bool _isLoadingMode = true;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    if (_isLoadingMode) {
      _isEditing = ModalRoute.of(context)!.settings.arguments as bool;
      _isLoadingMode = false;
    }
  }

  void _validateForm() {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    var author = Author("0", _nameController.text, []);
    Provider.of<AuthorsProvider>(context, listen: false)
        .saveAuthor(author)
        .then((_) => Navigator.of(context).pop(true));
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
        padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 15),
        child: Card(
          child: Form(
              key: _formKey,
              child: Column(
                children: [
                  const Text(
                    "Formulário do autor",
                    style: TextStyle(
                        fontFamily: "Acme", fontSize: 24, color: Colors.black),
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  TextFormField(
                    cursorColor: Colors.black,
                    style: Theme.of(context).textTheme.headline3,
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
                ],
              )),
        ),
      ),
    );
  }
}
