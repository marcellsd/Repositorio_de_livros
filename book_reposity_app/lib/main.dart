import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'providers/publisher/publishers_provider.dart';
import 'providers/bookstore/bookstore_provider.dart';
import 'views/book/book_form.dart';
import 'providers/book/books_provider.dart';
import 'views/author/author_form.dart';
import 'views/author/author_menu_screen.dart';
import 'views/author/authors_list_screen.dart';
import 'views/book/book_menu_screen.dart';
import 'views/book/books_list_screen.dart';
import 'views/bookstore/bookstore_form.dart';
import 'views/bookstore/bookstores_list_screen.dart';
import 'views/bookstore/bookstore_menu_screen.dart';
import 'views/publisher/publisher_form.dart';
import 'views/publisher/publisher_menu_screen.dart';
import 'views/publisher/publishers_list_screen.dart';
import 'views/splash_screen.dart';
import 'views/auth/auth_screen.dart';
import 'providers/auth_provider.dart';
import 'providers/author/authors_provider.dart';
import 'views/home_screen.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => AuthProvider()),
        ChangeNotifierProvider(create: (context) => AuthorsProvider()),
        ChangeNotifierProvider(create: (context) => BooksProvider()),
        ChangeNotifierProvider(create: (context) => PublishersProvider()),
        ChangeNotifierProvider(create: (context) => BookstoresProvider())
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
        "/book-menu-screen": (context) => const BookMenuScreen(),
        "/books-list-screen": (context) => const BooksListScreen(),
        "/publisher-menu-screen": (context) => const PublisherMenuScreen(),
        "/publishers-list-screen": (context) => const PublishersListScreen(),
        "/bookstore-menu-screen": (context) => const BookstoreMenuScreen(),
        "/bookstores-list-screen": (context) => const BookstoresListScreen()
      },
      onGenerateRoute: (RouteSettings settings) {
        final String routeName = settings.name ?? '';

        switch (routeName) {
          case '/book-form-screen':
            return MaterialPageRoute<bool>(
              builder: (BuildContext context) => const BookForm(),
              settings: settings,
            );
          case '/author-form-screen':
            return MaterialPageRoute<bool>(
                builder: (context) => AuthorForm(), settings: settings);
          case '/publisher-form-screen':
            return MaterialPageRoute<bool>(
                builder: (context) => const PublisherForm(),
                settings: settings);
          case '/bookstore-form-screen':
            return MaterialPageRoute<bool>(
                builder: (context) => const BookstoreForm(),
                settings: settings);
        }
      },
    );
  }
}
