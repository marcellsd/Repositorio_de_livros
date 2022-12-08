import '/configs/environment_config.dart';

class DevEnvConfig implements EnvironmentConfig {
  @override
  String get apiUrl => "http://10.0.2.2:8080/api";
}
