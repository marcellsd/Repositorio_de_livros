import 'package:flutter/material.dart';

import '../providers/auth_provider.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    Future.wait([
      AuthProvider().isAuth(),
      AuthProvider().initializeToken(),
      Future.delayed(const Duration(seconds: 2))
    ]).then((value) => value[0]
        ? Navigator.of(context).pushReplacementNamed("/home-screen")
        : Navigator.of(context).pushReplacementNamed("/auth-screen"));
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      child: const Center(child: CircularProgressIndicator()),
    );
  }
}
