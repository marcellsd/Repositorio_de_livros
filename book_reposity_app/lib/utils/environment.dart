import '../configs/dev_env_config.dart';
import '../configs/environment_config.dart';
import '../configs/homo_env_config.dart';

class Environment {
  factory Environment() {
    return _singleton;
  }

  Environment._internal();

  static final Environment _singleton = Environment._internal();

  static const String DEV = 'DEV';
  static const String HOMO = 'HOMO';

  late EnvironmentConfig config;

  initConfig(String environment) {
    config = _getConfig(environment);
  }

  EnvironmentConfig _getConfig(String environment) {
    switch (environment) {
      case Environment.HOMO:
        return HomoEnvConfig();
      default:
        return DevEnvConfig();
    }
  }
}
