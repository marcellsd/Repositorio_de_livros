import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../providers/auth_provider.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    var user = Provider.of<AuthProvider>(context, listen: false);

    return Scaffold(
      appBar: AppBar(
        title: Text(
          "Bem vindo, ${user.username}",
          style: const TextStyle(color: Colors.white, fontSize: 18),
        ),
        actions: [
          IconButton(
              onPressed: () {
                user.logout();
                Navigator.of(context).pushReplacementNamed("/auth-screen");
              },
              icon: const Icon(Icons.logout)),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
        child: Center(
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                InkWell(
                  onTap: () =>
                      Navigator.of(context).pushNamed("/author-menu-screen"),
                  child: Container(
                    alignment: Alignment.center,
                    height: 160,
                    width: double.infinity,
                    decoration: BoxDecoration(
                      color: Colors.orange,
                      borderRadius: BorderRadius.circular(15),
                    ),
                    child: Text(
                      "Autor",
                      style: Theme.of(context).textTheme.headline5,
                    ),
                  ),
                ),
                const SizedBox(
                  height: 10,
                ),
                InkWell(
                  onTap: () =>
                      Navigator.of(context).pushNamed("/book-menu-screen"),
                  child: Container(
                    alignment: Alignment.center,
                    height: 160,
                    width: double.infinity,
                    decoration: BoxDecoration(
                      color: Colors.purple,
                      borderRadius: BorderRadius.circular(15),
                    ),
                    child: Text(
                      "Livro",
                      style: Theme.of(context).textTheme.headline5,
                    ),
                  ),
                ),
                const SizedBox(
                  height: 10,
                ),
                InkWell(
                  child: Container(
                    alignment: Alignment.center,
                    height: 160,
                    width: double.infinity,
                    decoration: BoxDecoration(
                      color: Colors.blue,
                      borderRadius: BorderRadius.circular(15),
                    ),
                    child: Text(
                      "Editora",
                      style: Theme.of(context).textTheme.headline5,
                    ),
                  ),
                ),
                const SizedBox(
                  height: 10,
                ),
                InkWell(
                  child: Container(
                    alignment: Alignment.center,
                    height: 160,
                    width: double.infinity,
                    decoration: BoxDecoration(
                      color: Colors.green,
                      borderRadius: BorderRadius.circular(15),
                    ),
                    child: Text(
                      "Livraria",
                      style: Theme.of(context).textTheme.headline5,
                    ),
                  ),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
