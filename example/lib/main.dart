import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:gpsy/gpsy.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  double snr = -2;
  int _count = -2;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await Gpsy.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: _buildScaffold()
        ),
      ),
    );
  }
  Widget _buildScaffold() => Column(children: <Widget>[
      Text('Running on: $_platformVersion\n'),
      RaisedButton(onPressed: ()=>setState((){
        Gpsy.gpsyFunction.then((num)=>snr=num);
      }),
      child: Text("snr"),
      ),
    Text(snr.toString()),
    RaisedButton(onPressed: ()=>setState((){
        Gpsy.countSatellites.then((num)=>_count=num);
      }),
      child: Text("count"),
      ),
    Text(_count.toString()),
    ]);
}
