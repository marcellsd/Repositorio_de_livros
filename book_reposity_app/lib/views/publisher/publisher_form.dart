import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../models/address.dart';
import '../../models/publisher.dart';
import '../../providers/publisher/publishers_provider.dart';

class PublisherForm extends StatefulWidget {
  const PublisherForm({super.key});

  @override
  State<PublisherForm> createState() => _PublisherFormState();
}

class _PublisherFormState extends State<PublisherForm> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _hqAddressController = TextEditingController();
  final _webSiteAddressController = TextEditingController();
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

    var newAddress =
        Address("", _hqAddressController.text, _webSiteAddressController.text);

    var publisher = Publisher(
        id: "", name: _nameController.text, address: newAddress, books: []);

    Provider.of<PublishersProvider>(context, listen: false)
        .savePublisher(publisher)
        .then((_) => Navigator.of(context).pop());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          _isEditing ? "Editando editora" : "Criando editora",
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
                    "Formulário da editora",
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
                      labelText: "Nome da editora",
                      hintText: "Insira o nome da editora",
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
                  TextFormField(
                    cursorColor: Colors.black,
                    style: Theme.of(context).textTheme.headline3,
                    controller: _hqAddressController,
                    decoration: InputDecoration(
                      labelText: "Endereço da editora",
                      hintText: "Insira o endereço da editora",
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(15),
                      ),
                    ),
                    validator: (value) {
                      if (value!.isEmpty) {
                        return "Insira um endereço";
                      } else {
                        return null;
                      }
                    },
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  TextFormField(
                    cursorColor: Colors.black,
                    style: Theme.of(context).textTheme.headline3,
                    controller: _webSiteAddressController,
                    decoration: InputDecoration(
                      labelText: "Website da editora",
                      hintText: "Insira o website da editora",
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(15),
                      ),
                    ),
                    validator: (value) {
                      if (value!.isEmpty) {
                        return "Insira um website";
                      } else {
                        return null;
                      }
                    },
                  ),
                ],
              )),
        ),
      ),
    );
  }
}
