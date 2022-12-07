import 'package:book_reposity_app/models/bookstore.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../models/product.dart';
import '../../providers/bookstore/bookstore_provider.dart';

class BookstoreForm extends StatefulWidget {
  const BookstoreForm({super.key});

  @override
  State<BookstoreForm> createState() => _BookstoreFormState();
}

class _BookstoreFormState extends State<BookstoreForm> {
  final _nameController = TextEditingController();
  final List<Product> _productsList = [];
  Bookstore? _bookstore;

  var _isEditing = false;
  var _isLoadingMode = true;
  final _formKey = GlobalKey<FormState>();

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    if (_isLoadingMode) {
      _bookstore = ModalRoute.of(context)!.settings.arguments as Bookstore?;
      if (_bookstore != null) {
        _nameController.text = _bookstore!.name;
        if (_bookstore!.products != null) {
          _bookstore!.products!.forEach((product) {
            _productsList.add(product);
          });
        }
        _isEditing = true;
      }
      _isLoadingMode = false;
    }
  }

  void _validateForm() {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    Bookstore bookstore;

    bookstore = Bookstore(
      id: _isEditing ? _bookstore!.id : "",
      name: _nameController.text,
    );
    if (_isEditing) {
      Provider.of<BookstoresProvider>(context, listen: false)
          .updateBookstore(bookstore)
          .then((result) => Navigator.of(context).pop(result));
    } else {
      Provider.of<BookstoresProvider>(context, listen: false)
          .saveBookstore(bookstore)
          .then((result) => Navigator.of(context).pop<bool>(result));
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
    return Scaffold(
      appBar: AppBar(
        title: Text(
          _isEditing ? "Editando livraria" : "Criando livraria",
          style: const TextStyle(color: Colors.white, fontSize: 18),
        ),
        actions: [
          IconButton(onPressed: _validateForm, icon: const Icon(Icons.save))
        ],
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 5),
          child: Card(
            child: Padding(
              padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 10),
              child: Form(
                  key: _formKey,
                  child: Column(
                    children: [
                      const Text(
                        "Formulário da livraria",
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
                          keyboardType: TextInputType.name,
                          controller: _nameController,
                          decoration: InputDecoration(
                            labelText: "Nome da livraria",
                            hintText: "Insira o nome da livraria",
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
                      ),
                    ],
                  )),
            ),
          ),
        ),
      ),
    );
  }
}
