import 'package:flutter/cupertino.dart';

import '../../models/publisher.dart';

class PublishersProvider extends ChangeNotifier {
  final List<Publisher> _publishers = [];

  List<Publisher> get publishers => _publishers;
}
