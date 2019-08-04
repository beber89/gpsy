import 'dart:async';

import 'package:flutter/services.dart';

class Gpsy {
  static const MethodChannel _channel =
      const MethodChannel('gpsy');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
