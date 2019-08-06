package dev.dom.gpsy;

import android.Manifest;
import android.content.pm.PackageManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dev.dom.gpsy.data.SnrOptions;
import dev.dom.gpsy.tasks.SatellitesCountTask;
import dev.dom.gpsy.tasks.SnrUpdatesTask;
import dev.dom.gpsy.tasks.Task;
import dev.dom.gpsy.tasks.TaskFactory;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.util.Log;

// TODO Implement LocationListener and try
// TODO create an interface interwined with Task object to handle permission
// TODO implement gpsFunction using using Factory design pattern
/** GpsyPlugin */
public class GpsyPlugin implements MethodCallHandler {
  private final Registrar mRegistrar;
  private final Map<UUID, Task> mTasks = new HashMap<>();

  private static final int INITIAL_REQUEST = 1337;
  private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;
  private static final String[] LOCATION_PERMS = { Manifest.permission.ACCESS_FINE_LOCATION };


  private boolean hasPermission(String perm) {
    if(mRegistrar.activity() != null) {
      return (PackageManager.PERMISSION_GRANTED == mRegistrar.activity().checkSelfPermission(perm));
    }
    else {
      return (PackageManager.PERMISSION_GRANTED == mRegistrar.activeContext().checkSelfPermission(perm));
    }
  }

  private void askPermission(String[] perms, int req) {
    if(mRegistrar.activity() != null) {
      mRegistrar.activity().requestPermissions(perms, req);
    }
  }

  private GpsyPlugin(Registrar registrar) {
    this.mRegistrar  = registrar;
  }
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "gpsy");
    channel.setMethodCallHandler(new GpsyPlugin(registrar));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    System.out.println("[MethodCall]");
    Log.d("Plugin", "#########[MethodCall]");
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("gpsyFunction")) {
//      Task<SnrOptions> snrTask = TaskFactory.createSnrUpdatesTask(mRegistrar, result, call.arguments, this);
//      mTasks.put(snrTask.getTaskID(), snrTask);
//      snrTask.startTask();
      if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
        askPermission(LOCATION_PERMS, LOCATION_REQUEST);
      } else {
        SnrUpdatesTask snrUpdatesTask = new SnrUpdatesTask(mRegistrar, result);
        snrUpdatesTask.startTask();
      }
    }
    else if (call.method.equals("countSatellites")) {
      System.out.println("[MethodCall countSatellites]");
      Log.d("Plugin", "############[MethodCall countSatellites]");
      //      Task<SnrOptions> snrTask = TaskFactory.createSnrUpdatesTask(mRegistrar, result, call.arguments, this);
      //      mTasks.put(snrTask.getTaskID(), snrTask);
      //      snrTask.startTask();
            if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
              askPermission(LOCATION_PERMS, LOCATION_REQUEST);
            } else {
              SatellitesCountTask satellitesCountTask = new SatellitesCountTask(mRegistrar, result);
              satellitesCountTask.startTask();
            }
          }
    else {
      result.notImplemented();
    }
  }

//  public void onCompletion(UUID taskID) {
//    mTasks.remove(taskID);
//  }
}
