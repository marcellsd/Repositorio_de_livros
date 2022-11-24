import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../models/user_model.dart';
import '../providers/auth_provider.dart';

class AuthScreen extends StatefulWidget {
  @override
  State<AuthScreen> createState() => _AuthScreenState();
}

class _AuthScreenState extends State<AuthScreen> {
  var _isLogin = true;
  var _isAuthor = false;
  var _isPublisher = false;
  var _isObscure = true;
  var _isLoading = false;
  final _loginController = TextEditingController();
  final _passwordController = TextEditingController();
  final _formKey = GlobalKey<FormState>();

  @override
  void initState() {
    super.initState();
    _loginController.text = "";
    _passwordController.text = "";
  }

  void _validateForm() {
    if (!_formKey.currentState!.validate()) {
      return;
    }

    UserModel? _updatedUser;

    if (!_isLogin) {
      var user = UserModel(_loginController.text, _passwordController.text,
          _isAuthor, _isPublisher);
      Provider.of<AuthProvider>(context, listen: false)
          .registerUser(user)
          .then((value) {
        if (value != null) {
          Future.delayed(const Duration(seconds: 2)).then(
            (_) {
              setState(() {
                _updatedUser = value;
                _isLogin = true;
                _isLoading = false;
              });
            },
          );
        } else {
          print("error");
        }
      });
    } else {
      Provider.of<AuthProvider>(context, listen: false)
          .authenticateUser(_loginController.text, _passwordController.text)
          .then(
        (token) {
          if (token != '') {
            Navigator.of(context).pushReplacementNamed("/home-screen");
          }
        },
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Container(
          height: MediaQuery.of(context).size.height,
          decoration: const BoxDecoration(
              image: DecorationImage(
                  image: AssetImage("assets/images/livraria.png"),
                  fit: BoxFit.fill)),
          child: Center(
            child: Container(
              margin: const EdgeInsets.symmetric(vertical: 0, horizontal: 20),
              height: _isLogin ? 500 : 600,
              child: Card(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(
                      "Livraria Online",
                      style: Theme.of(context).textTheme.headline6,
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(
                          vertical: 0, horizontal: 40),
                      child: Form(
                        key: _formKey,
                        child: Padding(
                          padding: const EdgeInsets.symmetric(vertical: 40),
                          child: Column(
                            children: [
                              SizedBox(
                                height: 80,
                                child: TextFormField(
                                  controller: _loginController,
                                  style: Theme.of(context).textTheme.headline3,
                                  decoration: InputDecoration(
                                      prefixIcon: const Icon(Icons.person),
                                      border: OutlineInputBorder(
                                          borderRadius:
                                              BorderRadius.circular(15)),
                                      labelText: "Login"),
                                  validator: (value) {
                                    if (value!.isEmpty) {
                                      return "Insira um login";
                                    } else {
                                      return null;
                                    }
                                  },
                                ),
                              ),
                              const SizedBox(
                                height: 15,
                              ),
                              SizedBox(
                                height: 80,
                                child: TextFormField(
                                  controller: _passwordController,
                                  style: Theme.of(context).textTheme.headline3,
                                  obscureText: _isObscure,
                                  keyboardType: TextInputType.visiblePassword,
                                  decoration: InputDecoration(
                                      prefixIcon: const Icon(Icons.lock),
                                      suffixIcon: IconButton(
                                        icon: const Icon(
                                          Icons.visibility,
                                        ),
                                        onPressed: () {
                                          setState(() {
                                            _isObscure = !_isObscure;
                                          });
                                        },
                                      ),
                                      border: OutlineInputBorder(
                                          borderRadius:
                                              BorderRadius.circular(15)),
                                      labelText: "Senha"),
                                  validator: (value) {
                                    if (value!.isEmpty) {
                                      return "Insira uma senha";
                                    } else if (value.length < 6) {
                                      return "Insira uma senha de no mínimo 6 caracteres";
                                    } else {
                                      return null;
                                    }
                                  },
                                ),
                              ),
                              const SizedBox(
                                height: 15,
                              ),
                              if (!_isLogin)
                                Column(
                                  children: [
                                    SizedBox(
                                      height: 40,
                                      child: CheckboxListTile(
                                          visualDensity: VisualDensity.compact,
                                          contentPadding:
                                              const EdgeInsets.all(0),
                                          controlAffinity:
                                              ListTileControlAffinity.leading,
                                          title: Text(
                                            "Editor",
                                            style: Theme.of(context)
                                                .textTheme
                                                .headline1,
                                          ),
                                          value: _isPublisher,
                                          onChanged: (value) {
                                            setState(() {
                                              _isPublisher = value!;
                                            });
                                          }),
                                    ),
                                    SizedBox(
                                      height: 40,
                                      child: CheckboxListTile(
                                          visualDensity: VisualDensity.compact,
                                          contentPadding:
                                              const EdgeInsets.all(0),
                                          controlAffinity:
                                              ListTileControlAffinity.leading,
                                          title: Text(
                                            "Autor",
                                            style: Theme.of(context)
                                                .textTheme
                                                .headline1,
                                          ),
                                          value: _isAuthor,
                                          onChanged: (value) {
                                            setState(() {
                                              _isAuthor = value!;
                                            });
                                          }),
                                    ),
                                  ],
                                ),
                              const SizedBox(
                                height: 5,
                              ),
                              TextButton(
                                  onPressed: () {
                                    setState(() {
                                      _isLogin = !_isLogin;
                                    });
                                  },
                                  child: Text(_isLogin
                                      ? "Não possui conta? Cadastre-se agora."
                                      : "Já possui conta? Faça login.")),
                              const SizedBox(
                                height: 25,
                              ),
                              ClipRRect(
                                borderRadius: BorderRadius.circular(15),
                                child: ElevatedButton(
                                    style: ElevatedButton.styleFrom(
                                        elevation: 5,
                                        fixedSize: const Size(300, 50)),
                                    onPressed: () {
                                      setState(() {
                                        _isLoading = true;
                                        _validateForm();
                                      });
                                    },
                                    child: _isLoading
                                        ? const CircularProgressIndicator(
                                            color: Colors.white,
                                          )
                                        : Text(
                                            _isLogin ? "Entrar" : "Cadastrar")),
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
      ),
    );
  }
}
