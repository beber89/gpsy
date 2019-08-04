import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:gpsy/gpsy.dart';

void main() {
  const MethodChannel channel = MethodChannel('gpsy');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await Gpsy.platformVersion, '42');
  });
}
