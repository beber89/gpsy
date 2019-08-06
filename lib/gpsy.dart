import 'dart:async';

import 'package:flutter/services.dart';

class Gpsy {
  static const MethodChannel _channel =
      const MethodChannel('gpsy');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  static Future<double> get gpsyFunction async {
    final double snr = await _channel.invokeMethod('gpsyFunction');
    return snr;
  }
  static Future<int> get countSatellites async {
    final int count = await _channel.invokeMethod('countSatellites');
    return count;
  }
}
