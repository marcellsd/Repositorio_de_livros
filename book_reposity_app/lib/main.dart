import 'package:book_reposity_app/views/auth_screen.dart';
import 'package:book_reposity_app/views/splash_screen.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'providers/auth_provider.dart';
import 'providers/authors_provider.dart';
import 'views/authors_screen.dart';
import 'views/homescreen.dart';

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
        "/authors-screen": (context) => const AuthorsScreen()
      },
    );
  }
}
