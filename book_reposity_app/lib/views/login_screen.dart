import 'package:flutter/material.dart';

import 'register_screen.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        height: MediaQuery.of(context).size.height,
        decoration: const BoxDecoration(
            image: DecorationImage(
                image: AssetImage("assets/images/livraria.png"),
                fit: BoxFit.fill)),
        child: Center(
          child: Container(
            margin: const EdgeInsets.symmetric(vertical: 0, horizontal: 20),
            height: 450,
            child: Card(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Text(
                    "Livraria Online",
                    style: TextStyle(fontSize: 42),
                  ),
                  Padding(
                    padding:
                        const EdgeInsets.symmetric(vertical: 0, horizontal: 40),
                    child: Form(
                      child: Padding(
                        padding: const EdgeInsets.symmetric(vertical: 60),
                        child: Column(
                          children: [
                            SizedBox(
                              height: 50,
                              child: TextFormField(
                                  decoration: InputDecoration(
                                      border: OutlineInputBorder(
                                          borderRadius:
                                              BorderRadius.circular(15)),
                                      labelText: "Login")),
                            ),
                            const SizedBox(
                              height: 15,
                            ),
                            SizedBox(
                              height: 50,
                              child: TextFormField(
                                decoration: InputDecoration(
                                    border: OutlineInputBorder(
                                        borderRadius:
                                            BorderRadius.circular(15)),
                                    labelText: "Senha"),
                              ),
                            ),
                            const SizedBox(
                              height: 15,
                            ),
                            TextButton(
                                onPressed: () => Navigator.of(context)
                                    .pushReplacementNamed("/register-screen"),
                                child: const Text(
                                    "Não possui conta? Cadastre-se agora!")),
                            const SizedBox(
                              height: 25,
                            ),
                            ClipRRect(
                              borderRadius: BorderRadius.circular(15),
                              child: ElevatedButton(
                                  style: ElevatedButton.styleFrom(
                                      elevation: 5,
                                      fixedSize: const Size(300, 50)),
                                  onPressed: () {},
                                  child: const Text("Entrar")),
                            )
                          ],
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
