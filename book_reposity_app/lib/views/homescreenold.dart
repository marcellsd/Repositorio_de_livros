import 'package:book_reposity_app/providers/auth_provider.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

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
      )),
      body: SizedBox(
        width: double.infinity,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              child: const Text("Listar autores"),
              onPressed: () =>
                  Navigator.of(context).pushNamed("/authors-screen"),
            ),
            ElevatedButton(
              child: const Text("Listar editores"),
              onPressed: () =>
                  Navigator.of(context).pushNamed("/authors-screen"),
            ),
            ElevatedButton(
              child: const Text("Listar livros"),
              onPressed: () =>
                  Navigator.of(context).pushNamed("/authors-screen"),
            ),
            ElevatedButton(
              child: const Text("Listar livrarias"),
              onPressed: () =>
                  Navigator.of(context).pushNamed("/authors-screen"),
            ),
            ElevatedButton(
              child: const Text("Logout"),
              onPressed: () => Provider.of<AuthProvider>(context, listen: false)
                  .logout()
                  .then((_) => Navigator.of(context)
                      .pushReplacementNamed("/auth-screen")),
            ),
          ],
        ),
      ),
    );
  }
}
