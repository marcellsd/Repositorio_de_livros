import 'package:flutter/material.dart';

class AuthorMenuScreen extends StatelessWidget {
  const AuthorMenuScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Módulo Autor",
          style: TextStyle(color: Colors.white, fontSize: 18),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(vertical: 0, horizontal: 15),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              TextButton(
                style: TextButton.styleFrom(backgroundColor: Colors.blue),
                onPressed: () =>
                    Navigator.of(context).pushNamed("/authors-list-screen"),
                child: Padding(
                  padding:
                      const EdgeInsets.symmetric(vertical: 2, horizontal: 10),
                  child: Text(
                    "Lista autores",
                    style: Theme.of(context).textTheme.headline3,
                  ),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              TextButton(
                style: TextButton.styleFrom(backgroundColor: Colors.green),
                onPressed: () => Navigator.of(context)
                    .pushNamed("/author-form-screen", arguments: false)
                    .then((result) {
                  var _result = result as bool;
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                      content: Text(_result
                          ? "Author adicionado com sucesso!"
                          : "Problema a cadastrar o author"),
                    ),
                  );
                }),
                child: Padding(
                  padding:
                      const EdgeInsets.symmetric(vertical: 2, horizontal: 10),
                  child: Text(
                    "Cadastrar autor",
                    style: Theme.of(context).textTheme.headline3,
                  ),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              TextButton(
                style: TextButton.styleFrom(backgroundColor: Colors.blue),
                onPressed: () => Navigator.of(context)
                    .pushNamed("/author-form-screen", arguments: true),
                child: Padding(
                  padding:
                      const EdgeInsets.symmetric(vertical: 2, horizontal: 10),
                  child: Text(
                    "Editar autor",
                    style: Theme.of(context).textTheme.headline3,
                  ),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              TextButton(
                style: TextButton.styleFrom(backgroundColor: Colors.orange),
                onPressed: () {},
                child: Padding(
                  padding:
                      const EdgeInsets.symmetric(vertical: 2, horizontal: 10),
                  child: Text(
                    "Remover autor",
                    style: Theme.of(context).textTheme.headline3,
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
