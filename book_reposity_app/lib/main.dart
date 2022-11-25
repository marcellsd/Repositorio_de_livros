import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'views/author/author_form.dart';
import 'views/author/author_menu_screen.dart';
import 'views/author/authors_list_screen.dart';
import 'views/splash_screen.dart';
import 'views/auth/auth_screen.dart';
import 'providers/auth_provider.dart';
import 'providers/authors_provider.dart';
import 'views/home_screen.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => AuthProvider()),
        ChangeNotifierProvider(create: (context) => AuthorsProvider())
      ],
      child: MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    Provider.of<AuthProvider>(context, listen: false).initializeToken();
    return MaterialApp(
      theme: Theme.of(context).copyWith(
        primaryColor: Colors.blue,
        textTheme: const TextTheme(
          headline6: TextStyle(
              fontFamily: "Cherry Bomb", fontSize: 42, color: Colors.blue),
          headline1:
              TextStyle(fontFamily: "Acme", fontSize: 14, color: Colors.black),
          headline3:
              TextStyle(fontFamily: "Acme", fontSize: 18, color: Colors.black),
        ),
      ),
      debugShowCheckedModeBanner: false,
      home: const SplashScreen(),
      routes: {
        "/home-screen": (context) => const HomeScreen(),
        "/auth-screen": (context) => AuthScreen(),
        "/authors-list-screen": (context) => const AuthorsListScreen(),
        "/author-menu-screen": (context) => const AuthorMenuScreen(),
        "/author-form-screen": (context) => AuthorForm()
      },
    );
  }
}
